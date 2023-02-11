package compression.coding;

/**
 * Interface for our general abstraction of arithmetic encoding.
 */
public interface ArithmeticEncoder {

    /**
     * Encode the next symbol whose probability is the length of the given
     * interval.
     *
     * @param interval the interval that the next event corresponds to
     */
    void encodeNext(Interval interval);//use encode its more general

    /**
     * @return String of 0 and 1 representing the binary encoding of all encoded
     * symbols
     */
    String getFinalEncoding();

    /**
     * @return length (in bits) of the final encoding
     */
     int getFinalPrecision();
}
