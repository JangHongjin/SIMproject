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
	
	
	private JTextField input_size;

	private JButton submit;
	
	public InputFrame() {
		this.setTitle("InputFrame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(700, 700);
		this.setLayout(new GridLayout(0,1));
		this.setResizable(false);
		this.setLocationRelativeTo(null); // 프레임 위치를 가운데로
		
		
		
		
		JPanel p_size = new JPanel(new FlowLayout(FlowLayout.RIGHT)); this.add(p_size, "center"); 
		JLabel l_size = new JLabel("지도 사이즈를 입력해주세요");
		input_size = new HintTextField("(n, n)");
		input_size.setPreferredSize(new Dimension(100,25));
		input_size.setHorizontalAlignment(JTextField.CENTER);
		p_size.add(l_size);
		p_size.add(input_size);

		submit = new JButton("확인");
		submit.addActionListener(this);
		
		this.add(submit, "center");

		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {	
			MapManager.setInput_size(input_size.getText());
		}
			MapManager mm = new MapManager();
			if (mm.checkInput()) {	// MapManager에서 체크하고 그 결과가 true면
				mm.createMap();

				MainFrame.callInputFrame2(mm);
				dispose();
			} else {
				MainFrame.callInputFrame();

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