import java.awt.*; 
import java.awt.event.*; 

public class bullet
{

   public int height;
   public int width;
   public int health;
   public int xpos;
   public int ypos;
   public int dy;
   public boolean isAlive;
   public boolean moveDown;
   public boolean boom = false;
   public boolean fireball = false;
   public boolean boomhit = false;
   public boolean boomhithit = false;
   public boolean recAlive = true;
   public int boomTime;
   public boolean boomStatus;
   public Rectangle rec; 	
   					


   public bullet(int tempX, int tempY, int tempWidth, int tempHeight, int tempHealth)
   {
   
      xpos = tempX;
      ypos = tempY;
      
      dy = 4;
      isAlive = false;
   
      height = tempHeight;
      width = tempWidth;
      health = tempHealth;
      
      boomTime = 10;
      boomStatus = false;
      
      if(recAlive == true)
      {
         rec = new Rectangle( xpos, ypos, width, height);
      }
   }
   
   public void move()
   {
      if(moveDown)
      {
         ypos = ypos+dy;
         if(recAlive == true)
         {
            rec = new Rectangle(xpos, ypos, width, height);
         }
      }
   }
   public void stop()
   {
      moveDown = false;
   }
}