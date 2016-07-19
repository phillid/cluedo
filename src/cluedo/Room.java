package cluedo;

public class Room {
	String name;
	Room peer;


	public Room(String name, Room peer) {
		this.name = name;
		this.peer = peer;
	}
}
