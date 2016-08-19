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
import cluedo.Position;
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
		
		/* get a room and some position inside it */
		Room room = board.getRooms()[5];
		Position pos = new Position(room.getPositions().get(0));
		
		board.moveTokenToCell(cluestick, pos);
		
		/* assertions on the room */
		assertEquals(1, room.getWeapons().size());
		assertTrue(room.getWeapons().contains(cluestick));
		
		/* assertions on the token itself */
		assertEquals(pos, cluestick.getPosition());
	}
	
	@Test
	public void removeWeaponFromRoom() {
		WeaponToken cluestick = new WeaponToken("Clue-by-four");
		Room room1 = board.getRooms()[5];
		Room room2 = board.getRooms()[6];
		Position pos1 = new Position(room1.getPositions().get(0));
		Position pos2 = new Position(room2.getPositions().get(0));
			
		board.moveTokenToCell(cluestick, pos1);
		board.moveTokenToCell(cluestick, pos2);
		
		/* room assertions */
		assertEquals(0, room1.getWeapons().size());
		assertEquals(1, room2.getWeapons().size());
		
		/* token assertions */
		assertEquals(pos2, cluestick.getPosition());
	}
}
