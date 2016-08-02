package cluedo;


public class Board {
	private Cell[][] cells;
	public Board(Cell[][] cells) {
		this.cells = cells;
	}
	public void show() {
		/* FIXME show the board on stdout */
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 25; x++) {
				Cell cell = cells[x][y];
				char ch = ' ';
				if (cell == null)
					ch = '#';
				else if (cell instanceof Corridor)
					ch = '_';
				else if (cell instanceof Room)
					ch = 'R';
				System.out.print(ch);
			}
			System.out.print('\n');
		}
	}
}
