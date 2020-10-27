package de.tukl.programmierpraktikum2020.mp2.functions;

public class Log implements Function{
    Function f;

    public Log(Function input){
        f= input;
    }
    @Override
    public double apply(double x) {
        return Math.log(f.apply(x));
    }

    @Override
    public Function derive() {
        return new Div(f.derive(),f);
    }
    // v(x) = ln(f(x)) ; v'(x) = 1/f(x) * f'(x) =f'(x)/f(x)

    @Override
    public String toString(){
        return "ln("+f.toString()+")";
    }

    @Override
    public Function simplify() {
        // Wird durch Ableitung niemals gesamt vereinfachbar sein
        return new Log(f.simplify());
    }
}
