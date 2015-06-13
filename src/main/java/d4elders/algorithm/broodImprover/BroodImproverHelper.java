package d4elders.algorithm.broodImprover;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import java.util.logging.Level;

import d4elders.algorithm.AnnealingScheduler;
import d4elders.algorithm.helper.AlgorithmConfigurationCuckoo;
import d4elders.algorithm.helper.AlgorithmConfigurationHBMO;
import d4elders.algorithm.helper.AvailableProgramConfigurationOptions;
import d4elders.model.Solution;
import d4elders.mvc.controller.Controller;

public class BroodImproverHelper {

	private static final Logger log = Logger
			.getLogger(BroodImproverHelper.class.getName());

	private final static int defaultNumberOfThreads = 4;

	/** List of supported search algorithms. */
	protected static List<BroodImproverAlgorithm> SEARCH_ALGOS = new ArrayList<BroodImproverAlgorithm>();
	protected static List<String> SEARCH_ALGOS_NAMES = new ArrayList<String>(); //hack to quickly find an algorithm

	/** Adds a new item to the list of supported search algorithms. */
	public static void addSearchAlgorithm(String name, BroodImproverAlgorithm algo) {
		SEARCH_ALGOS.add(algo);
		SEARCH_ALGOS_NAMES.add(name);
	}

	public static void applyConfiguration(AlgorithmConfigurationHBMO algorithmConfiguration) {
		SEARCH_ALGOS.clear();
		for (String strategy : algorithmConfiguration.getWorkerModificationStrategies())
			switch (strategy) {
			case AvailableProgramConfigurationOptions.SIMULATED_ANNEALING:
				addSearchAlgorithm("Simulated Annealing", new SimulatedAnnealingBroodImprover(new AnnealingScheduler(
						algorithmConfiguration.getT0(),algorithmConfiguration.getAlpha(), algorithmConfiguration.getTmin())));
				break;
			case AvailableProgramConfigurationOptions.HILL_CLIMBING:
				addSearchAlgorithm("Hill Climbing", new HillClimbingBroodImprover(algorithmConfiguration.getHillClimbingNeighborhoodSize()));
				break;
			case AvailableProgramConfigurationOptions.SIMPLE_TABU_SEARCH:
				addSearchAlgorithm("Tabu Search", new TabuSearchBroodImprover(
						algorithmConfiguration.getMaxNrIterations(),
						algorithmConfiguration.getTabuSize(),
						algorithmConfiguration.getTabuNeighborhoodSize()));
				break;
			}
	}

	public static void applyConfiguration(AlgorithmConfigurationCuckoo algorithmConfiguration) {
		SEARCH_ALGOS.clear();
		for (String strategy : algorithmConfiguration.getWorkerModificationStrategies())
			switch (strategy) {
			case AvailableProgramConfigurationOptions.SIMULATED_ANNEALING:
				addSearchAlgorithm("Simulated Annealing", new SimulatedAnnealingBroodImprover(new AnnealingScheduler(
						algorithmConfiguration.getT0(),algorithmConfiguration.getAlpha(), algorithmConfiguration.getTmin())));
				break;
			case AvailableProgramConfigurationOptions.HILL_CLIMBING:
				addSearchAlgorithm("Hill Climbing", new HillClimbingBroodImprover(algorithmConfiguration.getHillClimbingNeighborhoodSize()));
				break;
			case AvailableProgramConfigurationOptions.SIMPLE_TABU_SEARCH:
				addSearchAlgorithm("Tabu Search", new TabuSearchBroodImprover(
						algorithmConfiguration.getMaxNrIterations(),
						algorithmConfiguration.getTabuSize(),
						algorithmConfiguration.getTabuNeighborhoodSize()));
				break;
			}
	}

