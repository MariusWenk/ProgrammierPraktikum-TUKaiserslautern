package de.tukl.programmierpraktikum2020.mp2.functions;

import java.rmi.server.ExportException;

public class Pow implements Function{
    Function f;
    Function g;

    public Pow(Function Basis, Function Exponent){
        f = Basis;
        g = Exponent;
    }
    @Override
    public double apply(double x) {
        return Math.pow(f.apply(x),g.apply(x));
    }

    @Override
    public Function derive() {
        return new Mult(new Mult(g,new Log(f)).derive(),new Pow(f,g));
    }
    // v(x) = f(x)^g(x) = e^(ln(f(x)^g(x))) = e^(g(x)*ln(f(x))) ;
    // v'(x) = (g(x)*ln(f(x)))' * e^(g(x)*ln(f(x))) = (g(x)*ln(f(x)))' * f(x)^g(x)

    @Override
    public String toString(){
        return "("+f.toString()+")^("+g.toString()+")";
    }

    @Override
    public Function simplify() {
        Function a = f.simplify();
        Function b = g.simplify();
        // Durch ABleitung entsteht nie f^0
        if(b instanceof Const && g.apply(0.0) == 0.0){
            return a;
        }
        else if(a instanceof Const && f.apply(0.0) == 0.0){
            return new Const(0);
        }
        else if(a instanceof Const && f.apply(0.0) == 1.0){
            return new Const(1);
        }
        return new Pow(a,b);
    }
}
