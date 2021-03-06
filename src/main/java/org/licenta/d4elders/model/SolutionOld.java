
package org.licenta.d4elders.model;
import org.licenta.d4elders.helper.HbmoNutrientNotFoundException;
import org.licenta.d4elders.helper.NutrientsIdealValuesHelper;

import org.licenta.d4elders.model.*;
import org.licenta.d4elders.model.meal.Breakfast;
import org.licenta.d4elders.model.meal.DayMeal;
import org.licenta.d4elders.model.meal.Dinner;
import org.licenta.d4elders.model.meal.Lunch;
import org.licenta.d4elders.model.meal.Snack;

import org.licenta.d4elders.model.outdated.FoodProperties;

import org.licenta.d4elders.model.outdated.DesertOld;
import org.licenta.d4elders.model.outdated.MainCourseOld;
import org.licenta.d4elders.model.outdated.StarterDishOld;

import org.licenta.d4elders.model.user_profile.UserProfileHelper;

import com.sun.istack.internal.NotNull;

import java.lang.Comparable;
import java.lang.Math;
import java.lang.Override;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The bee.
 */
public class SolutionOld implements Comparable<SolutionOld> {
    private static final Logger log = Logger.getLogger( SolutionOld.class.getName() );

    private DayMeal dayMeal;

    private int speed = 100;
    private int energy = 100;
    private double fitness = 0;

    static double speedReductionFactor = 0.9;
    static double energyReductionAmount = 5;
    public static double probabilityToMateDroneThreshold = 0.05;

    public DayMeal getDayMeal() {
        return dayMeal;
    }


    public SolutionOld()
    {
        this(null);
    }

    public SolutionOld(DayMeal dayMeal) {
        this.dayMeal = dayMeal;
        computeNutrientsValues();
        computeFitness();
    }

    public Map<String, Double> computeNutrientsValues()
    {
        return dayMeal.computeNutrientsValues();
    }

    @Override
    public int compareTo(@NotNull SolutionOld other) {
        if (this.equals(other)) {
            return 0;
        }

        double f1 = this.getFitness();
        double f2 = other.getFitness();
        // if this is > then other => fitness is better, thus return 1
        if (f1 < f2 + 0.00001)
            return -1;
        else
            return 1;
    }

    public double getFitness()
    {
        //System.out.println("fitness = " + fitness);
        return fitness;
    }

    /**
     * The final value of the fitness function will be the mean value between the gaussian applied
     * to x and y i.e.: e^(-x*x) + e^(-y*y) / 2. Resulting values between 0 and 1. The higher the
     * better Gauss function: http://mathworld.wolfram.com/GaussianFunction.html
     *
     * @return The fitness function of this solution.
     */
    private void computeFitness() {
        //return (Math.exp(-x * x) + Math.exp(-y * y)) / 2.0;
        //return 0.5 * (getFitnessLevel1() + getFitnessLevel2());
        fitness =  0.5* ( getFitnessLevel1() + getFitnessLevel2() );
    }

    private double getFitnessLevel1() {
        Map<String, Double> mealNutrientsValueMap = dayMeal.getNutrientsValuesMap();
        Double sum = new Double(0);
        double err;
        int weigth = 1, sum_weights = 0;
        Double[] interval;
        for (String key : mealNutrientsValueMap.keySet()) {
            try {
                weigth = NutrientsIdealValuesHelper.getWeightForNutrient(key);
                if(NutrientsIdealValuesHelper.nutrientHasInterval(key)) {
                    interval = NutrientsIdealValuesHelper.getIdealIntervalForNutrient(key);
                    err = errorMarginInterval(mealNutrientsValueMap.get(key), interval[0], interval[1]);
                }
                else{
                    err = errorMargin(mealNutrientsValueMap.get(key), NutrientsIdealValuesHelper.getIdealValueForNutrient(key));
                }

                sum += weigth * err;
                sum_weights += weigth;
            }
            catch (HbmoNutrientNotFoundException ex)
            {
                log.log(Level.SEVERE, ex.getMessage() + " Igoring ...");
            }

            //System.out.println(key + " " + " " + nutrientsValueMap.get(key) + " " + NutrientsIdealValuesHelper.getIdealValueForNutrient(key) + " " + err);
            //System.out.println(nutrientsValueMap.get(key) + " " + NutrientsIdealValuesHelper.getIdealValueForNutrient(key) + " " + err);
        }

        return sum / sum_weights;
    }

