package others;

import java.util.regex.Pattern;

public class GramaticRegex {
	public static final Pattern GenericGramaticRegex = Pattern.compile("\\w+|\\d+|[;=:\\(\\)\\[\\]\\{\\},\\.<\\>\\+\\-\\.]");
	public static final Pattern IdentGramaticRegex = Pattern.compile("^[a-zA-Z][a-zA-Z_]{0,19}$");
	public static final Pattern NintGramaticRegex = Pattern.compile("^(0|[1-9][0-9]{0,8})$");
	public static final Pattern NrealGramaticRegex = Pattern.compile("^([1-9]?[0-9]{1,9}|1000000000)\\.([0-9]{2})$");
}
