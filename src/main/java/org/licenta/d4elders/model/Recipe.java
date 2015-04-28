package org.licenta.d4elders.model;


public class Recipe extends FoodNutrients {
	private int recipeId;
	private String name;
	private String description;

	// private int dishType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public boolean equals(Recipe rec) {
		if (rec.recipeId != this.recipeId)
			return false;

		return true;
	}
}
