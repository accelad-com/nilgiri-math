package com.accelad.math.nilgiri;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DoubleDoubleRealTest {

    @Test
    public void testEquals() throws Exception {
        DoubleDoubleReal first = new DoubleDoubleReal(12);
        DoubleDoubleReal second = new DoubleDoubleReal(12);
        DoubleDoubleReal third = new DoubleDoubleReal(11);

        assertTrue(first.equals(second));
        assertFalse(first.equals(third));
    }
}
