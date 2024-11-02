/*
 * PROJECT II: Polynomial.java
 *
 * This file contains a template for the class Polynomial. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * This class is designed to use Complex in order to represent polynomials
 * with complex co-efficients. It provides very basic functionality and there
 * are very few methods to implement! The project formulation contains a
 * complete description.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 * 
 * Tasks:
 *
 * 1) Complete this class with the indicated methods and instance variables.
 *
 * 2) Fill in the following fields:
 *
 * NAME:Samuel Halford-Maw
 * UNIVERSITY ID: u5515680
 * DEPARTMENT: Mathematics
 */

import java.util.*;

public class Polynomial {
    /**
     * An array storing the complex co-efficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * co-efficients.
     *
     * @param coeff  The co-efficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {    
        boolean initial = true;
        do {
            if(coeff[coeff.length - 1].abs() == 0) {
                if(coeff.length == 1) {
                    initial = false;
                } else {
                    coeff = Arrays.copyOf(coeff, coeff.length-1);
                }
            } else {
                initial = false;
            }
        }
        while(initial == true);

        this.coeff = Arrays.copyOf(coeff, coeff.length);
    }
    
    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
        Complex[] coeff;
        coeff = new Complex[1];
        coeff[0] = new Complex(0.0, 0.0);
        this.coeff = coeff;
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Return the coefficients array.
     *
     * @return  The coefficients array.
     */
    public Complex[] getCoeff() {
        // You need to fill in this method with the correct code.
        return Arrays.copyOf(coeff, coeff.length);
    }

    /**
     * Create a string representation of the polynomial.
     * Use z to represent the variable.  Include terms
     * with zero co-efficients up to the degree of the
     * polynomial.
     *
     * For example: (-5.000+5.000i) + (2.000-2.000i)z + (-1.000+0.000i)z^2
     */
    public String toString() {
        String[] array_of_strings;
        array_of_strings = new String[coeff.length];
        for(int i = 0; i < coeff.length; i++) {
            array_of_strings[i] = coeff[i].toString();
            if(i > 0) {
                array_of_strings[i] += 'z';
            }
            if(i > 1) {
                array_of_strings[i] += ('^');
                array_of_strings[i] += (i);
            }
    
        }
        String output = String.join(" + ", array_of_strings);
        return output;
    }

    /**
     * Returns the degree of this polynomial.
     */
    public int degree() {
        return (coeff.length - 1);
    }

    /**
     * Evaluates the polynomial at a given point z.
     *
     * @param z  The point at which to evaluate the polynomial
     * @return   The complex number P(z).
     */
    public Complex evaluate(Complex z) {
        Complex total = new Complex(0.0, 0.0);
        
        for(int i = 1; i < coeff.length; i++) {
            total.setReal((total.add(coeff[coeff.length - i])).getReal());
            total.setImag((total.add(coeff[coeff.length - i])).getImag());

            Complex orignal_total = new Complex(total.getReal(), total.getImag());

            total.setReal((orignal_total.multiply(z)).getReal()); //referencing a modified version of total in the second function
            total.setImag((orignal_total.multiply(z)).getImag());

        }
        
        total.setReal((total.add(coeff[0])).getReal());
        total.setImag((total.add(coeff[0])).getImag());
        
        return total;
    }

    
    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        Complex[] test = {new Complex(-1.0, 0.0), new Complex(), new Complex(0.0, 0.0), new Complex(1.0, 0.0)};
        Polynomial poly_test = new Polynomial(test);
        System.out.println((poly_test.getCoeff()).length);

        Complex[] test2 = {new Complex(0.0, 0.0), new Complex(0.0, 0.0)};
        Polynomial poly_test2 = new Polynomial(test2);
        System.out.println((poly_test2.getCoeff()).length);

        System.out.println(poly_test.toString());
        System.out.println((poly_test.evaluate(new Complex(1.0, 1.0))).toString());
    }
}