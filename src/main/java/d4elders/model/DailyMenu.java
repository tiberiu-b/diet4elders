package d4elders.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import d4elders.model.food_package.FoodProviderPackage;
import d4elders.model.food_package.Menu;

/**
 *
 */
public class DailyMenu extends FoodNutrients implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected FoodProviderPackage breakfast;
	protected FoodProviderPackage dinner;
	protected FoodProviderPackage lunch;
	protected FoodProviderPackage snack1, snack2;
	private ArrayList<String> ingredientsList = new ArrayList<String>();

	/**
     *
     */
	public DailyMenu() {
	}

	public DailyMenu(FoodProviderPackage breakfast, FoodProviderPackage lunch,
			FoodProviderPackage dinner, FoodProviderPackage snack1,
			FoodProviderPackage snack2) {
		this.breakfast = breakfast;
		this.dinner = dinner;
		this.lunch = lunch;
		this.snack1 = snack1;
		this.snack2 = snack2;
		computeNutrientValues();
		computeIngredientsList();
	}

	private void computeIngredientsList() {
		ingredientsList.clear();
		ingredientsList.addAll(breakfast.getMenu().getIngredientsString());
		ingredientsList.addAll(lunch.getMenu().getIngredientsString());
		ingredientsList.addAll(dinner.getMenu().getIngredientsString());
		ingredientsList.addAll(snack1.getMenu().getIngredientsString());
		ingredientsList.addAll(snack2.getMenu().getIngredientsString());
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof DailyMenu))
			return false;

		DailyMenu _other = (DailyMenu) other;
		return breakfast.equals(_other.breakfast)
				&& dinner.equals(_other.dinner) && lunch.equals(_other.lunch)
				&& snack1.equals(_other.snack1) && snack2.equals(_other.snack2);
	}

	@Override
	public String toString() {
		return "	B: " + breakfast.toString() + "\n	L: " + lunch.toString()
				+ "\n	D:" + dinner.toString() + "\n	S1:" + snack1.toString()
				+ "\n	S2:" + snack2.toString();
	}

	public FoodProviderPackage getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(FoodProviderPackage breakfast) {
		this.breakfast = breakfast;
	}

	public FoodProviderPackage getDinner() {
		return dinner;
	}

	public void setDinner(FoodProviderPackage dinner) {
		this.dinner = dinner;
	}

	public FoodProviderPackage getLunch() {
		return lunch;
	}

	public void setLunch(FoodProviderPackage lunch) {
		this.lunch = lunch;
	}

	public FoodProviderPackage getSnack1() {
		return snack1;
	}

	public void setSnack1(FoodProviderPackage snack1) {
		this.snack1 = snack1;
	}

	public FoodProviderPackage getSnack2() {
		return snack2;
	}

	public void setSnack2(FoodProviderPackage snack2) {
		this.snack2 = snack2;
	}

	private Float getProteins_custom() {
		return breakfast.getMenu().getProteins()
				+ lunch.getMenu().getProteins()
				+ dinner.getMenu().getProteins()
				+ snack1.getMenu().getProteins()
				+ snack2.getMenu().getProteins();

	}

	private Float getLipids_custom() {
		return breakfast.getMenu().getLipids() + lunch.getMenu().getLipids()
				+ dinner.getMenu().getLipids() + snack1.getMenu().getLipids()
				+ snack2.getMenu().getLipids();
	}

	private Float getCarbohydrates_custom() {
		return breakfast.getMenu().getCarbohydrates()
				+ lunch.getMenu().getCarbohydrates()
				+ dinner.getMenu().getCarbohydrates()
				+ snack1.getMenu().getCarbohydrates()
				+ snack2.getMenu().getCarbohydrates();
	}

	private Float getEnergy_custom() {
		return breakfast.getMenu().getEnergy() + lunch.getMenu().getEnergy()
				+ dinner.getMenu().getEnergy() + snack1.getMenu().getEnergy()
				+ snack2.getMenu().getEnergy();
	}

	private Float getCalcium_custom() {
		return breakfast.getMenu().getCalcium() + lunch.getMenu().getCalcium()
				+ dinner.getMenu().getCalcium() + snack1.getMenu().getCalcium()
				+ snack2.getMenu().getCalcium();
	}

	private Float getIron_custom() {
		return breakfast.getMenu().getIron() + lunch.getMenu().getIron()
				+ dinner.getMenu().getIron() + snack1.getMenu().getIron()
				+ snack2.getMenu().getIron();
	}

	private Float getSodium_custom() {
		return breakfast.getMenu().getSodium() + lunch.getMenu().getSodium()
				+ dinner.getMenu().getSodium() + snack1.getMenu().getSodium()
				+ snack2.getMenu().getSodium();
	}

	private Float getVitA_custom() {
		return breakfast.getMenu().getVitA() + lunch.getMenu().getVitA()
				+ dinner.getMenu().getVitA() + snack1.getMenu().getVitA()
				+ snack2.getMenu().getVitA();
	}

	private Float getVitB_custom() {
		return breakfast.getMenu().getVitB() + lunch.getMenu().getVitB()
				+ dinner.getMenu().getVitB() + snack1.getMenu().getVitB()
				+ snack2.getMenu().getVitB();
	}

	private Float getVitC_custom() {
		return breakfast.getMenu().getVitC() + lunch.getMenu().getVitC()
				+ dinner.getMenu().getVitC() + snack1.getMenu().getVitC()
				+ snack2.getMenu().getVitC();
	}

	private Float getVitD_custom() {
		return breakfast.getMenu().getVitD() + lunch.getMenu().getVitD()
				+ dinner.getMenu().getVitD() + snack1.getMenu().getVitD()
				+ snack2.getMenu().getVitD();
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

	public ArrayList<String> getIngredientList() {
		ArrayList<String> ingredientList = new ArrayList<>();

		ingredientList.addAll(breakfast.getMenu().getDesert().getMainDish()
				.getRecipe().getIngredientList());
		ingredientList.addAll(breakfast.getMenu().getMainCourse().getMainDish()
				.getRecipe().getIngredientList());
		if (breakfast.getMenu().getMainCourse().getSideDish() != null)
			ingredientList.addAll(breakfast.getMenu().getMainCourse()
					.getSideDish().getRecipe().getIngredientList());

		ingredientList.addAll(lunch.getMenu().getStarter().getMainDish()
				.getRecipe().getIngredientList());
		ingredientList.addAll(lunch.getMenu().getDesert().getMainDish()
				.getRecipe().getIngredientList());
		ingredientList.addAll(lunch.getMenu().getMainCourse().getMainDish()
				.getRecipe().getIngredientList());
		if (lunch.getMenu().getMainCourse().getSideDish() != null)
			ingredientList.addAll(lunch.getMenu().getMainCourse().getSideDish()
					.getRecipe().getIngredientList());

		ingredientList.addAll(dinner.getMenu().getStarter().getMainDish()
				.getRecipe().getIngredientList());
		ingredientList.addAll(dinner.getMenu().getDesert().getMainDish()
				.getRecipe().getIngredientList());
		ingredientList.addAll(dinner.getMenu().getMainCourse().getMainDish()
				.getRecipe().getIngredientList());
		if (dinner.getMenu().getMainCourse().getSideDish() != null)
			ingredientList.addAll(dinner.getMenu().getMainCourse()
					.getSideDish().getRecipe().getIngredientList());

		ingredientList.addAll(snack1.getMenu().getMainCourse().getMainDish()
				.getRecipe().getIngredientList());
		ingredientList.addAll(snack2.getMenu().getMainCourse().getMainDish()
				.getRecipe().getIngredientList());

		return ingredientList;
	}

	public ArrayList<String> getIngredientsString() {
		return ingredientsList;
	}

	public double getCost() {
		return breakfast.getCost() + lunch.getCost() + dinner.getCost()
				+ snack1.getCost() + snack2.getCost();

	}

	public double getAvgDeliveryTime() {
		return (breakfast.getDeliveryTime() + lunch.getDeliveryTime()
				+ dinner.getDeliveryTime() + snack1.getDeliveryTime() + snack2
					.getDeliveryTime()) / 5.0;
	}

}