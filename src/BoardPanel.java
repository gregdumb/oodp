import javax.swing.*;
import java.awt.*;

/**
 * Board Panel
 * JPanel that contains the circles and stuff for the mancala board
 */
public class BoardPanel extends JPanel
{
	private final int HOLE_SIZE = 50;
	private final int HOLE_SPACING = 100;

	public BoardPanel() {

		// We want to use absolute positioning, no layout manager
		this.setLayout(null);

		// First row of holes
		for(int i = 0; i < 12; i++) {

			int row = (i / 6) + 1; // Will be 1 on first row, 2 on second row
			int column = (i % 6); // Will be 0-5 for each row
			System.out.println(Integer.toString(column));

			int x = (column * HOLE_SPACING) + 100; // Buffer of 100 on left side
			int y = row * 100; // Buffer of 100 on top, 200 for bottom row

			HoleComponent hole = createCenteredHole(i, x, y, HOLE_SIZE);

			this.add(hole);
		}
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
}
