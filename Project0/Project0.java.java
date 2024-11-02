/*
 * PROJECT 0
 *
 * This file is a SKELETON file. It has a SMALL set of test to check some features of 
 * the FRACTIONS class.  The BOSS system will test ALL parts of your solution.
 * You must create your own tests.
 * 
 * Tasks:
 *
 * 1) Create your own tests for the FRACTIONS class 
 *
 * 2) Fill in the following fields:
 *
 * NAME: 
 * UNIVERSITY ID: 
 * DEPARTMENT: 
 */

/**
 * Classname: Project0
 * Description: This class utlises a new type for fractions
 *              and corresponding arithmetic.
 * 
 * @author : A.Hague for use in the course MA117
 * @version: history: v1.0
 */

public class Project0 {
    // Simple tester function.
    public static void main(String[] args) {
        // Test constructors.
        Fraction A = new Fraction(-8,16);
        Fraction H = new Fraction(1,3);
        
        // Test conversions.
        System.out.println("A = "+A+" = "+A.toDouble());
        System.out.println("H = "+H+" = "+H.toDouble());
        
        // Test operations.
        System.out.println("A+H = "+A.add(H));

        // Test errors
        // While a denominator of 0 is not valid, the Fraction should continue to work.
        // Only toFloat() and toDouble() will implicitly raise divide by zero exceptions,
        // and this is the expected behaviour
		Fraction Bad = new Fraction(12,0);
		System.out.println(Bad);  
    }
}
