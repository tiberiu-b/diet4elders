package org.licenta.d4elders.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.licenta.d4elders.algorithm.broodImprover.BroodImproverHelper;
import org.licenta.d4elders.helper.AlgorithmConfiguration;
import org.licenta.d4elders.helper.AvailableProgramConfigurationOptions;
import org.licenta.d4elders.main.InitialSolutionsGenerator;
import org.licenta.d4elders.model.Solution;

/**
 * TODO: adaguam vreo 10 FoodProviders care au 25% din retete (da, overlapping), delivery cost, time
 * 
 * @author cristiprg
 *
 */
public class HoneyBeeMatingOptimization extends MainAlgorithm {
	private static final Logger log = Logger.getLogger(HoneyBeeMatingOptimization.class.getName());

	private AlgorithmConfiguration algorithmConfiguration = null;

	public AlgorithmConfiguration getAlgorithmConfiguration() {
		return algorithmConfiguration;
	}

	/**
	 * Applies the configuration in Solution class too.
	 * 
	 * @param algorithmConfiguration
	 */
	public void setAlgorithmConfiguration(AlgorithmConfiguration algorithmConfiguration) {
		this.algorithmConfiguration = algorithmConfiguration;
		Solution.applyConfiguration(algorithmConfiguration);

	}

	public HoneyBeeMatingOptimization() {
		this(null);
	}

	public HoneyBeeMatingOptimization(AlgorithmConfiguration algorithmConfiguration) {
		setAlgorithmConfiguration(algorithmConfiguration);
	}

	@Override
	public Solution performAlgorithm() {
		if (algorithmConfiguration == null) {
			log.log(Level.SEVERE, "Cannot perform algorithm, no algorithmConfiguration given!");
			return null;
		}

		long startTime = System.currentTimeMillis();

		BroodImproverHelper broodImprover = new BroodImproverHelper();

		// an initial set of solutions (bees) is generated
		SortedSet<Solution> solutions = InitialSolutionsGenerator.generateRandomSolutions(algorithmConfiguration
				.getPopSize());

		// the bee with the highest fitness function is selected, the others
		// become drones
		Solution queen = solutions.last();

		nrOfIterations = 0;

		// Do .. while the queen still has energy
		// TODO: de fiecare data cand intram in iteratie, populatia are sizeul fix, popSize
		while (queen.hasEnergy() && queen.hasSpeed()) {
			nrOfIterations++;
			int nrOfDronesTheQueenMatedWith = 0;

			/*
			 * System.out.println("------\nQueen:" + queen + " Fitness: " + queen.getFitness());
			 */
			SortedSet<Solution> broods = new TreeSet<Solution>();
			for (Solution drone : solutions) {

				if (queen.equals(drone)) {
					// This is the queen actually.
					continue;
				}
				// if mating probability with drone > threshold => queen mates
				// with drone
				// TODO: adjust the threshold
				double probToMateDrone = queen.probabilityToMateDrone(drone);
				if (probToMateDrone > Solution.probabilityToMateDroneThreshold) {
					nrOfDronesTheQueenMatedWith++;
					// broods.addAll((queen.createBroods(drone)));
					broods.addAll(workerModificationStrategy(drone, queen));
				}
			}

			// If there is no drone with good enough fitness generate new random
			// set of solutions
			// This step is not documented in the algorithm, should be devised
			// if needed or not
			if (broods.isEmpty()) {
				System.out.println("Generating new set of solutions..");
				solutions = InitialSolutionsGenerator.generateRandomSolutions(algorithmConfiguration.getPopSize());
				queen.nextIteration();
				continue;
			}

			broods = broodImprover.improve(broods);

			// TODO: add broods to population and then select the best 40, do not throw away the
			// current solutions
			// The old drones die, thus the new drones will be top 40 broods
			// that have the best fitness (take the last 40 broods)
			solutions = new TreeSet<Solution>();
			int nrOfBadBroods = broods.size() - algorithmConfiguration.getPopSize();
			for (Solution br : broods) {
				if (nrOfBadBroods > 0)
					nrOfBadBroods--;
				else
					solutions.add(br);
			}
			// printNewSetOfSolutions(solutions);

			// the best brood is selected as the queen
			Solution bestBrood = broods.last();
			// if bestBrood has a greater fitness function then
			if (bestBrood.compareTo(queen) > 0) {
				queen = bestBrood;
				/*
				 * System.out .println("Found better! Queen is replaced with brood");
				 */
			}

			/*
			 * System.out.println("Nr of MatingDrones: " + nrOfDronesTheQueenMatedWith +
			 * ", Broods Generated:" + broods.size());
			 */

			// Reduce speed and energy of queen
			queen.nextIteration();

		}

		duration = System.currentTimeMillis() - startTime;
		return queen;
	}


	@Override
	public ArrayList<String> getHeadersForExportedData() {
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Brood Modification Strategy");
		headers.add("Worker Modification Strategy");
		headers.add("MarNrMatings");
		headers.add("PopSize");
		headers.add("Queen's initial energy");
		headers.add("Queen's initial speed");
		headers.add("Speed reduction factor");
		headers.add("Energy reduction amount");
		headers.add("Probability to mate drone threshold");
		headers.add("Fitness values");
		headers.add("Number of iterations");
		headers.add("Duration");
		headers.add("kCal");
		headers.add("Carbohydrates");
		headers.add("Proteins");
		headers.add("Fats");
		headers.add("VitA");
		headers.add("VitB");
		headers.add("VitC");
		headers.add("VitD");
		headers.add("Calcium");
		headers.add("Iron");
		headers.add("Sodium");


		return headers;
	}

	// private methods

	private Collection<? extends Solution> workerModificationStrategy(Solution drone, Solution queen) {

		switch (algorithmConfiguration.getBroodModificationStrategy()) {
		case AvailableProgramConfigurationOptions.PATH_RELINKING:
			return PathRelinking.pathRelinking(drone, queen);
		case AvailableProgramConfigurationOptions.SIMPLE_CROSSOVER:
			return queen.createBroods(drone);
		}

		log.log(Level.SEVERE, "Returning null due to invalid worker modification strategy!");
		return null;
	}
}
