package cluedo;

import java.util.List;

public class Room {
	String name;
	Room peer;
	List<PlayerToken> occupants;
	List<WeaponToken> weapons;

	public Room(String name, Room peer) {
		this.name = name;
		this.peer = peer;
	}
}
