package d4elders.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import d4elders.algorithm.broodImprover.BroodImproverHelper;
import d4elders.algorithm.helper.*;
import d4elders.dal.helper.RandomSolutionsGenerator;
import d4elders.dal.helper.SolutionsGenerator;
import d4elders.main.*;
import d4elders.model.Solution;

/**
 * TODO: adaguam vreo 10 FoodProviders care au 25% din retete (da, overlapping), delivery cost, time
 *
 * @author cristiprg
 *
 */
public class HoneyBeeMatingOptimization extends MainAlgorithm {
	private static final Logger log = Logger.getLogger(HoneyBeeMatingOptimization.class.getName());

	private SolutionsGenerator solGenerator;

	private AlgorithmConfigurationHBMO algorithmConfiguration = null;

	public AlgorithmConfigurationHBMO getAlgorithmConfiguration() {
		return algorithmConfiguration;
	}

	/**
	 * Applies the configuration in Solution and BroodImproverHelper classs too.
	 *
	 * @param algorithmConfiguration
	 */
	public void setAlgorithmConfiguration(AlgorithmConfigurationHBMO algorithmConfiguration) {
		this.algorithmConfiguration = algorithmConfiguration;
		Solution.applyConfiguration(algorithmConfiguration);
		BroodImproverHelper.applyConfiguration(algorithmConfiguration);
	}

	public HoneyBeeMatingOptimization(SolutionsGenerator solGenerator) {
		this.setSolGenerator(solGenerator);
	}

	public HoneyBeeMatingOptimization(SolutionsGenerator solutionsGenerator,
			AlgorithmConfigurationHBMO algorithmConfiguration) {
		setAlgorithmConfiguration(algorithmConfiguration);
		setSolGenerator(solutionsGenerator);
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
		// SortedSet<Solution> solutions =
		// InitialSolutionsGenerator.generateRandomSolutions(algorithmConfiguration
		// .getPopSize());
		long start = System.currentTimeMillis();
		SortedSet<Solution> solutions = getSolGenerator().generateRandomSolutionsWithSimilarityCoeff(
				algorithmConfiguration.getPopSize(), algorithmConfiguration.getSimilarityCoefficientThreshold());
		long duration = System.currentTimeMillis() - start;
		// for (Solution sol : solutions) {
		// System.out.println(sol.getDailyMenu().getBreakfast().getPackageId() +
		// ", "
		// + sol.getDailyMenu().getLunch().getPackageId() + ", "
		// + sol.getDailyMenu().getDinner().getPackageId() + ", "
		// + sol.getDailyMenu().getSnack1().getPackageId() + ", "
		// + sol.getDailyMenu().getSnack2().getPackageId());
		// }
		// the bee with the highest fitness function is selected, the others
		// become drones
		Solution queen = solutions.last();

		nrOfIterations = 0;

		// Do .. while the queen still has energy
		// TODO: de fiecare data cand intram in iteratie, populatia are sizeul
		// fix, popSize
		while (queen.hasEnergy() && queen.hasSpeed()) {
			start = System.currentTimeMillis();
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
				if (nrOfDronesTheQueenMatedWith > algorithmConfiguration.getMaxNrMatings())
					break;
			}
			// If there is no drone with good enough fitness generate new random
			// set of solutions
			// This step is not documented in the algorithm, should be devised
			// if needed or not
			if (broods.isEmpty()) {
				solutions = getSolGenerator().generateRandomSolutions(algorithmConfiguration.getPopSize());
				queen.nextIteration();
				continue;
			}

			broods.addAll(broodImprover.improve(broods));
			duration = System.currentTimeMillis() - start;
			// System.out.println();
			// TODO: add broods to population and then select the best 40, do
			// not throw away the
			// current solutions
			// The old drones die, thus the new drones will be top 40 broods
			// that have the best fitness (take the last 40 broods)
			// solutions = new TreeSet<Solution>();
			// int nrOfBadBroods = broods.size() -
			// algorithmConfiguration.getPopSize();
			// for (Solution br : broods) {
			// if (nrOfBadBroods > 0)
			// nrOfBadBroods--;
			// else
			// solutions.add(br);
			// }
			// select new population randomly

			solutions = new TreeSet<Solution>();
			int curPopSize = algorithmConfiguration.getPopSize();
			Random r = new Random();
			for (Solution sol : broods) {
				if (r.nextBoolean()) {
					solutions.add(sol);
					curPopSize--;
				}
				if (curPopSize <= 0)
					break;
			}
			// printNewSetOfSolutions(solutions);

			// the best brood is selected as the queen
			Solution bestBrood = broods.last();
			// if bestBrood has a greater fitness function then
			if (bestBrood.compareTo(queen) > 0) {
				queen = bestBrood;
				/*System.out.println(queen.getDailyMenu().getBreakfast()
						.getPackageId()
						+ ", "
						+ queen.getDailyMenu().getLunch().getPackageId()
						+ ", "
						+ queen.getDailyMenu().getDinner().getPackageId()
						+ ", "
						+ queen.getDailyMenu().getSnack1().getPackageId()
						+ ", "
						+ queen.getDailyMenu().getSnack2().getPackageId());*/
			}

			/*
			 * System.out.println("Nr of MatingDrones: " + nrOfDronesTheQueenMatedWith +
			 * ", Broods Generated:" + broods.size());
			 */

			// Reduce speed and energy of queen
			queen.nextIteration();

		}

		algDuration = System.currentTimeMillis() - startTime;
		return queen;
	}

	@Override
	public ArrayList<String> getCustomHeadersForExportedData() {
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
		headers.add("Hill-Climb negih size");
		headers.add("T0");
		headers.add("alpha");
		headers.add("Tmin");
		headers.add("TabuSearchIters");
		headers.add("Tabu Size");
		headers.add("Tabu neigh size");

		headers.add("Number of iterations");
		headers.add("Fitness Min");
		headers.add("Fitness Max");
		headers.add("Fitness Avg");
		headers.add("Duration Min");
		headers.add("Duration Max");
		headers.add("Duration Avg");



		super.addDefaultHeadersForExportedData(headers);

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

	public SolutionsGenerator getSolGenerator() {
		return solGenerator;
	}

	public void setSolGenerator(SolutionsGenerator solGenerator) {
		this.solGenerator = solGenerator;
	}
}
