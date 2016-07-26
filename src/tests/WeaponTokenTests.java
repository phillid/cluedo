package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cluedo.WeaponToken;

public class WeaponTokenTests {

	@Test
	public void basicEquality() {
		WeaponToken w = new WeaponToken("Hammer");
		assertEquals(w, new WeaponToken("Hammer"));
	}

	/* check equals() method for equality, inequality and symmetry */
	@Test
	public void saneEquals() {
		WeaponToken rope1 = new WeaponToken("Twine");
		WeaponToken rope2 = new WeaponToken("Twine");
		WeaponToken candle = new WeaponToken("Nailgun");

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
