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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ADD_ON_System.Cell;
import ADD_ON_System.MapManager;
import SIM_System.SIM; 
@SuppressWarnings("serial") 
public class MapViewer extends JFrame { 
	int K = 40;  // 한칸(40픽셀)을 40번에 나눠서 이동 하기 위한 변수 
	int Xx, Yy;         // 로봇 draw에 사용할 임시 x, y좌표 
	
   final int NONE = 0; 
   final int BLACK = 1; 
   final int WHITE = 2; 
   int LINE_NUM_ROWS = InputFrame2.LINE_NUM_ROWS;//15; // 전체 맵 줄 수   // 세로 칸수
   int LINE_NUM_COLS = InputFrame2.LINE_NUM_COLS;//10;               // 가로 칸수 
   int LINE_WIDTH = 40; // 줄 간격 
   int BOARD_SIZE = LINE_WIDTH * (LINE_NUM_COLS - 1); // 맵의 가로 계산
   int BOARD_SIZE_HEIGHT = LINE_WIDTH * (LINE_NUM_ROWS - 1); // 맵의 높이 계산
   
   int STONE_SIZE = (int) (LINE_WIDTH * 0.8); // 돌의 크기 
   int X0; // 시작위치 x좌표 
   int Y0; // 시작위치 y좌표 
   int FRAME_WIDTH; // Frame의 폭 
   int FRAME_HEIGHT; // Frame의 높이 
   int turn = WHITE;
   MapManager mm;
   // 이건 여기선언해두긴 했는데 r_path가 로봇 경로그릴때 쓰이는거라 경로정보를 받아와야 하는데
   // 로봇 경로 저장해둘 리스트 Cell에 저장하던지 해야할듯 
 
