package Meals.Model;

import java.util.*;

/**
 * 
 */
public abstract class Dish {

    protected FoodProperties foodProperties = null; // Ar trebui sa fie mai multe aici? Adica un Dish poate sa aiba mai multe mancaruri ... ?

    public FoodProperties getFoodProperties() {
        return foodProperties;
    }

    public void setFoodProperties(FoodProperties foodProperties) {
        this.foodProperties = foodProperties;
    }
    /**
     * 
     */
    public Dish() {
        this(null);
    }

    public Dish(FoodProperties foodProperties)
    {
        this.foodProperties = foodProperties;
    }

    public Map<String, Double> getNutrientsIdealValuesMap() {
        return foodProperties.getNutrientsIdealValuesMap();
    }

    @Override
    public String toString()
    {
        return foodProperties.getCode() + " - " + foodProperties.getName();
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (!(other instanceof Dish))
            return false;

        return foodProperties.equals(((Dish) other).foodProperties);
    }
}