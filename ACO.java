/*
Tests:
You should implement and test: 
• Elitist Ant System, and
• Ant Colony System.

Parameters:
Your code should allow you to vary the following parameters:
• the number of ants,
• the number iterations,
• α, the degree of influence of the pheromone component,
• β, the degree of influence of the heuristic component,
• ρ, the pheromone evaporation factor,
• e, the elitism factor in Elitist Ant System, and
• the factors controlling “wearing away” of pheromones in Ant Colony System (ε and τ0), and
• q0, the probability in Ant Colony System that an ant will choose the best leg for the next leg of the tour it is constructing, instead of choosing probabilistically.

Termination conditions:
Given the kinds of tests I want you to run (see below), you will want to write your coder so that a run can be terminated:
• after a maximum number of iterations is reached, or
• after a tour has been found that is no more than a specified percentage over the
optimal (0.0 would mean you will not settle for anything less than the optimal), or
• both, whichever comes first.
or time elapsed

Reading in Files:
Problem files begin with some lines that provide information about the problem. 
In the example below: 
the problem name, 
a comment about the origin of the problem, 
the type of problem, 
how many cities, 
what type of TSP it is (in this example, a 2-d map using Euclidean distances), 
and a line specifying the beginning of the coordinates section, which is a list of coordinates of the cities (ending with EOF). 
You will need to calculate distances.

Testing Ranges:
You’ll want to use smaller problems to debug your code, but I want you to conduct tests on at least one problem in each of the following ranges of problem size: 
2,000-2,999 cities, 
3,000-3,999 cities, 
4,000-4,999 cities, and 
5,000-5,999 cities. 
*/

import java.io.*;
import java.util.*;

public class ACO{

	//an instance of the elitism system
	private static ElitistAnt elite;

	//file Variables
	private static BufferedReader reader = null;
	private static File file;

	//a vector that contains the info of all cities
	private static Vector<City> place = new Vector<City>();

	//shares variables
	private static String algorithm;
	private static int ants;
	private static int iterations;
	private static double alpha;
	private static double beta;
	private static double roh;
	private static double optimum;

	//elitist ant variables
	private static double elitism;

	//any colony variables
	private static double epsilon;
	private static double tao;
	private static double qProb;


	//Main method
	public static void main(String[] args) {
		
		file 		= new File(args[0]);
		algorithm 	= args[1];  					//either e or a
		ants 		= Integer.parseInt(args[2]);	//integer
		iterations 	= Integer.parseInt(args[3]);	//integer
		alpha 		= Double.parseDouble(args[4]);	//double
		beta		= Double.parseDouble(args[5]);	//double
		roh			= Double.parseDouble(args[6]);	//double
		optimum 	= Double.parseDouble(args[7]); 	//double

		if(algorithm.equals("e")){
			elitism = Double.parseDouble(args[8]);	//double

		}
		else if(algorithm.equals("a")){
			epsilon = Double.parseDouble(args[8]);	//double
			tao 	= Double.parseDouble(args[9]);	//double
			qProb 	= Double.parseDouble(args[10]);	//double
		}
		else{ 
			System.out.println("A valid algorithm name is needed.  e/a");
			System.exit(0);
		}

		readFile(file);

		//ANDREW: add optimum into your args
		elite.elitistAnt(place, ants, elitism, alpha, beta, roh, iterations, args[0]);

		
				
	}


	//reads file and stores the cities coordinates and identifier
	public static void readFile(File f) {
		
		try {
			reader = new BufferedReader(new FileReader(f));
			String line;
			boolean tempC = false; //true if the line read is coordinates

			while (!(line = reader.readLine()).equals("EOF")) {
				if (line.equals("NODE_COORD_SECTION")) {
					tempC = true;
					continue;
				}

				if(tempC){
					String tempL = line.trim();
					String[] splitStr = tempL.split("\\s+");

					if(splitStr.length != 3){
						System.out.println("There are more than 2 coordinates.");
						System.exit(0);
					}

					double[] tempS = new double[splitStr.length];
					tempS[0] = 	Double.parseDouble(splitStr[0]); 
					tempS[1] =	Double.parseDouble(splitStr[1]);
					tempS[2] =	Double.parseDouble(splitStr[2]);

					City town = new City(tempS);
					place.add(town);
				}
			}

			reader.close();

		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", f);
			e.printStackTrace();	
		}

		// for (int i =0; i<place.size(); i++) {  		
  //       	System.out.printf("%d, %f, %f %n",place.get(i).getIdentifier(), place.get(i).getX(), place.get(i).getY());
  //   	}
	}



}