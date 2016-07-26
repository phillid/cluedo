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

	public static Board parseBoard(Scanner s) {
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
		for (int i = 0; i < 25; i++) {
			for(int j = 0; j < 25; j++) {
				if (map[i][j] == '.') {
					cells[i][j] = new Corridor();
				}
				//FIXME starting cells
			}
		}
		for (int i = 0; i < 25; i++) {
			for(int j = 0; j < 25; j++) {
				if (cells[i][j] == null) {
					continue;
				}
				if (cells[i][j] instanceof Corridor) {

				}
			}
		}

		return null;
	}

}
