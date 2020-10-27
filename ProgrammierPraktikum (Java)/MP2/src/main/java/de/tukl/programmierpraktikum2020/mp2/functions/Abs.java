package de.tukl.programmierpraktikum2020.mp2.functions;

import java.awt.*;

public class Abs implements Function {
    Function f;
    public Abs(Function input){
        f = input;
    }
    @Override
    public double apply(double x) {
        return Math.abs(f.apply(x));
    }

    @Override
    public Function derive() {
        return new Div(new Mult(f,f.derive()),this);
    }
    //|x|' = x/|x|
    @Override
    public String toString(){
        return "|"+ f.toString() +"|";
    }

    @Override
    public Function simplify() {
        Function a = f.simplify();
        if(a instanceof Const && Math.abs(f.apply(0.0)) == 0.0){
            return new Const(0);
        }
        if(a instanceof Exp){
            return ((Exp)a).f;
        }
        if(a instanceof Mult){
            Mult af = (Mult) a;
            Function fl=af.f, gl=af.g;
            if(af.f instanceof Const){
                fl = new Const(Math.abs(((Const)af.f).ConstNum));
            }
            if(af.f instanceof Const){
                gl = new Const(Math.abs(((Const)af.f).ConstNum));
            }
            return new Mult(fl,gl);
        }
        return new Abs(a);
    }
}
