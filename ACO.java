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

public class ACO {

	//an instance of the elitism system
	private static ElitistAnt elite;
	private static ACS antCS;

	//file Variables
	private static BufferedReader reader = null;
	private static File file;
	private static String fileName;

	//a vector that contains the info of all cities
	private static Vector<City> place = new Vector<City>();

	//shares variables
	private static String algorithm;
	private static int ants;
	private static int iterations;
	private static double alpha;
	private static double beta;
	private static double rho;
	private static double optimum;

	//elitist ant variables
	private static double elitism;

	//any colony variables
	private static double epsilon;
	private static double qProb;

	//contains the output of the algorithms
	private static double[] output = new double[2]; 
	//0: best length 
	//1: time in seconds

	//main method
	public static void main(String[] args) 
	{
		if(args.length != 0){
			runCommand(args);
		}
		else{
			runTestElitism();
			//runTestACS();
		}
	}

	public static void runTestElitism(){
		PrintStream out = System.out;
        PrintStream std = System.out;
	    try
	    {
	        FileOutputStream fos = new FileOutputStream("resultsElitism.csv", true); 
	        out = new PrintStream(fos);
   		 	System.setOut(out);     
	    }
	    catch (FileNotFoundException ex)  
	    {
	    	System.out.println(ex.getMessage());
	    }

	    int runs = 10;

	    //fileName = "u2152.tsp";
	    //file = new File(fileName);

		//shares variables
		algorithm = "e";
		ants = 20;
		iterations = 50;
		alpha = 1;
		beta = 3.5;
		rho = 0.1;
		//optimum = 64253;

		//elitist ant variables
		//elitism = 10;

		 //first set of test to optimize the specialized parameters of elitist and acs
	    for(int i=0; i<4; i++){
	    	switch (i){
	    		case 0: 
	    			fileName = "u2152.tsp";
	    			file = new File(fileName);
	    			optimum = 64253;
	    			break;
	    		case 1:
	    			fileName = "fl3795.tsp";
	    			file = new File(fileName);
	    			optimum = 28772;
	    			break;
	    		case 2:
	    			fileName = "fnl4461.tsp";
	    			file = new File(fileName);
	    			optimum = 182566;
	    			break;
	    		case 3:
	    			fileName = "rl5934.tsp";
	    			file = new File(fileName);
	    			optimum = 556045;
	    			break;
	    		default:
	    			System.out.printf("Error in file name.");
	    			break;
	    	}
	    	System.out.printf("\nFile Name: %s\n", fileName);
	    	readFile(file);

	    	for(int j=0; j<3; j++){
	    		switch (j){
	    			case 0:
	    				elitism = 10;
	    				break;
	    			case 1:
	    				elitism = 20;
	    				break;
	    			case 2:
	    				elitism = 30;
	    				break;
	    			default: 
	    				System.out.printf("Error in elitism factor.");
	    				break;
	    		}
	    		System.out.printf("\nElitism Factor: %f\n", elitism);

	    		double avgLength = 0;
	    		double avgTime = 0;
	    		for(int k=0; k<runs; k++){
	    			elite.elitistAnt(place, ants, elitism, alpha, beta, rho, iterations, fileName, optimum);
	    			avgLength += elite.getBestLength();
					avgTime += (double)elite.getDuration();
					System.setOut(std); 
					float pComplete = (((float)k+1)/(float)runs)*100;
					System.out.printf("%d%%\n", (int)pComplete);
					System.setOut(out);
	    		}
	    		avgLength = avgLength/(double)runs;
	    		avgTime = avgTime/(double)runs;
	    		System.out.printf("\nAvgLength, AvgTime\n%f,   %f\n", avgLength, avgTime);

	    	}
	    	place.removeAllElements();
	    }   
	}

	public static void runCommand(String[] arg){
		file 		= new File(arg[0]);
		algorithm 	= arg[1];  					//either e or a
		ants 		= Integer.parseInt(arg[2]);	//integer
		iterations 	= Integer.parseInt(arg[3]);	//integer
		alpha 		= Double.parseDouble(arg[4]);	//double
		beta		= Double.parseDouble(arg[5]);	//double
		rho			= Double.parseDouble(arg[6]);	//double
		optimum 	= Double.parseDouble(arg[7]); 	//double

		readFile(file);
		
		if(algorithm.equals("e"))
		{
			elitism = Double.parseDouble(arg[8]);	//double
			
			//ANDREW: add optimum into your args
			elite.elitistAnt(place, ants, elitism, alpha, beta, rho, iterations, arg[0], optimum);
			output[0] = elite.getBestLength();
			output[1] = (double)elite.getDuration();
		}
		else if(algorithm.equals("a"))
		{
			epsilon = Double.parseDouble(arg[8]);	//double
			qProb 	= Double.parseDouble(arg[9]);	//double
			
			antCS.acs(arg[0], optimum, place, ants, iterations, alpha, beta, rho, epsilon, qProb);
			output[0] = antCS.getBestLength();
			output[1] = (double)antCS.getTime();
		}
		else
		{ 
			System.out.println("A valid algorithm name is needed.  e/a");
			System.exit(0);
		}

		System.out.printf("Best Length: %.0f, Time: %f, %f",output[0], output[1]);
		System.out.println();
	}


	//reads file and stores the cities coordinates and identifier
	public static void readFile(File f) 
	{	
		try 
		{
			reader = new BufferedReader(new FileReader(f));
			String line;
			boolean tempC = false; //true if the line read is coordinates

			while (!(line = reader.readLine()).equals("EOF")) 
			{
				if (line.equals("NODE_COORD_SECTION")) 
				{
					tempC = true;
					continue;
				}
				if(tempC)
				{
					String tempL = line.trim();
					String[] splitStr = tempL.split("\\s+");

					if(splitStr.length != 3)
					{
						System.out.println("There are more than 2 coordinates.");
						System.exit(0);
					}
					
					int identifier = Integer.parseInt(splitStr[0]); 
					double xCoord =	Double.parseDouble(splitStr[1]);
					double yCoord =	Double.parseDouble(splitStr[2]);

					City town = new City(xCoord, yCoord, identifier);
					place.add(town);
				}
			}

			reader.close();
		} 
		catch (Exception e) 
		{
			System.err.format("Exception occurred trying to read '%s'.", f);
			e.printStackTrace();	
		}
	}
	
}