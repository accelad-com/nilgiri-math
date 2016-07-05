package com.accelad.math.nilgiri;


public interface Ring<X> extends CommutativeGroup<X> {


    X mul(X i_v);


    X pow(int i_n);

}
