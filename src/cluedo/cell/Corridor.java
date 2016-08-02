package cluedo.cell;


import cluedo.token.PlayerToken;

/**
 * Represemts a corridor on the playing board
 *
 *
 */
public class Corridor extends Cell {

	public Corridor(Cell... neighbours) {
		super(neighbours);
	}

	public boolean addOccupant(PlayerToken occupant) {
		if (isOccupied())
			throw new IllegalStateException("May not add multiple occupants");

		return super.addOccupant(occupant);
	}
}
