package ADD_ON_System;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;

public class MapManager {

// UI에서 String형식으로 받아올 공간
	private static String input_size = new String();	
	private static int[] map_size = new int[2];	
	private Map map;
	private boolean hazard=false;

// UI에서 받아온 정보를 Integer로 변환시켜 저장할 공간
	public int[] getMap_size() {
		return map_size;
	}
	public void setMap_size(int[] map_size) {
		MapManager.map_size = map_size;
	}
	
	public int[][] getLacked_map(){
		return map.lacked_Map;
	}
	public int[][] getReal_map(){
		return map.real_Map;
	}
	public ArrayList<Cell> getTarget(){
		return map.target;
	}
	public ArrayList<Cell> getRobotPath(){
		return map.robot_Path;
	}


	public static void setInput_size(String input) {
		MapManager.input_size = input; 
	}

// InputFrame에서 받은 값을 체크하는 함수	
	public boolean checkInput() {
		
		StringTokenizer st_size = new StringTokenizer(input_size, " (,)");		
		
// 지도 사이즈 설정
		while (st_size.hasMoreTokens()) {
			try {
				map_size[0] = Integer.parseInt(st_size.nextToken());
				map_size[1] = Integer.parseInt(st_size.nextToken());
			}
			catch (NoSuchElementException e) {	//x, y 좌표 중 하나 빠졌을 때 예외 처리
				System.out.println("입력이덜되었습니다");
				return false;
			}
		}
		
		if (map_size[0] <= 0 || map_size[1] <= 0) {	// 지도 좌표 값이 음수일 때 예외처리
			System.out.println("지도 사이즈가 0또는 음수입니다");
			return false;
		}

		System.out.println("모든 값이 적절합니다!!객체를 생성할 수 있어요"); // 객체 생성으로 이어짐
		return true;

	}


	public void randomSpots(Map map) {	
		int i,j;
		for(i=0; i<map.real_Map.length; i++){
			for(j=0; j<map.real_Map[0].length; j++){
				if(map.lacked_Map[i][j]==1){
					map.real_Map[i][j]=1;
				}
			}
		}
		
		for(i=0; i<map.real_Map.length; i++){
				int a = (int) (Math.random()*map.real_Map[0].length);
				if(map.real_Map[i][a]==1){
					continue;
				}
				boolean result=false;
				for(j=0; j<map.target.size(); j++){
					if(map.target.get(j).x==i&&map.target.get(j).y==a)
							result=true;
				}
				if(result==true)
					continue;
				else
					map.real_Map[i][a]=1;		
		}
		
		for(i=0; i<map.real_Map.length; i++){
			int a = (int) (Math.random()*map.real_Map[0].length);
			if(map.real_Map[i][a]==1){
				continue;
			}
			else
				map.real_Map[i][a]=2;		
		}
		
		for(i=0; i<map.lacked_Map.length;i++){
			for(j=0; j<map.lacked_Map[0].length;j++){
				System.out.print(map.lacked_Map[i][j]+" ");
			}
			System.out.println(" ");
		}
		System.out.println(" ");System.out.println(" ");
		for(i=0; i<map.real_Map.length;i++){
			for(j=0; j<map.real_Map[0].length;j++){
				System.out.print(map.real_Map[i][j]+" ");
			}
			System.out.println(" ");
		}
		for(i=0; i<map.robot_Path.size(); i++){
			System.out.println("Path:["+map.robot_Path.get(i).x+","+map.robot_Path.get(i).y+"]");
		}
		System.out.println(" ");
		for(i=0; i<map.target.size(); i++){
			System.out.println("target:["+map.target.get(i).x+","+map.target.get(i).y+"]");
		}
	}
	
	public void createMap() {
		this.map = new Map(map_size[0], map_size[1]);
	}
	
	public void initializeMap(int[][] arr){
		for(int i=0; i<arr.length; i++){
			for(int j=0; j<arr[0].length;j++){
				if(arr[i][j]==1)
					map.lacked_Map[i][j]=1;
				else if(arr[i][j]==2)
					map.target.add(new Cell(i,j));
				else if(arr[i][j]==3)
					map.robot_Path.add(new Cell(i,j));		
			}
		}
		
		randomSpots(map);
	}
	public boolean checkHazard(){
		return hazard;
	}
	
	public Map getMap(){
		return map;
	}
	
	public int getDirection(){
		ArrayList<Cell> path=map.robot_Path;
		int direction = 0;
		if(path.size()==1)
			return direction;
		int x1=path.get(path.size()-1).x;
		int x2=path.get(path.size()-2).x;
		int y1=path.get(path.size()-1).y;
		int y2=path.get(path.size()-2).y;

		if(x1-x2==-1){
			direction=2;
		}
		else if(x1-x2==1){
			direction=3;
		}
		else if(y1-y2==-1){
			direction=1;
		}
		else if(y1-y2==1){
			direction=0;
		}
		return direction;
	}
	
	public void updateMap(boolean result,int currentDirection, int[] colorblob){
		ArrayList<Cell> path=map.robot_Path;
		int x=path.get(path.size()-1).x;
		int y=path.get(path.size()-1).y;
		if(colorblob[0]==2)
			map.lacked_Map[x][y+1]=2;
		if(colorblob[1]==2)
			map.lacked_Map[x][y-1]=2;
		if(colorblob[2]==2)
			map.lacked_Map[x+1][y]=2;
		if(colorblob[3]==2)
			map.lacked_Map[x-1][y]=2;

		
		
		if(currentDirection==0)
			y=y+1;
		else if(currentDirection==1)
			y=y-1;
		else if(currentDirection==2)
			x=x+1;
		else if(currentDirection==3)
			x=x-1;
		if(result==true){
			map.lacked_Map[x][y]=1;
			hazard=true;
		}
		hazard=false;
	}	

}
