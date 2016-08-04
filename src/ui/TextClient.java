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
		game.start();

		Scanner in = new Scanner(System.in);
		/*System.out.println("How many players?");
		int players = in.nextInt();*/
		playing = true;
		while (playing) {
			showBoard(game.board);
			System.out.println("Playing as "+game.getCurrentPlayer().getPlayerToken().getName());
			game.roll();
			System.out.println("You roll "+game.getRoll());
			
			/* FIXME off-by one error/??? */
			while (game.canMove()) {
				System.out.println(game.getRoll()+" moves left (n|e|s|w)");
				String command;
				do {
					command = in.next();
					command = command.toLowerCase();
				} while (!game.move(command));
				showBoard(game.board);
			}
			
			if (game.playerIsInRoom()) {
				/* FIXME get room name! */
				System.out.println("You are in a a room FIXME, make a suggestion?");
				throw new RuntimeException("Not yet implemented");
			}
			
			
			/*
			if (game.envelopeMatches(suggestion)) {
				System.out.println("You are winner!");
				playing = false;
			}*/
		}
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
					ch = 'D';
				System.out.print(ch);
			}
			System.out.print('\n');
		}
	}
}
