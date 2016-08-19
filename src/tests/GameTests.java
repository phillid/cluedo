package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cluedo.Game;
import cluedo.Player;
import cluedo.token.PlayerToken;

public class GameTests {
	Game game;
	
	@Before
	public void setupGame() {
		game = new Game(6);
		List<PlayerToken> avail = game.getAvailablePlayerTokens();
		for (PlayerToken pt : avail)
			game.addPlayer("Name", pt);
		game.start();
	}
	
	@Test
	public void getRoll() {
		int original = game.roll();
		int retreived = game.getRoll();
		assertEquals(original, retreived);
	}
	
	@Test
	public void decreaseRoll() {
		int original = game.roll();
		assertTrue(game.move("s"));
		int expected = original - 1;
		assertEquals(expected, game.getRoll());
	}
	
	@Test
	public void advancePlayer() {
		Player next = game.getCurrentPlayer();
		assertEquals(next.getPlayerToken(), new PlayerToken("Miss Scarlet", 'S'));
		game.nextPlayer();
		next = game.getCurrentPlayer();
		assertEquals(next.getPlayerToken(), new PlayerToken("Colonel Mustard", 'M'));
		game.nextPlayer();
		next = game.getCurrentPlayer();
		assertEquals(next.getPlayerToken(), new PlayerToken("Mrs White", 'W'));
		game.nextPlayer();
		next = game.getCurrentPlayer();
		assertEquals(next.getPlayerToken(), new PlayerToken("The Reverend Green", 'G'));
		game.nextPlayer();
		next = game.getCurrentPlayer();
		assertEquals(next.getPlayerToken(), new PlayerToken("Mrs Peacock", 'E'));
		game.nextPlayer();
		next = game.getCurrentPlayer();
		assertEquals(next.getPlayerToken(), new PlayerToken("Professor Plum", 'P'));
	}
	
	/**
	 * check that the Game is actually sorting the player order to match
	 * the game rules' dictated order (miss scarlet first, then around the board)
	 * regardless of the order players were added to the game
	 */
	@Test
	public void checkPlayerSorting() {
		game = new Game(3);
		List<PlayerToken> playerTokens = game.getPlayerTokens();
		game.addPlayer("First", playerTokens.get(2));
		game.addPlayer("Second", playerTokens.get(1));
		game.addPlayer("Third", playerTokens.get(4));
		
		game.start();
		
		assertEquals("Second", game.getCurrentPlayer().getName());
		game.nextPlayer();
		
		assertEquals("First", game.getCurrentPlayer().getName());
		game.nextPlayer();
		
		assertEquals("Third", game.getCurrentPlayer().getName());
		game.nextPlayer();
	}
}
