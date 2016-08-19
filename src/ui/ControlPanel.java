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
	private Game game;
	
	public ControlPanel(Game game) {
		this.game = game;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		
		//this.add(makeNavPanel());
		this.setBorder(makeBorder());
		
		/* initialise labels and button */
		setupLabels();
		setupButtons();
		
		/* trigger a refresh to match current the game state */
		update();
	}
	
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
	 * Make a panel of buttons for navigating in cardinal directions
	 * @return
	 */
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
