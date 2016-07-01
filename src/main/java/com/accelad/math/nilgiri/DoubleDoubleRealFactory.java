package com.accelad.math.nilgiri;

import com.accelad.math.DoubleDouble;

public class DoubleDoubleRealFactory implements AbstractRealNumberFactory<DoubleDoubleReal> {

    private static final DoubleDoubleRealFactory m_INSTANCE = new DoubleDoubleRealFactory();
    private static final DoubleDoubleReal m_ZERO = new DoubleDoubleReal(DoubleDouble.ZERO);
    private static final DoubleDoubleReal m_UNIT = new DoubleDoubleReal(DoubleDouble.ONE);

    private DoubleDoubleRealFactory() {
    }

    public static DoubleDoubleRealFactory instance() {
        return m_INSTANCE;
    }

    @Override
    public DoubleDoubleReal val(double value) {
        return new DoubleDoubleReal(value);
    }

    @Override
    public DoubleDoubleReal zero() {
        return m_ZERO;
    }

    @Override
    public DoubleDoubleReal one() {
        return m_UNIT;
    }

    @Override
    public DoubleDoubleReal cos(DoubleDoubleReal value) {
        return new DoubleDoubleReal(value.getDoubleDouble().cos());
    }

    @Override
    public DoubleDoubleReal sin(DoubleDoubleReal value) {
        return new DoubleDoubleReal(value.getDoubleDouble().sin());
    }

    @Override
    public DoubleDoubleReal tan(DoubleDoubleReal value) {
        return new DoubleDoubleReal(value.getDoubleDouble().tan());
    }

    @Override
    public DoubleDoubleReal exp(DoubleDoubleReal value) {
        return new DoubleDoubleReal(value.getDoubleDouble().exp());
    }

    @Override
    public DoubleDoubleReal log(DoubleDoubleReal value) {
        return new DoubleDoubleReal(value.getDoubleDouble().log());
    }

    @Override
    public DoubleDoubleReal pow(DoubleDoubleReal value, DoubleDoubleReal i_y) {
        return new DoubleDoubleReal(value.getDoubleDouble().pow(i_y.getDoubleDouble()));
    }

    @Override
    public DoubleDoubleReal sqrt(DoubleDoubleReal value) {
        return new DoubleDoubleReal(value.getDoubleDouble().sqrt());
    }

    @Override
    public DoubleDoubleReal square(DoubleDoubleReal value) {
        return new DoubleDoubleReal(value.getDoubleDouble().pow(2));
    }

    @Override
    public DoubleDoubleReal floor(DoubleDoubleReal value) {
        return value.floor();
    }

}
