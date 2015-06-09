package d4elders.algorithm.broodImprover;

import d4elders.model.Solution;

public class HillClimbingBroodImprover implements BroodImproverAlgorithm {

	private int neighbourhoodSize = 10;

	public HillClimbingBroodImprover(int neighSize) {
		neighbourhoodSize = neighSize;
	}
	@Override
	public Solution improve(Solution brood) {
		final int limit = 100;
		int timeStep = 0;

		boolean localMaximum = false;

		while(timeStep < limit && !localMaximum){

			++timeStep;
			localMaximum = true;
			for(int i = neighbourhoodSize; i > 0; i--){
				Solution newBrood = brood.randomSingleGenotypeMutation();
				if(newBrood.getFitness() > brood.getFitness()){
					brood = newBrood;
					localMaximum = false;
				}
			}
		}

		return brood;
	}

}
