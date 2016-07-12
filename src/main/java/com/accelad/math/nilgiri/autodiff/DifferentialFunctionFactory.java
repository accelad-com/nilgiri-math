package com.accelad.math.nilgiri.autodiff;

import java.util.ArrayList;
import java.util.List;

import com.accelad.math.nilgiri.AbstractFactory;
import com.accelad.math.nilgiri.Field;

public class DifferentialFunctionFactory<X extends Field<X>> {

    protected AbstractFactory<X> mFactory;

    public DifferentialFunctionFactory(AbstractFactory<X> mFactory) {
        if (mFactory != null) {
            this.mFactory = mFactory;
        } else {
            throw new IllegalArgumentException("Input not null value.");
        }
    }

    public Constant<X> val(X iX) {
        return new Constant<>(iX, mFactory);
    }

    public ConstantVector<X> val(X... iX) {
        int size = iX.length;
        ArrayList<Constant<X>> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(val(iX[i]));
        }
        return new ConstantVector<>(mFactory, list);
    }

    // ZeroVector
    public ConstantVector<X> zero(int iSize) {
        ArrayList<Constant<X>> list = new ArrayList<>(iSize);
        for (int i = 0; i < iSize; i++) {
            list.add(zero());
        }
        return new ConstantVector<>(mFactory, list);
    }

    public Variable<X> var(String iName, X iX, PreEvaluator<X> preEvaluator) {
        return new Variable<>(iName, iX, mFactory, preEvaluator);
    }

    public Variable<X> var(String iName, X iX) {
        return new Variable<>(iName, iX, mFactory);
    }

    public VariableVector<X> var(String iName, X... iX) {
        int size = iX.length;
        ArrayList<Variable<X>> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(var(iName + String.valueOf(i), iX[i]));
        }
        return new VariableVector<>(mFactory, list);
    }

    public VariableVector<X> var(String iName, int iSize) {
        ArrayList<Variable<X>> list = new ArrayList<>(iSize);
        for (int i = 0; i < iSize; i++) {
            list.add(var(iName + String.valueOf(i), mFactory.zero()));
        }
        return new VariableVector<>(mFactory, list);
    }

    public DifferentialVectorFunction<X> function(DifferentialFunction<X>... iX) {
        int size = iX.length;
        ArrayList<DifferentialFunction<X>> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(iX[i]);
        }
        return new DifferentialVectorFunction<>(mFactory, list);
    }

    public Zero<X> zero() {
        return new Zero<>(mFactory);
    }

    public One<X> one() {
        return new One<>(mFactory);
    }

    public DifferentialFunction<X> cos(DifferentialFunction<X> iX) {
        return new AbstractUnaryFunction<X>(iX) {

            @Override
            public X getValue() {
                return mFactory.cos(arg().getValue());
            }

            @Override
            public double getReal() {
                return Math.cos(arg().getReal());
            }

            @Override
            public DifferentialFunction<X> diff(Variable<X> i_v) {
                return (sin(arg()).mul(arg().diff(i_v))).negate();
            }

            @Override
            public String toString() {
                return "cos(" + arg().toString() + ")";
            }

            @Override
            public String getFormula(List<Variable<X>> variables) {
                return "Math.cos(" + arg().getFormula(variables) + ")";
            }
        };
    }

    public DifferentialFunction<X> sin(DifferentialFunction<X> iX) {
        return new AbstractUnaryFunction<X>(iX) {

            @Override
            public X getValue() {
                return mFactory.sin(arg().getValue());
            }

            @Override
            public double getReal() {
                return Math.sin(arg().getReal());
            }

            @Override
            public DifferentialFunction<X> diff(Variable<X> i_v) {
                return cos(arg()).mul(arg().diff(i_v));
            }

            @Override
            public String toString() {
                return "sin(" + arg().toString() + ")";
            }

            @Override
            public String getFormula(List<Variable<X>> variables) {
                return "Math.sin(" + arg().getFormula(variables) + ")";
            }
        };
    }

    public DifferentialFunction<X> tan(DifferentialFunction<X> iX) {
        return new AbstractUnaryFunction<X>(iX) {

            @Override
            public X getValue() {
                return mFactory.tan(arg().getValue());
            }

            @Override
            public double getReal() {
                return Math.tan(arg().getReal());
            }

            @Override
            public DifferentialFunction<X> diff(Variable<X> i_v) {
                return (new PolynomialTerm<>(1, cos(arg()), -2)).mul(arg().diff(i_v));
            }

            @Override
            public String toString() {
                return "tan(" + arg().toString() + ")";
            }

            @Override
            public String getFormula(List<Variable<X>> variables) {
                return "Math.tan(" + arg().getFormula(variables) + ")";
            }
        };
    }

    public DifferentialFunction<X> exp(DifferentialFunction<X> iX) {
        return new AbstractUnaryFunction<X>(iX) {

            @Override
            public X getValue() {
                return mFactory.exp(arg().getValue());
            }

            @Override
            public double getReal() {
                return Math.exp(arg().getReal());
            }

            @Override
            public DifferentialFunction<X> diff(Variable<X> i_v) {
                return exp(arg()).mul(arg().diff(i_v));
            }

            @Override
            public String toString() {
                return "exp(" + arg().toString() + ")";
            }

            @Override
            public String getFormula(List<Variable<X>> variables) {
                return "Math.exp(" + arg().getFormula(variables) + ")";
            }
        };
    }

    public DifferentialFunction<X> log(DifferentialFunction<X> iX) {
        return new AbstractUnaryFunction<X>(iX) {

            @Override
            public X getValue() {
                return mFactory.log(arg().getValue());
            }

            @Override
            public double getReal() {
                return Math.log(arg().getReal());
            }

            @Override
            public DifferentialFunction<X> diff(Variable<X> i_v) {
                return new Inverse<>(arg()).mul(arg().diff(i_v));
            }

            @Override
            public String toString() {
                return "log(" + arg().toString() + ")";
            }

            @Override
            public String getFormula(List<Variable<X>> variables) {
                return "Math.log(" + arg().getFormula(variables) + ")";
            }
        };
    }

    public DifferentialFunction<X> pow(DifferentialFunction<X> iX, Constant<X> i_y) {
        return new AbstractBinaryFunction<X>(iX, i_y) {

            @Override
            public X getValue() {
                return mFactory.pow(larg().getValue(), rarg().getValue());
            }

            @Override
            public double getReal() {
                return Math.pow(larg().getReal(), rarg().getReal());
            }

            @Override
            public DifferentialFunction<X> diff(Variable<X> i_v) {
                Constant<X> ym1 = DifferentialFunctionFactory.this
                        .val(rarg().getValue().minus(mFactory.one()));
                return rarg().mul(DifferentialFunctionFactory.this.pow(larg(), ym1))
                        .mul(larg().diff(i_v));
            }

            @Override
            public String toString() {
                return "pow(" + larg().toString() + ", " + rarg().toString() + ")";
            }

            @Override
            public String getFormula(List<Variable<X>> variables) {
                return "Math.pow(" + larg().getFormula(variables) + ","
                        + rarg().getFormula(variables) + ")";
            }
        };
    }

    public DifferentialFunction<X> sqrt(DifferentialFunction<X> iX) {
        return new AbstractUnaryFunction<X>(iX) {

            @Override
            public X getValue() {
                return mFactory.sqrt(arg().getValue());
            }

            @Override
            public double getReal() {
                return Math.sqrt(arg().getReal());
            }

            @Override
            public DifferentialFunction<X> diff(Variable<X> i_v) {
                return ((sqrt(arg()).inverse())
                        .div(DifferentialFunctionFactory.this.val(mFactory.one().mul(2L))))
                                .mul(arg().diff(i_v));
            }

            @Override
            public String toString() {
                return "sqrt(" + arg().toString() + ")";
            }

            @Override
            public String getFormula(List<Variable<X>> variables) {
                return "Math.sqrt(" + arg().getFormula(variables) + ")";
            }
        };
    }

    public DifferentialFunction<X> square(DifferentialFunction<X> iX) {
        return new AbstractUnaryFunction<X>(iX) {

            @Override
            public X getValue() {
                return mFactory.square(arg().getValue());
            }

            @Override
            public double getReal() {
                return Math.pow(arg().getReal(), 2);
            }

            @Override
            public DifferentialFunction<X> diff(Variable<X> i_v) {
                return arg().mul(DifferentialFunctionFactory.this.val(mFactory.one().mul(2L)))
                        .mul(arg().diff(i_v));
            }

            @Override
            public String toString() {
                return "square(" + arg().toString() + ")";
            }

            @Override
            public String getFormula(List<Variable<X>> variables) {
                return "Math.pow(" + arg().getFormula(variables) + ", 2d )";
            }
        };
    }

    public DifferentialFunction<X> floor(DifferentialFunction<X> iX) {
        return new AbstractUnaryFunction<X>(iX) {

            @Override
            public X getValue() {
                return mFactory.floor(arg().getValue());
            }

            @Override
            public double getReal() {
                return Math.floor(arg().getReal());
            }

            @Override
            public DifferentialFunction<X> diff(Variable<X> i_v) {
                throw new RuntimeException("not allowed");
            }

            @Override
            public String toString() {
                return "floor(" + arg().toString() + ")";
            }

            @Override
            public String getFormula(List<Variable<X>> variables) {
                return "Math.floor(" + arg().getFormula(variables) + ")";
            }
        };
    }
}