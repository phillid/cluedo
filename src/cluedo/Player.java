package cluedo;

import java.util.ArrayList;
import java.util.List;

import cluedo.cards.Card;

public class Player {
	private PlayerToken token;
	private List<Card> heldCards;

	public Player(PlayerToken token, List<Card> heldCards) {
		if (token == null) {
			throw new IllegalArgumentException("Token must not be null");
		}
		this.token = token;
		if (heldCards == null) {
			this.heldCards = new ArrayList<Card>();
		} else {
			this.heldCards = heldCards;
		}
	}

	public List<Card> getHeldCards() {
		return heldCards;
	}

	public void setHeldCards(List<Card> heldCards) {
		this.heldCards = heldCards;
	}
}
