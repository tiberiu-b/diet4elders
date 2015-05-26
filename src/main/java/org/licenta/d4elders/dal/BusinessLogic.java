package org.licenta.d4elders.dal;

import java.util.ArrayList;
import java.util.Random;

import javax.lang.model.type.ErrorType;

import org.licenta.d4elders.dal.helper.FileIO;
import org.licenta.d4elders.model.*;
import org.licenta.d4elders.model.dish.*;
import org.licenta.d4elders.model.food_package.FoodProviderPackage;
import org.licenta.d4elders.model.food_package.FoodServiceProvider;
import org.licenta.d4elders.model.food_package.GeographicalArea;
import org.licenta.d4elders.model.food_package.Menu;
import org.licenta.d4elders.model.meal.MealType;
import org.licenta.d4elders.model.meal.MealVariant;
import org.licenta.d4elders.ontology.FoodProviderOntology;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

public class BusinessLogic implements IBusinessLogic {
	private static BusinessLogic bl;
	private FoodProviderOntology foodProviderOntology;
	private Model model;
	private Model data;

	/*
	 * The arrays are fetched once and stored in cache. Subsequent calls return the cached arrays.
	 * Since the elements in the ontology are constant during a run of the program, there won't be
	 * any misses.
	 */
	private ArrayList<FoodProviderPackage> breakfastPackageListCache;
	private ArrayList<FoodProviderPackage> lunchPackageListCache;
	private ArrayList<FoodProviderPackage> dinnerPackageListCache;
	private ArrayList<FoodProviderPackage> snackPackageListCache;

	private ArrayList<GeographicalArea> geoAreaListCache;
	private ArrayList<FoodServiceProvider> foodProviderListCache;
	private ArrayList<Recipe> recipeListCache;
	private ArrayList<Dish> dishListCache;
	private ArrayList<MealVariant> mealVariantListCache;
	private ArrayList<Menu> menuListCache;
	private ArrayList<FoodProviderPackage> packageListCache;

	private BusinessLogic() {
		foodProviderOntology = new FoodProviderOntology();
		model = foodProviderOntology.getOntModel();
		data = foodProviderOntology.getD2rData();
		model.add(data);
		loadOntologyDataIntoMemory();
	}

	public static BusinessLogic getInstance() {
		if (bl == null)
			bl = new BusinessLogic();
		return bl;
	}

	/**
	 * Prefetches the data from ontolgy into main memory. Any subsequent call will return data from
	 * the faster main memory instead of the slower ontology.
	 */
	public void loadOntologyDataIntoMemory() {
		if (packageListCache == null)
			loadCache();
	}

