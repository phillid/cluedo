package cluedo.cell;

import java.util.Set;

import cluedo.token.WeaponToken;

import java.util.HashSet;


/**
 * Represents a room on the playing board
 *
 *
 */
public class Room extends Cell {
	private String name;
	private Set<WeaponToken> weapons;

	private int roomNumber = 0;

	public Room(String name, Cell... neighbours) {
		super(neighbours);
		this.name = name;
		this.weapons = new HashSet<WeaponToken>();
	}

	public Room(String name, int roomNumber, Cell... neighbours) {
		super(neighbours);
		this.name = name;
		this.roomNumber = roomNumber;
		this.weapons = new HashSet<WeaponToken>();
	}

	public void addWeapon(WeaponToken weaponToken) {
		weapons.add(weaponToken);

	}

	public Set<WeaponToken> getWeapons() {
		return new HashSet<WeaponToken>(weapons);
	}

	public String getName() {
		return name;
	}

	public int getRoomNumber() {
		return roomNumber;
	}
}
