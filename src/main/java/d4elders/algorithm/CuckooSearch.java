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
import d4elders.algorithm.helper.CuckooAlgorithmVersion;
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
	 * Applies the configuration in Solution and BroodImproverHelper class too.
	 *
	 * @param algorithmConfiguration
	 */
	public void setAlgorithmConfiguration(AlgorithmConfigurationCuckoo algorithmConfiguration) {
		this.algorithmConfiguration = algorithmConfiguration;
		BroodImproverHelper.applyConfiguration(algorithmConfiguration);

	}

	public CuckooSearch(SolutionsGenerator solGenerator, AlgorithmConfigurationCuckoo config) {
		this.solGenerator = solGenerator;
		setAlgorithmConfiguration(config);
	}

	@Override
	public Solution performAlgorithm() {

		switch (algorithmConfiguration.getAlgorithmVersion()) {
		case Version1:
			return csVersion1();
		case Version2:
			return csVersion2();
		case Version3:
			return csVersion3();
		case Version4:
			return csVersion4();
		}
		return null;
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

		while (t++ < maxIterations) {
			// Compute mean fitness value of all nests for statistics
			mean = meanFitness(nests);
			System.out.print(mean + " ");

			// generate new cuckoos
			for (int i = 0; i < cuckoos.length; i++) {
				// new cuckoo makes eggs similar to nest egg
				// random strategy is applied
				// cuckoos[i] = nests[i].randomGenotypesMutation();
				cuckoos[i] = solGenerator.generateRandomSolutions(1).first();

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
				// choose worst nests, which have lower fitness than mean value
				if (nests[i].getFitness() < mean) {
					// percentage equal to pa of the worse nests are discovered
					if (ran.nextDouble() > pa) {
						// If a nest is discovered the host bird will abort nest and
						// build a new one => a new solution
						nests[i] = solGenerator.generateRandomSolutions(1).first();
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
		long start = System.currentTimeMillis();
		// Adjustable parameters
		int nestSize = algorithmConfiguration.getNestSize();
		int maxIterations = algorithmConfiguration.getMaxIterations();
		double pa = algorithmConfiguration.getPa();

		double mean;

		// local parameters
		// BroodImproverHelper broodImprover = new BroodImproverHelper();
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

		while (t++ < maxIterations) {
			// Compute mean fitness value of all nests for statistics
			mean = meanFitness(nests);
			System.out.print(mean + " ");

			// generate new cuckoos
			for (int i = 0; i < cuckoos.length; i++) {
				// new cuckoo makes eggs similar to nest egg
				// random mutation from the best solution technique is applied

				// crossover between nest and best nest
				// cuckoos[i] = nests[i].combineGenotypes(bestSolution);
				cuckoos[i] = nests[i].randomGenotypesMutation();
				// Compute similarity coefficient between cuckoo and nest
				nestSimilarity[i] = cuckoos[i].getSolutionSimilarityCoefficientInteger(nests[i]);
			}

			boolean noChange = true;
			// replace all the nests that have a lower fitness value
			for (int i = 0; i < nests.length; i++) {
				if (nests[i].getFitness() < cuckoos[i].getFitness()) {
					nests[i] = cuckoos[i];
					noChange = false;
				}
			}
			mean = meanFitness(nests);
			if (noChange)
				System.out.println(mean + " - no change");
			else
				System.out.println(mean + " ");
			// handle the nests that have been discovered as intruded nests
			for (int i = 0; i < nests.length; i++) {

				// choose only worst nests => those with fitness lower than mean fitness
				if (nests[i].getFitness() < mean) {
					// percentage equal to pa of the worse nests are discovered
					if (ran.nextDouble() > pa) {
						// if a nest is discovered then the host will abort old nest
						// new nest will be combination of nest genotypes with best solution geno
						// nests[i] = nests[i].combineGenotypes(bestSolution);
						nests[i] = solGenerator.generateRandomSolutions(1).first();
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

	private Solution csVersion3() {
		long start = System.currentTimeMillis();
		// Adjustable parameters
		int nestSize = algorithmConfiguration.getNestSize();
		int maxIterations = algorithmConfiguration.getMaxIterations();
		double pa = algorithmConfiguration.getPa();

		double mean;

		// local parameters
		BroodImproverHelper broodImprover = new BroodImproverHelper();
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

		while (t++ < maxIterations) {
			// Compute mean fitness value of all nests for statistics
			mean = meanFitness(nests);
			System.out.print(mean + " ");

			// generate new cuckoos
			for (int i = 0; i < cuckoos.length; i++) {
				// new cuckoo makes eggs similar to nest eggs by combining genomes with best
				cuckoos[i] = nests[i].combineGenotypes(bestSolution);

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

				// percentage equal to pa of the worse nests are discovered
				if (ran.nextDouble() > pa) {
					// if a nest is discovered then the host will make new eggs
					// by using an improvement heuristic - hill climbing
					nests[i] = broodImprover.improveWithHillClimbing(nests[i]);
					// Solution temp = nests[i].combineGenotypes(bestSolution);
					// if (temp.getFitness() > nests[i].getFitness())
					// nests[i] = temp;
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

	private Solution csVersion4() {
		long start = System.currentTimeMillis();
		// Adjustable parameters
		int nestSize = algorithmConfiguration.getNestSize();
		int maxIterations = algorithmConfiguration.getMaxIterations();
		double pa = algorithmConfiguration.getPa();

		double mean;

		// local parameters
		BroodImproverHelper broodImprover = new BroodImproverHelper();
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

		while (t++ < maxIterations) {
			// Compute mean fitness value of all nests for statistics
			mean = meanFitness(nests);
			System.out.print(mean + " ");

			// generate new cuckoos
			for (int i = 0; i < cuckoos.length; i++) {
				// new cuckoo makes eggs similar to nest eggs but fitness value
				// will be improved by use of hill climbing heuristic
				cuckoos[i] = broodImprover.improveWithHillClimbing(nests[i]);

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

				// percentage equal to pa of the worse nests are discovered
				if (ran.nextDouble() > pa) {
					// if a nest is discovered then the host will make new eggs
					// by using an improvement heuristic - hill climbing
					nests[i] = broodImprover.improveWithHillClimbing(nests[i]);
					// Solution temp = nests[i].combineGenotypes(bestSolution);
					// if (temp.getFitness() > nests[i].getFitness())
					// nests[i] = temp;
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
