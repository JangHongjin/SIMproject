package UI_System;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MapViewer extends JFrame {
	
	public MapViewer() {
		this.setTitle("Movement of SIM");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(700, 700);	// 틀을 못만들겠다
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setResizable(false);
		
		JPanel view = new JPanel();
		JButton b = new JButton("fdsdsf");
		view.add(b);
		this.add(view);
	}

}
