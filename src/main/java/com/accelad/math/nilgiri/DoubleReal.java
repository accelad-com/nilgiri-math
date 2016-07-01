package com.accelad.math.nilgiri;

public class DoubleReal implements RealNumber<DoubleReal> {

    private double x;

    public DoubleReal() {
        this(0.0);

    }

    public DoubleReal(double x) {
        this.x = x;
    }

    public DoubleReal(String doubleString) {
        x = Double.parseDouble(doubleString);
    }

    public void set(double value) {
        this.x = value;
    }

    public double doubleValue() {
        return x;
    }

    public double modulus() {
        return Math.abs(x);
    }

    @Override
    public String toString() {
        return String.valueOf(x);
    }


    @Override
    public DoubleReal inverse() {
        return new DoubleReal(1.0 / x);
    }

    @Override
    public DoubleReal negate() {
        return new DoubleReal(-x);
    }

    // Operators for DoubleReal

    @Override
    public DoubleReal plus(DoubleReal rd) {
        return new DoubleReal(x + rd.x);
    }

    @Override
    public DoubleReal minus(DoubleReal rd) {
        return new DoubleReal(x - rd.x);
    }

    @Override
    public DoubleReal mul(DoubleReal rd) {
        return new DoubleReal(x * rd.x);
    }

    @Override
    public DoubleReal div(DoubleReal rd) {
        return new DoubleReal(x / rd.x);
    }

    public DoubleReal floor() {
        return new DoubleReal(Math.floor(x));
    }

    // Operators for double

    public DoubleReal plus(double v) {
        return new DoubleReal(x + v);
    }

    public DoubleReal minus(double v) {
        return new DoubleReal(x - v);
    }

    public DoubleReal prod(double v) {
        return new DoubleReal(x * v);
    }

    public DoubleReal div(double v) {
        return new DoubleReal(x / v);
    }

    public DoubleReal pow(double v) {
        return new DoubleReal(Math.pow(x, v));
    }

    @Override
    public DoubleReal pow(int n) {
        return new DoubleReal(Math.pow(x, n));
    }

    @Override
    public DoubleReal mul(long n) {
        return new DoubleReal(x * n);
    }


    @Override
    public double getReal() {
        return x;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DoubleReal other = (DoubleReal) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        return true;
    }

}
