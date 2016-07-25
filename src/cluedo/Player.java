package cluedo;

import java.util.List;

import cluedo.cards.Card;

public class Player {
	private PlayerToken token;
	private List<Card> heldCards;

	public Player(PlayerToken token, List<Card> heldCards) {
		this.token = token;
		this.heldCards = heldCards;
	}

	public List<Card> getHeldCards() {
		return heldCards;
	}

	public void setHeldCards(List<Card> heldCards) {
		this.heldCards = heldCards;
	}
}
