import java.awt.*;
import java.awt.event.*;

public class bomb {

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

	public int frameCounter;
	public int threadCounter;
	public int frames;
	public int firstFrameXPos, firstFrameYPos;
	public int frameWidth, frameHeight;

	public bomb(int tempX, int tempY, int tempWidth, int tempHeight, int tempHealth) {
		frameWidth = 50;
		frameHeight = 200;
		frames = 18;
		firstFrameXPos = 0;
		firstFrameYPos = -100;
		frameCounter = 0;

		xpos = tempX;
		ypos = tempY;

		dy = 2;
		isAlive = false;

		height = tempHeight;
		width = tempWidth;
		health = tempHealth;

		boomTime = 40;
		boomStatus = false;

		if (recAlive == true) {
			rec = new Rectangle(xpos, ypos, width, height);
		}
	}

	public void move() {
		if (moveDown) {
			ypos = ypos + dy;
			if (recAlive == true) {
				rec = new Rectangle(xpos, ypos, width, height);
			}
		}
	}

	public void stop() {
		moveDown = false;
	}
}