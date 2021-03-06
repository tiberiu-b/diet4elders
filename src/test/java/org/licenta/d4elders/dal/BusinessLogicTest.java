package org.licenta.d4elders.dal;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.SortedSet;

import org.junit.Test;
import org.licenta.d4elders.main.InitialSolutionsGenerator;
import org.licenta.d4elders.model.Solution;
import org.licenta.d4elders.model.meal.Breakfast;
import org.licenta.d4elders.model.meal.Dinner;
import org.licenta.d4elders.model.meal.Lunch;
import org.licenta.d4elders.model.meal.Snack;

public class BusinessLogicTest {

	/**
	 * Test whether the second time the call is much faster (at least 10 times), due to the cached arrays.
	 * Even in different instances, since they are declared static.
	 */
	@Test
	public void testCache() {
		long timeStamp1, timeStamp2, timeStamp3;

		timeStamp1 = System.currentTimeMillis();
		queryOntology(new BusinessLogic());
		timeStamp2 = System.currentTimeMillis();
		queryOntology(new BusinessLogic());
		timeStamp3 = System.currentTimeMillis();

		long duration1 = timeStamp2 - timeStamp1;
		long duration2 = timeStamp3 - timeStamp2;

		System.out.println("1 -> " + duration1);
		System.out.println("2 -> " + duration2);

		assertTrue(duration1 > 10 * duration2);
	}

	private void queryOntology(BusinessLogic bl){
		final int numberOfSolutions = 10;
		bl.generateBreakfastMeals(numberOfSolutions);
		bl.generateLunchMeals(numberOfSolutions);
		bl.generateDinnerMeals(numberOfSolutions);
		bl.generateSnackMeals(numberOfSolutions * 2);
	}

}
