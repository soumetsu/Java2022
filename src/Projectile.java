import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;


//FINAL PROJECT , will be bullets/projectiles. Modified dot class
public class Projectile {
	
	private double x;
	private double y;
	private double diameter;
	private double radius;
	private double xSpeed;
	private double ySpeed;
	private double damage;
	private boolean isDefaultBullet;
	private Color color;
	
	public Projectile(double xx, double yy) //bullet
	{
		x = xx;
		y = yy;
		diameter = 20;
		radius = diameter/5;
		color = Color.green;
		xSpeed = 0;
		ySpeed = 10;
		damage = 10;
		isDefaultBullet = true;
		
		
	}
	
	public Projectile(double xx, double yy, Color clr) //bullet
	{
		x = xx;
		y = yy;
		diameter = 15;
		radius = diameter/2;
		color = Color.yellow;
		xSpeed = 0;
		ySpeed = 50;
		damage = 10;
		isDefaultBullet = false;
		
		
	}
	
	public Projectile(double xx, double yy, double dia, Color clr) 
	{
		
		x = xx;
		y = yy;
		diameter = dia;
		radius = dia/2;
		color = clr;
		
		setRandomSpeed();
		xSpeed = 0;
		//Bad idea to both  diameter and radius, since if radius or either one changes, measurements could be wrong
				
	}
	
	
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
	
	   
	   public void launch(int xx, int yy, int type, int moverCheck)
	   {
		   if(type == 12)
		   {
			   yy = (int)getY() - (int)ySpeed;
			   setY(yy);
		   }
		   else {
			   yy = (int)getY() - (int)ySpeed;
			   setY(yy);
			   
			   if(moverCheck == 0)
			   {
				   
			   }
			   else if(moverCheck == 1)
			   {
				   xx = (int)getX() - 3;
			   		setX(xx);
			   }
			   else if(moverCheck == 2)
			   {
				xx = (int)getX() + 3;
			   	setX(xx);
			   }

			   
		   }
		   
		   
	   }
	   
		public boolean isCollision(Meteor otherMeteor)
		{
			//otherDot = new Dot;
			double otherRadi = otherMeteor.getWidth() / 2;
			double otherY = otherMeteor.getY();
			double otherX = otherMeteor.getX();
			
			double distance = Math.sqrt(Math.pow(otherX - x, 2) + Math.pow(otherY - y, 2));
			//if distance == 0
			//(y + radius > otherY - otherRadi||y - radius < otherY + otherRadi) && (x + radius > otherX - otherRadi||x - radius < otherX + otherRadi)
			if(distance < (otherRadi))
			{
				return true;
			}
		
			return false;
		}
	   
	   
	   public boolean checkRight(int rightEdge)
	   {
		   if(x > rightEdge - radius)
		   {
			   x = rightEdge - radius;
			   xSpeed *= -1;
			   //ySpeed *= -1;
			   return true;
		   }
		   return false;
	   }
	   
	   public boolean checkLeft()
	   {
		   if(x < 0) {
			   x = 0;
			   xSpeed *= -1;
			   //ySpeed *= -1;
			   return true;
		   }
		   return false;
	   }
	   
	   public boolean checkBottom(int bottomEdge)
	   {
		   if(y > bottomEdge - radius)
		   {
			   y = bottomEdge - radius;
			   ySpeed *= -1;
			   //xSpeed *= -1;
			   return true;
		   }
		   return false;
		   
		   
		   
	   }
	   
	   public boolean checkTop()
	   {
		   if(y < 0)
		   {
			   y = 0;
			   ySpeed *= -1;
			   //xSpeed *= -1;
			   return true;
		   }
		   return false;
	   }
	   
	   //Modifier/setter methods
	   public void setX(double x){
	      this.x = x;
	   }
	   public void setY(double y){
	      this.y = y;
	   }
	   
	   public void setXSpeed(double xs){
	      this.xSpeed = xs;
	   }
	   
	   public void setYSpeed(double ys){
	      this.ySpeed = ys;
	   }
	   
	   public void setColor(Color c){
	      this.color = c;
	   }
	   
	   public void setDiameter(double dia)
	   {
		   this.diameter = dia;
		   this.radius = dia/2;
	   }
	   
	   public void setRadius(double rad)
	   {
		   this.radius = rad;
		   this.diameter = rad * 2;
	   }
	   
	      //Accessor (getter) methods
	   public double getX(){
	      return this.x;
	   }
	   public double getY(){
	      return this.y;
	   }
	   
	   
	   public double getXSpeed(){
	      return this.xSpeed;
	   }
	   
	   public double getYSpeed(){
	      return this.ySpeed;
	   } 
	   
	   public double getRadius() 
	   {
		   return this.radius;
	   }
	   
	   public double getDiameter()
	   {
		   return this.diameter;
	   }
	   
	   public Color getColor(){
	      return this.color;
	   }
	
	   public void draw(Graphics g)
	   {
		   if(isDefaultBullet == true)
		   {
			    g.setColor(color);
			    g.fillOval((int)x, (int)y, (int)radius/2, (int)radius*2);
		   }
		   else
		   {
			    g.setColor(color);
			    g.fillRect((int)x, (int)y, (int)radius/2, (int)radius*4);
			    g.fillRect((int)x, (int)y-25, (int)radius/2, (int)radius*4);
		   }

		    //g.fillOval((int)x, (int)y - 30, (int)radius/2, (int)radius*8);
	   }

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}





