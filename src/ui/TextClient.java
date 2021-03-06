package ui;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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


/**
 * Text-based user interface for a cluedo.Game
 * Provides a simple text-based, but visual mode of gameplay
 * Draws a symbolic-text board and provides text prompts and
 * lists of choices.
 * 
 * @author David Phillips
 * @author Hamish Brown
 *
 */
public class TextClient {

	public TextClient() {
		/* scanner for user input on stdin */
		Scanner in = new Scanner(System.in);
		
		/* ask for the desired player count between 3 and 6 inclusive */
		int playerCount = getInt(in, "How many players?", 3, 6);
		
		/* create game, ask for player names tokens, add them */
		Game game = new Game(playerCount);
		addPlayers(in, playerCount, game);
		
		/* kick the game off */
		game.start();
		while (game.isPlaying()) {
			/* start the corridor input loop */
			corridorLoop(game, in);
			
			/* start the room input loop if player has travelled inside a room yet */
			if (game.playerIsInRoom()) {
				roomLoop(game, in);
			}
			
			/* turn is over; go to the next player */
			game.nextPlayer();
		}
		in.close();
	}

	public void addPlayers(Scanner scan, int playerCount, Game game) {
		for (int i = 0; i < playerCount; i++) {
			int tokIndex = 0;
			System.out.println("Player #"+(i+1)+", what is your name?");
			String name = scan.next();
			
			List<PlayerToken> avail = game.getAvailablePlayerTokens();
			for (PlayerToken pt : avail)
				System.out.println("\t"+(++tokIndex)+"\t"+pt.getName());
			
			int choice = getInt(scan, "Which token?", 1, avail.size());
			PlayerToken token = avail.get(choice-1);
			
			game.addPlayer(name, token);
		}
	}
	
	public static void main(String[] args) throws Throwable {
		new TextClient();
	}
	
	public void showBoard(Board b) {
		System.out.println("\n\n\n\n");
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
		System.out.println();
		/* beautifully complicated: build the room description */  
		for (int i = 0; i < b.getRooms().length; i++) {
			Room r = b.getRooms()[i];
			String description = "\tRoom "+i+" is the "+r.getName();
			if (r.isOccupied() || r.getWeapons().size() > 0)
				description += ", containing ";
			
			if (r.isOccupied()) {
				description += r.getOccupants();
			}
			if (r.getWeapons().size() > 0) {
				description += " " + r.getWeapons();
			}
			System.out.println(description);
		}
		System.out.println();
	}
	
	private void corridorLoop(Game game, Scanner in) {
		showBoard(game.getBoard());
		System.out.println("Playing as "+game.getCurrentPlayer().getPlayerToken().getName());
		game.roll();
		System.out.println("You roll "+game.getRoll());
		
		while (game.canMove() && game.isPlaying()) {
			System.out.println(game.getRoll()+" moves left. Commands: [n|e|s|w|cards|board|accuse]");
			String command;
			boolean turnRunning = true;
			while(turnRunning) {
				command = in.next();
				command = command.toLowerCase();
				switch(command) {
				case "cards":
					showHeld(game);
					break;
				case "accuse":
					makeAccusation(game, in);
					turnRunning = false;
					break;
				case "board":
					showBoard(game.getBoard());
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
			/* show board only if game still running (after eg accusation) */
			if (game.isPlaying())
				showBoard(game.getBoard());
		}	
	}
	
	/**
	 * Show the cards the current player is holding
	 * @param game
	 */
	private void showHeld(Game game) { 
		System.out.println("You're holding:" + game.getCurrentPlayer().getHeldCards());
	}
	
	/**
	 * Loop that runs while the current player is inside a room
	 * @param game
	 * @param in
	 */
	private void roomLoop(Game game, Scanner in) {
		while (game.playerIsInRoom()) {
			Room room = (Room)game.getCurrentPlayerCell();
			String roomName = room.getName();
			List<Cell> exits = room.getNeighbours();
			
			String suggestCommand = "";
			if (game.canSuggest())
				suggestCommand = "[suggest]";
			showBoard(game.getBoard());
			
			String exitCommands = "";
			String secretCommands = "";
			for (int i = 0; i < exits.size(); i++) {
				Cell exit = exits.get(i);
				String exitName, exitType;
				if (exit instanceof Doorway) {
					exitType = ((Doorway)exit).getOppositeDirection().name();
				} else {
					/* must be room -> secret passage*/
					exitType = "secret passage to " +((Room)exit).getName();
				}
				exitName = String.format("[%c (%s)]", 'a'+i, exitType);
				if (i != 0)
					exitCommands += ", ";
				exitCommands += exitName; 
			}
			
	
	
			String command = "";
			showHeld(game);
			System.out.println("You are in the "+roomName+", [accuse] "+suggestCommand);
			System.out.println("Exits are "+exitCommands);
			if (secretCommands.length() > 0)
				System.out.println(secretCommands);
			command = in.next();
			processRoomCommand(game,command,in);
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
				if (n >= min && n <= max)
					return n;
				System.out.println("Input must be from "+min+" to "+max);
			}
			/* eat non-integer foo */
			if (in.hasNext())
				in.next();
		}
	}
	
	/**
	 * Attempt an accusation on the current player
	 * @param game
	 * @param in
	 */
	private void makeAccusation(Game game, Scanner in) {
		Set<Card> accusation = constructCandidateEnvelope(in);
		System.out.println("Your accusation: "+accusation);
		if (game.accuse(accusation)) {
			System.out.println("You're winner!");
		} else {
			System.out.println("Accusation is incorrect! Sit out");
			System.out.println("Press return key to continue");
			waitForKey();
			game.roll();
			game.nextPlayer();
		}
	}
	
	/**
	 * Wait for return key
	 */
	public void waitForKey() {
		try { System.in.read(); } catch (IOException e){};	
	}
	
	/**
	 * Make a suggestion
	 * @param game
	 * @param in
	 */
	private void makeSuggestion(Game game, Scanner in) {
		if (!game.canSuggest()) {
			System.out.println("Can't make a suggestion now");
			return;
		}
		Set<Card> suggestion = constructCandidateEnvelope(in);
		Position playerPosition = game.getCurrentPlayer().getPlayerToken().getPosition();
		Cell playerCell = game.getBoard().getCellAt(playerPosition);
		
		for (Card c : suggestion) {
			if (c instanceof RoomCard) {
				Card other = new RoomCard(((Room)playerCell).getName());
				if (!c.equals(other)) {
					System.out.println("Error: You must be in the same room as your suggested room!");
					waitForKey();
					return;
				}
			}
		}
		
		System.out.println("Your suggestion: "+suggestion);
		if (game.suggest(suggestion)) {
			System.out.println("You're winner!");
			System.exit(0);
		} else {
			System.out.println("Suggestion refuted. Player had counter-evidence: "+game.getEvidence());
			System.out.println("Press return key to continue");
			waitForKey();
			game.roll();
			game.nextPlayer();
		}
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
		System.err.println("processing room command");
		Room room = (Room)game.getCurrentPlayerCell();
		command = command.toLowerCase();
		switch (command) {
		case "exit":
		case "accuse":
			makeAccusation(game, in);
			break;
		case "suggest":
			makeSuggestion(game, in);
			break;
			
		default:
			if (command.length() != 1) { //not a room exit
				System.out.println("Invalid command");
				return false;
			}
			char exitLetter = command.charAt(0);
			int exit = exitLetter - 'a';
			if (exit < 0 || exit >= room.getNeighbours().size()) {
				return false;
			}
			
			game.exitRoom(room,room.getNeighbours().get(exit));
			
			return true;
		}
		return false;
	}
}
