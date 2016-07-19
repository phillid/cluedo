package cluedo;

import java.util.List;

import cards.Card;
import cards.PlayerCard;
import cards.RoomCard;
import cards.WeaponCard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
	List<Card> deck = new ArrayList<Card>();
	List<Card> envelope = new ArrayList<Card>();

	public Board() {
		Card[] playerCards = {
				new PlayerCard("Miss Scarlet"),
				new PlayerCard("Colenenenel Mustard"),
				new PlayerCard("Mrs White"),
				new PlayerCard("The Reverend Green"),
				new PlayerCard("Mrs Peacock"),
				new PlayerCard("Professor Plum")
		};
		Card[] roomCards = {
				new RoomCard("Kitchen"),
				new RoomCard("Ball Room"),
				new RoomCard("Conservamatory"),
				new RoomCard("Dining Room"),
				new RoomCard("Billiard Room"),
				new RoomCard("Library"),
				new RoomCard("Lounge"),
				new RoomCard("Hall"),
				new RoomCard("Study")
		};
		Card[] weaponCards = {
				new WeaponCard("Candlestick"),
				new WeaponCard("Dagger"),
				new WeaponCard("Lead Pipe"),
				new WeaponCard("Revolver"),
				new WeaponCard("Rope"),
				new WeaponCard("Spanner"),
		};

		/* build the envelope */
		/* FIXME refactor this maybe? a lot of repetition going on */
		Random rand = new Random();
		int index = rand.nextInt(playerCards.length);
		envelope.add(playerCards[index]);
		index = rand.nextInt(roomCards.length);
		envelope.add(roomCards[index]);
		index = rand.nextInt(weaponCards.length);
		envelope.add(weaponCards[index]);

		/* add all cards to deck including those in envelope */
		deck.addAll(Arrays.asList(playerCards));
		deck.addAll(Arrays.asList(roomCards));
		deck.addAll(Arrays.asList(weaponCards));

		/* subtract the envelope from deck */
		deck.remove(envelope);


		System.err.println("DEBUG: Envelope: "+envelope);
	}

	public static void main(String[] args) {
		new Board();
	}

}
