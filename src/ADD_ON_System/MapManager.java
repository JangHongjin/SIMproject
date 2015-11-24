package ADD_ON_System;

import java.util.StringTokenizer;

import UI_System.MainFrame;

public class MapManager {
	// UI에서 String형식으로 받아올 공간
	private static String input_size = new String();
	private static String input_hazard = new String();
	private static String input_target = new String();

	//UI에서 받아온 정보를 Integer로 변환시켜 저장할 공간
	private static int[] map_size = new int[2];
	private static int[][] map_hazard;
	private static int[][] map_target;
	private static int[] temp;

	private Map map;
	
	public static void setInput_size(String input) { MapManager.input_size = input; }
	public static void setInput_hazard(String input) { MapManager.input_hazard = input; }
	public static void setInput_target(String input) { MapManager.input_target = input; }
	
	public Map getMap() { return map; }
	
	public boolean Check_Input() {
		int i, j;
		StringTokenizer st_size = new StringTokenizer(input_size, " (,)");
		StringTokenizer st_hazard = new StringTokenizer(input_hazard, " (,)");
		StringTokenizer st_target = new StringTokenizer(input_target, " (,)");
		
		i=0;
		while(st_size.hasMoreTokens())		//지도 사이즈 설정 
			map_size[i++] = Integer.parseInt(st_size.nextToken());
		
		map_hazard = new int[map_size[0]][map_size[1]];	//지도와 같은 크기의 배열생성, 초기화
		map_target = new int[map_size[0]][map_size[1]];
		
		temp = new int[map_size[0]*map_size[1]];	// hazard, target받아오기 위해 거치는 곳
		
		// 지도내용 hazard : 1 / target : 2 / nothing : -1
		
		for(i=0; i<map_size[0]; i++) {	// -1로 초기화
			for(j=0; j<map_size[1]; j++) {
				map_hazard[i][j]=-1;
				map_target[i][j]=-1;
			}
		}
		
		for(i=0; i<(map_size[0]*map_size[1]); i++)
			temp[i] = -1;
		
		i=0;	// hazard 지도 정보 입력
		while (st_hazard.hasMoreTokens()) {
			temp[i++] = Integer.parseInt(st_hazard.nextToken());	//rows
			temp[i++] = Integer.parseInt(st_hazard.nextToken());	//cols
		}
		
		i=0; j=1;	
		while (temp[i] != -1) {
			map_hazard[temp[i]][temp[j]] = 1;
			i+=2; j+=2;
		}
		
		i=0;	//target 지도 정보 입력
		while (st_target.hasMoreTokens()) {
			temp[i++] = Integer.parseInt(st_target.nextToken());
			temp[i++] = Integer.parseInt(st_target.nextToken());
		}
		
		i=0; j=1;
		while (temp[i] != -1) {
			try {
			map_target[temp[i]][temp[j]] = 2;
			} catch(IndexOutOfBoundsException e) {
				System.out.println(e.getMessage());
				System.out.println("target정보 입력이 잘못되었습니다.");
				MainFrame.Call_Input_Frame();
				break;
			}
			i+=2; j+=2;
		}
		
		i=0;

		//Check_Input
		if (map_size[0] <= 0 || map_size[1] <= 0) {	//지도정보 입력내용이 음수면
			System.out.println("다시 입력해주세요"); // InputFrame 재입력 요구하게 만들어야
			return false;
		}

		else {
			System.out.println("값이 정확합니다"); // 객체 생성으로 이어짐
			return true;
		}
	}

	// 지도 사이즈보다 hazard, target 좌표 숫자가 클 경우에 예외처리 해야함 (배열 오버플로)
	
	public void Create_Map() {
		this.map = new Map(map_size[0], map_size[1]);
	}
}
