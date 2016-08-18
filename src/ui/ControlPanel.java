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
	private JLabel currentPlayerLabel;
	private JLabel movesRemainingLabel;
	private Game game;
	
	public ControlPanel(Game game) {
		this.game = game;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		//this.add(makeNavPanel());
		this.setBorder(makeBorder());
		
		/* initialise labels */
		setupLabels();
		this.add(new JButton("Accuse"));
		this.add(new JButton("Suggest"));
		
		/* set the label texts */
		update();
	}
	
	private Border makeBorder() {
		Border line = BorderFactory.createLineBorder(Color.BLACK, 2);
		Border margin = BorderFactory.createEmptyBorder(2,2,2,2);
		Border compound = BorderFactory.createCompoundBorder(line, margin);
		return compound;
	}
	
	public void setupButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		JButton accuseButton = new JButton("Accuse");
		JButton suggestButton = new JButton("Suggest");
		JButton cardsButton = new JButton("My Cards");
		buttonPanel.add(suggestButton);
		buttonPanel.add(accuseButton);
		buttonPanel.add(cardsButton);
		this.add(buttonPanel);
		//buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
		//accuseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		//suggestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	public void setupLabels() {
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel,BoxLayout.Y_AXIS));
		currentPlayerLabel = new JLabel();
		movesRemainingLabel = new JLabel();
		labelPanel.add(currentPlayerLabel);
		labelPanel.add(movesRemainingLabel);
		this.add(labelPanel);
		labelPanel.setAlignmentX(LEFT_ALIGNMENT);
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
