package cluedo;

/**
 * Position class for grouping x and y together
 * @author David Phillips
 *
 */
public class Position {
	private int x;
	private int y;
	
	/**
	 * Bare constructor
	 * @param x -- x value of the position
	 * @param y -- y value of the position
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Construct from another Position object
	 * @param position
	 */
	public Position(Position position) {
		this.x = position.getX();
		this.y = position.getY();
	}
	
	@Override
	public String toString() {
		return "Position("+x+", "+y+")";
	}
	
	/**
	 * Get the X value of the position
	 * @return x value of position
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the Y value of the position
	 * @return y value of position
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set the X value of the position
	 * @param x -- x value of position
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Set the Y value of the position
	 * @param y -- y value of position
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
