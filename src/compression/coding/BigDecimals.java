package compression.coding;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Sebastian Wild (wild@liverpool.ac.uk)
 */
public class BigDecimals {

	/**
	 * @return convert binary code back a BigDecimal value; if the code is "010",
	 * then the value is the binary fraction 0.010 which is 0.25.
	 */
	public static BigDecimal binaryToDecimal(String bCode) {
		BigDecimal res = BigDecimal.ZERO;
		BigDecimal twoToMinusI = halfOf(BigDecimal.ONE); // 1/2 (initially
		for (int i = 0; i < bCode.length(); i++)
		{
			if (bCode.charAt(i) == '1') {
				res = res.add(twoToMinusI); // adds the fractional place value
			}
			// divide by 2
			twoToMinusI = halfOf(twoToMinusI);
		}
		return res;
	}

	/**
	 * @return convert a BigDecimal value in [0,1) to a binary string of the binary digits
	 * after the decimal point, using at the given number of bits.
	 */
	public static String bigDecimalToBinary(BigDecimal x, int nBits) {
		if (x.compareTo(BigDecimal.ZERO) < 0 || x.compareTo(BigDecimal.ONE) >= 0) {
			throw new IllegalArgumentException("x must be in [0,1)");
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nBits; i++) {
			x = x.multiply(BigDecimal.valueOf(2));
			if (x.compareTo(BigDecimal.ONE) >= 0) {
				sb.append('1');
				x = x.subtract(BigDecimal.ONE);
			} else {
				sb.append('0');
			}
		}
		if (x.compareTo(BigDecimal.ZERO) != 0) {
			throw new IllegalArgumentException("Not enough bits to represent x");
		}
		return sb.toString();
	}

	/**
	 * @return the value of x/2 without rounding
	 */
	public static BigDecimal halfOf(BigDecimal x) {
		// When dividing by 2, we need to add 1 to the scale to ensure that the
		// result does not require rounding.
		return x.divide(BigDecimal.valueOf(2), x.scale() + 1, RoundingMode.UNNECESSARY);
	}

	public static String toStringDetailed(BigDecimal x) {
		return x.toString() + " (" + x.unscaledValue() + " * 10^-" + x.scale() + ")";
	}
}
