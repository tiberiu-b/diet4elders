package org.licenta.d4elders.model.meal;

import org.licenta.d4elders.model.dish.Desert;
import org.licenta.d4elders.model.dish.MainCourse;

/**
 * 
 */
public class Breakfast extends Meal {

	protected Desert desert;

	public Desert getDesert() {
		return desert;
	}

	public void setDesert(Desert desert) {
		this.desert = desert;
	}

	/**
     * 
     */
	public Breakfast() {
		this(null, null);
	}

	public Breakfast(MainCourse mainCourse, Desert desert) {
		this.mainCourse = mainCourse;
		this.desert = desert;
	}

	@Override
	public String toString() {
		return mainCourse.toString() + " " + desert.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Breakfast))
			return false;

		Breakfast _other = (Breakfast) other;
		return mainCourse.equals(_other.mainCourse) && desert.equals(_other.desert);
	}

	public float getProteins() {
		return desert.getProteins() + mainCourse.getProteins();
	}

	public float getLipids() {
		return desert.getLipids() + mainCourse.getLipids();
	}

	public float getCarbohydrates() {
		return desert.getCarbohydrates() + mainCourse.getCarbohydrates();
	}

	public float getEnergy() {
		return desert.getEnergy() + mainCourse.getEnergy();
	}

	public float getCalcium() {
		return desert.getCalcium() + mainCourse.getCalcium();
	}

	public float getIron() {
		return desert.getIron() + mainCourse.getIron();
	}

	public float getSodium() {
		return desert.getSodium() + mainCourse.getSodium();
	}

	public float getVitA() {
		return desert.getVitA() + mainCourse.getVitA();
	}

	public float getVitB() {
		return desert.getVitB() + mainCourse.getVitB();
	}

	public float getVitC() {
		return desert.getVitC() + mainCourse.getVitC();
	}

	public float getVitD() {
		return desert.getVitD() + mainCourse.getVitD();
	}

}