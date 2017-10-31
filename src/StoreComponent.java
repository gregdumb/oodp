import javax.swing.*;
import java.awt.*;

/**
 * Created by Greg on 10/31/2017.
 */
public class StoreComponent extends JComponent
{
	private int id;
	private int width;
	private int height;
	
	public StoreComponent(int id, int width, int height) {
	
		this.id = id;
		this.width = width;
		this.height = height;
	
		this.setSize(width, height);
	}
	
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawRoundRect(0, 0, width-1, height-1, width/2, width/2);
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
}
