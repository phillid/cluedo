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

	PlayerToken plumToken, whiteToken, colonelToken;
	Player player;

	@Before
	public void first() {
		plumToken = new PlayerToken("Plum Dude");
		whiteToken = new PlayerToken("Mrs White");
		colonelToken = new PlayerToken("That Colonel Guy");
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

	@Test
	public void singleOccupant() {
		Cell c1 = new Cell();
		assertFalse(c1.isOccupied());
		c1.addOccupant(plumToken);
		assertTrue(c1.isOccupied());
		c1.removeOccupant(plumToken);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void addNullOccupant() {
		Cell c1 = new Cell();
		c1.addOccupant(null);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void removeNullOccupant() {
		Cell c1 = new Cell();
		c1.removeOccupant(null);
		assertFalse(c1.isOccupied());

		c1.addOccupant(null);
		c1.removeOccupant(null);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void removeEmpty() {
		Cell c1 = new Cell();
		c1.removeOccupant(plumToken);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void multipleOccupants() {
		Cell c1 = new Cell();
		c1.addOccupant(plumToken);
		try {
			c1.addOccupant(whiteToken);
			fail("Adding multiple occupants should have thrown IllegalStateException");
		} catch (IllegalStateException e) {
			assertTrue(c1.isOccupied());
			assertTrue(c1.getOccupants().contains(plumToken));
			assertEquals(1, c1.getOccupants().size());
			/* expected, pass */
		}
	}
}
