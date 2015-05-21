package org.licenta.d4elders.model;

import java.io.Serializable;

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

	/**
     * 
     */
	public DailyMenu() {
	}

	public DailyMenu(FoodProviderPackage breakfast, FoodProviderPackage lunch, FoodProviderPackage dinner,
			FoodProviderPackage snack1, FoodProviderPackage snack2) {
		this.breakfast = breakfast;
		this.dinner = dinner;
		this.lunch = lunch;
		this.snack1 = snack1;
		this.snack2 = snack2;
		computeNutrientValues();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof DailyMenu))
			return false;

		DailyMenu _other = (DailyMenu) other;
		return breakfast.equals(_other.breakfast) && dinner.equals(_other.dinner) && lunch.equals(_other.lunch)
				&& snack1.equals(_other.snack1) && snack2.equals(_other.snack2);
	}

	@Override
	public String toString() {
		return "	B: " + breakfast.toString() + "\n	L: " + lunch.toString() + "\n	D:" + dinner.toString() + "\n	S1:"
				+ snack1.toString() + "\n	S2:" + snack2.toString();
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

	public Float getProteins_custom() {
		return breakfast.getMenu().getProteins() + lunch.getMenu().getProteins() + dinner.getMenu().getProteins()
				+ snack1.getMenu().getProteins() + snack2.getMenu().getProteins();

	}

	public Float getLipids_custom() {
		return breakfast.getMenu().getLipids() + lunch.getMenu().getLipids() + dinner.getMenu().getLipids()
				+ snack1.getMenu().getLipids() + snack2.getMenu().getLipids();
	}

	public Float getCarbohydrates_custom() {
		return breakfast.getMenu().getCarbohydrates() + lunch.getMenu().getCarbohydrates()
				+ dinner.getMenu().getCarbohydrates() + snack1.getMenu().getCarbohydrates()
				+ snack2.getMenu().getCarbohydrates();
	}

	public Float getEnergy_custom() {
		return breakfast.getMenu().getEnergy() + lunch.getMenu().getEnergy() + dinner.getMenu().getEnergy()
				+ snack1.getMenu().getEnergy() + snack2.getMenu().getEnergy();
	}

	public Float getCalcium_custom() {
		return breakfast.getMenu().getCalcium() + lunch.getMenu().getCalcium() + dinner.getMenu().getCalcium()
				+ snack1.getMenu().getCalcium() + snack2.getMenu().getCalcium();
	}

	public Float getIron_custom() {
		return breakfast.getMenu().getIron() + lunch.getMenu().getIron() + dinner.getMenu().getIron()
				+ snack1.getMenu().getIron() + snack2.getMenu().getIron();
	}

	public Float getSodium_custom() {
		return breakfast.getMenu().getSodium() + lunch.getMenu().getSodium() + dinner.getMenu().getSodium()
				+ snack1.getMenu().getSodium() + snack2.getMenu().getSodium();
	}

	public Float getVitA_custom() {
		return breakfast.getMenu().getVitA() + lunch.getMenu().getVitA() + dinner.getMenu().getVitA()
				+ snack1.getMenu().getVitA() + snack2.getMenu().getVitA();
	}

	public Float getVitB_custom() {
		return breakfast.getMenu().getVitB() + lunch.getMenu().getVitB() + dinner.getMenu().getVitB()
				+ snack1.getMenu().getVitB() + snack2.getMenu().getVitB();
	}

	public Float getVitC_custom() {
		return breakfast.getMenu().getVitC() + lunch.getMenu().getVitC() + dinner.getMenu().getVitC()
				+ snack1.getMenu().getVitC() + snack2.getMenu().getVitC();
	}

	public Float getVitD_custom() {
		return breakfast.getMenu().getVitD() + lunch.getMenu().getVitD() + dinner.getMenu().getVitD()
				+ snack1.getMenu().getVitD() + snack2.getMenu().getVitD();
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

}