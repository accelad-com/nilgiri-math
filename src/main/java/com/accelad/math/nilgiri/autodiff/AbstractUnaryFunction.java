package com.accelad.math.nilgiri.autodiff;

import com.accelad.math.nilgiri.Field;


public abstract class AbstractUnaryFunction<X extends Field<X>> extends DifferentialFunction<X> {

    private DifferentialFunction<X> m_x;


    public AbstractUnaryFunction(DifferentialFunction<X> i_v) {

        if (i_v != null) {
            m_x = i_v;
        } else {
            throw new IllegalArgumentException("Input not null variable.");
        }
    }


    public DifferentialFunction<X> arg() {
        return m_x;
    }
}
