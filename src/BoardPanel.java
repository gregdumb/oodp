import javax.swing.*;
import java.awt.*;

/**
 * Created by Greg on 10/31/2017.
 */
public class BoardPanel extends JPanel
{
	private final int HOLE_SIZE = 50;
	private final int HOLE_SPACING = 100;

	public BoardPanel() {

		for(int i = 0; i < 6; i++) {
			this.setLayout(null);

			HoleComponent hole = new HoleComponent(i, 50);
			hole.setLocation(100 * i, 100);

			this.add(hole);
		}

	}
}
