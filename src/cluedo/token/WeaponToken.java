package cluedo.token;

/**
 * Represents a weapon token to be used on the game board.
 *
 * @author David Phillips
 * @author Hamish Brown
 */
public class WeaponToken extends Token {
	/**
	 * basic constructor
	 * @param name
	 */
	public WeaponToken(String name) {
		super(name);
	}

	/**
	 * basic constructor
	 * @param name
	 * @param initial
	 */
	public WeaponToken(String name, char initial) {
		super(name, initial);
	}
	
	/**
	 * Basic toString method
	 */
	@Override
	public String toString() {
		return getName();
	}
}
