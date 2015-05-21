package org.licenta.d4elders.main;

import org.licenta.d4elders.dal.BusinessLogic;
import org.licenta.d4elders.model.MealType;
import org.licenta.d4elders.model.dish.DishType;

public class BusinessLogicTester {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		BusinessLogic bl = new BusinessLogic();
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Time for ontology load: " + elapsedTime);
		startTime = System.currentTimeMillis();
//		bl.getAllRecipes();
		 bl.loadOntologyDataIntoMemory();
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
