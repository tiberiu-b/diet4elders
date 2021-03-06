package org.licenta.d4elders.model.meal;

import org.licenta.d4elders.model.dish.*;

/**
 * 
 */
public class Lunch extends Meal {
	protected StarterDish starterDish;
	protected Desert desert;

	public StarterDish getStarterDish() {
		return starterDish;
	}

	public void setStarterDish(StarterDish starterDish) {
		this.starterDish = starterDish;
	}

	public Desert getDesert() {
		return desert;
	}

	public void setDesert(Desert desert) {
		this.desert = desert;
	}

	/**
     * 
     */
	public Lunch() {
		this(null, null, null);
	}

	public Lunch(StarterDish starterDish, MainCourse mainCourse, Desert desert) {
		this.starterDish = starterDish;
		this.mainCourse = mainCourse;
		this.desert = desert;
	}

	@Override
	public String toString() {
		return starterDish.toString() + " " + mainCourse.toString() + " " + desert.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Lunch))
			return false;

		Lunch _other = (Lunch) other;
		return starterDish.equals(_other.starterDish) && mainCourse.equals(_other.mainCourse)
				&& desert.equals(_other.desert);
	}

	public float getProteins() {
		return desert.getProteins() + mainCourse.getProteins() + starterDish.getProteins();
	}

	public float getLipids() {
		return desert.getLipids() + mainCourse.getLipids() + starterDish.getLipids();
	}

	public float getCarbohydrates() {
		return desert.getCarbohydrates() + mainCourse.getCarbohydrates() + starterDish.getCarbohydrates();
	}

	public float getEnergy() {
		return desert.getEnergy() + mainCourse.getEnergy() + starterDish.getEnergy();
	}

	public float getCalcium() {
		return desert.getCalcium() + mainCourse.getCalcium() + starterDish.getCalcium();
	}

	public float getIron() {
		return desert.getIron() + mainCourse.getIron() + starterDish.getIron();
	}

	public float getSodium() {
		return desert.getSodium() + mainCourse.getSodium() + starterDish.getSodium();
	}

	public float getVitA() {
		return desert.getVitA() + mainCourse.getVitA() + starterDish.getVitA();
	}

	public float getVitB() {
		return desert.getVitB() + mainCourse.getVitB() + starterDish.getVitB();
	}

	public float getVitC() {
		return desert.getVitC() + mainCourse.getVitC() + starterDish.getVitC();
	}

	public float getVitD() {
		return desert.getVitD() + mainCourse.getVitD() + starterDish.getVitD();
	}
}