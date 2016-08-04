package cluedo;

import java.util.Scanner;

import cluedo.cell.Cell;
import cluedo.cell.Corridor;
import cluedo.cell.Doorway;
import cluedo.cell.Room;


/**
 * Parses in the board from a file.
 *
 */
public class BoardParser {
	private static final int BOARD_HEIGHT = 25;
	private static final int BOARD_WIDTH  = 25;
	
	private Room[] rooms = null;

	public BoardParser() {
		Room study = new Room("Study",0);
		Room kitchen = new Room("Kitchen",7);
		Room lounge = new Room("Lounge",2);
		Room conservatory = new Room("Conservatory",8);

		/* secret passages */
		lounge.addNeighbours(conservatory);
		conservatory.addNeighbours(lounge);

		study.addNeighbours(kitchen);
		kitchen.addNeighbours(study);

		rooms = new Room[9];
		rooms[0] = study;
		rooms[1] = new Room("Hall",1);
		rooms[2] = lounge;
		rooms[3] = new Room("Library",3);
		rooms[4] = new Room("Dining Room",4);
		rooms[5] = new Room("Billiard Room",5);
		rooms[6] = new Room("Ball Room",6);
		rooms[7] = kitchen;
		rooms[8] = conservatory;
	}

	public Board parseBoard(Scanner s) {
		char[][] map = new char[BOARD_WIDTH][BOARD_HEIGHT];
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
		Cell[][] cells = new Cell[BOARD_WIDTH][BOARD_HEIGHT];
		Board board = new Board(cells);
		board.setRooms(rooms);
		/* first pass: fill the cells array */
		for (y = 0; y < BOARD_HEIGHT; y++) {
			for (x = 0; x < BOARD_WIDTH; x++) {
				switch (map[x][y]) {
				case ' ':
				case '\n':
				case '\0':
					break;
				case '.':
					cells[x][y] = new Corridor();
					break;
				case '^':
					System.err.println("Adding north doorway");
					cells[x][y] = new Doorway(Doorway.Direction.NORTH);
					break;
				case 'v':
					System.err.println("Adding south doorway");
					cells[x][y] = new Doorway(Doorway.Direction.SOUTH);
					break;
				case '<':
					System.err.println("Adding west doorway");
					cells[x][y] = new Doorway(Doorway.Direction.WEST);
					break;
				case '>':
					System.err.println("Adding east doorway");
					cells[x][y] = new Doorway(Doorway.Direction.EAST);
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
					System.err.println("Adding room");
					Room room = rooms[map[x][y] - '0'];
					cells[x][y] = room;
					room.addPosition(new Position(x,y));
					break;
				case 'S': /* Miss Scarlet */
				case 'M': /* Colonel Mustard */
				case 'W': /* Mrs White */
				case 'G': /* Mr Green */
				case 'E': /* Mrs Peacock */
				case 'P': /* Professor Plum */
					cells[x][y] = new Corridor();
					board.addStartingPosition(map[x][y], cells[x][y]);
					break;
				default:
					throw new RuntimeException("Syntax error in map: unexpected characgter '"+map[x][y]+"'");
				}
			}
		}
		/* second pass: connect neighbours */
		for (y = 0; y < BOARD_HEIGHT; y++) {
			for(x = 0; x < BOARD_WIDTH; x++) {
				char m = map[x][y];
				Cell c = cells[x][y];
				if (c != null && c instanceof Corridor) {
					/* check neighbours */
					int north = y-1;
					int south = y+1;
					int west = x-1;
					int east = x+1;
					if (north >= 0)
						addNeighbourIfValid(c, cells[x][north]);
					if (south < BOARD_HEIGHT)
						addNeighbourIfValid(c, cells[x][south]);
					if (west >= 0)
						addNeighbourIfValid(c, cells[west][y]);
					if (east < BOARD_WIDTH)
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
					Cell neighbourRoom = cells[roomx][roomy];
					Cell neighbourCorridor = cells[corridorx][corridory];
					if (neighbourCorridor == null || !(neighbourCorridor instanceof Corridor)) {
						throw new RuntimeException("Doorway must connect room and corridor");
					}
					//TODO do the above stuff for the room
					System.err.println("BoardParser: DEBUG: Added doorway to room "+roomNumber);

					rooms[roomNumber].addNeighbours(cells[x][y]);
					neighbourCorridor.addNeighbours(cells[x][y]);
					cells[x][y].addNeighbours(neighbourRoom,neighbourCorridor);

				}
			}
		}

		return board;
	}

	private void addNeighbourIfValid(Cell current, Cell neighbour) {
		if (neighbour != null && neighbour instanceof Corridor)
			current.addNeighbours(neighbour);
	}

}
