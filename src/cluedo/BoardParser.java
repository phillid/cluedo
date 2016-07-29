package cluedo;

import java.util.Scanner;

public class BoardParser {

	private Room[] rooms = null;

	public BoardParser() {
		rooms = new Room[9];
		rooms[0] = new Room("Study");
		rooms[1] = new Room("Hall");
		rooms[2] = new Room("Lounge");
		rooms[3] = new Room("Library");
		rooms[4] = new Room("Dining Room");
		rooms[5] = new Room("Billiard Room");
		rooms[6] = new Room("Ball Room");
		rooms[7] = new Room("Kitchen");
		rooms[8] = new Room("Conservatory");
	}

	public Board parseBoard(Scanner s) {
		char[][] map = new char[25][25];
		int x = 0;
		int y = 0;
		while (s.hasNext()) {
			char next = s.next().charAt(0);
			if (next == '\n') {
				y++;
				x = 0;
				continue;
			}
			map[x][y] = next;
			x++;
		}
		Cell[][] cells = new Cell[25][25];
		/* first pass: fill the cells array */
		for (y = 0; y < 25; y++) {
			for (x = 0; x < 25; x++) {
				switch (map[x][y]) {
				case ' ':
				case '\n':
				case '\0':
					break;
				case '.':
					cells[x][y] = new Corridor();
					break;
				case '^':
				case 'v':
				case '<':
				case '>':
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
					/* ignore these in this pass */
					break;
				case 'S': /* Miss Scarlet? */
				case 'M': /* ??? */
				case 'W': /* Mrs White? */
				case 'P': /* Plum? */
				case 'E': /* ??? */
				case 'G': /* Mr Green? */
					// FIXME starting cells
					System.err.println("BoardParser: Warning: starting cell for '"+map[x][y]+"' is blank Corridor");
					cells[x][y] = new Corridor();
					break;
				default:
					throw new RuntimeException("Syntax error in map: unexpected characgter '"+map[x][y]+"'");
				}
			}
		}
		/* second pass: connect neighbours */
		for (y = 0; y < 25; y++) {
			for(x = 0; x < 25; x++) {
				char m = map[x][y];
				Cell c = cells[x][y];
				if (c == null) {
					continue;
				}
				if (c instanceof Corridor) {
					/* check neighbours */
					int north = y-1;
					int south = y+1;
					int west = x-1;
					int east = x+1;
					if (north >= 0)
						addNeighbourIfValid(c, cells[x][north]);
					if (south < 25)
						addNeighbourIfValid(c, cells[x][south]);
					if (west >= 0)
						addNeighbourIfValid(c, cells[west][y]);
					if (east < 25)
						addNeighbourIfValid(c, cells[east][y]);
				}
				if ("^v<>".indexOf(m) != -1) {
					int roomx = x;
					int roomy = y;
					int corridorx = x;
					int corridory = y;
					switch (m) {
					/* doorways */
					case '^':
						roomy--; corridory++;
						break;
					case 'v':
						roomy++; corridory--;
						break;
					case '<':
						roomx--; corridorx++;
						break;
					case '>':
						roomx++; corridorx--;
						break;
					}
					if (roomx < 0 || roomx > 24 || roomy < 0 || roomy > 24) {
						throw new RuntimeException("Dooway cannot lead off board");
					}
					int roomNumber = map[roomx][roomy] - '0';
					if (roomNumber < 0 || roomNumber > 8) {
						throw new RuntimeException("Invalid room number '"+map[roomx][roomy]+"'");
					}
					Cell neighbour = cells[corridorx][corridory];
					if (neighbour == null || !(neighbour instanceof Corridor)) {
						throw new RuntimeException("Doorway must connect room and corridor");
					}
					rooms[roomNumber].addNeighbours(neighbour);
					neighbour.addNeighbours(rooms[roomNumber]);
				}
			}
		}

		return null;
	}
	
	private void addNeighbourIfValid(Cell current, Cell neighbour) {
		if (neighbour != null && neighbour instanceof Corridor)
			current.addNeighbours(neighbour);	
	}

}
