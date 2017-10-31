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
	private final int HOLE_SIZE = 50;
	private final int HOLE_SPACING = 100;
	
	private final int EDGE_PADDING = 50;
	
	private int width;
	private int height;

	public BoardPanel(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		// We want to use absolute positioning, no layout manager
		this.setLayout(null);
		this.setPreferredSize(new Dimension(width, height));
		
		// Calculate position & dimensions
		int boardWidth = width - (2 * EDGE_PADDING);
		int boardHeight = height - (2 * EDGE_PADDING);
		
		int spacingX = boardWidth / (6 + 2); // 6 holes and 2 stores
		int spacingY = boardHeight;
		
		int holeStartX = EDGE_PADDING + spacingX;
		int holeStartY = EDGE_PADDING;
		
		int rightStoreX = boardWidth - EDGE_PADDING;
		int rightStoreY = height / 2;
		
		int leftStoreX = EDGE_PADDING;
		int leftStoreY = height / 2;
		
		int storeWidth = HOLE_SIZE;
		int storeHeight = HOLE_SIZE + spacingY;
		
		// Create rows of holes
		for(int i = 0; i < 12; i++) {

			int row = (i / 6); // Will be 0 on first row, 1 on second row
			int column = (i % 6); // Will be 0-5 for each column

			int x = holeStartX + (column * spacingX);
			int y = holeStartY + (row * spacingY);

			HoleComponent hole = createCenteredHole(i, x, y, HOLE_SIZE);
			
			hole.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					//super.mouseClicked(e);
					System.out.println("Clicked on " + hole.getId());
				}
			});
			
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

		HoleComponent newHole = new HoleComponent(id, size);

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
