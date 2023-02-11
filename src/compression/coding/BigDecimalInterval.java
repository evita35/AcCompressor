package compression.coding;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * A half-open interval [a,b) on the real line, represented by BigDecimal values
 * for lowerBound and length, so that [a,b) == [lowerBound, lowerBound + length)
 *
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class BigDecimalInterval implements Interval {

	private final BigDecimal lowerBound;
	private final BigDecimal length;
	private final double lnLength;


	public BigDecimalInterval(final double lowerBound, final double length) {
		this(BigDecimal.valueOf(lowerBound), BigDecimal.valueOf(length));
	}

	public BigDecimalInterval(final BigDecimal lowerBound, final BigDecimal length) {
		this(lowerBound, length, Math.log(length.doubleValue()));
	}

	public BigDecimalInterval(final BigDecimal lowerBound, final BigDecimal length, final double lnLength) {
		this.lowerBound = lowerBound;
		this.length = length;
		this.lnLength = lnLength;
	}

	@Override
	public BigDecimal getUpperBound() {
		return lowerBound.add(length, MathContext.UNLIMITED);
	}

	@Override
	public String toString() {
		return "BigDecimalInterval(" +
				"lowerBound=" + lowerBound +
				", length=" + length +
				", lnLength=" + lnLength +
				')';
	}

	@Override
	public BigDecimal getLowerBound() {
		return lowerBound;
	}

	public void tostring() {
		System.out.println(lowerBound + " " + length + " " + lnLength);
	}

	@Override
	public BigDecimal getLength() {
		return length;
	}

	@Override
	public double getLnLength() {
		return lnLength;
	}


	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final BigDecimalInterval that = (BigDecimalInterval) o;
		return lowerBound.compareTo(that.lowerBound) == 0
				&& length.compareTo(that.length) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(lowerBound, length);
	}
}
