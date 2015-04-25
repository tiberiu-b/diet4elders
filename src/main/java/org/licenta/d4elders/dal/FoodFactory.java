package org.licenta.d4elders.dal;
import org.licenta.d4elders.dao.FoodPropertiesDAOImpl;
import org.licenta.d4elders.model.outdated.FoodProperties;

import java.util.*;

/**
 * TODO: Bad naming - factory means it creates new instances, which it doesn't.
 * Maybe FoodFetcher ?
 * Stores the list of food items.
 */
public class FoodFactory {

    private final static List<FoodProperties> foodPropertiesList;
    /**
     * 
     */
    static {
        // Get all the food items
        foodPropertiesList = new FoodPropertiesDAOImpl().listFoodProperties();
    }

    static public synchronized FoodProperties getRandomFoodProperties()
    {
        Random r = new Random();
        return foodPropertiesList.get(r.nextInt(foodPropertiesList.size()));
    }
}