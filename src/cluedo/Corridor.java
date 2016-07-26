package cluedo;

import java.util.Set;
import java.util.HashSet;

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
