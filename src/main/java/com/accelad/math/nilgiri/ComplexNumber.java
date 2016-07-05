package com.accelad.math.nilgiri;


public interface ComplexNumber<R extends RealNumber<R>, X> extends Field<X> {


    X conjugate();


    R re();


    R im();
}
