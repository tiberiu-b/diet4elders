package org.licenta.d4elders.algorithm.broodImprover;

import java.util.TreeSet;

import org.licenta.d4elders.model.SolutionOld;

/**
 * Implementation of algorithm described here: http://en.wikipedia.org/wiki/Tabu_search
 * or here: http://www.cleveralgorithms.com/nature-inspired/stochastic/tabu_search.html
 * @author cristiprg
 */
public class TabuSearchBroodImprover implements BroodImproverAlgorithm {

	@Override
	public SolutionOld improve(SolutionOld brood) {
		final int limit = 100;
		final int maxTabuSize = 100;
		final int neighbourhoodSize = 10;

		int timeStep = 0;

		TreeSet<SolutionOld> tabuList = new TreeSet<SolutionOld>();
		TreeSet<SolutionOld> candidateList = new TreeSet<SolutionOld>();
		SolutionOld bestCandidate = null;
		SolutionOld globalBest = brood;
		SolutionOld current = brood;

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
	private TreeSet<SolutionOld> getNeighbourhood(SolutionOld brood, TreeSet<SolutionOld> tabuList,
			int neighbourhoodSize) {
		TreeSet<SolutionOld> set = new TreeSet<SolutionOld>();
		SolutionOld candidate = null;
		for(int i = neighbourhoodSize; i > 0; i--){
			candidate = brood.randomMutation();
			if (!tabuListContains(tabuList, candidate)){
				set.add(candidate);
			}
		}

		return set;
	}

	private boolean tabuListContains(TreeSet<SolutionOld> tabuList,
			SolutionOld candidate) {

		for(SolutionOld s : tabuList){
			if(s.equals(candidate)){
				return true;
			}
		}

		return false;
	}

}
