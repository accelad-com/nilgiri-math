package com.accelad.math.nilgiri;

import com.accelad.math.doubledouble.DoubleDouble;
import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoubleDoubleComplexTest {

    @Test
    public void testInverse() throws Exception {

        DoubleDouble thirteen = DoubleDouble.fromString("13.0");
        DoubleDouble expectedReal = DoubleDouble.TWO.divide(thirteen);
        DoubleDouble minusThree = DoubleDouble.fromString("-3.0");
        DoubleDouble expectedImaginary = minusThree.divide(thirteen);
        DoubleDoubleComplex expected = new DoubleDoubleComplex(expectedReal, expectedImaginary);

        DoubleDoubleComplex ddc = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex result = ddc.inverse();

        assertEquals(expected, result);
    }

    @Test
    public void testDiv() throws Exception {
        DoubleDouble five = DoubleDouble.fromString("5.0");
        DoubleDouble eight = DoubleDouble.fromString("8.0");
        DoubleDouble minusOne = DoubleDouble.ONE.negate();
        DoubleDouble expectedReal = eight.divide(five);
        DoubleDouble expectedImaginary = minusOne.divide(five);
        DoubleDoubleComplex expected = new DoubleDoubleComplex(expectedReal, expectedImaginary);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex ddc2 = new DoubleDoubleComplex(1, 2);
        DoubleDoubleComplex result = ddc1.div(ddc2);

        assertEquals(expected, result);
    }

    @Test
    public void testAbs() throws Exception {
        DoubleDouble expectedReal = DoubleDouble.fromString("13.0").sqrt();
        DoubleDouble expectedImaginary = DoubleDouble.ZERO;
        DoubleDoubleComplex expected = new DoubleDoubleComplex(expectedReal, expectedImaginary);

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
        Apcomplex arbitraryComplex = new Apcomplex(new Apfloat("2"), new Apfloat("3")).precision(31);
        Apcomplex arbitraryResult = ApcomplexMath.log(arbitraryComplex);
        String real = arbitraryResult.real().toString(true);
        String imaginary = arbitraryResult.imag().toString(true);

        DoubleDouble expectedReal = DoubleDouble.fromString(real);
        DoubleDouble expectedImaginary = DoubleDouble.fromString(imaginary);
        DoubleDoubleComplex expected = new DoubleDoubleComplex(expectedReal, expectedImaginary);

        DoubleDouble three = DoubleDouble.fromString("3");
        DoubleDouble two = DoubleDouble.TWO;
        DoubleDoubleComplex complexValue = new DoubleDoubleComplex(two, three);
        DoubleDoubleComplex actual = complexValue.log();

        assertEquals(expected, actual);
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
        DoubleDoubleComplex result = ddc1.mul(4);

        assertEquals(expected, result);
    }

    @Test
    public void testNegate() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(-2, -3);

        DoubleDoubleComplex ddc1 = new DoubleDoubleComplex(2, 3);
        DoubleDoubleComplex result = ddc1.negate();

        assertEquals(expected, result);
    }

    @Test
    public void testCos() {
        Apcomplex arbitraryComplex = new Apcomplex(new Apfloat("2"), new Apfloat("3")).precision(31);
        Apcomplex arbitraryResult = ApcomplexMath.cos(arbitraryComplex);
        String real = arbitraryResult.real().toString(true);
        String imaginary = arbitraryResult.imag().toString(true);

        DoubleDouble expectedReal = DoubleDouble.fromString(real);
        DoubleDouble expectedImaginary = DoubleDouble.fromString(imaginary);
        DoubleDoubleComplex expected = new DoubleDoubleComplex(expectedReal, expectedImaginary);

        DoubleDoubleComplex actual = new DoubleDoubleComplex(DoubleDouble.fromString("2"), DoubleDouble.fromString("3")).cos();

        assertEquals(expected, actual);
    }

    @Test
    public void testSin() {
        Apcomplex arbitraryComplex = new Apcomplex(new Apfloat("2"), new Apfloat("3")).precision(31);
        Apcomplex arbitraryResult = ApcomplexMath.sin(arbitraryComplex);
        String real = arbitraryResult.real().toString(true);
        String imaginary = arbitraryResult.imag().toString(true);

        DoubleDouble expectedReal = DoubleDouble.fromString(real);
        DoubleDouble expectedImaginary = DoubleDouble.fromString(imaginary);
        DoubleDoubleComplex expected = new DoubleDoubleComplex(expectedReal, expectedImaginary);

        DoubleDoubleComplex actual = new DoubleDoubleComplex(DoubleDouble.fromString("2"), DoubleDouble.fromString("3")).sin();

        assertEquals(expected, actual);
    }

    @Test
    public void testTan() {
        Apcomplex arbitraryComplex = new Apcomplex(new Apfloat("2"), new Apfloat("3")).precision(31);
        Apcomplex arbitraryResult = ApcomplexMath.tan(arbitraryComplex);
        String real = arbitraryResult.real().toString(true);
        String imaginary = arbitraryResult.imag().toString(true);

        DoubleDouble expectedReal = DoubleDouble.fromString(real);
        DoubleDouble expectedImaginary = DoubleDouble.fromString(imaginary);
        DoubleDoubleComplex expected = new DoubleDoubleComplex(expectedReal, expectedImaginary);

        DoubleDoubleComplex actual = new DoubleDoubleComplex(DoubleDouble.fromString("2"), DoubleDouble.fromString("3")).tan();

        assertEquals(expected, actual);
    }

    @Test
    public void testPowComplexExponent() throws Exception {
        Apcomplex arbitraryComplex = new Apcomplex(new Apfloat("2", 31), new Apfloat("3", 31));
        Apcomplex exponent = new Apcomplex(new Apfloat("4", 31), new Apfloat("4", 31));
        Apcomplex arbitraryResult = ApcomplexMath.pow(arbitraryComplex, exponent);
        String real = arbitraryResult.real().toString(true);
        String imaginary = arbitraryResult.imag().toString(true);

        DoubleDouble expectedReal = DoubleDouble.fromString(real);
        DoubleDouble expectedImaginary = DoubleDouble.fromString(imaginary);
        DoubleDoubleComplex expected = new DoubleDoubleComplex(expectedReal, expectedImaginary);

        DoubleDoubleComplex actual = new DoubleDoubleComplex(2, 3)
                .pow(new DoubleDoubleComplex(4, 4));

        assertEquals(expected, actual);
    }

    @Test
    public void testSqrt() throws Exception {
        DoubleDoubleComplex expected = new DoubleDoubleComplex(0, 1);

        DoubleDoubleComplex actual = new DoubleDoubleComplex(-1).sqrt();

        assertEquals(expected, actual);
    }
}
