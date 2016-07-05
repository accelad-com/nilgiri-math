package com.accelad.math.nilgiri.autodiff;

import java.util.List;

import com.accelad.math.nilgiri.Field;


public class Inverse<X extends Field<X>> extends AbstractUnaryFunction<X> {


    public Inverse(DifferentialFunction<X> i_v) {
        super(i_v);
    }

    @Override
    public X getValue() {
        return arg().getValue().inverse();
    }

    @Override
    public double getReal() {
        return 1d / arg().getReal();
    }

    @Override
    public DifferentialFunction<X> diff(Variable<X> i_v) {
        return new PolynomialTerm<X>(-1L, arg(), -2).mul(arg().diff(i_v));
    }

    @Override
    public String toString() {
        return "(" + arg().toString() + ")^(-1)";
    }

    @Override
    public String getFormula(List<Variable<X>> variables) {
        return "( 1d / " + arg().getFormula(variables) + ")";
    }

    @Override
    public DifferentialFunction<X> inverse() {
        return arg();
    }
}
