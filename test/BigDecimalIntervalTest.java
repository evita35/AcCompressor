/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import compression.coding.BigDecimalInterval;
import compression.coding.BigDecimals;
import compression.coding.Interval;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class BigDecimalIntervalTest {
    
    public BigDecimalIntervalTest() {
    }
    
    @Test
    public void testGetInterval() {
        Interval aInterval = new BigDecimalInterval(BigDecimal.ZERO,BigDecimal.valueOf(0.5));
        Interval bInterval = new BigDecimalInterval(BigDecimal.valueOf(0.5), BigDecimal.ONE);
        
        Assert.assertEquals(aInterval, Interval.getIntervalContaining(BigDecimal.valueOf(0.2),
                Arrays.asList(aInterval, bInterval)));
    }

    @Test
    public void testBinaryToDecimal() {
        Assert.assertEquals(BigDecimal.valueOf(0.25), BigDecimals.binaryToDecimal("010"));
        Assert.assertEquals(BigDecimal.valueOf(0.34375), BigDecimals.binaryToDecimal("01011"));
    }


    @Test
    public void bigDecimalToBinary() {
        Assert.assertEquals("010", BigDecimals.bigDecimalToBinary(BigDecimal.valueOf(0.25), 3));
        Assert.assertEquals("01011", BigDecimals.bigDecimalToBinary(BigDecimal.valueOf(0.34375), 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void bigDecimalToBinaryTooFew() {
        BigDecimals.bigDecimalToBinary(BigDecimal.valueOf(0.34375),4);
    }
}
