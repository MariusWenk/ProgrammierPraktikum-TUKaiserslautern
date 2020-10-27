package de.tukl.programmierpraktikum2020.mp2;

import de.tukl.programmierpraktikum2020.mp2.functions.Exp;
import de.tukl.programmierpraktikum2020.mp2.functions.Function;
import de.tukl.programmierpraktikum2020.mp2.functions.X;

import java.io.IOException;

public class Calculus {
    public static void main(String[] args) throws IOException {
        Function f = null;
        double xmin, xmax;

        // -------------------------------------------------------------------------------------------------------------
        // Aufgabe 1:
        // Funktionen direkt hier im Code instanziieren, z. B.:
        f = new Exp(new X());

        // Bereich, der geplottet werden soll:
        xmin = -10;
        xmax = 10;

        // Geplottet wird f weiter unten.


        // -------------------------------------------------------------------------------------------------------------
        // Aufgabe 3:

        // Benutzer nach Funktion (als String) fragen und diesen String parsen:
        // (Sobald die Klasse Parser exisitiert, können Sie die Kommentarzeichen vor der nächsten Zeile entfernen)
         f = Util.promptFunction(new Parser()).simplify();

        if (f != null) { // Gültige Funktion eingegeben
            // Benutzer nach xmin und xmax fragen
            // (Kommentarzeichen vor den folgenden beiden Zeilen entfernen!)
            // xmin = Util.promptXMin();
            // xmax = Util.promptXMax();

            // Eingaben anzeigen:
            System.out.println("\nf(x)  = " + f.toString());
            Function derivative = f.derive().simplify();
            if (derivative != null) {
                System.out.println("f'(x) = " + derivative.toString());
                Function simpleDerivative = derivative.simplify();
                if (simpleDerivative != null && simpleDerivative != derivative) {
                    System.out.println("f'(x) = " + simpleDerivative.toString() + "    // simplified");
                }
            }

            // Plot
            Util.plotFunction(f, xmin, xmax);
        } else {
            System.out.println("\nKeine gültige Funktion eingegeben!");
        }
    }
}
