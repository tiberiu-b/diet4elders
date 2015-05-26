package org.licenta.d4elders.model;

import org.licenta.d4elders.algorithm.broodImprover.BusinessLogicCacheFilteredOpt;
import org.licenta.d4elders.algorithm.helper.AlgorithmConfiguration;
import org.licenta.d4elders.dal.BusinessLogic;
import org.licenta.d4elders.dal.BusinessLogicCache;
import org.licenta.d4elders.dal.BusinessLogicCacheFiltered;
import org.licenta.d4elders.model.food_package.FoodProviderPackage;
import org.licenta.d4elders.model.user_profile.NutritionalRecommandationHelper;
import org.licenta.d4elders.model.user_profile.UserProfileHelper;

import com.sun.istack.internal.NotNull;

import java.lang.Comparable;
import java.lang.Math;
import java.lang.Override;
import java.util.*;
import java.util.logging.Logger;

/**
 * The bee.
 */
@SuppressWarnings("restriction")
public class Solution implements Comparable<Solution> {
	private static final Logger log = Logger
			.getLogger(Solution.class.getName());

	private DailyMenu dailyMenu;

	private int speed = initialSpeed;
	private int energy = initialEnergy;
	private double fitness = 0;

	private static int initialSpeed = 0;
	private static int initialEnergy = 0;
	public static double speedReductionFactor = 0;
	public static double energyReductionAmount = 0;
	public static double probabilityToMateDroneThreshold = 0;

	public static void applyConfiguration(
			AlgorithmConfiguration algorithmConfiguration) {
		initialSpeed = algorithmConfiguration.getInitialSpeed();
		initialEnergy = algorithmConfiguration.getInitialEnergy();
		speedReductionFactor = algorithmConfiguration.getSpeedReductionFactor();
		energyReductionAmount = algorithmConfiguration
				.getEnergyReductionAmount();
		probabilityToMateDroneThreshold = algorithmConfiguration
				.getProbabilityToMateDroneThreshold();
	}

	public Solution() {
		this(null);
	}

	public Solution(DailyMenu dailyMenu) {
		this.dailyMenu = dailyMenu;
		this.dailyMenu.computeNutrientValues();
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
	 * The final value of the fitness function will be the mean value between
	 * the gaussian applied to x and y i.e.: e^(-x*x) + e^(-y*y) / 2. Resulting
	 * values between 0 and 1. The higher the better Gauss function:
	 * http://mathworld.wolfram.com/GaussianFunction.html
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
		// weigth
		double proteinsErr, carbohydratesErr, energyErr, lipidsErr, calciumErr, ironErr, sodiumErr, vitAErr, vitBErr, vitCErr, vitDErr;
		proteinsErr = errorMarginInterval(dailyMenu.getProteins(),
				NutritionalRecommandationHelper.nutrR.getProteinsLowerLimit(),
				NutritionalRecommandationHelper.nutrR.getProteinsUpperLimit());
		lipidsErr = errorMarginInterval(dailyMenu.getLipids(),
				NutritionalRecommandationHelper.nutrR.getLipidsLowerLimit(),
				NutritionalRecommandationHelper.nutrR.getLipidsUpperLimit());
		energyErr = errorMarginInterval(dailyMenu.getLipids(),
				NutritionalRecommandationHelper.nutrR.getLipidsLowerLimit(),
				NutritionalRecommandationHelper.nutrR.getLipidsUpperLimit());
		carbohydratesErr = errorMarginInterval(dailyMenu.getCarbohydrates(),
				NutritionalRecommandationHelper.nutrR
						.getCarbohydratesLowerLimit(),
				NutritionalRecommandationHelper.nutrR
						.getCarbohydratesUpperLimit());
		ironErr = errorMargin(dailyMenu.getIron(),
				NutritionalRecommandationHelper.nutrR.getIronFixed());
		calciumErr = errorMargin(dailyMenu.getCalcium(),
				NutritionalRecommandationHelper.nutrR.getCalciumFixed());
		sodiumErr = errorMargin(dailyMenu.getSodium(),
				NutritionalRecommandationHelper.nutrR.getSodiumFixed());
		vitAErr = errorMargin(dailyMenu.getVitA(),
				NutritionalRecommandationHelper.nutrR.getVitAFixed());
		vitBErr = errorMargin(dailyMenu.getVitB(),
				NutritionalRecommandationHelper.nutrR.getVitBFixed());
		vitCErr = errorMargin(dailyMenu.getVitC(),
				NutritionalRecommandationHelper.nutrR.getVitCFixed());
		vitDErr = errorMargin(dailyMenu.getVitD(),
				NutritionalRecommandationHelper.nutrR.getVitDFixed());

		sum += proteinsErr
				* NutritionalRecommandationHelper.nutrR.getProteinsWeight()
				+ lipidsErr
				* NutritionalRecommandationHelper.nutrR.getLipidsWeight()
				+ carbohydratesErr
				* NutritionalRecommandationHelper.nutrR
						.getCarbohydratesWeight() + energyErr
				* NutritionalRecommandationHelper.nutrR.getEnergyWeight()
				+ ironErr
				* NutritionalRecommandationHelper.nutrR.getIronWeight()
				+ calciumErr
				* NutritionalRecommandationHelper.nutrR.getCalciumWeight()
				+ sodiumErr
				* NutritionalRecommandationHelper.nutrR.getSodiumWeight()
				+ vitAErr
				* NutritionalRecommandationHelper.nutrR.getVitAWeight()
				+ vitBErr
				* NutritionalRecommandationHelper.nutrR.getVitBWeight()
				+ vitCErr
				* NutritionalRecommandationHelper.nutrR.getVitCWeight()
				+ vitDErr
				* NutritionalRecommandationHelper.nutrR.getVitDWeight();
		sum_weights += NutritionalRecommandationHelper.nutrR
				.getProteinsWeight()
				+ NutritionalRecommandationHelper.nutrR.getLipidsWeight()
				+ NutritionalRecommandationHelper.nutrR
						.getCarbohydratesWeight()
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
	// Set<String> dayMealIngredientsSet =
	// dayMeal.getNutrientsValuesMap().keySet();
	// Set<String> preferenceSet = UserProfileHelper.getPreferenceSet();
	// Set<String> allIngredientsSet = new
	// TreeSet<String>(dayMealIngredientsSet);
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
	// ideal = likeSet.contains(ingredient) ? 1 : 0; // 1 for likeSet, 0 for
	// disLike
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
	 * Computes the error margin for fitness level 2. It is based on the lookup
	 * table described in the documentation.
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

		lower_limit = (float) ((lower_limit != 0) ? lower_limit : 0.000001); // don't
																				// divide
																				// by
																				// 0
		x = (float) (x * MAX / lower_limit);
		lower_limit = MAX;

		return Math.exp(-(x - lower_limit) * (x - lower_limit));
	}

