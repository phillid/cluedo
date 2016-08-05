package ui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import cluedo.Board;
import cluedo.Game;
import cluedo.cards.Card;
import cluedo.cards.Deck;
import cluedo.cell.Cell;
import cluedo.cell.Corridor;
import cluedo.cell.Doorway;
import cluedo.cell.Doorway.Direction;
import cluedo.cell.Room;

public class TextClient {

	public TextClient() {
		boolean playing = false;
		
		Set<Card> suggestion;
		Game game = new Game();

		Scanner in = new Scanner(System.in);
		/*System.out.println("How many players?");
		int players = in.nextInt();*/
		playing = true;
		while (playing) {
			corridorLoop(game, in);
			if (game.playerIsInRoom()) {
				roomLoop(game, in);
			}
			
			//temp. removed for debugging
			//game.nextPlayer();
			
			
			/* FIXME do suggestions and envelope thingy construction
			 * automatic checking and all sorts of bullshit like that */
		}
		in.close();
		/* roughing of what we should(?) do
		 *
		 * Make scanner and stuff
		 * loop while game is in motion
		 * show current player and stuff
		 * read commands and foo, maybe:
		 * 	north, south, east, west, suggest, cards, map
		 * parse that shit with a scanner. Piss easy m9.
		 * If a suggestion:
		 *	move all the shit into that room
		 *  Temporarily go to next player and ask to refute it
		 *  if cannot refute, choose next player to ask to refute
		 *  if cannot refute and run out of playes? WIN!
		 * refuted? yay.
		 * set working player to next (or first) player
		 *
		 */
	}

	public static void main(String[] args) throws Throwable {
		new TextClient();
	}
	
	public void showBoard(Board b) {
		for (int y = 0; y < b.getHeight(); y++) {
			System.out.print("\t");
			for (int x = 0; x < b.getWidth(); x++) {
				Cell cell = b.getCellAt(x,y);
				char ch = ' ';
				if (cell == null)
					ch = ' ';
				else if (cell instanceof Corridor)
					if (cell.isOccupied())
						ch = ((Corridor)cell).getOccupant().getInitial();
					else
						ch = '.';
				else if (cell instanceof Room)
					ch = (char) ('0'+((Room)cell).getRoomNumber());
				else if (cell instanceof Doorway)
					switch(((Doorway)cell).getDirection()) {
					case NORTH:
						ch = '^';
						break;
					case EAST:
						ch = '>';
						break;
					case SOUTH:
						ch = 'v';
						break;
					case WEST:
						ch = '<';
						break;
					}
				System.out.print(ch);
			}
			System.out.print('\n');
		}
		for (Room r : b.getRooms()) {
			if (r.isOccupied() || r.getWeapons().size() > 0) {
				System.out.println("\n\tIn the " + r.getName() + ":" + r.getOccupants() + " " + r.getWeapons());
			}
		}
	}
	
	private void corridorLoop(Game game, Scanner in) {
		showBoard(game.board);
		System.out.println("Playing as "+game.getCurrentPlayer().getPlayerToken().getName());
		game.roll();
		System.out.println("You roll "+game.getRoll());
		
		while (game.canMove()) {
			System.out.println(game.getRoll()+" moves left (n|e|s|w|cards|board)");
			String command;
			boolean turnRunning = true;
			while(turnRunning) {
				command = in.next();
				command = command.toLowerCase();
				switch(command) {
				case "cards":
					showHeld(game);
					break;
				case "board":
					showBoard(game.board);
					break;
				case "n":
				case "e":
				case "s":
				case "w":
					if (game.move(command)) {
						turnRunning = false;
					} else {
						System.out.println("You cannot move in that direction");
					}
					break;
				default:
					System.out.println("Invalid command");
					break;
				}
			}
			showBoard(game.board);
		}	
	}
	
	private void showHeld(Game game) { 
		System.out.println("You're holding:" + game.getCurrentPlayer().getHeldCards());
	}
	
