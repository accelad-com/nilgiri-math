package com.accelad.math.nilgiri;



public interface Field<X> extends CommutativeRing<X> {


    X inverse();


    X div(X i_v);

    double getReal();

    default double getImaginary() {
        throw new UnsupportedOperationException();
    }
}