    private double getFitnessLevel2(){
        Set<String> dayMealIngredientsSet = dayMeal.getNutrientsValuesMap().keySet();
        Set<String> preferenceSet = UserProfileHelper.getPreferenceSet();
        Set<String> allIngredientsSet = new TreeSet<String>(dayMealIngredientsSet);
        allIngredientsSet.addAll(preferenceSet);

        Set<String> likeSet = UserProfileHelper.getLikeList();
        Set<String> disLikeSet = UserProfileHelper.getDisLikeList();

        final int weight = 1;
        int sum_weights = 0;
        double sum = 0;
        int ideal = 0;
        int real = 0;

        for(String ingredient : preferenceSet){

                ideal = likeSet.contains(ingredient) ? 1 : 0; // 1 for likeSet, 0 for disLike
                real = dayMealIngredientsSet.contains(ingredient) ? 1 : 0;

                sum += weight * errorMarginIngredients(real, ideal);
                sum_weights += weight;

        }

        return sum / sum_weights;
    }

    /**
     * Computes the error margin for fitness level 2. It is based on the lookup table described in the documentation.
     * @param real the
     * @param ideal
     * @return
     */
    private double errorMarginIngredients(int real, int ideal) {
        // Look table
        if(real == 1)
            return ideal;

        if(ideal == 0)
            return 1;

        return .7;
    }

    private double errorMargin(Double x, Double ideal)
    {
        final Double MAX = new Double(1);

        ideal = (ideal != 0) ? ideal : 0.000001; // don't divide by 0
        x = x * MAX / ideal;
        ideal = MAX;
        //System.out.println("x = " + x + " ideal = " + ideal + "\n");
        return Math.exp(- (x - ideal) * (x - ideal));
    }

    private double  errorMarginInterval(Double x, Double lower_limit, Double upper_limit)
    {
        if (lower_limit <= x && x <= upper_limit)
            return 1;
        else if (x < lower_limit)
            return errorMargin(x, lower_limit);
        else
            return errorMargin(x, upper_limit);

    }

    /**
     * Computes to probability that this solution (which is to be the queen) will pick the drone in
     * the mating dance. NEEDS REVISION!!!!! VALUES ARE NOT PROPERLY CORRELATED WITH THRESHOLD
     *
     * @param drone
     * @return a double in interval [0, 1] representing the probability.
     */
    public double probabilityToMateDrone(SolutionOld drone) {
        double thisFitness = this.getFitness();
        double droneFitness = drone.getFitness();
        double prob = Math.exp(-Math.abs(thisFitness - droneFitness) / this.speed);
        return prob;
    }

    /**
     * Computes the number of broods this queen makes with the drone, based on: The queen's energy
     * The drone's fitness value A random float number in interval [0, 1] This function should
     * return between 5 and 15 drones Energy is between 0-100 Drone's fitness is between 0 and 1
     * Random value will be between
     *
     * @param drone
     * @return A non-negative integer.
     */
    public int numberOfBroodsWithDrone(SolutionOld drone) {
        // return (int) (energy * drone.getFitness() * (new Random().nextInt()))
        // % 10;

        // values from 5 to 10
        int energyAndFitnessVar = (int) ((energy * drone.getFitness()) / 20 + 5);
        // random number from 0 to 5
        int randomNumber = new Random().nextInt(6);
        // result will be from 5 to 15
        int result = energyAndFitnessVar + randomNumber;
        return result;
    }

    /**
     * Supposing this is the queen, the following parameters have to updated at each iteration:
     * speed, energy
     */
    public void nextIteration() {
        speed *= speedReductionFactor;
        energy -= energyReductionAmount;

        //System.out.println("Energy = " + energy + " Speed = " + speed);
    }

