import java.awt.*;
import java.awt.event.*; 

public class SupplyDrop
{
   public int height;
   public int width;
   public int xpos;
   public int ypos;
   public Rectangle rec; 						
   public int dy;
   public boolean isAlive;   
   public boolean open;
   public boolean hitairstrike = false;
   public boolean move;
   
   public SupplyDrop(int tempX, int tempY, int tempWidth, int tempHeight)
   {
   
      xpos = tempX;
      ypos = tempY;
      
      dy = 2;
   
      height = tempHeight;
      width = tempWidth;
      
      isAlive=true;
      open = false;
      move = true;
      
      rec = new Rectangle( xpos, ypos, width, height);
   
   }
   
   public void move()
   {       
	   if(move == true)
	   {
      ypos = ypos + dy;
      rec= new Rectangle (xpos,ypos,width,height);
	   }
   }
   
   public void stop()
   {
	   move = false;
   }

}

