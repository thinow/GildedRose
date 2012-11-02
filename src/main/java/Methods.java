import static java.lang.String.*;

public final class Methods {

	private Methods() {
	}

	public static void mandatory(String name, Object value) {
		if (value == null) {
			throw new IllegalArgumentException(format("%s is mandatory", name));
		}
	}

}
