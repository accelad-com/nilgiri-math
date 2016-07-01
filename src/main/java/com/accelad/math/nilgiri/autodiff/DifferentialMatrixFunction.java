package com.accelad.math.nilgiri.autodiff;

import com.accelad.math.nilgiri.Field;
import com.accelad.math.nilgiri.Ring;

public interface DifferentialMatrixFunction<X extends Field<X>> extends
        Ring<DifferentialMatrixFunction<X>>, Differential<X, DifferentialMatrixFunction<X>> {

}
