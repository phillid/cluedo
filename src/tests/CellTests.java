package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import cluedo.WeaponToken;
import cluedo.PlayerToken;
import cluedo.Player;
import cluedo.cards.*;
import cluedo.Cell;

public class CellTests {

	PlayerToken plumToken;
	Player player;

	@Before
	public void first() {
		plumToken = new PlayerToken("Plum Dude");
		List<Card> held = new ArrayList<Card>();
		held.add(new WeaponCard("Nailgun"));
		Player player = new Player(plumToken, held);

	}

	@Test
	public void singleNeighbour() {
		Cell c1 = new Cell();
		Cell c2 = new Cell(c1);
		c1.addNeighbours(c2);

		assertTrue(c2.isNeighbour(c1));
		assertTrue(c1.isNeighbour(c2));

		assertFalse(c1.isNeighbour(c1));
		assertFalse(c2.isNeighbour(c2));
	}

	@Test
	public void selfNeighbour() {
		Cell c1 = new Cell();
		try {
			c1.addNeighbours(c1);
			fail("Adding self as neighbour should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			/* expected, pass */
		}

	}
}
