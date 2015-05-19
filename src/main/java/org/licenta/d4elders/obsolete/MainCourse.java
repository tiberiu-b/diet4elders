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
			return getRecipe().getProteins();
		return recipeSide.getProteins() + getRecipe().getProteins();
	}

	public float getLipids() {
		if (recipeSide == null)
			return getRecipe().getLipids();
		return recipeSide.getLipids() + getRecipe().getLipids();
	}

	public float getCarbohydrates() {
		if (recipeSide == null)
			return getRecipe().getCarbohydrates();
		return recipeSide.getCarbohydrates() + getRecipe().getCarbohydrates();
	}

	public float getEnergy() {
		if (recipeSide == null)
			return getRecipe().getEnergy();
		return recipeSide.getEnergy() + getRecipe().getEnergy();
	}

	public float getCalcium() {
		if (recipeSide == null)
			return getRecipe().getCalcium();
		return recipeSide.getCalcium() + getRecipe().getCalcium();
	}

	public float getIron() {
		if (recipeSide == null)
			return getRecipe().getIron();
		return recipeSide.getIron() + getRecipe().getIron();
	}

	public float getSodium() {
		if (recipeSide == null)
			return getRecipe().getSodium();
		return recipeSide.getSodium() + getRecipe().getSodium();
	}

	public float getVitA() {
		if (recipeSide == null)
			return getRecipe().getVitA();
		return recipeSide.getVitA() + getRecipe().getVitA();
	}

	public float getVitB() {
		if (recipeSide == null)
			return getRecipe().getVitB();
		return recipeSide.getVitB() + getRecipe().getVitB();
	}

	public float getVitC() {
		if (recipeSide == null)
			return getRecipe().getVitC();
		return recipeSide.getVitC() + getRecipe().getVitC();
	}

	public float getVitD() {
		if (recipeSide == null)
			return getRecipe().getVitD();
		return recipeSide.getVitD() + getRecipe().getVitD();
	}
}
