package org.licenta.d4elders.dal;

import java.util.ArrayList;
import org.licenta.d4elders.dal.BusinessLogic;

public class BusinessLogicTester {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		BusinessLogic bl = BusinessLogic.getInstance();
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Time for ontology load: " + elapsedTime);
		startTime = System.currentTimeMillis();
		// ArrayList<Recipe> recipeList = bl.getAllRecipes();
		// ArrayList<FoodProviderPackage> packageList = bl.getAllFoodPackages();
		// bl.getAllMenusHelper();
		// bl.getAllMealVariants();
		// bl.getAllMenus();
		// bl.getAllMenus();
		// bl.getAllMenu2();
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println("Time for cache query: " + elapsedTime);
	}
}
