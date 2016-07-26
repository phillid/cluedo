package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import cluedo.WeaponToken;
import cluedo.Player;
import cluedo.Room;

public class RoomTests {
	@Test
	public void checkName() {
		Room room = new Room("Foolounge");
		assertEquals("Foolounge", room.getName());
	}

	@Test
	public void singleWeapon() {
		Room room = new Room("Qux Zone");
		room.addWeapon(new WeaponToken(WeaponToken.Type.CANDLESTICK));
		assertTrue(room.getWeapons().contains(new WeaponToken(WeaponToken.Type.CANDLESTICK)));
	}

}
