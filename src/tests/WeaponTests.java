package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cluedo.WeaponToken;

public class WeaponTests {

	@Test
	public void typeEquality() {
		WeaponToken w = new WeaponToken(WeaponToken.Type.CANDLESTICK);
		assertEquals(WeaponToken.Type.CANDLESTICK, w.getType());
	}

	@Test
	public void typeEqualityDual() {
		WeaponToken w1 = new WeaponToken(WeaponToken.Type.ROPE);
		WeaponToken w2 = new WeaponToken(WeaponToken.Type.ROPE);
		assertEquals(w1.getType(), w2.getType());
	}

	/* check equals() method for equality, inequality and symmetry */
	@Test
	public void saneEquals() {
		WeaponToken rope1 = new WeaponToken(WeaponToken.Type.ROPE);
		WeaponToken rope2 = new WeaponToken(WeaponToken.Type.ROPE);
		WeaponToken candle = new WeaponToken(WeaponToken.Type.CANDLESTICK);

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
