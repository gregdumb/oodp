import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * GameFrame
 * Swing frame that displays the Mancala game
 */
public class GameFrame
{
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private final String TITLE = "Mancala game";

	public GameFrame() {
		// Create frame
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setTitle(TITLE);
		frame.setResizable(false);

		// Add components to frame

		// THIS IS TO TEST HAVING AN IMAGE HERE
		// THIS NEEDS TO GO IN ITS OWN COMPONENT CLASS
		try
		{
			final BufferedImage image = ImageIO.read(new File("./res/board.jpg"));

			// VERY HACKY
			JPanel boardPanel = new JPanel()
			{
				@Override
				protected void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					g.drawImage(image, 0, 0, null);
				}
			};

			frame.add(boardPanel);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}

		// Show frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
