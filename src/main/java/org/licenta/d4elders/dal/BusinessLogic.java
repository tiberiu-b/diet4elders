package org.licenta.d4elders.dal;

import java.util.ArrayList;
import java.util.Random;

import org.licenta.d4elders.model.*;
import org.licenta.d4elders.model.dish.*;
import org.licenta.d4elders.model.meal.*;
import org.licenta.d4elders.ontology.FoodProviderOntology;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.resultset.ResultsFormat;

public class BusinessLogic {
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
	 * The arrays are fetched once and stored in cache.
	 * Subsequent calls return the cached arrays.
	 * Since the elements in the ontology are constant during a
	 * run of the program, there won't be any misses.
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

	public Breakfast generateSingleBreakfastMeal(){
		return generateBreakfastMeals(1).get(0);
	}

	public ArrayList<Breakfast> generateBreakfastMeals(int numberOfSolutions) {

		ArrayList<Breakfast> breakfastList = new ArrayList<Breakfast>();

		Random rand = new Random();

		ArrayList<Recipe> mainCourseSingleList = this.getAllBreakfastMainCourseSingleRecipes();
		ArrayList<Recipe> mainCourseNotSingleList = this.getAllBreakfastMainCourseNotSingleRecipes();
		ArrayList<Recipe> sideDishList = new ArrayList<Recipe>();
		if (mainCourseNotSingleList.size() != 0)
			sideDishList = this.getAllBreakfastSideDishRecipes();
		ArrayList<Recipe> desertList = this.getAllBreakfastDesert();

		int mainCourseSingleListSize = mainCourseSingleList.size();
		int mainCourseNotSingleListSize = mainCourseNotSingleList.size();
		int sideDishListSize = sideDishList.size();
		int desertListSize = desertList.size();

		float MainCourseSingleRation = (float) mainCourseSingleListSize
				/ (mainCourseSingleListSize + mainCourseNotSingleListSize);
		int numberOfMealWithMainCourseSingle = (int) (MainCourseSingleRation * numberOfSolutions);

		if (sideDishListSize == 0)
			numberOfMealWithMainCourseSingle = numberOfSolutions;

		for (int i = 0; i < numberOfSolutions; i++) {
			Breakfast br = new Breakfast();
			br.setDesert(new Desert(desertList.get(rand.nextInt(desertListSize))));
			if (i < numberOfMealWithMainCourseSingle) {
				// breakfast meal with main course single
				br.setMainCourse(new MainCourse(mainCourseSingleList.get(rand.nextInt(mainCourseSingleListSize))));
			} else {
				// breakfast meal with main course recipeMain + sideDish
				br.setMainCourse(new MainCourse(mainCourseSingleList.get(rand.nextInt(mainCourseNotSingleListSize)),
						sideDishList.get(rand.nextInt(sideDishListSize))));
			}
			breakfastList.add(br);
		}

		return breakfastList;
	}

	public Lunch generateSingleLunchMeal(){
		return generateLunchMeals(1).get(0);
	}

	public ArrayList<Lunch> generateLunchMeals(int numberOfSolutions) {

		ArrayList<Lunch> lunchList = new ArrayList<Lunch>();

		Random rand = new Random();

		ArrayList<Recipe> starterDishList = this.getAllLunchStarterDishRecipes();
		ArrayList<Recipe> mainCourseSingleList = this.getAllLunchMainCourseSingleRecipes();
		ArrayList<Recipe> mainCourseNotSingleList = this.getAllLunchMainCourseNotSingleRecipes();
		ArrayList<Recipe> sideDishList = new ArrayList<Recipe>();
		if (mainCourseNotSingleList.size() != 0)
			sideDishList = this.getAllLunchSideDishRecipes();
		ArrayList<Recipe> desertList = this.getAllLunchDesertRecipes();

		int starterDishListSize = starterDishList.size();
		int mainCourseSingleListSize = mainCourseSingleList.size();
		int mainCourseNotSingleListSize = mainCourseNotSingleList.size();
		int sideDishListSize = sideDishList.size();
		int desertListSize = desertList.size();

		float MainCourseSingleRation = (float) mainCourseSingleListSize
				/ (mainCourseSingleListSize + mainCourseNotSingleListSize);
		int numberOfMealWithMainCourseSingle = (int) (MainCourseSingleRation * numberOfSolutions);

		if (sideDishListSize == 0)
			numberOfMealWithMainCourseSingle = numberOfSolutions;

		for (int i = 0; i < numberOfSolutions; i++) {
			Lunch lunch = new Lunch();
			lunch.setStarterDish(new StarterDish(starterDishList.get(rand.nextInt(starterDishListSize))));
			if (i < numberOfMealWithMainCourseSingle) {
				// lunch meal with main course single
				lunch.setMainCourse(new MainCourse(mainCourseSingleList.get(rand.nextInt(mainCourseSingleListSize))));
			} else {
				// lunch meal with main course recipeMain + sideDish
				lunch.setMainCourse(new MainCourse(mainCourseSingleList.get(rand.nextInt(mainCourseNotSingleListSize)),
						sideDishList.get(rand.nextInt(sideDishListSize))));
			}
			lunch.setDesert(new Desert(desertList.get(rand.nextInt(desertListSize))));
			lunchList.add(lunch);
		}

		return lunchList;
	}

