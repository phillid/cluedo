package cluedo.token;

/**
 * Represents a player token to be used on the game board.
 */
public class PlayerToken extends Token {
	public PlayerToken(String name) {
		super(name);
	}

	public PlayerToken(String name, char initial) {
		super(name, initial);
	}
}
