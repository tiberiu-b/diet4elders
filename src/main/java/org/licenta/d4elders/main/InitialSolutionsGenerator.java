package org.licenta.d4elders.main;

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

import org.licenta.d4elders.algorithm.broodImprover.BusinessLogicCacheFilteredOpt;
import org.licenta.d4elders.dal.*;
import org.licenta.d4elders.model.DailyMenu;
import org.licenta.d4elders.model.Solution;
import org.licenta.d4elders.model.food_package.FoodProviderPackage;
import org.licenta.d4elders.model.food_package.Menu;
import org.licenta.d4elders.model.user_profile.NutritionalRecommendation;

public class InitialSolutionsGenerator {

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
	public static TreeSet<Solution> generateRandomSolutions(int size) {
		TreeSet<Solution> solutions = new TreeSet<Solution>();
		// BusinessLogic bl = new BusinessLogic();
		// BusinessLogicCache bl = BusinessLogicCache.getInstance();
		BusinessLogicCacheFilteredOpt bl = BusinessLogicCacheFilteredOpt.getInstance();

		for (int i = 0; i < size; i++) {
			FoodProviderPackage breakfast = bl.generateSingleBreakfastPackages();
			FoodProviderPackage lunch = bl.generateSingleLunchPackages();
			FoodProviderPackage dinner = bl.generateSingleDinnerPackages();
			FoodProviderPackage snack1 = bl.generateSingleSnackPackages();
			FoodProviderPackage snack2 = null;
			int maxCount = 100;
			while (maxCount > 0) {
				maxCount--;
				snack2 = bl.generateSingleSnackPackages();
				if (snack1.getMenu().getMainCourse().getMainDish().getDishId() != snack2.getMenu().getMainCourse()
						.getMainDish().getDishId())
					break;
			}
			if (snack2 == null)
				snack2 = snack1;

			DailyMenu dailyMenu = new DailyMenu(breakfast, lunch, dinner, snack1, snack2);
			Solution curSol = new Solution(dailyMenu);

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
	public static TreeSet<Solution> generateRandomSolutionsWithSimilarityCoeff(int size, double similarityCoefficientThreshold) {
		TreeSet<Solution> solutions = new TreeSet<Solution>();
		// BusinessLogic bl = new BusinessLogic();
		// BusinessLogicCache bl = BusinessLogicCache.getInstance();
		BusinessLogicCacheFilteredOpt bl = BusinessLogicCacheFilteredOpt.getInstance();

		for (int i = 0; i < size; i++) {
			FoodProviderPackage breakfast = bl.generateSingleBreakfastPackages();
			FoodProviderPackage lunch = bl.generateSingleLunchPackages();
			FoodProviderPackage dinner = bl.generateSingleDinnerPackages();
			FoodProviderPackage snack1 = bl.generateSingleSnackPackages();
			FoodProviderPackage snack2 = null;
			int maxCount = 100;
			while (maxCount > 0) {
				maxCount--;
				snack2 = bl.generateSingleSnackPackages();
				if (snack1.getMenu().getMainCourse().getMainDish().getDishId() != snack2.getMenu().getMainCourse()
						.getMainDish().getDishId())
					break;
			}
			if (snack2 == null)
				snack2 = snack1;

			DailyMenu dailyMenu = new DailyMenu(breakfast, lunch, dinner, snack1, snack2);
			Solution curSol = new Solution(dailyMenu);
			boolean solutionValid = true;
			for (Solution s : solutions) {
				if (s.getSolutionSimilarityCoefficient(curSol) > similarityCoefficientThreshold) {
					solutionValid = false;
					break;
				}
			}
			if (solutionValid == false) {
				i--;
				continue;
			}
			if (solutions.add(curSol) == false) {
				// TODO: scoate odiosenia asta
				System.err
						.println("Fatal error: NU CRED - Method \"add\" returned false - adica is doua exact la fel!");
				System.exit(1);
			}
		}
		return solutions;
	}

	public static SortedSet<Solution> generateRandomSolutions() {
		return generateRandomSolutions(NR_INITIAL_SOLUTIONS);
	}

}
