
public class Algorithm {
	
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