	/**
	 * Loop that runs while the current player is inside a room
	 * @param game
	 * @param in
	 */
	private void roomLoop(Game game, Scanner in) {
		Room room = (Room)game.getCurrentPlayerCell();
		String roomName = room.getName();
		List<Cell> exits = room.getNeighbours();
		
		String suggestCommand = "";
		if (game.canSuggest())
			suggestCommand = "[suggest]";
		showBoard(game.board);
		
		String exitCommands = "";
		String secretCommands = "";
		for (int i = 0; i < exits.size(); i++) {
			String exitName = String.format("[%c]", 'a'+i);
			if (i != 0)
				exitCommands += ", ";
			exitCommands += exitName;
			if (exits.get(i) instanceof Room)
				secretCommands = exitName + " is passage to "+((Room)exits.get(i)).getName(); /* FIXME only one secret passage with this */ 
		}
		
		String command = "";
		while (!command.equals("accuse")) {
			showHeld(game);
			System.out.println("You are in the "+roomName+", [accuse] "+suggestCommand);
			System.out.println("Exits are "+exitCommands);
			if (secretCommands.length() > 0)
				System.out.println(secretCommands);
			command = in.next();
			if (command.equals("suggest") && game.canSuggest())
				break;
		}
		
		switch (command) {
		case "accuse":
			makeAccusation(game, in);
			break;
		case "suggest":
			makeSuggestion(game, in);
			break;
		}
	}

	/**
	 * get an integer from the scanner
	 * @param in
	 * @param prompt
	 * @param min
	 * @param max
	 * @return
	 */
	private int getInt(Scanner in, String prompt, int min, int max) {
		int n = min-1;
		while (true) {
			System.out.println(prompt);
			if (in.hasNextInt()) {
				n = in.nextInt();
				if (n >= min || n <= max)
					return n;
			}
		}
	}
	
	/**
	 * Attempt an accusation on the current player
	 * @param game
	 * @param in
	 */
	private void makeAccusation(Game game, Scanner in) {
		Set<Card> accusation = constructCandidateEnvelope(in);
		System.out.println(accusation);
		if (game.accuse(accusation)) {
			System.out.println("You're winner!");
			System.exit(0);
		} else {
			System.out.println("Accusation is incorrect! Sit out");
			System.out.println("Press return key to continue");
			in.next();
		}
	}
	
	/**
	 * Make a suggestion
	 * @param game
	 * @param in
	 */
	private void makeSuggestion(Game game, Scanner in) {
		throw new RuntimeException("Not implemented");
	}

	/**
	 * Helper method for constructing an envelope-type Set for accusations or suggestions
	 * @param in
	 * @return
	 */
	private Set<Card> constructCandidateEnvelope(Scanner in) {
		Set<Card> envelope = new HashSet<Card>();
		List<Card> people = Deck.playerCards;
		List<Card> weapons = Deck.weaponCards;
		List<Card> rooms = Deck.roomCards;
		
		
		System.out.println("Murderer:");
		for (int i = 0; i < people.size(); i++)
			System.out.println("\t"+(i+1)+" "+people.get(i).getName());
		int index = getInt(in, "Murder #?", 1, people.size()) - 1;
		envelope.add(people.get(index));
		
		System.out.println("Location:");
		for (int i = 0; i < rooms.size(); i++)
			System.out.println("\t"+(i+1)+" "+rooms.get(i).getName());
		index = getInt(in, "Murder room #?", 1, rooms.size()) - 1;
		envelope.add(rooms.get(index));
		

		System.out.println("Weapon:");
		for (int i = 0; i < weapons.size(); i++)
			System.out.println("\t"+(i+1)+" "+weapons.get(i).getName());
		index = getInt(in, "Murder weapon #?", 1, weapons.size()) - 1;
		envelope.add(weapons.get(index));
		
		return envelope;
	}

	private boolean processRoomCommand(Game game, String command, Scanner in) {
		Room room = (Room)game.getCurrentPlayerCell();
		command = command.toLowerCase();
		switch (command) {
		case "exit":
			if (!in.hasNextInt()) {
				System.out.println("Invalid exit");
				return false;
			}
			int exit = in.nextInt() - 1;
			if (exit < 0 || exit >= room.getNeighbours().size())
				return false;
			
			
			return true;
		default:
			System.out.println("Invalid command");
			return true;
		}
	}
}