	public ArrayList<Menu> getAllMenus() {
		ArrayList<Menu> menuList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // prefix
				+ "SELECT DISTINCT ?menu ?menuId ?mealType ?mealV ?mealVId " // Menu
				// FoodProvider info
				+ "WHERE {?menu rdf:type nutritionassesment:Menu." // menu
				+ "?menu foodprovider:menuHasId ?menuId." // id
				+ "?menu foodprovider:menuHasMealType ?mealType." // meal type
				+ "?menu foodprovider:menuHasMealVariant ?mealV." // meal variant
				+ "?mealV foodprovider:mealVariantHasId ?mealVId}";// meal variant

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("menu") != null) {
				Menu menu = null;
				int menuId = row.getLiteral("menuId").getInt();
				int mealVId = row.getLiteral("mealVId").getInt();
				MealType mealType = MealType.valueOf(row.getResource("mealType").getLocalName());
				for (Menu m : menuList) {
					if (m.getMenuId() == menuId) {
						menu = m;
						break;
					}
				}
				MealVariant mealV = null;
				for (MealVariant mv : mealVariantListCache) {
					if (mv.getMealVariantId() == mealVId) {
						mealV = mv;
						break;
					}
				}
				if (mealV == null) {
					if (menu != null) {
						menuList.remove(menu);
					}
					continue;
				}

				if (menu == null) {
					menu = new Menu();
					menu.setMenuId(menuId);
					switch (mealV.getDishType()) {
					case Starter:
						menu.setStarter(mealV);
						break;
					case Main_Course:
						menu.setMainCourse(mealV);
						break;
					case Desert:
						menu.setDesert(mealV);
						break;
					}
					menuList.add(menu);
				} else {
					switch (mealV.getDishType()) {
					case Starter:
						menu.setStarter(mealV);
						break;
					case Main_Course:
						menu.setMainCourse(mealV);
						break;
					case Desert:
						menu.setDesert(mealV);
						break;
					}
				}
			}
		}
		int whatCount = 0;
		ArrayList<Menu> menusToDelete = new ArrayList<Menu>();
		for (Menu m : menuList) {
			if (m.getMainCourse() == null) {
				menusToDelete.add(m);
				whatCount++;
				// System.out.println("Whaat? " + m.getMenuId());
			} else {
				m.computeNutrientValues();
			}
		}
		for (Menu m : menusToDelete) {
			menuList.remove(m);
		}
		for (Menu m : menuList) {
			m.setMealType(m.getMainCourse().getMealType());
		}
		return menuList;
	}

	public ArrayList<FoodProviderPackage> getAllFoodPackages() {
		ArrayList<FoodProviderPackage> packageList = new ArrayList<>();
		breakfastPackageListCache = new ArrayList<>();
		lunchPackageListCache = new ArrayList<>();
		dinnerPackageListCache = new ArrayList<>();
		snackPackageListCache = new ArrayList<>();
		String queryString = FoodProviderOntology.PREFIX // prefix
				+ "SELECT DISTINCT ?package ?packageId ?cost ?deliveryTime " // package
				+ "?menu ?menuId ?foodProv ?foodProvId " // Menu
				// FoodProvider info
				+ "WHERE {?package rdf:type nutritionassesment:FoodProviderPackage." // menu
				+ "?package foodprovider:foodProviderPackageHasId ?packageId." // id
				+ "?package foodprovider:foodProviderPackageHasMenu ?menu." // id
				+ "?package foodprovider:foodProviderPackageHasFoodProvider ?foodProv." // id
				+ "?package foodprovider:foodProviderPackageHasCost ?cost." // id
				+ "?package foodprovider:foodProviderPackageHasDeliveryTime ?deliveryTime." // id
				+ "?menu foodprovider:menuHasId ?menuId." // id
				+ "?foodProv foodprovider:foodProviderHasID ?foodProvId}"; // meal type

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("package") != null) {
				FoodProviderPackage foodPackage = new FoodProviderPackage();
				int menuId = row.getLiteral("menuId").getInt();
				int foodProvId = row.getLiteral("foodProvId").getInt();
				int deliveryTime = row.getLiteral("deliveryTime").getInt();
				double cost = row.getLiteral("cost").getDouble();
				foodPackage.setCost(cost);
				foodPackage.setDeliveryTime(deliveryTime);
				for (Menu m : menuListCache) {
					if (m.getMenuId() == menuId) {
						foodPackage.setMenu(m);
						break;
					}
				}
				if (foodPackage.getMenu() == null)
					continue;
				FoodServiceProvider fProv = null;
				for (FoodServiceProvider fp : foodProviderListCache) {
					if (fp.getFoodProviderId() == foodProvId) {
						fProv = fp;
						break;
					}
				}
				if (fProv == null) {
					continue;
				}
				foodPackage.setFoodProv(fProv);
				foodPackage.setPackageId(row.getLiteral("packageId").getInt());
				switch (foodPackage.getMenu().getMealType()) {
				case Breakfast:
					breakfastPackageListCache.add(foodPackage);
					break;
				case Lunch:
					lunchPackageListCache.add(foodPackage);
					break;
				case Dinner:
					dinnerPackageListCache.add(foodPackage);
					break;
				case Snack:
					snackPackageListCache.add(foodPackage);
					break;
				}
				packageList.add(foodPackage);
			}
		}
		return packageList;
	}

	public ArrayList<GeographicalArea> getAllGeographicalAreas() {
		ArrayList<GeographicalArea> geoAreaList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // prefix
				+ "SELECT DISTINCT ?geoArea ?geoId ?geoCountry ?geoRegion ?geoCity " // Recipe
				// MealVariant info
				+ "WHERE {?geoArea rdf:type nutritionassesment:GeographicalArea." // geoArea
				+ "?geoArea foodprovider:geographicalAreaHasId ?geoId." // id
				+ "?geoArea foodprovider:geographicalAreaHasCountry ?geoCountry." // country
				+ "?geoArea foodprovider:geographicalAreaHasRegion ?geoRegion." // region
				+ "?geoArea foodprovider:geographicalAreaHasCity ?geoCity}"; // city

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("geoArea") != null) {
				GeographicalArea geoArea = new GeographicalArea();
				geoArea.setGeoAreaId(row.getLiteral("geoId").getInt());
				geoArea.setCountry(row.getLiteral("geoCountry").getString());
				geoArea.setRegion(row.getLiteral("geoRegion").getString());
				geoArea.setCity(row.getLiteral("geoCity").getString());
				geoAreaList.add(geoArea);
			}
		}
		return geoAreaList;
	}

	public ArrayList<FoodServiceProvider> getAllFoodProviders() {
		ArrayList<FoodServiceProvider> foodProviderList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // prefix
				+ "SELECT DISTINCT ?foodProv ?foodProvId ?foodProvName ?geoArea ?geoId " // Recipe
				// FoodProvider info
				+ "WHERE {?foodProv rdf:type nutritionassesment:FoodServiceProvider." // geoArea
				+ "?foodProv foodprovider:foodProviderHasID ?foodProvId." // id
				+ "?foodProv foodprovider:foodProviderHasName ?foodProvName." // name
				+ "?foodProv foodprovider:foodProviderHasGeographicalArea ?geoArea." // name
				+ "?geoArea foodprovider:geographicalAreaHasId ?geoId}"; // city

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("foodProv") != null) {
				FoodServiceProvider foodProv = new FoodServiceProvider();
				foodProv.setFoodProviderId(row.getLiteral("foodProvId").getInt());
				foodProv.setName(row.getLiteral("foodProvName").getString());
				int geoAreaId = row.getLiteral("geoId").getInt();

				GeographicalArea geoArea = null;
				for (GeographicalArea geoA : geoAreaListCache) {
					if (geoA.getGeoAreaId() == geoAreaId) {
						geoArea = geoA;
					}
				}
				if (geoArea == null)
					continue;
				foodProv.setGeoArea(geoArea);
				foodProviderList.add(foodProv);
			}
		}
		return foodProviderList;
	}

	public ArrayList<MealVariant> getAllMealVariants() {
		ArrayList<MealVariant> mealVariantList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // prefix
				+ "SELECT DISTINCT ?mealV ?mealVId ?diet ?mealType ?dish ?dishId ?dishType " // Recipe
				// MealVariant info
				+ "WHERE {?mealV rdf:type nutritionassesment:MealVariant." // menu
				+ "?mealV foodprovider:mealVariantHasId ?mealVId." // meal id
				+ "?mealV foodprovider:mealVariantHasMealType ?mealType." // mealtype
				+ "?mealV foodprovider:mealVariantHasDish ?dish." // dish
				+ "?dish foodprovider:dishHasDishType ?dishType." // dish
				+ "?dish foodprovider:dishHasId ?dishId}"; // dish

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("mealV") != null) {
				MealVariant mealV = new MealVariant();
				int mealVId = row.getLiteral("mealVId").getInt();
				mealV.setMealVariantId(mealVId);
				for (MealVariant mv : mealVariantList) {
					if (mv.getMealVariantId() == mealVId) {
						mealV = mv;
						break;
					}
				}
				DishType dishType = DishType.valueOf(row.getResource("dishType").getLocalName());
				MealType mealType = MealType.valueOf(row.getResource("mealType").getLocalName());
				int dishId = row.getLiteral("dishId").getInt();
				Dish dish = null;
				for (Dish d : dishListCache) {
					if (d.getDishId() == dishId) {
						dish = d;
						break;
					}
				}
				if (dish == null) {
					continue;
				}
				switch (dishType) {
				case Starter_Dish:
					mealV.setMainDish(dish);
					mealV.setDishType(DishTypeGeneral.Starter);
					mealV.setMealType(mealType);
					break;
				case Main_Course_Not_Single:
					mealV.setMainDish(dish);
					mealV.setDishType(DishTypeGeneral.Main_Course);
					mealV.setMealType(mealType);
					break;
				case Main_Course_Single:
					mealV.setMainDish(dish);
					mealV.setDishType(DishTypeGeneral.Main_Course);
					mealV.setMealType(mealType);
					break;
				case Dessert:
					mealV.setMainDish(dish);
					mealV.setDishType(DishTypeGeneral.Desert);
					mealV.setMealType(mealType);
					break;
				case Side_Dish:
					mealV.setSideDish(dish);
					mealV.setDishType(DishTypeGeneral.Main_Course);
					mealV.setMealType(mealType);
					break;
				}
				mealVariantList.add(mealV);
			}
		}
		int count = 30;
		for (MealVariant mv : mealVariantList) {
			// System.out.print(mv.getMealVariantId() + " ");
			count--;
			if (count < 0) {
				count = 30;
				// System.out.println();
			}
		}
		return mealVariantList;
	}

	public ArrayList<Dish> getAllDishes() {
		ArrayList<Dish> dishList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // Menu
				+ "SELECT DISTINCT ?dish ?dishId ?dishType ?mealType ?recipe ?recipeId " // Dish
				+ "WHERE {?dish rdf:type nutritionassesment:Dish." // menu
				+ "?dish foodprovider:dishHasId ?dishId." // dishId
				+ "?dish foodprovider:dishHasDishType ?dishType. "// dishType
				+ "?dish foodprovider:dishHasRecipe ?recipe."// recipe
				+ "?recipe foodprovider:recipeHasId ?recipeId}";

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		int counter = 0;
		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("dish") != null) {
				Dish dish = new Dish();
				int recipeId = row.getLiteral("recipeId").getInt();
				Recipe rec = null;
				for (Recipe r : recipeListCache) {
					if (r.getRecipeId() == recipeId) {
						rec = r;
						break;
					}
				}
				if (rec == null)
					continue;
				dish.setRecipe(rec);
				dish.setDishId(row.getLiteral("dishId").getInt());
				dishList.add(dish);
			}
		}
		return dishList;
	}

	public ArrayList<Recipe> getAllRecipes() {
		ArrayList<Recipe> recipeList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX
				+ "SELECT DISTINCT ?recipe ?recId ?recIngredient ?ingredientName ?recName ?recDescr ?recDishType " // Recipe
				+ "?recPro ?recLip ?recCar ?recEne ?recCal ?recIro ?recSod " // Recipe Nutr
				+ "?recVa ?recVb ?recVc ?recVd "// Recipe Nutr
				// Menu info
				+ "WHERE {?recipe rdf:type nutritionassesment:Recipe." // menu
				// Recipe data + nutrients
				+ "?recipe foodprovider:recipeHasId ?recId." // Id
				+ "?recipe foodprovider:recipeHasIngredient ?recIngredient." // Id
				+ "?recIngredient foodprovider:ingredientHasName ?ingredientName." // Id
				+ "?recipe foodprovider:recipeHasName ?recName." // Name
				+ "?recipe foodprovider:recipeHasDescription ?recDescr." // Descr
				+ "?recipe foodprovider:recipeHasDishType ?recDishType." // DishType
				+ "?recipe foodprovider:recipeHasProteins_g ?recPro." // Proteins
				+ "?recipe foodprovider:recipeHasFats_g ?recLip." // Fats
				+ "?recipe foodprovider:recipeHasCarbs_g ?recCar." // Carbs
				+ "?recipe foodprovider:recipeHasEnergy_kcal ?recEne." // Energy
				+ "?recipe foodprovider:recipeHasCalcium_mg ?recCal." // Calcium
				+ "?recipe foodprovider:recipeHasIron_mg ?recIro." // Iron
				+ "?recipe foodprovider:recipeHasSodium_mg ?recSod." // Sodium
				+ "?recipe foodprovider:recipeHasVitaminA_ug ?recVa." // Vitamin A
				+ "?recipe foodprovider:recipeHasVitaminB6_mg ?recVb." // Vitamin B
				+ "?recipe foodprovider:recipeHasVitaminC_mg ?recVc." // Vitamin C
				+ "?recipe foodprovider:recipeHasVitaminD_ug ?recVd}"; // Vitamin D

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("recipe") != null) {
				// ?recipe ?recId ?recName ?recDescr ?recDishType // Recipe
				// ?recPro ?recLip ?recCar ?recEne ?recCal ?recIro ?recSod // Recipe Nutr
				// ?recVa ?recVb ?recVc ?recVd // Recipe Nutr
				DishType dishType = DishType.valueOf(row.getResource("recDishType").getLocalName());
				Recipe rec = new Recipe();
				ArrayList<String> ingredientList = new ArrayList<>();
				boolean recipeExists = false;
				for (Recipe r : recipeList) {
					if (r.getRecipeId() == row.getLiteral("recId").getInt()) {
						rec = r;
						ingredientList = r.getIngredientList();
						ingredientList.add(row.getLiteral("ingredientName").getString());
						r.setIngredientList(ingredientList);
						recipeExists = true;
						break;
					}
				}
				if (recipeExists)
					continue;
				ingredientList.add(row.getLiteral("ingredientName").getString());
				rec.setIngredientList(ingredientList);
				rec.setRecipeId(row.getLiteral("recId").getInt());
				rec.setDishType(dishType);
				rec.setName(row.getLiteral("recName").getString());
				rec.setDescription(row.getLiteral("recDescr").getString());
				rec.setCalcium(row.getLiteral("recCal").getFloat());
				rec.setCarbohydrates(row.getLiteral("recCar").getFloat());
				rec.setEnergy(row.getLiteral("recEne").getFloat());
				rec.setIron(row.getLiteral("recIro").getFloat());
				rec.setLipids(row.getLiteral("recLip").getFloat());
				rec.setProteins(row.getLiteral("recPro").getFloat());
				rec.setSodium(row.getLiteral("recSod").getFloat());
				rec.setVitA(row.getLiteral("recVa").getFloat());
				rec.setVitB(row.getLiteral("recVb").getFloat());
				rec.setVitC(row.getLiteral("recVc").getFloat());
				rec.setVitD(row.getLiteral("recVd").getFloat());
				recipeList.add(rec);
			}
		}
		return recipeList;
	}

	public void loadCache() {
		long startTime = System.currentTimeMillis();
		recipeListCache = getAllRecipes();
		long stopTime = System.currentTimeMillis();
		System.out.println("Time for recipeCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		dishListCache = getAllDishes();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for dishCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		mealVariantListCache = getAllMealVariants();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for mealVariantCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		geoAreaListCache = getAllGeographicalAreas();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for geoAreaCache" + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		foodProviderListCache = getAllFoodProviders();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for foodProviderCache " + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		menuListCache = getAllMenus();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for menuCache" + (stopTime - startTime));
		startTime = System.currentTimeMillis();
		packageListCache = getAllFoodPackages();
		stopTime = System.currentTimeMillis();
		System.out.println("Time for foodPackageCache " + (stopTime - startTime));

		startTime = System.currentTimeMillis();
		storeCache();
		stopTime = System.currentTimeMillis();
		System.out.println("------------------\nTime for storing Cache " + (stopTime - startTime));

	}

	private void storeCache() {
		FileIO ser = new FileIO();
		ser.serializeRecipe(recipeListCache);
		ser.serializeDish(dishListCache);
		ser.serializeMealVariant(mealVariantListCache);
		ser.serializeGeographicalArea(geoAreaListCache);
		ser.serializeFoodServiceProvider(foodProviderListCache);
		ser.serializeMenu(menuListCache);
		ser.serializeFoodProviderPackage(packageListCache);
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
			loadCache();
		}
		return breakfastPackageListCache;
	}

	public ArrayList<FoodProviderPackage> getAllLunchPackages() {
		if (lunchPackageListCache == null) {
			loadCache();
		}
		return lunchPackageListCache;
	}

	public ArrayList<FoodProviderPackage> getAllDinnerPackages() {
		if (dinnerPackageListCache == null) {
			loadCache();
		}
		return dinnerPackageListCache;
	}

	public ArrayList<FoodProviderPackage> getAllSnackPackages() {
		if (snackPackageListCache == null) {
			loadCache();
		}
		return snackPackageListCache;
	}

}