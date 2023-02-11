package compression.coding;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents an interval of the form [lowerBound, upperBound) with length
 * upperBound - lowerBound.
 *
 */
public interface Interval {

    BigDecimal getUpperBound();

	BigDecimal getLength();

	BigDecimal getLowerBound();

	double getLnLength();

	String toString();

    default boolean contains(BigDecimal x) {
        return getLowerBound().compareTo(x) <= 0 && getUpperBound().compareTo(x) > 0;
    }

    static Interval getIntervalContaining(BigDecimal x, List<Interval> intervalList) {
        for (Interval interval : intervalList) {
            if (interval.contains(x)) return interval;
        }
        throw new IllegalArgumentException("Interval not found!");
    }
}
