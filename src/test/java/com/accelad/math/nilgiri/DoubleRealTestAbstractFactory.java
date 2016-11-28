package com.accelad.math.nilgiri;

public class DoubleRealTestAbstractFactory extends AbstractFactoriesTest<DoubleReal> {

    public DoubleRealTestAbstractFactory() {
        super(1e-15);
    }

    @Override
    protected AbstractFactory<DoubleReal> getFactory() {
        return DoubleRealFactory.instance();
    }
}
