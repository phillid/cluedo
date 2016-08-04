package cluedo;

import java.util.ArrayList;


import java.util.List;

import cluedo.cards.Card;
import cluedo.token.PlayerToken;

/**
 * Represents a human player
 *
 */

public class Player {
	private PlayerToken token;
	private List<Card> heldCards;
	private int x;
	private int y;
	
	/* false if player made accusation and was wrong */
	public boolean isPlaying; 


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
		isPlaying = true;
	}

	public List<Card> getHeldCards() {
		return heldCards;
	}

	public void setHeldCards(List<Card> heldCards) {
		this.heldCards = heldCards;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public PlayerToken getPlayerToken() {
		return token;
	}
}
