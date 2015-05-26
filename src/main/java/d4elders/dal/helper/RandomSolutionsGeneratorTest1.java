package d4elders.dal.helper;

/*
 import org.licenta.d4elders.dal.FoodFactory;
 import org.licenta.d4elders.model.*;
 import org.licenta.d4elders.model.dish.Desert;
 import org.licenta.d4elders.model.dish.MainCourse;
 import org.licenta.d4elders.model.dish.StarterDish;
 import org.licenta.d4elders.model.meal.Breakfast;
 import org.licenta.d4elders.model.meal.DayMeal;
 import org.licenta.d4elders.model.meal.Dinner;
 import org.licenta.d4elders.model.meal.Lunch;
 import org.licenta.d4elders.model.meal.Snack;

 */
import java.util.ArrayList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import d4elders.algorithm.broodImprover.BusinessLogicCacheFilteredOpt;
import d4elders.dal.*;
import d4elders.model.DailyMenu;
import d4elders.model.Solution;
import d4elders.model.SolutionTest;
import d4elders.model.food_package.FoodProviderPackage;
import d4elders.model.food_package.Menu;
import d4elders.model.user_profile.NutritionalRecommendation;

public class RandomSolutionsGeneratorTest1 extends SolutionsGenerator {

	public static final int NR_INITIAL_SOLUTIONS = 100;
	// public static final double MIN = 0.8;
	// public static final double MAX = 1.2;
	public static final double MIN = -100;
	public static final double MAX = 100;

	public static NutritionalRecommendation nutrRec;

	/**
	 * Aicia vine Honey Bee Colony in actiune. Deocamdata facem random
	 *
	 * @return A SortedSet (Tree) filled with random solutions.
	 */
	@Override
	public TreeSet<Solution> generateRandomSolutions(int size) {
		TreeSet<Solution> solutions = new TreeSet<Solution>();
		Random r = new Random();
		float fitnessValue;
		for (int i = 0; i < size; i++) {
			fitnessValue = r.nextFloat();
			Solution curSol = new SolutionTest(fitnessValue);
			if (solutions.add(curSol) == false) {
				// TODO: scoate odiosenia asta
				System.err
						.println("Fatal error: NU CRED - Method \"add\" returned false - adica is doua exact la fel!");
				System.exit(1);
			}
		}
		return solutions;
	}

	/**
	 * Aicia vine Honey Bee Colony in actiune. Deocamdata facem random
	 *
	 * @return A SortedSet (Tree) filled with random solutions.
	 */
	@Override
	public TreeSet<Solution> generateRandomSolutionsWithSimilarityCoeff(
			int size, double similarityCoefficientThreshold) {
		// for this testing generator we do not care about similarity because we
		// deal only with fitness values
		return this.generateRandomSolutions(size);
	}

	@Override
	public SortedSet<Solution> generateRandomSolutions() {
		return generateRandomSolutions(NR_INITIAL_SOLUTIONS);
	}

}