	private Float errorMarginInterval(Float x, Float lower_limit,
			Float upper_limit) {
		if (lower_limit <= x && x <= upper_limit)
			return (float) 1;
		else if (x < lower_limit)
			return (float) errorMargin(x, lower_limit);
		else
			return (float) errorMargin(x, upper_limit);
	}

	/**
	 * Computes to probability that this solution (which is to be the queen)
	 * will pick the drone in the mating dance. NEEDS REVISION!!!!! VALUES ARE
	 * NOT PROPERLY CORRELATED WITH THRESHOLD
	 *
	 * @param drone
	 * @return a double in interval [0, 1] representing the probability.
	 */
	public double probabilityToMateDrone(Solution drone) {
		double thisFitness = this.getFitness();
		double droneFitness = drone.getFitness();
		double prob = Math.exp(-Math.abs(thisFitness - droneFitness)
				/ this.speed);
		return prob;
	}

	/**
	 * Computes the number of broods this queen makes with the drone, based on:
	 * The queen's energy The drone's fitness value A random float number in
	 * interval [0, 1] This function should return between 5 and 15 drones
	 * Energy is between 0-100 Drone's fitness is between 0 and 1 Random value
	 * will be between
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
	 * Supposing this is the queen, the following parameters have to updated at
	 * each iteration: speed, energy
	 */
	public void nextIteration() {
		speed *= speedReductionFactor;
		energy -= energyReductionAmount;

		// System.out.println("Energy = " + energy + " Speed = " + speed);
	}

