package cluedo;

import java.util.Set;
import java.util.HashSet;

public class Room extends Cell {
	private String name;
	private Set<WeaponToken> weapons;

	public Room(String name, Cell... neighbours) {
		super(neighbours);
		this.name = name;
		this.weapons = new HashSet<WeaponToken>();
	}

	public void addWeapon(WeaponToken weaponToken) {
		// TODO Auto-generated method stub

	}

	public Set<WeaponToken> getWeapons() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
