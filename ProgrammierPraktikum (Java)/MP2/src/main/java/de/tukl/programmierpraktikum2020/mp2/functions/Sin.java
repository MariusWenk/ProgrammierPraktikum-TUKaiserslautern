package de.tukl.programmierpraktikum2020.mp2.functions;

public class Sin implements Function {
    Function f;
    public Sin(Function input){
        f = input;
    }
    @Override
    public double apply(double x) {
        return Math.sin(f.apply(x));
    }

    @Override
    public Function derive() {
        return new Mult(new Cos(f),f.derive());
    }
    //v(x)= sin(f(x)) ; v'(x) = cos(f(x)) * f'(x)
    @Override
    public String toString(){
        return "sin("+ f.toString() +")";
    }

    @Override
    public Function simplify() {
        Function a = f.simplify();
        if(a instanceof Const && Math.sin(f.apply(0.0)) == 0.0){
            return new Const(0);
        }
        if(a instanceof  ASin){
            return ((ASin)a).f;
        }
        return new Sin(a);
    }
}
