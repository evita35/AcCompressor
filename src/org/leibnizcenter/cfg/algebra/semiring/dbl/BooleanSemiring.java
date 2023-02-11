package org.leibnizcenter.cfg.algebra.semiring.dbl;

import org.leibnizcenter.cfg.algebra.semiring.Semiring;
import org.leibnizcenter.cfg.algebra.semiring.dbl.DblSemiring;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public final class BooleanSemiring implements Semiring<Boolean> {

	@Override
	public Boolean plus(final Boolean w1, final Boolean w2) {
		return w1 | w2;
	}

	@Override
	public Boolean times(final Boolean w1, final Boolean w2) {
		return w1 & w2;
	}

	@Override
	public Boolean zero() {
		return false;
	}

	@Override
	public Boolean one() {
		return true;
	}
}
