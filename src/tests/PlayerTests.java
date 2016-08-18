package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import cluedo.Player;
import cluedo.cards.*;
import cluedo.token.PlayerToken;

public class PlayerTests {

	PlayerToken plumToken;
	List<Card> deck;


	@Before
	public void first() {
		plumToken = new PlayerToken("Professor Plum");
		Card[] tempDeck = new Card[]{
				new WeaponCard("Nailgun"),
				new WeaponCard("Hammer"),
				new WeaponCard("Dagger"),
				new WeaponCard("Poison"),
				new WeaponCard("Clue-by-four"),
				new WeaponCard("Jayvuh"),

				new PlayerCard("Builder"),
				new PlayerCard("Plumber"),
				new PlayerCard("Artchitect"),
				new PlayerCard("Programmer"),
				new PlayerCard("Neckbeard"),
				new PlayerCard("Hipster"),

				new RoomCard("The Lab"),
				new RoomCard("Vicbooks"),
				new RoomCard("Revive"),
				new RoomCard("Wishbone"),
				new RoomCard("Malk and Honey"),
				new RoomCard("Library"),

		};
		deck = new ArrayList<Card>(Arrays.asList(tempDeck));
	}

	@Test
	public void basicPlayer() {
		Player p = new Player("Fooname", plumToken, deck);
		assertEquals(p.getHeldCards(), deck);
	}

	@Test
	public void nullToken() {
		try {
			Player p = new Player("Fooname", null, deck);
			fail("creating player with null token should have thrown IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			/* expected, so pass */
		}
	}

	@Test
	public void nullDeck() {
		Player p = new Player("Fooname", plumToken, null);
		if (p.getHeldCards() == null)
			fail("creating player with null token should have resulted in empty deck");
	}
}
