package cluedo.token;

import cluedo.Position;

/**
 * A common superclass for all tokens, defining default behaviour.
 * 
 * @author David Phillips
 * @author Hamish Brown
 */
public abstract class Token {
	private String name;
	private char initial;
	private int x;
	private int y;

	/**
	 * Basic lazy constructor with automatic initial generation
	 * @param name
	 */
	public Token(String name) {
		this.name = name;
		this.initial = name.charAt(0);
	}

	/**
	 * Extended constructor with specification of both the name and initial
	 * @param name -- name of the token
	 * @param initial -- initial of the token for the map
	 */
	public Token(String name, char initial) {
		this.name = name;
		this.initial = initial;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return new String(name);
	}

	/**
	 * 
	 * @return
	 */
	public char getInitial() {
		return initial;
	}

	/**
	 * Hashcode wrapper
	 * The initial doesn't come into the equals
	 */
	@Override
	public int hashCode() {
		return name.hashCode() ^ initial;
	}

	
	/**
	 * Get the X value on the board
	 * @return x value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the Y value on the board
	 * @return y value
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set the X value
	 * @param x value
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Set the Y value
	 * @param y value
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Set the player token's X and Y to those specified in the argument pos
	 * @param pos -- the position to set to
	 */
	public void setPosition(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}

	/**
	 * Get the position of the player token
	 * @return
	 */
	public Position getPosition() {
		return new Position(x,y);
	}
	
	/**
	 * We consider a token a wrapper around string.
	 * The initial is used for identification only, so it doesn't come into it
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		Token t = (Token)obj;
		return this.initial == t.initial && this.name.equals(t.name);
	}
}
