package ui;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import cluedo.Board;
import cluedo.Game;
import cluedo.cards.Card;
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
						System.out.println("You're holding:" + game.getCurrentPlayer().getHeldCards());
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
			if (game.playerIsInRoom()) {
				String roomName = ((Room)game.getCurrentPlayerCell()).getName();
				/* FIXME only show suggest if Game.canSuggest() is true */
				System.out.println("You are in the "+roomName+", (Exits: [1|2|3|4] | [suggest|accuse])");
				throw new RuntimeException("Not yet implemented");
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
}
