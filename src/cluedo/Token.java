package cluedo;

public abstract class Token {
	String name;

	public Token(String name) {
		this.name = name;
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
