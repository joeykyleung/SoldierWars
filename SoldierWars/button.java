import java.awt.*;
import java.awt.event.*;

public class button {
	public int height;
	public int width;
	public int xpos;
	public int ypos;
	public String text;
	public Rectangle rec;

	public button(int tempX, int tempY, int tempWidth, int tempHeight) {
		xpos = tempX;
		ypos = tempY;
		height = tempHeight;
		width = tempWidth;

		rec = new Rectangle(xpos, ypos, width, height);
	}
}