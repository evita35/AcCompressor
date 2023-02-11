/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression.coding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ExactArithmeticDecoder implements ArithmeticDecoder {

    /**
     * stores the encoded real value x in [0,1) initially given in binary; over the course
     * of the decoding process, this value will be modified according to the intervals we
     * "move" into.
     *
     * encodedDecimal is the lower bound of a dyadic interval.
     */
    private BigDecimal encodedDecimal;

    public ExactArithmeticDecoder(String encodedBits) {
        encodedDecimal = BigDecimals.binaryToDecimal(encodedBits);
        // Increase the scale to leave some slack for rounding.
        encodedDecimal = encodedDecimal.setScale(encodedBits.length()+3, RoundingMode.UNNECESSARY);
    }

    private void updateDecoded(Interval interval) {
        // Since we are maintaining the lower endpoint of the dyadic interval,
        // we need to make sure we don't round it downwards, otherwise we might
        // end up with the smaller interval.
        encodedDecimal = encodedDecimal.subtract(interval.getLowerBound()).divide(interval.getLength(), RoundingMode.CEILING);
    }

    @Override
    public Interval decodeNext(List<Interval> options) {
        Interval interval = Interval.getIntervalContaining(encodedDecimal, options);
        updateDecoded(interval);
        return interval;
    }

}
