import java.awt.*;
import java.awt.event.*;

public class boss {

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
	public boolean isMoving = true;
	public int explodeTime;
	public boolean explodeStatus;

	public int frameCounter;
	public int threadCounter;
	public int frames;
	public int firstFrameXPos, firstFrameYPos;
	public int frameWidth, frameHeight;

	public boss(int tempX, int tempY, int tempWidth, int tempHeight, int tempHealth) {

		frameWidth = 192;
		frameHeight = 195;
		frames = 25;
		firstFrameXPos = 0;
		firstFrameYPos = 0;
		frameCounter = 0;

		xpos = tempX;
		ypos = tempY;

		dx = (int) (6 * Math.random() + 2);
		dy = (int) (6 * Math.random() + 2);

		height = tempHeight;
		width = tempWidth;
		health = tempHealth;

		moveLeft = false;
		moveRight = false;
		moveUp = false;
		moveDown = false;

		explodeTime = 40;
		explodeStatus = false;

		isAlive = true;

		rec = new Rectangle(xpos, ypos, width, height);

	}

	public void move() {
		if (moveLeft) {
			xpos = xpos - dx;
		}

		if (moveRight) {
			xpos = xpos + dx;
		}

		if (moveUp) {
			ypos = ypos - dy;
		}

		if (moveDown) {
			ypos = ypos + dy;
		}

		rec = new Rectangle(xpos, ypos, width, height);
	}

	public void downFast() {
		dy = 6;
		isMoving = false;
		moveRight = false;
		moveDown = true;
	}

	public void left() {
		isMoving = false;
		moveLeft = true;
	}

	public void right() {
		isMoving = false;
		moveRight = true;
	}

	public void up() {
		isMoving = false;
		moveUp = true;
	}

	public void down() {
		isMoving = false;
		moveDown = true;
	}

	public void reset() {
		moveLeft = false;
		moveRight = false;
		moveUp = false;
		moveDown = false;
	}
}
