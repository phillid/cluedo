package tests;

import static org.junit.Assert.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import cluedo.Board;
import cluedo.cell.Room;
import cluedo.BoardParser;
import cluedo.token.*;

public class BoardTests {
	Board board;
	
	/**
	 * Set up a fresh board before tests
	 */
	@Before
	public void newBoard() throws FileNotFoundException {
		BoardParser bp = new BoardParser();
		Scanner boardScanner = new Scanner(new File("map"));
		boardScanner.useDelimiter("");
		board = bp.parseBoard(boardScanner);
	}
	
	@Test
	public void addWeaponTokenToRoom() {
		WeaponToken cluestick = new WeaponToken("Cluestick");
		Room room = board.getRooms()[0];
		board.moveTokenToCell(cluestick, room);
		assertEquals(1, room.getWeapons().size());
		assertTrue(room.getWeapons().contains(cluestick));
	}
}
