package ui;

import java.util.HashSet;
import java.util.List;
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

public class GuiClient {
	private JFrame mainWindow;
	private BoardPanel boardPanel;
	private ControlPanel controlPanel;
	private Game game;
	
	public GuiClient() {
		mainWindow   = new JFrame();
		boardPanel   = new BoardPanel();
		controlPanel = new ControlPanel();
		
		mainWindow.add(boardPanel);
		mainWindow.add(controlPanel);
		mainWindow.pack();
		
		
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
		
		mainWindow.setVisible(true);
		
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new GuiClient();
	}
}