    /**
     * Provides a set of solutions derived from this queen. Runs the genotypes combination algorithm
     * for each new brood.
     *
     * @param drone
     * @return A sorted set (TreeSet) of solutions.
     */
    public SortedSet<SolutionOld> createBroods(SolutionOld drone) {
        SortedSet<SolutionOld> broods = new TreeSet<SolutionOld>();
        int nr_broods = numberOfBroodsWithDrone(drone);
        // System.out.println("Creating " + nr_broods + " broods.");
        for (int i = 0; i < nr_broods; i++) {
            SolutionOld brood = combineGenotypes(drone);
            broods.add(brood);
        }

        return broods;
    }

    /**
     * Runs the genotypes combination algorithm for this queen and the drone.
     *
     * @param drone
     * @return A new solution (brood).
     */
    public SolutionOld combineGenotypes2(SolutionOld drone) {
        Random r = new Random();
        double x = r.nextDouble();
        double y = r.nextDouble();
        //return new Solution(x, y); //TODO

        return new SolutionOld();
    }

    public Solution combineSingleGenotype(Solution drone, GeneType type){
    	DayMeal newDayMeal = null;
    	Breakfast newBreakfast = null;
    	Lunch newLunch = null;
    	Dinner newDinner = null;

    	Breakfast thisBreakfast = this.getDayMeal().getBreakfast();
    	Lunch thisLunch = this.getDayMeal().getLunch();
    	Dinner thisDinner = this.getDayMeal().getDinner();
    	Snack thisSnack1 = this.getDayMeal().getSnack1();
    	Snack thisSnack2 = this.getDayMeal().getSnack2();

    	Breakfast droneBreakfast = drone.getDayMeal().getBreakfast();
    	Lunch droneLunch = drone.getDayMeal().getLunch();
    	Dinner droneDinner = drone.getDayMeal().getDinner();
    	Snack droneSnack1 = drone.getDayMeal().getSnack1();
    	Snack droneSnack2 = drone.getDayMeal().getSnack2();

    	System.out.println ("type = " + type);

    	switch(type){
    	case Breakfast:
    		newDayMeal = new DayMeal(thisBreakfast, droneLunch, droneDinner, droneSnack1, droneSnack2);
    		break;
		case Lunch:
			newDayMeal = new DayMeal(droneBreakfast, thisLunch, droneDinner, droneSnack1, droneSnack2);
			break;
		case Dinner:
			newDayMeal = new DayMeal(droneBreakfast, droneLunch, thisDinner, droneSnack1, droneSnack2);
			break;
		case Snack1:
			newDayMeal = new DayMeal(droneBreakfast, droneLunch, droneDinner, thisSnack1, droneSnack2);
			break;
		case Snack2:
			newDayMeal = new DayMeal(droneBreakfast, droneLunch, droneDinner, droneSnack1, thisSnack2);
			break;
		case BreakfastMainCourse:
			newBreakfast = new Breakfast(thisBreakfast.getMainCourse(), droneBreakfast.getDesert());
			newDayMeal = new DayMeal(newBreakfast, droneLunch, droneDinner, droneSnack1, droneSnack2);
			break;
		case BreakfastDesert:
			newBreakfast = new Breakfast(droneBreakfast.getMainCourse(), thisBreakfast.getDesert());
			newDayMeal = new DayMeal(newBreakfast, droneLunch, droneDinner, droneSnack1, droneSnack2);
			break;
		case DinnerStarter:
			newDinner = new Dinner(thisDinner.getStarterDish(), droneDinner.getMainCourse(), droneDinner.getDesert());
			newDayMeal = new DayMeal(droneBreakfast, droneLunch, newDinner, droneSnack1, droneSnack2);
			break;
		case DinnerMainCourse:
			newDinner = new Dinner(droneDinner.getStarterDish(), thisDinner.getMainCourse(), droneDinner.getDesert());
			newDayMeal = new DayMeal(droneBreakfast, droneLunch, newDinner, droneSnack1, droneSnack2);
			break;
		case DinnerDesert:
			newDinner = new Dinner(droneDinner.getStarterDish(), droneDinner.getMainCourse(), thisDinner.getDesert());
			newDayMeal = new DayMeal(droneBreakfast, droneLunch, newDinner, droneSnack1, droneSnack2);
			break;
		case LunchStarter:
			newLunch = new Lunch(thisLunch.getStarterDish(), droneLunch.getMainCourse(), droneLunch.getDesert());
			newDayMeal = new DayMeal(droneBreakfast, newLunch, droneDinner, droneSnack1, droneSnack2);
			break;
		case LunchMainCourse:
			newLunch = new Lunch(droneLunch.getStarterDish(), thisLunch.getMainCourse(), droneLunch.getDesert());
			newDayMeal = new DayMeal(droneBreakfast, newLunch, droneDinner, droneSnack1, droneSnack2);
			break;
		case LunchDesert:
			newLunch = new Lunch(droneLunch.getStarterDish(), droneLunch.getMainCourse(), thisLunch.getDesert());
			newDayMeal = new DayMeal(droneBreakfast, newLunch, droneDinner, droneSnack1, droneSnack2);
			break;
		default:
			break;

	//	default:
			//break;

    	}

    	return new Solution(newDayMeal);
    }

