import java.awt.*;
import java.awt.event.*;

public class plane {

	public int height;
	public int width;
	public int xpos;
	public int ypos;
	public Rectangle rec;
	public int dx;
	public int dy;
	public int health;
	public boolean moveLeft;
	public boolean moveRight;
	public boolean moveUp;
	public boolean moveDown;
	public boolean isAlive;
	public int explodeTime;
	public boolean explodeStatus;

	public int frameCounter;
	public int threadCounter;
	public int frames;
	public int firstFrameXPos, firstFrameYPos;
	public int frameWidth, frameHeight;

	public plane(int tempX, int tempY, int tempWidth, int tempHeight, int tempHealth, int tempdx) {
		frameWidth = 69;
		frameHeight = 73;
		frames = 18;
		firstFrameXPos = 0;
		firstFrameYPos = 0;
		frameCounter = 0;

		xpos = tempX;
		ypos = tempY;

		dx = tempdx;
		dy = 0;

		height = tempHeight;
		width = tempWidth;
		health = tempHealth;

		moveLeft = false;
		moveRight = false;

		explodeTime = 40;
		explodeStatus = false;

		isAlive = true;

		rec = new Rectangle(xpos, ypos, width, height);

	}

	public void moveLeft() {
		if (moveLeft == true) {
			if (xpos <= 0) {
				xpos = 1000 + (int) (400 * Math.random());
			}
			xpos = xpos - dx;
			rec = new Rectangle(xpos, ypos, width, height);

		}
	}

	public void moveRight() {
		if (moveRight == true) {
			if (xpos >= 1000) {
				xpos = -(int) (400 * Math.random());
			}
			xpos = xpos + dx;
			rec = new Rectangle(xpos, ypos, width, height);
		}
	}

	public void stop() {
		moveLeft = false;
		moveRight = false;
	}
}
