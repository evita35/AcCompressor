
import compression.coding.ArithmeticEncoder;
import compression.coding.BigDecimalInterval;
import compression.coding.BigDecimals;
import compression.coding.ExactArithmeticDecoder;
import compression.coding.ExactArithmeticEncoder;
import compression.coding.Interval;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class ExactArithmeticCoderTest {


	@Test
	public void testEncode() {
		//NB: BigDecimalIntervals are instantiated with 2 values: lowerbound and length of interval respectively NOT lowerbound and upperbound
		ArithmeticEncoder enc = new ExactArithmeticEncoder();
		enc.encodeNext(new BigDecimalInterval(BigDecimal.ZERO, BigDecimal.valueOf(1. / 10)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.ZERO, BigDecimal.ONE));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.25)));
		String B = enc.getFinalEncoding();
		Assert.assertEquals("0001000", B);

		enc = new ExactArithmeticEncoder();
		enc.encodeNext(new BigDecimalInterval(BigDecimal.ZERO, BigDecimal.valueOf(1. / 10)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.ZERO, BigDecimal.ONE));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5)));
		B = enc.getFinalEncoding();
		Assert.assertEquals("00010", B);

		enc = new ExactArithmeticEncoder();
		enc.encodeNext(new BigDecimalInterval(BigDecimal.ZERO, BigDecimal.valueOf(1. / 5)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5)));
		B = enc.getFinalEncoding();
		Assert.assertEquals("0010", B);
	}

	@Test
	public void testEncodeFinalInterval() {
		ExactArithmeticEncoder enc1 = new ExactArithmeticEncoder();
		Assert.assertEquals("111", enc1.encodeFinalInterval(new BigDecimalInterval(BigDecimal.valueOf(0.8), BigDecimal.valueOf(0.2))));
	}

	@Test(expected = ArithmeticException.class)
	public void testDivide() {
		BigDecimal one = BigDecimal.ONE;
		BigDecimal three = BigDecimal.valueOf(3);
		BigDecimal third = one.divide(three);
		System.out.println("third = " + third);
	}


	@Test
	public void testCeilDyadic() {
		Assert.assertEquals(BigDecimal.valueOf(0.5),
				ExactArithmeticEncoder.ceilDyadic(BigDecimal.valueOf(0.25), 1));
		Assert.assertEquals(BigDecimal.valueOf(11. / 32),
				ExactArithmeticEncoder.ceilDyadic(BigDecimal.valueOf(0.337895623), 5));
		Assert.assertEquals(BigDecimal.valueOf(0.0),
				ExactArithmeticEncoder.floorDyadic(BigDecimal.valueOf(0.25), 1));
		Assert.assertEquals(BigDecimal.valueOf(10. / 32).doubleValue(),
				ExactArithmeticEncoder.floorDyadic(BigDecimal.valueOf(0.337895623), 5).doubleValue(), 0);
		Assert.assertEquals(BigDecimal.valueOf(1),
				ExactArithmeticEncoder.ceilDyadic(BigDecimal.valueOf(0.25), 0));

	}

	/**
	 * Return a List of k uniform subintervals of [0,1)
	 */
	private static List<Interval> getUniformSubintervals(int k) {
		List<Interval> intervals = new java.util.ArrayList<Interval>(k);
		BigDecimal length = BigDecimal.ONE.divide(BigDecimal.valueOf(k));
		for (int i = 0; i < k; i++)
			intervals.add(new BigDecimalInterval(BigDecimal.valueOf(i).multiply(length), length));
		return intervals;
	}

	@Test
	public void testEncodeDecode() {
		// Encode a sequence of intervals with ExactArithmeticEncoder
		// and decode the resulting string with ExactArithmeticDecoder again
		// and check if the decoded intervals are the same as the original ones
		ArithmeticEncoder enc = new ExactArithmeticEncoder();
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(37./100), BigDecimal.valueOf(1. / 100)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(99./100), BigDecimal.valueOf(1. / 100)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(0./100), BigDecimal.valueOf(1. / 100)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(17./100), BigDecimal.valueOf(1. / 100)));
		String B = enc.getFinalEncoding();
		System.out.println("B = " + B);

		// Decode the string B
		ExactArithmeticDecoder dec = new ExactArithmeticDecoder(B);
		Interval interval = dec.decodeNext(getUniformSubintervals(100));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(37./100), BigDecimal.valueOf(1. / 100)), interval);
		interval = dec.decodeNext(getUniformSubintervals(100));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(99./100), BigDecimal.valueOf(1. / 100)), interval);
		interval = dec.decodeNext(getUniformSubintervals(100));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(0./100), BigDecimal.valueOf(1. / 100)), interval);
		interval = dec.decodeNext(getUniformSubintervals(100));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(17./100), BigDecimal.valueOf(1. / 100)), interval);

	}

	@Test
	public void testEncodeDecodeDyadic() {
		// Encode a sequence of intervals with ExactArithmeticEncoder
		// and decode the resulting string with ExactArithmeticDecoder again
		// and check if the decoded intervals are the same as the original ones
		ArithmeticEncoder enc = new ExactArithmeticEncoder();
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(0./2), BigDecimal.valueOf(1. / 2)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(1./2), BigDecimal.valueOf(1. / 2)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(1./2), BigDecimal.valueOf(1. / 2)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(0./2), BigDecimal.valueOf(1. / 2)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(1./2), BigDecimal.valueOf(1. / 2)));
		enc.encodeNext(new BigDecimalInterval(BigDecimal.valueOf(0./2), BigDecimal.valueOf(1. / 2)));
		String B = enc.getFinalEncoding();
		System.out.println("B = " + B);

		// Decode the string B
		ExactArithmeticDecoder dec = new ExactArithmeticDecoder(B);
		Interval interval = dec.decodeNext(getUniformSubintervals(2));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(0./2), BigDecimal.valueOf(1. / 2)), interval);
		interval = dec.decodeNext(getUniformSubintervals(2));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(1./2), BigDecimal.valueOf(1. / 2)), interval);
		interval = dec.decodeNext(getUniformSubintervals(2));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(1./2), BigDecimal.valueOf(1. / 2)), interval);
		interval = dec.decodeNext(getUniformSubintervals(2));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(0./2), BigDecimal.valueOf(1. / 2)), interval);
		interval = dec.decodeNext(getUniformSubintervals(2));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(1./2), BigDecimal.valueOf(1. / 2)), interval);
		interval = dec.decodeNext(getUniformSubintervals(2));
		Assert.assertEquals(new BigDecimalInterval(BigDecimal.valueOf(0./2), BigDecimal.valueOf(1. / 2)), interval);

	}

}