	/**
	 * TODO: revise the type of broods. Collection might not be the best one because it is
	 * immedately transformed into an array. Improves all the solutions contained in broods using
	 * numberOfThreads threads.
	 *
	 * @param broods
	 *            the initial set of broods
	 * @param numberOfThreads
	 *            the number of threads used to improve the broods
	 * @return the set of improved broods
	 */
	public SortedSet<Solution> improve(Collection<Solution> broods, int numberOfThreads) {

		// If no algorithms were specified, don't do a thing.
		if (SEARCH_ALGOS.size() == 0)
			return new TreeSet<Solution>();

		// array of threads
		ArrayList<Thread> threadsList = new ArrayList<Thread>();

		// The broods collection is going to be partitioned into numberOfThreads buckets and must be
		// transformed into an array first.
		ArrayList<Solution> broodsList = new ArrayList<Solution>();

		// TODO: decide whether to improve the entire or only a subset.
		// For now, we take the first 100 only.
		Iterator<Solution> it = broods.iterator();
		for (int i = 0; i < 4 && it.hasNext(); ++i) {
			broodsList.add(it.next());
		}

		// Compute the size of each bucket.
		int bucketSize = broodsList.size() / numberOfThreads;
		if (broodsList.size() % numberOfThreads != 0) {
			bucketSize++;
		}

		List<List<Solution>> partition = MyPartition.partition(broodsList, bucketSize);

		for (final List<Solution> bucket : partition) {
			Thread thread = new Thread(new AlgorithmJob(bucket));
			threadsList.add(thread);
			thread.start();
		}

		for (Thread t : threadsList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		TreeSet<Solution> returnValue = new TreeSet<Solution>(broodsList);
		returnValue.addAll(broods);
		return returnValue;
	}

	/**
	 * Improves all the solutios contained in broods using the default number of threads.
	 *
	 * @param broods
	 *            the initial set of broods
	 * @return the set of improved broods
	 */
	public SortedSet<Solution> improve(Collection<Solution> broods) {
		return improve(broods, defaultNumberOfThreads);
	}

	/**
	 * Improves one single brood using a random heuristic.
	 *
	 * @param brood
	 * @return a new, improved brood
	 */
	public Solution improve(Solution brood) {
		return improve(brood, SEARCH_ALGOS.get(new Random().nextInt(SEARCH_ALGOS.size())));
	}

	/**
	 * Improves one single brood using Hill Climbing.
	 *
	 * @param brood
	 * @return a new, improved brood
	 */
	public Solution improveWithHillClimbing(Solution brood) {
		return improve(brood, "Hill Climbing");
	}

	/**
	 * Improves one single brood using Tabu Search.
	 *
	 * @param brood
	 * @return a new, improved brood
	 */
	public Solution improveWithTabuSearch(Solution brood) {
		return improve(brood, "Tabu Search");
	}

	/**
	 * Improves one single brood using Simulated Annealing.
	 *
	 * @param brood
	 * @return a new, improved brood
	 */
	public Solution improveWithSimulatedAnnealing(Solution brood) {
		return improve(brood, "Simulated Annealing");
	}

	/**
	 * Modifies the brood by performing mutation operations, i.e. changing components (dishes), in
	 * order to improve its fitness value.
	 *
	 * @param brood
	 * @param algorithm
	 */
	private Solution improve(Solution brood, BroodImproverAlgorithm algorithm) {
		return algorithm.improve(brood);
	}

	private Solution improve(Solution brood, String algorithmName){
		int i = 0;
		for(String algoName : SEARCH_ALGOS_NAMES){
			if(algoName.equals(algorithmName))
				return improve(brood, SEARCH_ALGOS.get(i));
			++i;
		}

		log.log(Level.SEVERE, "Error finding " + algorithmName +"! Did you add it to the configuration?");
		return brood;
	}

	private class AlgorithmJob implements Runnable {

		List<Solution> bucket;

		public AlgorithmJob(List<Solution> bucket) {
			this.bucket = bucket;
		}

		@Override
		public void run() {
			int size = bucket.size();
			for (int i = size - 1; i >= 0; --i) {
				// Replace the solution on the current index with the improved one.
				bucket.set(i, improve(bucket.get(i)));
			}
		}
	}


}
