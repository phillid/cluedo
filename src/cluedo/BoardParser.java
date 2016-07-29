package cluedo;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardParser {

	private final Room[] rooms = null;

	public BoardParser() {
		// TODO Auto-generated constructor stub
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
			map[x][y] = s.next().charAt(0);
			x++;
		}
		Cell[][] cells = new Cell[25][25];
		/* first pass: fill the cells array */
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				switch (map[i][j]) {
				case ' ':
				case '\n':
				case '\0':
					break;
				case '.':
					cells[i][j] = new Corridor();
					break;
				case '^':
				case 'v':
				case '<':
				case '>':
					/* FIXME rooms */
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
					/* FIXME rooms */
					break;
				case 'S': /* Miss Scarlet? */
				case 'M': /* ??? */
				case 'W': /* Mrs White? */
				case 'P': /* Plum? */
				case 'E': /* ??? */
				case 'G': /* Mr Green? */
					// FIXME starting cells
					System.err.println("Warning: starting cell for '"+map[i][j]+"' is blank Corridor");
					break;
				default:
					throw new RuntimeException("Syntax error in map: unexpected characgter '"+map[i][j]+"'");
				}
			}
		}
		/* second pass: connect neighbours */
		for (int i = 0; i < 25; i++) {
			for(int j = 0; j < 25; j++) {
				Cell c = cells[i][j];
				if (c == null) {
					continue;
				}
				if (c instanceof Corridor) {
					/* check neighbours */
					int north = i-1;
					int south = i+1;
					int west = j-1;
					int east = j+1;
					if (north >= 0)
						addNeighbourIfValid(c, cells[north][j]);
					if (south < 25)
						addNeighbourIfValid(c, cells[south][j]);
					if (west >= 0)
						addNeighbourIfValid(c, cells[i][west]);
					if (east < 25)
						addNeighbourIfValid(c, cells[i][east]);
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
