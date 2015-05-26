package d4elders.model.meal;

import java.io.Serializable;

import d4elders.model.FoodNutrients;
import d4elders.model.dish.Dish;
import d4elders.model.dish.DishTypeGeneral;

public class MealVariant extends FoodNutrients implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mealVariantId;
	private String diet;
	private MealType mealType;
	private DishTypeGeneral dishType;
	private Dish mainDish;
	private Dish sideDish;

	public int getMealVariantId() {
		return mealVariantId;
	}

	public void setMealVariantId(int mealVariantId) {
		this.mealVariantId = mealVariantId;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public DishTypeGeneral getDishType() {
		return dishType;
	}

	public void setDishType(DishTypeGeneral dishType) {
		this.dishType = dishType;
	}

	public Dish getMainDish() {
		return mainDish;
	}

	public void setMainDish(Dish mainDish) {
		this.mainDish = mainDish;
	}

	public Dish getSideDish() {
		return sideDish;
	}

	public void setSideDish(Dish sideDish) {
		this.sideDish = sideDish;
	}

	public Float getProteins_custom() {
		if (sideDish == null)
			return mainDish.getProteins();
		else
			return mainDish.getProteins() + sideDish.getProteins();
	}

	public Float getLipids_custom() {
		if (sideDish == null)
			return mainDish.getLipids();
		else
			return mainDish.getLipids() + sideDish.getLipids();
	}

	public Float getCarbohydrates_custom() {

		if (sideDish == null)
			return mainDish.getCarbohydrates();
		else
			return mainDish.getCarbohydrates() + sideDish.getCarbohydrates();
	}

	public Float getEnergy_custom() {
		if (sideDish == null)
			return mainDish.getEnergy();
		else
			return mainDish.getEnergy() + sideDish.getEnergy();
	}

	public Float getCalcium_custom() {
		if (sideDish == null)
			return mainDish.getCalcium();
		else
			return mainDish.getCalcium() + sideDish.getCalcium();
	}

	public Float getIron_custom() {
		if (sideDish == null)
			return mainDish.getIron();
		else
			return mainDish.getIron() + sideDish.getIron();
	}

	public Float getSodium_custom() {
		if (sideDish == null)
			return mainDish.getSodium();
		else
			return mainDish.getSodium() + sideDish.getSodium();
	}

	public Float getVitA_custom() {
		if (sideDish == null)
			return mainDish.getVitA();
		else
			return mainDish.getVitA() + sideDish.getVitA();
	}

	public Float getVitB_custom() {
		if (sideDish == null)
			return mainDish.getVitB();
		else
			return mainDish.getVitB() + sideDish.getVitB();
	}

	public Float getVitC_custom() {
		if (sideDish == null)
			return mainDish.getVitC();
		else
			return mainDish.getVitC() + sideDish.getVitC();
	}

	public Float getVitD_custom() {
		if (sideDish == null)
			return mainDish.getVitD();
		else
			return mainDish.getVitD() + sideDish.getVitD();
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

	public Float getProteins() {
		if (proteins == null)
			computeNutrientValues();
		return proteins;
	}

	public Float getLipids() {
		if (lipids == null)
			computeNutrientValues();
		return lipids;
	}

	public Float getCarbohydrates() {
		if (carbohydrates == null)
			computeNutrientValues();
		return carbohydrates;
	}

	public Float getEnergy() {
		if (energy == null)
			computeNutrientValues();
		return energy;
	}

	public Float getCalcium() {
		if (calcium == null)
			computeNutrientValues();
		return calcium;
	}

	public Float getIron() {
		if (iron == null)
			computeNutrientValues();
		return iron;
	}

	public Float getSodium() {
		if (sodium == null)
			computeNutrientValues();
		return sodium;
	}

	public Float getVitA() {
		if (vitA == null)
			computeNutrientValues();
		return vitA;
	}

	public Float getVitB() {
		if (vitB == null)
			computeNutrientValues();
		return vitB;
	}

	public Float getVitC() {
		if (vitC == null)
			computeNutrientValues();
		return vitC;
	}

	public Float getVitD() {
		if (vitD == null)
			computeNutrientValues();
		return vitD;
	}

	public String toString() {
		if (sideDish != null)
			return mainDish.toString() + " with " + sideDish.toString();
		else
			return mainDish.toString() + " ";
	}
}
