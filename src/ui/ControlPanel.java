package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;

import cluedo.Game;
import cluedo.Player;

public class ControlPanel extends JPanel {
	private JButton button;
	private JLabel currentPlayerLabel;
	private JLabel movesRemainingLabel;
	private Game game;
	
	public ControlPanel(Game game) {
		this.game = game;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		this.add(makeNavPanel());
		this.setBorder(makeBorder());
		
		/* initialise labels */
		setupLabels();
		
		/* set the label texts */
		update();
	}
	
	private Border makeBorder() {
		Border line = BorderFactory.createLineBorder(Color.BLACK, 2);
		Border margin = BorderFactory.createEmptyBorder(2,2,2,2);
		Border compound = BorderFactory.createCompoundBorder(line, margin);
		return compound;
	}
	
	public void setupLabels() {
		currentPlayerLabel = new JLabel();
		movesRemainingLabel = new JLabel();
		this.add(currentPlayerLabel);
		this.add(movesRemainingLabel);
		currentPlayerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		movesRemainingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);	
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
	
	public void update() {
		Player currentPlayer = game.getCurrentPlayer();
		if (currentPlayer != null) {
			currentPlayerLabel.setText(
					"Current Player: "+currentPlayer.getPlayerToken().getName()
					+ "("+currentPlayer.getName()+")");
			movesRemainingLabel.setText(game.getRoll()+" moves remaining");
		} else {
			movesRemainingLabel.setText("");
			currentPlayerLabel.setText("Game not started");
		}
	}
}