    /**
     * Runs the genotypes combination algorithm for this queen and the drone. Combination of
     * genotypes is done by doing a crossover between the queens genomes and the drone's genomes
     *
     * @param drone
     * @return A new solution (brood).
     */
    public SolutionOld combineGenotypes(SolutionOld drone) {
        Random r = new Random();

        StarterDishOld starterDish1 = null, starterDish2 = null;
        MainCourseOld mainCourse1 = null, mainCourse2 = null;
        DesertOld desert1 = null, desert2 = null;

        // combine breakfast
        mainCourse1 = this.dayMeal.getBreakfast().getMainCourse();
        mainCourse2 = drone.dayMeal.getBreakfast().getMainCourse();

        desert1 = this.dayMeal.getBreakfast().getDesert();
        desert2 = drone.dayMeal.getBreakfast().getDesert();

        Breakfast breakfast = new Breakfast(
                r.nextBoolean() ? mainCourse1 : mainCourse2,
                r.nextBoolean() ? desert1 : desert2
        );

        // combine lunch
        starterDish1 = this.dayMeal.getLunch().getStarterDish();
        starterDish2 = drone.dayMeal.getLunch().getStarterDish();

        mainCourse1 = this.dayMeal.getLunch().getMainCourse();
        mainCourse2 = drone.dayMeal.getLunch().getMainCourse();

        desert1 = this.dayMeal.getLunch().getDesert();
        desert2 = drone.dayMeal.getLunch().getDesert();

        Lunch lunch = new Lunch(
                r.nextBoolean() ? starterDish1 : starterDish2,
                r.nextBoolean() ? mainCourse1 : mainCourse2,
                r.nextBoolean() ? desert1 : desert2
        );

        // combine dinner
        starterDish1 = this.dayMeal.getDinner().getStarterDish();
        starterDish2 = drone.dayMeal.getDinner().getStarterDish();

        mainCourse1 = this.dayMeal.getDinner().getMainCourse();
        mainCourse2 = drone.dayMeal.getDinner().getMainCourse();

        desert1 = this.dayMeal.getDinner().getDesert();
        desert2 = drone.dayMeal.getDinner().getDesert();

        Dinner dinner = new Dinner(
                r.nextBoolean() ? starterDish1 : starterDish2,
                r.nextBoolean() ? mainCourse1 : mainCourse2,
                r.nextBoolean() ? desert1 : desert2
        );

        // combine snack1
        mainCourse1 = this.dayMeal.getSnack1().getMainCourse();
        mainCourse2 = drone.dayMeal.getSnack1().getMainCourse();
        Snack snack1 = new Snack(
                r.nextBoolean() ? mainCourse1 : mainCourse2
        );

        // combine snack2
        mainCourse1 = this.dayMeal.getSnack1().getMainCourse();
        mainCourse2 = drone.dayMeal.getSnack1().getMainCourse();
        Snack snack2 = new Snack(
                r.nextBoolean() ? mainCourse1 : mainCourse2
        );

        DayMeal brood = new DayMeal(breakfast, lunch, dinner, snack1, snack2);


/*
        brood.setBreakfast(r.nextBoolean() ? // if random is true
                this.dayMeal.getBreakfast() : // take from this queen
                drone.dayMeal.getBreakfast()); // otherwise, take from drone.

        brood.setLunch(r.nextBoolean() ?
                this.dayMeal.getLunch() :
                drone.dayMeal.getLunch());

        brood.setDinner(r.nextBoolean() ?
                this.dayMeal.getDinner() :
                drone.dayMeal.getDinner());

        brood.setSnack1(r.nextBoolean() ?
                this.dayMeal.getSnack1() :
                drone.dayMeal.getSnack1());

        brood.setSnack2(r.nextBoolean() ?
                this.dayMeal.getSnack2() :
                drone.dayMeal.getSnack2());
*/
        return new SolutionOld(brood);
    }

