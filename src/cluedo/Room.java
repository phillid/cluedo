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
}
