package UI_System;


import ADD_ON_System.MapManager;

public class MainFrame {
	static MapViewer mv;
	public static void main(String[] args) {	
		callInputFrame();
	}
	public static void callInputFrame() {
		new InputFrame();
	}
	public static void callInputFrame2(MapManager mm) {
		new InputFrame2(mm);
	}
	public static void callMapViewer(MapManager mm) {
		mv= new MapViewer(mm);
	}
	
	public static void updateMapViewer(MapViewer mv, MapManager mm){
		mv.updateMap(mm);
	}
	
	public static void finishMapViewer(MapViewer mv, MapManager mm){
		mv.finishUpdateMap(mm);
	}
}