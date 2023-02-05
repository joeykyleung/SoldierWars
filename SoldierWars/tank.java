import java.awt.*;
import java.awt.event.*;

public class tank {

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
	public boolean dead = false;

	public int frameCounter;
	public int threadCounter;
	public int frames;
	public int firstFrameXPos, firstFrameYPos;
	public int frameWidth, frameHeight;

	public tank(int tempX, int tempY, int tempWidth, int tempHeight, int tempHealth) {
		frameWidth = 192;
		frameHeight = 195;
		frames = 25;
		firstFrameXPos = 0;
		firstFrameYPos = 0;
		frameCounter = 0;

		xpos = tempX;
		ypos = tempY;

		dx = 5;
		dy = 0;

		height = tempHeight;
		width = tempWidth;
		health = tempHealth;

		moveLeft = false;
		moveRight = false;

		isAlive = true;

		rec = new Rectangle(xpos, ypos, width, height);

	}

	public void move() {
		if (xpos <= 100) {
			moveLeft = false;
			;
		}
		if (xpos >= 800) {
			moveRight = false;
		}

		if (moveLeft) {
			xpos = xpos - dx;
		}

		if (moveRight) {
			xpos = xpos + dx;
		}

		rec = new Rectangle(xpos, ypos, width, height);
	}

	public void stop() {
		moveLeft = false;
		moveRight = false;
	}
}
