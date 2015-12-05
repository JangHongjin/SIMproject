package ADD_ON_System;

import java.util.ArrayList;

public class Map {
	//입력받은 start, hazard, target 정보로 lacked_Map을 사용 
	//0:정상 1:hazard 2:colorblob
	//real_Map에 랜덤하게 hazard와 colorblob를 추가
	int[][] lacked_Map;
	int[][] real_Map;
	ArrayList<Cell> robot_Path=new ArrayList<Cell>();
	ArrayList<Cell> target=new ArrayList<Cell>();

	public Map(int n, int m) {
		lacked_Map = new int[n][m];
		real_Map = new int[n][m];
		System.out.println("Lacked_Map이 초기화 되었습니다.");

	}
	
	
}