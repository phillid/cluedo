package cluedo.token;

/**
 * Represents a player token to be used on the game board.
 */
public class PlayerToken extends Token {
	private int x;
	private int y;
	
	public PlayerToken(String name) {
		super(name);
	}

	public PlayerToken(String name, char initial) {
		super(name, initial);
	}
	
	@Override
	public String toString() {
		return new String(getName() + " (" + getInitial() + ")");
	}
	
	/**
	 * Get the X value on the board
	 * @return x value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the Y value on the board
	 * @return y value
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set the X value
	 * @param x value
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Set the Y value
	 * @param y value
	 */
	public void setY(int y) {
		this.y = y;
	}
}
