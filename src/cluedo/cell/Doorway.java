package cluedo.cell;

import cluedo.Position;

/**
 * Represents a doorway on the board.
 */
public class Doorway extends Cell {
	private Direction direction;
	
	/**
	 * Direction of travel INTO the room
	 */
	public enum Direction {
		NORTH,
		SOUTH,
		EAST,
		WEST
	}

	/**
	 * Getter for the direction field 
	 * @return direction of this doorway
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Setter for the direction field
	 * @param direction -- new direction for doorway
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Basic constructor that calls the super-constructor
	 * @param neighbours
	 */
	public Doorway(Position position, Cell... neighbours) {
		super(position, neighbours);
	}
	
	/**
	 * Extended constructor supporting a direction
	 * @param direction -- direction of doorway
	 * @param neighbours -- neighbours of the doorway
	 */
	public Doorway(Position position, Direction direction, Cell... neighbours) {
		super(position, neighbours);
		this.direction = direction;
	}
}