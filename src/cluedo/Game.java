package cluedo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import cluedo.token.PlayerToken;
import cluedo.cards.Card;
import cluedo.cards.PlayerCard;
import cluedo.cards.RoomCard;
import cluedo.cards.WeaponCard;
import cluedo.cell.Cell;
import cluedo.cell.Room;

/**
 * Doezs al the stuff
 */

public class Game {

	public static final Map<Character,String> playerCodes = new HashMap<Character,String>();
	static {
		playerCodes.put('S', "Miss Scarlet");
		playerCodes.put('W',"Mrs White");
		playerCodes.put('M', "Colonel Mustard");
		playerCodes.put('G', "The Reverend Green");
		playerCodes.put('E', "Mrs Peacock");
		playerCodes.put('P', "Professor Plum");
	}

	public Board board;
	private List<Card> deck = new ArrayList<Card>();
	private List<Card> envelope = new ArrayList<Card>();
	private List<Player> players = new ArrayList<Player>();
	private List<PlayerToken> playerTokens = new ArrayList<PlayerToken>();
	private Player currentPlayer;
	private Random die = new Random();
	private int roll;
	
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
		//TODO use player token map instead
		playerTokens.add(new PlayerToken("Miss Scarlet", 'S'));
		playerTokens.add(new PlayerToken("Colonel Mustard", 'M'));
		playerTokens.add(new PlayerToken("Mrs White", 'W'));
		playerTokens.add(new PlayerToken("The Reverend Green", 'G'));
		playerTokens.add(new PlayerToken("Mrs Peacock", 'E'));
		playerTokens.add(new PlayerToken("Professor Plum", 'P'));

		/* set up the players */
		for (PlayerToken pt : playerTokens) {
			Player player = new Player(pt, new ArrayList<Card>());
			Cell starting = board.getStartingPositions().get(pt.getInitial());
			Position startingPos = board.getPos(starting);
			players.add(player);
			starting.addOccupant(pt);
			player.setX(startingPos.getX());
			player.setY(startingPos.getY());
		}


	}

	/**
	 * Prepare an envelope and deal the deck to the players
	 */
	public void start() {
		nextPlayer();
		makeEnvelope();
		dealToPlayers();
	}
	
	/**
	 * Advance the current player to the next in line
	 * If no current player, initialise it to the 0th.
	 */
	public void nextPlayer() {
		if (currentPlayer == null) {
			currentPlayer = players.get(0);
			return;
		}
		int length = players.size();
		int index = players.indexOf(currentPlayer);
		
		index += 1;
		index %= length;
		
		currentPlayer = players.get(index);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
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

	public boolean envelopeMatches(Set<Card> suggestion) {
		return envelope.equals(suggestion);
	}
	
	public int roll() {
		return roll = die.nextInt(5) + 1;
	}
	
	public int getRoll() {
		return roll;
	}
	
	public boolean canMove() {
		return roll != 0;
	}

	public boolean move(String direction) {
		if (roll <= 0)
			return false;
		int x = currentPlayer.getX();
		int y = currentPlayer.getY();
		switch (direction) {
		case "n": y--; break;
		case "s": y++; break;
		case "w": x--; break;
		case "e": x++; break;
		default: throw new RuntimeException("Invalid direction");
		}
		
		if (x < 0 || x >= board.getWidth() || y < 0 || y >= board.getHeight()) {
			return false;
		}
		
		/* FIXME moar checks */
		
		
		roll--;
		if (board.getCellAt(x, y) instanceof Room) {
			roll = 0;
		}
		return true;
	}

	/**
	 * determine if current player is situated inside a room
	 * @return true if player is in room, false otherwise
	 */
	public boolean playerIsInRoom() {
		int x = currentPlayer.getX();
		int y = currentPlayer.getY();
		
		return board.getCellAt(x, y) instanceof Room;
	}
}
