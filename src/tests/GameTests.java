package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cluedo.Game;

public class GameTests {
	Game game;
	
	@Before
	public void setupGame() {
		game = new Game();
	}
	
	@Test
	public void getRoll() {
		int original = game.roll();
		int retreived = game.getRoll();
		assertEquals(original, retreived);
	}
	
	@Test
	public void decreaseRoll() {
		game = new Game();
		int original = game.roll();
		assertTrue(game.move("s"));
		int expected = original - 1;
		assertEquals(expected, game.getRoll());
	}
	
}
