package AddOnSystem;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class AddOn {
    public static final int VHCOST = 10;
    
    
        
    //Blocked cells are just null Cell values in grid
    static Cell [][] grid = new Cell[5][5];
    
    static PriorityQueue<Cell> open;
     
    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;
            
    public static void setBlocked(int i, int j){
        grid[i][j] = null;
    }
    
    public static void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }
    
    public static void setEndCell(int i, int j){
        endI = i;
        endJ = j; 
    }
    
    static void checkAndUpdateCost(Cell current, Cell t, int cost){
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.heuristicCost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }
    
    public static void AStar(){ 
        
        //add the start location to open list.
        open.add(grid[startI][startJ]);
        
        Cell current;
        
        while(true){ 
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true; 

            if(current.equals(grid[endI][endJ])){
                return; 
            } 

            Cell t;  
            if(current.i-1>=0){
                t = grid[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+VHCOST); 
            } 

            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+VHCOST); 
            }

            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+VHCOST); 
            }

            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+VHCOST); 
            }
        } 
    }
   
    public static int searchPath(int[][] mymap, ArrayList<Cell> robotPath,ArrayList<Cell> target){
            //Reset
           int x=mymap.length;
           int y=mymap[0].length;
           int direction=7;
           int cx=robotPath.get(robotPath.size()-1).i;
           int cy=robotPath.get(robotPath.size()-1).j;
           int ex=0;
           int ey=0;
           int temp=100000;
           ArrayList<Cell> left_target=new ArrayList<Cell>();
           left_target=(ArrayList<Cell>) target.clone();
           
           
           for(int i=0; i<robotPath.size(); i++){
        	   for(int j=0; j<left_target.size();j++){
        		   if(robotPath.get(i).i==left_target.get(j).i && robotPath.get(i).j==left_target.get(j).j){
        			   left_target.remove(j);
        			   
        		   }
        	   }
           }
   
           for(int i=0; i<left_target.size(); i++){
        	   int tempx=robotPath.get(robotPath.size()-1).i;
        	   int tempy=robotPath.get(robotPath.size()-1).j;
        	   if( Math.addExact((int)Math.pow((tempx-left_target.get(i).i),2),(int) Math.pow((tempy-left_target.get(i).j),2))<temp){
        		   temp=Math.addExact((int)Math.pow((tempx-left_target.get(i).i),2),(int) Math.pow((tempy-left_target.get(i).j),2));
        		   ex=left_target.get(i).i;
        		   ey=left_target.get(i).j;
        		   
        	   }
        	   
        	   
           }
           
           for(int i=0; i<left_target.size(); i++){
        	   System.out.println("[[["+left_target.get(i).i+","+left_target.get(i).j+"]]]");
           }
           System.out.println(" ");
           for(int i=0; i<target.size(); i++){
        	   System.out.println("["+target.get(i).i+","+target.get(i).j+"]");
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

           System.out.println("[["+cx+","+cy+"]],[["+ex+","+ey+"]]");
           
           for(int i=0;i<x;++i){
              for(int j=0;j<y;++j){
                  grid[i][j] = new Cell(i, j);
                  grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
//                  System.out.print(grid[i][j].heuristicCost+" ");
              }
//              System.out.println();
           }
           grid[cx][cy].finalCost = 0;
           
           for(int i=0;i<block.size();++i){
               setBlocked(block.get(i).i, block.get(i).j);
           }
           
           //Display initial map
           System.out.println("Grid: ");
            for(int i=0;i<x;++i){
                for(int j=0;j<y;++j){
                   if(i==cx&&j==cy)System.out.print("S   "); //Source
                   else if(i==ex && j==ey)System.out.print("E   ");  //Destination
                   else if(grid[i][j]!=null)System.out.printf("%-3d ", 0);
                   else System.out.print("B   "); 
                }
                System.out.println();
            } 
            System.out.println();
           
           AStar(); 
           System.out.println("\nScores for cells: ");
           for(int i=0;i<x;++i){
               for(int j=0;j<x;++j){
                   if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
                   else System.out.print("B   ");
               }
               System.out.println();
           }
           System.out.println();
            
           if(closed[endI][endJ]){
               //Trace back the path 
                Cell current = grid[endI][endJ];
                
                while(current.parent!=null){
                    if((current.j-current.parent.j)==1){
                    	direction= 0;
                    }
                    else if((current.j-current.parent.j)==-1){
                    	direction= 1;
                    }
                    else if((current.i-current.parent.i)==1){
                    	direction= 2;
                    }
                    else if((current.i-current.parent.i)==-1){
                    	direction= 3;
                    }
            
                   current = current.parent;
                } 
                System.out.println();
           }else direction=4;
		return direction;
    }
     
    
     
    public static void main(String[] args) throws Exception{   
    	int lacked_Map[][]=	   {{0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,1,0,0,0},
								{0,0,0,0,1,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,1,0},	
								{0,0,1,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,1,0,0,0},
								{0,0,0,1,0,0,0,0,0,0},
								{0,1,0,1,0,0,0,0,1,0},
								{0,0,0,0,0,0,1,0,0,0}};
    	ArrayList<Cell> robotPath=new ArrayList<Cell>();
    	ArrayList<Cell> target=new ArrayList<Cell>();
    	robotPath.add(new Cell(2,2));
    	robotPath.add(new Cell(2,1));
    	robotPath.add(new Cell(3,1));
    	robotPath.add(new Cell(4,1));
    	robotPath.add(new Cell(5,1));
    	robotPath.add(new Cell(6,1));

    	
    	
    	target.add(new Cell(2,2));
    	target.add(new Cell(6,2));
    	target.add(new Cell(7,1));
    	
    
    	System.out.println(searchPath(lacked_Map,robotPath, target));
    	//System.out.println(searchPath(lacked_Map,robotPath.get(robotPath.size()-1).i,robotPath.get(robotPath.size()-1).j,1,2));

    	
    }
    
   
    
}