    /**
     * Returns a new instance of Solution which has exactly the same components as
     * this exept one component randomly selected which is replaced with a random
     * value.
     * @return a new Solution
     */
    public Solution randomMutation(){
    	Random r = new Random();
    	Breakfast breakfast = dayMeal.getBreakfast();
    	Lunch lunch = dayMeal.getLunch();
    	Dinner dinner = dayMeal.getDinner();
    	Snack snack1 = dayMeal.getSnack1();
    	Snack snack2 = dayMeal.getSnack2();

    	switch(r.nextInt(5)){
    	case 0:
    		// Replace Breakfast
    		Breakfast b = new Breakfast(
    				new MainCourse(FoodFactory.getRandomFoodProperties()),
    				new Desert(FoodFactory.getRandomFoodProperties()));

    		return new Solution(new DayMeal(b, lunch, dinner, snack1, snack2));

    	case 1:
    		// Replace Lunch
    		Lunch l = new Lunch(
    				new StarterDish(FoodFactory.getRandomFoodProperties()),
    				new MainCourse(FoodFactory.getRandomFoodProperties()),
    				new Desert(FoodFactory.getRandomFoodProperties()));

    		return new Solution(new DayMeal(breakfast, l, dinner, snack1, snack2));

    	case 2:
    		// Replace dinner
    		Dinner d = new Dinner(
    				new StarterDish(FoodFactory.getRandomFoodProperties()),
    				new MainCourse(FoodFactory.getRandomFoodProperties()),
    				new Desert(FoodFactory.getRandomFoodProperties()));

    		return new Solution(new DayMeal(breakfast, lunch, d, snack1, snack2));
    	case 3:
    	case 4:
    		// Replace snacks
    		Snack s = new Snack(new MainCourse(FoodFactory.getRandomFoodProperties()));
    		if(r.nextBoolean())
    			return new Solution(new DayMeal(breakfast, lunch, dinner, s, snack2));
    		else
    			return new Solution(new DayMeal(breakfast, lunch, dinner, snack1, s));
    	default:
    		return null;
    	}
    }

    public boolean hasEnergy() {
        return energy > 0;
    }

    public boolean hasSpeed()
    {
        return speed > 0;
    }

    @Override
    public String toString() {
        return dayMeal.toString() + "\n" + dayMeal.getNutrientsValuesMap().toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof SolutionOld))
            return false;

        return this.dayMeal.equals(((SolutionOld) other).getDayMeal());
    }

    /**
     * TODO revise: GeneType may be at the following 2 levels: 1) dish - starter, main ... or 2) meal - breakfast, lunch ...
     */
    public enum GeneType{
 /*   	BreakfastMainCourse, BreakfastDesert,
    	LunchStarter, LunchMainCourse, LunchDesert,
    	DinnerStarter, DinnerMainCourse, DinnerDesert,
    	Snack1, Snack2;
    	*/

    	Breakfast,BreakfastMainCourse, BreakfastDesert,
    	Lunch,LunchStarter, LunchMainCourse, LunchDesert,
    	Dinner,DinnerStarter, DinnerMainCourse, DinnerDesert,
    	Snack1,
    	Snack2;


/*
    	Breakfast,
    	Lunch,
    	Dinner,
    	Snack1,
    	Snack2;
*/
    	public static GeneType getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

}