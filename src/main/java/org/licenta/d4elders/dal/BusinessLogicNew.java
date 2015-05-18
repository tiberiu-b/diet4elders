package org.licenta.d4elders.dal;

import java.util.ArrayList;
import java.util.Random;

import org.licenta.d4elders.model.*;
import org.licenta.d4elders.model.dish.*;
import org.licenta.d4elders.model.meal.*;
import org.licenta.d4elders.obsolete.Desert;
import org.licenta.d4elders.obsolete.MainCourse;
import org.licenta.d4elders.obsolete.StarterDish;
import org.licenta.d4elders.ontology.FoodProviderOntology;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.resultset.ResultsFormat;

public class BusinessLogicNew {
	private static FoodProviderOntology foodProviderOntology;
	private static Model model;
	private static Model data;
	private String breakfast = "Breakfast";
	private String lunch = "Lunch";
	private String dinner = "Dinner";
	private String snack = "Snack";
	private String starterDish = "Starter_Dish";
	private String mainCourseSingle = "Main_Course_Single";
	private String mainCourseNotSingle = "Main_Course_Not_Single";
	private String sideDish = "Side_Dish";
	private String desert = "Dessert";

	/*
	 * The arrays are fetched once and stored in cache. Subsequent calls return the cached arrays.
	 * Since the elements in the ontology are constant during a run of the program, there won't be
	 * any misses.
	 */
	private static ArrayList<Recipe> breakfastMainCourseSingleRecipesCache;
	private static ArrayList<Recipe> breakfastMainCourseNotSingleRecipesCache;
	private static ArrayList<Recipe> breakfastSideDishRecipesCache;
	private static ArrayList<Recipe> breakfastDesertCache;
	private static ArrayList<Recipe> lunchStarterDishRecipesCache;
	private static ArrayList<Recipe> lunchMainCourseSingleRecipesCache;
	private static ArrayList<Recipe> lunchMainCourseNotSingleRecipesCache;
	private static ArrayList<Recipe> lunchSideDishRecipesCache;
	private static ArrayList<Recipe> lunchSideDesertRecipesCache;
	private static ArrayList<Recipe> dinnerStarterDishRecipesCache;
	private static ArrayList<Recipe> dinnerMainCourseSingleRecipesCache;
	private static ArrayList<Recipe> dinnerMainCourseNotSingleRecipesCache;
	private static ArrayList<Recipe> dinnerSideDishRecipesCache;
	private static ArrayList<Recipe> dinnerDesertRecipesCache;
	private static ArrayList<Recipe> snackMainCourseSingleRecipes;

	static {

		foodProviderOntology = new FoodProviderOntology();
		model = foodProviderOntology.getOntModel();
		data = foodProviderOntology.getD2rData();
		model.add(data);
	}

	/**
	 * Prefetches the data from ontolgy into main memory. Any subsequent call will return data from
	 * the faster main memory instead of the slower ontology.
	 */
	public void loadOntologyDataIntoMemory() {
		generateBreakfastMeals(0);
		generateLunchMeals(0);
		generateDinnerMeals(0);
		generateSnackMeals(0);
	}

	public Breakfast generateSingleBreakfastMeal() {
		return generateBreakfastMeals(1).get(0);
	}

	public ArrayList<Breakfast> generateBreakfastMeals(int numberOfSolutions) {

		ArrayList<Breakfast> breakfastList = new ArrayList<Breakfast>();

		Random rand = new Random();

		return breakfastList;
	}

	public Lunch generateSingleLunchMeal() {
		return generateLunchMeals(1).get(0);
	}

	public ArrayList<Lunch> generateLunchMeals(int numberOfSolutions) {

		ArrayList<Lunch> lunchList = new ArrayList<Lunch>();

		Random rand = new Random();

		return lunchList;
	}

	public Dinner generateSingleDinnerMeal() {
		return generateDinnerMeals(1).get(0);
	}

	public ArrayList<Dinner> generateDinnerMeals(int numberOfSolutions) {
		ArrayList<Dinner> dinnerList = new ArrayList<Dinner>();

		Random rand = new Random();

		return dinnerList;
	}

