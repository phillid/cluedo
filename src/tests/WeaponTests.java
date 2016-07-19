package tests;

import Cluedo.Weapon;
import static org.junit.Assert.*;

import org.junit.Test;

public class WeaponTests {

	@Test
	public void weaponTypeEquality() {
		Weapon w = new Weapon(Weapon.Type.CANDLESTICK);
		assertEquals(Weapon.Type.CANDLESTICK, w.getType());
	}

	@Test
	public void weaponTypeEqualityDual() {
		Weapon w1 = new Weapon(Weapon.Type.ROPE);
		Weapon w2 = new Weapon(Weapon.Type.ROPE);
		assertEquals(w1.getType(), w2.getType());
	}

	/* check equals() method for equality */
	@Test
	public void weaponEquality() {
		Weapon w1 = new Weapon(Weapon.Type.ROPE);
		Weapon w2 = new Weapon(Weapon.Type.ROPE);
		assertTrue(w1.equals(w2));
	}

	@Test
	public void weaponInequality() {
		Weapon w1 = new Weapon(Weapon.Type.CANDLESTICK);
		Weapon w2 = new Weapon(Weapon.Type.ROPE);
		assertNotEquals(w1, w2);
	}
}
