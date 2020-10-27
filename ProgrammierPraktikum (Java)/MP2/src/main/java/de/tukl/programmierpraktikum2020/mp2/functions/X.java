package de.tukl.programmierpraktikum2020.mp2.functions;

public class X implements Function{

    public X(){
        //leerer Konstruktor
    }

    @Override
    public double apply(double x) {
        return x;
    }

    @Override
    public Function derive() {
        return new Const(1);
    }
    // v(x) = x ; v'(x) = 1

    @Override
    public String toString(){
        return "x";
    }

    @Override
    public Function simplify() {
        return new X();
    }
}
