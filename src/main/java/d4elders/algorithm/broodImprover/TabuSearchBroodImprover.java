package d4elders.algorithm.broodImprover;

import java.util.TreeSet;

import d4elders.model.Solution;

/**
 * Implementation of algorithm described here: http://en.wikipedia.org/wiki/Tabu_search
 * or here: http://www.cleveralgorithms.com/nature-inspired/stochastic/tabu_search.html
 * @author cristiprg
 */
public class TabuSearchBroodImprover implements BroodImproverAlgorithm {

	private int limit = 100;
	private int maxTabuSize = 10;
	private int neighbourhoodSize = 10;

	public TabuSearchBroodImprover(int limit, int maxTabuSize, int neighSize) {
		this.limit = limit;
		this.maxTabuSize = maxTabuSize;
		this.neighbourhoodSize = neighSize;
	}

	@Override
	public Solution improve(Solution brood) {


		int timeStep = 0;

		TreeSet<Solution> tabuList = new TreeSet<Solution>();
		TreeSet<Solution> candidateList = new TreeSet<Solution>();
		Solution bestCandidate = null;
		Solution globalBest = brood;
		Solution current = brood;

		while(timeStep < limit){

			timeStep++;
			candidateList.clear();

			// Generate the candidates.
			candidateList = getNeighbourhood(current, tabuList, neighbourhoodSize);

			// Get the best candidate
			bestCandidate = candidateList.last();

			// Replace current
			current = bestCandidate;

			// If the best candidate is better than the global best solution
			if(bestCandidate.getFitness() > globalBest.getFitness()){
				globalBest = bestCandidate;
			}

			// Add the best candidate to tabu list
			tabuList.add(bestCandidate);


			// Make sure that the tabu list has at most maxTabuSize elements.
			while(tabuList.size() > maxTabuSize){
				tabuList.remove(tabuList.first());

			}
		}
		return globalBest;
	}

	/**
	 * Generates candidates that are not in the tablu list
	 * @param brood
	 * @param tabuList
	 * @param neighbourhoodSize
	 * @return
	 */
	private TreeSet<Solution> getNeighbourhood(Solution brood, TreeSet<Solution> tabuList,
			int neighbourhoodSize) {
		TreeSet<Solution> set = new TreeSet<Solution>();
		Solution candidate = null;
		for(int i = neighbourhoodSize; i > 0; i--){
			candidate = brood.randomSingleGenotypeMutation();
			if (!tabuListContains(tabuList, candidate)){
				set.add(candidate);
			}
		}

		// make sure there is at least one candidate
		if(set.size() == 0){
			set.add(candidate);
		}

		return set;
	}

	private boolean tabuListContains(TreeSet<Solution> tabuList,
			Solution candidate) {

		for(Solution s : tabuList){
			if(s.equals(candidate)){
				return true;
			}
		}

		return false;
	}

}
