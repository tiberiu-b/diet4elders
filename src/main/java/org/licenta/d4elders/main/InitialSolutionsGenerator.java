package org.licenta.d4elders.main;

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

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by cristiprg on 1/18/2015.
 */
public class InitialSolutionsGenerator {

    public static final int NR_INITIAL_SOLUTIONS = 10000;
    // public static final double MIN = 0.8;
    // public static final double MAX = 1.2;
    public static final double MIN = -100;
    public static final double MAX = 100;

    /**
     * Aicia vine Honey Bee Colony in actiune. Deocamdata facem random
     *
     * @return A SortedSet (Tree) filled with random solutions.
     */
    public static SortedSet<Solution> generateRandomSolutions() {
        SortedSet<Solution> solutions = new TreeSet<Solution>();
        FoodFactory foodFactory = new FoodFactory();

        for (int i = 0; i < NR_INITIAL_SOLUTIONS; i++)
        {
            Breakfast breakfast = new Breakfast(
                    new MainCourse(foodFactory.getRandomFoodProperties()),
                    new Desert(foodFactory.getRandomFoodProperties())
            );

            Lunch lunch = new Lunch(
                    new StarterDish(foodFactory.getRandomFoodProperties()),
                    new MainCourse(foodFactory.getRandomFoodProperties()),
                    new Desert(foodFactory.getRandomFoodProperties())
            );

            Dinner dinner = new Dinner(
                    new StarterDish(foodFactory.getRandomFoodProperties()),
                    new MainCourse(foodFactory.getRandomFoodProperties()),
                    new Desert(foodFactory.getRandomFoodProperties())
            );

            Snack snack1 = new Snack(
                    new MainCourse(foodFactory.getRandomFoodProperties())
            );

            Snack snack2 = new Snack(
                    new MainCourse(foodFactory.getRandomFoodProperties())
            );

            DayMeal dayMeal = new DayMeal(breakfast, lunch, dinner, snack1, snack2);

            if(solutions.add(new Solution(dayMeal)) == false){
                // TODO: scoate odiosenia asta
                System.err.println("Fatal error: NU CRED - Method \"add\" returned false - adica is doua exact la fel!");
                System.exit(1);
            }
        }

        return solutions;
    }
}