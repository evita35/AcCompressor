package org.leibnizcenter.cfg.algebra.semiring.dbl;

import org.leibnizcenter.cfg.algebra.semiring.dbl.ExpressionSemiring;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class BooleanExprSemiring extends ExpressionSemiring {

	private BooleanExprSemiring() {
	}

	public static final BooleanExprSemiring INSTANCE = new BooleanExprSemiring();

	public static BooleanExprSemiring get() {
		return INSTANCE;
	}

	@Override
	public double plus(final double w1, final double w2) {
		boolean b1 = w1 > 0, b2 = w2 > 0;
		return b1 | b2 ? 1.0 : 0.0;
	}

	@Override
	public double times(final double w1, final double w2) {
		boolean b1 = w1 > 0, b2 = w2 > 0;
		return b1 & b2 ? 1.0 : 0.0;
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
		return true;
	}

	@Override
	public double fromProbability(final double x) {
		return x > 0 ? 1.0 : 0.0;
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
