package cluedo.token;

/**
 * A common superclass for all tokens, defining default behaviour.
 */
public abstract class Token {
	private String name;
	private char initial;

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
