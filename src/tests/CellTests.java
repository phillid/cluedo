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
import cluedo.Corridor;

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
		Cell c1 = new Corridor();
		Cell c2 = new Corridor(c1);
		c1.addNeighbours(c2);

		assertTrue(c2.isNeighbour(c1));
		assertTrue(c1.isNeighbour(c2));

		assertFalse(c1.isNeighbour(c1));
		assertFalse(c2.isNeighbour(c2));
	}

	@Test
	public void selfNeighbour() {
		Corridor c1 = new Corridor();
		try {
			c1.addNeighbours(c1);
			fail("Adding self as neighbour should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			/* expected, pass */
		}
	}

	@Test
	public void singleOccupant() {
		Corridor c1 = new Corridor();
		assertFalse(c1.isOccupied());
		c1.addOccupant(plumToken);
		assertTrue(c1.isOccupied());
		c1.removeOccupant(plumToken);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void addNullOccupant() {
		Corridor c1 = new Corridor();
		c1.addOccupant(null);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void removeNullOccupant() {
		Corridor c1 = new Corridor();
		c1.removeOccupant(null);
		assertFalse(c1.isOccupied());

		c1.addOccupant(null);
		c1.removeOccupant(null);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void removeEmpty() {
		Corridor c1 = new Corridor();
		c1.removeOccupant(plumToken);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void multipleOccupants() {
		Corridor c1 = new Corridor();
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

	@Test
	public void constructNullNeighbourArray() {
		try {
			Corridor c1 = new Corridor(null);
			fail("Null neighbour array should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			/* expected, pass */
		}
	}

	@Test
	public void addNullNeighbourArray() {
		Corridor c1 = new Corridor();
		try {
			c1.addNeighbours(null);
			fail("Null neighbour array should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			/* expected, pass */
		}
	}

	@Test
	public void addNullNeighbour() {
		Corridor c1 = new Corridor();
		Corridor c2 = new Corridor();
		try {
			c1.addNeighbours(c2, null);
			fail("Null neighbour should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			/* expected, pass */
		}
	}
}
