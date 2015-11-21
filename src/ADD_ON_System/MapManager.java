package ADD_ON_System;

public class MapManager {
	private int rows, cols;
	private Map map;
	
	public int getRows() { return rows; }
	public void setRows(int rows) {	this.rows = rows; }
	public int getCols() { return cols; }
	public void setCols(int cols) { this.cols = cols; }

	public boolean Check_Input() {
		if (this.rows <= 0 || this.cols <= 0) {
			System.out.println("다시 입력해주세요");
			return false;
		}

		else {
			System.out.println("정확한 값을 입력해주세요");
			return true;
		}
	}

	public void Create_Map() {
		map = new Map(this.getRows(), this.getCols());
	}

	

}
