import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
   
//FINAL PROJECT VERSION, heavily modified Block class 
public class Meteor {
	
   //data fields
   private int x; //top left
   private int y; //top left
   private int width;
   private int height;
   private Color color;
   private int xSpeed; //change in x
   private int ySpeed; //change in y
   private int health;
   private boolean isPowerUpObject;
   private int somethingtest = 90;
//   private ImageIcon rock;
//   private ImageIcon rock2;
   
  
/** 
	 * Default constructor is used for normal meteor 
	 */
   public Meteor() {
//      this.x = 683;
	  setRandomPOS();
	  setRandomSize();
	   setRandomFall();
	   y = 0;
	   xSpeed = 0;
	   color = Color.ORANGE;
	   health = width * 2 + 50;
	   isPowerUpObject = false;
//	   rock = new ImageIcon("meteorfire");
	   
   }

   public Meteor(int something) 
   {
	   somethingtest = something;
		  setRandomPOS();
		  setRandomSize();
		   setRandomFall();
		   y = 0;
		   xSpeed = 0;
		   color = Color.RED;
		   health = width * 2;
		   isPowerUpObject = false;
//		   rock = new ImageIcon("meteore");
//		   rock2 = new ImageIcon("pngwing.com.png");
   }
	/**
	 * Creates a square block of the given color at (x, y)
	 * with a width and height of size.  The The speed of the block
	 * is randomized between -6 and 6, but not 0.   
	 * @param x the x-coordinate of the top-left corner
	 * @param y the y-coordinate of the top-left corner
	 * @param size the width and height of the block
	 * @param color the color of the block
	 */
   public Meteor(int x, int y, Color color, String powerUp) {
      this.x = x;
      this.y = y;
      width = 25;
      height = 25;
      xSpeed = 0;
      ySpeed = 1;
      this.color = color;
      isPowerUpObject = true;
      
   }
	
