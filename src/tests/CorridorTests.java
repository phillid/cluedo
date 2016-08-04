package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import cluedo.Player;
import cluedo.cell.Corridor;
import cluedo.token.PlayerToken;

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
		Corridor c = new Corridor(null);
		c.addOccupant(plumToken);
		try {
			c.addOccupant(whiteToken);
		} catch (IllegalStateException e) {
			/* expected, pass */
		}
	}
}
