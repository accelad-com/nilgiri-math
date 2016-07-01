package com.accelad.math.nilgiri.autodiff;

import com.accelad.math.nilgiri.Field;

public interface VectorDifferential<X extends Field<X>, D> {

    public D diff(VariableVector<X> i_v);
}
