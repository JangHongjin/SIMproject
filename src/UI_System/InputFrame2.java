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
   
   public static int LINE_NUM_ROWS ; // 전체 맵 줄 수   // 세로 칸수
   public static int LINE_NUM_COLS;               // 가로 칸수 
   int LINE_WIDTH; // 줄 간격 
   int BOARD_SIZE; // 맵의 크기 
   int BOARD_SIZE_HEIGHT;
   
   
   int IMG_SIZE = 40; //이미지 사이즈 지정
   int X0; // 시작위치 x좌표 
   int Y0; // 시작위치 y좌표 
   int FRAME_WIDTH; // Frame의 폭 
   int FRAME_HEIGHT; // Frame의 높이 
   int turn = NONE;

   static ArrayList<Cell> hazard = new ArrayList<Cell>();
   static ArrayList<Cell> target = new ArrayList<Cell>();
   static Cell start = new Cell(-1, -1);
   
   int map_Info[][]; // 맵정보  //이걸 Map객체에 넘겨줘야함 
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
	   
	   map_Info = new int[LINE_NUM_ROWS][LINE_NUM_COLS]; // 맵정보 //이걸 Map객체에 넘겨줘야함 
	   System.out.println(LINE_NUM_ROWS + "," + LINE_NUM_COLS);
	   LINE_WIDTH = 40; // 줄 간격 
	   BOARD_SIZE = LINE_WIDTH * (LINE_NUM_COLS - 1); // 맵의 크기 
	   BOARD_SIZE_HEIGHT = LINE_WIDTH * (LINE_NUM_ROWS - 1);
	   
	   f = this; 
	   setLayout(new BorderLayout()); 
	   setTitle("TestMap2"); 
   		add(new MyPanel(), "Center"); 
   		pnl_menu = new JPanel(); 
   		// 버튼들 추가 
   		btn_clear = new JButton("초기화"); 
   		pnl_menu.add(btn_clear); 
   		btn_start = new JButton("시작 지점");
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

      
	     // Frame의 크기를 계산한다. 
	     FRAME_WIDTH = BOARD_SIZE + LINE_WIDTH * 2 + insets.left + insets.right; 
	     FRAME_HEIGHT = BOARD_SIZE_HEIGHT + LINE_WIDTH * 2 + pnl_menu.getSize().height 
	    		 + insets.top + insets.bottom; 
	     // insets는 visible 후에! 
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
            // 초기화하는 내용들 
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
            	
            	
            	 			// 현재 프레임 닫기
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
    	  // 사용할 이미지 불러오기 
    	  Image hazard_img = Toolkit.getDefaultToolkit().getImage("bomb.png");
    	  Image start_img = Toolkit.getDefaultToolkit().getImage("start.png");
    	  Image target_img = Toolkit.getDefaultToolkit().getImage("flag.png");
    	  Image backMap_img = Toolkit.getDefaultToolkit().getImage("backMap2.jpg");

    	 // 라인 그려질 보드 크기 결정 
         Image img = createImage(BOARD_SIZE + LINE_WIDTH * 2, BOARD_SIZE_HEIGHT + LINE_WIDTH * 2); 
         Graphics bg = img.getGraphics(); 
         
          
         // 배경 보드 설정
         // backMap 이미지를 0,0 부터 보드사이즈에 맞게 계산된대로 표시
         bg.drawImage(backMap_img, 0, 0, BOARD_SIZE + LINE_WIDTH * 2, BOARD_SIZE_HEIGHT 
         + LINE_WIDTH * 2, this);
         
         
         
         bg.setColor(Color.white); 
         for (int i = 0; i < LINE_NUM_ROWS; i++) { // 가로 라인 개수 결정
            bg.drawLine(X0, Y0 + i * LINE_WIDTH, X0 + BOARD_SIZE, Y0 + i 
            * LINE_WIDTH); //(x시작점, y시작점, x축 끝위치 (가로 라인 길이 여기수정), y축 끝위치)
            //bg.drawLine(X0 + i * LINE_WIDTH, Y0, X0 + i * LINE_WIDTH, Y0 
            //+ BOARD_SIZE); 
         }
         
         for (int i = 0; i < LINE_NUM_COLS; i++) {  
              //세로 라인 뻗어나가는 길이 결정 
             bg.drawLine(X0 + i * LINE_WIDTH, Y0, X0 + i * LINE_WIDTH, Y0 
             + BOARD_SIZE_HEIGHT); //(x시작점, y시작점, x축 끝위치, y축 끝위치(세로 라인 길이 여기수정))
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
         
      } /////////////////////////////////////여기까지 배경그리기 
      public class MyListener implements MouseListener { 
         public void mousePressed(MouseEvent e) { 
            int x = e.getX(); // 마우스 포인터의 x좌표 
            int y = e.getY(); // 마우스 포인터의 y좌표 
            int pX, pY;  // 포인터 그대로 큰 숫자인 x, y 좌표를 계산하여 0, 1, 2.. 같이 보기에 맞는 좌표로 변경 
            // 1. x 또는 y의 값이 판의 밖을 벗어난 곳이면 메서드를 종료한다. 
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
            // 2. x와 y의 값을 클릭한 곳에서 가장 가까운 교차점으로 변경한다.(반올림) 
            x = (x - X0 + LINE_WIDTH / 2) / LINE_WIDTH * LINE_WIDTH + X0; 
            y = (y - Y0 + LINE_WIDTH / 2) / LINE_WIDTH * LINE_WIDTH + Y0; 
            pX = x / 40 - 1; 
            pY = y / 40 - 1; 
            // 3. x와 y의 값에서 이미지의 크기(IMG_SIZE)의 절반을 빼야 클릭부분을 중심으로 이미지가 그려진다. 
            x -= IMG_SIZE / 2; 
            y -= IMG_SIZE / 2; 
            // 4. 눌러진 버튼이 마우스 왼쪽 버튼이면 이미지를 그린다 
            
            if (map_Info[pY][pX] == NONE) { 

            	if(turn == HAZARD){
            		// HAZARD 선택된 상태이면 hazard 그리고
            		hazard.add(new Cell(x, y));
            		map_Info[pY][pX] = HAZARD;
            		System.out.println("Hazard : " + pX + "," + pY);
            		// 이부분에 배열값 추가 
            	}
            	if(turn == TARGET){
            		// POINT 선택된 상태이면 point 그리고
            		target.add(new Cell(x, y));
            		map_Info[pY][pX] = TARGET;
            		System.out.println("Point : " + pX + "," + pY);
            	}
            	
            	if(turn == START){
            		// START 선택된 상태이면 point 그린다
            		if(start.x > 0){  // 이때 전에 그려진 start가 있으면 (좌표값-20)/40 하여 해당 부분 NONE으로 
            			map_Info[(start.y-20)/40][(start.x-20)/40] = NONE;
            		}
            		start=new Cell(x, y);
            		map_Info[pY][pX] = START;
            		System.out.println("Point : " + pX + "," + pY);
            	}
            	
         
            } 
            
            // 5. repaint()를 호출한다. 
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

