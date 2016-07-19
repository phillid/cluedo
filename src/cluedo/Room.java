package cluedo;

public class Room {
	String name;
	Room peer;
	PlayerToken[] occupants;
	WeaponToken[] weapons;

	public Room(String name, Room peer) {
		this.name = name;
		this.peer = peer;
	}
}
