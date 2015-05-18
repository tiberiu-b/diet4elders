package org.licenta.d4elders.model;

public class Menu extends FoodNutrients {
	private int menuId;
	private MealVariant starter;
	private MealVariant mainCourse;
	private MealVariant desert;
	private FoodServiceProvider foodProvider;
	private Float cost;
	private int deliveryTime;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public FoodServiceProvider getFoodProvider() {
		return foodProvider;
	}

	public void setFoodProvider(FoodServiceProvider foodProvider) {
		this.foodProvider = foodProvider;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
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
		if (starter == null) {
			return mainCourse.getProteins() + desert.getProteins();
		} else {
			return starter.getProteins() + mainCourse.getProteins() + desert.getProteins();
		}
	}

	public Float getLipids_custom() {
		if (starter == null) {
			return mainCourse.getLipids() + desert.getLipids();
		} else {
			return starter.getLipids() + mainCourse.getLipids() + desert.getLipids();
		}
	}

	public Float getCarbohydrates_custom() {
		if (starter == null) {
			return mainCourse.getCarbohydrates() + desert.getCarbohydrates();
		} else {
			return starter.getCarbohydrates() + mainCourse.getCarbohydrates() + desert.getCarbohydrates();
		}
	}

	public Float getEnergy_custom() {
		if (starter == null) {
			return mainCourse.getEnergy() + desert.getEnergy();
		} else {
			return starter.getEnergy() + mainCourse.getEnergy() + desert.getEnergy();
		}
	}

	public Float getCalcium_custom() {
		if (starter == null) {
			return mainCourse.getCalcium() + desert.getCalcium();
		} else {
			return starter.getCalcium() + mainCourse.getCalcium() + desert.getCalcium();
		}
	}

	public Float getIron_custom() {
		if (starter == null) {
			return mainCourse.getIron() + desert.getIron();
		} else {
			return starter.getIron() + mainCourse.getIron() + desert.getIron();
		}
	}

	public Float getSodium_custom() {
		if (starter == null) {
			return mainCourse.getSodium() + desert.getSodium();
		} else {
			return starter.getSodium() + mainCourse.getSodium() + desert.getSodium();
		}
	}

	public Float getVitA_custom() {
		if (starter == null) {
			return mainCourse.getVitA() + desert.getVitA();
		} else {
			return starter.getVitA() + mainCourse.getVitA() + desert.getVitA();
		}
	}

	public Float getVitB_custom() {
		if (starter == null) {
			return mainCourse.getVitB() + desert.getVitB();
		} else {
			return starter.getVitB() + mainCourse.getVitB() + desert.getVitB();
		}
	}

	public Float getVitC_custom() {
		if (starter == null) {
			return mainCourse.getVitC() + desert.getVitC();
		} else {
			return starter.getVitC() + mainCourse.getVitC() + desert.getVitC();
		}
	}

	public Float getVitD_custom() {
		if (starter == null) {
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