	public Dinner generateSingleDinnerMeal(){
		return generateDinnerMeals(1).get(0);
	}

	public ArrayList<Dinner> generateDinnerMeals(int numberOfSolutions) {
		ArrayList<Dinner> dinnerList = new ArrayList<Dinner>();

		Random rand = new Random();

		ArrayList<Recipe> starterDishList = this.getAllDinnerStarterDishRecipes();
		ArrayList<Recipe> mainCourseSingleList = this.getAllDinnerMainCourseSingleRecipes();
		ArrayList<Recipe> mainCourseNotSingleList = this.getAllDinnerMainCourseNotSingleRecipes();
		ArrayList<Recipe> sideDishList = new ArrayList<Recipe>();
		if (mainCourseNotSingleList.size() != 0)
			sideDishList = this.getAllDinnerSideDishRecipes();
		ArrayList<Recipe> desertList = this.getAllDinnerDesertRecipes();

		int starterDishListSize = starterDishList.size();
		int mainCourseSingleListSize = mainCourseSingleList.size();
		int mainCourseNotSingleListSize = mainCourseNotSingleList.size();
		int sideDishListSize = sideDishList.size();
		int desertListSize = desertList.size();

		float MainCourseSingleRation = (float) mainCourseSingleListSize
				/ (mainCourseSingleListSize + mainCourseNotSingleListSize);
		int numberOfMealWithMainCourseSingle = (int) (MainCourseSingleRation * numberOfSolutions);

		if (sideDishListSize == 0)
			numberOfMealWithMainCourseSingle = numberOfSolutions;

		for (int i = 0; i < numberOfSolutions; i++) {
			Dinner dinner = new Dinner();
			dinner.setStarterDish(new StarterDish(starterDishList.get(rand.nextInt(starterDishListSize))));
			if (i < numberOfMealWithMainCourseSingle) {
				// dinner meal with main course single
				dinner.setMainCourse(new MainCourse(mainCourseSingleList.get(rand.nextInt(mainCourseSingleListSize))));
			} else {
				// dinner meal with main course recipeMain + sideDish
				dinner.setMainCourse(new MainCourse(
						mainCourseSingleList.get(rand.nextInt(mainCourseNotSingleListSize)), sideDishList.get(rand
								.nextInt(sideDishListSize))));
			}
			dinner.setDesert(new Desert(desertList.get(rand.nextInt(desertListSize))));
			dinnerList.add(dinner);
		}

		return dinnerList;
	}

