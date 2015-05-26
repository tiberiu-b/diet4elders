package d4elders.dal;

import java.util.ArrayList;
import java.util.Random;

import javax.lang.model.type.ErrorType;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

import d4elders.dal.helper.FileIO;
import d4elders.model.*;
import d4elders.model.dish.*;
import d4elders.model.food_package.*;
import d4elders.model.meal.*;
import d4elders.ontology.FoodProviderOntology;

public class BusinessLogicCache implements IBusinessLogic {
	protected static BusinessLogicCache bl;

	/*
	 * The arrays are fetched once and stored in cache. Subsequent calls return the cached arrays.
	 * Since the elements in the ontology are constant during a run of the program, there won't be
	 * any misses.
	 */

	protected ArrayList<FoodProviderPackage> breakfastPackageListCache;
	protected ArrayList<FoodProviderPackage> lunchPackageListCache;
	protected ArrayList<FoodProviderPackage> dinnerPackageListCache;
	protected ArrayList<FoodProviderPackage> snackPackageListCache;

	protected ArrayList<GeographicalArea> geoAreaListCache;
	protected ArrayList<FoodServiceProvider> foodProviderListCache;
	protected ArrayList<Recipe> recipeListCache;
	protected ArrayList<Dish> dishListCache;
	protected ArrayList<MealVariant> mealVariantListCache;
	protected ArrayList<Menu> menuListCache;
	protected ArrayList<FoodProviderPackage> packageListCache;

	private BusinessLogicCache() {
		// foodProviderOntology = new FoodProviderOntology();
		// model = foodProviderOntology.getOntModel();
		// data = foodProviderOntology.getD2rData();
		// model.add(data);
		loadOntologyDataIntoMemory();
	}

	public static BusinessLogicCache getInstance() {
		if (bl == null)
			bl = new BusinessLogicCache();
		return bl;
	}

	/**
	 * Prefetches the data from ontolgy into main memory. Any subsequent call will return data from
	 * the faster main memory instead of the slower ontology.
	 */
	public void loadOntologyDataIntoMemory() {
		if (packageListCache == null)
			loadCache2();
	}

	public void loadCache2() {
		FileIO des = new FileIO();
		long startTime = System.currentTimeMillis();
		recipeListCache = des.deserializeRecipe();
		long stopTime = System.currentTimeMillis();
		System.out.println("Time for recipeCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		dishListCache = des.deserializeDish();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for dishCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		mealVariantListCache = des.deserializeMealVariant();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for mealVariantCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		geoAreaListCache = des.deserializeGeographicalArea();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for geoAreaCache" + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		foodProviderListCache = des.deserializeFoodServiceProvider();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for foodProviderCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		menuListCache = des.deserializeMenu();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for menuCache" + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		packageListCache = des.deserializeFoodProviderPackage();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for foodPackageCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();

		// load mealPackages for speed
		breakfastPackageListCache = new ArrayList<FoodProviderPackage>();
		lunchPackageListCache = new ArrayList<FoodProviderPackage>();
		dinnerPackageListCache = new ArrayList<FoodProviderPackage>();
		snackPackageListCache = new ArrayList<FoodProviderPackage>();
		for (FoodProviderPackage fp : packageListCache) {
			switch (fp.getMenu().getMealType()) {
			case Breakfast:
				breakfastPackageListCache.add(fp);
				break;
			case Lunch:
				lunchPackageListCache.add(fp);
				break;
			case Dinner:
				dinnerPackageListCache.add(fp);
				break;
			case Snack:
				snackPackageListCache.add(fp);
				break;
			}
		}
	}

	public FoodProviderPackage generateSingleBreakfastPackages() {
		Random r = new Random();
		return breakfastPackageListCache.get(r.nextInt(breakfastPackageListCache.size()));
	}

	public FoodProviderPackage generateSingleLunchPackages() {
		Random r = new Random();
		return lunchPackageListCache.get(r.nextInt(lunchPackageListCache.size()));
	}

	public FoodProviderPackage generateSingleDinnerPackages() {
		Random r = new Random();
		return dinnerPackageListCache.get(r.nextInt(dinnerPackageListCache.size()));
	}

	public FoodProviderPackage generateSingleSnackPackages() {
		Random r = new Random();
		return snackPackageListCache.get(r.nextInt(snackPackageListCache.size()));
	}

	public ArrayList<FoodProviderPackage> generateBreakfastPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> breakfastPackageList = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < numberOfSolutions; i++) {
			breakfastPackageList.add(breakfastPackageListCache.get(r.nextInt(breakfastPackageListCache.size())));
		}
		return breakfastPackageList;
	}

	public ArrayList<FoodProviderPackage> generateLunchPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> lunchPackageList = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < numberOfSolutions; i++) {
			lunchPackageList.add(lunchPackageListCache.get(r.nextInt(lunchPackageListCache.size())));
		}
		return lunchPackageList;
	}

	public ArrayList<FoodProviderPackage> generateDinnerPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> dinnerPackageList = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < numberOfSolutions; i++) {
			dinnerPackageList.add(dinnerPackageListCache.get(r.nextInt(dinnerPackageListCache.size())));
		}
		return dinnerPackageList;
	}

	public ArrayList<FoodProviderPackage> generateSnackPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> snackPackageList = new ArrayList<>();
		Random r = new Random();
		for (int i = 0; i < numberOfSolutions; i++) {
			snackPackageList.add(snackPackageListCache.get(r.nextInt(snackPackageListCache.size())));
		}
		return snackPackageList;
	}

	public ArrayList<FoodProviderPackage> getAllBreakfastPackages() {
		if (breakfastPackageListCache == null) {
			loadCache2();
		}
		return breakfastPackageListCache;
	}

	public ArrayList<FoodProviderPackage> getAllLunchPackages() {
		if (lunchPackageListCache == null) {
			loadCache2();
		}
		return lunchPackageListCache;
	}

	public ArrayList<FoodProviderPackage> getAllDinnerPackages() {
		if (dinnerPackageListCache == null) {
			loadCache2();
		}
		return dinnerPackageListCache;
	}

	public ArrayList<FoodProviderPackage> getAllSnackPackages() {
		if (snackPackageListCache == null) {
			loadCache2();
		}
		return snackPackageListCache;
	}

}