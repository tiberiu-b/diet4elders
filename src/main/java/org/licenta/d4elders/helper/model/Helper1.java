package org.licenta.d4elders.helper.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		generateMenuScript(dao.getAllMealVariants());
	}

	private void generateMenuScript(ArrayList<MealVariant> allMealVariants) {
		// MealTypeId: 0 - breakfast, 1 - lunch, 2 - dinner, 3 - snack
		// DistTypeId: 0 - starter, 1 - mainCourseSingle, 2 - dessert, 3 - mainCourseNotSingle, 4
		// -sideDish
		ArrayList<Breakfast> breakfastList = new ArrayList<Breakfast>();
		ArrayList<Lunch> lunchList = new ArrayList<Lunch>();
		ArrayList<Dinner> dinnerList = new ArrayList<Dinner>();
		ArrayList<Snack> snackList = new ArrayList<Snack>();
		try {
			out = new PrintWriter("menu.sql");
			out2 = new PrintWriter("menu_mealvariant_relation.sql");
			out.println("insert into menu values ");
			out2.println("insert into menu_mealvariant_relation values ");
			int menuId = 1;
			for (MealVariant mv : allMealVariants) {
				String x = "(" + menuId + ", null, " + mv.mealTypeId + "),";
				out.println(x);
				x = "(" + mv.mealVariantId + "," + menuId + "),";
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
		try {
			out = new PrintWriter("meal_variant.sql");
			out2 = new PrintWriter("meal_variant_dish_rel.sql");
			out.println("insert into meal_variant values ");
			out2.println("insert into mealvariant_dish_relation values ");
			int id = 1;
			for (Dish d : dishList) {
				String x = "(" + id + ", null, " + d.mealTypeId + "),";
				out.println(x);
				x = "(" + d.dishId + "," + id + "),";
				out2.println(x);
				id++;
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
