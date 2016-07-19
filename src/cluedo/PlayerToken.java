package cluedo;

import java.util.List;

public class PlayerToken {
	String name;
	List<Card> heldCards;

	public PlayerToken(String name, List<Card> heldCards) {
		this.heldCards = heldCards;
		this.name = name;
	}
}
