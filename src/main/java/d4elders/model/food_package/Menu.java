package d4elders.model.food_package;

import java.io.Serializable;
import java.util.ArrayList;

import d4elders.model.FoodNutrients;
import d4elders.model.meal.MealType;
import d4elders.model.meal.MealVariant;

public class Menu extends FoodNutrients implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int menuId;
	private MealVariant starter;
	private MealVariant mainCourse;
	private MealVariant desert;
	private MealType mealType;
	private String ingredients = "";

	public void computeIngredientsList(){
		if (starter != null){
			ingredients += starter
				.getMainDish()
				.getRecipe()
				.getIngredientList();
		}

		if (desert != null){
			ingredients += desert
				.getMainDish()
				.getRecipe()
				.getIngredientList();
		}

		if(mainCourse != null){
			ingredients += mainCourse
				.getMainDish()
				.getRecipe()
				.getIngredientList();

			if (getMainCourse().getSideDish() != null)
				ingredients += getMainCourse()
				.getSideDish()
				.getRecipe()
				.getIngredientList();
		}
	}

	public String getIngredientsString(){
		return ingredients;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public MealVariant getMainCourse() {
		return mainCourse;
	}

	public void setMainCourse(MealVariant mainCourse) {
		this.mainCourse = mainCourse;
	}

	public MealVariant getDesert() {
		return desert;
	}

	public void setDesert(MealVariant desert) {
		this.desert = desert;
	}

	public MealVariant getStarter() {
		return starter;
	}

	public void setStarter(MealVariant starter) {
		this.starter = starter;
	}

	public Float getProteins_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getProteins();
		} else if (starter == null) {
			return mainCourse.getProteins() + desert.getProteins();
		} else {
			return starter.getProteins() + mainCourse.getProteins() + desert.getProteins();
		}
	}

	public Float getLipids_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getLipids();
		} else if (starter == null) {
			return mainCourse.getLipids() + desert.getLipids();
		} else {
			return starter.getLipids() + mainCourse.getLipids() + desert.getLipids();
		}
	}

	public Float getCarbohydrates_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getCarbohydrates();
		} else if (starter == null) {
			return mainCourse.getCarbohydrates() + desert.getCarbohydrates();
		} else {
			return starter.getCarbohydrates() + mainCourse.getCarbohydrates() + desert.getCarbohydrates();
		}
	}

	public Float getEnergy_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getEnergy();
		} else if (starter == null) {
			return mainCourse.getEnergy() + desert.getEnergy();
		} else {
			return starter.getEnergy() + mainCourse.getEnergy() + desert.getEnergy();
		}
	}

	public Float getCalcium_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getCalcium();
		} else if (starter == null) {
			return mainCourse.getCalcium() + desert.getCalcium();
		} else {
			return starter.getCalcium() + mainCourse.getCalcium() + desert.getCalcium();
		}
	}

	public Float getIron_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getIron();
		} else if (starter == null) {
			return mainCourse.getIron() + desert.getIron();
		} else {
			return starter.getIron() + mainCourse.getIron() + desert.getIron();
		}
	}

	public Float getSodium_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getSodium();
		} else if (starter == null) {
			return mainCourse.getSodium() + desert.getSodium();
		} else {
			return starter.getSodium() + mainCourse.getSodium() + desert.getSodium();
		}
	}

	public Float getVitA_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getVitA();
		} else if (starter == null) {
			return mainCourse.getVitA() + desert.getVitA();
		} else {
			return starter.getVitA() + mainCourse.getVitA() + desert.getVitA();
		}
	}

	public Float getVitB_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getVitB();
		} else if (starter == null) {
			return mainCourse.getVitB() + desert.getVitB();
		} else {
			return starter.getVitB() + mainCourse.getVitB() + desert.getVitB();
		}
	}

	public Float getVitC_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getVitC();
		} else if (starter == null) {
			return mainCourse.getVitC() + desert.getVitC();
		} else {
			return starter.getVitC() + mainCourse.getVitC() + desert.getVitC();
		}
	}

	public Float getVitD_custom() {
		if (starter == null && desert == null) {
			return mainCourse.getVitD();
		} else if (starter == null) {
			return mainCourse.getVitD() + desert.getVitD();
		} else {
			return starter.getVitD() + mainCourse.getVitD() + desert.getVitD();
		}
	}

	public void computeNutrientValues() {

		setProteins(getProteins_custom());
		setLipids(getLipids_custom());
		setCarbohydrates(getCarbohydrates_custom());
		setEnergy(getEnergy_custom());
		setCalcium(getCalcium_custom());
		setIron(getIron_custom());
		setSodium(getSodium_custom());
		setVitA(getVitA_custom());
		setVitB(getVitB_custom());
		setVitC(getVitC_custom());
		setVitD(getVitD_custom());
	}

	@Override
	public Float getProteins() {
		if (proteins == null)
			computeNutrientValues();
		return proteins;
	}

	@Override
	public Float getLipids() {
		if (lipids == null)
			computeNutrientValues();
		return lipids;
	}

	@Override
	public Float getCarbohydrates() {
		if (carbohydrates == null)
			computeNutrientValues();
		return carbohydrates;
	}

	@Override
	public Float getEnergy() {
		if (energy == null)
			computeNutrientValues();
		return energy;
	}

	@Override
	public Float getCalcium() {
		if (calcium == null)
			computeNutrientValues();
		return calcium;
	}

	@Override
	public Float getIron() {
		if (iron == null)
			computeNutrientValues();
		return iron;
	}

	@Override
	public Float getSodium() {
		if (sodium == null)
			computeNutrientValues();
		return sodium;
	}

	@Override
	public Float getVitA() {
		if (vitA == null)
			computeNutrientValues();
		return vitA;
	}

	@Override
	public Float getVitB() {
		if (vitB == null)
			computeNutrientValues();
		return vitB;
	}

	@Override
	public Float getVitC() {
		if (vitC == null)
			computeNutrientValues();
		return vitC;
	}

	@Override
	public Float getVitD() {
		if (vitD == null)
			computeNutrientValues();
		return vitD;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	@Override
	public String toString() {
		if (starter == null && desert == null)
			return "MenuId: " + menuId + " Starter: -no starter" + " MainCourse: " + mainCourse.toString()
					+ " Desert: - no desert ";
		else if (starter == null)
			return "MenuId: " + menuId + " Starter: -no starter" + " MainCourse: " + mainCourse.toString()
					+ " Desert: " + desert.toString();
		else
			return "MenuId: " + menuId + " Starter: " + starter.toString() + " MainCourse: " + mainCourse.toString()
					+ " Desert: " + desert.toString();

	}

	public ArrayList<String> getIngredientList() {
		ArrayList<String> ingredientList = new ArrayList<String>();
		if (starter != null)
			ingredientList.addAll(starter.getMainDish().getRecipe().getIngredientList());
		if (desert != null)
			ingredientList.addAll(desert.getMainDish().getRecipe().getIngredientList());
		if (mainCourse != null) {
			ingredientList.addAll(mainCourse.getMainDish().getRecipe().getIngredientList());
			if (mainCourse.getSideDish() != null) {
				ingredientList.addAll(mainCourse.getSideDish().getRecipe().getIngredientList());
			}
		}
		return ingredientList;
	}
}
