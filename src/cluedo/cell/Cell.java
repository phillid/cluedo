package cluedo.cell;

import java.util.Set;

import cluedo.token.PlayerToken;

import java.util.HashSet;


/**
 * Represents a cell on the game board, that can contain tokens and have neighbours.
 *
 */
public class Cell {
	protected Set<Cell> neighbours;
	protected Set<PlayerToken> occupants;

	/**
	 * Construct a cell which allows travel to the specified neighbours
	 * @param neighbours -- other cells to which this cell allows travel 
	 */
	public Cell(Cell... neighbours) {
		this.neighbours = new HashSet<Cell>();
		addNeighbours(neighbours);
		this.occupants = new HashSet<PlayerToken>();
	}

	/**
	 * Determine if this cell is occupied
	 * @return true if any occupants, false if none
	 */
	public boolean isOccupied() {
		return occupants.size() >= 1;
	}

	/**
	 * Get a list of the current occupants of this cell 
	 * @return Set of player tokens who are currently in this cell
	 */
	public Set<PlayerToken> getOccupants() {
		return new HashSet<PlayerToken>(occupants);
	}

	/**
	 * Add cells to which this cell allows passage/travel.
	 * Useful for adding neighbours that cannot be added in the constructor
	 * @param neighbours -- additional neighbours
	 */
	public void addNeighbours(Cell... neighbours) {
		if (neighbours == null) {
			throw new IllegalArgumentException("Neighbours array must be non-null, or must be empty (unspecified)");
		}
		for (Cell n : neighbours) {
			if (n == null) {
				throw new IllegalArgumentException("Neighbours must be non-null");
			}
			if (this == n) {
				throw new IllegalArgumentException("Can't make cell its own neighbour, that's just messed up");
			}
			if (false == this.neighbours.add(n)) {
				System.err.println("Warning: duplicate neighbour given (didn't add it)");
			}
		}
	}

	/**
	 * Determine if the Cell other is a neighbour of this cell
	 * @param other
	 * @return true if `other` is a neighbour of this cell, false otherwise 
	 */
	public boolean isNeighbour(Cell other) {
		return neighbours.contains(other);
	}

	/**
	 * Get a set of neighbouring cells
	 * @return set of cells who are this cell's neighbours
	 */
	public Set<Cell> getNeighbours() {
		return new HashSet<Cell>(neighbours);
	}

	/**
	 * Add an occupant to this cell
	 * @param occupant -- must not be null to be valid
	 * @return true if the occupant is valid and was added, false if invalid
	 */
	public boolean addOccupant(PlayerToken occupant) {
		/* may not add null occupant */
		if (occupant == null)
			return false;
		return occupants.add(occupant);
	}

	/**
	 * Remove an occupant from the cell
	 * @param occupant
	 * @return -- return value of java.util.Set.remove(occupant)
	 */
	public boolean removeOccupant(PlayerToken occupant) {
		return occupants.remove(occupant);
	}
}
