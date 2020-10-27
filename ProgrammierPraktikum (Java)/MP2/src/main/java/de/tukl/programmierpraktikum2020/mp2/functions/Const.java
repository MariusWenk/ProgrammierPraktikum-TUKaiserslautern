package de.tukl.programmierpraktikum2020.mp2.functions;

public class Const implements Function {
    double ConstNum;
    public Const(double x){
        ConstNum = x;
    }

    @Override
    public double apply(double x) {
        return ConstNum;
    }

    @Override
    public Function derive() {
        return new Const(0);
    }
    // v(x) = c ; v'(x) = 0

    @Override
    public String toString(){
        return Double.toString(ConstNum);
    }

    @Override
    public Function simplify() {
        return new Const(ConstNum);
    }
}
