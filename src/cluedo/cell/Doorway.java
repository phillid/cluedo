package cluedo.cell;

import cluedo.Position;

/**
 * Represents a doorway between a corridor and a room on the board
 * 
 * @author David Phillips
 * @author Hamish Brown
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
	 * Get direction in the opposite direction of doorway
	 * @return
	 */
	public Direction getOppositeDirection() {
		switch (getDirection()) {
		case NORTH:
			return Direction.SOUTH;
		case SOUTH:
			return Direction.NORTH;
		case EAST:
			return Direction.WEST;
		case WEST:
			return Direction.EAST;
		default:
			throw new RuntimeException("Unreachable code");
		}
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