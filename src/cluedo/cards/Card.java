package cluedo.cards;

/**
 * A common superclass for the cards that will be used by the players to play the game.
 */
public abstract class Card {
	private String name;

	public Card(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "[Card "+name+"]";
	}
}
