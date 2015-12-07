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
	int K = 40;  // ��ĭ(40�ȼ�)�� 40���� ������ �̵� �ϱ� ���� ���� 
	int Xx, Yy;         // �κ� draw�� ����� �ӽ� x, y��ǥ 
	
   final int NONE = 0; 
   final int BLACK = 1; 
   final int WHITE = 2; 
   int LINE_NUM_ROWS = InputFrame2.LINE_NUM_ROWS;//15; // ��ü �� �� ��   // ���� ĭ��
   int LINE_NUM_COLS = InputFrame2.LINE_NUM_COLS;//10;               // ���� ĭ�� 
   int LINE_WIDTH = 40; // �� ���� 
   int BOARD_SIZE = LINE_WIDTH * (LINE_NUM_COLS - 1); // ���� ���� ���
   int BOARD_SIZE_HEIGHT = LINE_WIDTH * (LINE_NUM_ROWS - 1); // ���� ���� ���
   
   int STONE_SIZE = (int) (LINE_WIDTH * 0.8); // ���� ũ�� 
   int X0; // ������ġ x��ǥ 
   int Y0; // ������ġ y��ǥ 
   int FRAME_WIDTH; // Frame�� �� 
   int FRAME_HEIGHT; // Frame�� ���� 
   int turn = WHITE;
   MapManager mm;
   // �̰� ���⼱���صα� �ߴµ� r_path�� �κ� ��α׸��� ���̴°Ŷ� ��������� �޾ƿ;� �ϴµ�
   // �κ� ��� �����ص� ����Ʈ Cell�� �����ϴ��� �ؾ��ҵ� 
 
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
      // ��ư�� �߰� 
      
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

      
      // Frame�� ũ�⸦ ����Ѵ�. 
      FRAME_WIDTH = BOARD_SIZE + LINE_WIDTH * 2 + insets.left + insets.right; 
      FRAME_HEIGHT = BOARD_SIZE_HEIGHT + LINE_WIDTH * 2 + pnl_menu.getSize().height 
      + insets.top + insets.bottom; 
      // insets�� visible �Ŀ�! 
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

	      
	      // Frame�� ũ�⸦ ����Ѵ�. 
	      FRAME_WIDTH = BOARD_SIZE + LINE_WIDTH * 2 + insets.left + insets.right; 
	      FRAME_HEIGHT = BOARD_SIZE_HEIGHT + LINE_WIDTH * 2 + pnl_menu.getSize().height 
	      + insets.top + insets.bottom; 
	      // insets�� visible �Ŀ�!
	      setFrameCenter(this, FRAME_WIDTH, FRAME_HEIGHT); 
	      revalidate();
	      repaint();
	      setVisible(true);	      
	      while(true){   		/// add on ���� 1000 ������ �� ���� 40���� ������ K�� ���� (�Ʒ��� MyPanel���� ���)
		      try {				
	  			Thread.sleep(30);			//  25ms���� repaint�Ͽ� MyPanel ���� 
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
	      // ��ư�� �߰� 
	      btn_restart = new JButton("�����");
	      pnl_menu.add(btn_restart); 
	      //����� ��ư ���� �ǵ帮�� �Ǵ� �κ� 
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

	      
	      // Frame�� ũ�⸦ ����Ѵ�. 
	      FRAME_WIDTH = BOARD_SIZE + LINE_WIDTH * 2 + insets.left + insets.right; 
	      FRAME_HEIGHT = BOARD_SIZE_HEIGHT + LINE_WIDTH * 2 + pnl_menu.getSize().height 
	      + insets.top + insets.bottom; 
	      // insets�� visible �Ŀ�!
	      setFrameCenter(this, FRAME_WIDTH, FRAME_HEIGHT); 
	      revalidate();
	      repaint();	
	      setVisible(true);	      
	      K=-1;
	      JOptionPane.showMessageDialog(null, "Ž���Ϸ�!!");
   }
   static void setFrameCenter(JFrame f, int w, int h) { 
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); 
      int xpos = (int) (screen.getWidth() / 2 - w / 2); 
      int ypos = (int) (screen.getHeight() / 2 - h / 2); 
      f.setBounds(xpos, ypos, w, h); 
   } 
   // ��ư ������ �� �׼� 
   public class MyListener implements ActionListener { 
      @Override 
      public void actionPerformed(ActionEvent e) { 
         if (e.getSource() == btn_restart) { 
            // ����� ��ư ������ �ʱ�ȭ�ϴ� ����� 
            
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
    	 
    	 
    	  // ����� �̹��� �ҷ����� 
    	  Image robot_img = Toolkit.getDefaultToolkit().getImage("robot2.png");
    	  Image hazard_img = Toolkit.getDefaultToolkit().getImage("bomb.png");
    	  Image colorblob_img = Toolkit.getDefaultToolkit().getImage("colorblob.png");
    	  Image backMap_img = Toolkit.getDefaultToolkit().getImage("backMap2.jpg");
    	  Image target_img = Toolkit.getDefaultToolkit().getImage("flag.png");
    	  Image start_img = Toolkit.getDefaultToolkit().getImage("start.png");
    	 // ���� �׷��� ���� ũ�� ���� 
         Image img = createImage(BOARD_SIZE + LINE_WIDTH * 2, BOARD_SIZE_HEIGHT 
         + LINE_WIDTH * 2); 
         Graphics bg = img.getGraphics(); 
         
         // ��� ���� ����
         // backMap �̹����� 0,0 ���� �������� �°� ���ȴ�� ǥ��
         bg.drawImage(backMap_img, 0, 0, BOARD_SIZE + LINE_WIDTH * 2, BOARD_SIZE_HEIGHT 
         + LINE_WIDTH * 2, this);
         
         
         bg.setColor(Color.white); //���� �� ����
         for (int i = 0; i < LINE_NUM_ROWS; i++) { // ���� ���� ���� ����
            bg.drawLine(X0, Y0 + i * LINE_WIDTH, X0 + BOARD_SIZE, Y0 + i 
            * LINE_WIDTH); //(x������, y������, x�� ����ġ (���� ���� ���� �������), y�� ����ġ)
         }
         
         for (int i = 0; i < LINE_NUM_COLS; i++) {  // ���� ���� ���� ����
              //���� ���� ������� ���� ���� 
             bg.drawLine(X0 + i * LINE_WIDTH, Y0, X0 + i * LINE_WIDTH, Y0 
             + BOARD_SIZE_HEIGHT); //(x������, y������, x�� ����ġ, y�� ����ġ(���� ���� ���� �������))
         }

         //start ������ robot,startǥ���ϱ�
         if(mm.getRobotPath().get(0).x >= 0){
        	 bg.drawImage(start_img, mm.getRobotPath().get(0).y*40+20, mm.getRobotPath().get(0).x*40+20,this);
        	  	 
         }
         
         
         // hazard�� ����� ��ǥ���� haz�̹��� �׸��� 
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
         // target�� ����� ��ǥ���� target�̹��� �׸��� 
        
         for(int j=0; j<mm.getTarget().size(); j++){
        	 bg.drawImage(target_img, mm.getTarget().get(j).y*40+20, mm.getTarget().get(j).x*40+20, this);
         }
        
         // r_path�� ����� �κ� ��� ����Ʈ�� ���� path �׸���
         if(mm.getRobotPath().size()>1){  // �� ���� �β��� (���� �׸�)
        	 ArrayList<Cell> path=mm.getRobotPath();
	         for(int i=0; i<path.size()-2; i++){
	        	 bg.setColor(Color.GREEN); 
	        	 bg.drawLine(path.get(i).y*40+40,path.get(i).x*40+40, path.get(i+1).y*40+40,path.get(i+1).x*40+40); 
	        	 bg.drawLine(path.get(i).y*40+39,path.get(i).x*40+39, path.get(i+1).y*40+39,path.get(i+1).x*40+39); 
	        	 bg.drawLine(path.get(i).y*40+41,path.get(i).x*40+41, path.get(i+1).y*40+41,path.get(i+1).x*40+41); 
	         }
	         //�̺κп� �κ��� �Բ� �̵���θ� �׷��ִ� �ִϸ��̼�ȿ�� ������

	         int x1=path.get(path.size()-2).y;
	         int y1=path.get(path.size()-2).x;
	         int x2=path.get(path.size()-1).y;
	         int y2=path.get(path.size()-1).x;
	         int width=(x2-x1)*2;
	         int height=(y2-y1)*2;
	         if(K==-1){
		         for(int i=0; i<20; i++){ //���⵵ �� �����׸�
		        	 bg.setColor(Color.GREEN);
		        	 bg.drawLine(x1*40+40+width*i,y1*40+40+height*i,x1*40+40+width*(i+1),y1*40+40+height*(i+1)); 
		        	 bg.drawLine(x1*40+41+width*i,y1*40+41+height*i,x1*40+41+width*(i+1),y1*40+41+height*(i+1));
		        	 bg.drawLine(x1*40+39+width*i,y1*40+39+height*i,x1*40+39+width*(i+1),y1*40+39+height*(i+1));
		        	 repaint();
		        	 setVisible(true);
		        	 
		         }
	         }
	         else{
		         for(int i=0; i<20; i++){ //���⵵ �� �����׸�
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
         
         else if(mm.getRobotPath().size()>1){  // K��, Xx, Yy�� �̿��Ͽ� ��ĭ�� ��ȭ�� ��ǥ ǥ�� 
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
         
      } /////////////////////////////////////������� ���׸��� 
      
   } 
  
} 