package cluedo;

import java.util.List;

public class Room extends Cell {
	String name;
	Room passage;
	List<PlayerToken> occupants; /* FIXME gettser and seeters */
	List<WeaponToken> weapons;

	public Room(String name, Room passage, Cell... neighbours) {
		super(neighbours);
		this.name = name;
		this.passage = passage;
	}

	public List<PlayerToken> getOccupants() {
		return occupants;
	}

	public void setOccupants(List<PlayerToken> occupants) {
		this.occupants = occupants;
	}

	public List<WeaponToken> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<WeaponToken> weapons) {
		this.weapons = weapons;
	}
}
