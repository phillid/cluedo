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

/**
 * A graphical input panel for cluedo.Game
 * 
 * @author Hamish Brown
 * @author David Phillips
 *
 */
public class ControlPanel extends JPanel {
	private JLabel currentPlayerLabel;
	private JLabel movesRemainingLabel;
	private GuiClient guiClient;
	private Game game;
	
	public ControlPanel(GuiClient guiClient) {
		this.guiClient = guiClient;
		this.game = guiClient.getGame();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		this.setBorder(makeBorder());
		
		/* initialise labels and button */
		setupLabels();
		setupButtons();
		
		/* trigger a refresh to match current the game state */
		update();
	}
	
	/**
	 * Construct a border for the control panel using a BorderFactory
	 * @return the fresh border
	 */
	private Border makeBorder() {
		Border line = BorderFactory.createLineBorder(Color.BLACK, 2);
		Border margin = BorderFactory.createEmptyBorder(2,2,2,2);
		Border compound = BorderFactory.createCompoundBorder(line, margin);
		return compound;
	}
	
	/**
	 * Initialise the buttons to be displayed on the ControlPanel,
	 * setting default text values 
	 */
	public void setupButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		JButton accuseButton = new JButton("Accuse");
		JButton suggestButton = new JButton("Suggest");
		JButton cardsButton = new JButton("My Cards");
		
		suggestButton.addActionListener(e -> guiClient.makeSuggestion());
		accuseButton.addActionListener(e -> guiClient.makeAccusation());
		cardsButton.addActionListener(e -> guiClient.showHeldCards());
		
		buttonPanel.add(suggestButton);
		buttonPanel.add(accuseButton);
		buttonPanel.add(cardsButton);
		this.add(buttonPanel);
	}
	
	/**
	 * Initialise the labels to be displayed on the ControlPanel, setting
	 * their alignment and adding them to the panel itself
	 */
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

	/**
	 * Trigger an update of the elements on the ControlPanel according to
	 * the current game state
	 */
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
