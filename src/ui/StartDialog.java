package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Greg on 11/28/2017.
 *
 * Dialog that is displayed before the game starts and asks players
 * to enter number of starting pieces in each hole (and eventually
 * the color/style as well)
 */
public class StartDialog extends JDialog {

	public StartDialog() {
		// Force on top
		this.setModal(true);
		this.setResizable(false);
		this.setTitle("Game Options");

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridheight = 4;
		c.gridwidth = 4;

		// Dialog title label
		JLabel titleLabel = new JLabel("Select your game options");

		// Starting pieces combo
		String[] startingOptions = {"3", "4"};
		JComboBox numPiecesCombo = new JComboBox(startingOptions);

		// Play button
		JButton playButton = new JButton("Play");

		// Add components
		panel.add(titleLabel);
		panel.add(numPiecesCombo);
		panel.add(playButton);
		this.add(panel);
		this.pack();
	}

}
