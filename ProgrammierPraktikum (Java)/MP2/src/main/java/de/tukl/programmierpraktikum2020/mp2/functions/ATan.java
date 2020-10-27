package de.tukl.programmierpraktikum2020.mp2.functions;

public class ATan implements Function {
    Function f;
    public ATan(Function input){
        f = input;
    }
    @Override
    public double apply(double x) {
        return Math.atan(f.apply(x));
    }

    @Override
    public Function derive() {
        return new Div(f.derive(),new Plus(new Pow(f,new Const(2)),new Const(1)));
    }
    //atan'x=1/(x^2+1)
    @Override
    public String toString(){
        return "atan("+ f.toString() +")";
    }

    @Override
    public Function simplify() {
        Function a = f.simplify();
        if(a instanceof Const && Math.atan(f.apply(0.0)) == 0.0){
            return new Const(0);
        }
        if(a instanceof  Tan){
            return ((Tan)a).f;
        }
        return new ATan(a);
    }
}
