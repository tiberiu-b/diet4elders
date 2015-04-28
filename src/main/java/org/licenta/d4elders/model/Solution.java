package org.licenta.d4elders.model;

import org.licenta.d4elders.helper.HbmoNutrientNotFoundException;
import org.licenta.d4elders.helper.NutrientsIdealValuesHelper;
import org.licenta.d4elders.model.dish.Desert;
import org.licenta.d4elders.model.dish.MainCourse;
import org.licenta.d4elders.model.dish.StarterDish;
import org.licenta.d4elders.model.meal.Breakfast;
import org.licenta.d4elders.model.meal.DayMeal;
import org.licenta.d4elders.model.meal.Dinner;
import org.licenta.d4elders.model.meal.Lunch;
import org.licenta.d4elders.model.meal.Snack;
import org.licenta.d4elders.model.user_profile.NutritionalRecommandationHelper;
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
public class Solution implements Comparable<Solution> {
	private static final Logger log = Logger.getLogger(Solution.class.getName());

	private DayMeal dayMeal;

	private int speed = 100;
	private int energy = 100;
	private double fitness = 0;

	public static double speedReductionFactor = 0.9;
	public static double energyReductionAmount = 5;
	public static double probabilityToMateDroneThreshold = 0.05;

	public DayMeal getDayMeal() {
		return dayMeal;
	}

	public Solution() {
		this(null);
	}

	public Solution(DayMeal dayMeal) {
		this.dayMeal = dayMeal;
		this.dayMeal.computeNutrientValues();
		computeFitness();
	}

