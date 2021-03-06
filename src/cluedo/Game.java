package cluedo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
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
 * Provides game logic for the game of Cluedo
 * 
 * @author David Phillips
 * @author Hamish Brown
 * 
 */

public class Game {
	private Board board;
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
	private boolean hasWon;
	
	/**
	 * Game constructor. Set up the player tokens and players
	 */
	public Game(int playerCount) {
		/* set player count field */
		this.playerCount = playerCount;
		
		/* nobody has won yet */
		hasWon = false;
		
		/* set up the board */
		try {
			Scanner s = new Scanner(new File("map"));
			s.useDelimiter("");
			board = new BoardParser().parseBoard(s);
		} catch (FileNotFoundException e) {
			throw new Error(e);
		}

		/* set up the player tokens */
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
		
		/* set up the weapons */
		for (WeaponToken wt : weaponTokens) {
			while (true) {
				int roomNum = die.nextInt(board.getRooms().length);
				Room room = board.getRooms()[roomNum];
				if (room.getWeapons().isEmpty()) {
					board.moveTokenToCell(wt, getNextFreePosition(room));
					break;
				}
			}
		}
		
		/* put player tokens in their starting positions */
		for (PlayerToken pt : playerTokens) {
			Cell starting = board.getStartingPositions().get(pt.getInitial());
			Position startingPos = starting.getPosition();
			pt.setPosition(startingPos);
			starting.addOccupant(pt);
		}

		/* exempt non-playing players from playing 😛 */ 
		for (int i = playerCount; i < players.size(); i++)
			players.get(i).isPlaying = false;
		
	}

	/**
	 * Get a unique COPY of the player tokens in the game. 
	 * @return
	 */
	public List<PlayerToken> getPlayerTokens() {
		return new ArrayList<PlayerToken>(playerTokens);
	}
	
	/**
	 * Get a unique COPY of the weapon tokens in the game
	 * @return
	 */
	public List<WeaponToken> getWeaponTokens() {
		return new ArrayList<WeaponToken>(weaponTokens);
	}
	
	/**
	 * Get a COPY of the available player tokens
	 * @return
	 */
	public List<PlayerToken> getAvailablePlayerTokens() {
		List<PlayerToken> avail = new ArrayList<PlayerToken>(playerTokens);
		for (Player p : players) {
			avail.remove(p.getPlayerToken());
		}
		return avail;
	}
	
	/**
	 * Add a player to the game who has the specified token and name
	 * @param name -- real life name of the players
	 * @param pt -- token the player will play with 
	 * @return true on success, false if token already taken
	 */
	public boolean addPlayer(String name, PlayerToken pt) {
		/* check an existing entered player doesn't have that token */ 
		if (!getAvailablePlayerTokens().contains(pt))
			return false;
		
		Player player = new Player(name, pt, new ArrayList<Card>());
		
		players.add(player);
		
		return true;
	}
		
	/**
	 * Prepare an envelope and deal the deck to the players
	 */
	public void start() {
		/* sort the players according to their game order, not the order they were added */
		players.sort((a,b) ->
				playerTokens.indexOf(a.getPlayerToken())
				- playerTokens.indexOf(b.getPlayerToken())); 

		nextPlayer();
		makeEnvelope();
		dealToPlayers();
	}
	
	/**
	 * Advance the current player to the next in line
	 * If no current player, initialise it to the 0th.
	 */
	public void nextPlayer() throws GameFinishedException {
		/*  */
		if (currentPlayer == null) {
			currentPlayer = players.get(0);
			return;
		}
		int length = players.size();
		int index = players.indexOf(currentPlayer);
		
		int playCount = 0;
		do {
			playCount++;
			index++;
			index %= length;
			currentPlayer = players.get(index);
		} while (currentPlayer.isPlaying == false && playCount != players.size());
		
		/* if no players were playing, the game has ended */
		if (playCount == players.size())
			throw new GameFinishedException();
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

		System.err.println("DEBUG: Envelope: "+envelope);
	}

	/**
	 * Deal the entirety of the deck to the players
	 */
	public void dealToPlayers() {
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
	
	/* FIXME adapt code and remove this method; duplicated in Game (?) */ 
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
		return board.getCellAt(pt.getPosition()) instanceof Room;
	}
	
	/**
	 * Get the cell that the current player's occupying
	 * @return cell current player is in
	 */
	public Cell getCurrentPlayerCell() {
		PlayerToken pt = currentPlayer.getPlayerToken();
		return board.getCellAt(pt.getPosition());
	}
	
