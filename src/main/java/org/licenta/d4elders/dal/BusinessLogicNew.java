package org.licenta.d4elders.dal;

import java.util.ArrayList;
import java.util.Random;

import org.licenta.d4elders.model.*;
import org.licenta.d4elders.model.dish.*;
import org.licenta.d4elders.model.meal.*;
import org.licenta.d4elders.ontology.FoodProviderOntology;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.resultset.ResultsFormat;

public class BusinessLogicNew {
	private static FoodProviderOntology foodProviderOntology;
	private static Model model;
	private static Model data;
	private String breakfast = "Breakfast";
	private String lunch = "Lunch";
	private String dinner = "Dinner";
	private String snack = "Snack";
	private String starterDish = "Starter_Dish";
	private String mainCourseSingle = "Main_Course_Single";
	private String mainCourseNotSingle = "Main_Course_Not_Single";
	private String sideDish = "Side_Dish";
	private String desert = "Dessert";

	/*
	 * The arrays are fetched once and stored in cache. Subsequent calls return the cached arrays.
	 * Since the elements in the ontology are constant during a run of the program, there won't be
	 * any misses.
	 */
	private static ArrayList<Menu> menuListCache;
	private static ArrayList<Menu> breakfastListCache;
	private static ArrayList<Menu> lunchListCache;
	private static ArrayList<Menu> dinnerListCache;
	private static ArrayList<Menu> snackListCache;

	private static ArrayList<GeographicalArea> geoAreaListCache;
	private static ArrayList<FoodServiceProvider> foodProviderListCache;
	private static ArrayList<Recipe> recipeListCache;
	private static ArrayList<Dish> dishListCache;
	private static ArrayList<MealVariant> mealVariantListCache;

	static {
		foodProviderOntology = new FoodProviderOntology();
		model = foodProviderOntology.getOntModel();
		data = foodProviderOntology.getD2rData();
		model.add(data);
	}

	/**
	 * Prefetches the data from ontolgy into main memory. Any subsequent call will return data from
	 * the faster main memory instead of the slower ontology.
	 */
	public void loadOntologyDataIntoMemory() {
		loadCache();
		getAllBreakfastMenus();
		getAllLunchMenus();
		getAllDinnerMenus();
		getAllSnackMenus();
	}

