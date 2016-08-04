package cluedo.cell;

import java.util.Set;

import cluedo.Position;
import cluedo.token.WeaponToken;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * Represents a room on the playing board
 */
public class Room extends Cell {
	private String name;
	private Set<WeaponToken> weapons;
	private int roomNumber = 0;
	private List<Position> positions;
	
	/**
	 * 
	 * @param name
	 * @param neighbours
	 */
	public Room(String name, List<Position> positions, Cell... neighbours) {
		super(neighbours);
		this.name = name;
		this.positions = positions;
		this.weapons = new HashSet<WeaponToken>();
	}

	/**
	 * Extended constructor supporting entry of a room name and room number
	 * @param name -- Human-friendly name of the room
	 * @param roomNumber -- map-friendly room number
	 * @param neighbours -- cells this room allows travel to
	 */
	public Room(String name, int roomNumber, Cell... neighbours) {
		super(neighbours);
		this.name = name;
		this.roomNumber = roomNumber;
		this.weapons = new HashSet<WeaponToken>();
	}

	/**
	 * Add a weapon token to the room
	 * @param weaponToken -- weapon token to add
	 */
	public void addWeapon(WeaponToken weaponToken) {
		weapons.add(weaponToken);
	}
	
	/**
	 * Remove a weapon token from the room
	 * @param weaponToken -- weapon token to remove
	 */
	public void removeWeapon(WeaponToken weaponToken) {
		weapons.remove(weaponToken);
	}
	
	/**
	 * Get all of the weapon tokens which are currently inside this room
	 * @return Set of weapon tokens in the room
	 */
	public Set<WeaponToken> getWeapons() {
		return new HashSet<WeaponToken>(weapons);
	}

	/**
	 * Getter for the room name 
	 * @return room name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the first position in the position list
	 * @return
	 */
	public Position getPosition() {
		return positions.get(0);
	}

	/**
	 * Add a position to the list of positions the room covers
	 */
	public void addPosition(Position position) {
		positions.add(position);
	}
	
	/**
	 * Get a list of all the positions that this room covers
	 * @return list of positions covered by room
	 */
	public List<Position> getPositions() {
		return new ArrayList<Position>(positions);
	}
	
	/**
	 * Getter for the room number
	 * @return room number
	 */
	public int getRoomNumber() {
		return roomNumber;
	}
}
