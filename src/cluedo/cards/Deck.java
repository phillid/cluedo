package cluedo.cards;

import java.util.List;
import java.util.ArrayList;

/**
 * Static class containing routines and data for constructing decks
 * @author David Phillips
 */
public final class Deck {
	public static final List<Card> playerCards = new ArrayList<Card>();
	public static final List<Card> roomCards = new ArrayList<Card>();
	public static final List<Card> weaponCards = new ArrayList<Card>();
	
	static {
			playerCards.add(new PlayerCard("Miss Scarlet"));
			playerCards.add(new PlayerCard("Colonel Mustard"));
			playerCards.add(new PlayerCard("Mrs White"));
			playerCards.add(new PlayerCard("The Reverend Green"));
			playerCards.add(new PlayerCard("Mrs Peacock"));
			playerCards.add(new PlayerCard("Professor Plum"));
			
			roomCards.add(new RoomCard("Kitchen"));
			roomCards.add(new RoomCard("Ball Room"));
			roomCards.add(new RoomCard("Conservatory"));
			roomCards.add(new RoomCard("Dining Room"));
			roomCards.add(new RoomCard("Billiard Room"));
			roomCards.add(new RoomCard("Library"));
			roomCards.add(new RoomCard("Lounge"));
			roomCards.add(new RoomCard("Hall"));
			roomCards.add(new RoomCard("Study"));
			
			weaponCards.add(new WeaponCard("Candlestick"));
			weaponCards.add(new WeaponCard("Dagger"));
			weaponCards.add(new WeaponCard("Lead Pipe"));
			weaponCards.add(new WeaponCard("Revolver"));
			weaponCards.add(new WeaponCard("Rope"));
			weaponCards.add(new WeaponCard("Spanner"));
	}

	/**
	 * Generate a full deck
	 * @return List of cards to make full deck
	 */
	public static List<Card> generatePlain() {
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(playerCards);
		deck.addAll(roomCards);
		deck.addAll(weaponCards);
		return deck;
	}
	
	/**
	 * Generate a deck of all player cards in the game
	 * @return List of player cards
	 */
	public static List<Card> generatePlayerDeck() {
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(playerCards);
		return deck;
	}
	
	/**
	 * Generate a deck of all room cards in the game
	 * @return List of room cards
	 */
	public static List<Card> generateRoomDeck() {
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(roomCards);
		return deck;
	}
	
	/**
	 * Generate a deck of all weapon cards in the game
	 * @return list of weapon cards
	 */
	public static List<Card> generateWeaponDeck() {
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(weaponCards);
		return deck;
	}
}
