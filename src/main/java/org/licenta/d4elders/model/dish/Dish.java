package org.licenta.d4elders.model.dish;

import org.licenta.d4elders.model.Recipe;

public abstract class Dish {

	protected Recipe recipeMain;

	public Dish() {

	}

	public Dish(Recipe recipe) {
		recipeMain = recipe;
	}

	@Override
	public String toString() {
		// return foodProperties.getCode() + " - " + foodProperties.getName();
		return recipeMain.getRecipeId() + " - " + recipeMain.getName();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Dish))
			return false;

		return recipeMain.equals(((Dish) other).recipeMain);
	}

	public Recipe getRecipeMain() {
		return recipeMain;
	}

	public float getProteins() {
		return recipeMain.getProteins();
	}

	public float getLipids() {
		return recipeMain.getLipids();
	}

	public float getCarbohydrates() {
		return recipeMain.getCarbohydrates();
	}

	public float getEnergy() {
		return recipeMain.getEnergy();
	}

	public float getCalcium() {
		return recipeMain.getCalcium();
	}

	public float getIron() {
		return recipeMain.getIron();
	}

	public float getSodium() {
		return recipeMain.getSodium();
	}

	public float getVitA() {
		return recipeMain.getVitA();
	}

	public float getVitB() {
		return recipeMain.getVitB();
	}

	public float getVitC() {
		return recipeMain.getVitC();
	}

	public float getVitD() {
		return recipeMain.getVitD();
	}

}