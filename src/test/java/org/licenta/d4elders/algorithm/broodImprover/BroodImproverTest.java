package org.licenta.d4elders.algorithm.broodImprover;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.licenta.d4elders.algorithm.AnnealingScheduler;
import org.licenta.d4elders.main.InitialSolutionsGenerator;
import org.licenta.d4elders.model.SolutionOld;
import org.licenta.d4elders.model.user_profile.NutritionalRecommandationHelper;
import org.licenta.d4elders.model.user_profile.UserProfileStub;
/**
 * Tests different aspects of the brood improvement feature.
 * @author cristiprg
 */
@RunWith(Parameterized.class)
public class BroodImproverTest {

	private final static int NUMBER_OF_RUNS=10;

	@Before
	public void init(){
		new NutritionalRecommandationHelper(new UserProfileStub());
	}

	@Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[NUMBER_OF_RUNS][0]);
    }

    /**
     * Checks whether the solution resulted from the algorithm has a
     *	greater fitness value.
     * Runs NUMBER_OF_RUNS times.
     * This is great to discover/compute the annealing schedule.
     */
	@Test
	public void testImprove() {

		SolutionOld sol = InitialSolutionsGenerator.generateRandomSolutions(1).first();
		double fintess1 = sol.getFitness();

		sol = new SimulatedAnnealingBroodImprover(new AnnealingScheduler(1, 90, 0.1)).improve(sol);

		double fitness2 = sol.getFitness();

		//System.out.println("fintess1="+fintess1+" fitness2="+fitness2);
		assertTrue(fitness2 > fintess1);
	}

	/**
	 * Tests whether the average of the improved set is greater.
	 * Tests whether using 4 threads is faster than 1 thread.
	 */
	@Test
	public void testImproveCollection(){
		final int size = 1000;

		SortedSet<SolutionOld> solutions = InitialSolutionsGenerator.generateRandomSolutions(size);
		double fitnessAvg = fitnessAverage(solutions);

		long startTime1 = System.currentTimeMillis();
		solutions = new BroodImproverHelper().improve(solutions, 1);
		long finishTime1 = System.currentTimeMillis();
		double fitnessAvg2 = fitnessAverage(solutions);

		assertTrue(fitnessAvg2 > fitnessAvg);

		long startTime2 = System.currentTimeMillis();
		solutions = new BroodImproverHelper().improve(solutions, 4);
		long finishTime2 = System.currentTimeMillis();
		double fitnessAvg3 = fitnessAverage(solutions);

		assertTrue(fitnessAvg3 > fitnessAvg2);
		assertTrue(finishTime2 - startTime2 < finishTime1 - startTime1);
	}

	private double fitnessAverage(Collection<SolutionOld> col){
		double avg = 0.0;
		for(SolutionOld item : col){
			//System.out.println(item.getFitness() + " " + item.toString());
			avg += item.getFitness();
		}

		return avg /= col.size();
	}

}
