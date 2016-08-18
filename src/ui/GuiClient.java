package ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.*;

import cluedo.Board;
import cluedo.Game;
import cluedo.Position;
import cluedo.cards.Card;
import cluedo.cards.Deck;
import cluedo.cards.RoomCard;
import cluedo.cell.Cell;
import cluedo.cell.Corridor;
import cluedo.cell.Doorway;
import cluedo.cell.Room;
import cluedo.token.PlayerToken;
import cluedo.token.Token;

public class GuiClient {
	private JFrame mainWindow;
	private BoardPanel boardPanel;
	private ControlPanel controlPanel;
	private Game game;
	
	public GuiClient() {
		int playerCount = 0;
		String input = "";
		do {
			try {
				input = JOptionPane.showInputDialog("How many players?");
				playerCount = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				/* input is null on cancel */
				if (input == null)
					System.exit(0);
				
				/* otherwise, just invalid input; go around */
				continue;
			}
		} while(playerCount < 3 || playerCount > 6);
		
		game = new Game(playerCount);
		for (int i = 0; i < playerCount; i++) {
			pickPlayer(i+1);
		}
		mainWindow   = new JFrame();
		JPanel contentPanel = new JPanel(new BorderLayout());
		boardPanel   = new BoardPanel(game);
		boardPanel.addMouseListener(new BoardMouse(boardPanel));
		controlPanel = new ControlPanel(game);
		
		mainWindow.setContentPane(contentPanel);
		contentPanel.add(boardPanel, BorderLayout.CENTER);
		contentPanel.add(controlPanel, BorderLayout.EAST);
		mainWindow.pack();
		
		mainWindow.setVisible(true);
		
		/* set the confirm close dialog (spec) */
		mainWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        mainWindow.addWindowListener(new WindowAdapter() {
        	/* capture window close event */
            public void windowClosing(WindowEvent ev) {
            	/* show confirmation dialog */
            	if (JOptionPane.showConfirmDialog(
            			mainWindow,
            			"Really exit?",
            			"Cluedo",
            			JOptionPane.YES_NO_OPTION
            		) == JOptionPane.YES_OPTION)
            		mainWindow.dispose();
            }
        });
	}
	
	public static void main(String[] args) {
		new GuiClient();
	}
	
	public void pickPlayer(int playerNumber) {
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel,BoxLayout.Y_AXIS));
		optionPanel.add(new JLabel("Player "+playerNumber+", Enter your name:"));
		JTextField nameField = new JTextField();
		optionPanel.add(nameField);
		optionPanel.add(new JLabel("Pick your token:"));
		ButtonGroup group = new ButtonGroup();
		Map<JRadioButton,PlayerToken> buttonMap = new HashMap<>();
		for (PlayerToken t : game.getAvailablePlayerTokens()) {
			JRadioButton b = new JRadioButton(t.getName());
			buttonMap.put(b, t);
			group.add(b);
			optionPanel.add(b);
		}
		PlayerToken chosenToken = null;
		while(chosenToken == null) {
			JOptionPane.showMessageDialog(null,optionPanel);
			for (Map.Entry<JRadioButton,PlayerToken> e : buttonMap.entrySet()) {
				if(e.getKey().isSelected()) {
					chosenToken = e.getValue();
					break;
				}
			}
			if (chosenToken == null) {
				JOptionPane.showMessageDialog(null, "Please choose a token!");
			}
		}
		game.addPlayer(nameField.getText(), chosenToken);
	}
}
