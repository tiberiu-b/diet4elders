package org.licenta.d4elders.main;

import org.licenta.d4elders.dal.BusinessLogicNew;
import org.licenta.d4elders.model.MealType;
import org.licenta.d4elders.model.dish.DishType;

public class BusinessLogicTester {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		BusinessLogicNew bl = new BusinessLogicNew();
		bl.getAllMenusHelper();
		// bl.getAllMealVariants();
		// bl.loadOntologyDataIntoMemory();
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Time for ontology load: " + elapsedTime);

		// bl.getAllMenus();
		// bl.getAllMenu2();
		startTime = System.currentTimeMillis();
		stopTime = System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		System.out.println("Time for getAllMenus query: " + elapsedTime);
	}
}
