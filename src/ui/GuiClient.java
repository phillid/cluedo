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
		
		mainWindow.add(boardPanel, controlPanel);
		mainWindow.pack();
		
		game = new Game(-5);
	}
	
	public static void main(String[] args) {
		new GuiClient();
	}
}
