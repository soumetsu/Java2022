//Creates Block objects and makes them move
import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Font;

//based off BouncingBlockKeyBoard_Mouse java file
public class FinalProject22 extends JPanel
{

	/*
	 * CONTROLS FOR THE PROGRAM:
	 * W: Moves ship forward
	 * A: Moves ship left
	 * S: Moves ship backward
	 * D: Moves ship right
	 * Space: Hold to fire bullets
	 * Shift: Slows ship speed down when moving. 
	 * If shift is held while firing, it will fire a laser beam instead of a volley of bullets.
	 * 
	 */
	
   //any variable that will be used throughout the class needs to be declared here
   
   //constants for size of panel. also static so can be used in static main method
   private static final int WIDTH = 1366;
   private static final int HEIGHT = 768;
   
   private ImageIcon bg;
   
   
   private BufferedImage myImage;
   private Graphics buffer;
   private Ship smallBlock;
   private ArrayList<Meteor> faller = new ArrayList<Meteor>();
   private Timer t, shoot, shower, shower2;
   private ArrayList<Projectile> bullet =new ArrayList<Projectile>();
   private Font title = new Font("Optima", Font.BOLD, 28);
   private Font stats = new Font("Arial", Font.ITALIC, 20);
   private int bgSpeed = 2;
   
   private int rightCheck;  //0 = not moving, 1 = left, 2 = right

   private boolean dblPoints = false;
   private int score;
   private int time = 0; //In centiseconds
   private int time2 = 0; //like time, but used to run the background image.
   private int bossTimer = 0;
   private int extraTimer = 0;
   private int secondsTime = 0;
   private int minutesTime = 0;
   private String displayTime = "";
   private int scoreMulti = 1;
   private int lives = 5;
   private int difficulty = 0; //0 = easy, 1 = hard
   private int meteorsDestroyed = 0;
   private boolean startedRunning = false;
   
   //private int 	
   

   
   //FOR KEYBOARD: boolean variables for each key used
   private boolean leftPush, rightPush, upPush, downPush, spacePush, shiftPush;
 
    /* Constructor - everything for initial setup of lab,
   including instantiating the variables above, and what should be displayed
   on the Panel when it first comes up*/
   public FinalProject22()
   {
   //create a BufferedImage object
      myImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
   //connect the above BufferedImage object with a Graphics object   
      buffer = myImage.getGraphics();
      
      //initially a black background
      buffer.setColor(Color.black);
      buffer.fillRect(0, 0, WIDTH, HEIGHT);
   
      bg = new ImageIcon("SpaceBackground.jpg");
      buffer.drawImage(bg.getImage(),0,0, 100, 50,null);
   //instantiate blocks, make them move, and draw them

      
      smallBlock = new Ship();
      smallBlock.draw(buffer);
      
      faller.add(new Meteor());
      //faller.fall(HEIGHT);
      faller.get(0).draw(buffer);
      
      
      
      
      //FOR KEYBOARD: instantiate boolean for keys to false
      rightPush = false;
      leftPush = false;
      upPush = false;
      downPush = false;
      spacePush = false;
      shiftPush = false;
      
      
      bullet.add(new Projectile(smallBlock.getX(),smallBlock.getY()));
   //instantiate Timer object and link it to associated ActionListener 
      t = new Timer(1, new T1Listener());
      shoot = new Timer(20, new RapidFire());
      shower = new Timer(950, new RockShower());
      shower2 = new Timer(650, new RockShower2());
    //start the Timer
      t.start(); 
      shower.start();
      
      //FOR KEYBOARD: connect KeyListener to JPanel
      addKeyListener(new Key());
      setFocusable(true);
      
   //FOR MOUSE: create a mouse Object
      Mouse myMouse = new Mouse();
     //connect to Mouse object to Mouse listener for mousePressed events:
      addMouseListener(myMouse);
     //below needed ONLY if using mouseMoved or mouseDragged methods:
     //addMouseMotionListener(myMouse);
     //below needed  ONLY if using mouseWheelMoved method:
     //addMouseWheelListener(myMouse);
   }
   
