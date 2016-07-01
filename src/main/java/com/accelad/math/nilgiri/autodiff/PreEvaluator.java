package com.accelad.math.nilgiri.autodiff;

import com.accelad.math.nilgiri.Field;

public interface PreEvaluator<X extends Field<X>> {
    public void update(Variable<X> v);

}
