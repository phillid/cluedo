package cluedo;


import java.util.HashMap;
import java.util.Map;

import cluedo.cell.Cell;
import cluedo.cell.Corridor;
import cluedo.cell.Room;
import cluedo.token.PlayerToken;


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

	/**
	 * Add a starting position for a character to the board
	 * @param ch -- initial of the character
	 * @param cell -- the cell that the player should start at
	 * @return the cell that was added
	 */
	public Cell addStartingPosition(Character ch, Cell cell) {
		return startingPositions.put(ch, cell);
	}

	/**
	 * Set the internal rooms array to that specified one
	 * @param rooms
	 */
	public void setRooms(Room[] rooms) {
		this.rooms = rooms;
	}
	
	/**
	 * Get the internal rooms array 
	 * @return internal array of Room objects
	 */
	public Room[] getRooms() {
		return rooms;
	}

	/**
	 * Get the map of the player starting position
	 * @return Map of the character starting position
	 */
	public Map<Character, Cell> getStartingPositions() {
		return new HashMap<Character, Cell>(startingPositions);
	}

	/**
	 * Get the Cell object at specified x and y
	 * @param x -- x position -- must be within board bounds 
	 * @param y -- y position -- must be within board bounds
	 * @return cell as position (x,y) on the board
	 */
	public Cell getCellAt(int x, int y) {
		return cells[x][y];
	}

	/**
	 * Get the height of the board
	 * @return height of the board
	 */
	public int getHeight() {
		if (cells == null || cells[0] == null)
			return 0;
		return cells[0].length;
	}
	
	/**
	 * Get the width of the board
	 * @return width of the board
	 */
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
	public boolean movePlayer(PlayerToken playerToken, int x, int y) {
		int oldx = playerToken.getX();
		int oldy = playerToken.getY();
		
		Cell from = getCellAt(oldx, oldy);
		Cell to = getCellAt(x, y);
		
		if (from == null || to == null)
			return false;
		
		if (!from.isNeighbour(to))
			return false;
		
		if (to instanceof Corridor && to.isOccupied())
			return false;
		
		cells[oldx][oldy].removeOccupant(playerToken);
		cells[x][y].addOccupant(playerToken);
		
		playerToken.setX(x);
		playerToken.setY(y);
		return true;
	}

	/**
	 * Get the Position of a cell on the board
	 * throws a RuntimeException if the board doesn't contain the "needle" cell
	 * @param needle -- cell to get position of
	 * @return Position representing position of needle
	 */
	public Position getPos(Cell needle) {
		for (int y = 0; y < getHeight(); y++)
			for (int x = 0; x < getWidth(); x++)
				if (needle.equals(cells[x][y]))
					return new Position(x,y);
		throw new RuntimeException("Cell not on board");
	}
}
