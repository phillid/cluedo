package cluedo.cards;

/**
 * A common superclass for the cards that will be used by the players to play the game.
 * Mostly a marker class and wrapper around String
 */
public abstract class Card {
	private String name;

	/**
	 * Basic constructor
	 * @param name
	 */
	public Card(String name) {
		this.name = name;
	}

	/**
	 * Get the name of this card
	 * @return name of the card
	 */
	public String getName() {
		return name;
	}

	/**
	 * Simple toString method
	 */
	@Override
	public String toString() {
		return "[Card "+name+"]";
	}
}
