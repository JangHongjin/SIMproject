package UI_System;

import java.util.ArrayList;

import javax.swing.JFrame;

import ADD_ON_System.ADD_ON;
import ADD_ON_System.Cell;
import ADD_ON_System.MapManager;

public class MainFrame extends JFrame {
	public static void main(String[] args) {	
    	int lacked_Map[][]=	   {{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,1,0,0,0},
				{0,0,0,0,1,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,1,0},	
				{0,0,1,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,1,0,0,0},
				{0,0,0,1,0,0,0,0,0,0},
				{0,1,0,1,0,0,0,0,1,0},
				{0,0,0,0,0,0,1,0,0,0}};
ArrayList<Cell> robotPath=new ArrayList<Cell>();
ArrayList<Cell> target=new ArrayList<Cell>();
robotPath.add(new Cell(2,2));
robotPath.add(new Cell(2,1));
robotPath.add(new Cell(3,1));
robotPath.add(new Cell(4,1));
robotPath.add(new Cell(5,1));
robotPath.add(new Cell(6,1));

ADD_ON ao=new ADD_ON();

target.add(new Cell(2,2));
target.add(new Cell(6,2));
target.add(new Cell(7,1));


	System.out.println(ao.searchPath(lacked_Map,robotPath, target));


		Call_Input_Frame();	// 조건에 맞게 input frame이랑 mapviewr 호출해야할
	}
	
	

	public static void Call_Input_Frame() {
		InputFrame IF = new InputFrame();
	}
	
	public static void Call_MapViewer() {
		MapViewer MV = new MapViewer();
		
	}
}