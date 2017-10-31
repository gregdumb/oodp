import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Greg on 10/31/2017.
 */
public class GameFrame
{
	private final int WIDTH = 800;
	private final int HEIGHT = 600;

	public GameFrame() {

		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setTitle("Mancala Game");

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

		// Set up view

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
