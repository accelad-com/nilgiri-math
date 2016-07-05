package com.accelad.math.nilgiri;


public class DoubleRealFactory implements AbstractRealNumberFactory<DoubleReal> {

    private static final DoubleRealFactory m_INSTANCE = new DoubleRealFactory();

    private DoubleRealFactory() {
    }


    public static DoubleRealFactory instance() {
        return m_INSTANCE;
    }


    @Override
    public DoubleReal val(double i_v) {
        return new DoubleReal(i_v);
    }

    private static final DoubleReal m_ZERO = new DoubleReal(0.0);
    private static final DoubleReal m_UNIT = new DoubleReal(1.0);

    @Override
    public DoubleReal zero() {
        return m_ZERO;
    }

    @Override
    public DoubleReal one() {
        return m_UNIT;
    }

    @Override
    public DoubleReal cos(DoubleReal i_x) {
        return new DoubleReal(Math.cos(i_x.doubleValue()));
    }

    @Override
    public DoubleReal sin(DoubleReal i_x) {
        return new DoubleReal(Math.sin(i_x.doubleValue()));
    }

    @Override
    public DoubleReal tan(DoubleReal i_x) {
        return new DoubleReal(Math.tan(i_x.doubleValue()));
    }

    @Override
    public DoubleReal exp(DoubleReal i_x) {
        return new DoubleReal(Math.exp(i_x.doubleValue()));
    }

    @Override
    public DoubleReal log(DoubleReal i_x) {
        return new DoubleReal(Math.log(i_x.doubleValue()));
    }

    @Override
    public DoubleReal pow(DoubleReal i_x, DoubleReal i_y) {
        return new DoubleReal(Math.pow(i_x.doubleValue(), i_y.doubleValue()));
    }

    @Override
    public DoubleReal sqrt(DoubleReal i_x) {
        return new DoubleReal(Math.sqrt(i_x.doubleValue()));
    }

    @Override
    public DoubleReal square(DoubleReal i_x) {
        return new DoubleReal(i_x.doubleValue() * i_x.doubleValue());
    }

    @Override
    public DoubleReal floor(DoubleReal value) {
        return value.floor();
    }

}
