package org.licenta.d4elders.main;

import java.util.ArrayList;

import org.licenta.d4elders.dal.BusinessLogic;
import org.licenta.d4elders.model.meal.Breakfast;
import org.licenta.d4elders.model.meal.DayMeal;

public class MainTest1 {

	public static void main(String[] args) {
		long start, end;
		start = System.currentTimeMillis();

		BusinessLogic bl = new BusinessLogic();
		end = System.currentTimeMillis();
		System.out.println("Ontology load time: " + (end - start));
		// bl.getAllGeogrAreas();
		bl.getAllFoodProviders();
		// bl.getAllMenus();
		// bl.getAllMealVariants();
		// bl.getAllDishes();
		// bl.getAllRecipes("Breakfast", "Dessert");
		// BusinessLogic.query7();
		// BusinessLogic.getAllBreakfastSideDishRecipes();
		// BusinessLogic.generateLunchMeals(100);
		// ArrayList<Breakfast> brList = bl.generateBreakfastMeals(20);
		// for(Breakfast b : brList) {
		// System.out.println(b.getMainCourse().getRecipeMain().getName());
		// }

		end = System.currentTimeMillis();
		System.out.println("Program time is: " + (end - start));
	}
}
