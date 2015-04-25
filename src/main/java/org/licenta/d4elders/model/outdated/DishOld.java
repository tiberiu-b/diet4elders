package org.licenta.d4elders.model.outdated;
import java.util.*;

/**
 * 
 */
public abstract class DishOld {

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
    public DishOld() {
        this(null);
    }

    public DishOld(FoodProperties foodProperties)
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

        if (!(other instanceof DishOld))
            return false;

        return foodProperties.equals(((DishOld) other).foodProperties);
    }
}