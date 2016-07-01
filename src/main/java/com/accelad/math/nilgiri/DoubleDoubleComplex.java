package com.accelad.math.nilgiri;

import org.apache.commons.math3.util.FastMath;

import com.accelad.math.DoubleDouble;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class DoubleDoubleComplex implements ComplexNumber<DoubleDoubleReal, DoubleDoubleComplex> {

    public static final DoubleDoubleComplex I = new DoubleDoubleComplex(0.0, 1.0);
    public static final DoubleDoubleComplex ONE = new DoubleDoubleComplex(1.0, 0.0);
    public static final DoubleDoubleComplex ZERO = new DoubleDoubleComplex(0.0, 0.0);
    private final DoubleDoubleReal imaginary;
    private final DoubleDoubleReal real;

    public DoubleDoubleComplex() {
        this(0, 0);
    }

    public DoubleDoubleComplex(double real) {
        this(real, 0.0);
    }

    public DoubleDoubleComplex(double real, double imaginary) {
        this(new DoubleDoubleReal(real), new DoubleDoubleReal(imaginary));
    }

    public DoubleDoubleComplex(DoubleDouble real, DoubleDouble imaginary) {
        this(new DoubleDoubleReal(real), new DoubleDoubleReal(imaginary));
    }

    public DoubleDoubleComplex(DoubleDoubleReal real, DoubleDoubleReal imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    @Override
    public double getImaginary() {
        return imaginary.doubleValue();
    }

    @Override
    public double getReal() {
        return real.doubleValue();
    }

    @Override
    public DoubleDoubleComplex inverse() {
        return ONE.div(this);
    }

    @Override
    public DoubleDoubleComplex div(DoubleDoubleComplex divisor) {
        DoubleDoubleReal denominator = divisor.real.pow(2).plus(divisor.imaginary.pow(2));
        DoubleDoubleReal realPart = real.mul(divisor.real).plus(imaginary.mul(divisor.imaginary))
                .div(denominator);
        DoubleDoubleReal imaginaryPart = imaginary.mul(divisor.real)
                .minus(real.mul(divisor.imaginary)).div(denominator);
        return new DoubleDoubleComplex(realPart, imaginaryPart);
    }

    public DoubleDoubleComplex abs() {
        return new DoubleDoubleComplex(real.pow(2).plus(imaginary.pow(2)).getDoubleDouble().sqrt(),
                new DoubleDouble());
    }

    @Override
    public DoubleDoubleComplex mul(DoubleDoubleComplex factor) {
        return new DoubleDoubleComplex(real.mul(factor.real).minus(imaginary.mul(factor.imaginary)),
                real.mul(factor.imaginary).plus(imaginary.mul(factor.real)));
    }

    @Override
    public DoubleDoubleComplex pow(int x) {
        return this.log().mul(x).exp();
    }

    private DoubleDoubleComplex exp() {
        DoubleDoubleReal expReal = new DoubleDoubleReal(real.getDoubleDouble().exp());
        return new DoubleDoubleComplex(
                expReal.mul(new DoubleDoubleReal(imaginary.getDoubleDouble().cos())),
                expReal.mul(new DoubleDoubleReal(imaginary.getDoubleDouble().sin())));
    }

    public DoubleDoubleComplex log() {
        return new DoubleDoubleComplex(
                new DoubleDoubleReal(abs().re().getDoubleDouble().log()),
                new DoubleDoubleReal(FastMath.atan2(imaginary.doubleValue(), real.doubleValue())));
    }

    @Override
    public DoubleDoubleComplex plus(DoubleDoubleComplex plusend) {
        return new DoubleDoubleComplex(real.plus(plusend.real), imaginary.plus(plusend.imaginary));
    }

    @Override
    public DoubleDoubleComplex minus(DoubleDoubleComplex subtrahend) {
        return new DoubleDoubleComplex(real.minus(subtrahend.real),
                imaginary.minus(subtrahend.imaginary));
    }

    @Override
    public DoubleDoubleComplex mul(long factor) {
        return mul(new DoubleDoubleComplex(factor));
    }

    @Override
    public DoubleDoubleComplex negate() {
        return new DoubleDoubleComplex(real.negate(), imaginary.negate());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(imaginary, real);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DoubleDoubleComplex) {
            DoubleDoubleComplex that = (DoubleDoubleComplex) object;
            return Objects.equal(this.imaginary, that.imaginary)
                    && Objects.equal(this.real, that.real);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("imaginary", imaginary).add("real", real)
                .toString();
    }

    @Override
    public DoubleDoubleComplex conjugate() {
        return new DoubleDoubleComplex(real, imaginary.negate());
    }

    @Override
    public DoubleDoubleReal re() {
        return real;
    }

    @Override
    public DoubleDoubleReal im() {
        return imaginary;
    }
}
