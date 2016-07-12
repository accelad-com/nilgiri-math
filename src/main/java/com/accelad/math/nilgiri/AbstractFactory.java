package com.accelad.math.nilgiri;


public interface AbstractFactory<X extends Field<X>>
        extends AbstractIdentityFactory<X> {


    public X val(double i_v);


    X cos(X i_x);


    X sin(X i_x);


    X tan(X i_x);


    X exp(X i_x);


    X log(X i_x);


    X pow(X i_x, X i_y);


    X sqrt(X i_x);


    X square(X i_x);

    X floor(X value);
}
