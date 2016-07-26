package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import cluedo.Player;
import cluedo.token.PlayerToken;
import cluedo.Corridor;

public class CorridorTests {

	PlayerToken plumToken, whiteToken;
	Player player;

	@Before
	public void first() {
		plumToken = new PlayerToken("Plum Dude");
		whiteToken = new PlayerToken("Mrs White");
	}

	@Test
	public void onlyOneOccupant() {
		Corridor c = new Corridor();
		c.addOccupant(plumToken);
		try {
			c.addOccupant(whiteToken);
		} catch (IllegalStateException e) {
			/* expected, pass */
		}
	}
}
