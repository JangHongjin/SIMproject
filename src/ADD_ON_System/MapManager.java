package ADD_ON_System;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;

public class MapManager {

// UI에서 String형식으로 받아올 공간
	private static String input_position = new String();
	private static String input_size = new String();
	private static String input_hazard = new String();
	private static String input_target = new String();

// UI에서 받아온 정보를 Integer로 변환시켜 저장할 공간
	private static int[] map_position = new int[2];
	private static int[] map_size = new int[2];
	private static int[][] map_hazard;
//	private static int[][] map_target;	//얘는 배열로 둘 필요 없음. ArrayList로 정보 전달
	private static int[] temp;
	
	private static ArrayList<Cell> Target = new ArrayList<Cell>();	// SIM이 길 찾을 때 쓸 형식
	
	private Map map;

	public static void setInput_position(String map_position) {	MapManager.input_position = map_position; }
	public static void setInput_size(String input) { MapManager.input_size = input; }
	public static void setInput_hazard(String input) { MapManager.input_hazard = input; }
	public static void setInput_target(String input) { MapManager.input_target = input; }

// InputFrame에서 받은 값을 체크하는 함수	
	public boolean Check_Input() {
		int i, j;
		
		StringTokenizer st_position = new StringTokenizer(input_position, " (,)");
		StringTokenizer st_size = new StringTokenizer(input_size, " (,)");
		StringTokenizer st_hazard = new StringTokenizer(input_hazard, " (,)");
		StringTokenizer st_target = new StringTokenizer(input_target, " (,)");
		
// 지도 사이즈 설정
		while (st_size.hasMoreTokens()) {
			try {
				map_size[0] = Integer.parseInt(st_size.nextToken());
				map_size[1] = Integer.parseInt(st_size.nextToken());
			}
			catch (NoSuchElementException e) {	//x, y 좌표 중 하나 빠졌을 때 예외 처리
				System.out.println("뭔가 입력이 덜 됐지?");
				return false;
			}
		}
		
		if (map_size[0] <= 0 || map_size[1] <= 0) {	// 지도 좌표 값이 음수일 때 예외처리
			System.out.println("지도 좌표값이 음수야");
			return false;
		}
		
// 나머지 요소들도 초기화를 시작
		else {
			map_hazard = new int[map_size[0]][map_size[1]]; // 지도와 같은 크기의 배열을 생성
//			map_target = new int[map_size[0]][map_size[1]];
			temp = new int[map_size[0] * map_size[1]]; // hazard, target 좌표를 받아오기 위해 거치는 곳

// 지도내용 = 초기값 : -1 / hazard : 1 / colorblob : 2 / robot : 3
			for (i = 0; i < map_size[0]; i++) {
				for (j = 0; j < map_size[1]; j++) {
					map_hazard[i][j] = -1;
//					map_target[i][j] = -1;
				}
			}
			
// Position 초기화			
			while (st_position.hasMoreTokens()) {
				try {
					map_position[0] = Integer.parseInt(st_position.nextToken());
					map_position[1] = Integer.parseInt(st_position.nextToken());
				}
				catch (NoSuchElementException e) {	//x, y 좌표 중 하나 빠졌을 때 예외 처리
					System.out.println("뭔가 입력이 덜 됐지?");
					return false;
				}
			}
			
			if (map_position[0] < 0 || map_position[1] < 0) {	// 지도 좌표 값이 음수일 때 예외처리
				System.out.println("로봇 위치값이 음수입니다");
				return false;
			}
			
			if (map_position[0] >= map_size[0] || map_position[1] >= map_size[1]) {
				System.out.println("로봇 위치 좌표가 지도 사이즈를 벗어났습니다.");
				return false;
			}

// HAZARD 정보 입력
			for (i = 0; i < (map_size[0] * map_size[1]); i++)
				temp[i] = -1;
			
			i = 0; j = 1; // hazard 정보를 일단 temp에 옮김
			while (st_hazard.hasMoreTokens()) {
				try {
					temp[i] = Integer.parseInt(st_hazard.nextToken()); //행
					temp[j] = Integer.parseInt(st_hazard.nextToken()); //열
				} catch (NoSuchElementException e) {	// 예외1. x, y중 하나 빠졌을 때 예외처리
					System.out.println("hazard 입력 값 짝이 안맞음다");
					return false;
				}
				i += 2; j += 2;
			}

			if (temp[0] == -1 || temp[j - 2] == -1) { // 예외2. 값 입력자체가 안됐을 때 예외처리
				System.out.println("hazard값이 입력되지 않았습니다.");
				return false;
			}

			i = 0; j = 1;	// 진짜 target배열에 값을 저장 
			while (temp[i] != -1) {
				try {
					map_hazard[temp[i]][temp[j]] = 1;
				} catch (IndexOutOfBoundsException e) { // 예외3. hazard가 지도 범위를 벗어났을 때
					System.out.println("hazard 좌표가 지도 사이즈를 벗어났습니다.");
					return false;
				}
				i += 2; j += 2;
			}

// TARGET정보 입력
			for (i = 0; i < (map_size[0] * map_size[1]); i++)
				temp[i] = -1;

			i = 0; j = 1;	// target 정보를 일단 temp에 옮김
			while (st_target.hasMoreTokens()) {
				try {
				temp[i] = Integer.parseInt(st_target.nextToken());
				temp[j] = Integer.parseInt(st_target.nextToken());
				} catch (NoSuchElementException e) {	// 예외1. x, y중 하나 빠졌을 때 예외처리
					System.out.println("target 입력 값 짝이 안맞음다");
					return false;
				}
				i += 2; j += 2;
			}

			if (temp[0] == -1 || temp[j - 2] == -1) { // 예외2. 값 입력자체가 안됐을 때 예외처리
				System.out.println("target값이 입력되지 않았습니다.");
				return false;
			}

			i = 0; j = 1;	// 진짜 target배열에 값을 저장 
			while (temp[i] != -1) {
				try {
					Target.add(new Cell(temp[i],temp[j]));
//					map_target[temp[i]][temp[j]] = 2;
				} catch (IndexOutOfBoundsException e) {  // 예외3. hazard가 지도 범위를 벗어났을 때
					System.out.println("target 좌표가 지도 사이즈를 벗어났습니다.");
					return false;
				}
				i += 2; j += 2;
			}

			System.out.println("모든 값이 적절합니다!!객체를 생성할 수 있어요"); // 객체 생성으로 이어짐
			return true;
		}

	}

	public void Random_Spots() {	
		int n, i, j;
		Random RG = new Random();
	
		n = RG.nextInt(5);
		while((n--)!=0) {
			i = RG.nextInt(MapManager.map_size[0]);
			j = RG.nextInt(MapManager.map_size[1]);
			if(MapManager.map_hazard[i][j]==-1) {
				MapManager.map_hazard[i][j]=1;
			}
		}
		
		n = RG.nextInt(10);	
		while((n--)!=0) {
			i = RG.nextInt(MapManager.map_size[0]);
			j = RG.nextInt(MapManager.map_size[1]);
			if(MapManager.map_hazard[i][j]==-1) {
				MapManager.map_hazard[i][j]=2;
			}
		}
	}
	
	public void Create_Map() {
		this.map = new Map(map_size[0], map_size[1]);
		this.Random_Spots();
		Map.Lacked_Map = (int[][])map_hazard.clone();

	}
	
	
}

//hazard 배열에 hazard랜덤, colorblob랜덤 넣으면 lack_map임