	public ArrayList<Menu> getAllMenus(MealType mealType, DishType dishType) {
		ArrayList<Menu> menuList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX
				+ "SELECT DISTINCT ?menu ?menuId ?mealV ?mealVId ?mealVMealType ?dish ?dishId "
				+ "?foodProv ?foodProvId ?foodProvName ?geoArea ?geoAreaId ?geoCountry ?geoCity ?geoRegion "
				+ "?recipe ?recId ?recName ?recDescr ?recDishType "
				+ "?recPro ?recLip ?recCar ?recEne ?recCal ?recIro ?recSod ?recVa ?recVb ?recVc ?recVd "
				// Menu info
				+ "WHERE {?menu rdf:type nutritionassesment:Menu." // menu
				+ "?menu foodprovider:menuHasId ?menuId." // menu id
				+ "?menu foodprovider:menuHasMealType foodprovider:"
				+ mealType
				+ ". "
				+ "?menu foodprovider:menuHasMealVariant ?mealV." // mealVariant
				// Food provider & Geographical Area of food provider
				+ "?menu foodprovider:menuHasFoodProvider ?foodProv."
				+ "?foodProv foodprovider:foodProviderHasGeographicalArea ?geoArea."
				+ "?foodProv foodprovider:foodProviderHasID ?foodProvId."
				+ "?foodProv foodprovider:foodProviderHasName ?foodProvName."
				+ "?geoArea foodprovider:geographicalAreaHasId ?geoAreaId."
				+ "?geoArea foodprovider:geographicalAreaHasCity ?geoCity."
				+ "?geoArea foodprovider:geographicalAreaHasCountry ?geoCountry."
				+ "?geoArea foodprovider:geographicalAreaHasRegion ?geoRegion."
				// Menu meal variant, dish, recipe
				+ "?mealV foodprovider:mealVariantHasId ?mealVId." // mealVId
				+ "?mealV foodprovider:mealVariantHasDish ?dish."
				+ "?mealV foodprovider:mealVariantHasMealType ?mealVMealType."
				+ "?dish foodprovider:dishHasId ?dishId." // dishId
				// + "?dish foodprovider:dishHasDishType foodprovider:" + dishType + ". "// dishType
				+ "?dish foodprovider:dishHasRecipe ?recipe."
				// Recipe data + nutrients
				+ "?recipe foodprovider:recipeHasId ?recId." // Id
				+ "?recipe foodprovider:recipeHasName ?recName." // Name
				+ "?recipe foodprovider:recipeHasDescription ?recDescr." // Descr
				+ "?recipe foodprovider:recipeHasDishType ?recDishType." // DishType
				+ "?recipe foodprovider:recipeHasProteins_g ?recPro." // Proteins
				+ "?recipe foodprovider:recipeHasFats_g ?recLip." // Fats
				+ "?recipe foodprovider:recipeHasCarbs_g ?recCar." // Carbs
				+ "?recipe foodprovider:recipeHasEnergy_kcal ?recEne." // Energy
				+ "?recipe foodprovider:recipeHasCalcium_mg ?recCal." // Calcium
				+ "?recipe foodprovider:recipeHasIron_mg ?recIro." // Iron
				+ "?recipe foodprovider:recipeHasSodium_mg ?recSod." // Sodium
				+ "?recipe foodprovider:recipeHasVitaminA_ug ?recVa." // Vitamin A
				+ "?recipe foodprovider:recipeHasVitaminB6_mg ?recVb." // Vitamin B
				+ "?recipe foodprovider:recipeHasVitaminC_mg ?recVc." // Vitamin C
				+ "?recipe foodprovider:recipeHasVitaminD_ug ?recVd}"; // Vitamin D

		// String queryString1 = FoodProviderOntology.PREFIX + "SELECT DISTINCT ?dish ?dishId "
		// + "WHERE {?dish rdf:type nutritionassesment:Dish." // aaa
		// + "?dish foodprovider:dishHasId ?dishId." // bbb
		// + "?dish foodprovider:dishHasDishType foodprovider:Dessert}"; // bbb
		// + "{?dish foodprovider:dishHasDishType foodprovider:Dessert.} UNION" // bbb
		// + "{?dish foodprovider:dishHasDishType foodprovider:Side_Dish}}"; // bbb

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		int counter = 0;
		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			// System.out.println(row.getResource("mealVMealType").toString() + ", "
			// + row.getResource("recDishType").toString() + ", " +
			// row.getLiteral("mealVId").getLong());
			if (row.getResource("menu") != null) {
				counter++;
				Menu menu = new Menu();

				// recipe.setName(row.getLiteral("name").getString());
				// recipe.setDescription(row.getLiteral("descr").getString());
				menuList.add(menu);
			}
		}
		System.out.println(mealType + " " + dishType + " ->nr of rows returned:" + counter);
		return menuList;
	}

	public ArrayList<Menu> getAllMenus2() {
		ArrayList<Menu> menuList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX
				+ "SELECT DISTINCT ?menu ?menuId " // Menu
				+ "?mealV ?mealVId ?mealVMealType " // Meal variant
				+ "?dish ?dishType ?dishId ?dishTypeId ?dishTypeName " // Dish
				+ "?foodProv ?foodProvId ?foodProvName " // Food provider
				+ " ?geoArea ?geoAreaId ?geoCountry ?geoCity ?geoRegion " // Geographical Area
				+ "?recipe ?recId ?recName ?recDescr ?recDishType " // Recipe
				+ "?recPro ?recLip ?recCar ?recEne ?recCal ?recIro ?recSod " // Recipe Nutr
				+ "?recVa ?recVb ?recVc ?recVd "// Recipe Nutr
				// Menu info
				+ "WHERE {?menu rdf:type nutritionassesment:Menu." // menu
				+ "?menu foodprovider:menuHasId ?menuId." // menu id
				+ "?menu foodprovider:menuHasMealVariant ?mealV." // mealVariant
				// Food provider & Geographical Area of food provider
				+ "?menu foodprovider:menuHasFoodProvider ?foodProv."
				+ "?foodProv foodprovider:foodProviderHasGeographicalArea ?geoArea."
				+ "?foodProv foodprovider:foodProviderHasID ?foodProvId."
				+ "?foodProv foodprovider:foodProviderHasName ?foodProvName."
				+ "?geoArea foodprovider:geographicalAreaHasId ?geoAreaId."
				+ "?geoArea foodprovider:geographicalAreaHasCity ?geoCity."
				+ "?geoArea foodprovider:geographicalAreaHasCountry ?geoCountry."
				+ "?geoArea foodprovider:geographicalAreaHasRegion ?geoRegion."
				// Menu meal variant, dish, recipe
				+ "?mealV foodprovider:mealVariantHasId ?mealVId." // mealVId
				+ "?mealV foodprovider:mealVariantHasDish ?dish."
				+ "?mealV foodprovider:mealVariantHasMealType ?mealVMealType."
				+ "?dish foodprovider:dishHasId ?dishId." // dishId
				// + "?dish foodprovider:dishHasDishType foodprovider:" + dishType + ". "// dishType
				+ "?dish foodprovider:dishHasDishType ?dishType. "// dishType
				+ "?dish foodprovider:dishHasRecipe ?recipe."
				// Recipe data + nutrients
				+ "?recipe foodprovider:recipeHasId ?recId." // Id
				+ "?recipe foodprovider:recipeHasName ?recName." // Name
				+ "?recipe foodprovider:recipeHasDescription ?recDescr." // Descr
				+ "?recipe foodprovider:recipeHasDishType ?recDishType." // DishType
				+ "?recipe foodprovider:recipeHasProteins_g ?recPro." // Proteins
				+ "?recipe foodprovider:recipeHasFats_g ?recLip." // Fats
				+ "?recipe foodprovider:recipeHasCarbs_g ?recCar." // Carbs
				+ "?recipe foodprovider:recipeHasEnergy_kcal ?recEne." // Energy
				+ "?recipe foodprovider:recipeHasCalcium_mg ?recCal." // Calcium
				+ "?recipe foodprovider:recipeHasIron_mg ?recIro." // Iron
				+ "?recipe foodprovider:recipeHasSodium_mg ?recSod." // Sodium
				+ "?recipe foodprovider:recipeHasVitaminA_ug ?recVa." // Vitamin A
				+ "?recipe foodprovider:recipeHasVitaminB6_mg ?recVb." // Vitamin B
				+ "?recipe foodprovider:recipeHasVitaminC_mg ?recVc." // Vitamin C
				+ "?recipe foodprovider:recipeHasVitaminD_ug ?recVd}"; // Vitamin D

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		int counter = 0;
		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			// System.out.println(row.getResource("mealVMealType").toString() + ", "
			// + row.getResource("recDishType").toString() + ", " +
			// row.getLiteral("mealVId").getLong());
			if (row.getResource("menu") != null) {
				// ?menu ?menuId // Menu
				// ?mealV ?mealVId ?mealVMealType // Meal variant
				// ?dish ?dishType ?dishId ?dishTypeId ?dishTypeName // Dish
				// ?foodProv ?foodProvId ?foodProvName // Food provider
				// ?geoArea ?geoAreaId ?geoCountry ?geoCity ?geoRegion // Geographical Area
				// ?recipe ?recId ?recName ?recDescr ?recDishType // Recipe
				// ?recPro ?recLip ?recCar ?recEne ?recCal ?recIro ?recSod // Recipe Nutr
				// ?recVa ?recVb ?recVc ?recVd // Recipe Nutr
				counter++;
				Menu menu = new Menu();
				int currentMenuId = row.getLiteral("menuId").getInt();
				// if menu already exists do not create a new one, but add the meal variants to the
				// existing one
				for (Menu m : menuList) {
					if (m.getMenuId() == currentMenuId) {
						menu = m;
						break;
					}
				}
				// Menu id
				menu.setMenuId(currentMenuId);
				// Menu food provider
				GeographicalArea geoArea = new GeographicalArea(row.getLiteral("geoAreaId").getInt(), row.getLiteral(
						"geoCountry").getString(), row.getLiteral("geoRegion").getString(), row.getLiteral("geoCity")
						.getString());
				FoodServiceProvider foodProv = new FoodServiceProvider(row.getLiteral("foodProvId").getInt(), row
						.getLiteral("foodProvName").getString(), geoArea);
				menu.setFoodProvider(foodProv);

				// Current MealVariant
				int currentMealVariantId = row.getLiteral("mealVId").getInt();
				String dishTypeLocalName = row.getResource("dishType").getLocalName();
				DishType dishType = DishType.valueOf(dishTypeLocalName);
				String mealTypeLocalName = row.getResource("mealVMealType").getLocalName();
				MealType mealType = MealType.valueOf(mealTypeLocalName);
				MealVariant mealV = new MealVariant();
				mealV.setMealVariantId(currentMealVariantId);

				Recipe rec = new Recipe();
				rec.setCalcium(row.getLiteral("recCal").getFloat());
				rec.setCarbohydrates(row.getLiteral("recCar").getFloat());
				rec.setDescription(row.getLiteral("recDescr").getString());
				rec.setDishType(dishType);
				rec.setEnergy(row.getLiteral("recEne").getFloat());
				rec.setIron(row.getLiteral("recIro").getFloat());
				rec.setLipids(row.getLiteral("recLip").getFloat());
				rec.setName(row.getLiteral("recName").getString());
				rec.setProteins(row.getLiteral("recPro").getFloat());
				rec.setRecipeId(row.getLiteral("recId").getInt());
				rec.setSodium(row.getLiteral("recSod").getFloat());
				rec.setVitA(row.getLiteral("recVa").getFloat());
				rec.setVitB(row.getLiteral("recVb").getFloat());
				rec.setVitC(row.getLiteral("recVc").getFloat());
				rec.setVitD(row.getLiteral("recVd").getFloat());
				Dish d = new Dish(rec);
				d.setDishId(row.getLiteral("dishId").getInt());

				MealVariant mainCourseMealV = menu.getMainCourse();
				switch (dishType) {
				case Starter_Dish:
					mealV.setDishType(DishTypeGeneral.Starter);
					mealV.setMainDish(d);
					menu.setStarter(mealV);
					break;
				case Dessert:
					mealV.setDishType(DishTypeGeneral.Desert);
					mealV.setMainDish(d);
					menu.setDesert(mealV);
					break;
				case Main_Course_Single:
					mealV.setDishType(DishTypeGeneral.Desert);
					mealV.setMainDish(d);
					menu.setDesert(mealV);
					break;
				case Main_Course_Not_Single:
					if (mainCourseMealV == null) {
						mealV.setDishType(DishTypeGeneral.MainCourse);
						mealV.setMainDish(d);
						menu.setMainCourse(mealV);
					} else {
						mainCourseMealV.setMainDish(d);
					}
					break;
				case Side_Dish:
					if (mainCourseMealV == null) {
						mealV.setDishType(DishTypeGeneral.MainCourse);
						mealV.setSideDish(d);
						menu.setMainCourse(mealV);
					} else {
						mainCourseMealV.setSideDish(d);
					}
					break;
				}
				// if (menu.getMainCourse() != null) {
				// // System.out.println(menu.getMainCourse());
				// if (menu.getMainCourse().getSideDish() != null &&
				// menu.getMainCourse().getMainDish() != null) {
				// System.out.print(menu.getMainCourse().getSideDish() + " \n"
				// + menu.getMainCourse().getMainDish());
				// System.out.println();
				// }
				// // else if (menu.getMainCourse().getSideDish() != null) {
				// // System.out.print(menu.getMainCourse().getSideDish());
				// // System.out.println();
				// // } else if (menu.getMainCourse().getMainDish() != null) {
				// // System.out.print(menu.getMainCourse().getMainDish());
				// // System.out.println();
				// // }
				// }
				menuList.add(menu);
			}
		}
		return menuList;
	}

	public ArrayList<Menu> getAllMenus() {
		ArrayList<Menu> menuList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // prefix
				+ "SELECT DISTINCT ?menu ?menuId ?foodProv ?foodProvId ?mealType " // Menu
				// FoodProvider info
				+ "WHERE {?menu rdf:type nutritionassesment:Menu." // menu
				+ "?menu foodprovider:menuHasId ?menuId." // id
				+ "?menu foodprovider:menuHasMealType ?mealType." // meal type
				+ "?menu foodprovider:menuHasFoodProvider ?foodProv." // food prov
				+ "?foodProv foodprovider:foodProviderHasID ?foodProvId}"; // food prov id

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("menu") != null) {
				Menu menu = new Menu();
				int menuId = row.getLiteral("menuId").getInt();
				int foodProvId = row.getLiteral("foodProvId").getInt();
				for (Menu m : menuList) {
					if (m.getMenuId() == menuId) {
						menu = m;
						break;
					}
				}

				FoodServiceProvider foodProv = null;
				for (FoodServiceProvider foodP : foodProviderListCache) {
					if (foodP.getFoodProviderId() == foodProvId) {
						foodProv = foodP;
					}
				}
				if (foodProv == null)
					continue;
				menu.setFoodProvider(foodProv);
				menuList.add(menu);
			}
		}
		return menuList;
	}

	public ArrayList<GeographicalArea> getAllGeographicalAreas() {
		ArrayList<GeographicalArea> geoAreaList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // prefix
				+ "SELECT DISTINCT ?geoArea ?geoId ?geoCountry ?geoRegion ?geoCity " // Recipe
				// MealVariant info
				+ "WHERE {?geoArea rdf:type nutritionassesment:GeographicalArea." // geoArea
				+ "?geoArea foodprovider:geographicalAreaHasId ?geoId." // id
				+ "?geoArea foodprovider:geographicalAreaHasCountry ?geoCountry." // country
				+ "?geoArea foodprovider:geographicalAreaHasRegion ?geoRegion." // region
				+ "?geoArea foodprovider:geographicalAreaHasCity ?geoCity}"; // city

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("geoArea") != null) {
				GeographicalArea geoArea = new GeographicalArea();
				geoArea.setGeoAreaId(row.getLiteral("geoId").getInt());
				geoArea.setCountry(row.getLiteral("geoCountry").getString());
				geoArea.setRegion(row.getLiteral("geoRegion").getString());
				geoArea.setCity(row.getLiteral("geoCity").getString());
				geoAreaList.add(geoArea);
			}
		}
		return geoAreaList;
	}

	public ArrayList<FoodServiceProvider> getAllFoodProviders() {
		ArrayList<FoodServiceProvider> foodProviderList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // prefix
				+ "SELECT DISTINCT ?foodProv ?foodProvId ?foodProvName ?geoArea ?geoId " // Recipe
				// FoodProvider info
				+ "WHERE {?foodProv rdf:type nutritionassesment:FoodServiceProvider." // geoArea
				+ "?foodProv foodprovider:foodProviderHasID ?foodProvId." // id
				+ "?foodProv foodprovider:foodProviderHasName ?foodProvName." // name
				+ "?foodProv foodprovider:foodProviderHasGeographicalArea ?geoArea." // name
				+ "?geoArea foodprovider:geographicalAreaHasId ?geoId}"; // city

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("foodProv") != null) {
				FoodServiceProvider foodProv = new FoodServiceProvider();
				foodProv.setFoodProviderId(row.getLiteral("foodProvId").getInt());
				foodProv.setName(row.getLiteral("foodProvName").getString());
				int geoAreaId = row.getLiteral("geoId").getInt();

				GeographicalArea geoArea = null;
				for (GeographicalArea geoA : geoAreaListCache) {
					if (geoA.getGeoAreaId() == geoAreaId) {
						geoArea = geoA;
					}
				}
				if (geoArea == null)
					continue;
				foodProv.setGeoArea(geoArea);
				foodProviderList.add(foodProv);
			}
		}
		return foodProviderList;
	}

	public ArrayList<MealVariant> getAllMealVariants() {
		ArrayList<MealVariant> mealVariantList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // prefix
				+ "SELECT DISTINCT ?mealV ?mealVId ?diet ?mealType ?dish ?dishId ?dishType " // Recipe
				// MealVariant info
				+ "WHERE {?mealV rdf:type nutritionassesment:MealVariant." // menu
				+ "?mealV foodprovider:mealVariantHasId ?mealVId." // meal id
				+ "?mealV foodprovider:mealVariantHasMealType ?mealType." // mealtype
				+ "?mealV foodprovider:mealVariantHasDish ?dish." // dish
				+ "?dish foodprovider:dishHasDishType ?dishType." // dish
				+ "?dish foodprovider:dishHasId ?dishId}"; // dish

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("mealV") != null) {
				MealVariant mealV = new MealVariant();
				int mealVId = row.getLiteral("mealVId").getInt();
				for (MealVariant mv : mealVariantList) {
					if (mv.getMealVariantId() == mealVId) {
						mealV = mv;
						break;
					}
				}
				DishType dishType = DishType.valueOf(row.getResource("dishType").getLocalName());
				MealType mealType = MealType.valueOf(row.getResource("mealType").getLocalName());
				int dishId = row.getLiteral("dishId").getInt();
				Dish dish = null;
				for (Dish d : dishListCache) {
					if (d.getDishId() == dishId) {
						dish = d;
						break;
					}
				}
				if (dish == null) {
					continue;
				}
				switch (dishType) {
				case Side_Dish:
					mealV.setSideDish(dish);
					mealV.setDishType(DishTypeGeneral.Starter);
					mealV.setMealType(mealType);
					break;
				default:
					mealV.setMainDish(dish);
					mealV.setDishType(DishTypeGeneral.Starter);
					mealV.setMealType(mealType);
					break;
				}
				mealVariantList.add(mealV);
			}
		}
		return mealVariantList;
	}

	public ArrayList<Dish> getAllDishes() {
		ArrayList<Dish> dishList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // Menu
				+ "SELECT DISTINCT ?dish ?dishId ?dishType ?mealType ?recipe ?recipeId " // Dish
				+ "WHERE {?dish rdf:type nutritionassesment:Dish." // menu
				+ "?dish foodprovider:dishHasId ?dishId." // dishId
				+ "?dish foodprovider:dishHasDishType ?dishType. "// dishType
				+ "?dish foodprovider:dishHasRecipe ?recipe."// recipe
				+ "?recipe foodprovider:recipeHasId ?recipeId}";

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		int counter = 0;
		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("dish") != null) {
				Dish dish = new Dish();
				int recipeId = row.getLiteral("recipeId").getInt();
				Recipe rec = null;
				for (Recipe r : recipeListCache) {
					if (r.getRecipeId() == recipeId) {
						rec = r;
						break;
					}
				}
				if (rec == null)
					continue;
				dish.setRecipe(rec);
				dish.setDishId(row.getLiteral("dishId").getInt());
				dishList.add(dish);
			}
		}
		return dishList;
	}

	public ArrayList<Recipe> getAllRecipes() {
		ArrayList<Recipe> recipeList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX
				+ "SELECT DISTINCT ?recipe ?recId ?recName ?recDescr ?recDishType " // Recipe
				+ "?recPro ?recLip ?recCar ?recEne ?recCal ?recIro ?recSod " // Recipe Nutr
				+ "?recVa ?recVb ?recVc ?recVd "// Recipe Nutr
				// Menu info
				+ "WHERE {?recipe rdf:type nutritionassesment:Recipe." // menu
				// Recipe data + nutrients
				+ "?recipe foodprovider:recipeHasId ?recId." // Id
				+ "?recipe foodprovider:recipeHasName ?recName." // Name
				+ "?recipe foodprovider:recipeHasDescription ?recDescr." // Descr
				+ "?recipe foodprovider:recipeHasDishType ?recDishType." // DishType
				+ "?recipe foodprovider:recipeHasProteins_g ?recPro." // Proteins
				+ "?recipe foodprovider:recipeHasFats_g ?recLip." // Fats
				+ "?recipe foodprovider:recipeHasCarbs_g ?recCar." // Carbs
				+ "?recipe foodprovider:recipeHasEnergy_kcal ?recEne." // Energy
				+ "?recipe foodprovider:recipeHasCalcium_mg ?recCal." // Calcium
				+ "?recipe foodprovider:recipeHasIron_mg ?recIro." // Iron
				+ "?recipe foodprovider:recipeHasSodium_mg ?recSod." // Sodium
				+ "?recipe foodprovider:recipeHasVitaminA_ug ?recVa." // Vitamin A
				+ "?recipe foodprovider:recipeHasVitaminB6_mg ?recVb." // Vitamin B
				+ "?recipe foodprovider:recipeHasVitaminC_mg ?recVc." // Vitamin C
				+ "?recipe foodprovider:recipeHasVitaminD_ug ?recVd}"; // Vitamin D

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("recipe") != null) {
				// ?recipe ?recId ?recName ?recDescr ?recDishType // Recipe
				// ?recPro ?recLip ?recCar ?recEne ?recCal ?recIro ?recSod // Recipe Nutr
				// ?recVa ?recVb ?recVc ?recVd // Recipe Nutr
				DishType dishType = DishType.valueOf(row.getResource("recDishType").getLocalName());
				Recipe rec = new Recipe();
				rec.setRecipeId(row.getLiteral("recId").getInt());
				if (rec.getRecipeId() == 6000)
					System.out.println("aa no way");
				rec.setDishType(dishType);
				rec.setName(row.getLiteral("recName").getString());
				rec.setDescription(row.getLiteral("recDescr").getString());
				rec.setCalcium(row.getLiteral("recCal").getFloat());
				rec.setCarbohydrates(row.getLiteral("recCar").getFloat());
				rec.setEnergy(row.getLiteral("recEne").getFloat());
				rec.setIron(row.getLiteral("recIro").getFloat());
				rec.setLipids(row.getLiteral("recLip").getFloat());
				rec.setProteins(row.getLiteral("recPro").getFloat());
				rec.setSodium(row.getLiteral("recSod").getFloat());
				rec.setVitA(row.getLiteral("recVa").getFloat());
				rec.setVitB(row.getLiteral("recVb").getFloat());
				rec.setVitC(row.getLiteral("recVc").getFloat());
				rec.setVitD(row.getLiteral("recVd").getFloat());
				recipeList.add(rec);
			}
		}
		return recipeList;
	}

	public Menu generateSingleBreakfastMenu() {
		return generateBreakfastMenus(1).get(0);
	}

	public Menu generateSingleLunchMenu() {
		return generateLunchMenus(1).get(0);
	}

	public Menu generateSingleDinnerMenu() {
		return generateDinnerMenus(1).get(0);
	}

	public Menu generateSingleSnackMenu() {
		return generateSnackMenus(1).get(0);
	}

	public ArrayList<Menu> generateBreakfastMenus(int numberOfSolutions) {
		ArrayList<Menu> breakfastList = new ArrayList<Menu>();
		Random r = new Random();
		int listSize = breakfastListCache.size();
		for (int i = 0; i < listSize; i++) {
			int randomIndex = r.nextInt(listSize);
			breakfastList.add(breakfastListCache.get(randomIndex));
		}
		return breakfastList;
	}

	public ArrayList<Menu> generateLunchMenus(int numberOfSolutions) {
		ArrayList<Menu> lunchList = new ArrayList<Menu>();
		Random r = new Random();
		int listSize = lunchListCache.size();
		for (int i = 0; i < listSize; i++) {
			int randomIndex = r.nextInt(listSize);
			lunchList.add(lunchListCache.get(randomIndex));
		}
		return lunchList;
	}

	public ArrayList<Menu> generateDinnerMenus(int numberOfSolutions) {
		ArrayList<Menu> dinnerList = new ArrayList<Menu>();
		Random r = new Random();
		int listSize = dinnerListCache.size();
		for (int i = 0; i < listSize; i++) {
			int randomIndex = r.nextInt(listSize);
			dinnerList.add(dinnerListCache.get(randomIndex));
		}
		return dinnerList;
	}

	public ArrayList<Menu> generateSnackMenus(int numberOfSolutions) {
		ArrayList<Menu> snackList = new ArrayList<Menu>();
		Random r = new Random();
		int listSize = snackListCache.size();
		for (int i = 0; i < listSize; i++) {
			int randomIndex = r.nextInt(listSize);
			snackList.add(snackListCache.get(randomIndex));
		}
		return snackList;
	}

	public ArrayList<Menu> getAllBreakfastMenus() {
		if (breakfastListCache == null) {
			if (menuListCache == null)
				menuListCache = getAllMenus();
			breakfastListCache = new ArrayList<>();
			for (Menu m : menuListCache) {
				if (m.getMealType() == MealType.Breakfast)
					breakfastListCache.add(m);
			}
		}
		return breakfastListCache;
	}

	public ArrayList<Menu> getAllLunchMenus() {
		if (lunchListCache == null) {
			if (menuListCache == null)
				menuListCache = getAllMenus();
			lunchListCache = new ArrayList<>();
			for (Menu m : menuListCache) {
				if (m.getMealType() == MealType.Lunch)
					lunchListCache.add(m);
			}
		}
		return lunchListCache;
	}

	public ArrayList<Menu> getAllDinnerMenus() {
		if (dinnerListCache == null) {
			if (menuListCache == null)
				menuListCache = getAllMenus();
			dinnerListCache = new ArrayList<>();
			for (Menu m : menuListCache) {
				if (m.getMealType() == MealType.Dinner)
					dinnerListCache.add(m);
			}
		}
		return dinnerListCache;
	}

	public ArrayList<Menu> getAllSnackMenus() {
		if (snackListCache == null) {
			if (menuListCache == null)
				menuListCache = getAllMenus();
			snackListCache = new ArrayList<>();
			for (Menu m : menuListCache) {
				if (m.getMealType() == MealType.Snack)
					snackListCache.add(m);
			}
		}
		return snackListCache;
	}

	public void loadCache() {
		recipeListCache = getAllRecipes();
		dishListCache = getAllDishes();
		mealVariantListCache = getAllMealVariants();
		geoAreaListCache = getAllGeographicalAreas();
		foodProviderListCache = getAllFoodProviders();
	}

	public void getAllMenu2() {
		ArrayList<Menu> menuList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX + "SELECT DISTINCT ?menu ?menuId ?mealV ?mealVId ?dish"
		// Menu info
				+ "WHERE {?menu rdf:type nutritionassesment:Menu." // menu
				+ "?menu foodprovider:menuHasId ?menuId." // menu id
				+ "?menu foodprovider:menuHasMealVariant ?mealV." // menu id
				+ "?mealV foodprovider:mealVariantHasId ?mealVId." // menu id
				+ "?mealV foodprovider:mealVariantHasDish ?dish}"; // menu id
		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		int counter = 0;
		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("menu") != null) {
				counter++;
				Menu m = new Menu();
				m.setMenuId(row.getLiteral("menuId").getInt());
				menuList.add(m);
			}
		}
		System.out.println(menuList.size());
	}

	public ArrayList<Menu> getAllMenusHelper() {
		ArrayList<Menu> menuList = new ArrayList<>();

		String queryString = FoodProviderOntology.PREFIX // Menu
				+ "SELECT DISTINCT ?menu ?menuId ?cost ?deliveryTime " // Dish
				+ "WHERE {?menu rdf:type nutritionassesment:Menu." // menu
				+ "?menu foodprovider:menuHasId ?menuId}"; // dishId
//				+ "?menu foodprovider:menuHasCost ?cost}";// dishType
		// + "?menu foodprovider:menuHasDeliveryTime ?deliveryTime}";// recipe

		ResultSet rs = foodProviderOntology.queryModelForResult(queryString, foodProviderOntology.getOntModel(),
				foodProviderOntology.getD2rData());

		while (rs.hasNext()) {
			QuerySolution row = rs.nextSolution();

			if (row.getResource("menu") != null) {
				Menu menu = new Menu();
				System.out.println(row.getResource("menu"));
				menuList.add(menu);
			}
		}
		System.out.println(menuList.size()+" size");
		return menuList;
	}
}
