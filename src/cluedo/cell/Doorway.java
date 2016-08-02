package cluedo.cell;


/**Represents a doorway on the board.
 *
 *
 *
 */
public class Doorway extends Cell {

	public enum Direction {
		NORTH,
		SOUTH,
		EAST,
		WEST
	}

	private Direction direction;

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Doorway(Cell... neighbours) {
		super(neighbours);
	}

	public Doorway(Direction direction, Cell... neighbours) {
		super(neighbours);
		this.direction = direction;
	}

}
