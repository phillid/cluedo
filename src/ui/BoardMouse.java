package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import cluedo.Board;
import cluedo.Position;
import cluedo.cell.Cell;

public class BoardMouse implements MouseListener { 
	private BoardPanel boardPanel;
	
	public BoardMouse(BoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}
	
	public void mouseClicked(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		System.err.println("Mouse click on ("+x+","+y+")");
		if (boardPanel.coordsWithinBoard(x,y)) {
			Position position =  boardPanel.getPositionFromClickCoords(x, y);
			if (boardPanel.getGame().move(position)) {
				System.err.println("Move succeeded");
				boardPanel.updateHighlights();
				boardPanel.repaint();
			}
		} else {
			System.err.println("Mmouse click was outside board area");
		}
	}
	
	

	
	/*
	 * DEAD METHODS LIE BELOW
	 * Interface MouseListener requires that we provide these
	 * methods, but we won't listen to them
	 */
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		/* nothing*/
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		/* nothing */
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		/* nothing */
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		/* nothing */
	}
}
