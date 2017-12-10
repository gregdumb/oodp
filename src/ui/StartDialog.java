package ui;

import logic.GameModel;
import styles.BoardStyle;
import styles.OceanStyle;
import styles.SunshineStyle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Greg Brisebois
 * @version 1.0
 *
 * Dialog that is displayed before the game starts and asks players
 * to enter number of starting pieces in each hole and the color/
 * style
 */
public class StartDialog extends JDialog {

	/** Padding around the edges */
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
		String[] startingOptions = {"4", "3"};
		JComboBox numPiecesCombo = new JComboBox(startingOptions);

		// Style label
		JLabel styleLabel = new JLabel("Style: ");
		styleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		// Style combo
		String[] styles = {"Ocean", "Sunshine"};
		JComboBox styleCombo = new JComboBox(styles);
		
		// Play button
		JButton playButton = new JButton("Play");

		// Play button event
		playButton.addActionListener(e -> {
			String selectedStr = (String) numPiecesCombo.getSelectedItem();
			int selectedInt = Integer.parseInt(selectedStr);
			
			String styleStr = (String) styleCombo.getSelectedItem();
			BoardStyle style = (styleStr.equals("Ocean")) ? new OceanStyle() : new SunshineStyle();
			
			model.initialize(selectedInt, style);
			this.setVisible(false);
		});
		
		// Inputs section
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
		//
		numPanel.add(comboLabel);
		numPanel.add(numPiecesCombo);
		inputPanel.add(numPanel);
		//
		stylePanel.add(styleLabel);
		stylePanel.add(styleCombo);
		inputPanel.add(stylePanel);
		//
		panel.add(inputPanel, BorderLayout.CENTER);
		panel.add(playButton, BorderLayout.PAGE_END);
		this.add(panel);
		this.pack();
	}

}
