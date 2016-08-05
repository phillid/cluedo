package tests;

import static org.junit.Assert.*;

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
	
}
