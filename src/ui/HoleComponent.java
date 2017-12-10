package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.GameModel;

/**
 * @author Greg Brisebois
 * @version 1.0
 *
 * A circle that represents a mancala hole
 */
public class HoleComponent extends JComponent {
	/**
	 * Stores/holes are numbered like so:
	 *
	 *     12 11 10 9  8  7
	 *  13                  6
	 *     0  1  2  3  4  5
	 */
	private final int id;
	private final GameModel model;

	// Width/height of hole
	private int size;

	public HoleComponent(int id, int size, GameModel model) {

		this.id = id;
		this.size = size;
		this.model = model;

		this.setSize(size, size);

		// Tell the model when we are clicked, it will handle it from there
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				model.holeClicked(getId());
			}
		});
	}
	
	/**
	 * Paint component
	 * @param g Graphics context
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Draw hole outline
		g2d.drawOval(0, 0, size - 1, size - 1); // add a buffer of 1 pixel so the edges don't get cut off
		
		// Marble placement settings
		int numPieces = model.getCountOfHole(id);
		int pieceSize = 12;
		int pieceSpacing = pieceSize + 5;
		int maxColumns = 4;
		
		int totalPieceWidth = (pieceSpacing * maxColumns) - 5;
		int start = (size - totalPieceWidth) / 2;
		
		// Draw marbles
		for(int i = 0; i < numPieces; i++) {
			int x = start + (i % maxColumns) * pieceSpacing;
			int y = start + (i / maxColumns) * pieceSpacing;
			g2d.fillOval(x, y, pieceSize, pieceSize);
		}
		
		// Draw ID of hole (for debugging)
		g2d.drawString(Integer.toString(this.getId()), 0, 10);
	}
	
	/**
	 * Get ID of hole
	 * @return ID of hole
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Used by swing to get what size we want to be
	 * @return preferred size
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(size, size);
	}
}
