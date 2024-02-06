import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
   

//FINAL PROJECT VERSION, class that creates the ship
public class Ship {
	
   //data fields
   private int x; //top left
   private int y; //top left
   private int width;
   private int height;
   private Color color;
   private int xSpeed; //change in x
   private int ySpeed; //change in y
   private boolean shifter;
   
   private ImageIcon xw;
  
/** 
	 * Creates a red Block starting at (683, 600) with a 
	 * width of 50 and height of 50.  The speed of the block
	 * is randomized between -6 and 6, but not 0.  
	 */
   public Ship() {
      this.x = 683;
      this.y = 600;
      this.width = 5;
      this.height = 5;
      this.color = Color.GREEN;
      setXspeed(2); //changed from 8 to 2 as of 10/26/2023 due to too fast speed/FPS
      setYspeed(2);
      xw = new ImageIcon("XWing.png");

   
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

	
	/**
	 * Creates a Block at (x, y) with the given width 
	 * and height and specific color.  The speed of the block 
	 * is randomized -6 and 6, but not 0.
	 * @param x the x-coordinate of the top-left corner
	 * @param y the y-coordinate of the top-left corner
	 * @param width the width of the block
	 * @param height the height of the block
	 * @param color the color of the block
	 */

   
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
      g.setColor(color);
      //g.fillRect(x, y, width, height);
      g.drawImage(xw.getImage(), x-27 ,y-27, width+50, height+50,null);
      
      g.setColor(Color.BLUE);
      g.drawRect(x, y, 5, 5);
//      int bodyWidth = width/2; 
//      int[] shapeX = {x, x + bodyWidth, x + width};
//      int[] shapeY = {y, y - 25, y};
//      
//      g.fillPolygon(shapeX, shapeY, 3);
   
   }
   
   public void moveState(boolean yesN)
   {
	   if(yesN == true) {
		   shifter = true;
	   }
	   else
		   shifter = false;
   }
   
//   public int getMoveState(int choice)
//   {
//	   
//   }
   
   public void shiftState(boolean yesN)
   {
	   if(yesN == true)
	   {
		   shifter = true;
	   }
	   else
		   shifter = false;
	   
		   
   }
   public boolean getShiftState()
   {
	   return shifter;
   }

   

   /**
      * Method: inBumper - THIS IS ALREADY COMPLETED FOR YOU
      * Postcond: Returns true if any part of the Dot is inside the block
      */      public boolean inBlock(Meteor met)
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
              if(getDistance(xx, yy, met.getX(), met.getY()) <= met.getWidth())
              { 
                 return true; 
              }
                   
           }
        }  
        //if at this point in the code, then true was never returned
        //therefore Dot is NOT inside of the Bumper: return false and end       
        return false;
     }  
 
     
		public boolean isCollision(Meteor otherMeteor)
		{
			//otherDot = new Dot;
			double otherRadi = otherMeteor.getWidth();
			double otherY = otherMeteor.getY();
			double otherX = otherMeteor.getX();
			
			double distance = Math.sqrt(Math.pow(x - otherX, 2) + Math.pow(y - otherY, 2));
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


  


}




