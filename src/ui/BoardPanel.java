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

	/**
	 * Draw the base cell blocks of the board
	 * @param g -- graphics object to draw onto
	 */
	public void drawBase(Graphics g) {
		for (int celly = 0; celly < board.getHeight(); celly++) {
			for (int cellx = 0; cellx < board.getWidth(); cellx++) {
				Cell cell = board.getCellAt(cellx, celly);

				/* muh readability! But ternary statements feel so good */
				Color co = (cell == null) ? Color.BLACK : cellColours.get(cell.getClass()); 
				
				if (co == null) {
					/* no colour defined? set colour to red for error */
					g.setColor(Color.RED);
				} else {
					/* set colour to the one found */
					g.setColor(co);
				}
				
				int x = cellx*CELL_WIDTH;
				int y = celly*CELL_HEIGHT;
				/* draw the main cell */
				g.fillRect(x, y, CELL_WIDTH, CELL_HEIGHT);
			}
		}
	}
	
	/**
	 * Draw the grid overlay onto the board
	 * @param g -- graphics object to draw onto
	 */
	private void drawGrid(Graphics g) {
		/* draw the outlines/grid */
		g.setColor(Color.BLACK);
		for (int x = CELL_WIDTH; x < CELL_WIDTH*board.getWidth(); x+=CELL_WIDTH) {
			g.drawLine(x, 0, x, CELL_HEIGHT*board.getHeight()-1);
		}
		for (int y = CELL_HEIGHT; y < CELL_HEIGHT*board.getHeight(); y+=CELL_HEIGHT) {
			g.drawLine(0, y, CELL_WIDTH*board.getWidth()-1, y);
		}		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		drawBase(g);
		//drawTokens(g);
		drawGrid(g);
	}
}
