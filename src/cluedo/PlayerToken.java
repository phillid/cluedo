package cluedo;

import java.util.List;

import cluedo.cards.Card;

public class PlayerToken extends Token {
	String name;
	List<Card> heldCards;

	public PlayerToken(String name, List<Card> heldCards) {
		this.heldCards = heldCards; /* FIXME do the thing */
		this.name = name;
	}
}
