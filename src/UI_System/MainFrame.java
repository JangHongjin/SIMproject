package UI_System;

import javax.swing.JFrame;

import ADD_ON_System.MapManager;

public class MainFrame extends JFrame {
	public static void main(String[] args) {	
		
		Call_Input_Frame();	// 조건에 맞게 input frame이랑 mapviewr 호출해야할
	}
	
	public static void Call_Input_Frame() {
		InputFrame IF = new InputFrame();
	}
	
	public static void Call_MapViewer() {
		MapViewer MV = new MapViewer();
		
	}
}
