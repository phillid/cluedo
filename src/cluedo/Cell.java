package cluedo;

import java.util.Set;
import java.util.HashSet;

public class Cell {
	private Set<Cell> neighbours;
	private Set<PlayerToken> occupants;

	public Cell(Cell... neighbours) {
		this.neighbours = new HashSet<Cell>();
		addNeighbours(neighbours);
		this.occupants = new HashSet<PlayerToken>(); /* let's be explicit */
	}

	public boolean isOccupied() {
		return occupants != null && occupants.size() >= 1;
	}

	public Set<PlayerToken> getOccupants() {
		return new HashSet<PlayerToken>(occupants);
	}

	public void addNeighbours(Cell... neighbours) {
		for (Cell n : neighbours) {
			if (false == this.neighbours.add(n)) {
				System.err.println("Warning: duplicate neighbour given (didn't add)");
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
		return occupants.add(occupant);
	}

	public boolean removeOccupant(PlayerToken occupant) {
		return occupants.remove(occupant);
	}
}
