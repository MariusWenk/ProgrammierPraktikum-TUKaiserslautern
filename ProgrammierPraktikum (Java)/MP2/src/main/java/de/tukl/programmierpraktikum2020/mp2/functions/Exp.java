package de.tukl.programmierpraktikum2020.mp2.functions;

import org.apache.pdfbox.pdmodel.fdf.FDFAnnotationUnderline;

public class Exp implements Function{
    Function f;
    public Exp(Function Exponent){
        this.f = Exponent;
    };

    @Override
    public double apply(double x) {
        return Math.exp(f.apply(x));
    }

    @Override
    public Function derive() {
        return new Mult(new Exp(f),f.derive());
    }
    //v(x) = e^f(x) ; v'(x) = f'(x) * e^f(x)
    @Override
    public String toString(){
        return "exp("+f.toString()+")";
    }

    @Override
    public Function simplify() {
        // Wird durch Ableitung niemals gesamt vereinfachbar sein
        return new Exp(f.simplify());
    }
}
