package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import cluedo.Player;
import cluedo.Position;
import cluedo.cards.Card;
import cluedo.cards.PlayerCard;
import cluedo.token.PlayerToken;
import cluedo.cell.Cell;

public class CardTests {

	
	
	@Test
	public void basicEquality() {
		Card hello1 = new PlayerCard("Hello");
		Card hello2 = new PlayerCard("Hello");
		Card blah = new PlayerCard("asf");
		assertEquals(hello1, hello2);
		assertEquals(hello1, hello1);
		assertEquals(hello2, hello1);
		assertNotEquals(hello1, blah);
	}

}
