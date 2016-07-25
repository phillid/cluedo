package cluedo;

import java.util.List;
import java.util.ArrayList;

public class Room extends Cell {
	private String name;
	private Room passage;
	private List<PlayerToken> occupants;
	private List<WeaponToken> weapons;

	public Room(String name, Room passage, Cell... neighbours) {
		super(neighbours);
		this.name = name;
		this.passage = passage;
		this.occupants = new ArrayList<PlayerToken>();
		this.weapons = new ArrayList<WeaponToken>();
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
