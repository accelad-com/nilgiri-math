package com.accelad.math.nilgiri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.function.BiFunction;

import org.junit.Test;

public abstract class AbstractFactoriesTest<X extends Field<X>> {

    private final double testEpsilon;

    protected AbstractFactoriesTest(double testEpsilon) {
        this.testEpsilon = testEpsilon;
    }

    private X generateX(double input) {
        return getFactory().val(input);
    }

    private void testFactoryWithError(BiFunction<AbstractFactory<X>, X, X> function, double input,
            double theoreticalResult) {
        assertRelativeError(theoreticalResult,
                function.apply(getFactory(), generateX(input)).getReal(), testEpsilon);
    }

    private void testFactoryEqual(BiFunction<AbstractFactory<X>, X, X> function, double input,
            double theoreticalResult) {
        assertEquals(theoreticalResult, function.apply(getFactory(), generateX(input)).getReal(),
                0);
    }

    @Test
    public void testAbs() {
        testFactoryEqual(AbstractFactory::abs, -1, 1);
    }

    @Test
    public void testMin() {
        X min = generateX(1);
        X max = generateX(5);

        assertEquals(getFactory().min(min, max), min);
        assertEquals(getFactory().min(max, min), min);
    }

    @Test
    public void testMax() {
        X min = generateX(3);
        X max = generateX(8);

        assertEquals(getFactory().max(min, max), max);
        assertEquals(getFactory().max(max, min), max);
    }

    @Test
    public void testFloor() {
        testFactoryWithError(AbstractFactory::floor, 7.6, 7);
        testFactoryWithError(AbstractFactory::floor, 4.2, 4);
    }

    @Test
    public void testCeil() {
        testFactoryWithError(AbstractFactory::ceil, 7.6, 8);
        testFactoryWithError(AbstractFactory::ceil, 4.2, 5);
    }

    @Test
    public void testRound() {
        testFactoryWithError(AbstractFactory::round, 7.6, 8);
        testFactoryWithError(AbstractFactory::round, 4.2, 4);
    }

    @Test
    public void testLog10() {
        testFactoryWithError(AbstractFactory::log10, 5, 0.6989700043360188);
        testFactoryEqual(AbstractFactory::log10, 100, 2);
        testFactoryEqual(AbstractFactory::log10, 1000, 3);
    }

    @Test
    public void testFlat() {
        testFactoryEqual(AbstractFactory::flat, 0, 0);
        X x = generateX(10);
        for (int i = 0; i < 100; i++) {
            X result = getFactory().flat(x);
            assertTrue(result.getReal() + " is out of bounds",
                    result.getReal() > x.negate().getReal() && result.getReal() < x.getReal());
        }
    }

    @Test
    public void testMc() {
        assertEquals(getFactory().mc(generateX(0), generateX(0)).getReal(), 0, 0);
        X x = generateX(10);
        X y = generateX(5);
        for (int i = 0; i < 100; i++) {
            X result = getFactory().mc(x, y);

            assertTrue(
                    result.getReal() + " is out of bounds (" + (x.getReal() * (1 - y.getReal())) + ", " + (x
                            .getReal() * (1 + y.getReal())) + ")",
                    result.getReal() > (x.getReal() * (1 - y.getReal())) && result.getReal() < (x.getReal() * (1 + y
                            .getReal())));
        }
    }

    @Test
    public void testRand() {
        X x = generateX(1);
        assertEquals(getFactory().rand(x).getReal(), getFactory().rand(x).getReal(), 0);
    }

    @Test
    public void testRandom() {
        X x = generateX(1);
        assertEquals(getFactory().random(x).getReal(), getFactory().random(x).getReal(), 0);
    }

    @Test
    public void testSgn() {
        testFactoryEqual(AbstractFactory::sgn, 0.5, 1);
        testFactoryEqual(AbstractFactory::sgn, 0, 0);
        testFactoryEqual(AbstractFactory::sgn, -0.5, -1);
    }

    @Test
    public void testU() {
        testFactoryEqual(AbstractFactory::u, 0.5, 1);
        testFactoryEqual(AbstractFactory::u, 0, 0);
        testFactoryEqual(AbstractFactory::u, -0.5, 0);
    }

    @Test
    public void testUramp() {
        testFactoryEqual(AbstractFactory::uramp, 0.5, 0.5);
        testFactoryEqual(AbstractFactory::uramp, 0, 0);
        testFactoryEqual(AbstractFactory::uramp, -0.5, 0);
    }

    @Test
    public void testIfx() {
        assertEquals(generateX(2), getFactory().ifx(generateX(0), generateX(1), generateX(2)));
        assertEquals(generateX(1), getFactory().ifx(generateX(0.6), generateX(1), generateX(2)));
    }

