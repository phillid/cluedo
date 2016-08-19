package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

import cluedo.Game;
import cluedo.Player;
import cluedo.cards.Card;
import cluedo.cards.Deck;
import cluedo.cards.RoomCard;
import cluedo.cell.Cell;
import cluedo.cell.Room;
import cluedo.token.PlayerToken;

public class GuiClient {
	private JMenuBar menuBar;
	private JFrame mainWindow;
	private BoardPanel boardPanel;
	private ControlPanel controlPanel;
	private Game game;
	
	public GuiClient() {
		int playerCount;
		
		/* ask the user the number of players to play with */
		try {
			playerCount = getInt("How many players?", 3, 6);
		} catch(EmptyInputError e) {
			/* exit on cancel */
			return;
		}
		
		/* construct game and populate it with players */
		game = new Game(playerCount);
		for (int i = 0; i < playerCount; i++) {
			pickPlayer(i+1);
		}
		
		JPanel contentPanel = new JPanel(new BorderLayout());
		controlPanel = new ControlPanel(this);
		boardPanel   = new BoardPanel(this);
		boardPanel.addMouseListener(new BoardMouse(this));
		
		setupMainWindow();
		setupMenuBar();
		
		mainWindow.setContentPane(contentPanel);
		mainWindow.pack();
		contentPanel.add(boardPanel, BorderLayout.CENTER);
		contentPanel.add(controlPanel, BorderLayout.EAST);
		
        /* start the game */
        mainWindow.setVisible(true);
		game.start();
		game.roll();
		update();
		
	}
	
	/**
	 * Set up the main window JFrame with its associated close actions etc
	 */
	private void setupMainWindow() {
		mainWindow   = new JFrame();
		
		/* set the confirm close dialog (spec) */
		mainWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        mainWindow.addWindowListener(new WindowAdapter() {
        	/* capture window close event */
        	@Override
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
	
	/**
	 * Initialise the menu bar and its associated menus
	 */
	private void setupMenuBar() {
		menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_G);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(e -> mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING)));
		
		mainWindow.setJMenuBar(menuBar);
		menuBar.add(menu);
		menu.add(exitItem);
	}
	
	/**
	 * Show a dialog asking the user a question and taking an integer
	 * between specified bounds. Repeats the question until the input
	 * is an integer within the bounds. If the user cancels, an exception
	 * is thrown
	 * @param prompt -- question to ask the user
	 * @param min -- inclusive minimum value
	 * @param max -- inclusive maximum value
	 * @return integer user entered 
	 */
	protected int getInt(String prompt, int min, int max) throws EmptyInputError {
		int inputInt = 0;
		String input = "";
		do {
			try {
				input = JOptionPane.showInputDialog("How many players?");
				inputInt = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				/* input is null on cancel */
				if (input == null)
					throw new EmptyInputError();
				
				/* otherwise, just invalid input; go around */
				continue;
			}
		} while(inputInt < 3 || inputInt > 6);
		return inputInt;
	}

	public static void main(String[] args) {
		new GuiClient();
	}
	
	/**
	 * Show a dialog to get a player's name and token, adding a new player to
	 * the Game on completion
	 * @param playerNumber
	 */
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
	
	/**
	 * Show a dialog box with the current player's held cards listed
	 */
	public void showHeldCards() {
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new BoxLayout(cardsPanel,BoxLayout.Y_AXIS));
		
		Player player = game.getCurrentPlayer();
		String playerName = player.getName();
		
		/* fall back on token name if player name not appropriate */
		if (playerName == null || playerName.length() == 0)
			playerName = player.getPlayerToken().getName();

		cardsPanel.add(new JLabel(playerName+"'s cards:"));
		for (Card card : game.getCurrentPlayer().getHeldCards()) {
			cardsPanel.add(new JLabel(card.getName()));
		}
		
		
		JOptionPane.showMessageDialog(null, cardsPanel);
	}
	
	public void makeSuggestion() {
		Cell currCell = game.getCurrentPlayerCell();
		if (!(currCell instanceof Room)) {
			JOptionPane.showMessageDialog(null, "You must be in a room to make a suggestion",null,JOptionPane.ERROR_MESSAGE);
		}
		Room currRoom = (Room)currCell;
		
		JPanel contPanel = new JPanel(new GridLayout(0,1));
		
		ListCellRenderer<Card> renderer = (list,value,index,isSelected,cellHasFocus) -> new JLabel((value.getName()));
		
		JComboBox<Card> weaponBox = new JComboBox<Card>(Deck.weaponCards.toArray(new Card[0]));
		weaponBox.setRenderer(renderer);
		JComboBox<Card> playerBox = new JComboBox<Card>(Deck.playerCards.toArray(new Card[0]));
		playerBox.setRenderer(renderer);
		JLabel weaponLabel = new JLabel("Choose a weapon:");
		JLabel playerLabel = new JLabel("Choose a murderer:");
		JLabel roomLabel = new JLabel("In the "+currRoom.getName());
		
		
		contPanel.add(weaponLabel);
		contPanel.add(weaponBox);
		contPanel.add(playerLabel);
		contPanel.add(playerBox);
		contPanel.add(roomLabel);
		
		JOptionPane.showMessageDialog(null, contPanel,"Suggestion",JOptionPane.QUESTION_MESSAGE);
		
		Set<Card> suggestion = new HashSet<>();
		suggestion.add((Card)weaponBox.getSelectedItem());
		suggestion.add((Card)playerBox.getSelectedItem());
		
		suggestion.add(new RoomCard(currRoom.getName()));
		
		if (game.suggest(suggestion)) {
			JOptionPane.showMessageDialog(null, "The suggestion was valid!");
		} else {
			if (game.getEvidence() == null)
				JOptionPane.showMessageDialog(null, "The suggestion was disallowed by game rules");
			else
				JOptionPane.showMessageDialog(null, "The suggestion was refuted becuase a player had "+game.getEvidence().getName());	
		}
		game.nextPlayer();
		game.roll();
		boardPanel.update();
		controlPanel.update();
		
	}
	
	
	
	public void update() {
		boardPanel.update();
		controlPanel.update();
	}
	
	/**
	 * Get the board panel object on the client
	 * @return
	 */
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	/**
	 * Get the game object of the client
	 * @return
	 */
	public Game getGame() {
		return game;
	}
}
