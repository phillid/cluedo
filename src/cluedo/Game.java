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
import cluedo.token.Token;
import cluedo.token.WeaponToken;
import cluedo.cards.Card;
import cluedo.cards.Deck;
import cluedo.cards.PlayerCard;
import cluedo.cards.RoomCard;
import cluedo.cards.WeaponCard;
import cluedo.cell.Cell;
import cluedo.cell.Corridor;
import cluedo.cell.Doorway;
import cluedo.cell.Room;

/**
 * Doezs al the stuff
 */

public class Game {

	/*public static final Map<Character,String> playerCodes = new HashMap<Character,String>();
	static {
		playerCodes.put('S', "Miss Scarlet");
		playerCodes.put('W',"Mrs White");
		playerCodes.put('M', "Colonel Mustard");
		playerCodes.put('G', "The Reverend Green");
		playerCodes.put('E', "Mrs Peacock");
		playerCodes.put('P', "Professor Plum");
	}*/

	public Board board;
	private List<Card> deck = new ArrayList<Card>();
	private List<Card> envelope = new ArrayList<Card>();
	private List<Player> players = new ArrayList<Player>();
	private List<PlayerToken> playerTokens = new ArrayList<PlayerToken>();
	private List<WeaponToken> weaponTokens = new ArrayList<WeaponToken>();
	private Player currentPlayer;
	private Random die = new Random();
	private int roll;
	private int playerCount;
	private Card evidence;
	
	/**
	 * Game constructor. Set up the player tokens and players
	 */
	public Game(int playerCount) {
		/* set player count field */
		this.playerCount = playerCount;
		
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
		/* FIXME should be based off deck??? */
		playerTokens.add(new PlayerToken("Miss Scarlet", 'S'));
		playerTokens.add(new PlayerToken("Colonel Mustard", 'M'));
		playerTokens.add(new PlayerToken("Mrs White", 'W'));
		playerTokens.add(new PlayerToken("The Reverend Green", 'G'));
		playerTokens.add(new PlayerToken("Mrs Peacock", 'E'));
		playerTokens.add(new PlayerToken("Professor Plum", 'P'));

		/* set up weapon tokens */
		/* FIXME should be based off deck??? */
		weaponTokens.add(new WeaponToken("Candlestick"));
		weaponTokens.add(new WeaponToken("Dagger"));
		weaponTokens.add(new WeaponToken("Lead Pipe"));
		weaponTokens.add(new WeaponToken("Revolver"));
		weaponTokens.add(new WeaponToken("Rope"));
		weaponTokens.add(new WeaponToken("Spanner"));
		
		/* set up the players */
		for (Token pt : playerTokens) {
			Player player = new Player((PlayerToken)pt, new ArrayList<Card>());
			Cell starting = board.getStartingPositions().get(pt.getInitial());
			Position startingPos = starting.getPosition();
			players.add(player);
			starting.addOccupant((PlayerToken)pt);
			player.getPlayerToken().setPosition(startingPos);
		}
		
		/* set up the weapons */
		for (WeaponToken wt : weaponTokens) {
			while (true) {
				int roomNum = die.nextInt(9);
				Room room = board.getRooms()[roomNum];
				if (room.getWeapons().isEmpty()) {
					room.addWeapon(wt);
					break;
				}
			}
		}

		/* exempt non-playing players from playing 😛 */ 
		for (int i = playerCount; i < players.size(); i++)
			players.get(i).isPlaying = false;
		
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
		/*  */
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
		envelope.add(getRandomCard(playerCards));
		envelope.add(getRandomCard(roomCards));
		envelope.add(getRandomCard(weaponCards));

		deck = Deck.generatePlain();

		/* subtract the envelope from deck */
		deck.removeAll(envelope);

	}

	/**
	 * Deal the entirety of the deck to the players
	 */
	public void dealToPlayers() {
		///int playerCount = players.size();
		int cardCount = deck.size();
		ArrayList<Card> playerDeck;

		/* shuffle the deck */
		Collections.shuffle(deck);

		/* split the deck amongst players */
		int i = 0;
		while (deck.size() != 0) {
			players.get(i).getHeldCards().add(deck.get(0));
			deck.remove(0);
			i++;
			i %= playerCount;
		}
		if (deck.size() != 0)
			throw new RuntimeException("Deck not fully dealt, help! HELP ME! Still have "+deck.size()+" cards");
	}

	/**
	 * Check if the internal envelope matches the one given in suggestion
	 * @param suggestion
	 * @return true if suggestion matches envelope, false otherwise
	 */
	public boolean envelopeMatches(Set<Card> suggestion) {
		return envelope.containsAll(suggestion);
	}
	
