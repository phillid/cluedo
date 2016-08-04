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
	
	public Room[] getRooms() {
		return rooms;
	}

	public Map<Character, Cell> getStartingPositions() {
		return new HashMap<Character, Cell>(startingPositions);
	}

	public Cell getCellAt(int x, int y) {
		return cells[x][y];
	}

	public int getHeight() {
		if (cells == null || cells[0] == null)
			return 0;
		return cells[0].length;
	}
	
	public int getWidth() {
		if (cells == null)
			return 0;
		return cells.length;
	}
	
	/**
	 * move a player to the x and y coords
	 * 
	 * @returns false if the position given does not correspond to an
	 *          existing cell or if the cell at the position is not a
	 *          neighbour of the player's current cell
	 */
	public boolean movePlayer(Player player, int x, int y) {
		int oldx = player.getX();
		int oldy = player.getY();
		
		Cell from = getCellAt(oldx, oldy);
		Cell to = getCellAt(x, y);
		
		if (from == null || to == null)
			return false;
		
		if (!from.isNeighbour(to))
			return false;
		
		
		cells[oldx][oldy].removeOccupant(player.getPlayerToken());
		cells[x][y].addOccupant(player.getPlayerToken());
		
		player.setX(x);
		player.setY(y);
		return true;
	}

	public Position getPos(Cell needle) {
		for (int y = 0; y < getHeight(); y++)
			for (int x = 0; x < getWidth(); x++)
				if (needle.equals(cells[x][y]))
					return new Position(x,y);
		throw new RuntimeException("Cell not on board");
	}
}
