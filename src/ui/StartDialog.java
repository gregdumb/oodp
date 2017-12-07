package ui;

import logic.GameModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Greg on 11/28/2017.
 *
 * Dialog that is displayed before the game starts and asks players
 * to enter number of starting pieces in each hole (and eventually
 * the color/style as well)
 */
public class StartDialog extends JDialog {

	private final int GAP = 10;

	public StartDialog(GameModel model) {
		// Force on top
		this.setModal(true);
		this.setResizable(false);
		this.setTitle("Game Options");

		JPanel panel = new JPanel(new BorderLayout(GAP, GAP));
		panel.setBorder(new EmptyBorder(GAP, GAP, GAP, GAP));

		// Dialog title label
		JLabel titleLabel = new JLabel("Select your game options");

		// Combo label
		JLabel comboLabel = new JLabel("Pieces/hole: ");
		comboLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		// Starting pieces combo
		String[] startingOptions = {"3", "4"};
		JComboBox numPiecesCombo = new JComboBox(startingOptions);

		// Style label
		JLabel styleLabel = new JLabel("Style: ");
		styleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		// Style combo
		String[] styles = {"Red", "Blue"};
		JComboBox styleCombo = new JComboBox(styles);
		
		// Play button
		JButton playButton = new JButton("Play");

		playButton.addActionListener(e -> {
			String selectedStr = (String) numPiecesCombo.getSelectedItem();
			int selectedInt = Integer.parseInt(selectedStr);
			model.initialize(selectedInt);
			this.setVisible(false);
		});
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
		
		// Num pieces row
		JPanel numPanel = new JPanel();
		numPanel.setLayout(new BoxLayout(numPanel, BoxLayout.LINE_AXIS));
		
		// Style row
		JPanel stylePanel = new JPanel();
		stylePanel.setLayout(new BoxLayout(stylePanel, BoxLayout.LINE_AXIS));
		
		// Add components
		panel.add(titleLabel, BorderLayout.PAGE_START);
		
		numPanel.add(comboLabel);
		numPanel.add(numPiecesCombo);
		inputPanel.add(numPanel);
		
		stylePanel.add(styleLabel);
		stylePanel.add(styleCombo);
		inputPanel.add(stylePanel);
		
		panel.add(inputPanel, BorderLayout.CENTER);
		
		panel.add(playButton, BorderLayout.PAGE_END);
		this.add(panel);
		this.pack();
	}

}
