package com.accelad.math.doubledouble;

import java.util.function.Supplier;

class DoubleDoubleCache<T> {

    private final DoubleMap<DoubleMap<T>> map = new DoubleMap<>();

    public T get(Double hi, Double lo, Supplier<T> supplier) {
        DoubleMap<T> hiMap = map.get(hi, DoubleMap::new);
        return hiMap.get(lo, supplier);
    }
}
