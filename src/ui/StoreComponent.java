package ui;

import logic.GameModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Greg Brisebois
 * @version 1.0
 *
 * A rounded rectangle that represents a mancala store
 */
public class StoreComponent extends JComponent
{
	/**
	 * Stores/holes are numbered like so:
	 *
	 *     12 11 10 9  8  7
	 *  13                  6
	 *     0  1  2  3  4  5
	 */
	private int id;
	private int width;
	private int height;
	
	private GameModel model;
	
	public StoreComponent(int id, int width, int height, GameModel model) {
	
		this.id = id;
		this.width = width;
		this.height = height;
		
		this.model = model;
		
		this.setSize(width, height);
	}
	
	/**
	 * Paint component
	 * @param g Graphics context
	 */
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		// Draw store outline
		g2d.drawRoundRect(0, 0, width-1, height-1, width/2, width/2);
		
		// Marble positioning settings
		int numPieces = model.getCountOfHole(id);
		int pieceSize = 12;
		int pieceSpacing = pieceSize + 5;
		int maxColumns = 4;
		
		int totalPieceWidth = (pieceSpacing * maxColumns) - 5;
		int start = (width - totalPieceWidth) / 2;
		
		// Place marbles
		for(int i = 0; i < numPieces; i++) {
			int x = start + (i % maxColumns) * pieceSpacing;
			int y = start + (i / maxColumns) * pieceSpacing;
			g2d.fillOval(x, y, pieceSize, pieceSize);
		}
		
		// Draw ID of store (for debugging)
		//g2d.drawString(Integer.toString(this.id), 0, 10);
		
	}
	
	/**
	 * Used by swing to get what size we want to be
	 * @return preferred size
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
}
