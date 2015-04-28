package org.licenta.d4elders.model.dish;

import org.licenta.d4elders.model.Recipe;

public class MainCourse extends Dish {

	protected Recipe recipeSide;

	public Recipe getRecipeSide() {
		return recipeSide;
	}

	public void setRecipeSide(Recipe recipeSide) {
		this.recipeSide = recipeSide;
	}

	public MainCourse(Recipe recipe) {
		super(recipe);
	}

	public MainCourse(Recipe recipe, Recipe recipeSide) {
		super(recipe);
		this.recipeSide = recipeSide;
	}

	public float getProteins() {
		if (recipeSide == null)
			return recipeMain.getProteins();
		return recipeSide.getProteins() + recipeMain.getProteins();
	}

	public float getLipids() {
		if (recipeSide == null)
			return recipeMain.getLipids();
		return recipeSide.getLipids() + recipeMain.getLipids();
	}

	public float getCarbohydrates() {
		if (recipeSide == null)
			return recipeMain.getCarbohydrates();
		return recipeSide.getCarbohydrates() + recipeMain.getCarbohydrates();
	}

	public float getEnergy() {
		if (recipeSide == null)
			return recipeMain.getEnergy();
		return recipeSide.getEnergy() + recipeMain.getEnergy();
	}

	public float getCalcium() {
		if (recipeSide == null)
			return recipeMain.getCalcium();
		return recipeSide.getCalcium() + recipeMain.getCalcium();
	}

	public float getIron() {
		if (recipeSide == null)
			return recipeMain.getIron();
		return recipeSide.getIron() + recipeMain.getIron();
	}

	public float getSodium() {
		if (recipeSide == null)
			return recipeMain.getSodium();
		return recipeSide.getSodium() + recipeMain.getSodium();
	}

	public float getVitA() {
		if (recipeSide == null)
			return recipeMain.getVitA();
		return recipeSide.getVitA() + recipeMain.getVitA();
	}

	public float getVitB() {
		if (recipeSide == null)
			return recipeMain.getVitB();
		return recipeSide.getVitB() + recipeMain.getVitB();
	}

	public float getVitC() {
		if (recipeSide == null)
			return recipeMain.getVitC();
		return recipeSide.getVitC() + recipeMain.getVitC();
	}

	public float getVitD() {
		if (recipeSide == null)
			return recipeMain.getVitD();
		return recipeSide.getVitD() + recipeMain.getVitD();
	}
}
