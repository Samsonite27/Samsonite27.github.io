/*
 * PROJECT III: Project3.java
 *
 * This file contains a template for the class Project3. None of methods are
 * implemented and they do not have placeholder return statements. Make sure 
 * you have carefully read the project formulation before starting to work 
 * on this file. You will also need to have completed the Matrix class, as 
 * well as GeneralMatrix and TriMatrix.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
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
 * NAME: Samuel Halford-Maw
 * UNIVERSITY ID: u5515680
 * DEPARTMENT: Warwock Mathematics Department
 */

import java.util.Arrays;

public class Project3 {
    /**
     * Calculates the variance of the distribution defined by the determinant
     * of a random matrix. See the formulation for a detailed description.
     *
     * @param matrix      The matrix object that will be filled with random
     *                    samples.
     * @param nSamp       The number of samples to generate when calculating 
     *                    the variance. 
     * @return            The variance of the distribution.
     */
    public static double matVariance(Matrix matrix, int nSamp) {
        
        double det_sum = 0.0;
        double det_square_sum = 0.0;
        double number_samp = (double) nSamp;

        for(int i = 0; i < nSamp; i++) {
            matrix.random();
            double det = matrix.determinant();
            det_sum += det;
            det_square_sum += det * det;
        }
        
        double exp = (1/number_samp) * det_sum;
        double variance = (1/number_samp) * det_square_sum - (exp * exp);

        return variance;

    }
    
    /**
     * This function should calculate the variances of matrices for matrices
     * of size 2 <= n <= 50 and print the results to the output. See the 
     * formulation for more detail.
     */
    public static void main(String[] args) {

        double[] genVar = new double[49];
        double[] triVar = new double[49];

        int gen_sample = 20000;
        int tri_sample = 200000;

        String output = "";
        
        for(int i = 0; i < 49; i++){
            GeneralMatrix gen_test_size_n = new GeneralMatrix(i + 2, i + 2);
            genVar[i] = matVariance(gen_test_size_n, gen_sample);

            TriMatrix tri_test_size_n = new TriMatrix(i + 2);
            triVar[i] = matVariance(tri_test_size_n, tri_sample);

            String str = String.format("%2d %.15e %.15e \n", (i + 2), genVar[i], triVar[i]);
            output += str;

        }

        System.out.println(output);
    }
}