package com.accelad.math.nilgiri;


public interface Group<X> {


    X negate();


    X plus(X i_v);


    X minus(X i_v);


    X mul(long i_n);
}
