package cluedo.token;

public abstract class Token {
	private String name;
	private char initial;

	public Token(String name) {
		this.name = name;
		this.initial = name.charAt(0);
	}

	public Token(String name, char initial) {
		this.name = name;
		this.initial = initial;
	}

	public String getName() {
		return new String(name);
	}

	public char getInitial() {
		return initial;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		return this.name.equals(((Token)obj).name);
	}
}
