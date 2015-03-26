        /****************************************
         *                                      *
         *              Path class              *
         *         Andrew Miller-Smith          *
         *                                      *
         ****************************************/

        /*

        Description: The path class represents the path between two cities. It stores the two cities, as well as the 
                        distance between them, the pheromone levels on that path, and a unique identifier. Functions 
                        include basic get and set equations for the variables.

        */

import java.util.*;
import java.io.*;
import java.util.Random;

//import java.util.Timer;


public class Path {

    //variables for the path
    private double pherLevel;
    private City a;
    private City b;
    private int identifier;
    private double length;

    //constructor takes the two cities and the unique identifier. Initializes the pheromone level to 0 and calculates the path length
    public Path(City one, City two, int index){

        a = one;
        b = two;
        identifier = index;
        pherLevel = 0.2;
        length = Math.sqrt((a.getX() - b.getX())*(a.getX() - b.getX()) + (a.getY() - b.getY())*(a.getY() - b.getY()));
    }

    public double getPheromone(){

        return pherLevel;
    }

    public void setPheronome(double update){

        pherLevel = update;
    }

    public City getA(){

        return a;
    }

    public City getB(){

        return b;
    }

    public int getIdentifier(){

        return identifier;
    }

    public double getLength(){

        return length;
    }

}