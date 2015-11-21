package ADD_ON_System;

public class Map {
	private int[][] Lacked_Map;
	private int[][] Real_Map;
	private int[][] Path;
	private int[][] Targets;

	public Map(int n, int m) {
		Lacked_Map = new int[n][m];
		System.out.println("Laced_Map이 초기화 되었습니다.");
	}
}
