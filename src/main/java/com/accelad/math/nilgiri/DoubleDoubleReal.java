package com.accelad.math.nilgiri;

import com.accelad.math.DoubleDouble;
import com.google.common.base.Objects;

public class DoubleDoubleReal implements RealNumber<DoubleDoubleReal> {

    private DoubleDouble doubleDouble;

    public DoubleDoubleReal() {
        doubleDouble = DoubleDouble.ZERO;
    }

    public DoubleDoubleReal(double value) {
        doubleDouble = new DoubleDouble(value);
    }

    public DoubleDoubleReal(String doubleString) {
        doubleDouble = new DoubleDouble(doubleString);
    }

    public DoubleDoubleReal(DoubleDouble bigDecimal) {
        this.doubleDouble = bigDecimal;
    }

    public void set(double value) {
        doubleDouble = new DoubleDouble(value);
    }

    public double doubleValue() {
        return doubleDouble.doubleValue();
    }

    public DoubleDouble getDoubleDouble() {
        return doubleDouble;
    }

    public DoubleDouble modulus() {
        return doubleDouble.abs();
    }

    @Override
    public String toString() {
        return doubleDouble.toString();
    }

    @Override
    public DoubleDoubleReal inverse() {
        return new DoubleDoubleReal(DoubleDouble.ONE.divide(doubleDouble));
    }

    @Override
    public DoubleDoubleReal negate() {
        return new DoubleDoubleReal(doubleDouble.negate());
    }

    // Operators for DoubleReal
    @Override
    public DoubleDoubleReal plus(DoubleDoubleReal value) {
        return new DoubleDoubleReal(doubleDouble.add(value.doubleDouble));
    }

    @Override
    public DoubleDoubleReal minus(DoubleDoubleReal value) {
        return new DoubleDoubleReal(doubleDouble.subtract(value.doubleDouble));
    }

    @Override
    public DoubleDoubleReal mul(DoubleDoubleReal value) {
        return new DoubleDoubleReal(doubleDouble.multiply(value.doubleDouble));
    }

    @Override
    public DoubleDoubleReal div(DoubleDoubleReal value) {
        return new DoubleDoubleReal(doubleDouble.divide(value.doubleDouble));
    }

    @Override
    public DoubleDoubleReal pow(int value) {
        return new DoubleDoubleReal(doubleDouble.pow(value));
    }

    @Override
    public DoubleDoubleReal mul(long value) {
        return new DoubleDoubleReal(doubleDouble.multiply(new DoubleDouble(value)));
    }

    @Override
    public double getReal() {
        return doubleDouble.doubleValue();
    }

    public DoubleDoubleReal floor() {
        return new DoubleDoubleReal(doubleDouble.floor());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(doubleDouble);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DoubleDoubleReal) {
            DoubleDoubleReal that = (DoubleDoubleReal) object;
            return Objects.equal(this.doubleDouble, that.doubleDouble);
        }
        return false;
    }
}
