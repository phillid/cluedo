package cluedo;

public class Cell {
	Cell[] neighbours;
	private PlayerToken occupant;

	public Cell(Cell... neighbours) {
		if (neighbours.length > 4)
			throw new IllegalArgumentException("Foo gimme less neighbours yo");

		this.neighbours = neighbours;
		this.occupant = null; /* let's be explicit */
	}

	public boolean isOccupied() {
		return this.occupant != null;
	}

	public PlayerToken getOccupant() {
		return this.occupant;
	}

	public void setOccupant(PlayerToken occupant) {
		this.occupant = occupant;
	}
}
