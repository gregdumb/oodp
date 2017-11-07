package ui;

import logic.GameModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Board Panel
 * JPanel that contains the circles and stuff for the mancala board
 */
public class BoardPanel extends JPanel implements ChangeListener
{
	/** Diameter of the holes */
	private final int HOLE_SIZE = 50;
	
	/** Distance from center of holes to edge of panel */
	private final int EDGE_PADDING = 100;

	private final int LABEL_SIZE_X = 200;
	private final int LABEL_SIZE_Y = 20;
	
	/** Dimensions of panel */
	private int width;
	private int height;
	
	GameModel model;

	public BoardPanel(GameModel model, int width, int height) {
		
		this.model = model;
		this.width = width;
		this.height = height;
		
		// We want to use absolute positioning, no layout manager
		this.setLayout(null);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(new Color(255, 143, 212)); // Using ugly blue just to see where the board is on the screen
		
		// Calculate positioning

		// Size of board, not including padding
		int boardWidth = width - (2 * EDGE_PADDING);
		int boardHeight = height - (2 * EDGE_PADDING);

		// Distance between centers of holes
		int spacingX = boardWidth / (7); // 6 holes and 2 stores
		int spacingY = boardHeight;

		// Position where the first hole goes
		int holeStartX = EDGE_PADDING + spacingX;
		int holeStartY = EDGE_PADDING + spacingY;

		// Position for center of stores
		int rightStoreX = width - EDGE_PADDING;
		int leftStoreX = EDGE_PADDING;
		int storeY = height / 2;

		// Dimensions of stores
		int storeWidth = HOLE_SIZE;
		int storeHeight = HOLE_SIZE + spacingY;

		// Position of player 1/2 labels
		int p1LabelX = (width / 2) - (LABEL_SIZE_X / 2);
		int p1LabelY = height - (EDGE_PADDING / 2) - (LABEL_SIZE_Y / 2);
		int p2LabelX = (width / 2) - (LABEL_SIZE_X / 2);
		int p2LabelY = (EDGE_PADDING / 2) - (LABEL_SIZE_Y / 2);
		
		// Create rows of holes and stores
		for(int i = 0; i < 14; i++) {
			
			// Draw holes
			if(i != 6 && i != 13)
			{
				int row = (i / 7); // Will be 0 on bottom row, 1 on top row
				int column = (i % 7); // Will be 0-5 for each column
				int id = (i > 6) ? (19 - i) : i; // Will be 0-5 for bottom row, 12-7 for top row
				
				int x = holeStartX + (column * spacingX);
				int y = holeStartY - (row * spacingY);
				
				HoleComponent hole = createCenteredHole(id, x, y, HOLE_SIZE);
				this.add(hole);
			}
			// When i = 6 or 13 we are on a store
			else
			{
				int x = (i == 6) ? rightStoreX : leftStoreX;
				int y = storeY;
				
				StoreComponent store = createCenteredStore(i, x, y, storeWidth, storeHeight);
				this.add(store);
			}
		}
		
		// Create stores
		//StoreComponent storeR = createCenteredStore(0, rightStoreX, rightStoreY, storeWidth, storeHeight);
		//this.add(storeR);
		//StoreComponent storeL = createCenteredStore(1, leftStoreX, leftStoreY, storeWidth, storeHeight);
		//this.add(storeL);

		// Create "Player 1/2" labels
		JLabel p1Label = new JLabel("Player 1 >>", SwingConstants.CENTER);
		p1Label.setLocation(p1LabelX, p1LabelY);
		p1Label.setSize(LABEL_SIZE_X, LABEL_SIZE_Y);
		this.add(p1Label);

		JLabel p2Label = new JLabel("<< Player 2", SwingConstants.CENTER);
		p2Label.setLocation(p2LabelX, p2LabelY);
		p2Label.setSize(LABEL_SIZE_X, LABEL_SIZE_Y);
		this.add(p2Label);

		// Attach as listener so we will repaint when the modal changes
		model.attachListener(this);
	}

	/**
	 * Creates a ui.HoleComponent centered on a location
	 * @param id ID of new hole
	 * @param x X of center
	 * @param y Y of center
	 * @param size Size of hole (diameter)
	 * @return the created hole
	 */
	private HoleComponent createCenteredHole(int id, int x, int y, int size) {

		HoleComponent newHole = new HoleComponent(id, size, model);

		int actualX = x - (size / 2);
		int actualY = y - (size / 2);

		newHole.setLocation(actualX, actualY);

		return newHole;
	}
	
	/**
	 * Creates a ui.StoreComponent centered on a location
	 * @param id ID of new store
	 * @param x X of center
	 * @param y Y of center
	 * @param width Width of store
	 * @param height Height of store
	 * @return the created store
	 */
	private StoreComponent createCenteredStore(int id, int x, int y, int width, int height) {
		StoreComponent newStore = new StoreComponent(id, width, height, model);
		
		int actualX = x - (width / 2);
		int actualY = y - (height / 2);
		
		newStore.setLocation(actualX, actualY);
		
		return newStore;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void stateChanged(ChangeEvent e) {
		this.revalidate();
		this.repaint();
	}
}
