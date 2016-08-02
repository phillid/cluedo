package cluedo;


import java.util.HashMap;
import java.util.Map;

import cluedo.cell.Cell;
import cluedo.cell.Room;


/**
 * Represents the game board
 */
public class Board {
	Map<Character, Cell> startingPositions = new HashMap<Character, Cell>();

	private Cell[][] cells;
	private Room[] rooms;
	public Board(Cell[][] cells) {
		this.cells = cells;
	}

	public Cell addStartingPosition(Character ch, Cell cell) {
		return startingPositions.put(ch, cell);
	}

	public void setRooms(Room[] rooms) {
		this.rooms = rooms;
	}

	public Map<Character, Cell> getStartingPositions() {
		return new HashMap<Character, Cell>(startingPositions);
	}

	public Cell getCellAt(int x, int y) {
		return cells[x][y];
	}
}
