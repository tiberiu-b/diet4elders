package org.licenta.d4elders.algorithm.broodImprover;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.licenta.d4elders.algorithm.AnnealingScheduler;
import org.licenta.d4elders.model.SolutionOld;

public class BroodImproverHelper{

	private final static int defaultNumberOfThreads = 4;

	/** List of supported search algorithms. */
	protected static List<BroodImproverAlgorithm> SEARCH_ALGOS = new ArrayList<BroodImproverAlgorithm>();

	/** Adds a new item to the list of supported search algorithms. */
	public static void addSearchAlgorithm(String name, BroodImproverAlgorithm algo) {
		SEARCH_ALGOS.add(algo);
	}

	static {
		addSearchAlgorithm("Simulated Annealing", new SimulatedAnnealingBroodImprover(new AnnealingScheduler(1, 90, 0.1)));
		addSearchAlgorithm("Hill Climbing", new HillClimbingBroodImprover());
		addSearchAlgorithm("Tabu Search", new TabuSearchBroodImprover());
	}

	/**
	 * TODO: revise the type of broods. Collection might not be the best one because it is immedately transformed into an array.
	 * Improves all the solutions contained in broods using numberOfThreads threads.
	 * @param broods the initial set of broods
	 * @param numberOfThreads the number of threads used to improve the broods
	 * @return the set of improved broods
	 */
	public SortedSet<SolutionOld> improve(Collection<SolutionOld> broods, int numberOfThreads){
		// array of threads
		ArrayList<Thread> threadsList = new ArrayList<Thread>();

		// The broods collection is going to be partitioned into numberOfThreads buckets and must be transformed into an array first.
		ArrayList<SolutionOld> broodsList = new ArrayList<SolutionOld>();

		// TODO: decide whether to improve the entire or only a subset.
		// For now, we take the first 100 only.
		Iterator<SolutionOld> it = broods.iterator();
		for(int i = 0; i < 100 && it.hasNext(); ++i){
			broodsList.add(it.next());
		}

		// Compute the size of each bucket.
		int bucketSize = broodsList.size() / numberOfThreads;
		if(broodsList.size() % numberOfThreads != 0){
			bucketSize++;
		}

		List<List<SolutionOld>> partition = MyPartition.partition(broodsList, bucketSize);

		for(final List<SolutionOld> bucket : partition){
			Thread thread = new Thread(new AlgorithmJob(bucket));
			threadsList.add(thread);
			thread.start();
		}

		for(Thread t : threadsList){
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new TreeSet<SolutionOld>(broodsList);
	}

	/**
	 * Improves all the solutios contained in broods using the default number of threads.
	 * @param broods the initial set of broods
	 * @return the set of improved broods
	 */
	public SortedSet<SolutionOld> improve(Collection<SolutionOld> broods){
		return improve(broods, defaultNumberOfThreads);
	}

	/**
	 * Improves one single brood using a random heuristic.
	 * @param brood
	 * @return a new, improved brood
	 */
	public SolutionOld improve(SolutionOld brood){
		return improve(brood, SEARCH_ALGOS.get(new Random().nextInt(SEARCH_ALGOS.size())));
	}

	/**
	 * Modifies the brood by performing mutation operations, i.e. changing components (dishes),
	 * in order to improve its fitness value.
	 * @param brood
	 * @param algorithm
	 */
	private SolutionOld improve(SolutionOld brood, BroodImproverAlgorithm algorithm){
		return algorithm.improve(brood);
	}

	private class AlgorithmJob implements Runnable{

		List<SolutionOld> bucket;

		public AlgorithmJob(List<SolutionOld> bucket) {
			this.bucket = bucket;
		}

		@Override
		public void run() {
			int size = bucket.size();
			for(int i = size-1; i >= 0; --i){
				// Replace the solution on the current index with the improved one.
				bucket.set(i, improve(bucket.get(i)));
			}
		}
	}
}
