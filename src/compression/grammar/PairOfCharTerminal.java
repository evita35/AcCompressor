package compression.grammar;

import org.leibnizcenter.cfg.category.terminal.Terminal;
import org.leibnizcenter.cfg.token.Token;

import java.util.Objects;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public final class PairOfCharTerminal implements Terminal<PairOfChar> {
	private final PairOfChar chars;

	public PairOfCharTerminal(final PairOfChar chars) {
		this.chars = chars;
	}

	@Override
	public boolean hasCategory(final Token<PairOfChar> token) {
		return chars.equals(token.obj);
	}

	public PairOfChar getChars() {
		return chars;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final PairOfCharTerminal that = (PairOfCharTerminal) o;
		return chars.equals(that.chars);
	}

	@Override
	public int hashCode() {
		return Objects.hash(chars);
	}

	@Override
	public String toString() {
		return chars.toString();
	}


}
