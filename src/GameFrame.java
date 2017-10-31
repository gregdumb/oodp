import javax.swing.*;
import java.awt.*;

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

		// Create components
		BoardPanel boardPanel = new BoardPanel();

		// Add components to frame
		frame.add(boardPanel);

		// Show frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
