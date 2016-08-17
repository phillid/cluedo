package ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
		
		mainWindow   = new JFrame();
		JPanel contentPanel = new JPanel(new BorderLayout());
		boardPanel   = new BoardPanel(game);
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
}
