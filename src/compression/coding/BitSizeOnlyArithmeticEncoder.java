package compression.coding;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class BitSizeOnlyArithmeticEncoder implements ArithmeticEncoder {

	double lnLength;

	@Override
	public void encodeNext(final Interval interval) {
		this.lnLength += interval.getLnLength();
	}

	@Override
	public int getFinalPrecision() {
		return (int) (((-lnLength) / Math.log(2.0))+4);
	}


	@Override
	public String getFinalEncoding() {
		throw new UnsupportedOperationException();
	}
}
