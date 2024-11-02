/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
 * implemented and they do not have placeholder return statements. Make sure 
 * you have carefully read the project formulation before starting to work 
 * on this file. You will also need to have completed the Matrix class.
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
 * DEPARTMENT: Mathematics
 */

public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diagonal;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upperDiagonal;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lowerDiagonal;
    
    /**
     * Constructor function: should initialise iDim and jDim through the Matrix
     * constructor and set up the values array.
     *
     * @param dimension  The dimension of the array.
     */
    public TriMatrix(int dimension) {
        super(dimension, dimension);
        
        this.diagonal = new double[dimension];
        this.upperDiagonal = new double[dimension - 1];
        this.lowerDiagonal = new double[dimension - 1];
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        if(i >= this.iDim || j >= this.jDim){
            throw new MatrixException("You cannot access elements that are not inside the dimensions of the matrix.");
        } else{ 
            if(i == j){
                return diagonal[i];
            }else if(i + 1 == j) {
                return upperDiagonal[i];
            }else if(i == j + 1) {
                return lowerDiagonal[j];
            }else {
                return 0.0;
            }
        }
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i      The location in the first co-ordinate.
     * @param j      The location in the second co-ordinate.
     * @param value  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double value) {
        if(i >= this.iDim || j >= this.jDim){
            throw new MatrixException("You cannot mutate elements that are not inside the dimensions of the matrix.");
        } else {
            if(i == j){
                this.diagonal[i] = value;
            }else if(i + 1 == j) {
                this.upperDiagonal[i] = value;
            }else if(i == j + 1) {
                this.lowerDiagonal[j] = value;
            }else {
                if(value == 0.0) {
                } else {
                    throw new MatrixException("An entry outside of the three main diagonals must be 0.0.");
                }
            }
        }
    }
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        
        TriMatrix decomp = this.LUdecomp();
        
        double det = 1.0;

        for(int i = 0; i < this.iDim; i++){
            det *= decomp.getIJ(i, i);
        }

        return det;

    }
    
    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     * 
     * @return The LU decomposition of this matrix.
     */
    
     public TriMatrix LUdecomp() { //Think there is an issue here, line 146 with division by zero if d(i) is 0
        
        TriMatrix condensed_LUdecomp = new TriMatrix(this.iDim);

        for(int i = 0; i < this.iDim - 1; i++){
            condensed_LUdecomp.setIJ(i + 1, i, this.getIJ(i + 1, i));
        }

        condensed_LUdecomp.diagonal[0] = this.getIJ(0 , 0);

        for(int i = 0; i < this.iDim - 1; i++){
            
            condensed_LUdecomp.setIJ(i, i + 1, (this.getIJ(i, i) / condensed_LUdecomp.getIJ(i, i)));

            condensed_LUdecomp.setIJ(i + 1, i + 1, (this.getIJ(i + 1, i + 1) - (condensed_LUdecomp.getIJ(i + 1, i) * condensed_LUdecomp.getIJ(i, i + 1))));
        }

        return condensed_LUdecomp;
    }

    /**
     * Add the matrix to another second matrix.
     *
     * @param second  The Matrix to add to this matrix.
     * @return        The sum of this matrix with the second matrix.
     */
    public Matrix add(Matrix second){
        
        if(iDim == second.iDim && jDim == second.jDim) {
            
            if(second instanceof TriMatrix){
                TriMatrix result = new TriMatrix(this.iDim);

                for(int i = 0; i < iDim; i++) {
                    for(int j = 0; j < jDim; j++) {
                            result.setIJ(i, j, (this.getIJ(i, j) + second.getIJ(i,j)));
                    }
                }

                return result;

            } else {
                GeneralMatrix result = new GeneralMatrix(this.iDim, this.jDim);

                for(int i = 0; i < iDim; i++) {
                    for(int j = 0; j < jDim; j++) {
                            result.setIJ(i, j, (this.getIJ(i, j) + second.getIJ(i,j)));
                    }
                }

                return result;

            }
        } else {
            throw new MatrixException("Incorrect matrix sizes for addition");
        }  
    }
    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
        if(this.jDim == A.iDim) {
            
            GeneralMatrix result = new GeneralMatrix(this.iDim, A.jDim);

            for(int i = 0; i < iDim; i++){
                for(int j = 0; j < jDim; j++){
                    double c_ij = 0.0;
                    
                    for(int k = 0; k < iDim; k++){
                        c_ij += this.getIJ(i, k) * A.getIJ(k, j);
                    }
                
                    result.setIJ(i, j, c_ij);
                
                }
            }

            return result;

        } else {
            throw new MatrixException("Incorrect matrix sizes for multiplication");
        }
    }
    
    /**
     * Multiply the matrix by a scalar.
     *
     * @param scalar  The scalar to multiply the matrix by.
     * @return        The product of this matrix with the scalar.
     */
    public Matrix multiply(double scalar) {
        
        TriMatrix result = new TriMatrix(this.iDim); 
        
        for(int i = 0; i < this.iDim - 1; i++) {
            result.setIJ(i, i, this.getIJ(i, i) * scalar);
            result.setIJ(i + 1, i, this.getIJ(i + 1, i) * scalar);
            result.setIJ(i, i + 1, this.getIJ(i, i + 1) * scalar);
        }

        result.setIJ(this.iDim - 1, this.jDim - 1, this.getIJ(this.iDim, this.iDim) * scalar);

        return result;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        for(int i = 0; i < iDim - 1; i++) {
                this.setIJ(i, i, Math.random());
                this.setIJ(i, i + 1, Math.random());
                this.setIJ(i + 1, i, Math.random());
        }
        this.setIJ(iDim - 1, iDim - 1, Math.random());
    }
    
    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {

        //Test constructor
        TriMatrix test_tri_1 = new TriMatrix(50);

        //Test random
        test_tri_1.random();

        long start = System.nanoTime();

        Double decomp = test_tri_1.determinant();

        long end = System.nanoTime();

        System.out.println(end - start);

        GeneralMatrix test_gen = new GeneralMatrix(50, 50);

        test_gen.random();

        long start_2 = System.nanoTime();

        Double decomp_2 = test_gen.determinant();

        long end_2 = System.nanoTime();

        System.out.println(end_2 - start_2);
    }

}