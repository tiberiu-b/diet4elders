package org.licenta.d4elders.helper.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ibm.icu.math.BigDecimal;

public class Helper1 {

	private MySQLAccess dao;
	private PrintWriter out, out2, out3;

	public static void main(String[] args) throws Exception {
		Helper1 h = new Helper1();
		h.generateScript();
	}

	public void generateScript() {
		dao = new MySQLAccess();

		// generateDishScript(dao.getAllDishes());
		// generateGeographicalAreaScript();
		// generateFoodProviderScript();
		// generateMealVariantScript(dao.getAllDishesFromDB());
		// generateMenuScript(dao.getAllMealVariants());
		generateMenuFoodProviderRelationScript(dao.getAllMenuIds(), dao.getAllFoodProviderIds());
	}

	private void generateMenuFoodProviderRelationScript(ArrayList<Integer> allMenuIds, ArrayList<Integer> allProviderIds) {
		System.out.println("Total number of menus: " + allMenuIds.size());
		try {
			out = new PrintWriter("menu_foodprovider_relation.sql");
			out.println("insert into menu_foodprovider_relation values ");
			// assign each food menu provider about 60% of the menus available
			Random r = new Random();
			int probability = 40;
			// user only first 30 providers, it is enough
			// allProviderIds = allProviderIds.subList(1, 30);
			List<Integer> allProviderIds2 = allProviderIds.subList(0, 30);
			for (Integer provId : allProviderIds2) {
				int count = 0;
				for (Integer menuId : allMenuIds) {
					// we use probability; the probability that integer is lower than 60 is 60% for
					// example
					if (r.nextInt(100) < probability) {
						count++;
						// set cost between 10 and 50 lei
						int cost = r.nextInt(40) + 10;
						// set deliveryTime between 20 and 100 minutes
						int deliveryTime = r.nextInt(80) + 20;
						String x = "(" + menuId + ", " + provId + ", " + cost + ", " + deliveryTime + "),";
						out.println(x);
					}
				}
				System.out.println("FoodProvider: " + provId + " has " + count + " menus");
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateMenuScript(ArrayList<MealVariant> allMealVariants) {
		// MealTypeId: 0 - breakfast, 1 - lunch, 2 - dinner, 3 - snack
		// DistTypeId: 0 - starter, 1 - mainCourseSingle, 2 - dessert, 3 - mainCourseNotSingle, 4
		// -sideDish
		ArrayList<MealVariant> breakfastMainCourseList = new ArrayList<MealVariant>();
		ArrayList<MealVariant> breakfastDesertList = new ArrayList<MealVariant>();

		ArrayList<MealVariant> lunchStarterList = new ArrayList<MealVariant>();
		ArrayList<MealVariant> lunchMainCourseList = new ArrayList<MealVariant>();
		ArrayList<MealVariant> lunchDesertList = new ArrayList<MealVariant>();

		ArrayList<MealVariant> dinnerStarterList = new ArrayList<MealVariant>();
		ArrayList<MealVariant> dinnerMainCourseList = new ArrayList<MealVariant>();
		ArrayList<MealVariant> dinnerDesertList = new ArrayList<MealVariant>();

		ArrayList<MealVariant> snackMainCourseList = new ArrayList<MealVariant>();

		for (MealVariant mv : allMealVariants) {
			System.out.println(breakfastMainCourseList.size() + " br main");
			System.out.println(breakfastDesertList.size() + " br desert");
			System.out.println(dinnerMainCourseList.size() + " dinner main");
			System.out.println(dinnerDesertList.size() + " dinner desert");
			System.out.println(lunchMainCourseList.size() + " lunch main");
			System.out.println(lunchDesertList.size() + " lunch desert");
			System.out.println(snackMainCourseList.size() + " snack main");
			System.out.println("---------------------");
			switch (mv.mealTypeId) {
			case 0: // breakfast
				switch (mv.dishTypeId) {
				case 0: // starter
					// no such case
					break;
				case 1: // main course single
					breakfastMainCourseList.add(mv);
					break;
				case 2: // desert
					breakfastDesertList.add(mv);
					break;
				case 3: // main course not single
					breakfastMainCourseList.add(mv);
					break;
				case 4: // side dish
					breakfastMainCourseList.add(mv);
					break;
				}
				break;
			case 1: // lunch
				switch (mv.dishTypeId) {
				case 0: // starter
					lunchStarterList.add(mv);
					break;
				case 1: // main course single
					lunchMainCourseList.add(mv);
					break;
				case 2: // desert
					lunchDesertList.add(mv);
					break;
				case 3: // main course not single
					lunchMainCourseList.add(mv);
					break;
				case 4: // side dish
					lunchMainCourseList.add(mv);
					break;
				}
				break;
			case 2:
				switch (mv.dishTypeId) {
				case 0: // starter
					dinnerStarterList.add(mv);
					break;
				case 1: // main course single
					dinnerMainCourseList.add(mv);
					break;
				case 2: // desert
					dinnerDesertList.add(mv);
					break;
				case 3: // main course not single
					dinnerMainCourseList.add(mv);
					break;
				case 4: // side dish
					dinnerMainCourseList.add(mv);
					break;
				}
				break;
			case 3:
				snackMainCourseList.add(mv);
				break;
			}
		}

		// Generate all possible breakfast menus
		ArrayList<Breakfast> breakfastList = new ArrayList<Breakfast>();
		for (MealVariant mvMain : breakfastMainCourseList)
			for (MealVariant mvDesert : breakfastDesertList) {
				Breakfast breakfast = new Breakfast();
				breakfast.mainCourse = mvMain;
				breakfast.desert = mvDesert;
				breakfastList.add(breakfast);
			}
		// Generate all possible lunch menus
		ArrayList<Lunch> lunchList = new ArrayList<Lunch>();
		for (MealVariant mvStarter : lunchStarterList)
			for (MealVariant mvMain : lunchMainCourseList)
				for (MealVariant mvDesert : lunchDesertList) {
					Lunch lu = new Lunch();
					lu.starter = mvStarter;
					lu.mainCourse = mvMain;
					lu.desert = mvDesert;
					lunchList.add(lu);
				}
		// Generate all possible dinner menus
		ArrayList<Dinner> dinnerList = new ArrayList<Dinner>();
		for (MealVariant mvStarter : dinnerStarterList)
			for (MealVariant mvMain : dinnerMainCourseList)
				for (MealVariant mvDesert : dinnerDesertList) {
					Dinner dinner = new Dinner();
					dinner.starter = mvStarter;
					dinner.mainCourse = mvMain;
					dinner.desert = mvDesert;
					dinnerList.add(dinner);
				}
		// Generate all possible snack menus
		ArrayList<Snack> snackList = new ArrayList<Snack>();
		for (MealVariant mvMain : snackMainCourseList) {
			Snack snack = new Snack();
			snack.mainCourse = mvMain;
			snackList.add(snack);
		}

		try {
			out = new PrintWriter("menu.sql");
			out2 = new PrintWriter("menu_mealvariant_relation.sql");
			out.println("insert into menu values ");
			out2.println("insert into menu_mealvariant_relation values ");
			int menuId = 1;
			// ! DishTypeGeneral: 1 - starter, 2 - mainCourse, 3 - desert
			// Insert breakfast menus
			for (Breakfast breakfast : breakfastList) {
				String x = "(" + menuId + ", 0),";
				out.println(x);
				x = "(" + breakfast.mainCourse.mealVariantId + "," + menuId + ", 2),";
				out2.println(x);
				x = "(" + breakfast.desert.mealVariantId + "," + menuId + ", 3),";
				out2.println(x);
				menuId++;
			}
			// Insert lunch menus
			for (Lunch lunch : lunchList) {
				String x = "(" + menuId + ", 1),";
				out.println(x);
				x = "(" + lunch.starter.mealVariantId + "," + menuId + ", 1),";
				out2.println(x);
				x = "(" + lunch.mainCourse.mealVariantId + "," + menuId + ", 2),";
				out2.println(x);
				x = "(" + lunch.desert.mealVariantId + "," + menuId + ", 3),";
				out2.println(x);
				menuId++;
			}
			// Insert dinner menus
			for (Dinner dinner : dinnerList) {
				String x = "(" + menuId + ", 2),";
				out.println(x);
				x = "(" + dinner.starter.mealVariantId + "," + menuId + ", 1),";
				out2.println(x);
				x = "(" + dinner.mainCourse.mealVariantId + "," + menuId + ", 2),";
				out2.println(x);
				x = "(" + dinner.desert.mealVariantId + "," + menuId + ", 3),";
				out2.println(x);
				menuId++;
			}
			// Insert snack menus
			for (Snack snack : snackList) {
				String x = "(" + menuId + ", 3),";
				out.println(x);
				x = "(" + snack.mainCourse.mealVariantId + "," + menuId + ", 2),";
				out2.println(x);
				menuId++;
			}
			out.close();
			out2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateMealVariantScript(ArrayList<Dish> dishList) {
		ArrayList<Dish> dishSingleList = new ArrayList<Dish>();
		ArrayList<Dish> dishMainCourseNotSingleList = new ArrayList<Dish>();
		ArrayList<Dish> sideDishList = new ArrayList<Dish>();
		// DistTypeId: 0 - starter, 1 - mainCourseSingle, 2 - dessert, 3 - mainCourseNotSingle, 4
		// -sideDish
		for (Dish d : dishList) {
			if (d.dishTypeId == 3)
				dishMainCourseNotSingleList.add(d);
			else if (d.dishTypeId == 4)
				sideDishList.add(d);
			else
				dishSingleList.add(d);
		}
		try {
			out = new PrintWriter("meal_variant.sql");
			out2 = new PrintWriter("meal_variant_dish_rel.sql");
			out.println("insert into meal_variant values ");
			out2.println("insert into mealvariant_dish_relation values ");
			int id = 1;
			for (Dish d : dishSingleList) {

				String x = "(" + id + ", null, " + d.mealTypeId + "),";
				out.println(x);
				x = "(" + d.dishId + "," + id + "),";
				out2.println(x);
				id++;
			}
			for (Dish mainCourseNotSingle : dishMainCourseNotSingleList) {
				for (Dish sideDish : sideDishList) {
					if (id == 315)
						id = 315;
					// the dishes must be of the same meal type
					// ex side dish of breakfast type goes with main course of breakfast type
					if (mainCourseNotSingle.mealTypeId == sideDish.mealTypeId) {
						// insert the mealVariant as well

						String x = "(" + id + ", null, " + mainCourseNotSingle.mealTypeId + "),";
						out.println(x);
						// insert into the mealvariant_dish_relation - main course
						x = "(" + mainCourseNotSingle.dishId + "," + id + "),";
						out2.println(x);
						// insert into the mealvariant_dish_relation and the side dish
						x = "(" + sideDish.dishId + "," + id + "),";
						out2.println(x);
						id++;
					}
				}
			}
			out.close();
			out2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateDishScript(ArrayList<Dish> dishList) {
		try {
			out = new PrintWriter("dish.sql");
			out.println("insert into dish values ");
			for (Dish d : dishList) {
				String x = "(null, " + d.dishTypeId + ", " + d.mealTypeId + ", " + d.recipeId + "),";
				out.println(x);
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateGeographicalAreaScript() {
		try {
			out = new PrintWriter("geogr_area.sql");
			out.println("insert into geographical_area values ");
			for (int i = 1; i <= 100; i++) {
				out.println("(" + i + ", 'country " + i + "','city " + i + "','region " + i + "'),");
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateFoodProviderScript() {
		try {
			out = new PrintWriter("food_provider.sql");
			out.println("insert into food_service_provider values ");
			for (int i = 1; i <= 100; i++) {
				out.println("(" + i + ", 'generated provider " + i + "', " + i + "),");
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
