package de.tukl.programmierpraktikum2020.mp2.functions;

public class Div implements Function{
    Function f;
    Function g;

    public Div(Function Dividend, Function Divisor){
        f = Dividend;
        g = Divisor;
    }
    @Override
    public double apply(double x) {
        return (f.apply(x)/g.apply(x));
    }

    @Override
    public Function derive() {
        return new Div(new Plus (new Mult(f.derive(),g),new Mult(new Mult(g.derive(),f),new Const(-1))),new Mult(g,g));
        //v(x) = f(x)/g(x) ; v'()= ((f'(x) * g(x))-(g'(x) *f(x)))/g(x)^2
    }
    @Override
    public String toString(){
        return "("+f.toString()+"/" +g.toString()+")";
    }

    @Override
    public Function simplify() {
        // Teilen durch 0 kann in keiner Ableitung passieren
        Function a = f.simplify();
        Function b = g.simplify();
        if (a instanceof Const && f.apply(0.0) == 0.0) {
            return new Const(0);
        } else if (b instanceof Const && g.apply(0.0) == 1.0) {
            return a;
        } else if (b instanceof Const && a instanceof Const) {
            return new Const(f.apply(0.0)/g.apply(0.0));
        } else if (a == b) {
            return new Const(1);
        }
        return new Div(a, b);
    }
}
