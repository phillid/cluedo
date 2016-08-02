package cluedo;


public class Board {
	private Cell[][] cells;
	public Board(Cell[][] cells) {
		this.cells = cells;
	}

	public Cell getCellAt(int x, int y) {
		return cells[x][y];
	}
}
