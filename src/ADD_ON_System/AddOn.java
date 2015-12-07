package ADD_ON_System;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

import javax.swing.JOptionPane;

import SIM_System.SIM;
import UI_System.MainFrame;
import UI_System.MapViewer;

public class AddOn {
	//가로세로 가중치
    public static final int VHCOST = 10;    
    //Blocked cells are just null Cell values in grid
    static Cell [][] grid = new Cell[40][40];
    
    static PriorityQueue<Cell> open;
     
    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;
            
    
    public static void setBlocked(int x, int y){
        grid[x][y] = null;
    }
    
    public static void setStartCell(int x, int y){
        startI = x;
        startJ = y;
    }
    
    public static void setEndCell(int x, int y){
        endI = x;
        endJ = y; 
    }
    
    static void checkAndUpdateCost(Cell current, Cell t, int cost){
        if(t == null || closed[t.x][t.y])return;
        int t_final_cost = t.heuristicCost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }
    

    public static boolean finishCheck(MapManager mm){
		boolean result=false;
		ArrayList<Cell> targetlist=(ArrayList<Cell>) mm.getTarget().clone();
		int left_target=targetlist.size();
		for(int i=0; i<targetlist.size(); i++){
			for(int j=0; j<mm.getRobotPath().size(); j++){
				if(targetlist.get(i).x==mm.getRobotPath().get(j).x && targetlist.get(i).y==mm.getRobotPath().get(j).y){
					left_target--;
					targetlist.set(i, new Cell(-1,-1));
				}				
			}
		}
		if(left_target==0){
			result=true;
		}
		return result;
	
	}
    public AddOn(MapViewer mv, MapManager mm){
    	while(true){
    		if(finishCheck(mm)){	//finsh_check의 반환값이 true인경우 while문 종료
    			System.out.println("finish!!");
    			MainFrame.finishMapViewer(mv, mm);
    			break;
    		}
    		
    		int currentDirection=mm.getDirection();		//현재로봇의 방향
    		int [][] lacked_Map=mm.getLacked_map();
    		int [][] real_Map=mm.getReal_map();
    		ArrayList<Cell> robotPath=mm.getRobotPath();
    		ArrayList<Cell> target=mm.getTarget();
    		//ASTAR알고리즘을 통한 다음 이동할 방향 
    		//동:0 서:1 남:2 북:3 경로X:4
    		
    		int nextDirection=AddOn.searchPath(lacked_Map, robotPath, target);
    		
    		//경로가 존재하지 않는경우 반복분 종료
    		if(nextDirection==4){
    		    JOptionPane.showMessageDialog(null, "경로 X! 탐색종료!!");  
    			break;
    		}
    		
    		
    		//다음이동방향에 맞게 로봇을 회전
    		if(currentDirection!=nextDirection)
    			currentDirection=SIM.turnRight(currentDirection,nextDirection);
    		
    		
    		int[] colorblob=new int[4];
    		boolean result;
    		colorblob=SIM.discvrColorBlobs(robotPath, lacked_Map, real_Map);
    		
    		result=SIM.discvrHazards(robotPath, real_Map, currentDirection);
    		mm.updateMap(result, currentDirection, colorblob);
    		if(result==true)
    			continue;
    		else{
    			SIM.moveForward(robotPath,currentDirection);
    			result=SIM.positionSensor(robotPath);
    			if(result==false)
    				SIM.moveBack(robotPath);	
    			}
    		try {
    			Thread.sleep(1000);
    		}catch(InterruptedException e){
    			System.out.println(e.getMessage()); //sleep 메소드가 발생하는 InterruptedException 
    		}
    		MainFrame.updateMapViewer(mv,mm);
    		}
    }
    

