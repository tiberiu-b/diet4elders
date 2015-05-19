package org.licenta.d4elders.model.dish;

import org.licenta.d4elders.model.Recipe;

public class Dish {

	private Recipe recipe;
	private int dishId;

	public Dish() {

	}

	public Dish(Recipe recipe) {
		this.setRecipe(recipe);
	}

	@Override
	public String toString() {
		// return foodProperties.getCode() + " - " + foodProperties.getName();
		return this.getRecipe().getRecipeId() + " - " + this.getRecipe().getName();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Dish))
			return false;

		return this.getRecipe().equals(((Dish) other).getRecipe());
	}

	public Recipe getRecipe() {
		return this.recipe;
	}

	public float getProteins() {
		return this.getRecipe().getProteins();
	}

	public float getLipids() {
		return this.getRecipe().getLipids();
	}

	public float getCarbohydrates() {
		return this.getRecipe().getCarbohydrates();
	}

	public float getEnergy() {
		return this.getRecipe().getEnergy();
	}

	public float getCalcium() {
		return this.getRecipe().getCalcium();
	}

	public float getIron() {
		return this.getRecipe().getIron();
	}

	public float getSodium() {
		return this.getRecipe().getSodium();
	}

	public float getVitA() {
		return this.getRecipe().getVitA();
	}

	public float getVitB() {
		return this.getRecipe().getVitB();
	}

	public float getVitC() {
		return this.getRecipe().getVitC();
	}

	public float getVitD() {
		return this.getRecipe().getVitD();
	}

	public int getDishId() {
		return dishId;
	}

	public void setDishId(int dishId) {
		this.dishId = dishId;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}