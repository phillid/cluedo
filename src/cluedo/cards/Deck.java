package cluedo.cards;

import java.util.List;
import java.util.ArrayList;

public final class Deck {
	public static final List<Card> playerCards = new ArrayList<Card>();
	public static final List<Card> roomCards = new ArrayList<Card>();
	public static final List<Card> weaponCards = new ArrayList<Card>();
	
	static {
			playerCards.add(new PlayerCard("Miss Scarlet"));
			playerCards.add(new PlayerCard("Colenel Mustard"));
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

	public static List<Card> generatePlain() {
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(playerCards);
		deck.addAll(roomCards);
		deck.addAll(weaponCards);
		return deck;
	}
	
	public static List<Card> generatePlayerDeck() {
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(playerCards);
		return deck;
	}
	
	public static List<Card> generateRoomDeck() {
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(roomCards);
		return deck;
	}
	
	public static List<Card> generateWeaponDeck() {
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(weaponCards);
		return deck;
	}
}
