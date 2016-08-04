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
import cluedo.cards.Deck;
import cluedo.cards.PlayerCard;
import cluedo.cards.RoomCard;
import cluedo.cards.WeaponCard;
import cluedo.cell.Cell;
import cluedo.cell.Doorway;
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
			player.getPlayerToken().setX(startingPos.getX());
			player.getPlayerToken().setY(startingPos.getY());
		}

		start();
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
		
		do {
			index += 1;
			index %= length;
			currentPlayer = players.get(index);
		} while (currentPlayer.isPlaying == false);
	}

	/**
	 * Get the current player who is taking their turn in the game
	 * The current "active" player is kept internally by the Game, to avoid rule breakage etc
	 * @return currently active player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Get a random card from a list of cards. Leaves `cards` unaffected
	 * Small helper method for envelope generation.
	 * @param cards -- list of cards to draw from
	 * @return random card from cards
	 */
	private Card getRandomCard(List<Card> cards) {
		return cards.get(die.nextInt(cards.size()));
	}
	
	/**
	 * build the envelope randomly based on a full deck
	 */
	public void makeEnvelope() {
		List<Card> playerCards = Deck.generatePlayerDeck();
		List<Card> roomCards = Deck.generateRoomDeck();
		List<Card> weaponCards = Deck.generateWeaponDeck();
		
		/* build the envelope */
		/* FIXME refactor this maybe? a lot of repetition going on */
		
		System.out.println("w deck size " + weaponCards.size());
		
		envelope.add(getRandomCard(playerCards));
		envelope.add(getRandomCard(roomCards));
		envelope.add(getRandomCard(weaponCards));

		deck = Deck.generatePlain();

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

	/**
	 * Check if the internal envelope matches the one given in suggestion
	 * @param suggestion
	 * @return true if suggestion matches envelope, false otherwise
	 */
	public boolean envelopeMatches(Set<Card> suggestion) {
		return envelope.equals(suggestion);
	}
	
	/**
	 * Roll the game's die and save it internally for the current player
	 * @return the result of the die roll
	 */
	public int roll() {
		return roll = die.nextInt(5) + 1;
	}
	
	/**
	 * Get the current player's die roll, with any moves made against that roll subtracted
	 * @return the current remaining die roll
	 */
	public int getRoll() {
		return roll;
	}
	
	/**
	 * Determine whether the player's remaining die roll means they may still make a move
	 * @return true if player may still move, false otherwise
	 */
	public boolean canMove() {
		return roll > 0;
	}

	/**
	 * Make a the current player move in the given direction
	 * Performs the move if it i valid to do so, otherwise leaves the board and game state unchanged
	 * The player's die roll, stored internally, is decremented on a valid move 
	 * @param direction -- one of {"n","s","e","w"} for north, south, east and west respectively
	 * @return true if the move was valid and performed, false otherwise
	 */
	public boolean move(String direction) {
		if (roll <= 0)
			return false;
		int x = currentPlayer.getPlayerToken().getX();
		int y = currentPlayer.getPlayerToken().getY();
		switch (direction) {
		case "n": y--; break;
		case "s": y++; break;
		case "w": x--; break;
		case "e": x++; break;
		default: throw new RuntimeException("Invalid direction '"+direction+"'");
		}
		
		if (x < 0 || x >= board.getWidth() || y < 0 || y >= board.getHeight()) {
			return false;
		}
		
		if (board.movePlayer(currentPlayer.getPlayerToken(), x, y) == false)
			return false;
		
		roll--;
		if (board.getCellAt(x, y) instanceof Doorway) {
			//move the player into the room neighbouring the doorway
			switch (direction) {
			case "n": y--; break;
			case "s": y++; break;
			case "w": x--; break;
			case "e": x++; break;
			}
			roll = 0;
			if (board.movePlayer(currentPlayer.getPlayerToken(), x, y) == false)
				throw new RuntimeException("Doorway -> room failed");
		}
		
		return true;
	}
	

	/**
	 * determine if current player is situated inside a room
	 * @return true if player is in room, false otherwise
	 */
	public boolean playerIsInRoom() {
		PlayerToken pt = currentPlayer.getPlayerToken();
		return board.getCellAt(pt.getX(), pt.getY()) instanceof Room;
	}
	
	/**
	 * Get the cell that the current player's occupying
	 * @return cell current player is in
	 */
	public Cell getCurrentPlayerCell() {
		PlayerToken pt = currentPlayer.getPlayerToken();
		return board.getCellAt(pt.getX(), pt.getY());
	}
	
	/**
	 * Make a suggestion of the murder stuffs
	 * @param suggestion
	 * @return true if suggestion is valid and unrefuted, false otherwise
	 */
	public boolean suggest(Set<Card> suggestion) {
		/* FIXME move the tokens around */
		/* FIXME check if player is in room */
		/* FIXME probably need to loop through players' decks looking for match to suggestion */
		if (envelopeMatches(suggestion)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Current player makes an accusation.
	 * Much the same as a suggestion, but much simpler and clear cut.
	 * This doesn't require the player to be in that room, and that
	 * failure/incorrectness results in that player sitting out for the rest of the game
	 * @param accusation
	 * @return true if accusation is correct, false in all other cases
	 */
	public boolean accuse(Set<Card> accusation) {
		if (envelopeMatches(accusation)) {
			/* hooray! */
			return true;
		}
		
		/* lolno.jpg, your accusation was incorrect, buddy */
		currentPlayer.isPlaying = false;
		return false;
	}
}