   /*paintComponent will not only have ONE line of code, drawing the 
   COMPLETED buffered image onto the Panel like a picture*/
   public void paintComponent(Graphics g)
   {
	 
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);

   }   
   

   
   /*ActionListener classes will always be linked to timers*/
   private class T1Listener implements ActionListener
   {
   
   //actionPerformed method will be executed every 1000 milliseconds by Timer
      public void actionPerformed(ActionEvent e)
      {
    	 
         //cover up existing shapes
         buffer.setColor(Color.black);
         buffer.fillRect(0, 0, WIDTH, HEIGHT);
      
         buffer.drawImage(bg.getImage(), 0, 0+time2, 1000, 768, null);
         buffer.drawImage(bg.getImage(), 0, -768+time2, 1000, 768, null);
         
         if(time2 % 768 == 0 && time2 != 0)
         {
        	 time2 = 0;
         }

      //make ship move and draw again
         smallBlock.draw(buffer);

// aaaaaaaaaaaaaaaaaaaaaaaaaaaaa
 
         for(int y1 = 0; y1 < faller.size(); y1++)
         {
             if(faller.get(y1).getY() >= HEIGHT)
             {
        		 if(y1 == faller.size()-1&& faller.size() != 1)
        		 {
                	 faller.remove(y1);
                	 y1--;
                	 System.out.println("Meteor removed (out of bounds, last in array)");
        		 }
        		 else {
        			 faller.remove(y1);
            	 	System.out.println("Meteor removed (out of bounds)");
        		 }
             }
             else {
            	 faller.get(y1).fall(0);
             	faller.get(y1).draw(buffer);
             
             	if(smallBlock.inBlock(faller.get(y1))) //ACSKDCSAADCSDSACKADCSHJKDACSHKLDSACHLKCDSALKHJCADSLKHJCDSALHKJADCSHLKJDCASHKJLCDASK  DOO
             	{
             		if(y1 == faller.size()-1&& faller.size() != 1) {
             			if(faller.get(y1).isItPowerUp())
             			{
             				//scoreMulti = scoreMulti *2;
             				dealDamage();
                 			faller.remove(y1);
                 			y1--;
                 			System.out.println("Meteor removed (crashed, last in array)");
             			}
             			else
             			{
             				dealDamage();
                 			faller.remove(y1);
                 			y1--;
                 			meteorsDestroyed++;
                 			
                 			System.out.println("Meteor removed (crashed, last in array)");
             			}

             		}
             		else {
             			if(faller.get(y1).isItPowerUp())
             			{
             				dealDamage();
             				//scoreMulti = scoreMulti *2;
                 			faller.remove(y1);
                 			System.out.println("Meteor removed (crashed)");
             			}
             			else
             			{
             				dealDamage();
                 			faller.remove(y1);
                 			System.out.println("Meteor removed (crashed)");
             			}

             		}

             		
             	}
             }
             
             
         }
         
         if(lives == 0)
         {
        	 t.stop();
        	 shoot.stop();
        	 shower.stop();
        	 shower2.stop();
        	 buffer.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 36));
        	 buffer.setColor(Color.RED);
        	 buffer.drawString("YOU DIED!", 500, 300);
        	 
         }
         
         if(meteorsDestroyed == 50)
         {
        	 t.stop();
        	 shoot.stop();
        	 shower.stop();
        	 shower2.stop();
        	 buffer.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 36));
        	 buffer.setColor(Color.GREEN);
        	 buffer.drawString("YOU WIN!", 500, 300);
         }
         
         for(int y1 = 0; y1 < faller.size(); y1++)
         {
             for(int x1 = 0; x1 < bullet.size(); x1++)
             {
            	 if(faller.get(y1).isItPowerUp() == false)
            	 {
                	 if(faller.get(y1).inBlock(bullet.get(x1)))
                	 {
                		 faller.get(y1).takeHealth();
                		 if(faller.get(y1).getHealth() <= 0) {
                			score = score + 50 * scoreMulti;
                			meteorsDestroyed++;
//                			if((int)(Math.random() * 100 + 1) % 2 == 0) {
//                				faller.add(new Meteor(smallBlock.getX(), smallBlock.getY(), Color.YELLOW, "Double Points"));
//                			}
                		 	faller.get(y1).setWidth(0);
                		 	faller.get(y1).setHeight(0);
                		 	faller.get(y1).setY(HEIGHT);
                		 }
                		 else
                			 score = score + 5 * scoreMulti;
                			 
                		 bullet.get(x1).setRadius(0);
                		 bullet.get(x1).setY(0);               		 
                		
                	 }
            	 }
            	 
             }
         }
         
         for(int x1 = 0; x1 < bullet.size(); x1++)
         {
        	 if(bullet.get(x1).getY() <= 1)
        	 {
        		 if(x1 == bullet.size()-1&& bullet.size() != 1)
        		 {
            		 bullet.remove(x1);
            		 x1--;
            		 //System.out.println("Bullet removed (out of bounds, last array)");
        		 }
        		 else
        		 {
        			 bullet.remove(x1);
        		 //System.out.println("Bullet removed (out of bounds)");
        		 }
        	 }
        	 else
        	 {
            	 bullet.get(x1).draw(buffer);
            	 bullet.get(x1).launch((int)bullet.get(x1).getX(), (int)bullet.get(x1).getY(), localSpeed, rightCheck);
        	 }

        	 
         }
         
         if(bossTimer == 25 && time == 0)
         { 
        	 shower.stop();
        	 shower2.start();
        	 startedRunning = true;
        	 bgSpeed = 8;
         }
         
         
         
         time++;
         time2 = time2 + bgSpeed;
         if(time%100 == 0)
         {
        	 secondsTime++;
        	 bossTimer++;
        	 time = 0;
         }
         
         
         if(secondsTime == 60)
         {
        	 minutesTime++;
        	 secondsTime = 0;
         }
         
         if(minutesTime == 0)
         {
        	 if(secondsTime < 10) {
            	 displayTime =  "0:0"  + secondsTime + ":" + time;
        	 }
        	 else
        		 displayTime =  "0:" + secondsTime + ":" + time;
         }
         else if(secondsTime < 10)
        	 displayTime = "" + minutesTime + ":0" + secondsTime + ":" + time;
         else
        	 displayTime = "" + minutesTime + ":" + secondsTime + ":" + time;
         

         buffer.setColor(Color.white);
         buffer.fillRect(1000, 0, 366, 768);
         buffer.setColor(Color.red);
         //String titledraw = "Java Bullet Hell";
         buffer.setFont(title);  //Comes with Java by default, but had to search up how to change fonts/font sizes. This is the only thing I had to search up for "outside help"
         //buffer.setFont();
         buffer.drawString("Java Meteor Shower", 1025, 50);
         buffer.setColor(Color.black);
         buffer.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
         buffer.drawString("by Henry Adams    Block 7", 1035, 75);
         
         
         //buffer.setFont(new Font(fontSelect[4], Font.BOLD, 28));
         buffer.setColor(Color.black);
         buffer.drawString("Time:     " + displayTime, 1025, 300);
         buffer.drawString("Points:      " + score, 1025, 325);
         buffer.drawString("Lives: " + lives, 1025, 350);
         buffer.drawString("Meteors Destroyed: " + meteorsDestroyed, 1025, 375);
         
         buffer.setFont(new Font(Font.SANS_SERIF,Font.ITALIC, 19));
         
         buffer.drawString("W -> Move Up          A -> Move Left ", 1020, 600);
         buffer.drawString("S -> Move Down        D -> Move Right ", 1020, 620);
         buffer.drawString("Space -> Shoot        Shift  -> Slow Down", 1020, 640);
         
         
         
         
         //FOR KEYBOARD: invoke the moveBlock method defined in this class
         moveBlock();
