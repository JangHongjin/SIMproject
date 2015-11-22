package ADD_ON_System;

import java.util.StringTokenizer;

public class MapManager {
	private String input_size = new String();
	private int[] map_size = new int[2];
	private Map map;
	
	public String getInput_size() { return input_size; }
	public void setInput_size(String input_size) { this.input_size = input_size; }

	public Map getMap() { return map; }
	public void setMap(Map map) { this.map = map; }
	
	public boolean Check_Input() {
		int i=0;
		StringTokenizer st = new StringTokenizer(input_size, " (,)");

		while(st.hasMoreTokens()) 
			map_size[i++] = Integer.parseInt(st.nextToken());
		
		if (this.map_size[0] <= 0 || this.map_size[1] <= 0) {
			System.out.println("다시 입력해주세요"); // InputFrame 재입력 요구하게 만들어야
			return false;
		}

		else {
			System.out.println("값이 정확합니다"); // 객체 생성으로 이어짐
			return true;
		}
	}

	public void Create_Map() {
		map = new Map(this.map_size[0], this.map_size[1]);
	}
}
