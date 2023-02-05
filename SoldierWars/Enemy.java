import java.awt.*;
import java.awt.event.*; 

public class Enemy
{
   public int height;
   public int width;
   public int xpos;
   public int ypos;
   public int health;
   public Rectangle rec; 						
   public int dy;
   public boolean isAlive;
   public boolean parachute;
   
   
   public Enemy(int tempX, int tempY, int tempWidth, int tempHeight, int tempdy, int tempHealth)
   {
   
      xpos = tempX;
      ypos = tempY;
      
      dy = tempdy;
   
      height = tempHeight;
      width = tempWidth;
      health = tempHealth;
      
      isAlive=true;
      parachute = false;
      
      rec = new Rectangle( xpos, ypos, width, height);
   
   }
   
   public void move()
   {        
      ypos = ypos + dy;
      rec= new Rectangle (xpos,ypos,width,height);
   }
}

