package cluedo.cell;


import cluedo.Position;
import cluedo.token.PlayerToken;

/**
 * Represents a corridor on the playing board
 *
 */
public class Corridor extends Cell {

	/**
	 * Default (super) constructor 
	 * @param neighbours -- cells that this Corridor allows travel to
	 */
	public Corridor(Position position, Cell... neighbours) {
		super(position, neighbours);
	}

	/**
	 * Overridden addOccupant(PlayerToken) method as a Corridor cell allows only one occupant
	 */
	@Override
	public boolean addOccupant(PlayerToken occupant) {
		if (isOccupied())
			throw new IllegalStateException("May not add multiple occupants");

		return super.addOccupant(occupant);
	}

	/**
	 * Helper method for getting the single occupant of a Corridor cell
	 * @return PlayerToken of single occupant
	 */
	public PlayerToken getOccupant() {
		return this.occupants.iterator().next();
	}
}
