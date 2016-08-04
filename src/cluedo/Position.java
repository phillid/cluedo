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
}
