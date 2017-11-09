package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.GameModel;

/**
 * Created by Greg on 10/31/2017.
 */
public class HoleComponent extends JComponent {
	/**
	 * Holes are numbered like so:
	 * 0  1  2  3  4  5
	 * 6  7  8  9  10 11
	 */
	private final int id;
	private final GameModel model;
	public int count = 0;

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
	 * Draws the hole's circle
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawOval(0, 0, size - 1, size - 1); // add a buffer of 1 pixel so the edges don't get cut off
		g2d.drawString(Integer.toString(model.getCountOfHole(id)), size / 2, size / 2);
		g2d.drawString(Integer.toString(this.getId()), 0, 10);
	}

	public int getId() {
		return id;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(size, size);
	}
}