	/**
		Enemy bullets
	 */
   public Meteor(int x, int y, int width, int height, Color color) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.color = color;
      setRandomSpeed();
   }

   public int getHealth() {
	   return health;
   }
   
   public void takeHealth() {
	   health = health - 10;
   }
   
   public boolean isItPowerUp() {
	   return isPowerUpObject;
   }
   
   
   
   public void setRandomSize() {
	      do{
	          width = (int)(Math.random() * 90 + 30);
	          height = width;
	       }while(width == 0);
	    
	    	// change ySpeed between -6 and 6, but not 0
//	       do{
//	          height = (int)(Math.random() * 90 + 30);
//	       }while(height == 0);
   }
   
   public void setRandomPOS() 
   {
	      do{
	          x = (int)(Math.random() * 900 + 10);
	       }while(x == 0);
	    
	    	// change ySpeed between -6 and 6, but not 0
 
   }
   
   public void setRandomFall() {
	      do{
	          ySpeed = (int)(Math.random() * 6);
	       }while(ySpeed == 0);
	   
   }
   	/**
	 * Sets the xSpeed and ySpeed independently to a random 
	 * integer value between -6 and 6, but not 0.
	 */
   public void setRandomSpeed() {
   
   	// change xSpeed between -6 and 6, but not 0
      do{
         xSpeed = (int)(Math.random() * 12 - 6);
      }while(xSpeed == 0);
   
   	// change ySpeed between -6 and 6, but not 0
      do{
         ySpeed = (int)(Math.random() * 12 - 6);
      }while(ySpeed == 0);
   }


   //Modifier/setter methods
   public void setX(int x){
      this.x = x;
   }
   public void setY(int y){
      this.y = y;
   }
   
   public void setWidth(int w){
      this.width = w;
   }
   
   public void setHeight(int h){
      this.height = h;
   }
   
   public void setXspeed(int xs){
      this.xSpeed = xs;
   }
   
   public void setYspeed(int ys){
      this.ySpeed = ys;
   }
   
   public void setColor(Color c){
      this.color = c;
   }
   
      //Accessor (getter) methods
   public int getX(){
      return this.x;
   }
   public int getY(){
      return this.y;
   }
   
   public int getWidth(){
      return this.width;
   }
   
   public int getHeight(){
      return this.height;
   }
   
   public int getXspeed(){
      return this.xSpeed;
   }
   
   public int getYspeed(){
      return this.ySpeed;
   }
   
   public Color getColor(){
      return this.color;
   }
   
     	/**
	 * Draws a Block
	 * @param g the Graphics object
	 */
   public void draw(Graphics g) {
	   
	  if(somethingtest != 0)
	  {
		  if(isItPowerUp() == false)
		  {
		      g.setColor(color);
		      g.fillOval(x, y, width, height);
		      
		      g.setColor(Color.WHITE);
		      g.drawString("" + getHealth() +"HP", x, y);
//		      g.setColor(Color.BLUE);
//		      g.drawRect(x, y, width, height);
//		      g.drawImage(rock.getImage(), x, y, 200, 200, null);
		  }
		  else
		  {
			   g.setColor(Color.GREEN);
			   g.fillOval(x,y,width,height);
			   g.drawString("2x", x+(width/2),y+(width/2));
		  }
	  }
	  else
	  {
	      g.setColor(color);
	      g.fillOval(x, y, width, height);
	      g.setColor(Color.WHITE);
	      g.drawString("" + getHealth() +"HP", x, y);
//	      g.setColor(Color.BLUE);
//	      g.drawRect(x, y, width, height);
//	      g.drawImage(rock.getImage(), x, y, 200, 200, null);
	  }
	
	  

      
      
      
//      int[] shapeX = {x, x + 40, x + 80};
//      int[] shapeY = {y, y - 60, y};
//      g.fillPolygon(shapeX, shapeY, 3);
   
   }
   


   public void fall(int bottomEdge)
   {
	   y = getY() + ySpeed;
   }
   
   
   /*moveAndBounce: update x and y, check for edge collission*/
   public void moveAndBounce(int rightEdge, int bottomEdge)
   {
   //update x and y with xSpeed and ySpeed
      x = x + xSpeed;
      y = y + ySpeed;
      
      //invoke collision check methods:
      checkRight(rightEdge);
      checkLeft();
      checkBottom(bottomEdge);
      checkTop();
   
   }
   

   /**
      * Method: inBumper - THIS IS ALREADY COMPLETED FOR YOU
      * Postcond: Returns true if any part of the Dot is inside the block
      */  
     public boolean inBlock(Projectile dot)
     {
     //starts at upper left corner's x-xoord
     //each time outter for loop restarts, x will move over one
     //and recycle through all y values
        for(int x = getX(); x <= getX() + getWidth(); x++)   
        {
           //as x-coord remains constant, cycles through y's
           for(int y = getY(); y <= getY() + getHeight(); y++) 
           {
              //executes distance method defined below this method
              //checks every point on the bumper
              //if Dot is inside of the Bumper, method returns true and ends
              if(getDistance(x, y, dot.getX(), dot.getY()) <= dot.getRadius() )
              { 
                 return true; 
              }
                   
           }
        }  
        //if at this point in the code, then true was never returned
        //therefore Dot is NOT inside of the Bumper: return false and end       
        return false;
     }  
     
     public boolean inShip(Ship shi)
     {
     //starts at upper left corner's x-xoord
     //each time outter for loop restarts, x will move over one
     //and recycle through all y values
        for(int xx = getX(); xx <= getX() + getWidth(); xx++)   
        {
           //as x-coord remains constant, cycles through y's
           for(int yy = getY(); yy <= getY() + getHeight(); yy++) 
           {
              //executes distance method defined below this method
              //checks every point on the bumper
              //if Dot is inside of the Bumper, method returns true and ends
              if(getDistance(xx, yy, shi.getX(), shi.getY()) <= shi.getWidth())
              { 
                 return true; 
              }
                   
           }
        }  
        //if at this point in the code, then true was never returned
        //therefore Dot is NOT inside of the Bumper: return false and end       
        return false;
     }   
     
		public boolean isCollision(Ship otherBlock)
		{
			//otherDot = new Dot;
			double otherRadi = otherBlock.getWidth() / 2;
			double otherY = otherBlock.getY();
			double otherX = otherBlock.getX();
			
			double distance = Math.sqrt(Math.pow(otherX - x, 2) + Math.pow(otherY - y, 2));
			//if distance == 0
			//(y + radius > otherY - otherRadi||y - radius < otherY + otherRadi) && (x + radius > otherX - otherRadi||x - radius < otherX + otherRadi)
			if(distance < (otherRadi))
			{
				return true;
			}
		
			return false;
		}
	   
     
     
      /**
       * Postcond: Calculates and returns the distance between (x1, y1) and (x2, y2)
      * Note: This method is needed as a helper method for the inBumper method
      */ 
     private double getDistance(double x1, double y1, double x2, double y2)
     {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
     }

   //right side collision  
   public boolean checkRight(int rightEdge){
   
   
      if(x>rightEdge - width) {//if x is too big
         x = rightEdge - width; //force it to be biggest value
         xSpeed *= -1;  //reverse xSpeed
         return true;
      }
      return false;
   
   }
     
   //left side collision
   public boolean checkLeft(){
      if(x < 0) { //if x is too small
         x = 0; //force it to be smallest value
         xSpeed *= -1;  //reverse xSpeed
         return true;
      }
      return false;
   }

      
       //bottom side collision
   public boolean checkBottom(int bottomEdge){
      if(y>bottomEdge - height) {//if y is too big
         y = bottomEdge - height; //force it to be biggest value
         ySpeed *= -1;  //reverse xSpeed
         return true;
      }
      return false;
   }
      
       //top side collision
   public boolean checkTop(){
      if(y < 0) {//if y is too small
         y = 0; //force it to be smallest value
         ySpeed *= -1;  //reverse ySpeed
         return true;
      }
      return false;
   }
  


}