	/**
	 * Provides a set of solutions derived from this queen. Runs the genotypes
	 * combination algorithm for each new brood.
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
	 * Performs crossover only on a single element. The result will be the same
	 * as drone with <i>type</i> stolen from this queen.
	 * 
	 * @param drone
	 * @param type
	 * @return
	 */
	public Solution combineSingleGenotype(Solution drone, GeneType type) {
		DailyMenu dailyMenu = null;
		FoodProviderPackage droneBreakfast = drone.getDailyMenu()
				.getBreakfast();
		FoodProviderPackage droneLunch = drone.getDailyMenu().getLunch();
		FoodProviderPackage droneDinner = drone.getDailyMenu().getDinner();
		FoodProviderPackage droneSnack1 = drone.getDailyMenu().getSnack1();
		FoodProviderPackage droneSnack2 = drone.getDailyMenu().getSnack2();

		switch (type) {
		case Breakfast:
			dailyMenu = new DailyMenu(this.dailyMenu.breakfast, droneLunch,
					droneDinner, droneSnack1, droneSnack2);
			break;
		case Lunch:
			dailyMenu = new DailyMenu(droneBreakfast, this.dailyMenu.lunch,
					droneDinner, droneSnack1, droneSnack2);
			break;
		case Dinner:
			dailyMenu = new DailyMenu(droneBreakfast, droneLunch,
					this.dailyMenu.dinner, droneSnack1, droneSnack2);
			break;
		case Snack1:
			if (this.dailyMenu.snack1.getMenu().getMenuId() == droneSnack2
					.getMenu().getMenuId())
				dailyMenu = new DailyMenu(droneBreakfast, droneLunch,
						droneDinner, droneSnack1, droneSnack2);
			else
				dailyMenu = new DailyMenu(droneBreakfast, droneLunch,
						droneDinner, this.dailyMenu.snack1, droneSnack2);
			break;
		case Snack2:
			if (this.dailyMenu.snack2.getMenu().getMenuId() == droneSnack1
					.getMenu().getMenuId())
				dailyMenu = new DailyMenu(droneBreakfast, droneLunch,
						droneDinner, droneSnack1, droneSnack2);
			else
				dailyMenu = new DailyMenu(droneBreakfast, droneLunch,
						droneDinner, droneSnack1, this.dailyMenu.snack2);
			break;
		default:
			break;
		}

		return new Solution(dailyMenu);
	}

	/**
	 * Runs the genotypes combination algorithm for this queen and the drone.
	 * Combination of genotypes is done by doing a crossover between the queens
	 * genomes and the drone's genomes
	 *
	 * @param drone
	 * @return A new solution (brood).
	 */
	public Solution combineGenotypes(Solution drone) {
		Random r = new Random();

		FoodProviderPackage breakfast = r.nextBoolean() ? drone.getDailyMenu()
				.getBreakfast() : this.dailyMenu.getBreakfast();
		FoodProviderPackage lunch = r.nextBoolean() ? drone.getDailyMenu()
				.getLunch() : this.dailyMenu.getLunch();
		FoodProviderPackage dinner = r.nextBoolean() ? drone.getDailyMenu()
				.getDinner() : this.dailyMenu.getDinner();
		FoodProviderPackage snack1 = r.nextBoolean() ? drone.getDailyMenu()
				.getSnack1() : this.dailyMenu.getSnack1();
		FoodProviderPackage snack2 = r.nextBoolean() ? drone.getDailyMenu()
				.getSnack2() : this.dailyMenu.getSnack2();
		DailyMenu newdailyMenu = null;
		if (snack1.getMenu().getMenuId() == snack2.getMenu().getMenuId())
			newdailyMenu = new DailyMenu(breakfast, lunch, dinner, drone
					.getDailyMenu().getSnack1(), drone.getDailyMenu()
					.getSnack2());
		else
			newdailyMenu = new DailyMenu(breakfast, lunch, dinner, snack1,
					snack2);

		return new Solution(newdailyMenu);
	}

	/**
	 * Returns a new instance of Solution which has exactly the same components
	 * as this exept one component randomly selected which is replaced with a
	 * random value.
	 * 
	 * @return a new Solution
	 */
	public Solution randomMutation() {
		// BusinessLogic bl = new BusinessLogic();
		// BusinessLogicCache bl = BusinessLogicCache.getInstance();
		BusinessLogicCacheFilteredOpt bl = BusinessLogicCacheFilteredOpt
				.getInstance();
		Random r = new Random();

		FoodProviderPackage breakfast = dailyMenu.getBreakfast();
		FoodProviderPackage lunch = dailyMenu.getLunch();
		FoodProviderPackage dinner = dailyMenu.getDinner();
		FoodProviderPackage snack1 = dailyMenu.getSnack1();
		FoodProviderPackage snack2 = dailyMenu.getSnack2();

		switch (r.nextInt(4)) {
		case 0:
			// Replace Breakfast
			FoodProviderPackage b = bl.generateSingleBreakfastPackages();
			return new Solution(new DailyMenu(b, lunch, dinner, snack1, snack2));

		case 1:
			// Replace Lunch
			FoodProviderPackage l = bl.generateSingleLunchPackages();
			return new Solution(new DailyMenu(breakfast, l, dinner, snack1,
					snack2));

		case 2:
			// Replace dinner
			FoodProviderPackage d = bl.generateSingleDinnerPackages();
			return new Solution(new DailyMenu(breakfast, lunch, d, snack1,
					snack2));
		case 3:
			// Replace snacks
			FoodProviderPackage s = bl.generateSingleSnackPackages();
			if (r.nextBoolean()) {
				if (s.getMenu().getMenuId() != snack2.getMenu().getMenuId())
					return new Solution(new DailyMenu(breakfast, lunch, dinner,
							s, snack2));
				else
					return new Solution(new DailyMenu(breakfast, lunch, dinner,
							snack1, snack2));
			} else {
				if (snack1.getMenu().getMenuId() != s.getMenu().getMenuId())
					return new Solution(new DailyMenu(breakfast, lunch, dinner,
							snack1, s));
				else
					return new Solution(new DailyMenu(breakfast, lunch, dinner,
							snack1, snack2));
			}
		default:
			return null;
		}
	}

