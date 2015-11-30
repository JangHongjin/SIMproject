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

import ADD_ON_System.Map;
import ADD_ON_System.MapManager;

class InputFrame extends JFrame implements ActionListener {
	private MapManager MM = new MapManager();
	
	private JTextField input_position;
	private JTextField input_size;
	private JTextField input_hazard;
	private JTextField input_target;
	private JButton submit;
	
	public InputFrame() {
		this.setTitle("InputFrame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(700, 700);
		this.setLayout(new GridLayout(0,1));
		this.setResizable(false);
		
		JPanel p_size = new JPanel(new FlowLayout(FlowLayout.RIGHT)); this.add(p_size); 
		JLabel l_size = new JLabel("지도 사이즈를 입력해주세요");
		input_size = new HintTextField("(n, n)");
		input_size.setPreferredSize(new Dimension(100,25));
		input_size.setHorizontalAlignment(JTextField.CENTER);
		p_size.add(l_size);
		p_size.add(input_size);
		
		JPanel p_position = new JPanel(new FlowLayout(FlowLayout.RIGHT)); this.add(p_position); 
		JLabel l_position = new JLabel("로봇의 출발 위치를 입력해주세요");
		input_position = new HintTextField("(n, n)");
		input_position.setPreferredSize(new Dimension(100,25));
		input_position.setHorizontalAlignment(JTextField.CENTER);
		p_position.add(l_position);
		p_position.add(input_position);

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
		if (e.getSource() == submit) {	
			MapManager.setInput_size(input_size.getText());
			MapManager.setInput_position(input_position.getText());
			MapManager.setInput_hazard(input_hazard.getText());
			MapManager.setInput_target(input_target.getText());
			
			if (MM.Check_Input()) {	// MapManager에서 체크하고 그 결과가 true면
				MM.Create_Map();
//지도 제대로 찍히나 테스트				
				for(int i=0; i<5; i++) {
					for(int j=0; j<5; j++) {
						System.out.printf("%3d", Map.Lacked_Map[i][j]);
					}
					System.out.println("");
				}
//여기까지
				MainFrame.Call_MapViewer();
			} else {
				MainFrame.Call_Input_Frame();
			}
		}
	}
}

/////////////////////////////////////////////////////////////////////////////////////////
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