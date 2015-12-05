package UI_System;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ADD_ON_System.AddOn;
import ADD_ON_System.Cell;
import ADD_ON_System.MapManager;
import SIM_System.SIM; 
@SuppressWarnings("serial") 
public class InputFrame2 extends JFrame { 
	
	
	
   final int NONE = 0;
   final int HAZARD = 1;
   final int TARGET = 2;
   final int START = 3;
   
   public static int LINE_NUM_ROWS ; // ��ü �� �� ��   // ���� ĭ��
   public static int LINE_NUM_COLS;               // ���� ĭ�� 
   int LINE_WIDTH; // �� ���� 
   int BOARD_SIZE; // ���� ũ�� 
   int BOARD_SIZE_HEIGHT;
   
   
   int IMG_SIZE = 40; //�̹��� ������ ����
   int X0; // ������ġ x��ǥ 
   int Y0; // ������ġ y��ǥ 
   int FRAME_WIDTH; // Frame�� �� 
   int FRAME_HEIGHT; // Frame�� ���� 
   int turn = NONE;

   static ArrayList<Cell> hazard = new ArrayList<Cell>();
   static ArrayList<Cell> target = new ArrayList<Cell>();
   static Cell start = new Cell(-1, -1);
   
   int map_Info[][]; // ������  //�̰� Map��ü�� �Ѱ������ 
   Image img = null; 
   Graphics gImg = null; 
   JFrame f; 
   JPanel pnl_menu; 
   JButton btn_start; 
   JButton btn_haz;
   JButton btn_target;
   JButton btn_finish;
   JButton btn_clear;
   MapManager mm;
  
   
   class t1 extends Thread{
	   MapManager mm;
	   t1(MapManager mm){
		   this.mm=mm;
	   }
	   public void run(){
		   new AddOn(MainFrame.mv,mm);
	   }
   }
   
