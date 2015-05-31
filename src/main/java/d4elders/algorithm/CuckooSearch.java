package d4elders.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import d4elders.algorithm.broodImprover.BroodImproverHelper;
import d4elders.dal.helper.SolutionsGenerator;
import d4elders.model.Solution;

public class CuckooSearch extends MainAlgorithm {
	private static final Logger log = Logger.getLogger(CuckooSearch.class
			.getName());

	private SolutionsGenerator solGenerator;

	public CuckooSearch(SolutionsGenerator solGenerator) {
		this.solGenerator = solGenerator;
	}

	@Override
	public Solution performAlgorithm() {
		long start = System.currentTimeMillis();
		// Adjustable parameters
		int nestSize = 5;
		int maxGenerations = 500;
		double pa = 0.25;


		double mean;

		// local parameters
		int t = 0;
		Random ran = new Random();
		Solution bestSolution;
		Solution[] nests = new Solution[nestSize];
		int[] nestSimilarity = new int[nestSize];
		// Generate initial solutions
		solGenerator.generateRandomSolutions(nestSize).toArray(nests);
		bestSolution = nests[nestSize - 1];
		mean = meanFitness(nests);

		Solution[] cuckoos = new Solution[nestSize];
		BroodImproverHelper broodImprover = new BroodImproverHelper();
		while (t++ < maxGenerations) {
			mean = meanFitness(nests);
			System.out.print(mean + " ");
			// generate new cuckoos


			for (int i = 0; i < cuckoos.length; i++) {
				cuckoos[i] = nests[i].randomGenotypesMutation();

				// varianta 2: crossover cu best - mutatie ghidata
				//cuckoos[i] = nests[i].combineGenotypes(bestSolution);

				nestSimilarity[i] = cuckoos[i]
						.getSolutionSimilarityCoefficientInteger(nests[i]);
			}

			// varianta 3 : sa creeam cuckoos prin imbunatatirea nests-urilor cu euristicile de la HBMO
			//broodImprover.improve(nests).toArray(cuckoos);

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

				if (ran.nextDouble() > pa) {
					for (int j = 0; j < Solution.nrOfPackages - nestSimilarity[i]; j++) {
						Solution tmp = nests[i].randomSingleGenotypeMutation();

						// pasarea e naspa rau cu puii: numa daca is mai faini astia noi ii arunca pe aia vechi.
						// nefidel Cuckoo search acest if
						if(nests[i].getFitness() < tmp.getFitness())
							nests[i] = tmp;
					}

					// varianta 2: sa imbunatatim cu o euristica
					//nests[i] = broodImprover.improve(nests[i]);
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

	private double meanFitness(Solution[] sols){
		double sum = 0;
		for(Solution sol : sols){
			sum += sol.getFitness();
		}
		return sum / sols.length;
	}
}