        /****************************************
         *                                      *
         *              Path class              *
         *         Andrew Miller-Smith          *
         *                                      *
         ****************************************/

        /*

        Description: The Enrichment Center promises to always provide a safe testing environment. In dangerous testing environments, 
                        the Enrichment Center promises to always provide useful advice. For instance, the floor here will kill you. Try to avoid it. 
        */

import java.util.*;
import java.io.*;
import java.util.Random;

public class ElitistAnt {



    //Vector for storing tours the ants make
    private static Vector<Tour> solutions = new Vector<Tour>();

    //variables for storing best solution so far
    private static Tour bestSolution;
    private static double bestLength = Double.NEGATIVE_INFINITY;


    //time limit to stop algorithm. set at ten minutes
    private static final long TIME_LIMIT = 600000;

    //random number generators
    private static Random rand = new Random();

    //TEMP VARIABLES. WILL BE REPLACED WHEN INTEGRATE WITH INPUT CODE
    private static Vector<City> cities = new Vector<City>();
    private static Vector<Path> paths = new Vector<Path>();

    private static final int NUM_ANTS = 15;
    private static final double OPTIMAL_LENGTH = Double.POSITIVE_INFINITY;

    private static final int NUM_CITIES = 10;


    public static void main(String[] args){

        //FILL TEST VECTOR WITH RANDOM CITIES. REMOVE LATER
        generateCities();
        System.out.println("Number of cities: " + cities.size());


        
        /****************************************
         *                                      *
         *              Class tests             *
         *         Andrew Miller-Smith          *
         *                                      *
         ****************************************/

        // City testOne = new City(10, 20, 0);

        // City testTwo = new City(50, 60, 1);


        // Path myPath = new Path(testOne, testTwo, 100);

        // System.out.println("City one x: " + myPath.getA().getX());
        // System.out.println("City one y: " + myPath.getA().getY());
        // System.out.println("City two x: " + myPath.getB().getX());
        // System.out.println("City two y: " + myPath.getB().getY());
        // System.out.println("Length of path: " + myPath.getLength());

        // Tour testTour = new Tour();

        // System.out.println("Tour size: " + testTour.size());
        // System.out.println("Tour length: " + testTour.getLength());
        // testTour.add(myPath);

        // System.out.println("Tour size: " + testTour.size());
        // System.out.println("Tour length: " + testTour.getLength());


        //start timer
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        long duration = 0;


        //CREATE TEMP PATHS VECTOR. REMOVE LATER
        int counter = 0;
        for (int i = 0; i < cities.size(); i++){
            for (int j = i + 1; j < cities.size(); j++){

                Path newPath = new Path(cities.get(i), cities.get(j), counter);
                paths.add(newPath);
                counter++;
                System.out.println("City one: " + i + " City two: " + j + " Identifier: " + counter);

            }
        }

        //continue sending out ants (finding solutions and updating) until find optimum or reach time limit
        while (bestLength < OPTIMAL_LENGTH && duration <= TIME_LIMIT){

            System.out.println("Entered while loop.");


            //construct a tour for each ant
            for (int i = 0; i < NUM_ANTS; i++){

                //choose random starting city
                int startingCity = rand.nextInt(cities.size() - 1);

                //create array to track which cities we have visited, counter to track total number
                int numVisited = 1;
                boolean[] visitedCities = new boolean[NUM_CITIES];
                visitedCities[startingCity] = true;

                //tour to store the ant's journey
                Tour route = new Tour();

                //visit new cities until we have been to all of them
                while (numVisited < NUM_CITIES){

                    //loop through cities to find 
                    break;
                }




            }

            break;
        }


        endTime = System.currentTimeMillis();
        duration = (endTime - startTime);
        System.out.println("\nThis method took: " + duration + " milliseconds.");

    }


/*

 
//while bestSolution.length < bestLength && OTHER CONDITION???
 
                //for I = 0 to NUM_ANTS – 1
                                startingIndex = rand.nextInt(cities.size() - 1);
                                Choose random starting city
                                Create Boolean array to keep track of visited cities
                                Create counter to keep track of number of visited cities
                                Create counter to track tour length
 
                                While (not visited all cities)
Use probability functions to pick next city
                               
                                Store tour in vector
                                Check if best so far, update accordingly
 
                //Evaporate pheromones
                //create pherChange vector[double]
                For I = 0 to tourVector.size()
                               
                                For j = 0 to tour.length()
                                               
                                                If pherChange.get(currTour.path.get(j).identifier) == 0
 
                                                                add evaporation
 
                                                if currTour is best, add with elitism factor
 
                                                else add normally
 
                //loop through paths vector, add pherChange to each
//update bestSolution and bestLength
                //empty tours vector
               
//print out results, time, GLaDOS easter egg
 
                               
 
               
 
*/

    //method to fill temp cities vector
    public static void generateCities(){

        for (int i = 0; i < NUM_CITIES; i++){

            City newCity = new City(rand.nextInt(100), rand.nextInt(100), i);
            cities.add(newCity);
            System.out.println("X: " + newCity.getX() + " Y: " + newCity.getY() + " Identifier: " + newCity.getIdentifier());
        }
    }

}