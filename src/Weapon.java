
public class Weapon {
	public enum Type {
		CANDLESTICK,
		DAGGER,
		LEAD_PIPE,
		REVOLVER,
		ROPE,
		SPANNER
	}

	private Type type;

	public Weapon(Type type) {
		this.type = type;
	}

	Type getType() {
		return this.type;
	}
}