	@Override
	public int compareTo(@NotNull Solution other) {
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

	public double getFitness() {
		// System.out.println("fitness = " + fitness);
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
		// return (Math.exp(-x * x) + Math.exp(-y * y)) / 2.0;
		// return 0.5 * (getFitnessLevel1() + getFitnessLevel2());
		// fitness = 0.5 * (getFitnessLevel1() + getFitnessLevel2());
		fitness = getFitnessLevel1();
	}

	private float getFitnessLevel1() {
		float sum = 0;
		int sum_weights = 0;
		Double[] interval;
		// weigth
		double proteinsErr, carbohydratesErr, energyErr, lipidsErr, calciumErr, ironErr, sodiumErr, vitAErr, vitBErr, vitCErr, vitDErr;
		proteinsErr = errorMarginInterval(dayMeal.getProteins(),
				NutritionalRecommandationHelper.nutrR.getProteinsLowerLimit(),
				NutritionalRecommandationHelper.nutrR.getProteinsUpperLimit());
		lipidsErr = errorMarginInterval(dayMeal.getLipids(),
				NutritionalRecommandationHelper.nutrR.getLipidsLowerLimit(),
				NutritionalRecommandationHelper.nutrR.getLipidsUpperLimit());
		energyErr = errorMarginInterval(dayMeal.getLipids(),
				NutritionalRecommandationHelper.nutrR.getLipidsLowerLimit(),
				NutritionalRecommandationHelper.nutrR.getLipidsUpperLimit());
		carbohydratesErr = errorMarginInterval(dayMeal.getCarbohydrates(),
				NutritionalRecommandationHelper.nutrR.getCarbohydratesLowerLimit(),
				NutritionalRecommandationHelper.nutrR.getCarbohydratesUpperLimit());
		ironErr = errorMargin(dayMeal.getIron(), NutritionalRecommandationHelper.nutrR.getIronFixed());
		calciumErr = errorMargin(dayMeal.getCalcium(), NutritionalRecommandationHelper.nutrR.getCalciumFixed());
		sodiumErr = errorMargin(dayMeal.getSodium(), NutritionalRecommandationHelper.nutrR.getSodiumFixed());
		vitAErr = errorMargin(dayMeal.getVitA(), NutritionalRecommandationHelper.nutrR.getVitAFixed());
		vitBErr = errorMargin(dayMeal.getVitB(), NutritionalRecommandationHelper.nutrR.getVitBFixed());
		vitCErr = errorMargin(dayMeal.getVitC(), NutritionalRecommandationHelper.nutrR.getVitCFixed());
		vitDErr = errorMargin(dayMeal.getVitD(), NutritionalRecommandationHelper.nutrR.getVitDFixed());

		sum += proteinsErr * NutritionalRecommandationHelper.nutrR.getProteinsWeight() + lipidsErr
				* NutritionalRecommandationHelper.nutrR.getLipidsWeight() + carbohydratesErr
				* NutritionalRecommandationHelper.nutrR.getCarbohydratesWeight() + energy
				* NutritionalRecommandationHelper.nutrR.getEnergyWeight() + ironErr
				* NutritionalRecommandationHelper.nutrR.getIronWeight() + calciumErr
				* NutritionalRecommandationHelper.nutrR.getCalciumWeight() + sodiumErr
				* NutritionalRecommandationHelper.nutrR.getSodiumWeight() + vitAErr
				* NutritionalRecommandationHelper.nutrR.getVitAWeight() + vitBErr
				* NutritionalRecommandationHelper.nutrR.getVitBWeight() + vitCErr
				* NutritionalRecommandationHelper.nutrR.getVitCWeight() + vitDErr
				* NutritionalRecommandationHelper.nutrR.getVitDWeight();
		sum_weights += NutritionalRecommandationHelper.nutrR.getProteinsWeight()
				+ NutritionalRecommandationHelper.nutrR.getLipidsWeight()
				+ NutritionalRecommandationHelper.nutrR.getCarbohydratesWeight()
				+ NutritionalRecommandationHelper.nutrR.getEnergyWeight()
				+ NutritionalRecommandationHelper.nutrR.getIronWeight()
				+ NutritionalRecommandationHelper.nutrR.getCalciumWeight()
				+ NutritionalRecommandationHelper.nutrR.getSodiumWeight()
				+ NutritionalRecommandationHelper.nutrR.getVitAWeight()
				+ NutritionalRecommandationHelper.nutrR.getVitBWeight()
				+ NutritionalRecommandationHelper.nutrR.getVitCWeight()
				+ NutritionalRecommandationHelper.nutrR.getVitDWeight();
		return sum / sum_weights;
	}

	// private double getFitnessLevel2() {
	// Set<String> dayMealIngredientsSet = dayMeal.getNutrientsValuesMap().keySet();
	// Set<String> preferenceSet = UserProfileHelper.getPreferenceSet();
	// Set<String> allIngredientsSet = new TreeSet<String>(dayMealIngredientsSet);
	// allIngredientsSet.addAll(preferenceSet);
	//
	// Set<String> likeSet = UserProfileHelper.getLikeList();
	// Set<String> disLikeSet = UserProfileHelper.getDisLikeList();
	//
	// final int weight = 1;
	// int sum_weights = 0;
	// double sum = 0;
	// int ideal = 0;
	// int real = 0;
	//
	// for (String ingredient : preferenceSet) {
	//
	// ideal = likeSet.contains(ingredient) ? 1 : 0; // 1 for likeSet, 0 for disLike
	// real = dayMealIngredientsSet.contains(ingredient) ? 1 : 0;
	//
	// sum += weight * errorMarginIngredients(real, ideal);
	// sum_weights += weight;
	//
	// }
	//
	// return sum / sum_weights;
	// }
	/**
	 * Computes the error margin for fitness level 2. It is based on the lookup table described in
	 * the documentation.
	 * 
	 * @param real
	 *            the
	 * @param ideal
	 * @return
	 */
	private double errorMarginIngredients(int real, int ideal) {
		// Look table
		if (real == 1)
			return ideal;

		if (ideal == 0)
			return 1;

		return .7;
	}

	private double errorMargin(Float x, Float lower_limit) {
		final Float MAX = new Float(1);

		lower_limit = (float) ((lower_limit != 0) ? lower_limit : 0.000001); // don't divide by 0
		x = (float) (x * MAX / lower_limit);
		lower_limit = MAX;
		// System.out.println("x = " + x + " ideal = " + ideal + "\n");
		return Math.exp(-(x - lower_limit) * (x - lower_limit));
	}

	private Float errorMarginInterval(Float x, Float lower_limit, Float upper_limit) {
		if (lower_limit <= x && x <= upper_limit)
			return (float) 1;
		else if (x < lower_limit)
			return (float) errorMargin(x, lower_limit);
		else
			return (float) errorMargin(x, upper_limit);
	}

	/**
	 * Computes to probability that this solution (which is to be the queen) will pick the drone in
	 * the mating dance. NEEDS REVISION!!!!! VALUES ARE NOT PROPERLY CORRELATED WITH THRESHOLD
	 *
	 * @param drone
	 * @return a double in interval [0, 1] representing the probability.
	 */
	public double probabilityToMateDrone(Solution drone) {
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
	public int numberOfBroodsWithDrone(Solution drone) {
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

		// System.out.println("Energy = " + energy + " Speed = " + speed);
	}

	/**
	 * Provides a set of solutions derived from this queen. Runs the genotypes combination algorithm
	 * for each new brood.
	 *
	 * @param drone
	 * @return A sorted set (TreeSet) of solutions.
	 */
	public SortedSet<Solution> createBroods(Solution drone) {
		SortedSet<Solution> broods = new TreeSet<Solution>();
		int nr_broods = numberOfBroodsWithDrone(drone);
		// System.out.println("Creating " + nr_broods + " broods.");
		for (int i = 0; i < nr_broods; i++) {
			Solution brood = combineGenotypes(drone);
			broods.add(brood);
		}

		return broods;
	}

	/**
	 * Runs the genotypes combination algorithm for this queen and the drone. Combination of
	 * genotypes is done by doing a crossover between the queens genomes and the drone's genomes
	 *
	 * @param drone
	 * @return A new solution (brood).
	 */
	public Solution combineGenotypes(Solution drone) {
		Random r = new Random();

		StarterDish starterDish1 = null, starterDish2 = null;
		MainCourse mainCourse1 = null, mainCourse2 = null;
		Desert desert1 = null, desert2 = null;

		// combine breakfast
		mainCourse1 = this.dayMeal.getBreakfast().getMainCourse();
		mainCourse2 = drone.dayMeal.getBreakfast().getMainCourse();

		desert1 = this.dayMeal.getBreakfast().getDesert();
		desert2 = drone.dayMeal.getBreakfast().getDesert();

		Breakfast breakfast = new Breakfast(r.nextBoolean() ? mainCourse1 : mainCourse2, r.nextBoolean() ? desert1
				: desert2);

		// combine lunch
		starterDish1 = this.dayMeal.getLunch().getStarterDish();
		starterDish2 = drone.dayMeal.getLunch().getStarterDish();

		mainCourse1 = this.dayMeal.getLunch().getMainCourse();
		mainCourse2 = drone.dayMeal.getLunch().getMainCourse();

		desert1 = this.dayMeal.getLunch().getDesert();
		desert2 = drone.dayMeal.getLunch().getDesert();

		Lunch lunch = new Lunch(r.nextBoolean() ? starterDish1 : starterDish2, r.nextBoolean() ? mainCourse1
				: mainCourse2, r.nextBoolean() ? desert1 : desert2);

		// combine dinner
		starterDish1 = this.dayMeal.getDinner().getStarterDish();
		starterDish2 = drone.dayMeal.getDinner().getStarterDish();

		mainCourse1 = this.dayMeal.getDinner().getMainCourse();
		mainCourse2 = drone.dayMeal.getDinner().getMainCourse();

		desert1 = this.dayMeal.getDinner().getDesert();
		desert2 = drone.dayMeal.getDinner().getDesert();

		Dinner dinner = new Dinner(r.nextBoolean() ? starterDish1 : starterDish2, r.nextBoolean() ? mainCourse1
				: mainCourse2, r.nextBoolean() ? desert1 : desert2);

		// combine snack1
		mainCourse1 = this.dayMeal.getSnack1().getMainCourse();
		mainCourse2 = drone.dayMeal.getSnack1().getMainCourse();
		Snack snack1 = new Snack(r.nextBoolean() ? mainCourse1 : mainCourse2);

		// combine snack2
		mainCourse1 = this.dayMeal.getSnack1().getMainCourse();
		mainCourse2 = drone.dayMeal.getSnack1().getMainCourse();
		Snack snack2 = new Snack(r.nextBoolean() ? mainCourse1 : mainCourse2);

		DayMeal brood = new DayMeal(breakfast, lunch, dinner, snack1, snack2);

		/*
		 * brood.setBreakfast(r.nextBoolean() ? // if random is true this.dayMeal.getBreakfast() :
		 * // take from this queen drone.dayMeal.getBreakfast()); // otherwise, take from drone.
		 * 
		 * brood.setLunch(r.nextBoolean() ? this.dayMeal.getLunch() : drone.dayMeal.getLunch());
		 * 
		 * brood.setDinner(r.nextBoolean() ? this.dayMeal.getDinner() : drone.dayMeal.getDinner());
		 * 
		 * brood.setSnack1(r.nextBoolean() ? this.dayMeal.getSnack1() : drone.dayMeal.getSnack1());
		 * 
		 * brood.setSnack2(r.nextBoolean() ? this.dayMeal.getSnack2() : drone.dayMeal.getSnack2());
		 */
		return new Solution(brood);
	}

	public boolean hasEnergy() {
		return energy > 0;
	}

	public boolean hasSpeed() {
		return speed > 0;
	}

	@Override
	public String toString() {
		// return dayMeal.toString() + "\n" + dayMeal.getNutrientsValuesMap().toString();
		return dayMeal.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Solution))
			return false;

		return this.dayMeal.equals(((Solution) other).getDayMeal());
	}

}