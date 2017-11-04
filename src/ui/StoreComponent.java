package ui;

import logic.GameModel;

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
	
	private GameModel model;
	
	public StoreComponent(int id, int width, int height, GameModel model) {
	
		this.id = id;
		this.width = width;
		this.height = height;
		
		this.model = model;
		
		this.setSize(width, height);
	}
	
	protected void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawRoundRect(0, 0, width-1, height-1, width/2, width/2);
		
		g2d.drawString(Integer.toString(model.getCountOfHole(id)), width/2, height/2);
		g2d.drawString(Integer.toString(this.id), 0, 10);
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
}
