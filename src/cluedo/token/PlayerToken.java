package cluedo.token;

/**
 * Represents a player token to be used on the game board.
 * 
 * @author David Phillips
 * @author Hamish Brown
 */
public class PlayerToken extends Token {
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
}
