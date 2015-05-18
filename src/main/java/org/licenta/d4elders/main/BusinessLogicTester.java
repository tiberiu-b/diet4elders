package org.licenta.d4elders.main;

import org.licenta.d4elders.dal.BusinessLogicNew;
import org.licenta.d4elders.model.MealType;
import org.licenta.d4elders.model.dish.DishType;

public class BusinessLogicTester {

	public static void main(String[] args) {
		BusinessLogicNew bl = new BusinessLogicNew();
		bl.getAllMenus(MealType.Breakfast, DishType.Side_Dish);
	}
}
