package d4elders.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import d4elders.algorithm.broodImprover.BroodImproverHelper;
import d4elders.algorithm.helper.AlgorithmConfigurationCuckoo;
import d4elders.algorithm.helper.AlgorithmConfigurationHBMO;
import d4elders.dal.helper.SolutionsGenerator;
import d4elders.model.Solution;

public class CuckooSearch extends MainAlgorithm {
	private static final Logger log = Logger.getLogger(CuckooSearch.class.getName());

	private SolutionsGenerator solGenerator;
	private AlgorithmConfigurationCuckoo algorithmConfiguration = null;

	public AlgorithmConfigurationCuckoo getAlgorithmConfiguration() {
		return algorithmConfiguration;
	}

	/**
	 * Applies the configuration in Solution and BroodImproverHelper classs too.
	 *
	 * @param algorithmConfiguration
	 */
	public void setAlgorithmConfiguration(AlgorithmConfigurationCuckoo algorithmConfiguration) {
		this.algorithmConfiguration = algorithmConfiguration;
	}

	public CuckooSearch(SolutionsGenerator solGenerator, AlgorithmConfigurationCuckoo config) {
		this.solGenerator = solGenerator;
		this.algorithmConfiguration = config;
	}

	@Override
	public Solution performAlgorithm() {

	}

	private Solution csVersion1() {
		long start = System.currentTimeMillis();
		// Adjustable parameters
		int nestSize = algorithmConfiguration.getNestSize();
		int maxIterations = algorithmConfiguration.getMaxIterations();
		double pa = algorithmConfiguration.getPa();

		double mean;

		// local parameters
		int t = 0;
		Random ran = new Random();
		Solution bestSolution;

		int[] nestSimilarity = new int[nestSize];
		// Generate initial solutions
		Solution[] nests = new Solution[nestSize];
		solGenerator.generateRandomSolutions(nestSize).toArray(nests);
		// Best solution is last, because it originates from an ordered structure
		bestSolution = nests[nestSize - 1];

		// create a number of cuckoos equal to the nest size
		Solution[] cuckoos = new Solution[nestSize];
		BroodImproverHelper broodImprover = new BroodImproverHelper();
		while (t++ < maxIterations) {
			// Compute mean fitness value of all nests for statistics
			mean = meanFitness(nests);
			System.out.print(mean + " ");

			// generate new cuckoos
			for (int i = 0; i < cuckoos.length; i++) {
				// new cuckoo makes eggs similar to nest eggs
				// cuckoos[i] = nests[i].randomGenotypesMutation();

				cuckoos[i] = broodImprover.improveWithHillClimbing(nests[i]);

				// cuckoos[i] = broodImprover.improveWithTabuSearch(nests[i]);
				// varianta 2: crossover cu best - mutatie ghidata
				// cuckoos[i] = nests[i].combineGenotypes(bestSolution);

				// varianta 3 : sa creeam cuckoos prin imbunatatirea nests-urilor cu
				// euristicile de la HBMO broodImprover.improve(nests).toArray(cuckoos);

				// Compute similarity coefficient between cuckoo and nest
				nestSimilarity[i] = cuckoos[i].getSolutionSimilarityCoefficientInteger(nests[i]);
			}

			// replace all the nests that have a lower fitness value
			for (int i = 0; i < nests.length; i++) {
				if (nests[i].getFitness() < cuckoos[i].getFitness()) {
					nests[i] = cuckoos[i];
				}
			}
			mean = meanFitness(nests);
			System.out.println(mean);

			// handle the nests that have been discovered as intruded nests
			for (int i = 0; i < nests.length; i++) {

				if (nests[i].getFitness() < mean) {
					// percentage equal to pa of the worse nests are discovered
					if (ran.nextDouble() > pa) {
						// *****Varianta 1
						// if a nest is discovered to be intruded then the host will throw
						// a number of eggs equal to the number of intruder eggs, but not
						// necessarily the cuckoo's eggs
						/*
						 * for (int j = 0; j < Solution.nrOfPackages - nestSimilarity[i]; j++) {
						 * Solution tmp = nests[i].randomSingleGenotypeMutation(); if
						 * (nests[i].getFitness() < tmp.getFitness()) nests[i] = tmp; }
						 */
						// *****Varianta 2 -> sa imbunatatim cu o euristica
						// if a nest is discovered then the host will make new eggs
						// by using an improvement heuristic - hill climbing
						// nests[i] = broodImprover.improveWithHillClimbing(nests[i]);
						Solution temp = nests[i].combineGenotypes(bestSolution);
						if (temp.getFitness() > nests[i].getFitness())
							nests[i] = temp;
						// nests[i] = nests[i].randomGenotypesMutation();
						System.out.println();
					}
				}
			}
			// update new best solution
			for (Solution sol : nests)
				if (bestSolution.getFitness() < sol.getFitness())
					bestSolution = sol;

		}
		algDuration = System.currentTimeMillis() - start;
		return bestSolution;

	}

	private Solution csVersion2() {
		return null;
	}

	private Solution csVersion3() {
		return null;
	}

	@Override
	public ArrayList<String> getCustomHeadersForExportedData() {
		// TODO Auto-generated method stub
		return null;
	}

	private double meanFitness(Solution[] sols) {
		double sum = 0;
		for (Solution sol : sols) {
			sum += sol.getFitness();
		}
		return sum / sols.length;
	}
}