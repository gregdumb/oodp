import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Board Panel
 * JPanel that contains the circles and stuff for the mancala board
 */
public class BoardPanel extends JPanel
{
	/** Diameter of the holes */
	private final int HOLE_SIZE = 50;
	
	/** Distance from center of holes to edge of panel */
	private final int EDGE_PADDING = 100;
	
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
		this.setBackground(Color.CYAN); // Using ugly blue just to see where the board is on the screen
		
		// Calculate positioning
		// Size of board, not including padding
		int boardWidth = width - (2 * EDGE_PADDING);
		int boardHeight = height - (2 * EDGE_PADDING);
		// Distance between centers of holes
		int spacingX = boardWidth / (7); // 6 holes and 2 stores
		int spacingY = boardHeight;
		// Position where the first hole goes
		int holeStartX = EDGE_PADDING + spacingX;
		int holeStartY = EDGE_PADDING;
		// Position for center of right store
		int rightStoreX = width - EDGE_PADDING;
		int rightStoreY = height / 2;
		// Position for center of left store
		int leftStoreX = EDGE_PADDING;
		int leftStoreY = height / 2;
		// Dimensions of stores
		int storeWidth = HOLE_SIZE;
		int storeHeight = HOLE_SIZE + spacingY;
		
		// Create background
		//BackgroundComponent bg = new BackgroundComponent();
		//bg.setSize(width, height);
		//this.add(bg);
		
		// Create rows of holes
		for(int i = 0; i < 12; i++) {

			int row = (i / 6); // Will be 0 on first row, 1 on second row
			int column = (i % 6); // Will be 0-5 for each column

			int x = holeStartX + (column * spacingX);
			int y = holeStartY + (row * spacingY);

			HoleComponent hole = createCenteredHole(i, x, y, HOLE_SIZE);
			
			// This is temporary, but is demo of how we will handle mouse clicks
			hole.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					//super.mouseClicked(e);
					model.incrementHole(hole.getId());
					System.out.println("Clicked on " + hole.getId());
				}
			});
			
			model.attachListener(hole);
			
			this.add(hole);
		}
		
		// Create stores
		StoreComponent storeR = createCenteredStore(0, rightStoreX, rightStoreY, storeWidth, storeHeight);
		this.add(storeR);
		
		StoreComponent storeL = createCenteredStore(1, leftStoreX, leftStoreY, storeWidth, storeHeight);
		this.add(storeL);
	}

	/**
	 * Creates a HoleComponent centered on a location
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
	 * Creates a StoreComponent centered on a location
	 * @param id ID of new store
	 * @param x X of center
	 * @param y Y of center
	 * @param width Width of store
	 * @param height Height of store
	 * @return the created store
	 */
	private StoreComponent createCenteredStore(int id, int x, int y, int width, int height) {
		StoreComponent newStore = new StoreComponent(id, width, height);
		
		int actualX = x - (width / 2);
		int actualY = y - (height / 2);
		
		newStore.setLocation(actualX, actualY);
		
		return newStore;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
}
