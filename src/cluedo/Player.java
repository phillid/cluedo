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
	public boolean isPlaying; /* false if player takes no turns (eg sitting out/lost) */
	public boolean canSuggest;
	
	/**
	 * Basic constructor
	 * @param token -- PlayerToken that this player is responsible for
	 * @param heldCards -- List of cards which the player is holding
	 */
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

	/**
	 * Get the List of cards held by this player
	 * @return List of cards held
	 */
	public List<Card> getHeldCards() {
		return heldCards;
	}

	/**
	 * Replace the List of cards held by the player
	 * @param heldCards -- list of cards to hold
	 */
	public void setHeldCards(List<Card> heldCards) {
		this.heldCards = heldCards;
	}

	/**
	 * Get the PlayerToken this player is controlling
	 * @return PlayerToken of this player
	 */
	public PlayerToken getPlayerToken() {
		return token;
	}
}
