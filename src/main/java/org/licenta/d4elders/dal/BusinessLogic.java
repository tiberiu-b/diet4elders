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

	public BusinessLogic() {

		foodProviderOntology = new FoodProviderOntology();
		model = foodProviderOntology.getOntModel();
		data = foodProviderOntology.getD2rData();
		model.add(data);
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
		return getAllRecipes(breakfast, mainCourseSingle);
	}

	public ArrayList<Recipe> getAllBreakfastMainCourseNotSingleRecipes() {
		return getAllRecipes(breakfast, mainCourseNotSingle);
	}

	public ArrayList<Recipe> getAllBreakfastSideDishRecipes() {
		return getAllRecipes(breakfast, sideDish);
	}

	public ArrayList<Recipe> getAllBreakfastDesert() {
		return getAllRecipes(breakfast, desert);
	}

	public ArrayList<Recipe> getAllLunchStarterDishRecipes() {
		return getAllRecipes(lunch, starterDish);
	}

	public ArrayList<Recipe> getAllLunchMainCourseSingleRecipes() {
		return getAllRecipes(lunch, mainCourseSingle);
	}

	public ArrayList<Recipe> getAllLunchMainCourseNotSingleRecipes() {
		return getAllRecipes(lunch, mainCourseNotSingle);
	}

	public ArrayList<Recipe> getAllLunchSideDishRecipes() {
		return getAllRecipes(lunch, sideDish);
	}

	public ArrayList<Recipe> getAllLunchDesertRecipes() {
		return getAllRecipes(lunch, desert);
	}

	public ArrayList<Recipe> getAllDinnerStarterDishRecipes() {
		return getAllRecipes(dinner, starterDish);
	}

	public ArrayList<Recipe> getAllDinnerMainCourseSingleRecipes() {
		return getAllRecipes(dinner, mainCourseSingle);
	}

	public ArrayList<Recipe> getAllDinnerMainCourseNotSingleRecipes() {
		return getAllRecipes(dinner, mainCourseNotSingle);
	}

	public ArrayList<Recipe> getAllDinnerSideDishRecipes() {
		return getAllRecipes(dinner, sideDish);
	}

	public ArrayList<Recipe> getAllDinnerDesertRecipes() {
		return getAllRecipes(dinner, desert);
	}

	public ArrayList<Recipe> getAllSnackMainCourseSingleRecipes() {
		return getAllRecipes(snack, mainCourseSingle);
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
