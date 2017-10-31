import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Greg on 10/31/2017.
 * DEPRECATED DO NOT USE WILL DELETE SOON
 */
public class BoardComponent extends JComponent
{
	// Not sure if we will use image or just draw circles yet
	private final String BOARD_IMG = "./res/board.jpg";
	private BufferedImage boardImage;

	public BoardComponent() {

		// Load board image
		try {
			boardImage = ImageIO.read(new File(BOARD_IMG));
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}


	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Number of circles on a mancala board row
		final int numCircles = 6;

		// Distance between each circle
		final int circleOffset = this.getWidth() / (numCircles + 1);

		for(int i = 1; i <= numCircles; i++) {
			drawCenteredCircle(g2d, circleOffset*i, 100, 50);
			drawCenteredCircle(g2d, circleOffset*i, 300, 50);
		}
	}

	/**
	 * Draws a circle centered at given point
	 * @param g2d Graphics2D to draw the circle on
	 * @param x Center X
	 * @param y Center Y
	 * @param radius Radius of circle
	 */
	private void drawCenteredCircle(Graphics2D g2d, int x, int y, int radius) {

		int actualX = x - radius;
		int actualY = y - radius;
		int actualW = radius * 2;

		g2d.drawOval(actualX, actualY, actualW, actualW);

	}
}