   int map_Info[][] = new int[LINE_NUM_ROWS][LINE_NUM_COLS]; 
   Image img = null; 
   Graphics gImg = null; 
   JFrame f; 
   JPanel total=new JPanel();
   JPanel pnl_menu; 
   JButton btn_restart; 
   JButton btn_finish;
   JButton btn_save; 
   JButton btn_load;
   
   
   public MapViewer(MapManager mm) { 
      f = this; 
      this.mm=mm;
      setLayout(new BorderLayout()); 
      setTitle("TestMap"); 
      add(total,"Center");
      total.setLayout(new BorderLayout());
      total.add(new MyPanel(), "Center"); 
      pnl_menu = new JPanel(); 
      // 버튼들 추가 
      
      total.add(pnl_menu, "South"); 
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
   public void updateMap(MapManager mm){
	   
	   f = this; 
	      this.mm=mm;
	      setLayout(new BorderLayout()); 
	      setTitle("TestMap"); 
	      total.setLayout(new BorderLayout());
	      add(total);
	      total.add(new MyPanel(), "Center"); 
	      pnl_menu = new JPanel(); 
	      
	      total.add(pnl_menu, "South"); 
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
	      revalidate();
	      repaint();
	      setVisible(true);	      
	      while(true){   		/// add on 에서 1000 스레드 돌 동안 40번에 나눠서 K값 감소 (아래의 MyPanel에서 사용)
		      try {				
	  			Thread.sleep(30);			//  25ms마다 repaint하여 MyPanel 실행 
	  			if(K==0){	
	  				K=40;
	  				break;
	  				
	  			}
	  			else{
	  				K--;
	  				repaint();
	  			}
	  			
	  			
	  			
		  		}catch(InterruptedException e){
		  			System.out.println(e.getMessage()); //sleep InterruptedException 
		  		}
	      }
	     
   }
public void finishUpdateMap(MapManager mm){
	   
	   f = this; 
	      this.mm=mm;
	      setLayout(new BorderLayout()); 
	      setTitle("TestMap"); 
	      total.setLayout(new BorderLayout());
	      add(total);
	      total.add(new MyPanel(), "Center"); 
	      pnl_menu = new JPanel(); 
	      // 버튼들 추가 
	      btn_restart = new JButton("재시작");
	      pnl_menu.add(btn_restart); 
	      //재시작 버튼 사용시 건드리면 되는 부분 
	      btn_restart.addActionListener(new MyListener()); 
	      
	      
	      total.add(pnl_menu, "South"); 
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
	      revalidate();
	      repaint();	
	      setVisible(true);	      
	      K=-1;
	      JOptionPane.showMessageDialog(null, "탐색완료!!");
   }
   static void setFrameCenter(JFrame f, int w, int h) { 
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); 
      int xpos = (int) (screen.getWidth() / 2 - w / 2); 
      int ypos = (int) (screen.getHeight() / 2 - h / 2); 
      f.setBounds(xpos, ypos, w, h); 
   } 
   // 버튼 눌렸을 때 액션 
   public class MyListener implements ActionListener { 
      @Override 
      public void actionPerformed(ActionEvent e) { 
         if (e.getSource() == btn_restart) { 
            // 재시작 버튼 누르면 초기화하는 내용들 
            
            InputFrame2.hazard.clear();
            InputFrame2.target.clear();
            InputFrame2.start = new Cell(-1, -1);
            
            for (int i = 0; i < LINE_NUM_ROWS; i++) { 
               for (int j = 0; j < LINE_NUM_COLS; j++) { 
                  map_Info[i][j] = NONE; 
               } 
            } 
            
            

            MainFrame.callInputFrame();
            dispose();
         } 
      } 
   } 
  public class MyPanel extends JPanel { 
      public void paintComponent(Graphics g) { 
    	 
    	 
    	  // 사용할 이미지 불러오기 
    	  Image robot_img = Toolkit.getDefaultToolkit().getImage("robot2.png");
    	  Image hazard_img = Toolkit.getDefaultToolkit().getImage("bomb.png");
    	  Image colorblob_img = Toolkit.getDefaultToolkit().getImage("colorblob.png");
    	  Image backMap_img = Toolkit.getDefaultToolkit().getImage("backMap2.jpg");
    	  Image target_img = Toolkit.getDefaultToolkit().getImage("flag.png");
    	  Image start_img = Toolkit.getDefaultToolkit().getImage("start.png");
    	 // 라인 그려질 보드 크기 결정 
         Image img = createImage(BOARD_SIZE + LINE_WIDTH * 2, BOARD_SIZE_HEIGHT 
         + LINE_WIDTH * 2); 
         Graphics bg = img.getGraphics(); 
         
         // 배경 보드 설정
         // backMap 이미지를 0,0 부터 보드사이즈에 맞게 계산된대로 표시
         bg.drawImage(backMap_img, 0, 0, BOARD_SIZE + LINE_WIDTH * 2, BOARD_SIZE_HEIGHT 
         + LINE_WIDTH * 2, this);
         
         
         bg.setColor(Color.white); //라인 색 설정
         for (int i = 0; i < LINE_NUM_ROWS; i++) { // 가로 라인 개수 결정
            bg.drawLine(X0, Y0 + i * LINE_WIDTH, X0 + BOARD_SIZE, Y0 + i 
            * LINE_WIDTH); //(x시작점, y시작점, x축 끝위치 (가로 라인 길이 여기수정), y축 끝위치)
         }
         
         for (int i = 0; i < LINE_NUM_COLS; i++) {  // 세로 라인 개수 결정
              //세로 라인 뻗어나가는 길이 결정 
             bg.drawLine(X0 + i * LINE_WIDTH, Y0, X0 + i * LINE_WIDTH, Y0 
             + BOARD_SIZE_HEIGHT); //(x시작점, y시작점, x축 끝위치, y축 끝위치(세로 라인 길이 여기수정))
         }

         //start 지점에 robot,start표시하기
         if(mm.getRobotPath().get(0).x >= 0){
        	 bg.drawImage(start_img, mm.getRobotPath().get(0).y*40+20, mm.getRobotPath().get(0).x*40+20,this);
        	  	 
         }
         
         
         // hazard에 저장된 좌표값에 haz이미지 그리기 
         for(int j=0; j<mm.getLacked_map().length; j++){
        	 for(int k=0; k<mm.getLacked_map()[0].length; k++){
        		 if(mm.getLacked_map()[j][k]==1){
        			 bg.drawImage(hazard_img, 40*k+20, 40*j+20, this);
        		 }
        		 if(mm.getLacked_map()[j][k]==2){
        			 bg.drawImage(colorblob_img, 40*k+20, 40*j+20, this);
        		 }
        	 }
        }
         // target에 저장된 좌표값에 target이미지 그리기 
        
         for(int j=0; j<mm.getTarget().size(); j++){
        	 bg.drawImage(target_img, mm.getTarget().get(j).y*40+20, mm.getTarget().get(j).x*40+20, this);
         }
        
         // r_path에 저장된 로봇 경로 리스트에 따라 path 그리기
         if(mm.getRobotPath().size()>1){  // 걍 라인 두껍게 (세번 그림)
        	 ArrayList<Cell> path=mm.getRobotPath();
	         for(int i=0; i<path.size()-2; i++){
	        	 bg.setColor(Color.GREEN); 
	        	 bg.drawLine(path.get(i).y*40+40,path.get(i).x*40+40, path.get(i+1).y*40+40,path.get(i+1).x*40+40); 
	        	 bg.drawLine(path.get(i).y*40+39,path.get(i).x*40+39, path.get(i+1).y*40+39,path.get(i+1).x*40+39); 
	        	 bg.drawLine(path.get(i).y*40+41,path.get(i).x*40+41, path.get(i+1).y*40+41,path.get(i+1).x*40+41); 
	         }
	         //이부분에 로봇과 함께 이동경로를 그려주는 애니메이션효과 들어가야함

	         int x1=path.get(path.size()-2).y;
	         int y1=path.get(path.size()-2).x;
	         int x2=path.get(path.size()-1).y;
	         int y2=path.get(path.size()-1).x;
	         int width=(x2-x1)*2;
	         int height=(y2-y1)*2;
	         if(K==-1){
		         for(int i=0; i<20; i++){ //여기도 걍 세번그림
		        	 bg.setColor(Color.GREEN);
		        	 bg.drawLine(x1*40+40+width*i,y1*40+40+height*i,x1*40+40+width*(i+1),y1*40+40+height*(i+1)); 
		        	 bg.drawLine(x1*40+41+width*i,y1*40+41+height*i,x1*40+41+width*(i+1),y1*40+41+height*(i+1));
		        	 bg.drawLine(x1*40+39+width*i,y1*40+39+height*i,x1*40+39+width*(i+1),y1*40+39+height*(i+1));
		        	 repaint();
		        	 setVisible(true);
		        	 
		         }
	         }
	         else{
		         for(int i=0; i<20; i++){ //여기도 걍 세번그림
		        	 bg.setColor(Color.RED);
		        	 bg.drawLine(x1*40+40+width*i,y1*40+40+height*i,x1*40+40+width*(i+1),y1*40+40+height*(i+1)); 
		        	 bg.drawLine(x1*40+41+width*i,y1*40+41+height*i,x1*40+41+width*(i+1),y1*40+41+height*(i+1));
		        	 bg.drawLine(x1*40+39+width*i,y1*40+39+height*i,x1*40+39+width*(i+1),y1*40+39+height*(i+1));
		        	 repaint();
		        	 setVisible(true);
		        	 
		         }
	         }
	         
	         
         }
         if(mm.getRobotPath().size()==1){
        	 bg.drawImage(robot_img, mm.getRobotPath().get(0).y*40+20, mm.getRobotPath().get(0).x*40+20,this);
             
         }
         
         else if(mm.getRobotPath().size()>1){  // K값, Xx, Yy를 이용하여 한칸씩 변화된 좌표 표현 
        		 	//for(int i=0; i<K; i++){
        	 if(K==-1){
        		 bg.drawImage(robot_img, mm.getRobotPath().get(mm.getRobotPath().size()-1).y*40+20, mm.getRobotPath().get(mm.getRobotPath().size()-1).x*40+20,this);
        	 }
        	 else{
        		 Xx = mm.getRobotPath().get(mm.getRobotPath().size()-1).x - mm.getRobotPath().get(mm.getRobotPath().size()-2).x;
        		 Yy = mm.getRobotPath().get(mm.getRobotPath().size()-1).y - mm.getRobotPath().get(mm.getRobotPath().size()-2).y;
        		 if(Xx == 0 && Yy > 0)
        			 bg.drawImage(robot_img, mm.getRobotPath().get(mm.getRobotPath().size()-2).y*40+20+Yy*40-K, mm.getRobotPath().get(mm.getRobotPath().size()-2).x*40+20,this);
        		 else if(Yy ==0 && Xx > 0)
        			 bg.drawImage(robot_img, mm.getRobotPath().get(mm.getRobotPath().size()-2).y*40+20, mm.getRobotPath().get(mm.getRobotPath().size()-2).x*40+20+Xx*40-K,this);
        		 else if(Yy < 0)
        			 bg.drawImage(robot_img, mm.getRobotPath().get(mm.getRobotPath().size()-2).y*40+20-Xx*40-(40-K), mm.getRobotPath().get(mm.getRobotPath().size()-2).x*40+20,this);
        		 else if(Xx < 0)
        			 bg.drawImage(robot_img, mm.getRobotPath().get(mm.getRobotPath().size()-2).y*40+20, mm.getRobotPath().get(mm.getRobotPath().size()-2).x*40+20-Yy*40-(40-K),this);
        		 
        	 }
         }
         
         
         g.drawImage(img, 0, 0, this); 
         
      } /////////////////////////////////////여기까지 배경그리기 
      
   } 
  
} 