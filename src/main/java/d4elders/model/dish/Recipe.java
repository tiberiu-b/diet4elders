package d4elders.model.dish;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import d4elders.model.FoodNutrients;

public class Recipe extends FoodNutrients implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int recipeId;
	private String name;
	private String description;
	private DishType dishType;
	private ArrayList<String> ingredientList;
	private HashSet<String> ingredientsHashSet;

	public Recipe() {
		ingredientList = new ArrayList<>();
	}

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

	public DishType getDishType() {
		return dishType;
	}

	public void setDishType(DishType dishType) {
		this.dishType = dishType;
	}

	public ArrayList<String> getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(ArrayList<String> ingredientList) {
		this.ingredientList = ingredientList;
	}
}
