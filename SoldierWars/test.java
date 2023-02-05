import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class test extends Applet implements Runnable, KeyListener
{
   //Graphics bufferGraphics; 
   //Image offscreen;
   
   public tank tank;
   public Image tankPic;
   public Image wallPic;
   public wall wallRight;
   public wall floor;
   public wall wall;
        
   public int fireCount=0;
   public int levelCount=1;
   public int aliveCount;
   
   public int timer;
   public boolean time;
   
   public boolean gameOver = false;
   public boolean gameStart = true;
   
   Thread thread;


   public void init()
   {
      setSize(1000,1000);
   
      wall = new wall(0, 0, 100, 1000);
      wallRight = new wall(900, 0, 1000, 1000);      
      floor = new wall(-100, 700, 1000, 1000);
      wallPic = getImage(getDocumentBase(),"wall.jpg");
        
      tankPic = getImage(getDocumentBase(),"tank.png");
      tank = new tank(450, floor.ypos-100, 100, 100, 3);
      
      time = false;
      timer = 10;
      
      addKeyListener(this);
      
      thread = new Thread(this);  
      thread.start();
   }
   
   public void moveEverything()
   {
      if(gameStart == true)
      {
         tank.move();
      }
   }
   
   public void checkIntersections()
   {     
      if(tank.xpos == 200)
      {
         time = true;
      }
   }   
   

   public void paint(Graphics g)
   {
     
      
      g.drawImage(wallPic, wall.xpos, wall.ypos, wall.width, wall.height, this);
      g.drawImage(wallPic, wallRight.xpos, wallRight.ypos, wallRight.width, wallRight.height, this);
         
      g.drawImage(tankPic, tank.xpos, tank.ypos, tank.width, tank.height, this);
      
      
      if(time == true)
      {
         if(tank.isAlive==true)
         {
            g.drawImage(wallPic, floor.xpos, floor.ypos, floor.width, floor.height, this);
         }
         timer--;
         System.out.println(timer);
         if(timer == 0)
         {
            time = false;
            timer = 10;
         }
      }
      
      
      
   } 
         

   
   public void run() 
   {
      while(true)
      {
         moveEverything();		
         checkIntersections();
         repaint();			
      	
         try 
         {
            thread.sleep(10);
         }
         catch (Exception e){ }
      }
   }
   
   
   public void keyPressed( KeyEvent event)
   {
      String keyin;
      keyin = ""+event.getKeyText(event.getKeyCode());
      if(event.getKeyCode()==KeyEvent.VK_LEFT)
      {
         if (tank.xpos>wall.xpos && gameOver == false)
         { 
            tank.moveLeft = true;
         }        
      
      }
      if(event.getKeyCode()==KeyEvent.VK_RIGHT)
      {
         if (tank.xpos<wallRight.xpos && gameOver == false)
         {
            tank.moveRight = true;
         }
      }     
      if (event.getKeyCode()==KeyEvent.VK_SPACE)
      {
      
      } 
      if(keyin.equals("R") && gameOver == true)
      {
         //restart();
      }  
      if(keyin.equals("S"))
      {
         gameStart = true;
      }  
   }
   
   public void keyReleased(KeyEvent event)
   {
      String keyin;
      keyin = ""+event.getKeyText(event.getKeyCode());
      if(event.getKeyCode()==KeyEvent.VK_LEFT)
      {
         tank.moveLeft=false;
      }
      if(event.getKeyCode()==KeyEvent.VK_RIGHT)
      {
         tank.moveRight=false;
      }  
         
   }
   
   public void keyTyped(KeyEvent event)
   {
      char keyin;
      keyin = event.getKeyChar();
      //System.out.println("Key Typed;"+keyin);
   }
}