    @Test
    public void testPow() {
        assertEquals(generateX(4), getFactory().pow(generateX(2), generateX(2)));
        assertEquals(generateX(8), getFactory().pow(generateX(4), generateX(1.5)));
    }

    @Test
    public void testPwr() {
        assertEquals(generateX(0.3535533905932738),
                getFactory().pwr(generateX(0.5), generateX(1.5)));
        assertEquals(generateX(0.3535533905932738),
                getFactory().pwr(generateX(-0.5), generateX(1.5)));
    }

    @Test
    public void testPwrs() {
        assertEquals(generateX(0.3535533905932738),
                getFactory().pwr(generateX(0.5), generateX(1.5)));
        assertEquals(generateX(-0.3535533905932738),
                getFactory().pwrs(generateX(-0.5), generateX(1.5)));
    }

    @Test
    public void testHypot() {
        assertEquals(generateX(5.6568542494923810), getFactory().hypot(generateX(4), generateX(4)));
        assertEquals(generateX(2.8284271247461903), getFactory().hypot(generateX(2), generateX(2)));
    }

    @Test
    public void testInv() {
        testFactoryEqual(AbstractFactory::inv, 0.4, 1);
        testFactoryEqual(AbstractFactory::inv, 0.6, 0);
    }

    @Test
    public void testBuf() {
        testFactoryEqual(AbstractFactory::buf, 0.4, 0);
        testFactoryEqual(AbstractFactory::buf, 0.6, 1);
    }

    @Test
    public void testAcos() {
        testFactoryWithError(AbstractFactory::acos, -1, Math.PI);
        testFactoryWithError(AbstractFactory::acos, 0, Math.PI / 2);
        testFactoryWithError(AbstractFactory::acos, 1, 0);
    }

    @Test
    public void testAsin() {
        testFactoryWithError(AbstractFactory::asin, -1, -Math.PI / 2);
        testFactoryWithError(AbstractFactory::asin, 0, 0);
        testFactoryWithError(AbstractFactory::asin, 1, Math.PI / 2);
    }

    @Test
    public void testAtan() {
        testFactoryWithError(AbstractFactory::atan, -1, -Math.PI / 4);
        testFactoryWithError(AbstractFactory::atan, 0, 0);
        testFactoryWithError(AbstractFactory::atan, 1, Math.PI / 4);
    }

    @Test
    public void testCosh() throws Exception {
        testFactoryWithError(AbstractFactory::cosh, -1, 1.5430806348152437d);
        testFactoryWithError(AbstractFactory::cosh, 0, 1);
        testFactoryWithError(AbstractFactory::cosh, 1, 1.5430806348152437d);
    }

    @Test
    public void testSinh() throws Exception {
        testFactoryWithError(AbstractFactory::sinh, -1, -1.1752011936438014d);
        testFactoryWithError(AbstractFactory::sinh, 0, 0);
        testFactoryWithError(AbstractFactory::sinh, 1, 1.1752011936438014d);
    }

    @Test
    public void testTanh() throws Exception {
        testFactoryWithError(AbstractFactory::tanh, -1, -0.7615941559557648d);
        testFactoryWithError(AbstractFactory::tanh, 0, 0);
        testFactoryWithError(AbstractFactory::tanh, 1, 0.7615941559557648d);
    }

    @Test
    public void testAcosh() throws Exception {
        testFactoryWithError(AbstractFactory::acosh, 1, 0);
        testFactoryWithError(AbstractFactory::acosh, 2, 1.3169578969248166d);
        testFactoryWithError(AbstractFactory::acosh, 5, 2.2924316695611777d);
    }

    @Test
    public void testAsinh() throws Exception {
        testFactoryWithError(AbstractFactory::asinh, -1, -0.881373587019543);
        testFactoryWithError(AbstractFactory::asinh, 0, 0);
        testFactoryWithError(AbstractFactory::asinh, 1, 0.881373587019543);
    }

    @Test
    public void testAtanh() throws Exception {
        testFactoryWithError(AbstractFactory::atanh, -0.5, -0.5493061443340548);
        testFactoryWithError(AbstractFactory::atanh, 0, 0);
        testFactoryWithError(AbstractFactory::atanh, 0.5, 0.5493061443340548);
    }

    protected abstract AbstractFactory<X> getFactory();

    void assertRelativeError(double expected, double actual, double maxError) {
        if (expected == 0) {
            assertEquals(expected, actual, maxError);
        } else {
            double relativeError = Math.abs(1 - (actual / expected));
            assertTrue(relativeError + " > " + maxError, relativeError <= maxError);
        }
    }
}
