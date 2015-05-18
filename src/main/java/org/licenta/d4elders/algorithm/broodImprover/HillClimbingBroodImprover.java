package org.licenta.d4elders.algorithm.broodImprover;

import org.licenta.d4elders.model.SolutionOld;

public class HillClimbingBroodImprover implements BroodImproverAlgorithm {

	@Override
	public SolutionOld improve(SolutionOld brood) {
		final int limit = 100;
		int timeStep = 0;
		int neighbourhoodSize = 10;
		boolean localMaximum = false;

		while(timeStep < limit && !localMaximum){

			++timeStep;
			localMaximum = true;
			for(int i = neighbourhoodSize; i > 0; i--){
				SolutionOld newBrood = brood.randomMutation();
				if(newBrood.getFitness() > brood.getFitness()){
					brood = newBrood;
					localMaximum = false;
				}
			}
		}

		return brood;
	}

}
