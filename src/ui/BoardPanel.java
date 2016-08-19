package ui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import cluedo.Board;
import cluedo.Game;
import cluedo.Position;
import cluedo.cell.*;
import cluedo.token.PlayerToken;

/**
 * Panel for displaying the game board of a cluedo.Game
 * 
 * @author David Phillips
 * @author Hamish Brown
 *
 */
public class BoardPanel extends JPanel {
	private int cellWidth;
	private int cellHeight;
	private int boardWidthPx;
	private int boardHeightPx;
	
	private Set<Cell> highlights = null;
	
	private static HashMap<Class, Color> cellColours = new HashMap<Class, Color>();
	private static HashMap<Character, Color> playerTokenColours = new HashMap<Character, Color>();
	static {
		cellColours.put(Corridor.class, new Color(0xFF, 0xFF, 0xDD));
		cellColours.put(Room.class, new Color(0x99, 0xCC, 0xFF));
		cellColours.put(Doorway.class, new Color(0x99, 0xCC, 0xFF));
		playerTokenColours.put('S', Color.RED);
		playerTokenColours.put('M', Color.YELLOW);
		playerTokenColours.put('W', Color.WHITE);
		playerTokenColours.put('G', Color.GREEN);
		playerTokenColours.put('P', new Color(0xFF, 0x00, 0xFF));
		playerTokenColours.put('E', Color.BLUE);
	}
	
	private Board board;
	private Game game;
	
	/**
	 * Constructor which wraps around BoardPanel(Board)
	 * Constructs a new BoardPanel based on the board inside given game
	 * @param game
	 */
	public BoardPanel(Game game) {
		this.game = game;
		this.board = game.getBoard();
	}
	
	/**
	 * Get the game this BoardPanel is based on
	 * @return
	 */
	public Game getGame() {
		return game;
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
				
				/* highlight accessible cells in pink */
				if (highlights != null && highlights.contains(cell)) {
					if (cell instanceof Room) {
						g.setColor(Color.ORANGE);
					} else {
						g.setColor(Color.PINK);
					}
				}
				
				int x = cellx*cellWidth;
				int y = celly*cellHeight;
				/* draw the main cell */
				g.fillRect(x, y, cellWidth, cellHeight);
				
				g.setColor(Color.BLACK);
			
				/* special case: doorways need their direction drawn */
				if (cell instanceof Doorway) {
					switch (((Doorway)cell).getDirection()) {
					case EAST:
					case WEST:
						g.fillRect(x, y, cellWidth, 5);
						g.fillRect(x, y+cellHeight-5, cellWidth, 5);
						break;
						
					case NORTH:
					case SOUTH:
						g.fillRect(x, y, 5, cellHeight);
						g.fillRect(x+cellWidth-5, y, 5, cellHeight);
						break;
					default:
						throw new IllegalStateException();
					}
				}
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
	 * Get the cell object at specific screen/pixel X and Y value on the BoardPanel 
	 * @param x -- x pixel value
	 * @param y -- y pixel value
	 * @return cell covering pixel (x,y)
	 */
	public Cell getCellFromClickCoords(int x, int y) {
		return board.getCellAt(getPositionFromClickCoords(x, y));
	}
	
	/**
	 * Determine if the specified coordinate is within the graphical
	 * board on this BoardPanel
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean coordsWithinBoard(int x, int y) {
		/* remove translation offset */
		x -= (getWidth() - boardWidthPx) / 2;
		y -= (getHeight() - boardHeightPx) / 2;
		
		return (y >= 0 && y < boardHeightPx
			    && x >= 0 && x < boardWidthPx);
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
	
	/**
	 * Draw the tokens onto the board
	 * @param g
	 */
	public void drawTokens(Graphics g) {
		List<PlayerToken> pts = game.getPlayerTokens();
		for (PlayerToken pt : pts) { 
			Position pos = pt.getPosition();
			int x = pos.getX()*cellWidth;
			int y = pos.getY()*cellHeight;
			
			Color col = playerTokenColours.get(pt.getInitial());
			
			g.setColor(col);
			g.fillOval(x, y, cellWidth, cellHeight);
			g.setColor(Color.BLACK);
			g.drawOval(x, y, cellWidth, cellHeight);
			//g.drawString(Character.toString(pt.getInitial()), x, y);
		}
		
	}

	@Override
	public void paintComponent(Graphics g) {
		beginGraphics(g);
		drawBase(g);
		drawTokens(g);
		drawGrid(g);
	}

	public Position getPositionFromClickCoords(int x, int y) {
		/* remove translation offset from coordinate */
		x -= (getWidth() - boardWidthPx) / 2;
		y -= (getHeight() - boardHeightPx) / 2;
		
		/* divide down */
		x /= cellWidth;
		y /= cellHeight;
		return new Position(x, y);
	}

	/**
	 * Update the set of locally stored movement highlights
	 */
	public void updateHighlights() {
		highlights = game.getAccessibleCells().keySet();
	}
	
	/**
	 * Update and redraw the boardpanel according to the game state
	 */
	public void update() {
		updateHighlights();
		repaint();		
	}
}
