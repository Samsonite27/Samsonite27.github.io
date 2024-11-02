/*
 * PROJECT I: Project1.java
 *
 * As in project 0, this file - and the others you downloaded - form a
 * template which should be modified to be fully functional.
 *
 * This file is the *last* file you should implement, as it depends on both
 * Point and Circle. Thus your tasks are to:
 *
 * 1) Make sure you have carefully read the project formulation. It contains
 *    the descriptions of all of the functions and variables below.
 * 2) Write the class Point.
 * 3) Write the class Circle
 * 4) Write this class, Project1. The results() method will perform the tasks
 *    laid out in the project formulation.
 */

import java.util.*;
import java.io.*;

public class Project1 {
    // -----------------------------------------------------------------------
    // Do not modify the names or types of the instance variables below! This 
    // is where you will store the results generated in the Results() function.
    // -----------------------------------------------------------------------
    public int      circleCounter; // Number of non-singular circles in the file.
    public double[] aabb;          // The bounding rectangle for the first and 
                                   // last circles
    public double   Smax;          // Area of the largest circle (by area).
    public double   Smin;          // Area of the smallest circle (by area).
    public double   areaAverage;   // Average area of the circles.
    public double   areaSD;        // Standard deviation of area of the circles.
    public double   areaMedian;    // Median of the area.
    public int      stamp = 220209;
    // -----------------------------------------------------------------------
    // You should implement - but *not* change the types, names or parameters of
    // the variables and functions below.
    // -----------------------------------------------------------------------

    /**
     * Default constructor for Project1. You should leave it empty.
     */
    public Project1() {
        // This method is complete.
    }

    /**
     * Results function. It should open the file called fileName (using
     * Scanner), and from it generate the statistics outlined in the project
     * formulation. These are then placed in the instance variables above.
     *
     * @param fileName  The name of the file containing the circle data.
     */
    public void results(String fileName){
        Circle[] circles_data;
        double x, y, rad;
        int linecount = 0;

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("student.data")))) {
            while(scanner.hasNext()) {
                linecount++;
                scanner.nextLine();            
        }
        
        Scanner scanner2 = new Scanner(new BufferedReader(new FileReader("student.data")));
        circles_data = new Circle[linecount];

        for(int i = 0; i < linecount; i++) {
            x = scanner2.nextDouble();
            y = scanner2.nextDouble();
            rad = scanner2.nextDouble();
            scanner2.nextLine();

            circles_data[i] = new Circle(new Point(x,y), rad);
        }
       
        
        sortCirclesByArea(circles_data);


        if (circles_data.length % 2 == 0) {
            areaMedian = (circles_data[(circles_data.length / 2) - 1].area() + circles_data[(circles_data.length / 2)].area()) * 0.5;
        } else {
            areaMedian = circles_data[(circles_data.length - 1) / 2].area();
        }

        areaAverage = averageCircleArea(circles_data);
        areaSD = areaStandardDeviation(circles_data);
        Smax = circles_data[circles_data.length - 1].area();
        Smin = circles_data[0].area();
        circleCounter = circles_data.length - countSingularCircles(circles_data);
        aabb = calculateAABB(circles_data);


    } catch(Exception e) {
        System.err.println("An error has occured. See below for details");
        e.printStackTrace();
    } 
        
    }

    /**
     * A function to calculate the avarage area of circles in the array provided. 
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     */
    public double averageCircleArea(Circle[] circles) {
      double sum = 0.0; 
      for(Circle i : circles) {
        sum += i.area();
      }
      double to_assign_areaAverage = (sum / circles.length);
      return to_assign_areaAverage;
    }
    
    /**
     * A function to calculate the standard deviation of areas in the circles in the array provided. 
     * This array may contain 0 or more circles.
     * 
     * @param circles  An array of Circles
     */
    public double areaStandardDeviation(Circle[] circles) {
      double sum = 0.0;
      double mean = averageCircleArea(circles);
      for(Circle i : circles) {
        sum += Math.pow((i.area() - mean), 2);
      }
      double to_assign_SD = Math.sqrt((sum / circles.length));
      return to_assign_SD;
    }
    
    /**
     * Returns 4 values in an array [X1,Y1,X2,Y2] that define the rectangle
     * that surrounds the array of circles given.
     * This array may contain 0 or more circles.
     *
     * @param circles  An array of Circles
     * @return An array of doubles [X1,Y1,X2,Y2] that define the bounding rectangle with
     *         the origin (bottom left) at [X1,Y1] and opposite corner (top right)
     *         at [X2,Y2]
     */
    public double[] calculateAABB(Circle[] circles)
    {
        Circle tenth_non_singular = circles[9 + countSingularCircles(circles)];
        Circle twenty_non_singuler = circles[19 + countSingularCircles(circles)];
        double tenth_rad = tenth_non_singular.getRadius();
        double twenty_rad = twenty_non_singuler.getRadius();
        Point ten_c = tenth_non_singular.getCentre();
        Point twen_c = twenty_non_singuler.getCentre();
        double ten_c_x = ten_c.getX();
        double ten_c_y = ten_c.getY();
        double twen_c_x = twen_c.getX();
        double twen_c_y = twen_c.getY();

        double max_ten_x = ten_c_x + tenth_rad;
        double min_ten_x = ten_c_x - tenth_rad;

        double max_ten_y = ten_c_y + tenth_rad;
        double min_ten_y = ten_c_y - tenth_rad;

        double max_twen_x = twen_c_x + twenty_rad;
        double min_twen_x = twen_c_x - twenty_rad;

        double max_twen_y = twen_c_y + twenty_rad;
        double min_twen_y = twen_c_y - twenty_rad;

        double xmin = Math.min(min_ten_x, min_twen_x);
        double xmax = Math.max(max_ten_x, max_twen_x);

        double ymin = Math.min(min_ten_y, min_twen_y);
        double ymax = Math.max(max_ten_y, max_twen_y);

        return new double[]{xmin,xmax,ymin,ymax};
    }

    public void sortCirclesByArea(Circle[] circles) {
        for (int i = 0; i < circles.length - 1; i++) {
            for (int j = 0; j < circles.length - i - 1; j++) {
                if (circles[j].area() > circles[j + 1].area()) {
                    Circle temporary = circles[j];
                    circles[j] = circles[j + 1];
                    circles[j + 1] = temporary;
                }
            }
        }
    }

    public int countSingularCircles(Circle[] circles_data) {
        int singular = 0;
        for (int i = 0; i < circles_data.length; i++) {
            if(circles_data[i].getRadius() <=  1.0e-6) {
                singular += 1;
            }
        }
        return singular;
    }
  
    // =======================================================
    // Tester - tests methods defined in this class
    // =======================================================

    /**
     * Your tester function should go here (see week 14 lecture notes if
     * you're confused). It is not tested by BOSS, but you will receive extra
     * credit if it is implemented in a sensible fashion.
     */
    public static void main(String args[]){
        Project1 project1 = new Project1();
        project1.results("student.data");
    }
}