package org.licenta.d4elders.model;

/**
 * 
 */
public class DailyMenu extends FoodNutrients {
	protected Menu breakfast;
	protected Menu dinner;
	protected Menu lunch;
	protected Menu snack1, snack2;

	/**
     * 
     */
	public DailyMenu() {
	}

	public DailyMenu(Menu breakfast, Menu lunch, Menu dinner, Menu snack1, Menu snack2) {
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

	public Menu getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(Menu breakfast) {
		this.breakfast = breakfast;
	}

	public Menu getDinner() {
		return dinner;
	}

	public void setDinner(Menu dinner) {
		this.dinner = dinner;
	}

	public Menu getLunch() {
		return lunch;
	}

	public void setLunch(Menu lunch) {
		this.lunch = lunch;
	}

	public Menu getSnack1() {
		return snack1;
	}

	public void setSnack1(Menu snack1) {
		this.snack1 = snack1;
	}

	public Menu getSnack2() {
		return snack2;
	}

	public void setSnack2(Menu snack2) {
		this.snack2 = snack2;
	}

	public Float getProteins_custom() {
		return breakfast.getProteins() + lunch.getProteins() + dinner.getProteins() + snack1.getProteins()
				+ snack2.getProteins();

	}

	public Float getLipids_custom() {
		return breakfast.getLipids() + lunch.getLipids() + dinner.getLipids() + snack1.getLipids() + snack2.getLipids();
	}

	public Float getCarbohydrates_custom() {
		return breakfast.getCarbohydrates() + lunch.getCarbohydrates() + dinner.getCarbohydrates()
				+ snack1.getCarbohydrates() + snack2.getCarbohydrates();
	}

	public Float getEnergy_custom() {
		return breakfast.getEnergy() + lunch.getEnergy() + dinner.getEnergy() + snack1.getEnergy() + snack2.getEnergy();
	}

	public Float getCalcium_custom() {
		return breakfast.getCalcium() + lunch.getCalcium() + dinner.getCalcium() + snack1.getCalcium()
				+ snack2.getCalcium();
	}

	public Float getIron_custom() {
		return breakfast.getIron() + lunch.getIron() + dinner.getIron() + snack1.getIron() + snack2.getIron();
	}

	public Float getSodium_custom() {
		return breakfast.getSodium() + lunch.getSodium() + dinner.getSodium() + snack1.getSodium() + snack2.getSodium();
	}

	public Float getVitA_custom() {
		return breakfast.getVitA() + lunch.getVitA() + dinner.getVitA() + snack1.getVitA() + snack2.getVitA();
	}

	public Float getVitB_custom() {
		return breakfast.getVitB() + lunch.getVitB() + dinner.getVitB() + snack1.getVitB() + snack2.getVitB();
	}

	public Float getVitC_custom() {
		return breakfast.getVitC() + lunch.getVitC() + dinner.getVitC() + snack1.getVitC() + snack2.getVitC();
	}

	public Float getVitD_custom() {
		return breakfast.getVitD() + lunch.getVitD() + dinner.getVitD() + snack1.getVitD() + snack2.getVitD();
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