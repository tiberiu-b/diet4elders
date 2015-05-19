package org.licenta.d4elders.algorithm;

import org.licenta.d4elders.model.Solution;

/**
 * Parent class for main algorithms that we'd like to compare. The idea is to make sure that
 *  these algorithms contain the comparison criteria, like: duration and TODO others
 * @author cristiprg
 *
 */
public abstract class MainAlgorithm {
	protected int nrOfIterations;
	protected long duration;


	// Abstract methods
	public abstract Solution performAlgorithm();


	// Non abstract methods
	MainAlgorithm(){
		nrOfIterations = 0;
		duration = 0;
	}

	/**
	 * Returns information regarding the last run of the algorithm.
	 * @return an instance of RunInformation containing information regarding the last run of the algorithm
	 */
	public RunInformation getLastRunInformation(){
		return new RunInformation(nrOfIterations, duration);
	}


	/**
	 * This is the class that embeds the pieces of information useful for comparison.
	 * @author cristiprg
	 *
	 */
	public class RunInformation{
		/**
		 * The number of <b>main</b> iterations the algorithm did.
		 */
		public int nrOfItertions;

		/**
		 * The duration of the algorithm in milliseconds. The start timestamp is (at least should be) the first instruction
		 * the performAlgorithm method and the end timestamp the last.
		 */
		public long duration;

		public RunInformation(int nrOfIterations, long duration){
			this.nrOfItertions = nrOfIterations;
			this.duration = duration;
		}
	}
}
