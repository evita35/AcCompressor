package org.leibnizcenter.cfg.algebra.semiring.dbl;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class PlainDoubleSemiring extends ExpressionSemiring {

	private PlainDoubleSemiring() {
	}

	public static final PlainDoubleSemiring INSTANCE = new PlainDoubleSemiring();

	public static PlainDoubleSemiring get() {
		return INSTANCE;
	}

	@Override
	public double plus(final double w1, final double w2) {
		return w1 + w2;
	}

	@Override
	public double times(final double w1, final double w2) {
		return w1 * w2;
	}

	@Override
	public double zero() {
		return 0.0;
	}

	@Override
	public double one() {
		return 1.0;
	}

	@Override
	public boolean member(final double m) {
		return 0 <= m && m <= 1;
	}

	@Override
	public double fromProbability(final double x) {
		return x;
	}

	@Override
	public double toProbability(final double x) {
		return x;
	}

	@Override
	public int compare(final double x, final double y) {
		return Double.compare(x,y);
	}
}
