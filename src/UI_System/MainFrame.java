package UI_System;

public class MainFrame {
	public static void main(String[] args) {
		// 여기서 InputFrame, MapViewer 호출
		Call_Input_Frame();
	}
	
	static void Call_Input_Frame() {
		InputFrame IF = new InputFrame();
	}
	
	static void Call_MapViewer() {
		MapViewer MV = new MapViewer();
		
	}
}