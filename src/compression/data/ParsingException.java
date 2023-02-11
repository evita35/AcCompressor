package compression.data;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class ParsingException extends RuntimeException {

	public ParsingException() {
		super();
	}

	public ParsingException(final String message) {
		super(message);
	}

	public ParsingException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
