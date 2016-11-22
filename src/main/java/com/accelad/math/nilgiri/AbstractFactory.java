package com.accelad.math.nilgiri;


public interface AbstractFactory<X extends Field<X>>
        extends AbstractIdentityFactory<X> {

    X val(double i_v);

    X abs(X i_x);

    X min(X i_x, X i_y);
    X max(X i_x, X i_y);

    X cos(X i_x);
    X acos(X i_x);
    X cosh(X i_x);
    X acosh(X i_x);

    X sin(X i_x);
    X asin(X i_x);
    X sinh(X i_x);
    X asinh(X i_x);

    X tan(X i_x);
    X atan(X i_x);
    X atan2(X i_x, X i_y);
    X tanh(X i_x);
    X atanh(X i_x);

    X exp(X i_x);
    X log(X i_x);

    X log10(X i_x);

    X flat(X i_x);
    X mc(X i_x, X i_y);
    X rand(X i_x);
    X random(X i_x);
    X gauss(X i_x);

    X sgn(X i_x);

    X ifx(X i_x, X i_y, X i_z);

    X buf(X i_x);
    X inv(X i_x);
    X u(X i_x);
    X uramp(X i_x);

    X pow(X i_x, X i_y);
    X pwr(X i_x, X i_y);
    X pwrs(X i_x, X i_y);
    X sqrt(X i_x);
    X square(X i_x);
    X hypot(X i_x, X i_y);

    X floor(X value);
    X ceil(X value);
    X round(X value);
}
