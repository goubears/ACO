
public class Algorithm {
	
	//variables:
	/* 1: number of ants */
	/* 2: number of iterations */
	/* 3: (alpha) the degree of influence of the pheromone component */
	/* 4: (beta) the degree of influence of the heuristic component */
	/* 5: (p) the pheromone evaporation factor */

	//the elitism factor in Elitist Ant System

	/* 6: (t) the pheromone concentration on the leg connecting 2 cities (initially t = (1-p)*t
			/* 7: (e) the factor controlling "wearing away" of pheromones in Ant Colony System (typically e = 0.1) */
	/* 8: (q) the probability in Ant Colony System that an ant will choose the best leg for the next leg of the tour it is constructing, instead of choosing probabilistically */

	/* 9: (d) distance between 2 cities */
	/* 10: (n) = 1/d */
	/* 11: (N) set of unvisited cities */
	/* 12: (b) best tour so far */
	/* 13: (L) total length of best-so-far tour */
	/* 14: (x) change in t on given leg in best-so-far tour */



	/*
	 * posting changes:
	 * git pull
	 * git add -A
	 * git commit -m "message"
	 * git push

	 * if it gives you shit, just do:
	 * git stash
	 * git add -A
	 * git commit -m "message"
	 * git push
	 */

	
	
	//use to determine where ant moves to next
		//exploit search experience strongly
			//use probability q to choose ant's next city destination
				//within probability -> maximize equation t*(n^beta) 
				//otherwise choose next city according to action choice rule
					//probabilityOfChoosingPathA = [(k+t(a))^alpha]/[((k+t(a))^alpha)+((k+t(otherPossiblePath))^alpha)+((k+t(otherPossiblePath)^alpha)+...]
	
	//use to update CityMap after a completed tour
		//pheromone increases only on legs of best-so-far tour
			//of leg is on best-so-far tour, add pheromone -> x = t+(1/L)
			//otherwise, nothing (change in t = 0)
		//pheromone evaporates on every leg after a tour -> t = t*(1-p)+(p*x)
	
	//use to update CityMap after each ant moves
		//pheromone "worn away" when ants traverse legs during tour construction
			//for each leg crossed, decrease t ->  t = (1-e)*t+e*smallConstant
	
	
	
	

}
