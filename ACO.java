       
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

	public static void runTestShared(){
		PrintStream out = System.out;
        PrintStream std = System.out;
	    try
	    {
	        FileOutputStream fos = new FileOutputStream("resultsShared.csv", true); 
	        out = new PrintStream(fos);
   		 	System.setOut(out);     
	    }
	    catch (FileNotFoundException ex)  
	    {
	    	System.out.println(ex.getMessage());
	    }

	   	elitism = 20; 					// placeholder
	   	// epsilon = 0.1;					// placeholder
	   	// qProb = 0.9;					// placeholder


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
	   	
	   		for(int a=0; a<2; a++){
	   			switch (a){
	   				case 0:
	   					ants = 3;
	   					break;
	   				case 1:
	   					ants = 10;
	   					break;
	   				default:
	   					System.out.printf("Error in ants.");
	   					break;
	   			}
	   			System.out.printf("\nAnts: %d\n", ants);

	   			for(int b=0; b<2; b++){	
	   				switch (b){				
	   					case 0:
	   						iterations = 3;
	   						break;
	   					case 1:
	   						iterations = 10;
	   						break;
	   					default:
	   						System.out.printf("Error in iterations.");
	   						break;
	   				}
	   				System.out.printf("\nIterations: %d\n", iterations);

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
	   										System.out.printf("\nElitist Ant System\n");
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
	   		}

	   		//A
	    	place = null;
	    	place = new Vector<City>();
	   	}
	}


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



	public static void runTestACS(){
		PrintStream out = System.out;
        PrintStream std = System.out;
	    try
	    {
	        FileOutputStream fos = new FileOutputStream("resultsACS.csv", true); 
	        out = new PrintStream(fos);
   		 	System.setOut(out);     
	    }
	    catch (FileNotFoundException ex)  
	    {
	    	System.out.println(ex.getMessage());
	    }

	    int runs = 3;

	    //shares Variables
		ants = 7;
		iterations = 5;
		alpha = 1;
		beta = 3.5;
		rho = 0.1;

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

	    	for(int j=0; j<2; j++){
	    		switch (j){
	    			case 0:
	    				epsilon = 0.1;
	    				break;
	    			case 1:
	    				epsilon = 0.2;
	    				break;
	    			default:
	    				System.out.printf("Error in epsilon.");
	    				break;
	    		}
	    		System.out.printf("\nEpsilon: %f\n", epsilon);

	    		for(int k=0; k<2; k++){
	    			switch (k){
	    				case 0:
	    					qProb = 0.5;
	    					break;
	    				case 1:
	    					qProb = 0.9;
	    					break;
	    				default:
	    					System.out.println("Error in q probability.");
	    					break;
	    			}
	    			System.out.printf("\nQ Probability: %f\n", qProb);

	    			double avgLength = 0;
	    			double avgTime = 0;
	    			for(int l=0; l<runs; l++){
	    				antCS.acs(fileName, optimum, place, ants, iterations, alpha, beta, rho, epsilon, qProb);
	    				avgLength += antCS.getBestLength();
						avgTime += (double)antCS.getTime();
						System.setOut(std); 
						float pComplete = (((float)l+1)/(float)runs)*100;
						System.out.printf("%d%%\n", (int)pComplete);
						System.setOut(out);
	    			}
	    			avgLength = avgLength/(double)runs;
	    			avgTime = avgTime/(double)runs;
	    			System.out.printf("\nAvgLength, AvgTime\n%f,   %f\n", avgLength, avgTime);
	    		}
	    	}

	    	//A
	    	place = null;
	    	place = new Vector<City>();
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

	    int runs = 3;

		//shared variables    			 		//change variables depending on the document
		ants = 7;
		iterations = 5;
		alpha = 1;
		beta = 3.5;
		rho = 0.1;

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

	    	//A
	    	place = null;
	    	place = new Vector<City>();
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