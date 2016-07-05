package com.accelad.math.nilgiri.autodiff;

import java.util.List;

import com.accelad.math.nilgiri.Field;


public class Sum<X extends Field<X>> extends AbstractBinaryFunction<X> {

    public Sum(DifferentialFunction<X> i_v1, DifferentialFunction<X> i_v2) {
        super(i_v1, i_v2);
    }

    @Override
    public X getValue() {
        return larg().getValue().plus(rarg().getValue());
    }

    @Override
    public double getReal() {
        return larg().getReal() + rarg().getReal();
    }

    @Override
    public DifferentialFunction<X> diff(Variable<X> i_v1) {
        return (larg() == rarg()) ? larg().diff(i_v1).mul(2L) // Field is
                                                              // commutative
                                                              // with respect to
                                                              // addition.
                : larg().diff(i_v1).plus(rarg().diff(i_v1));
    }

    @Override
    public String toString() {
        return "(" + larg().toString() + "+" + rarg().toString() + ")";
    }

    @Override
    public String getFormula(List<Variable<X>> variables) {
        return "(" + larg().getFormula(variables) + "+" + rarg().getFormula(variables) + ")";
    }
}
