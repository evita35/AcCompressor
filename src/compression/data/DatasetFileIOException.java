package compression.data;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class DatasetFileIOException extends RuntimeException {

	public DatasetFileIOException(final String message) {
		super(message);
	}

	public DatasetFileIOException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DatasetFileIOException(final Throwable cause) {
		super(cause);
	}
}
