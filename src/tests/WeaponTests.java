package tests;

import Cluedo.Weapon;
import static org.junit.Assert.*;

import org.junit.Test;

public class WeaponTests {

	@Test
	public void typeEquality() {
		Weapon w = new Weapon(Weapon.Type.CANDLESTICK);
		assertEquals(Weapon.Type.CANDLESTICK, w.getType());
	}

	@Test
	public void typeEqualityDual() {
		Weapon w1 = new Weapon(Weapon.Type.ROPE);
		Weapon w2 = new Weapon(Weapon.Type.ROPE);
		assertEquals(w1.getType(), w2.getType());
	}

	/* check equals() method for equality, inequality and symmetry */
	@Test
	public void saneEquals() {
		Weapon rope1 = new Weapon(Weapon.Type.ROPE);
		Weapon rope2 = new Weapon(Weapon.Type.ROPE);
		Weapon candle = new Weapon(Weapon.Type.CANDLESTICK);

		/* equality to self */
		assertEquals(rope1, rope1);

		/* symmetric equality across separate objects */
		assertEquals(rope1, rope2);
		assertEquals(rope2, rope1);

		/* symmetric inequality */
		assertNotEquals(rope1, candle);
		assertNotEquals(candle, rope1);
	}
}