	/**
	 * Move the current player's token to cell, if it's accessible
	 * with the current dice roll
	 * @param cell
	 * @return true if moved, false if invalid
	 */
	public boolean move(Position position) {
		if (roll == 0) {
			System.err.println("Game: Cannot zippy move on 0 roll");
			return false;
		}
		
		Map<Cell, Integer> accessible = getAccessibleCells();
		Cell cell = board.getCellAt(position);
		Cell oldCell = getCurrentPlayerCell();
		
		if (oldCell == cell) {
			System.err.println("Game: Not allowing move to same room as current");
			return false;
		}
		
		if (!accessible.containsKey(cell)) {
			System.err.println("Game: Desired cell not reachable");
			return false;
		}
		
		/* if we're exiting a room by going ONTO
		 * doorway, push through if feasible*/
		if (oldCell instanceof Room && cell instanceof Doorway && oldCell.isNeighbour(cell)) {
			int x = position.getX();
			int y = position.getY();
			switch (((Doorway)cell).getDirection()){
				case NORTH: y++; break;
				case EAST : x--; break;
				case SOUTH: y--; break;
				case WEST : x++; break;
				default:
					throw new RuntimeException("Wut");
			}
			position = new Position(x, y);
			
			/* try to perform push-through and move to immediate-outside cell */
			if (board.getCellAt(position).isOccupied())
				return false;
				
			board.moveTokenToCell(currentPlayer.getPlayerToken(), position);
			
		} else if (cell instanceof Doorway) {
			/* regular room entry -> push into room through doorway*/
			roll = 0;
			int x = position.getX();
			int y = position.getY();
			/* push through doorway */
			switch (((Doorway)cell).getDirection()){
				case NORTH: y--; break;
				case EAST : x++; break;
				case SOUTH: y++; break;
				case WEST : x--; break;
				default:
					throw new RuntimeException("Wut");
			}
			position = new Position(x, y);
			
			Room room = (Room)board.getCellAt(position);
			currentPlayer.canSuggest = true;
			position = getNextFreePosition(room);
		} else {
			/* non-room transition: update the remaining dice roll */
			roll -= accessible.get(cell);
		}
		
		/* do the actual "force-move" */
		board.moveTokenToCell(currentPlayer.getPlayerToken(), position);
		
		/* handle special case of secret passage */
		if (oldCell instanceof Room && cell instanceof Room)
			currentPlayer.canSuggest = true;
		
		/* end turn if no roll left and not inside a room */
		if (!(board.getCellAt(position) instanceof Room) && roll == 0) {
			this.nextPlayer();
			this.roll();
		}
		return true;
	}
	
	/**
	 * Get a list of cells accessible by the current player (excluding rooms) 
	 * within the distance allowed by the current roll.
	 * 
	 * @return Map of accessible cells and the distance to them
	 */
	public Map<Cell,Integer> getAccessibleCells() {
		Map<Cell,Integer> dists = new HashMap<>(); //distance to each cell from the current player's cell
		Deque<Cell> toVisit = new ArrayDeque<>(); //queue of cells to look at next
		Set<Cell> visited = new HashSet<>(); //cells we've already visited (so we dont follow loops)
		Cell current = getCurrentPlayerCell(); //The cell we're looking at right now
		toVisit.addFirst(current);
		dists.put(current, 0);
		
		/* special case: cannot move at all */
		if (roll == 0)
			return dists;
		
		if (current instanceof Room) {
			for (Cell n : current.getNeighbours()) {
				if (n instanceof Doorway) {
					dists.put(n, 0);
					toVisit.addFirst(n);
				} else if (n instanceof Room) {
					dists.put(n, roll);
				}
			}
		}
		
		while(!toVisit.isEmpty()) {
			current = toVisit.removeLast();
			visited.add(current); //Mark this cell as visited
			for (Cell neighbour : current.getNeighbours()) {
				if (neighbour instanceof Corridor && neighbour.isOccupied()) {
					continue;
				}
				int newDist = dists.get(current)+1; //Get the distance from the start cell to this neighbour via the current cell
				if (!(neighbour instanceof Room)) { //Dont do rooms, just their doorways
					if(!dists.containsKey(neighbour)) { //If we dont know the distance to this neighbour,
						dists.put(neighbour, newDist); //memorise it
					} else {
						if (dists.get(neighbour) > newDist) { //If we can get to this neighbour in a shorter distance via this cell instead of via a cell checked earlier,
							dists.put(neighbour,newDist); //memorise the new distance
						}
					}
					if (!visited.contains(neighbour) && newDist < roll ) { //If this neighbour isnt already visited and isnt at the edge of the range,
						toVisit.addFirst(neighbour); //add it to the queue of cell to check
					}
				}
			}
		}
		return dists;
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
		evidence = null;
		
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
			System.err.println("Failed to parse suggestion: "+playerToken+" "+weaponToken+" "+room);
			return false;
		}
		
		/* check that the suggesting player is actually in the room they're suggesting */
		if (!room.getOccupants().contains(currentPlayer.getPlayerToken())) {
			System.err.println("Debug: error: remote suggestion disallowed");
			return false;
		}
		
		/* valid/sane suggestion: cannot suggest again */
		currentPlayer.canSuggest = false;
		
		/* move the player token to the room */
		Position playerPos = getNextFreePosition(room);
		board.moveTokenToCell(playerToken, playerPos);
		
		/* move the weapon token to the room */
		Position tokenPos = getNextFreePosition(room);
		board.moveTokenToCell(weaponToken, tokenPos);
		
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
			List<Token> allTokens = new ArrayList<Token>();
			allTokens.addAll(playerTokens);
			allTokens.addAll(weaponTokens);
			for (Token tok : allTokens) {
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
	 * Sets the internal hasWon state to true if the player is correct
	 * Much the same as a suggestion, but much simpler and clear cut.
	 * This doesn't require the player to be in that room, and that
	 * failure/incorrectness results in that player sitting out for the rest of the game
	 * @param accusation
	 * @return true if accusation is correct, false in all other cases
	 */
	public boolean accuse(Set<Card> accusation) {
		if (envelopeMatches(accusation)) {
			/* hooray! */
			hasWon = true;
			return true;
		}
		
		/* lolno.jpg, your accusation was incorrect, buddy */
		currentPlayer.isPlaying = false;
		return false;
	}

	/**
	 * Getter for the game board
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Check if the game has been won yet, or if all players have left
	 * @return true if game is still in motion, false otherwise
	 */
	public boolean isPlaying() {
		/* has the game been won? */
		if (hasWon)
			return false;
		
		/* has anyone not been excluded? */
		for (Player p : players) {
			if (p.isPlaying)
				return true;
		}
		/* nope, everyone excluded; everyone lost */
		return false;
	}
}
