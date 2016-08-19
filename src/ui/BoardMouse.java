package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import cluedo.Board;
import cluedo.Position;
import cluedo.cell.Cell;

public class BoardMouse implements MouseListener { 
	private GuiClient parent;
	private BoardPanel boardPanel;
	
	
	public BoardMouse(GuiClient gc) {
		this.parent = gc;
		this.boardPanel = gc.getBoardPanel();
	}
	
	public void mouseClicked(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		System.err.println("Mouse click on ("+x+","+y+")");
		if (boardPanel.coordsWithinBoard(x,y)) {
			Position position =  boardPanel.getPositionFromClickCoords(x, y);
			if (boardPanel.getGame().move(position)) {
				System.err.println("Move succeeded");
				parent.update();
			}
		} else {
			System.err.println("Mmouse click was outside board area");
		}
	}
	
	
	/*
	 * Empty methods below; have to implement them to conform to the MouseListener
	 * interface, but we are not interested in triggering behaviour from them
	 */
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}
}
