package org.licenta.d4elders.algorithm.broodImprover;

import static org.junit.Assert.*;

import java.lang.annotation.Repeatable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.licenta.d4elders.algorithm.AnnealingScheduler;
import org.licenta.d4elders.dal.FoodFactory;
import org.licenta.d4elders.main.InitialSolutionsGenerator;
import org.licenta.d4elders.model.Solution;
import org.licenta.d4elders.model.dish.Desert;
import org.licenta.d4elders.model.dish.MainCourse;
import org.licenta.d4elders.model.dish.StarterDish;
import org.licenta.d4elders.model.meal.Breakfast;
import org.licenta.d4elders.model.meal.DayMeal;
import org.licenta.d4elders.model.meal.Dinner;
import org.licenta.d4elders.model.meal.Lunch;
import org.licenta.d4elders.model.meal.Snack;
/**
 * Tests different aspects of the brood improvement feature.
 * @author cristiprg
 */
@RunWith(Parameterized.class)
public class SimulatedAnnealingBroodImproverTest {

	private final static int NUMBER_OF_RUNS=10;

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

		Breakfast b = new Breakfast(
				new MainCourse(FoodFactory.getRandomFoodProperties()),
				new Desert(FoodFactory.getRandomFoodProperties()));



		Lunch l = new Lunch(
				new StarterDish(FoodFactory.getRandomFoodProperties()),
				new MainCourse(FoodFactory.getRandomFoodProperties()),
				new Desert(FoodFactory.getRandomFoodProperties()));



		Dinner d = new Dinner(
				new StarterDish(FoodFactory.getRandomFoodProperties()),
				new MainCourse(FoodFactory.getRandomFoodProperties()),
				new Desert(FoodFactory.getRandomFoodProperties()));


		Snack s1 = new Snack(new MainCourse(FoodFactory.getRandomFoodProperties()));
		Snack s2 = new Snack(new MainCourse(FoodFactory.getRandomFoodProperties()));

		Solution sol = new Solution(new DayMeal(b, l, d, s1, s2));
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

		SortedSet<Solution> solutions = InitialSolutionsGenerator.generateRandomSolutions(size);
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

	private double fitnessAverage(Collection<Solution> col){
		double avg = 0.0;
		for(Solution item : col){
			//System.out.println(item.getFitness() + " " + item.toString());
			avg += item.getFitness();
		}

		return avg /= col.size();
	}

}
