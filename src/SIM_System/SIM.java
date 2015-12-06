package SIM_System;

import java.util.ArrayList;

import ADD_ON_System.AddOn;
import ADD_ON_System.Cell;
import ADD_ON_System.MapManager;

import UI_System.MapViewer;

public class SIM {
	MapManager mm = new MapManager();
	    
    public static boolean discvrHazards(MapManager MM, int direction){
    	boolean result=false;
    	int x=MM.getRobotPath().get(MM.getRobotPath().size()-1).x;
    	int y=MM.getRobotPath().get(MM.getRobotPath().size()-1).y;
    	if(direction==0){
    		y=y+1;
    	}
    	else if(direction==1){
    		y=y-1;
    	}
    	else if(direction==2){
    		x=x+1;
    	}
    	else if(direction==3){
    		x=x-1;
    	}
    	if(MM.getReal_map()[x][y]==1)
    		result=true;
    	else
    		result=false;
    	//���� ��ĭ�� hazard�ΰ�� true�� ����
    	return result;
    }
    public static int[] discvrColorBlobs(MapManager mm){
    	int[] colorblob=new int[4];
    	int x=mm.getRobotPath().get(mm.getRobotPath().size()-1).x;
    	int y=mm.getRobotPath().get(mm.getRobotPath().size()-1).y;
    	if(y+1<mm.getLacked_map()[0].length){
	    	if(mm.getReal_map()[x][y+1]==2) //������ġ�� ������ colorblob���� 
	    		colorblob[0]=2;
    	}
    	if(y-1>=0){
	    	if(mm.getReal_map()[x][y-1]==2)	//������ġ�� ������ colorblob���� 
	    		colorblob[1]=2;
    	}
    	if(x+1<mm.getLacked_map().length){
	    	if(mm.getReal_map()[x+1][y]==2)	//������ġ��  ������ colorblob���� 
	    		colorblob[2]=2;
    	}
    	if(x-1>=0){
	    	if(mm.getReal_map()[x-1][y]==2)//������ġ�� ������ colorblob���� 
	    		colorblob[3]=2;
    	}
    	//colorblob[0]:���� colorblob[1]:���� colorblob[2]:���� colorblob[3]:���� 
    	return colorblob;
    }
    public static boolean positionSensor(MapManager mm){
    	ArrayList<Cell> path=mm.getRobotPath();
		boolean result=true;
    	int x1=path.get(path.size()-1).x;
		int y1=path.get(path.size()-1).y;
		int x2=path.get(path.size()-2).x;
		int y2=path.get(path.size()-2).y;
			
		if(Math.abs(x1-x2)==2 || Math.abs(y1-y2)==2 ){
			result=false;
		}
		//2ĭ�̵��Ѱ�� false�� ����
		return result;
    }
     
    public static int turnRight(int currentDirection, int nextDirection){
		
    	while(currentDirection!=nextDirection){
			if(currentDirection==0){
				currentDirection=2;
				continue;
			}
			if(currentDirection==2){
				currentDirection=1;
				continue;
			}
			if(currentDirection==1){
				currentDirection=3;
				continue;
			}
			if(currentDirection==3){
				currentDirection=0;
				continue;
			}
		}
		//�����̵��� ���⿡ �°� ȸ��
    	return currentDirection;
	}
    
    public static void moveForward(MapManager mm, int direction){
		ArrayList<Cell> path=mm.getRobotPath();
		int x=path.get(path.size()-1).x;
		int y=path.get(path.size()-1).y;
		int a = (int) (Math.random()*50);
		
		if(direction==0){
			y=y+1;
			if(a==1){
				y=y+1;
			}
		}
		else if(direction==1){
			y=y-1;
			if(a==1){
				y=y-1;
			}
		}
		else if(direction==2){
			x=x+1;
			if(a==1){
				x=x+1;
			}
		}
		else if(direction==3){
			x=x-1;
			if(a==1){
				x=x-1;
			}
		}
		//�κ��� path�� �̵��� ���� �߰�
		mm.getRobotPath().add(new Cell(x,y));
		
	}
    
    public static void moveBack(MapManager mm){
    	ArrayList<Cell> path=mm.getRobotPath();
    	path.remove(path.size()-1);
    }

}