    public static void AStar(){ 
      
        	
        //add the start location to open list.
        open.add(grid[startI][startJ]);
        
        Cell current;
        
        while(true){ 
            current = open.poll();
            if(current==null)break;
            closed[current.x][current.y]=true; 

            if(current.equals(grid[endI][endJ])){
                return; 
            } 

            Cell t;  
            if(current.x-1>=0){
                t = grid[current.x-1][current.y];
                checkAndUpdateCost(current, t, current.finalCost+VHCOST); 
            } 

            if(current.y-1>=0){
                t = grid[current.x][current.y-1];
                checkAndUpdateCost(current, t, current.finalCost+VHCOST); 
            }

            if(current.y+1<grid[0].length){
                t = grid[current.x][current.y+1];
                checkAndUpdateCost(current, t, current.finalCost+VHCOST); 
            }

            if(current.x+1<grid.length){
                t = grid[current.x+1][current.y];
                checkAndUpdateCost(current, t, current.finalCost+VHCOST); 
            }
        } 
    }
   
    public static int searchPath(int[][] mymap, ArrayList<Cell> robotPath,ArrayList<Cell> target){
            //Reset
           int x=mymap.length;
           int y=mymap[0].length;
           int direction=7;
           int cx=robotPath.get(robotPath.size()-1).x;
           int cy=robotPath.get(robotPath.size()-1).y;
           int ex=0;
           int ey=0;
           int temp=100000;
           ArrayList<Cell> left_target=new ArrayList<Cell>();
           left_target=(ArrayList<Cell>) target.clone();
           
           //로봇의 경로중에 target가 있으면 해당 target을 list에서 제거
           for(int i=0; i<robotPath.size(); i++){
        	   for(int j=0; j<left_target.size();j++){
        		   if(robotPath.get(i).x==left_target.get(j).x && robotPath.get(i).y==left_target.get(j).y){
        			   left_target.remove(j);
        			   
        		   }
        	   }
           }
           //방문하지 않은 target에서 최소거리를 가진 타겟을 설정
           for(int i=0; i<left_target.size(); i++){
        	   int tempx=robotPath.get(robotPath.size()-1).x;
        	   int tempy=robotPath.get(robotPath.size()-1).y;
        	   if( Math.abs(tempx-left_target.get(i).x)+Math.abs(tempy-left_target.get(i).y)<temp){
        		   temp=Math.abs(tempx-left_target.get(i).x)+Math.abs(tempy-left_target.get(i).y);
        		   ex=left_target.get(i).x;
        		   ey=left_target.get(i).y;
        		   
        	   }
        	   
        	   
           }
           
           
           
           ArrayList<Cell> block=new ArrayList<Cell>();
           for(int i=0; i<x; i++){
        	   for(int j=0; j<y; j++){
        		   if(mymap[i][j]==1){
        			   Cell tempcell=new Cell(i,j);
        			   block.add(tempcell);
        		   }        		   
        	   }
           }
           
           
           
           grid = new Cell[x][y];
           closed = new boolean[x][y];
           open = new PriorityQueue<>((Object o1, Object o2) -> {
                Cell c1 = (Cell)o1;
                Cell c2 = (Cell)o2;

                return c1.finalCost<c2.finalCost?-1:
                        c1.finalCost>c2.finalCost?1:0;
            });
           //Set start position
           setStartCell(cx, cy);  //Setting to 0,0 by default. Will be useful for the UI part
           
           //Set End Location
           setEndCell(ex, ey); 

           
           for(int i=0;i<x;++i){
              for(int j=0;j<y;++j){
                  grid[i][j] = new Cell(i, j);
                  grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
              }
           }
           grid[cx][cy].finalCost = 0;
           
           for(int i=0;i<block.size();++i){
               setBlocked(block.get(i).x, block.get(i).y);
           }
           
           AStar(); 
            
           if(closed[endI][endJ]){
               //Trace back the path 
                Cell current = grid[endI][endJ];
                
          
                while(current.parent!=null){
                    if((current.y-current.parent.y)==1){
                    	direction= 0;
                    }
                    else if((current.y-current.parent.y)==-1){
                    	direction= 1;
                    }
                    else if((current.x-current.parent.x)==1){
                    	direction= 2;
                    }
                    else if((current.x-current.parent.x)==-1){
                    	direction= 3;
                    }
            
                   current = current.parent;
                } 
           }else direction=4;
           
           open=null;
           
           closed=null;
           //이동해야할 방향 리턴
           //0:동 1:서 2:남 3:북 4:경로존재 X
		return direction;
    }
}