	public Snack generateSingleSnackMeal() {
		return generateSnackMeals(1).get(0);
	}

	public ArrayList<Snack> generateSnackMeals(int numberOfSolutions) {
		ArrayList<Snack> snackList = new ArrayList<Snack>();

		Random rand = new Random();

		ArrayList<Recipe> mainCourseSingleList = this.getAllSnackMainCourseSingleRecipes();

		int mainCourseSingleListSize = mainCourseSingleList.size();
		for (int i = 0; i < numberOfSolutions; i++) {
			Snack snack = new Snack();
			snack.setMainCourse(new MainCourse(mainCourseSingleList.get(rand.nextInt(mainCourseSingleListSize))));
			snackList.add(snack);
		}

		return snackList;
	}

	public ArrayList<Recipe> getAllBreakfastMainCourseSingleRecipes() {
		if (breakfastMainCourseSingleRecipesCache != null) {
			return breakfastMainCourseSingleRecipesCache;
		}

		breakfastMainCourseSingleRecipesCache = getAllRecipes(breakfast, mainCourseSingle);
		return breakfastMainCourseSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllBreakfastMainCourseNotSingleRecipes() {
		if (breakfastMainCourseNotSingleRecipesCache != null) {
			return breakfastMainCourseNotSingleRecipesCache;
		}

		breakfastMainCourseNotSingleRecipesCache = getAllRecipes(breakfast, mainCourseNotSingle);
		return breakfastMainCourseNotSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllBreakfastSideDishRecipes() {
		if (breakfastSideDishRecipesCache != null) {
			return breakfastSideDishRecipesCache;
		}

		breakfastSideDishRecipesCache = getAllRecipes(breakfast, sideDish);
		return breakfastSideDishRecipesCache;
	}

	public ArrayList<Recipe> getAllBreakfastDesert() {
		if (breakfastDesertCache != null) {
			return breakfastDesertCache;
		}

		breakfastDesertCache = getAllRecipes(breakfast, desert);
		return breakfastDesertCache;
	}

	public ArrayList<Recipe> getAllLunchStarterDishRecipes() {
		if (lunchStarterDishRecipesCache != null) {
			return lunchStarterDishRecipesCache;
		}

		lunchStarterDishRecipesCache = getAllRecipes(lunch, starterDish);
		return lunchStarterDishRecipesCache;
	}

	public ArrayList<Recipe> getAllLunchMainCourseSingleRecipes() {
		if (lunchMainCourseSingleRecipesCache != null) {
			return lunchMainCourseSingleRecipesCache;
		}

		lunchMainCourseSingleRecipesCache = getAllRecipes(lunch, mainCourseSingle);
		return lunchMainCourseSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllLunchMainCourseNotSingleRecipes() {
		if (lunchMainCourseNotSingleRecipesCache != null) {
			return lunchMainCourseNotSingleRecipesCache;
		}

		lunchMainCourseNotSingleRecipesCache = getAllRecipes(lunch, mainCourseNotSingle);
		return lunchMainCourseNotSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllLunchSideDishRecipes() {
		if (lunchSideDishRecipesCache != null) {
			return lunchSideDishRecipesCache;
		}

		lunchSideDishRecipesCache = getAllRecipes(lunch, sideDish);
		return lunchSideDishRecipesCache;
	}

	public ArrayList<Recipe> getAllLunchDesertRecipes() {
		if (lunchSideDesertRecipesCache != null) {
			return lunchSideDesertRecipesCache;
		}

		lunchSideDesertRecipesCache = getAllRecipes(lunch, desert);
		return lunchSideDesertRecipesCache;
	}

	public ArrayList<Recipe> getAllDinnerStarterDishRecipes() {
		if (dinnerStarterDishRecipesCache != null) {
			return dinnerStarterDishRecipesCache;
		}

		dinnerStarterDishRecipesCache = getAllRecipes(dinner, starterDish);
		return dinnerStarterDishRecipesCache;
	}

	public ArrayList<Recipe> getAllDinnerMainCourseSingleRecipes() {
		if (dinnerMainCourseSingleRecipesCache != null) {
			return dinnerMainCourseSingleRecipesCache;
		}

		dinnerMainCourseSingleRecipesCache = getAllRecipes(dinner, mainCourseSingle);
		return dinnerMainCourseSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllDinnerMainCourseNotSingleRecipes() {
		if (dinnerMainCourseNotSingleRecipesCache != null) {
			return dinnerMainCourseNotSingleRecipesCache;
		}

		dinnerMainCourseNotSingleRecipesCache = getAllRecipes(dinner, mainCourseNotSingle);
		return dinnerMainCourseNotSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllDinnerSideDishRecipes() {
		if (dinnerSideDishRecipesCache != null) {
			return dinnerSideDishRecipesCache;
		}

		dinnerSideDishRecipesCache = getAllRecipes(dinner, sideDish);
		return dinnerSideDishRecipesCache;
	}

	public ArrayList<Recipe> getAllDinnerDesertRecipes() {
		if (dinnerDesertRecipesCache != null) {
			return dinnerDesertRecipesCache;
		}

		dinnerDesertRecipesCache = getAllRecipes(dinner, desert);
		return dinnerDesertRecipesCache;
	}

	public ArrayList<Recipe> getAllSnackMainCourseSingleRecipes() {
		if (snackMainCourseSingleRecipes != null) {
			return snackMainCourseSingleRecipes;
		}

		snackMainCourseSingleRecipes = getAllRecipes(snack, mainCourseSingle);
		return snackMainCourseSingleRecipes;
	}

	private ArrayList<Recipe> getAllRecipes(String mealType, String dishType) {
		ArrayList<Recipe> list = new ArrayList<Recipe>();
		String queryString = FoodProviderOntology.PREFIX
				+ "SELECT DISTINCT ?recipe ?recipeId?name ?descr ?mealType ?p ?f ?c ?e ?i ?s ?ca ?va ?vb ?vc ?vd "
				+ "WHERE {?recipe rdf:type nutritionassesment:Recipe." + "?recipe foodprovider:recipeHasName ?name."
				+ "?recipe foodprovider:recipeHasDescription ?descr." + "?recipe foodprovider:recipeHasId ?recipeId."
				+ "?recipe foodprovider:recipeHasProteins_g ?p." + "?recipe foodprovider:recipeHasFats_g ?f."
				+ "?recipe foodprovider:recipeHasCarbs_g ?c." + "?recipe foodprovider:recipeHasEnergy_kcal ?e."
				+ "?recipe foodprovider:recipeHasIron_mg ?i." + "?recipe foodprovider:recipeHasSodium_mg ?s."
				+ "?recipe foodprovider:recipeHasCalcium_mg ?ca." + "?recipe foodprovider:recipeHasVitaminA_ug ?va."
				+ "?recipe foodprovider:recipeHasVitaminB6_mg ?vb." + "?recipe foodprovider:recipeHasVitaminC_mg ?vc."
				+ "?recipe foodprovider:recipeHasVitaminD_ug ?vd."
				+ "?recipe foodprovider:recipeHasMealType foodprovider:" + mealType + ". "
				+ "?recipe foodprovider:recipeHasDishType foodprovider:" + dishType + " }";

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		int counter = 0;
		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("recipe") != null) {
				counter++;
				Recipe recipe = new Recipe();

				recipe.setName(row.getLiteral("name").getString());
				recipe.setDescription(row.getLiteral("descr").getString());
				recipe.setRecipeId(row.getLiteral("recipeId").getInt());
				recipe.setProteins(row.getLiteral("p").getFloat());
				recipe.setLipids(row.getLiteral("f").getFloat());
				recipe.setEnergy(row.getLiteral("e").getFloat());
				recipe.setCarbohydrates(row.getLiteral("c").getFloat());
				recipe.setIron(row.getLiteral("i").getFloat());
				recipe.setCalcium(row.getLiteral("ca").getFloat());
				recipe.setSodium(row.getLiteral("s").getFloat());
				recipe.setVitA(row.getLiteral("va").getFloat());
				recipe.setVitB(row.getLiteral("vb").getFloat());
				recipe.setVitC(row.getLiteral("vc").getFloat());
				recipe.setVitD(row.getLiteral("vd").getFloat());
				list.add(recipe);
			}
		}
		System.out.println(mealType + " " + dishType + " ->nr of rows returned:" + counter);
		return list;
	}

	public ArrayList<Menu> getAllMenus(MealType mealType, DishType dishType) {
		ArrayList<Menu> menuList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX
				+ "SELECT DISTINCT ?menu ?menuId ?mealV ?mealVId ?mealVMealType ?dish ?dishId "
				+ "?foodProv ?foodProvId ?foodProvName ?geoArea ?geoAreaId ?geoCountry ?geoCity ?geoRegion "
				+ "?recipe ?recId ?recName ?recDescr ?recDishType "
				+ "?recPro ?recLip ?recCar ?recEne ?recCal ?recIro ?recSod ?recVa ?recVb ?recVc ?recVd "
				// Menu info
				+ "WHERE {?menu rdf:type nutritionassesment:Menu." // menu
				+ "?menu foodprovider:menuHasId ?menuId." // menu id
				+ "?menu foodprovider:menuHasMealType foodprovider:"
				+ mealType
				+ ". "
				+ "?menu foodprovider:menuHasMealVariant ?mealV." // mealVariant
				// Food provider & Geographical Area of food provider
				+ "?menu foodprovider:menuHasFoodProvider ?foodProv."
				+ "?foodProv foodprovider:foodProviderHasGeographicalArea ?geoArea."
				+ "?foodProv foodprovider:foodProviderHasID ?foodProvId."
				+ "?foodProv foodprovider:foodProviderHasName ?foodProvName."
				+ "?geoArea foodprovider:geographicalAreaHasId ?geoAreaId."
				+ "?geoArea foodprovider:geographicalAreaHasCity ?geoCity."
				+ "?geoArea foodprovider:geographicalAreaHasCountry ?geoCountry."
				+ "?geoArea foodprovider:geographicalAreaHasRegion ?geoRegion."
				// Menu meal variant, dish, recipe
				+ "?mealV foodprovider:mealVariantHasId ?mealVId." // mealVId
				+ "?mealV foodprovider:mealVariantHasDish ?dish."
				+ "?mealV foodprovider:mealVariantHasMealType ?mealVMealType."
				+ "?dish foodprovider:dishHasId ?dishId." // dishId
				+ "?dish foodprovider:dishHasDishType foodprovider:" + dishType + ". "// dishType
				+ "?dish foodprovider:dishHasRecipe ?recipe."
				// Recipe data + nutrients
				+ "?recipe foodprovider:recipeHasId ?recId." // Id
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

		// String queryString1 = FoodProviderOntology.PREFIX + "SELECT DISTINCT ?dish ?dishId "
		// + "WHERE {?dish rdf:type nutritionassesment:Dish." // aaa
		// + "?dish foodprovider:dishHasId ?dishId." // bbb
		// + "?dish foodprovider:dishHasDishType foodprovider:Dessert}"; // bbb
		// + "{?dish foodprovider:dishHasDishType foodprovider:Dessert.} UNION" // bbb
		// + "{?dish foodprovider:dishHasDishType foodprovider:Side_Dish}}"; // bbb

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		int counter = 0;
		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			System.out.println(row.getLiteral("mealVMealType").toString() + ", " + row.getLiteral("mealVId").getLong());
			if (row.getResource("menu") != null) {
				counter++;
				Menu menu = new Menu();

				// recipe.setName(row.getLiteral("name").getString());
				// recipe.setDescription(row.getLiteral("descr").getString());
				menuList.add(menu);
			}
		}
		System.out.println(mealType + " " + dishType + " ->nr of rows returned:" + counter);
		return menuList;
	}
}
