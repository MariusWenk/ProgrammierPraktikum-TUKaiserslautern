package de.tukl.programmierpraktikum2020.mp2.functions;

public class Mult implements Function{

    Function f;
    Function g;
    public Mult(Function Faktor1, Function Faktor2){
        f= Faktor1;
        g= Faktor2;
    }
    @Override
    public double apply(double x) {
        return (f.apply(x)*g.apply(x));
    }

    @Override
    public Function derive() {
        return new Plus(new Mult(f.derive(), g),new Mult(f , g.derive()));
    }
    //v(x)= f(x)*g(x) ; v'(x) = f'(x) * g(x) + g'(x) * f(x)

    @Override
    public String toString(){
        return "("+f.toString()+" * "+g.toString()+")";
    }

    @Override
    public Function simplify() {
        Function a = f.simplify();
        Function b = g.simplify();
        if(a instanceof Const && f.apply(0.0) == 0.0){
            return new Const(0);
        }
        else if(b instanceof Const && g.apply(0.0) == 0.0){
            return new Const(0);
        }
        else if(a instanceof Const && f.apply(0.0) == 1.0){
            return b;
        }
        else if(b instanceof Const && g.apply(0.0) == 1.0){
            return a;
        }
        else if(b instanceof Const && a instanceof Const){
            return new Const(g.apply(0.0) * f.apply(0.0));
        }
        // Kein Zugriff auf Teiler von möglicher Multiplikation mit Division, daher hier weggelassen
        return new Mult(a,b);
    }
}