	public Snack generateSingleSnackMeal(){
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
		if (breakfastMainCourseSingleRecipesCache != null){
			return breakfastMainCourseSingleRecipesCache;
		}

		breakfastMainCourseSingleRecipesCache = getAllRecipes(breakfast, mainCourseSingle);
		return breakfastMainCourseSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllBreakfastMainCourseNotSingleRecipes() {
		if (breakfastMainCourseNotSingleRecipesCache != null){
			return breakfastMainCourseNotSingleRecipesCache;
		}

		breakfastMainCourseNotSingleRecipesCache = getAllRecipes(breakfast, mainCourseNotSingle);
		return breakfastMainCourseNotSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllBreakfastSideDishRecipes() {
		if (breakfastSideDishRecipesCache != null){
			return breakfastSideDishRecipesCache;
		}

		breakfastSideDishRecipesCache = getAllRecipes(breakfast, sideDish);
		return breakfastSideDishRecipesCache;
	}

	public ArrayList<Recipe> getAllBreakfastDesert() {
		if (breakfastDesertCache != null){
			return breakfastDesertCache;
		}

		breakfastDesertCache = getAllRecipes(breakfast, desert);
		return breakfastDesertCache;
	}

	public ArrayList<Recipe> getAllLunchStarterDishRecipes() {
		if (lunchStarterDishRecipesCache != null){
			return lunchStarterDishRecipesCache;
		}

		lunchStarterDishRecipesCache = getAllRecipes(lunch, starterDish);
		return lunchStarterDishRecipesCache;
	}

	public ArrayList<Recipe> getAllLunchMainCourseSingleRecipes() {
		if (lunchMainCourseSingleRecipesCache != null){
			return lunchMainCourseSingleRecipesCache;
		}

		lunchMainCourseSingleRecipesCache = getAllRecipes(lunch, mainCourseSingle);
		return lunchMainCourseSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllLunchMainCourseNotSingleRecipes() {
		if (lunchMainCourseNotSingleRecipesCache != null){
			return lunchMainCourseNotSingleRecipesCache;
		}

		lunchMainCourseNotSingleRecipesCache = getAllRecipes(lunch, mainCourseNotSingle);
		return lunchMainCourseNotSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllLunchSideDishRecipes() {
		if (lunchSideDishRecipesCache != null){
			return lunchSideDishRecipesCache;
		}

		lunchSideDishRecipesCache = getAllRecipes(lunch, sideDish);
		return lunchSideDishRecipesCache;
	}

	public ArrayList<Recipe> getAllLunchDesertRecipes() {
		if (lunchSideDesertRecipesCache != null){
			return lunchSideDesertRecipesCache;
		}

		lunchSideDesertRecipesCache = getAllRecipes(lunch, desert);
		return lunchSideDesertRecipesCache;
	}

	public ArrayList<Recipe> getAllDinnerStarterDishRecipes() {
		if (dinnerStarterDishRecipesCache != null){
			return dinnerStarterDishRecipesCache;
		}

		dinnerStarterDishRecipesCache = getAllRecipes(dinner, starterDish);
		return dinnerStarterDishRecipesCache;
	}

	public ArrayList<Recipe> getAllDinnerMainCourseSingleRecipes() {
		if (dinnerMainCourseSingleRecipesCache != null){
			return dinnerMainCourseSingleRecipesCache;
		}

		dinnerMainCourseSingleRecipesCache = getAllRecipes(dinner, mainCourseSingle);
		return dinnerMainCourseSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllDinnerMainCourseNotSingleRecipes() {
		if (dinnerMainCourseNotSingleRecipesCache != null){
			return dinnerMainCourseNotSingleRecipesCache;
		}

		dinnerMainCourseNotSingleRecipesCache = getAllRecipes(dinner, mainCourseNotSingle);
		return dinnerMainCourseNotSingleRecipesCache;
	}

	public ArrayList<Recipe> getAllDinnerSideDishRecipes() {
		if (dinnerSideDishRecipesCache != null){
			return dinnerSideDishRecipesCache;
		}

		dinnerSideDishRecipesCache = getAllRecipes(dinner, sideDish);
		return dinnerSideDishRecipesCache;
	}

	public ArrayList<Recipe> getAllDinnerDesertRecipes() {
		if (dinnerDesertRecipesCache != null){
			return dinnerDesertRecipesCache;
		}

		dinnerDesertRecipesCache = getAllRecipes(dinner, desert);
		return dinnerDesertRecipesCache;
	}

	public ArrayList<Recipe> getAllSnackMainCourseSingleRecipes() {
		if (snackMainCourseSingleRecipes != null){
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

	public void query7() {
		String queryString = FoodProviderOntology.PREFIX
				+ "SELECT DISTINCT ?recipe ?name ?mealType ?p ?f ?c ?e ?i ?s ?ca ?va ?vb ?vc ?vd "
				+ "WHERE {?recipe rdf:type nutritionassesment:Recipe." + "?recipe foodprovider:recipeHasName ?name."
				+ "?recipe foodprovider:recipeHasMealType ?mealType." + "?recipe foodprovider:recipeHasProteins_g ?p."
				+ "?recipe foodprovider:recipeHasFats_g ?f." + "?recipe foodprovider:recipeHasCarbs_g ?c."
				+ "?recipe foodprovider:recipeHasEnergy_kcal ?e." + "?recipe foodprovider:recipeHasIron_mg ?i."
				+ "?recipe foodprovider:recipeHasSodium_mg ?s." + "?recipe foodprovider:recipeHasCalcium_mg ?ca."
				+ "?recipe foodprovider:recipeHasVitaminA_ug ?va." + "?recipe foodprovider:recipeHasVitaminB6_mg ?vb."
				+ "?recipe foodprovider:recipeHasVitaminC_mg ?vc." + "?recipe foodprovider:recipeHasVitaminD_ug ?vd}";

		queryString = FoodProviderOntology.PREFIX + "SELECT * WHERE { "
				+ "?recipe rdf:type nutritionassesment:Recipe. " + "?recipe foodprovider:recipeHasName ?name. "
				// + "?recipe foodprovider:recipeHasMealType ?mealType. "
				+ " ?recipe foodprovider:recipeHasMealType foodprovider:Breakfast}";
		foodProviderOntology.queryModel(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

	}
}
