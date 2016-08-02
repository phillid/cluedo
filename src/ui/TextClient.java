package ui;

import cluedo.Board;
import cluedo.Cell;
import cluedo.Corridor;
import cluedo.Doorway;
import cluedo.Game;
import cluedo.Room;

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
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 25; x++) {
				Cell cell = b.getCellAt(x,y);
				char ch = ' ';
				if (cell == null)
					ch = ' ';
				else if (cell instanceof Corridor)
					ch = '.';
				else if (cell instanceof Room)
					ch = 'R';
				else if (cell instanceof Doorway)
					ch = 'D';
				System.out.print(ch);
			}
			System.out.print('\n');
		}
	}
}
