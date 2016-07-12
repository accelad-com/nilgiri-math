package com.accelad.math.nilgiri;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DoubleDoubleComplexTest {

    @Test
    public void testInverse() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(2.0 / 13.0, -3.0 / 13.0);

        DoubleDoubleComplex ddc = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex result = ddc.inverse();

        assertEquals(expected.getReal(), result.getReal(), 1e-12);
        assertEquals(expected.getImaginary(), result.getImaginary(), 1e-12);
    }

    @Test
    public void testDiv() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(8.0 / 5.0, -1.0 / 5.0);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex ddc2 = new DoubleDoubleComplex(1, 2);
        DoubleDoubleComplex result = ddc1.div(ddc2);

        assertEquals(expected, result);
    }

    @Test
    public void testAbs() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(Math.sqrt(13.0), 0);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex result = ddc1.abs();

        assertEquals(expected, result);
    }

    @Test
    public void testMulDoubleDoubleComplex() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(-4, 7);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex ddc2 = new DoubleDoubleComplex(1, 2);
        DoubleDoubleComplex result = ddc1.mul(ddc2);

        assertEquals(expected, result);
    }

    @Test
    public void testPow() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(-46, 9);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex result = ddc1.pow(3);

        assertEquals(expected, result);
    }

    @Test
    public void testLog() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(1.282474678730, 0.982793723247);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex result = ddc1.log();

        assertEquals(expected, result);
    }

    @Test
    public void testPlus() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(3, 5);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex ddc2 = new DoubleDoubleComplex(1, 2);
        DoubleDoubleComplex result = ddc1.plus(ddc2);

        assertEquals(expected, result);
    }

    @Test
    public void testMinus() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(1, 1);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex ddc2 = new DoubleDoubleComplex(1, 2);
        DoubleDoubleComplex result = ddc1.minus(ddc2);

        assertEquals(expected, result);
    }

    @Test
    public void testMulLong() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(8, 12);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex result = ddc1.mul(4l);

        assertEquals(expected, result);
    }

    @Test
    public void testNegate() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(-2, -3);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex result = ddc1.negate();

        assertEquals(expected, result);
    }

}