//         bullet.set
         
         
         //execute paint component again
         repaint();
      }
   }
   
   private class RapidFire implements ActionListener
   {
	public void actionPerformed(ActionEvent e) {
		Projectile bullet = new Projectile(smallBlock.getX(),smallBlock.getY());
		bullet.launch(smallBlock.getX(),smallBlock.getY(), localSpeed, rightCheck);
		bullet.draw(buffer);
		
		repaint();
		
	}
	   
   }
   
   
   
   private class RockShower implements ActionListener
   {
	   public void actionPerformed(ActionEvent e) {
		     faller.add(new Meteor());
		     repaint();   
	   }
   }
   
   private class RockShower2 implements ActionListener
   {
	   public void actionPerformed(ActionEvent e) {
		   faller.add(new Meteor(0));
		   repaint();
		   
	   }
	   
	   
   }
    
    //FOR KEYBOARD: Key Adapater class needed for keyboard use use
   private class Key extends KeyAdapter
   {
      /*Determines which keys are pressed and sets
      /appropriate boolean variable to true*/
      public void keyPressed(KeyEvent e)
      {    
         if (e.getKeyCode() == KeyEvent.VK_A)
            leftPush = true;  
         if (e.getKeyCode() == KeyEvent.VK_D)
            rightPush = true;  
         if (e.getKeyCode() == KeyEvent.VK_W)
        	upPush = true;
         if(e.getKeyCode() == KeyEvent.VK_S)
        	 downPush = true;
         if(e.getKeyCode() == KeyEvent.VK_SPACE)
        	 spacePush = true;
         if(e.getKeyCode() == KeyEvent.VK_SHIFT)
        	 shiftPush = true;
         
         
      }
      
      /*Determines which keys are released and sets
      /appropriate boolean variable to false*/
      public void keyReleased(KeyEvent e)
      {
         if (e.getKeyCode() == KeyEvent.VK_A)
            leftPush = false;  
         if (e.getKeyCode() == KeyEvent.VK_D)
            rightPush = false;
         if (e.getKeyCode() == KeyEvent.VK_W)
        	upPush = false;
         if(e.getKeyCode() == KeyEvent.VK_S)
        	 downPush = false;
         if(e.getKeyCode() == KeyEvent.VK_SPACE)
        	 spacePush = false;
         if(e.getKeyCode() == KeyEvent.VK_SHIFT)
        	 shiftPush = false;
      }
   } 
   
   public void dealDamage()
   {
	   lives--;
   }
   
   public void fire(int posX, int posY)
   {
	   shoot.start();
	   
   }
   
   public void stopRocks()
   {
	   shower.stop();
   }
   
   int localSpeed = 10;
    //FOR KEYBOARD: method to move block based on associated booleans being true
   public void moveBlock()
   {
	   if(leftPush == false && rightPush == false)
	   {
		   rightCheck = 0;
	   }
      if (leftPush == true)
      {
    	  rightCheck = 1;
          buffer.setColor(Color.RED);
          buffer.drawString("A -> Move Left", 1193, 600);
         smallBlock.setX(smallBlock.getX() - localSpeed);
         // ensure bump doesn't go past the top of the screen
         if (smallBlock.getX() < 0)
            smallBlock.setX(0);
      }
     //else
      if (rightPush == true)
      {
    	  rightCheck = 2;
          buffer.setColor(Color.RED);
          buffer.drawString("D -> Move Right", 1203, 620);
         smallBlock.setX(smallBlock.getX() + localSpeed);
         // ensure bump doesn't go past the bottom of the screen
         if (smallBlock.getX() > (WIDTH - 366) - smallBlock.getWidth())
            smallBlock.setX((WIDTH - 366) - smallBlock.getWidth());
      }
      
      if (upPush == true)
      {
          buffer.setColor(Color.RED);
          buffer.drawString("W -> Move Up", 1020, 600);
    	  smallBlock.setY(smallBlock.getY() - localSpeed);
    	  if (smallBlock.getY() < 0)
    		  smallBlock.setY(0);
    	  
      }
      
      if (downPush == true)
      {
          buffer.setColor(Color.RED);
          buffer.drawString("S -> Move Down", 1020, 620);
    	  smallBlock.setY(smallBlock.getY() + localSpeed);
    	  if (smallBlock.getY() > HEIGHT - smallBlock.getHeight())
    		  smallBlock.setY(HEIGHT - smallBlock.getHeight());
      }
      
      if(spacePush == true)
      {
    	  buffer.setColor(Color.RED);
          buffer.drawString("Space -> Shoot", 1020, 640);
    	  if(localSpeed == 12)
    	  {
    		  if(time%5 ==0 ) // sets the fire-rate without directly relying on the timer
    		  {
        		  bullet.add(new Projectile(smallBlock.getX()-25,smallBlock.getY()));
        		  bullet.add(new Projectile(smallBlock.getX()-30,smallBlock.getY()+5));
            	  bullet.add(new Projectile(smallBlock.getX() + smallBlock.getWidth()+ 20,smallBlock.getY()));
            	  bullet.add(new Projectile(smallBlock.getX() + smallBlock.getWidth()+25,smallBlock.getY()+5));
    		  }

    	  }
    	  else
    	  {
    		  bullet.add(new Projectile(smallBlock.getX() + smallBlock.getWidth()/2 -3,smallBlock.getY(), Color.yellow));
    	  }
    	  //bullet.launch((int)bullet.getX(), (int)bullet.getY());
      }
      
      if(shiftPush == true)
      {
    	  buffer.setColor(Color.RED);
          buffer.drawString("Shift  -> Slow Down", 1194, 640);
    	  localSpeed = 3;
    	  smallBlock.shiftState(true);
      }
      else
      {
    	  localSpeed = 12;
    	  smallBlock.shiftState(false);
      }

   }
   
   
   
   
   //FOR MOUSE: MouseAdapter needed for Mouse use
   private class Mouse extends MouseAdapter {
   
      public void mouseEntered(MouseEvent e) {
         System.out.println("mouse entered frame");
         if(lives > 0)
         {
             t.start();
             shower.start();
             if(startedRunning == true)
             {
            	 shower2.start();
             }
             
         }

      }
      public void mouseExited(MouseEvent e) {
         System.out.println("mouse exited frame");
         t.stop();
         shower.stop();
         shower2.stop();
      }
//      public void mousePressed(MouseEvent m)
//      {
//         //get (x,y) location of where mouse is clicked:
//         int mouseX = m.getX();
//         int mouseY = m.getY();
//            
//        //Ctrl and Left Click  
//         if(m.isControlDown()) {
//            System.out.println("CTRL Left Click!");
//            
//         }
//         //Alt and Left Click  
//         else if(m.isAltDown()) {
//            System.out.println("ALT Left Click!");
//         }
//         //Shift and left mouse click
//         else if(m.isShiftDown()) {
//            System.out.println("Shift Left Click!");
//            //place block at location of mouse click
//            smallBlock.setX(mouseX);
//            smallBlock.setY(mouseY);
//         }
//         //BUTTON1 = Left mouse click 
//         else if(m.getButton() == MouseEvent.BUTTON1) {
//            System.out.println("Left Click!");
//            //place block off screen
//            smallBlock.setX(-100);
//            smallBlock.setY(-100);
//         
//         }
//      
//         //BUTTON3: Right Click
//         if(m.getButton() == MouseEvent.BUTTON3) {
//            System.out.println("Right Click!");
//            //make block bigger
//            smallBlock.setWidth(smallBlock.getWidth() + 5);
//            smallBlock.setHeight(smallBlock.getHeight() + 5);
//         }
//      
//      
//      
//      }
   /*mouseMoved not used in this example   
      public void mouseMoved(MouseEvent e){
         System.out.println("Mouse moved");
      }
   */
   /*mouseDragged not used in this example 
      public void mouseDragged(MouseEvent e){
         System.out.println("mouse dragged");
      }
   */
   /*mouseWheelMoved not used in this example
      public void mouseWheelMoved(MouseWheelEvent me) { 
      //method getWheelRotation() returns integer
      //e.getWheelRotation()
       }
   */
   } 
      
   public static void main(String[] args)
   {
   //creating a JFrame object and setting title bar
      JFrame frame = new JFrame("Space battle");
   //setting size of JFrame
      frame.setSize(WIDTH + 10, HEIGHT + 10);
   //setting initial location of JFrame
      frame.setLocation(0, 0);
   //allows JFrame to be closed
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //loads L1MyGraffiti JPanel into this JFrame (must match class name)
      frame.setContentPane(new FinalProject22());
    //sets JFrame visible
      frame.setVisible(true);
   }
}



