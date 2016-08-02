package cluedo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import cluedo.token.PlayerToken;
import cluedo.cards.Card;
import cluedo.cards.PlayerCard;
import cluedo.cards.RoomCard;
import cluedo.cards.WeaponCard;

/**
 * Doezs al the stuff
 */

public class Game {
	public Board board;
	private List<Card> deck = new ArrayList<Card>();
	private List<Card> envelope = new ArrayList<Card>();
	private List<Player> players = new ArrayList<Player>();
	private List<PlayerToken> playerTokens = new ArrayList<PlayerToken>();

	/**
	 * Game constructor. Set up the player tokens and players
	 */
	public Game() {
		/* set up the board */
		try {
			Scanner s = new Scanner(new File("map"));
			s.useDelimiter("");
			board = new BoardParser().parseBoard(s);
		} catch (FileNotFoundException e) {
			throw new Error(e);
		}

		/* set up the player tokens */
		playerTokens.add(new PlayerToken("Professor Plum"));
		playerTokens.add(new PlayerToken("Mrs White"));
		playerTokens.add(new PlayerToken("Miss Scarlet"));
		playerTokens.add(new PlayerToken("Colenel Mustard"));
		playerTokens.add(new PlayerToken("The Reverend Green"));
		playerTokens.add(new PlayerToken("Mrs Peacock"));

		/* set up the players */
		for (PlayerToken pt : playerTokens) {
			players.add(new Player(pt, new ArrayList<Card>()));
		}
	}

	/**
	 * Prepare an envelope and deal the deck to the players
	 */
	public void start() {
		makeEnvelope();
		dealToPlayers();
	}

	/**
	 * build the envelope randomly based on a full deck
	 */
	public void makeEnvelope() {
		Card[] playerCards = {
				new PlayerCard("Miss Scarlet"),
				new PlayerCard("Colenel Mustard"),
				new PlayerCard("Mrs White"),
				new PlayerCard("The Reverend Green"),
				new PlayerCard("Mrs Peacock"),
				new PlayerCard("Professor Plum")
		};
		Card[] roomCards = {
				new RoomCard("Kitchen"),
				new RoomCard("Ball Room"),
				new RoomCard("Conservatory"),
				new RoomCard("Dining Room"),
				new RoomCard("Billiard Room"),
				new RoomCard("Library"),
				new RoomCard("Lounge"),
				new RoomCard("Hall"),
				new RoomCard("Study")
		};
		Card[] weaponCards = {
				new WeaponCard("Candlestick"),
				new WeaponCard("Dagger"),
				new WeaponCard("Lead Pipe"),
				new WeaponCard("Revolver"),
				new WeaponCard("Rope"),
				new WeaponCard("Spanner"),
		};

		/* build the envelope */
		/* FIXME refactor this maybe? a lot of repetition going on */
		Random rand = new Random();
		int index = rand.nextInt(playerCards.length);
		envelope.add(playerCards[index]);
		index = rand.nextInt(roomCards.length);
		envelope.add(roomCards[index]);
		index = rand.nextInt(weaponCards.length);
		envelope.add(weaponCards[index]);

		/* add all cards to deck including those in envelope */
		deck.addAll(Arrays.asList(playerCards));
		deck.addAll(Arrays.asList(roomCards));
		deck.addAll(Arrays.asList(weaponCards));

		/* subtract the envelope from deck */
		deck.removeAll(envelope);

		System.err.println("DEBUG: Envelope: "+envelope);
	}

	 /**
	  * Deal the entirety of the deck to the players
	  */
	public void dealToPlayers() {
		int playerCount = players.size();
		int cardCount = deck.size();
		ArrayList<Card> playerDeck;

		/* shuffle the deck */
		Collections.shuffle(deck);

		/* split the deck amongst players */
		for (int i = 0; i < playerCount; i++) {
			playerDeck = new ArrayList<Card>();
			if (cardCount / playerCount > deck.size())
				playerDeck.addAll(deck.subList(0, deck.size()));
			else
				playerDeck.addAll(deck.subList(0, cardCount/playerCount));
			deck.removeAll(playerDeck);
			players.get(i).setHeldCards(playerDeck);
		}
		assert deck.size() != 0 : "Deck not fully dealt (still has "+deck.size()+" cards)";
	}
}