	public boolean hasEnergy() {
		return energy > 0;
	}

	public boolean hasSpeed() {
		return speed > 0;
	}

	@Override
	public String toString() {
		// return dayMeal.toString() + "\n" +
		// dayMeal.getNutrientsValuesMap().toString();
		return dailyMenu.toString();
	}

	public ArrayList<String> exportDataAsString() {
		ArrayList<String> data = new ArrayList<String>();

		// nutrients
		data.add(String.valueOf(dailyMenu.getEnergy()));
		data.add(String.valueOf(dailyMenu.getCarbohydrates()));
		data.add(String.valueOf(dailyMenu.getProteins()));
		data.add(String.valueOf(dailyMenu.getLipids()));
		data.add(String.valueOf(dailyMenu.getVitA()));
		data.add(String.valueOf(dailyMenu.getVitB()));
		data.add(String.valueOf(dailyMenu.getVitC()));
		data.add(String.valueOf(dailyMenu.getVitD()));
		data.add(String.valueOf(dailyMenu.getCalcium()));
		data.add(String.valueOf(dailyMenu.getIron()));
		data.add(String.valueOf(dailyMenu.getSodium()));

		// food items
		data.add(dailyMenu.getBreakfast().toString());
		data.add(dailyMenu.getLunch().toString());
		data.add(dailyMenu.getDinner().toString());
		data.add(dailyMenu.getSnack1().toString());
		data.add(dailyMenu.getSnack2().toString());

		// cost and delivery time
		data.add(String.valueOf(dailyMenu.getCost()));
		data.add(String.valueOf(dailyMenu.getAvgDeliveryTime()));

		return data;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Solution))
			return false;

		return this.dailyMenu.equals(((Solution) other).getDailyMenu());
	}

	public DailyMenu getDailyMenu() {
		return dailyMenu;
	}

	public void setDailyMenu(DailyMenu dailyMenu) {
		this.dailyMenu = dailyMenu;
	}

	public static Logger getLog() {
		return log;
	}

	public float getSolutionSimilarityCoefficient(Solution drone) {
		float sumComp = 0;
		float sumOfWeights = 5;
		// 5 meals / day with weight of 1
		if (this.dailyMenu.getBreakfast().getMenu().getMenuId() == drone.dailyMenu
				.getBreakfast().getMenu().getMenuId())
			sumComp++;
		if (this.dailyMenu.getLunch().getMenu().getMenuId() == drone.dailyMenu
				.getLunch().getMenu().getMenuId())
			sumComp++;
		if (this.dailyMenu.getDinner().getMenu().getMenuId() == drone.dailyMenu
				.getDinner().getMenu().getMenuId())
			sumComp++;
		if (this.dailyMenu.getSnack1().getMenu().getMenuId() == drone.dailyMenu
				.getSnack1().getMenu().getMenuId())
			sumComp++;
		if (this.dailyMenu.getSnack2().getMenu().getMenuId() == drone.dailyMenu
				.getSnack2().getMenu().getMenuId())
			sumComp++;
		return sumComp / sumOfWeights;
	}

	/**
	 * TODO revise: GeneType may be at the following 2 levels: 1) dish -
	 * starter, main ... or 2) meal - breakfast, lunch ...
	 */
	public enum GeneType {
		/*
		 * BreakfastMainCourse, BreakfastDesert, LunchStarter, LunchMainCourse,
		 * LunchDesert, DinnerStarter, DinnerMainCourse, DinnerDesert, Snack1,
		 * Snack2;
		 */

		Breakfast, Lunch, Dinner, Snack1, Snack2;

		/*
		 * Breakfast, Lunch, Dinner, Snack1, Snack2;
		 */
		public static GeneType getRandom() {
			return values()[(int) (Math.random() * values().length)];
		}
	}
}
