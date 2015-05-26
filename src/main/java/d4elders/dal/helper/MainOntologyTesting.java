package d4elders.dal.helper;

import com.hp.hpl.jena.rdf.model.Model;

import d4elders.ontology.FoodProviderOntology;

public class MainOntologyTesting {
	private static FoodProviderOntology foodProviderOntology;
	private static Model model;
	private static Model data;

	public static void main1(String[] args) {

		long start = System.currentTimeMillis();

		foodProviderOntology = new FoodProviderOntology();
		model = foodProviderOntology.getOntModel();
		data = foodProviderOntology.getD2rData();
		model.add(data);
		// query();
		// query2();
		// query3();
		// query4();
		// query5();
		// query6();
		query7();
		// query8();
		long end = System.currentTimeMillis();
		System.out.println("Time is: " + (end - start));
	}

	private static void query() {
		String queryString = FoodProviderOntology.PREFIX + "SELECT DISTINCT ?mealtype ?id "
				+ "WHERE {?mealtype rdf:type foodprovider:Breakfast." + "?mealtype foodprovider:mealTypeHasId ?id}";

		foodProviderOntology.queryModel(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

	}

	private static void query2() {
		String queryString = FoodProviderOntology.PREFIX + "SELECT DISTINCT ?dishtype ?id "
				+ "WHERE {?dishtype rdf:type foodprovider:Side_Dish." + "?dishtype foodprovider:dishHasId ?id}";

		foodProviderOntology.queryModel(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

	}

	private static void query3() {
		String queryString = FoodProviderOntology.PREFIX + "SELECT DISTINCT ?recipe ?id ?name ?d ?dt ?s ?mt "
				+ "WHERE {?recipe rdf:type nutritionassesment:Recipe." + "?recipe foodprovider:recipeHasId ?id."
				+ "?recipe foodprovider:recipeHasName ?name." + "?recipe foodprovider:recipeHasDescription ?d."
				+ "?recipe foodprovider:recipeHasDishType ?dt." + "?recipe foodprovider:recipeHasNumberOfServings ?s."
				+ "?recipe foodprovider:recipeHasMealType ?mt}";

		foodProviderOntology.queryModel(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

	}

	private static void query4() {
		String queryString = FoodProviderOntology.PREFIX + "SELECT DISTINCT ?basicfood ?id "
				+ "WHERE {?basicfood rdf:type foodprovider:Ingredient."
				+ "?basicfood foodprovider:hasFoodGroupCode ?id}";

		foodProviderOntology.queryModel(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

	}

	private static void query5() {
		String queryString = FoodProviderOntology.PREFIX + "SELECT DISTINCT ?basicfood ?id "
				+ "WHERE {?basicfood rdf:type nutritionassesment:BasicFood."
				+ "?basicfood foodprovider:hasFoodGroupCode ?id}";

		foodProviderOntology.queryModel(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

	}

	private static void query6() {
		String queryString = FoodProviderOntology.PREFIX + "SELECT DISTINCT ?basicfood ?d "
				+ "WHERE {?basicfood rdf:type foodprovider:Ingredient."
				// + "?basicfood foodprovider:ingredientHasId 01001."
				+ "?basicfood foodprovider:ingredientHasBasicFoodType ?d}";

		foodProviderOntology.queryModel(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

	}

	private static void query7() {
		String queryString = FoodProviderOntology.PREFIX
				+ "SELECT DISTINCT ?recipe ?p ?f ?c ?e ?i ?s ?ca ?va ?vb ?vc ?vd "
				+ "WHERE {?recipe rdf:type nutritionassesment:Recipe." + "?recipe foodprovider:recipeHasProteins_g ?p."
				+ "?recipe foodprovider:recipeHasFats_g ?f." + "?recipe foodprovider:recipeHasCarbs_g ?c."
				+ "?recipe foodprovider:recipeHasEnergy_kcal ?e." + "?recipe foodprovider:recipeHasIron_mg ?i."
				+ "?recipe foodprovider:recipeHasSodium_mg ?s." + "?recipe foodprovider:recipeHasCalcium_mg ?ca."
				+ "?recipe foodprovider:recipeHasVitaminA_ug ?va." + "?recipe foodprovider:recipeHasVitaminB6_mg ?vb."
				+ "?recipe foodprovider:recipeHasVitaminC_mg ?vc." + "?recipe foodprovider:recipeHasVitaminD_ug ?vd}";

		foodProviderOntology.queryModel(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

	}

	private static void query8() {
		String queryString = FoodProviderOntology.PREFIX + "SELECT DISTINCT ?recipe ?ingredient ?d "
				+ "WHERE {?recipe rdf:type nutritionassesment:Recipe." + "?recipe foodprovider:recipeHasId 3111."
				+ "?recipe foodprovider:recipeHasIngredient ?ingredient."
				+ "?ingredient foodprovider:ingredientHasBasicFoodType ?d}";

		foodProviderOntology.queryModel(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

	}
}
