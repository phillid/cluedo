package cluedo;


import java.util.HashMap;
import java.util.Map;

import cluedo.cell.*;
import cluedo.token.*;


/**
 * Represents the game board as a two-dimensional grid of cells
 * 
 * @author David Phillips
 * @author Hamish Brown
 * 
 */
public class Board {
	Map<Character, Cell> startingPositions = new HashMap<Character, Cell>();

	private Cell[][] cells;
	private Room[] rooms;
	
	/**
	 * Construct new Board based on cells array
	 * @param cells -- array of cells for board
	 */
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
	 * Unconditionally force a movement of the specified weapon token to the specified Cell
	 * @param weaponToken -- the token to move
	 * @param room -- the room to force-move it to
	 */
	public void moveTokenToCell(WeaponToken weaponToken, Room room) {
		/* first, remove the weapon from any room it's currently in */
		for (Room r : rooms) {
			if (r.getWeapons().contains(weaponToken))
				r.removeWeapon(weaponToken);
		}
		room.addWeapon(weaponToken);
	}
	
	/**
	 * Unconditionally force a movement of the specified player token to the specified Cell
	 * @param playerToken -- the player token to move
	 * @param cell -- the cell to force-move it to
	 * @param pos -- the position of the cell
	 */
	public void moveTokenToCell(PlayerToken playerToken, Cell cell, Position pos) {
		Cell from = getCellAt(playerToken.getPosition());
		from.removeOccupant(playerToken);
		cell.addOccupant(playerToken);
		
		playerToken.setPosition(pos);
	}

	/**
	 * Get the cell at the position specified
	 * Simple wrapper around getCellAt(int, int)
	 * @param position -- position of desired cell
	 * @return cell at position on board
	 */
	public Cell getCellAt(Position position) {
		return getCellAt(position.getX(), position.getY());
	}
}
