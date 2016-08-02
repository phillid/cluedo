package ui;

import cluedo.Board;
import cluedo.Game;
import cluedo.cell.Cell;
import cluedo.cell.Corridor;
import cluedo.cell.Doorway;
import cluedo.cell.Room;

public class TextClient {

	public TextClient() {
		Game game = new Game();
		game.start();

		showBoard(game.board);
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
