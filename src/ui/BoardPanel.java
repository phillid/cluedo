package ui;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cluedo.Board;
import cluedo.Game;
import cluedo.cell.*;

public class BoardPanel extends JPanel {
	private int cellWidth;
	private int cellHeight;
	private int boardWidthPx;
	private int boardHeightPx;
	
	private static HashMap<Class, Color> cellColours = new HashMap<Class, Color>();
	static {
		cellColours.put(Corridor.class, Color.YELLOW);
		cellColours.put(Room.class, Color.BLUE);
		cellColours.put(Doorway.class, Color.BLUE);
	}
	
	private Board board;
	
	/**
	 * board constructor
	 * @param board
	 */
	public BoardPanel(Board board) {
		this.board = board;
	}
	
	/**
	 * Constructor which wraps around BoardPanel(Board)
	 * Constructs a new BoardPanel based on the board inside given game
	 * @param game
	 */
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
				
				int x = cellx*cellWidth;
				int y = celly*cellHeight;
				/* draw the main cell */
				g.fillRect(x, y, cellWidth, cellHeight);
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
		for (int x = 0; x <= boardWidthPx; x+=cellWidth) {
			g.drawLine(x, 0, x, boardWidthPx);
		}
		for (int y = 0; y <= boardHeightPx; y+=cellHeight) {
			g.drawLine(0, y, boardHeightPx, y);
		}		
	}
	
	/**
	 * Initialise cell dimension parameters with values suitable for
	 * a new redraw at the current frame size
	 * @param g -- graphics object that will be drawn to
	 */
	public void beginGraphics(Graphics g) {
		/* find minimum of canvas width or height */
		int boardMin = getWidth();
		if (getHeight() < getWidth())
			boardMin = getHeight();
		
		/* calculate each cell's size to fit into this minimum
		 * dimension, keeping cells square */
		cellHeight = boardMin / board.getHeight();
		cellWidth = boardMin / board.getWidth();
		
		/* set up board pixel size variables */
		boardWidthPx = cellWidth*board.getWidth();
		boardHeightPx = cellHeight*board.getHeight();
		
		/* centre the board on the available screen space */
		int drawBeginX = (getWidth() - boardWidthPx) / 2;
		int drawBeginY = (getHeight() - boardHeightPx) / 2;
		g.translate(drawBeginX, drawBeginY);
	}

	@Override
	public void paintComponent(Graphics g) {
		beginGraphics(g);
		drawBase(g);
		//drawTokens(g);
		drawGrid(g);
	}
}
