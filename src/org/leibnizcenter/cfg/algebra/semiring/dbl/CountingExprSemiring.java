package org.leibnizcenter.cfg.algebra.semiring.dbl;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class CountingExprSemiring extends ExpressionSemiring {

	// tropical semiring

	private CountingExprSemiring() {
	}

	public static final CountingExprSemiring INSTANCE = new CountingExprSemiring();

	public static CountingExprSemiring get() {
		return INSTANCE;
	}

	@Override
	public double plus(final double w1, final double w2) {
		return Double.min(w1,w2);
	}

	@Override
	public double times(final double w1, final double w2) {
		return w1 + w2;
	}

	@Override
	public double zero() {
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public double one() {
		return 0.0;
	}

	@Override
	public boolean member(final double m) {
		return m >= 0;
	}

	@Override
	public double fromProbability(final double x) {
		return x > 0 ? one() : zero();
	}

	@Override
	public double toProbability(final double x) {
		return x > 0 ? 1 : 0;
	}

	@Override
	public int compare(final double x, final double y) {
		return Double.compare(x,y);
	}

}
