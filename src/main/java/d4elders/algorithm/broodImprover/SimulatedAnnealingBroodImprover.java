package d4elders.algorithm.broodImprover;

import java.util.Random;

import d4elders.algorithm.AnnealingScheduler;
import d4elders.algorithm.helper.AlgorithmConfiguration;
import d4elders.model.Solution;

public class SimulatedAnnealingBroodImprover implements BroodImproverAlgorithm {

	private final AnnealingScheduler scheduler;

	public SimulatedAnnealingBroodImprover(AnnealingScheduler scheduler){
		this.scheduler = scheduler;
	}

	public SimulatedAnnealingBroodImprover(){
		this(new AnnealingScheduler());
	}

	@Override
	public Solution improve(Solution brood) {
		// The initial guess is the already given brood.

		int timeStep = 0;

		while(true){
			double temperature = scheduler.getTemp(timeStep);
			timeStep++;

			if(temperature == 0.0){
				break;
			}

			int neighbourhoodSize = scheduler.getNumberOfEvaluations(timeStep);
			//int neighbourhoodSize = 1;

			for(int i = neighbourhoodSize; i > 0; --i){
				Solution newBrood = brood.randomSingleGenotypeMutation();
				double deltaE = newBrood.getFitness() - brood.getFitness();

				if(shouldAccept(temperature, deltaE)){
					brood = newBrood;
				}
			}
		}

		return brood;
	}

	/**
	 * Returns <em>e</em><sup>&delta<em>E / T</em></sup>
	 *
	 * @param temperature
	 *            <em>T</em>, a "temperature" controlling the probability of
	 *            downward steps
	 * @param deltaE
	 *            VALUE[<em>next</em>] - VALUE[<em>current</em>]
	 * @return <em>e</em><sup>&delta<em>E / T</em></sup>
	 */
	private double probabilityOfAcceptance(double temperature, double deltaE) {
		return Math.exp(deltaE / temperature);
	}

	// if /\E > 0 then current <- next
	// else current <- next only with probability e^(/\E/T)
	private boolean shouldAccept(double temperature, double deltaE) {
		/*return (deltaE > 0.0)
				|| (new Random().nextDouble() <= probabilityOfAcceptance(
						temperature, deltaE));*/
		return deltaE > 0.0;
	}

}
