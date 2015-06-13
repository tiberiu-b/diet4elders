package d4elders.algorithm.broodImprover;

import java.util.ArrayList;
import java.util.Random;

import d4elders.dal.IBusinessLogic;
import d4elders.dal.helper.FileIO;
import d4elders.model.dish.Dish;
import d4elders.model.dish.Recipe;
import d4elders.model.food_package.FoodProviderPackage;
import d4elders.model.food_package.FoodServiceProvider;
import d4elders.model.food_package.GeographicalArea;
import d4elders.model.food_package.Menu;
import d4elders.model.meal.MealVariant;

public class BusinessLogicCacheFilteredOpt implements IBusinessLogic {
	protected static BusinessLogicCacheFilteredOpt bl;

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
	private ArrayList<String> allergyList;
	private Random r = new Random();
	private BusinessLogicCacheFilteredOpt() {
		allergyList = new ArrayList<String>();
	}

	public static BusinessLogicCacheFilteredOpt getInstance() {
		if (bl == null)
			bl = new BusinessLogicCacheFilteredOpt();
		return bl;
	}

	/**
	 * Prefetches the data from ontolgy into main memory. Any subsequent call will return data from
	 * the faster main memory instead of the slower ontology.
	 */
	public void loadOntologyDataIntoMemory(ArrayList<String> allergyList) {
		this.allergyList=allergyList;
		if (packageListCache == null)
			loadCache2();
	}

	public void loadCache2() {
		FileIO des = new FileIO();
		long startTime = System.currentTimeMillis();
		//recipeListCache = des.deserializeRecipe();
		long stopTime = System.currentTimeMillis();
		System.out.println("Time for recipeCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		//dishListCache = des.deserializeDish();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for dishCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		//mealVariantListCache = des.deserializeMealVariant();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for mealVariantCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		//geoAreaListCache = des.deserializeGeographicalArea();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for geoAreaCache" + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		//foodProviderListCache = des.deserializeFoodServiceProvider();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for foodProviderCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		//menuListCache = des.deserializeMenu();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for menuCache" + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		filterFoodPackagesList(des.deserializeFoodProviderPackage());
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
		System.out.println("Hello");
	}

	@Override
	public FoodProviderPackage generateSingleBreakfastPackages() {

		return breakfastPackageListCache.get(r.nextInt(breakfastPackageListCache.size()));
	}

	@Override
	public FoodProviderPackage generateSingleLunchPackages() {
		return lunchPackageListCache.get(r.nextInt(lunchPackageListCache.size()));
	}

	@Override
	public FoodProviderPackage generateSingleDinnerPackages() {
		return dinnerPackageListCache.get(r.nextInt(dinnerPackageListCache.size()));
	}

	@Override
	public FoodProviderPackage generateSingleSnackPackages() {
		return snackPackageListCache.get(r.nextInt(snackPackageListCache.size()));
	}

	@Override
	public ArrayList<FoodProviderPackage> generateBreakfastPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> breakfastPackageList = new ArrayList<>();
		for (int i = 0; i < numberOfSolutions; i++) {
			breakfastPackageList.add(breakfastPackageListCache.get(r.nextInt(breakfastPackageListCache.size())));
		}
		return breakfastPackageList;
	}

	@Override
	public ArrayList<FoodProviderPackage> generateLunchPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> lunchPackageList = new ArrayList<>();
		for (int i = 0; i < numberOfSolutions; i++) {
			lunchPackageList.add(lunchPackageListCache.get(r.nextInt(lunchPackageListCache.size())));
		}
		return lunchPackageList;
	}

	@Override
	public ArrayList<FoodProviderPackage> generateDinnerPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> dinnerPackageList = new ArrayList<>();
		for (int i = 0; i < numberOfSolutions; i++) {
			dinnerPackageList.add(dinnerPackageListCache.get(r.nextInt(dinnerPackageListCache.size())));
		}
		return dinnerPackageList;
	}

	@Override
	public ArrayList<FoodProviderPackage> generateSnackPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> snackPackageList = new ArrayList<>();
		for (int i = 0; i < numberOfSolutions; i++) {
			snackPackageList.add(snackPackageListCache.get(r.nextInt(snackPackageListCache.size())));
		}
		return snackPackageList;
	}

	@Override
	public ArrayList<FoodProviderPackage> getAllBreakfastPackages() {
		if (breakfastPackageListCache == null) {
			loadCache2();
		}
		return breakfastPackageListCache;
	}

	@Override
	public ArrayList<FoodProviderPackage> getAllLunchPackages() {
		if (lunchPackageListCache == null) {
			loadCache2();
		}
		return lunchPackageListCache;
	}

	@Override
	public ArrayList<FoodProviderPackage> getAllDinnerPackages() {
		if (dinnerPackageListCache == null) {
			loadCache2();
		}
		return dinnerPackageListCache;
	}

	@Override
	public ArrayList<FoodProviderPackage> getAllSnackPackages() {
		if (snackPackageListCache == null) {
			loadCache2();
		}
		return snackPackageListCache;
	}

	public ArrayList<String> getAllergyList() {
		return allergyList;
	}

	public void setAllergyList(ArrayList<String> allergyList) {
		this.allergyList = allergyList;
	}

	private void filterFoodPackagesList(ArrayList<FoodProviderPackage> list) {
		packageListCache = new ArrayList<FoodProviderPackage>();

		for (FoodProviderPackage pkg : list) {
			boolean packageValid = true;
			for (String allergy : allergyList) {
				for (String ingredient : pkg.getMenu().getIngredientList()) {
					if (ingredient.contains(allergy)) {
						packageValid = false;
						break;
					}

				}
			}
			if (packageValid)
				packageListCache.add(pkg);
		}
	}

}
