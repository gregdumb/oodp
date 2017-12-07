package ui;

import logic.GameModel;
import ui.BoardPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * ui.GameFrame
 * Swing frame that displays the Mancala game
 */
public class GameFrame implements ChangeListener
{
	private final int WIDTH = 1280;
	private final int HEIGHT = 720;
	private final String TITLE = "Mancala game";
	
	private GameModel model;

	private final JFrame frame;
	private final JLabel turnLabel;
	private final JLabel promptLabel;
	private final StartDialog dialog;

	public GameFrame(GameModel model) {
		
		this.model = model;
		model.attachListener(this);
		
		// Create frame
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setTitle(TITLE);
		frame.setResizable(false);
		
		// Create dialog
		dialog = new StartDialog(model);
		
		// Create panel
		JPanel panel = new JPanel();
		
		// Create components
		BoardPanel boardPanel = new BoardPanel(model, WIDTH, (int)(HEIGHT * 0.6));
		turnLabel = new JLabel("Player 1's turn");
		promptLabel = new JLabel(model.getStateMessage());
		
		// New game button
		JButton newButton = new JButton("New Game");
		newButton.addActionListener(e -> {
			dialog.setVisible(true);
		});
		
		// Undo button
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(e -> {
			model.undo();
		});
		
		// Add components & panel to frame
		panel.add(boardPanel);
		panel.add(turnLabel);
		panel.add(promptLabel);
		panel.add(newButton);
		panel.add(undoButton);
		frame.add(panel);

		// Show frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		// Show starting dialog
		dialog.setVisible(true);
	}

	public void stateChanged(ChangeEvent e) {

		int turn = model.getCurrentTurn() + 1;
		turnLabel.setText("Player " + Integer.toString(turn) + "'s turn");
		promptLabel.setText(model.getStateMessage());

		//frame.repaint();
	}
}
