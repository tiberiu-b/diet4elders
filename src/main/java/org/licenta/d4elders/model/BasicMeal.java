package Meals.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cristiprg on 02.03.2015.
 */
public abstract class BasicMeal {
    protected Map<String, Double> nutrientsValuesMap = null;

    public Map<String, Double> getNutrientsValuesMap() {
        return nutrientsValuesMap;
    }

    public abstract Map<String, Double> computeNutrientsValues();
}
