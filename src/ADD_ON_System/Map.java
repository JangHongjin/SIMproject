package ADD_ON_System;

import java.util.ArrayList;

public class Map {
	//�Է¹��� start, hazard, target ������ lacked_Map�� ��� 
	//0:���� 1:hazard 2:colorblob
	//real_Map�� �����ϰ� hazard�� colorblob�� �߰�
	int[][] lacked_Map;
	int[][] real_Map;
	ArrayList<Cell> robot_Path=new ArrayList<Cell>();
	ArrayList<Cell> target=new ArrayList<Cell>();

	public Map(int n, int m) {
		lacked_Map = new int[n][m];
		real_Map = new int[n][m];
		System.out.println("Lacked_Map�� �ʱ�ȭ �Ǿ����ϴ�.");

	}
	
	
}