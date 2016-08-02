package cluedo.token;

/**
 * Represents a weapon token to be used on the game board.
 *
 *
 */
public class WeaponToken extends Token {
	public WeaponToken(String name) {
		super(name);
	}

	public WeaponToken(String name, char initial) {
		super(name, initial);
	}
}
