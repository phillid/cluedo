package cluedo.token;

import cluedo.Position;

/**
 * Represents a player token to be used on the game board.
 */
public class PlayerToken extends Token {
	private int x;
	private int y;
	
	/**
	 * basic constructor
	 * @param name
	 */
	public PlayerToken(String name) {
		super(name);
	}

	/**
	 * basic constructor
	 * @param name
	 * @param initial
	 */
	public PlayerToken(String name, char initial) {
		super(name, initial);
	}
	
	/**
	 * Basic toString method
	 */
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

	/**
	 * Set the player token's X and Y to those specified in the argument pos
	 * @param pos -- the position to set to
	 */
	public void setPosition(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}

	/**
	 * Get the position of the player token
	 * @return
	 */
	public Position getPosition() {
		return new Position(x,y);
	}
}
