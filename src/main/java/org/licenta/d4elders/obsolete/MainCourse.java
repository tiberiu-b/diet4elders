package org.licenta.d4elders.obsolete;

import org.licenta.d4elders.model.Recipe;
import org.licenta.d4elders.model.dish.Dish;

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
			return recipe.getProteins();
		return recipeSide.getProteins() + recipe.getProteins();
	}

	public float getLipids() {
		if (recipeSide == null)
			return recipe.getLipids();
		return recipeSide.getLipids() + recipe.getLipids();
	}

	public float getCarbohydrates() {
		if (recipeSide == null)
			return recipe.getCarbohydrates();
		return recipeSide.getCarbohydrates() + recipe.getCarbohydrates();
	}

	public float getEnergy() {
		if (recipeSide == null)
			return recipe.getEnergy();
		return recipeSide.getEnergy() + recipe.getEnergy();
	}

	public float getCalcium() {
		if (recipeSide == null)
			return recipe.getCalcium();
		return recipeSide.getCalcium() + recipe.getCalcium();
	}

	public float getIron() {
		if (recipeSide == null)
			return recipe.getIron();
		return recipeSide.getIron() + recipe.getIron();
	}

	public float getSodium() {
		if (recipeSide == null)
			return recipe.getSodium();
		return recipeSide.getSodium() + recipe.getSodium();
	}

	public float getVitA() {
		if (recipeSide == null)
			return recipe.getVitA();
		return recipeSide.getVitA() + recipe.getVitA();
	}

	public float getVitB() {
		if (recipeSide == null)
			return recipe.getVitB();
		return recipeSide.getVitB() + recipe.getVitB();
	}

	public float getVitC() {
		if (recipeSide == null)
			return recipe.getVitC();
		return recipeSide.getVitC() + recipe.getVitC();
	}

	public float getVitD() {
		if (recipeSide == null)
			return recipe.getVitD();
		return recipeSide.getVitD() + recipe.getVitD();
	}
}
