package cluedo;

import java.util.List;

import cards.Card;

public class PlayerToken {
	String name;
	List<Card> heldCards;

	public PlayerToken(String name, List<Card> heldCards) {
		this.heldCards = heldCards;
		this.name = name;
	}
}
