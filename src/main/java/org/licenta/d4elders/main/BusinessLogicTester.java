package org.licenta.d4elders.main;

import java.util.ArrayList;

import org.licenta.d4elders.dal.BusinessLogic;
import org.licenta.d4elders.model.*;
import org.licenta.d4elders.model.dish.*;

public class BusinessLogicTester {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		BusinessLogic bl = new BusinessLogic();
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Time for ontology load: " + elapsedTime);
		startTime = System.currentTimeMillis();
		// ArrayList<Recipe> recipeList = bl.getAllRecipes();
		 bl.loadOntologyDataIntoMemory();
		//ArrayList<FoodProviderPackage> packageList = bl.getAllFoodPackages();
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
