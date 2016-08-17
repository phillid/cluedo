package ui;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cluedo.Board;
import cluedo.Game;
import cluedo.cell.*;

public class BoardPanel extends JPanel {
	private static final int CELL_WIDTH = 30;
	private static final int CELL_HEIGHT= 30;
	
	private static HashMap<Class, Color> cellColours = new HashMap<Class, Color>();
	static {
		cellColours.put(Corridor.class, Color.YELLOW);
		cellColours.put(Room.class, Color.BLUE);
		cellColours.put(Doorway.class, Color.BLUE);
	}
	
	private Board board;
	
	public BoardPanel(Board board) {
		this.board = board;
	}
	
	public BoardPanel(Game game) {
		this(game.board);
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int celly = 0; celly < board.getHeight(); celly++) {
			for (int cellx = 0; cellx < board.getWidth(); cellx++) {
				Cell cell = board.getCellAt(cellx, celly);
				Color co = null;
				if (cell != null)
					co = cellColours.get(cell.getClass());
				if (co == null) {
					/* set colour to red for error */
					g.setColor(Color.RED);
				} else {
					/* set colour to the one found */
					g.setColor(co);
				}
				
				int x = cellx*CELL_WIDTH;
				int y = celly*CELL_HEIGHT;
				g.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
			}
		}
	}
}
