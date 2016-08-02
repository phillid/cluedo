package cluedo;


import java.util.HashMap;
import java.util.Map;

import cluedo.cell.Cell;


/**
 * Represents the game board
 */
public class Board {
	Map<Character, Cell> startingPositions = new HashMap<Character, Cell>();

	private Cell[][] cells;
	public Board(Cell[][] cells) {
		this.cells = cells;
	}

	public Cell addStartingPosition(Character ch, Cell cell) {
		return startingPositions.put(ch, cell);
	}

	public Map<Character, Cell> getStartingPositions() {
		return new HashMap<Character, Cell>(startingPositions);
	}

	public Cell getCellAt(int x, int y) {
		return cells[x][y];
	}
}
