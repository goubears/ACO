       
		/****************************************
         *                                      *
         *               ACO class              *
         *              Adela Yang              *
         *                                      *
         ****************************************/

        /*
        
        Description:    This class is the main class that calls the ACS and Elitist Ant Algorithm. 
        				The script for running the tests is also available here.         
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
			//runTestElitism();
			//runTestACS();
			runTestShared();
		}
	}

	/*the test script for testing the parameters of elitism with:
		e = 20
		iterations = 500
		file = d2103.tsp, pr2392.tsp
		ants = 20, 40
		alpha = 0.8, 1
		beta = 1, 3.5
		rho = 0.1, 0.2
	gets the bestlength and time
	average over 3 runs
	*/
	public static void runTestShared(){
		PrintStream out = System.out;
        PrintStream std = System.out;
	    try
	    {
	        FileOutputStream fos = new FileOutputStream("resultsSharedNewE.csv", true); 
	        out = new PrintStream(fos);
   		 	System.setOut(out);     
	    }
	    catch (FileNotFoundException ex)  
	    {
	    	System.out.println(ex.getMessage());
	    }

	   	elitism = 20; 					
	   	iterations = 500;


	   	for(int i=0; i<2; i++){
	    	switch (i){
	    		case 0: 
	    			fileName = "d2103.tsp";
	    			file = new File(fileName);
	    			optimum = 80450;
	    			break;
	    		case 1:
	    			fileName = "pr2392.tsp";
	    			file = new File(fileName);
	    			optimum = 378032;
	    			break;
	    		default:
	    			System.out.printf("Error in file name.");
	    			break;
	    	}
	    	System.out.printf("\nFile Name: %s\n", fileName);
	    	readFile(file);
	   	
	   		for(int a=0; a<2; a++){
	   			switch (a){
	   				case 0:
	   					ants = 20;
	   					break;
	   				case 1:
	   					ants = 40;
	   					break;
	   				default:
	   					System.out.printf("Error in ants.");
	   					break;
	   			}
	   			System.out.printf("\nAnts: %d\n", ants);

	   				for(int c=0; c<2; c++){
	   					switch (c){				
	   						case 0:
	   							alpha = 0.8;
	   							break;
	   						case 1:
	   							alpha = 1;
	   							break;
	   						default:
	   							System.out.printf("Error in alpha.");
	   							break;
	   					}
	   					System.out.printf("\nAlpha: %f\n", alpha);

	   					for(int d=0; d<2; d++){
	   						switch (d){				//get actual values later
	   							case 0:
	   								beta = 1;
	   								break;
	   							case 1:
	   								beta = 3.5;
	   								break;
	   							default:
	   								System.out.printf("Error in beta.");
	   								break;
	   						}
	   						System.out.printf("\nBeta: %f\n", beta);

	   						for(int e=0; e<2; e++){
	   							switch (e){				//get actual values later
	   								case 0:
	   									rho = 0.1;
	   									break;
	   								case 1:
	   									rho = 0.2;
	   									break;
	   								default:
	   									System.out.printf("Error in rho.");
	   									break;
	   							}
	   							System.out.printf("\nRho : %f\n", rho);

	   							// for(int f=0; f<2; f++){
	   							// 	switch (f){				//get actual values later
	   							// 		case 0:
	   										//System.out.printf("\nElitist Ant System\n");
	   										testRunsElitism();
	   										//break;
	   									// case 1:
	   									// 	System.out.printf("\nAnt Colony System\n");
	   									// 	testRunsACS();
	   									// 	break;
	   									// default:
	   									// 	System.out.printf("Error in algorithm.");
	   									// 	break;
	   								//}
	   								System.out.printf("\nAvgLength, AvgTime\n%f,   %f\n", output[0], output[1]);
		   						//}
		   					}
	   					}
	   				}
	   			
	   		}

	   		//A
	    	place = null;
	    	place = new Vector<City>();
	   	}
	}



	//helper functions for test script of elitism
	public static void testRunsElitism(){
		int runs = 3;
		double avgLength = 0;
	    double avgTime = 0;
	    for(int l=0; l<runs; l++){
	    	elite.elitistAnt(place, ants, elitism, alpha, beta, rho, iterations, fileName, optimum);
	    	avgLength += elite.getBestLength();
			avgTime += (double)elite.getDuration();
	    }
	    output[0] = avgLength/(double)runs;
	 	output[1]= avgTime/(double)runs;
	}



	//helper functions for test script of acs
	public static void testRunsACS(){
		int runs = 3;
		double avgLength = 0;
	    double avgTime = 0;
	    for(int l=0; l<runs; l++){
	    	antCS.acs(fileName, optimum, place, ants, iterations, alpha, beta, rho, epsilon, qProb);
	    	avgLength += antCS.getBestLength();
			avgTime += (double)antCS.getTime();
	    }
	    output[0] = avgLength/(double)runs;
	 	output[1] = avgTime/(double)runs;
	}


	/*the test script for testing optimizing the elitism factor of elitism with:
		iterations = 500
		file = d2103.tsp, pr2392.tsp
		ants = 30
		alpha = 1
		beta = 3.5
		rho = 0.1
	gets the bestlength and time
	averaged over 3 runs
	*/
	public static void runTestElitism(){
		PrintStream out = System.out;
        PrintStream std = System.out;
	    try
	    {
	        FileOutputStream fos = new FileOutputStream("resultsElitismNew.csv", true); 
	        out = new PrintStream(fos);
   		 	System.setOut(out);     
	    }
	    catch (FileNotFoundException ex)  
	    {
	    	System.out.println(ex.getMessage());
	    }

	    int runs = 3;

		//shared variables    			 		//change variables depending on the document
		ants = 30;
		iterations = 500;
		alpha = 1;
		beta = 3.5;
		rho = 0.1;

		 //first set of test to optimize the specialized parameters of elitist and acs
	    for(int i=0; i<2; i++){
	    	switch (i){
	    		case 0: 
	    			fileName = "d2103.tsp";
	    			file = new File(fileName);
	    			optimum = 80450;
	    			break;
	    		case 1:
	    			fileName = "pr2392.tsp";
	    			file = new File(fileName);
	    			optimum = 378032;
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

	    	//A
	    	place = null;
	    	place = new Vector<City>();
	    }   
	}


	//calls the methods, if there are command line arguments
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

		System.out.printf("Best Length: %.0f, Time: %f",output[0], output[1]);
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

					//A
					town = null;
				}
			}

			reader.close();

			//A
			reader = null;
		} 
		catch (Exception e) 
		{
			System.err.format("Exception occurred trying to read '%s'.", f);
			e.printStackTrace();	
		}
	}
	
}