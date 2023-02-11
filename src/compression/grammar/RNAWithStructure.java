package compression.grammar;

import org.leibnizcenter.cfg.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class RNAWithStructure {

	public final String primaryStructure, secondaryStructure;
	public final String name;

	public RNAWithStructure(final String primaryStructure, final String secondaryStructure) {
		this(primaryStructure, secondaryStructure, "N/A");
	}

	public RNAWithStructure(final String primaryStructure, final String secondaryStructure, final String name) {
		this.name = name;

		if (primaryStructure.length() != secondaryStructure.length()) {
			System.out.println("Length of Primary Structure " + primaryStructure.length() + " secondary" +
					"structure length " + secondaryStructure.length());
			throw new IllegalArgumentException();
		}
		this.primaryStructure = primaryStructure;
		this.secondaryStructure = secondaryStructure;
	}

	//to do rename to getNumberOfBases
	public int getNumberOfBases() {
		return primaryStructure.length();
	}

	public List<Token<PairOfChar>> asTokens() {
		List<Token<PairOfChar>> tokens = new ArrayList<>();
		int n = primaryStructure.length();
		for (int i = 0; i < n; ++i) {
			tokens.add(Token.from(new PairOfChar(primaryStructure.charAt(i), secondaryStructure.charAt(i))));
		}
		return tokens;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final RNAWithStructure that = (RNAWithStructure) o;
		return primaryStructure.equalsIgnoreCase(that.primaryStructure) && secondaryStructure.equalsIgnoreCase(that.secondaryStructure);
	}

	@Override
	public int hashCode() {
		return Objects.hash(primaryStructure, secondaryStructure);
	}

	@Override
	public String toString() {
		return String.format(this.primaryStructure + " \n" + this.secondaryStructure + "\n");
	}

	public static RNAWithStructure from(List<Token<PairOfChar>> tokens) {
		StringBuilder primary = new StringBuilder();
		StringBuilder secondary = new StringBuilder();
		for (Token<PairOfChar> token : tokens) {
			primary.append(token.obj.asTerminal().getChars().getPry());
			secondary.append(token.obj.asTerminal().getChars().getSec());
		}
		return new RNAWithStructure(primary.toString(), secondary.toString());
	}

}
