package ADD_ON_System;

public class MapManager {
	private int rows, cols;
	private Map map;
	
	public Map getMap() { return map; }
	public void setMap(Map map) { this.map = map; }
	public int getRows() { return rows; }
	public void setRows(int rows) {	this.rows = rows; }
	public int getCols() { return cols; }
	public void setCols(int cols) { this.cols = cols; }

	public boolean Check_Input() {
		if (this.rows <= 0 || this.cols <= 0) {
			System.out.println("다시 입력해주세요");	// InputFrame 재입력 요구하게 만들어야
			return false;
		}

		else {
			System.out.println("값이 정확합니다"); // 객체 생성으로 이어짐
			return true;
		}
	}

	public void Create_Map() {
		map = new Map(this.getRows(), this.getCols());
	}

	

}
