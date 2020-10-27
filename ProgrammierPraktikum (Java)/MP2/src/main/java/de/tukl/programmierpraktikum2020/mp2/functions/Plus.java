package de.tukl.programmierpraktikum2020.mp2.functions;

public class Plus implements Function {
    Function f;
    Function g;

    public Plus(Function input1, Function input2){
        f= input1;
        g= input2;
    }

    @Override
    public double apply(double x) {
        return (f.apply(x)+g.apply(x));
    }

    @Override
    public Function derive() {
        return new Plus(f.derive(),g.derive());
    }
    // v(x) = f(x) + g(x) ; v'(x) = f'(x) + g'(x)

    @Override
    public String toString(){
        return "("+f.toString() +" + "+g.toString() +")";
    }

    @Override
    public Function simplify() {
        Function a = f.simplify();
        Function b = g.simplify();
        if(a instanceof Const && f.apply(0.0) == 0.0){
            return b;
        }
        else if(b instanceof Const && g.apply(0.0) == 0.0){
            return a;
        }
        else if(b instanceof Const && a instanceof Const){
            return new Const(f.apply(0.0) +g.apply(0.0));
        }
        return new Plus(a,b);
    }
}
