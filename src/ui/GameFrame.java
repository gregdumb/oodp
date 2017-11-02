package ui;

import logic.GameModel;
import ui.BoardPanel;

import javax.swing.*;
import java.awt.*;

/**
 * ui.GameFrame
 * Swing frame that displays the Mancala game
 */
public class GameFrame
{
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private final String TITLE = "Mancala game";
	
	private GameModel model;

	public GameFrame(GameModel model) {
		
		this.model = model;
		
		// Create frame
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setTitle(TITLE);
		frame.setResizable(false);
		
		// Create panel
		JPanel panel = new JPanel();
		
		// Create components
		BoardPanel boardPanel = new BoardPanel(model, WIDTH, 300);

		// Add components & panel to frame
		panel.add(boardPanel);
		frame.add(panel);

		// Show frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
