import java.awt.*;
import java.awt.event.*;

public class projectile {

	public int height;
	public int width;
	public int xpos;
	public int ypos;
	public int dy;
	public boolean isAlive;
	public Rectangle rec;

	public int frameCounter;
	public int threadCounter;
	public int frames;
	public int firstFrameXPos, firstFrameYPos;
	public int frameWidth, frameHeight;

	public projectile(int tempX, int tempY, int tempWidth, int tempHeight) {
		frameWidth = 35;
		frameHeight = 40;
		frames = 20;
		firstFrameXPos = 0;
		firstFrameYPos = 0;
		frameCounter = 0;

		xpos = tempX;
		ypos = tempY;

		dy = 4;
		isAlive = false;

		height = tempHeight;
		width = tempWidth;

		rec = new Rectangle(xpos, ypos, width, height);

	}

	public void move() {
		ypos = ypos - dy;
		rec = new Rectangle(xpos, ypos, width, height);
	}

}