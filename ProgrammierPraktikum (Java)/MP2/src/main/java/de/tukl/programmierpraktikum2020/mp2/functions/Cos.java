package de.tukl.programmierpraktikum2020.mp2.functions;

public class Cos implements Function{
    Function f;
    public Cos(Function input){
        f = input;
    }
    @Override
    public double apply(double x) {
        return Math.cos(f.apply(x));
    }

    @Override
    public Function derive() {
        return new Mult(new Mult(new Sin(f), new Const(-1)),f.derive());
    }
    // v(x) = - sin(f(x)) * f'(x)

    @Override
    public String toString(){
        return "cos("+ f.toString()+")";
    }

    @Override
    public Function simplify() {
        Function a = f.simplify();
        if(a instanceof Const && Math.cos(f.apply(0.0)) == 0.0){
            return new Const(0);
        }
        if(a instanceof  ACos){
            return ((ACos)a).f;
        }
        return new Cos(a);
    }
}
