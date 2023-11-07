package uo.mp.lab.util.check;

public class ArgumentChecks {

	private static final String message = "Something went wrong ...";
	
	public static void isTrue(boolean condition) {
		if (!condition)
			throw new IllegalArgumentException(message);
	}
	
	public static void isTrue(boolean condition, String msg) {
		if (!condition)
			throw new IllegalArgumentException(msg);
	}
	
	public static void checkNotNull(Object o, String msg) {
		if (o == null)
			throw new NullPointerException(msg);
	}
	
	
}
