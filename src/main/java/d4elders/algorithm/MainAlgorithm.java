package d4elders.algorithm;

import java.util.ArrayList;

import d4elders.model.Solution;

/**
 * Parent class for main algorithms that we'd like to compare. The idea is to make sure that
 *  these algorithms contain the comparison criteria, like: duration and TODO others
 * @author cristiprg
 *
 */
public abstract class MainAlgorithm {
	protected int nrOfIterations;
	protected long algDuration;


	// Abstract methods
	public abstract Solution performAlgorithm();
	public abstract ArrayList<String> getCustomHeadersForExportedData();


	// Non abstract methods

	/**
	 * Adds the headers that are irrespective of the algorithm.
	 * @param headers
	 */
	protected void addDefaultHeadersForExportedData(ArrayList<String> headers){
		// level 1: nutrients
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

		// level 2: food items
		headers.add("Breakfast");
		headers.add("Lunch");
		headers.add("Dinner");
		headers.add("Snack1");
		headers.add("Snack2");

		// level 3: cost and delivery time
		headers.add("Cost");
		headers.add("Delivery time");
	}

	MainAlgorithm(){
		nrOfIterations = 0;
		algDuration = 0;
	}

	/**
	 * Returns information regarding the last run of the algorithm.
	 * @return an instance of RunInformation containing information regarding the last run of the algorithm
	 */
	public RunInformation getLastRunInformation(){
		return new RunInformation(nrOfIterations, algDuration);
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
