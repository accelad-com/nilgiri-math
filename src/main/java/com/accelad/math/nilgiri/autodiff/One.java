package com.accelad.math.nilgiri.autodiff;

import com.accelad.math.nilgiri.AbstractIdentityFactory;
import com.accelad.math.nilgiri.Field;


public class One<X extends Field<X>> extends Constant<X> {


    public One(AbstractIdentityFactory<X> i_factory) {
        super(i_factory.one(), i_factory);
    }

    public DifferentialFunction<X> mul(DifferentialFunction<X> i_v) {
        return i_v;
    }

    protected DifferentialFunction<X> muled(DifferentialFunction<X> i_v) {
        return i_v;
    }

}
