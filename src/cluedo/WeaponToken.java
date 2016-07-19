package cluedo;

public class WeaponToken {
	public enum Type {
		CANDLESTICK,
		DAGGER,
		LEAD_PIPE,
		REVOLVER,
		ROPE,
		SPANNER
	}

	private Type type;

	public WeaponToken(Type type) {
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeaponToken other = (WeaponToken) obj;
		if (type != other.type)
			return false;
		return true;
	}

}
