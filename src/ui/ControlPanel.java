package ui;

import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;

import cluedo.Game;

public class ControlPanel extends JPanel {
	private JButton button;
	private JLabel currentPlayerLabel;
	private Game game;
	
	public ControlPanel(Game game) {
		this.game = game;
		button = new JButton("KCF");
		currentPlayerLabel = new JLabel("Current Player: "+game.getCurrentPlayer().getPlayerToken().getName());
		this.add(button);
		this.add(currentPlayerLabel);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}
}
