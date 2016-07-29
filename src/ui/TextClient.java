package ui;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import cluedo.BoardParser;
import cluedo.Game;

public class TextClient {

	public TextClient() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Throwable {
		int playerCount = 6; /* FIXME should be asked from user, not set to 6 */
		Game game = new Game(playerCount);
		game.start();


	}
}
