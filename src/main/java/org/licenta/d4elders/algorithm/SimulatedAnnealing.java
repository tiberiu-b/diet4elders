package org.licenta.d4elders.algorithm;

import java.util.ArrayList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.licenta.d4elders.main.Solution;

public class SimulatedAnnealing {

	private final AnnealingScheduler scheduler;

	public SimulatedAnnealing(AnnealingScheduler scheduler){
		this.scheduler = scheduler;
	}

	public SimulatedAnnealing(){
		this(new AnnealingScheduler());
	}

	/**
	 * Performs the simulated annealing search on the searchSpace. The neigborhood of
	 * a node is selected randomly, so for performance reasons, the searchSpace must
	 * be an array.
	 * @param searchSpace an array of Solutions
	 * @return a SortedSet of Solutions
	 */
	public SortedSet<Solution> search(ArrayList<Solution> searchSpace){
		SortedSet<Solution> outcome = new TreeSet<Solution>();
		randomGuess(outcome, searchSpace);
		Random r = new Random();

		int timeStep = 0;

		while (true){
			double temperature = scheduler.getTemp(timeStep);
			timeStep++;

			if(temperature == 0.0){

				break;
			}

			// The neighbourhood
			int neighbourhoodSize = scheduler.getNumberOfEvaluations(timeStep);
			for(int i = neighbourhoodSize; i > 0; --i){
				int randomIndex = r.nextInt(searchSpace.size());
				double deltaE = searchSpace.get(randomIndex).getFitness() - outcome.last().getFitness();

				if(shouldAccept(temperature, deltaE)){
					// to accept a new solution 1) remove the weakest and 2) insert the new one.
					outcome.remove(outcome.last());
					outcome.add(searchSpace.get(randomIndex));
				}
				else {
					System.out.println("O intrat temp=" + temperature + " timeStep=" + timeStep);
				}
			}

		}

		return outcome;
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
	public double probabilityOfAcceptance(double temperature, double deltaE) {
		return Math.exp(deltaE / temperature);
	}

	// if /\E > 0 then current <- next
	// else current <- next only with probability e^(/\E/T)
	private boolean shouldAccept(double temperature, double deltaE) {
		return (deltaE > 0.0)
				|| (new Random().nextDouble() <= probabilityOfAcceptance(
						temperature, deltaE));
	}

	private void randomGuess(SortedSet<Solution> outcome, ArrayList<Solution> searchSpace) {
		final int outcomeSize = 10;
		final int searchSpaceSize = searchSpace.size();
		Random r = new Random();
		for (int i = outcomeSize; i > 0; --i){
			outcome.add(searchSpace.get(r.nextInt(searchSpaceSize)));
		}

	}
}
