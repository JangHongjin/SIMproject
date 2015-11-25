package ADD_ON_System;

public class Map {
	static int[][] Lacked_Map;
	static int[][] Real_Map;
	static int[][] Path;
	static int[][] Targets;

	public Map(int n, int m) {
		Lacked_Map = new int[n][m];
		Real_Map = new int[n][m];
		System.out.println("Laced_Map이 초기화 되었습니다.");
	}
}
