package cluedo.cell;

import java.util.Set;

import cluedo.token.PlayerToken;

import java.util.HashSet;

public class Cell {
	protected Set<Cell> neighbours;
	protected Set<PlayerToken> occupants;

	public Cell(Cell... neighbours) {
		this.neighbours = new HashSet<Cell>();
		addNeighbours(neighbours);
		this.occupants = new HashSet<PlayerToken>();
	}

	public boolean isOccupied() {
		return occupants.size() >= 1;
	}

	public Set<PlayerToken> getOccupants() {
		return new HashSet<PlayerToken>(occupants);
	}

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

	public boolean isNeighbour(Cell other) {
		return neighbours.contains(other);
	}

	public Set<Cell> getNeighbours() {
		return new HashSet<Cell>(neighbours);
	}

	public boolean addOccupant(PlayerToken occupant) {
		/* may not add null occupant */
		if (occupant == null)
			return false;
		return occupants.add(occupant);
	}

	public boolean removeOccupant(PlayerToken occupant) {
		return occupants.remove(occupant);
	}
}
