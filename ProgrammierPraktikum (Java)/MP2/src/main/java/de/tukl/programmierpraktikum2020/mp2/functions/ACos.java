package de.tukl.programmierpraktikum2020.mp2.functions;

public class ACos implements Function {
    Function f;
    public ACos(Function input){
        f = input;
    }
    @Override
    public double apply(double x) {
        return Math.acos(f.apply(x));
    }

    @Override
    public Function derive() {
        return new Mult(new Const(-1), new Div(f.derive(), new Pow(new Plus(new Const(1),new Mult(new Const(-1),new Pow(f,new Const(2)))),new Const(1f/2))));
    }
    //acos'x=-1/sqrt(1-x^2)
    @Override
    public String toString(){
        return "acos("+ f.toString() +")";
    }

    @Override
    public Function simplify() {
        Function a = f.simplify();
        if(a instanceof Const && Math.acos(f.apply(0.0)) == 0.0){
            return new Const(0);
        }
        if(a instanceof  Cos){
            return ((Cos)a).f;
        }
        return new ACos(a);
    }
}
