package org.licenta.d4elders.model.dish;

import org.licenta.d4elders.model.Recipe;

public abstract class Dish {

	private Recipe recipe;
	private int dishId;

	public Dish() {

	}

	public Dish(Recipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public String toString() {
		// return foodProperties.getCode() + " - " + foodProperties.getName();
		return this.recipe.getRecipeId() + " - " + this.recipe.getName();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Dish))
			return false;

		return this.recipe.equals(((Dish) other).getRecipe());
	}

	public Recipe getRecipe() {
		return this.recipe;
	}

	public float getProteins() {
		return this.recipe.getProteins();
	}

	public float getLipids() {
		return this.recipe.getLipids();
	}

	public float getCarbohydrates() {
		return this.recipe.getCarbohydrates();
	}

	public float getEnergy() {
		return this.recipe.getEnergy();
	}

	public float getCalcium() {
		return this.recipe.getCalcium();
	}

	public float getIron() {
		return this.recipe.getIron();
	}

	public float getSodium() {
		return this.recipe.getSodium();
	}

	public float getVitA() {
		return this.recipe.getVitA();
	}

	public float getVitB() {
		return this.recipe.getVitB();
	}

	public float getVitC() {
		return this.recipe.getVitC();
	}

	public float getVitD() {
		return this.recipe.getVitD();
	}

	public int getDishId() {
		return dishId;
	}

	public void setDishId(int dishId) {
		this.dishId = dishId;
	}

}