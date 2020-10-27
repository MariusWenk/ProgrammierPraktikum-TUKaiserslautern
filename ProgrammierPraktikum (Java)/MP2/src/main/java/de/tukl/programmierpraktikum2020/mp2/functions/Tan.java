package de.tukl.programmierpraktikum2020.mp2.functions;

public class Tan implements Function {
    Function f;
    public Tan(Function input){
        f = input;
    }
    @Override
    public double apply(double x) {
        return Math.tan(f.apply(x));
    }

    @Override
    public Function derive() {
        return new Div(f.derive(),new Pow(new Cos(f),new Const(2)));
    }
    //tan'x = 1/(cos^2(x))
    @Override
    public String toString(){
        return "tan("+ f.toString() +")";
    }

    @Override
    public Function simplify() {
        Function a = f.simplify();
        if(a instanceof Const && Math.tan(f.apply(0.0)) == 0.0){
            return new Const(0);
        }
        if(a instanceof  ATan){
            return ((ATan)a).f;
        }
        return new Tan(a);
    }
}
