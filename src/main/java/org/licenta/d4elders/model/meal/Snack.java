package org.licenta.d4elders.model.meal;

import org.licenta.d4elders.obsolete.MainCourse;

/**
 * 
 */
public class Snack extends Meal {

	/**
     * 
     */
	public Snack() {
		this(null);
	}

	public Snack(MainCourse mainCourse) {
		this.mainCourse = mainCourse;
	}

	@Override
	public String toString() {
		return mainCourse.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Snack))
			return false;

		Snack _other = (Snack) other;
		return mainCourse.equals(_other.mainCourse);
	}

	public float getProteins() {
		return mainCourse.getProteins();
	}

	public float getLipids() {
		return mainCourse.getLipids();
	}

	public float getCarbohydrates() {
		return mainCourse.getCarbohydrates();
	}

	public float getEnergy() {
		return mainCourse.getEnergy();
	}

	public float getCalcium() {
		return mainCourse.getCalcium();
	}

	public float getIron() {
		return mainCourse.getIron();
	}

	public float getSodium() {
		return mainCourse.getSodium();
	}

	public float getVitA() {
		return mainCourse.getVitA();
	}

	public float getVitB() {
		return mainCourse.getVitB();
	}

	public float getVitC() {
		return mainCourse.getVitC();
	}

	public float getVitD() {
		return mainCourse.getVitD();
	}

}