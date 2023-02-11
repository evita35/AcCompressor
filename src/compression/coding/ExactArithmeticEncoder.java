/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression.coding;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static compression.coding.BigDecimals.bigDecimalToBinary;
import static compression.coding.BigDecimals.halfOf;

/**
 * Simple hackish implementation of arithmetic encoder that uses BigDecimal intervals
 */
public class ExactArithmeticEncoder implements ArithmeticEncoder {

    private BigDecimalInterval interval;

    public ExactArithmeticEncoder() {
        interval = new BigDecimalInterval(BigDecimal.ZERO, BigDecimal.ONE, 0.0);
    }

    @Override
    public void encodeNext(Interval ruleInterval) {
        interval = zoomIntoSubinterval(interval, ruleInterval);
    }


    @Override
    public String getFinalEncoding() {
        return encodeFinalInterval(interval);
    }

    @Override
    public int getFinalPrecision() {
        return encodeFinalInterval(interval).length();
    }

    public int getFinalPrecisionCheapBound(BigDecimalInterval interval) {
        // NB: The +4 is a conservative estimate to make sure we have enough precision
        return  (int) (((-interval.getLnLength())/Math.log(2.0)) + 4);
    }


    public static BigDecimal ceilDyadic(BigDecimal x, int l) {
        // compute ceiling(x*2^l)/2^l
        BigDecimal twoToL = BigDecimal.valueOf(2).pow(l);
        BigDecimal xTimesTwoToL = x.multiply(twoToL);
        BigDecimal ceiling = xTimesTwoToL.setScale(0, RoundingMode.CEILING);
        return ceiling.divide(twoToL, l, RoundingMode.UNNECESSARY);
    }

    public static BigDecimal floorDyadic(BigDecimal x, int l) {
        // compute floor(x*2^l)/2^l
        BigDecimal twoToL = BigDecimal.valueOf(2).pow(l);
        BigDecimal xTimesTwoToL = x.multiply(twoToL);
        BigDecimal floor = xTimesTwoToL.setScale(0, RoundingMode.FLOOR);
        return floor.divide(twoToL, l, RoundingMode.UNNECESSARY);
    }


    public String encodeFinalInterval(BigDecimalInterval interval) {
        int l = -1;
        BigDecimal midpoint = interval.getLowerBound().add(halfOf(interval.getLength()));
        // Increase l until the dyadic l-interval around midpoint is contained in interval
        BigDecimal ceilDyadic, floorDyadic, twoToMinusLP2 = BigDecimal.valueOf(0.125);
        do {
            ++l;
            ceilDyadic = ceilDyadic(midpoint, l);
            floorDyadic = floorDyadic(midpoint, l);
            twoToMinusLP2 = halfOf(twoToMinusLP2);
            if (ceilDyadic.compareTo(floorDyadic) == 0) {
                BigDecimal m = midpoint.add(halfOf(twoToMinusLP2));
                ceilDyadic = ceilDyadic(m, l);
                floorDyadic = floorDyadic(m, l);
            }
        } while (floorDyadic.compareTo(interval.getLowerBound()) < 0 ||
                 ceilDyadic.compareTo(interval.getUpperBound()) > 0);
        return bigDecimalToBinary(floorDyadic, l);
    }

    /**
     * @return subinterval of currentInterval corresponding to the given relative
     * subinterval (of [0,1)), i.e.,
     * if the current interval is [a,b) and the subinterval is [c,d), then
     * the returned interval is [a+c(b-a),a+d(b-a)).
     */
    public static BigDecimalInterval zoomIntoSubinterval(final Interval currentInterval, final Interval relativeInterval) {
        BigDecimal newLen = currentInterval.getLength().multiply(relativeInterval.getLength());
        BigDecimal newLB = currentInterval.getLowerBound().add(relativeInterval.getLowerBound().multiply(currentInterval.getLength()));
        double newLnLen = currentInterval.getLnLength() + relativeInterval.getLnLength();
        return new BigDecimalInterval(newLB, newLen, newLnLen);
    }


}
