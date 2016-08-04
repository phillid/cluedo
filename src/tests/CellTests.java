package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import cluedo.Player;
import cluedo.token.PlayerToken;
import cluedo.cell.Cell;

public class CellTests {

	PlayerToken plumToken, whiteToken;
	Player player;

	@Before
	public void first() {
		plumToken = new PlayerToken("Plum Dude");
		whiteToken = new PlayerToken("Mrs White");
	}

	@Test
	public void singleNeighbour() {
		Cell c1 = new Cell(null);
		Cell c2 = new Cell(null, c1);
		c1.addNeighbours(c2);

		assertTrue(c2.isNeighbour(c1));
		assertTrue(c1.isNeighbour(c2));

		assertFalse(c1.isNeighbour(c1));
		assertFalse(c2.isNeighbour(c2));
	}

	@Test
	public void selfNeighbour() {
		Cell c1 = new Cell(null);
		try {
			c1.addNeighbours(c1);
			fail("Adding self as neighbour should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			/* expected, pass */
		}
	}

	@Test
	public void singleOccupant() {
		Cell c1 = new Cell(null);
		assertFalse(c1.isOccupied());
		c1.addOccupant(plumToken);
		assertTrue(c1.isOccupied());
		c1.removeOccupant(plumToken);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void addNullOccupant() {
		Cell c1 = new Cell(null);
		c1.addOccupant(null);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void removeNullOccupant() {
		Cell c1 = new Cell(null);
		c1.removeOccupant(null);
		assertFalse(c1.isOccupied());

		c1.addOccupant(null);
		c1.removeOccupant(null);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void removeEmpty() {
		Cell c1 = new Cell(null);
		c1.removeOccupant(plumToken);
		assertFalse(c1.isOccupied());
	}

	@Test
	public void multipleOccupants() {
		Cell c1 = new Cell(null);
		c1.addOccupant(plumToken);
		c1.addOccupant(whiteToken);
		assertTrue(c1.isOccupied());
		assertTrue(c1.getOccupants().contains(plumToken));
		assertTrue(c1.getOccupants().contains(whiteToken));
		assertEquals(2, c1.getOccupants().size());
	}

	@Test
	public void constructNullNeighbourArray() {
		try {
			Cell c1 = new Cell(null);
			fail("Null neighbour array should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			/* expected, pass */
		}
	}

	@Test
	public void addNullNeighbourArray() {
		Cell c1 = new Cell(null);
		try {
			c1.addNeighbours(null);
			fail("Null neighbour array should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			/* expected, pass */
		}
	}

	@Test
	public void addNullNeighbour() {
		Cell c1 = new Cell(null);
		Cell c2 = new Cell(null);
		try {
			c1.addNeighbours(c2, null);
			fail("Null neighbour should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			/* expected, pass */
		}
	}
}
