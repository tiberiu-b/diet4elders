package d4elders.dal.helper;

import java.util.Arrays;
import java.util.Collection;

public class IngredientsDataStructure {
	private static int size = 1000;

	private static String[] AvailableIngredients;

	private int[] ingredients = new int[size];

	/*public static void setAvailableIngredients(String[] ingredients){
		AvailableIngredients = ingredients;
	}*/

	public static void setAvailableIngredients(Collection<String> col){
		size = col.size();
		AvailableIngredients = new String[size];

		col.toArray(AvailableIngredients);
		Arrays.sort(AvailableIngredients);

		System.out.println("Available Ingredients: ");
		for(int i = 0; i < AvailableIngredients.length; ++i){
			System.out.println(i + " " + AvailableIngredients[i]);
		}
	}

	public boolean contains(String ing){
		int index = getIndex(ing);
		if(index < 0)
			return false;

		return ingredients[index] != 0;
	}

	/**
	 * Called in DailiyMenu
	 * @param source
	 */
	public void addAll(IngredientsDataStructure source){
		addAll(source.ingredients);
	}

	public void clear(){
		for(int i = ingredients.length-1; i >= 0 ; --i){
			ingredients[i] = 0;
		}
	}

	@Override
	public String toString(){
		String ings = "";
		for(int i = ingredients.length-1; i >= 0 ; --i){
			if(ingredients[i] != 0){
				ings += AvailableIngredients[i] + " ";
			}
		}
		return ings;
	}

	/**
	 * Called in Menu - initialization phase
	 * @param col
	 */
	public void setIngredients(Collection<String> col){
		int index = 0;
		for(String ing : col){
			index = getIndex(ing);
			ingredients[index] = 1;
		}
	}

	private void addAll(int[] source){
		// for each ingredient
		for(int i = ingredients.length-1; i >= 0 ; --i){
			// if exists in source
			if(source[i] != 0){
				// add it
				ingredients[i] = 1;
			}
		}
	}

	private int getIndex(String ing){
		return Arrays.binarySearch(AvailableIngredients, ing);
	}
}
