package org.licenta.d4elders.algorithm.broodImprover;

import org.licenta.d4elders.main.Solution;

public class HillClimbingBroodImprover implements BroodImproverAlgorithm {

	@Override
	public Solution improve(Solution brood) {
		final int limit = 100;
		int timeStep = 0;
		int neighbourhoodSize = 10;
		boolean localMaximum = false;

		while(timeStep < limit && !localMaximum){

			++timeStep;
			localMaximum = true;
			for(int i = neighbourhoodSize; i > 0; i--){
				Solution newBrood = brood.randomMutation();
				if(newBrood.getFitness() > brood.getFitness()){
					brood = newBrood;
					localMaximum = false;
				}
			}
		}

		return brood;
	}

}
