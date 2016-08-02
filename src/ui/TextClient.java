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
