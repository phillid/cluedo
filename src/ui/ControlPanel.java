package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;

import cluedo.Game;

public class ControlPanel extends JPanel {
	private JButton button;
	private JLabel currentPlayerLabel;
	private JLabel movesRemainingLabel;
	private Game game;
	
	public ControlPanel(Game game) {
		this.game = game;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		setupInfo();
		this.add(makeNavPanel());
		this.setBorder(makeBorder());
	}
	
	private Border makeBorder() {
		Border line = BorderFactory.createLineBorder(Color.BLACK, 2);
		Border margin = BorderFactory.createEmptyBorder(2,2,2,2);
		Border compound = BorderFactory.createCompoundBorder(line, margin);
		return compound;
	}
	
	private void setupInfo() {
		if (game.getCurrentPlayer() != null) {
			currentPlayerLabel = new JLabel("Current Player: "+game.getCurrentPlayer().getPlayerToken().getName());
		} else {
			currentPlayerLabel = new JLabel("Game not started");
		}
		movesRemainingLabel = new JLabel(game.getRoll()+" moves remaining");
		currentPlayerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		movesRemainingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(currentPlayerLabel);
		this.add(movesRemainingLabel);
	}
	
	private JPanel makeNavPanel() {
		JPanel nav = new JPanel(new GridLayout(3,3));
		JButton northButton = new JButton("N");
		JButton eastButton = new JButton("E");
		JButton southButton = new JButton("S");
		JButton westButton = new JButton("W");
		nav.add(new Container());
		nav.add(northButton);
		nav.add(new Container());
		nav.add(westButton);
		nav.add(new Container());
		nav.add(eastButton);
		nav.add(new Container());
		nav.add(southButton);
		nav.add(new Container());
		nav.setMaximumSize(new Dimension(150,150));
		return nav;
	}
}
