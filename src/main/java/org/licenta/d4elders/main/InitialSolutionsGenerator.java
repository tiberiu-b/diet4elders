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

import org.licenta.d4elders.dal.*;
import org.licenta.d4elders.model.Solution;
import org.licenta.d4elders.model.meal.*;
import org.licenta.d4elders.model.user_profile.NutritionalRecommendation;

public class InitialSolutionsGenerator {

	public static final int NR_INITIAL_SOLUTIONS = 200;
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
	public static SortedSet<Solution> generateRandomSolutions(int size) {
		int numberOfSolutions = 1000;
		SortedSet<Solution> solutions = new TreeSet<Solution>();
		BusinessLogic bl = new BusinessLogic();
		ArrayList<Breakfast> breakfastList = bl.generateBreakfastMeals(numberOfSolutions);
		ArrayList<Lunch> lunchList = bl.generateLunchMeals(numberOfSolutions);
		ArrayList<Dinner> dinnerList = bl.generateDinnerMeals(numberOfSolutions);
		ArrayList<Snack> snackList = bl.generateSnackMeals(numberOfSolutions * 2);

		for (int i = 0; i < size; i++) {
			Random generator = new Random();
			int randomNumber = generator.nextInt(numberOfSolutions);
			Breakfast breakfast = breakfastList.get(randomNumber);

			randomNumber = generator.nextInt(numberOfSolutions);
			Lunch lunch = lunchList.get(randomNumber);

			randomNumber = generator.nextInt(numberOfSolutions);
			Dinner dinner = dinnerList.get(randomNumber);

			randomNumber = generator.nextInt(numberOfSolutions);
			Snack snack1 = snackList.get(randomNumber);

			randomNumber = generator.nextInt(numberOfSolutions) + numberOfSolutions;
			Snack snack2 = snackList.get(randomNumber);

			DayMeal dayMeal = new DayMeal(breakfast, lunch, dinner, snack1, snack2);

			if (solutions.add(new Solution(dayMeal)) == false) {
				// TODO: scoate odiosenia asta
				System.err
						.println("Fatal error: NU CRED - Method \"add\" returned false - adica is doua exact la fel!");
				System.exit(1);
			}
		}


        return solutions;
    }

    public static SortedSet<Solution> generateRandomSolutions(){
    	return generateRandomSolutions(NR_INITIAL_SOLUTIONS);
    }

}