	/**
	 * Roll the game's die and save it internally for the current player
	 * @return the result of the die roll
	 */
	public int roll() {
		return roll = die.nextInt(5) + die.nextInt(5) + 2;
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
	 * Determine whether the player's is allowed to make a suggestion at this time
	 * @return true if player may make a suggestion, false otherwise
	 */
	public boolean canSuggest() {
		return currentPlayer.canSuggest;
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
		int oldx = x;
		int oldy = y;
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
		
		/* make the move, bailing on failure */
		if (board.movePlayer(currentPlayer.getPlayerToken(), x, y) == false)
			return false;
		
		/* move was successful, decrement roll */
		roll--;
		
		/* Moving from corridor to doorway -> end turn */
		if (   board.getCellAt(oldx, oldy) instanceof Corridor
			&& board.getCellAt(x, y) instanceof Doorway) {
			roll = 0;
			//move the player into the room neighbouring the doorway
			switch (direction) {
			case "n": y--; break;
			case "s": y++; break;
			case "w": x--; break;
			case "e": x++; break;
			}
			
			if (board.movePlayer(currentPlayer.getPlayerToken(), x, y) == false)
				throw new RuntimeException("Doorway -> room failed");
			
			Room room = (Room)board.getCellAt(x,y);
			/* just entered a room -> enable suggestion ability and set (x,y) to -1*/
			currentPlayer.canSuggest = true;
			currentPlayer.getPlayerToken().setPosition(getNextFreePosition(room));
		}
		
		/* move successful */
		return true;
	}
	
	public void exitRoom(Cell room,Cell dest) {
		if (dest instanceof Doorway) {
			Doorway door = (Doorway)dest;
			for(Cell other : door.getNeighbours()) {
				if (other instanceof Room) {
					continue; //dont move back into the room we want to leave!
				} else {
					movePlayerToCell(room,other);
				}
			}
		} else {
			movePlayerToCell(room,dest);
		}
	}
	
	public void movePlayerToCell(Cell from, Cell to) {
		from.removeOccupant(currentPlayer.getPlayerToken());
		to.addOccupant(currentPlayer.getPlayerToken());
		currentPlayer.getPlayerToken().setPosition(to.getPosition());
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
	 * Fetch player token from list of tokens which matches the given card 
	 * @param tokens -- list of tokens to search
	 * @param card -- card to match a token to
	 * @return null if no token matches, the first matching token otherwise
	 */
	public Token getPlayerTokenMatching(List<PlayerToken> tokens, Card card) {
		for (Token t : tokens) {
			PlayerCard needle = new PlayerCard(t.getName());
			if (card.equals(needle))
				return t;
		}
		return null;
	}
	
	/**
	 * Fetch weapon token from list of tokens which matches the given card 
	 * @param tokens -- list of tokens to search
	 * @param card -- card to match a token to
	 * @return null if no token matches, the first matching token otherwise
	 */
	public Token getWeaponTokenMatching(List<WeaponToken> tokens, Card card) {
		for (Token t : tokens) {
			WeaponCard needle = new WeaponCard(t.getName());
			if (card.equals(needle))
				return t;
		}
		return null;
	}
	
	/**
	 * Make a suggestion of the murder stuffs
	 * @param suggestion
	 * @return true if suggestion is valid and unrefuted, false otherwise
	 */
	public boolean suggest(Set<Card> suggestion) {
		if (!currentPlayer.canSuggest)
			return false;
		
		/* match tokens to cards */
		PlayerToken playerToken = null;
		WeaponToken weaponToken = null;
		Room room = null;
		for (Card c : suggestion) {
			if (c instanceof PlayerCard) {
				playerToken = (PlayerToken)getPlayerTokenMatching(playerTokens, c);
			} else if (c instanceof WeaponCard) {
				weaponToken = (WeaponToken)getWeaponTokenMatching(weaponTokens, c);
			} else if (c instanceof RoomCard) {
				for (Room r : board.getRooms()) {
					if ((new RoomCard(r.getName())).equals(c))
						room = r;
				}
			}
		}
		
		/* accusation must have one of each type to be valid
		 * if any are still null, it's incomplete/invalid */
		if (   playerToken == null
			|| weaponToken == null
			|| room == null) {
			return false;
		}
		
		/* check that the suggesting player is actually in the room they're suggesting */
		if (!room.getOccupants().contains(currentPlayer.getPlayerToken())) {
			return false;
		}
		
		/* valid/sane suggestion: cannot suggest again */
		currentPlayer.canSuggest = false;
		
		/* move the tokens to the room */
		Position playerPos = getNextFreePosition(room);
		board.moveTokenToCell(playerToken, room, playerPos);
		board.moveTokenToCell(weaponToken, room);
		
		/* null-out the evidence field */
		this.evidence = null;
		
		/* find matching evidence from a player's held cards */
		for (Player player : players) {
			for (Card card : suggestion) {
				if (player.getHeldCards().contains(card)) {
					this.evidence = card;
					return false;
				}
			}
		}
		
		/* FIXME probably need to loop through players' decks looking for match to suggestion */
		if (envelopeMatches(suggestion)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get the first card from the last suggestion that proved that suggestion wrong
	 * @return card that proves it wrong, null if unrefuted
	 */
	public Card getEvidence() {
		return evidence;
	}
	
	/**
	 * 
	 * @param room
	 * @return
	 */
	private Position getNextFreePosition(Room room) {
		/* find next free position in the room */
		Position playerPos = null;
		position: for (Position pos : room.getPositions()) {
			/* FIXME do we need to include weapons tokens? */
			for (PlayerToken tok : playerTokens) {
				if (tok.getPosition().equals(pos))
					continue position;
			}
			/* we have one ! */
			return pos;
		}
		if (playerPos == null)
			throw new RuntimeException("Room's full, how the heck did that happen?!");
		
		return null;
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
