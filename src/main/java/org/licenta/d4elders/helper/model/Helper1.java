package org.licenta.d4elders.helper.model;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Helper1 {

	private MySQLAccess dao;
	private PrintWriter out;

	public static void main(String[] args) throws Exception {
		Helper1 h = new Helper1();
		h.generateScript();
	}

	public void generateScript() throws Exception {
		dao = new MySQLAccess();
		ArrayList<Integer> recipeIdList = dao.getIdsOfAllRecipes();
		ArrayList<String> nutrientIdList = dao.getIdsOfAllNutrients();
		ArrayList<NutrientRecipeRelation> nutrRecipeList;

		out = new PrintWriter("nutrient_recipe_relation.txt");
		out.println("insert into nutrient_recipe_relation values ");

		for (Integer recipeId : recipeIdList) {

			System.out.println("Recipe " + recipeId);

			nutrRecipeList = new ArrayList<>();

			for (String nutr : nutrientIdList) {
				NutrientRecipeRelation nrr = new NutrientRecipeRelation();
				nrr.setRecipeId(recipeId);
				nrr.setNutr_no(nutr);
				float value = dao.getTotalNutrientValueOfAllIngredientOfARecipe(recipeId, nutr);
				nrr.setValue(value);
				nutrRecipeList.add(nrr);
			}

			for (NutrientRecipeRelation nrr : nutrRecipeList) {

				String x = "(" + nrr.getRecipeId() + ",'" + nrr.getNutr_no() + "','" + nrr.getValue() + "'),";
				out.println(x);

			}
		}
		out.close();
	}
}
