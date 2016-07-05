package com.accelad.math.nilgiri.autodiff;

import com.accelad.math.nilgiri.Field;


public interface Differential<X extends Field<X>, D> {


    public D diff(Variable<X> i_v);
}
