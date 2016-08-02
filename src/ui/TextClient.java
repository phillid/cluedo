package ui;

import cluedo.Game;

public class TextClient {

	public TextClient() {
		Game game = new Game();
		game.start();
		
		game.board.show();
	}
	
	public static void main(String[] args) throws Throwable {
		new TextClient();
	}
}
