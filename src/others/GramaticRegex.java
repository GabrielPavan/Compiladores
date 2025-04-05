package others;

import java.util.regex.Pattern;

public class GramaticRegex {
	public static final Pattern GenericGramaticRegex = Pattern.compile("\\w+|\\d+|[;=:\\(\\)\\[\\]\\{\\},\\.]");
	public static final Pattern IdentGramaticRegex = Pattern.compile("^[a-zA-Z]+$");
}