   public InputFrame2(MapManager mm) { 
	   this.mm=mm;
	   LINE_NUM_ROWS=mm.getMap_size()[0];
	   LINE_NUM_COLS=mm.getMap_size()[1];
	   
	   map_Info = new int[LINE_NUM_ROWS][LINE_NUM_COLS]; // ������ //�̰� Map��ü�� �Ѱ������ 
	   System.out.println(LINE_NUM_ROWS + "," + LINE_NUM_COLS);
	   LINE_WIDTH = 40; // �� ���� 
	   BOARD_SIZE = LINE_WIDTH * (LINE_NUM_COLS - 1); // ���� ũ�� 
	   BOARD_SIZE_HEIGHT = LINE_WIDTH * (LINE_NUM_ROWS - 1);
	   
	   f = this; 
	   setLayout(new BorderLayout()); 
	   setTitle("TestMap2"); 
   		add(new MyPanel(), "Center"); 
   		pnl_menu = new JPanel(); 
   		// ��ư�� �߰� 
   		btn_clear = new JButton("�ʱ�ȭ"); 
   		pnl_menu.add(btn_clear); 
   		btn_start = new JButton("���� ����");
   		pnl_menu.add(btn_start); 
   		btn_haz = new JButton("Add Hazard"); 
   		pnl_menu.add(btn_haz); 
   		btn_target = new JButton("Add Target"); 
   		pnl_menu.add(btn_target); 
   		btn_finish = new JButton("Add Finish");
   		pnl_menu.add(btn_finish);
      
   		btn_start.addActionListener(new MyListener()); 
   		btn_clear.addActionListener(new MyListener());
   		btn_haz.addActionListener(new MyListener());
   		btn_target.addActionListener(new MyListener());
   		btn_finish.addActionListener(new MyListener());
      
   		add(pnl_menu, "South"); 
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      	setVisible(true); 
      	for (int i = 0; i < LINE_NUM_ROWS; i++) { 
      		for (int j = 0; j < LINE_NUM_COLS; j++) { 
      			map_Info[i][j] = NONE; 
      		} 
      	}
	     Insets insets = getInsets(); 
	     X0 = LINE_WIDTH; 
	     Y0 = LINE_WIDTH; 

      
	     // Frame�� ũ�⸦ ����Ѵ�. 
	     FRAME_WIDTH = BOARD_SIZE + LINE_WIDTH * 2 + insets.left + insets.right; 
	     FRAME_HEIGHT = BOARD_SIZE_HEIGHT + LINE_WIDTH * 2 + pnl_menu.getSize().height 
	    		 + insets.top + insets.bottom; 
	     // insets�� visible �Ŀ�! 
	     setFrameCenter(this, FRAME_WIDTH, FRAME_HEIGHT); 
      
 
	     
   	} 
   static void setFrameCenter(JFrame f, int w, int h) { 
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); 
      int xpos = (int) (screen.getWidth() / 2 - w / 2); 
      int ypos = (int) (screen.getHeight() / 2 - h / 2); 
      f.setBounds(xpos, ypos, w, h); 
   } 
   public class MyListener implements ActionListener { 
      @Override 
      public void actionPerformed(ActionEvent e) { 
         if (e.getSource() == btn_clear) { 
            // �ʱ�ȭ�ϴ� ����� 
            hazard.clear(); 
            target.clear();
            start.x = -1;
            start.y = -1;
           
            for (int i = 0; i < LINE_NUM_ROWS; i++) { 
               for (int j = 0; j < LINE_NUM_COLS; j++) { 
                  map_Info[i][j] = NONE; 
               } 
            } 
            turn = NONE; 
            repaint(); 
            } else if (e.getSource() == btn_haz) {
            	turn = HAZARD;
            } else if (e.getSource() == btn_target) { 
            	turn = TARGET;
            } else if (e.getSource() == btn_finish){
            	mm.initializeMap(map_Info);

         	    MainFrame.callMapViewer(mm);
         		
            	dispose();
            	t1 t=new t1(mm);
            	t.start();
            	
            	
            	 			// ���� ������ �ݱ�
            } else if (e.getSource() == btn_start){
            	turn = START;
         } 
      } 
   } 
   public class MyPanel extends JPanel { 
      public MyPanel() { 
         addMouseListener(new MyListener()); 
      } 
      public void paintComponent(Graphics g) { 
    	  // ����� �̹��� �ҷ����� 
    	  Image hazard_img = Toolkit.getDefaultToolkit().getImage("bomb.png");
    	  Image start_img = Toolkit.getDefaultToolkit().getImage("start.png");
    	  Image target_img = Toolkit.getDefaultToolkit().getImage("flag.png");
    	  Image backMap_img = Toolkit.getDefaultToolkit().getImage("backMap2.jpg");

    	 // ���� �׷��� ���� ũ�� ���� 
         Image img = createImage(BOARD_SIZE + LINE_WIDTH * 2, BOARD_SIZE_HEIGHT + LINE_WIDTH * 2); 
         Graphics bg = img.getGraphics(); 
         
          
         // ��� ���� ����
         // backMap �̹����� 0,0 ���� �������� �°� ���ȴ�� ǥ��
         bg.drawImage(backMap_img, 0, 0, BOARD_SIZE + LINE_WIDTH * 2, BOARD_SIZE_HEIGHT 
         + LINE_WIDTH * 2, this);
         
         
         
         bg.setColor(Color.white); 
         for (int i = 0; i < LINE_NUM_ROWS; i++) { // ���� ���� ���� ����
            bg.drawLine(X0, Y0 + i * LINE_WIDTH, X0 + BOARD_SIZE, Y0 + i 
            * LINE_WIDTH); //(x������, y������, x�� ����ġ (���� ���� ���� �������), y�� ����ġ)
            //bg.drawLine(X0 + i * LINE_WIDTH, Y0, X0 + i * LINE_WIDTH, Y0 
            //+ BOARD_SIZE); 
         }
         
         for (int i = 0; i < LINE_NUM_COLS; i++) {  
              //���� ���� ������� ���� ���� 
             bg.drawLine(X0 + i * LINE_WIDTH, Y0, X0 + i * LINE_WIDTH, Y0 
             + BOARD_SIZE_HEIGHT); //(x������, y������, x�� ����ġ, y�� ����ġ(���� ���� ���� �������))
         }

         
         
         for(int j=0; j<hazard.size(); j++){
        	 bg.drawImage(hazard_img, hazard.get(j).x, hazard.get(j).y, this);
         }
         for(int k=0; k<target.size(); k++){
        	 bg.drawImage(target_img, target.get(k).x, target.get(k).y, this);
         }
         if(start.x > 0)
        	 bg.drawImage(start_img, start.x, start.y, this);
    	
         
         g.drawImage(img, 0, 0, this); 
         
      } /////////////////////////////////////������� ���׸��� 
      public class MyListener implements MouseListener { 
         public void mousePressed(MouseEvent e) { 
            int x = e.getX(); // ���콺 �������� x��ǥ 
            int y = e.getY(); // ���콺 �������� y��ǥ 
            int pX, pY;  // ������ �״�� ū ������ x, y ��ǥ�� ����Ͽ� 0, 1, 2.. ���� ���⿡ �´� ��ǥ�� ���� 
            // 1. x �Ǵ� y�� ���� ���� ���� ��� ���̸� �޼��带 �����Ѵ�. 
        	System.out.println(map_Info.length);
        	System.out.println(map_Info[0].length);
            if (x < X0 - LINE_WIDTH / 2 
            || x > X0 + (LINE_NUM_COLS - 1) * LINE_WIDTH + LINE_WIDTH 
            / 2) 
            return; 
            if (y < Y0 - LINE_WIDTH / 2 
            || y > Y0 + (LINE_NUM_ROWS - 1) * LINE_WIDTH + LINE_WIDTH 
            / 2) 
            return; 
            // 2. x�� y�� ���� Ŭ���� ������ ���� ����� ���������� �����Ѵ�.(�ݿø�) 
            x = (x - X0 + LINE_WIDTH / 2) / LINE_WIDTH * LINE_WIDTH + X0; 
            y = (y - Y0 + LINE_WIDTH / 2) / LINE_WIDTH * LINE_WIDTH + Y0; 
            pX = x / 40 - 1; 
            pY = y / 40 - 1; 
            // 3. x�� y�� ������ �̹����� ũ��(IMG_SIZE)�� ������ ���� Ŭ���κ��� �߽����� �̹����� �׷�����. 
            x -= IMG_SIZE / 2; 
            y -= IMG_SIZE / 2; 
            // 4. ������ ��ư�� ���콺 ���� ��ư�̸� �̹����� �׸��� 
            
            if (map_Info[pY][pX] == NONE) { 

            	if(turn == HAZARD){
            		// HAZARD ���õ� �����̸� hazard �׸���
            		hazard.add(new Cell(x, y));
            		map_Info[pY][pX] = HAZARD;
            		System.out.println("Hazard : " + pX + "," + pY);
            		// �̺κп� �迭�� �߰� 
            	}
            	if(turn == TARGET){
            		// POINT ���õ� �����̸� point �׸���
            		target.add(new Cell(x, y));
            		map_Info[pY][pX] = TARGET;
            		System.out.println("Point : " + pX + "," + pY);
            	}
            	
            	if(turn == START){
            		// START ���õ� �����̸� point �׸���
            		if(start.x > 0){  // �̶� ���� �׷��� start�� ������ (��ǥ��-20)/40 �Ͽ� �ش� �κ� NONE���� 
            			map_Info[(start.y-20)/40][(start.x-20)/40] = NONE;
            		}
            		start=new Cell(x, y);
            		map_Info[pY][pX] = START;
            		System.out.println("Point : " + pX + "," + pY);
            	}
            	
         
            } 
            
            // 5. repaint()�� ȣ���Ѵ�. 
            repaint(); 
             
         } 
         
         public void mouseReleased(MouseEvent e) { 
         } 
         public void mouseClicked(MouseEvent e) { 
         } 
         public void mouseEntered(MouseEvent e) { 
         } 
         public void mouseExited(MouseEvent e) { 
         } 
      } 
   } 
} 

