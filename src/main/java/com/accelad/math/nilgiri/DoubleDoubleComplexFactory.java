package com.accelad.math.nilgiri;

public class DoubleDoubleComplexFactory
        implements AbstractFactory<DoubleDoubleComplex> {

    private static final DoubleDoubleComplexFactory m_INSTANCE = new DoubleDoubleComplexFactory();
    private static final DoubleDoubleComplex ZERO = new DoubleDoubleComplex(0.0, 0.0);
    private static final DoubleDoubleComplex ONE = new DoubleDoubleComplex(1.0, 0.0);


    private DoubleDoubleComplexFactory() {
    }

    public static DoubleDoubleComplexFactory instance() {
        return m_INSTANCE;
    }

    @Override
    public DoubleDoubleComplex zero() {
        return ZERO;
    }

    @Override
    public DoubleDoubleComplex one() {
        return ONE;
    }

    @Override
    public DoubleDoubleComplex val(double value) {
        return new DoubleDoubleComplex(value);
    }

    @Override
    public DoubleDoubleComplex cos(DoubleDoubleComplex value) {
        return value.cos();
    }

    @Override
    public DoubleDoubleComplex sin(DoubleDoubleComplex value) {
        return value.sin();
    }

    @Override
    public DoubleDoubleComplex tan(DoubleDoubleComplex value) {
        return value.tan();
    }

    @Override
    public DoubleDoubleComplex exp(DoubleDoubleComplex value) {
        return value.exp();
    }

    @Override
    public DoubleDoubleComplex log(DoubleDoubleComplex value) {
        return value.log();
    }

    @Override
    public DoubleDoubleComplex pow(DoubleDoubleComplex value, DoubleDoubleComplex pow) {
        return value.pow(pow);
    }

    @Override
    public DoubleDoubleComplex sqrt(DoubleDoubleComplex value) {
        return value.sqrt();
    }

    @Override
    public DoubleDoubleComplex square(DoubleDoubleComplex value) {
        return value.pow(2);
    }

    @Override
    public DoubleDoubleComplex floor(DoubleDoubleComplex value) {
        throw new UnsupportedOperationException();
    }
}
