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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Card other = (Card) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
