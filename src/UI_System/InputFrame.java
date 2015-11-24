package UI_System;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ADD_ON_System.MapManager;

class InputFrame extends JFrame implements ActionListener {
	private MapManager MM = new MapManager();
	private JTextField input_size;
	private JTextField input_hazard;
	private JTextField input_target;
	private JButton submit;
	
	public InputFrame() {
		this.setTitle("InputFrame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(700, 700);	// 틀을 못만들겠다
		this.setLayout(new GridLayout(0,1));
		this.setResizable(false);
		
		JPanel p_size = new JPanel(new FlowLayout(FlowLayout.RIGHT)); this.add(p_size); 
		JLabel l_size = new JLabel("지도 사이즈를 입력해주세요");
		input_size = new HintTextField("(n, n)");
		input_size.setPreferredSize(new Dimension(100,25));
		input_size.setHorizontalAlignment(JTextField.CENTER);
		p_size.add(l_size);
		p_size.add(input_size);

		JPanel p_hazard = new JPanel(new FlowLayout(FlowLayout.RIGHT)); this.add(p_hazard);
		JLabel l_hazard = new JLabel("위험요소 위치를 입력해주세요");
		input_hazard = new HintTextField("(n, n)");
		input_hazard.setPreferredSize(new Dimension(100,25));
		input_hazard.setHorizontalAlignment(JTextField.CENTER);
		p_hazard.add(l_hazard);
		p_hazard.add(input_hazard);
		
		JPanel p_target = new JPanel(new FlowLayout(FlowLayout.RIGHT)); this.add(p_target);
		JLabel l_target = new JLabel("방문지점 위치를 입력해주세요");
		input_target = new HintTextField("(n, n)");
		input_target.setPreferredSize(new Dimension(100,25));
		input_target.setHorizontalAlignment(JTextField.CENTER);
		p_target.add(l_target);
		p_target.add(input_target);
		
		submit = new JButton("확인");
		submit.addActionListener(this);
		
		this.add(submit);

		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {	// 들어온 문자열 (n, n) 파싱해서 형식 걸러내고, 값만 추출해야됨
			
			MapManager.setInput_size(input_size.getText());
			MapManager.setInput_hazard(input_hazard.getText());
			MapManager.setInput_target(input_target.getText());
			
			if (MM.Check_Input()) {
				MM.Create_Map();
				MainFrame.Call_MapViewer();
			} else {
				System.out.println("재입력을 요구");
			}
		}
	}
}

class HintTextField extends JTextField implements FocusListener {
	private final String hint;
	private boolean showingHint;

	public HintTextField(final String hint) {
		super(hint);
		this.hint = hint;
		this.showingHint = true;
		super.addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setText("");
			showingHint = false;
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setText(hint);
			showingHint = true;
		}
	}

	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
}
