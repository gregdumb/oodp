import javax.swing.*;
import java.awt.*;

/**
 * Created by Greg on 10/31/2017.
 * CURRENTLY IS NOT WORKING
 */
public class BackgroundComponent extends JComponent
{
	public BackgroundComponent() {
	
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.CYAN);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
