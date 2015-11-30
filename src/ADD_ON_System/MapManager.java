package ADD_ON_System;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;

public class MapManager {

// UI���� String�������� �޾ƿ� ����
	private static String input_position = new String();
	private static String input_size = new String();
	private static String input_hazard = new String();
	private static String input_target = new String();

// UI���� �޾ƿ� ������ Integer�� ��ȯ���� ������ ����
	private static int[] map_position = new int[2];
	private static int[] map_size = new int[2];
	private static int[][] map_hazard;
//	private static int[][] map_target;	//��� �迭�� �� �ʿ� ����. ArrayList�� ���� ����
	private static int[] temp;
	
	private static ArrayList<Cell> Target = new ArrayList<Cell>();	// SIM�� �� ã�� �� �� ����
	
	private Map map;

	public static void setInput_position(String map_position) {	MapManager.input_position = map_position; }
	public static void setInput_size(String input) { MapManager.input_size = input; }
	public static void setInput_hazard(String input) { MapManager.input_hazard = input; }
	public static void setInput_target(String input) { MapManager.input_target = input; }

// InputFrame���� ���� ���� üũ�ϴ� �Լ�	
	public boolean Check_Input() {
		int i, j;
		
		StringTokenizer st_position = new StringTokenizer(input_position, " (,)");
		StringTokenizer st_size = new StringTokenizer(input_size, " (,)");
		StringTokenizer st_hazard = new StringTokenizer(input_hazard, " (,)");
		StringTokenizer st_target = new StringTokenizer(input_target, " (,)");
		
// ���� ������ ����
		while (st_size.hasMoreTokens()) {
			try {
				map_size[0] = Integer.parseInt(st_size.nextToken());
				map_size[1] = Integer.parseInt(st_size.nextToken());
			}
			catch (NoSuchElementException e) {	//x, y ��ǥ �� �ϳ� ������ �� ���� ó��
				System.out.println("���� �Է��� �� ����?");
				return false;
			}
		}
		
		if (map_size[0] <= 0 || map_size[1] <= 0) {	// ���� ��ǥ ���� ������ �� ����ó��
			System.out.println("���� ��ǥ���� ������");
			return false;
		}
		
// ������ ��ҵ鵵 �ʱ�ȭ�� ����
		else {
			map_hazard = new int[map_size[0]][map_size[1]]; // ������ ���� ũ���� �迭�� ����
//			map_target = new int[map_size[0]][map_size[1]];
			temp = new int[map_size[0] * map_size[1]]; // hazard, target ��ǥ�� �޾ƿ��� ���� ��ġ�� ��

// �������� = �ʱⰪ : -1 / hazard : 1 / colorblob : 2 / robot : 3
			for (i = 0; i < map_size[0]; i++) {
				for (j = 0; j < map_size[1]; j++) {
					map_hazard[i][j] = -1;
//					map_target[i][j] = -1;
				}
			}
			
// Position �ʱ�ȭ			
			while (st_position.hasMoreTokens()) {
				try {
					map_position[0] = Integer.parseInt(st_position.nextToken());
					map_position[1] = Integer.parseInt(st_position.nextToken());
				}
				catch (NoSuchElementException e) {	//x, y ��ǥ �� �ϳ� ������ �� ���� ó��
					System.out.println("���� �Է��� �� ����?");
					return false;
				}
			}
			
			if (map_position[0] < 0 || map_position[1] < 0) {	// ���� ��ǥ ���� ������ �� ����ó��
				System.out.println("�κ� ��ġ���� �����Դϴ�");
				return false;
			}
			
			if (map_position[0] >= map_size[0] || map_position[1] >= map_size[1]) {
				System.out.println("�κ� ��ġ ��ǥ�� ���� ����� ������ϴ�.");
				return false;
			}

// HAZARD ���� �Է�
			for (i = 0; i < (map_size[0] * map_size[1]); i++)
				temp[i] = -1;
			
			i = 0; j = 1; // hazard ������ �ϴ� temp�� �ű�
			while (st_hazard.hasMoreTokens()) {
				try {
					temp[i] = Integer.parseInt(st_hazard.nextToken()); //��
					temp[j] = Integer.parseInt(st_hazard.nextToken()); //��
				} catch (NoSuchElementException e) {	// ����1. x, y�� �ϳ� ������ �� ����ó��
					System.out.println("hazard �Է� �� ¦�� �ȸ�����");
					return false;
				}
				i += 2; j += 2;
			}

			if (temp[0] == -1 || temp[j - 2] == -1) { // ����2. �� �Է���ü�� �ȵ��� �� ����ó��
				System.out.println("hazard���� �Էµ��� �ʾҽ��ϴ�.");
				return false;
			}

			i = 0; j = 1;	// ��¥ target�迭�� ���� ���� 
			while (temp[i] != -1) {
				try {
					map_hazard[temp[i]][temp[j]] = 1;
				} catch (IndexOutOfBoundsException e) { // ����3. hazard�� ���� ������ ����� ��
					System.out.println("hazard ��ǥ�� ���� ����� ������ϴ�.");
					return false;
				}
				i += 2; j += 2;
			}

// TARGET���� �Է�
			for (i = 0; i < (map_size[0] * map_size[1]); i++)
				temp[i] = -1;

			i = 0; j = 1;	// target ������ �ϴ� temp�� �ű�
			while (st_target.hasMoreTokens()) {
				try {
				temp[i] = Integer.parseInt(st_target.nextToken());
				temp[j] = Integer.parseInt(st_target.nextToken());
				} catch (NoSuchElementException e) {	// ����1. x, y�� �ϳ� ������ �� ����ó��
					System.out.println("target �Է� �� ¦�� �ȸ�����");
					return false;
				}
				i += 2; j += 2;
			}

			if (temp[0] == -1 || temp[j - 2] == -1) { // ����2. �� �Է���ü�� �ȵ��� �� ����ó��
				System.out.println("target���� �Էµ��� �ʾҽ��ϴ�.");
				return false;
			}

			i = 0; j = 1;	// ��¥ target�迭�� ���� ���� 
			while (temp[i] != -1) {
				try {
					Target.add(new Cell(temp[i],temp[j]));
//					map_target[temp[i]][temp[j]] = 2;
				} catch (IndexOutOfBoundsException e) {  // ����3. hazard�� ���� ������ ����� ��
					System.out.println("target ��ǥ�� ���� ����� ������ϴ�.");
					return false;
				}
				i += 2; j += 2;
			}

			System.out.println("��� ���� �����մϴ�!!��ü�� ������ �� �־��"); // ��ü �������� �̾���
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