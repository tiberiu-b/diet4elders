package org.licenta.d4elders.model.meal;
import java.util.*;

import org.licenta.d4elders.model.dish.Desert;
import org.licenta.d4elders.model.dish.MainCourse;
import org.licenta.d4elders.model.outdated.MapHelper;

/**
 * 
 */
public class Breakfast extends BasicMeal {

    protected MainCourse mainCourse;
    protected Desert desert;

    public MainCourse getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(MainCourse mainCourse) {
        this.mainCourse = mainCourse;
    }

    public Desert getDesert() {
        return desert;
    }

    public void setDesert(Desert desert) {
        this.desert = desert;
    }

    /**
     * 
     */
    public Breakfast()
    {
        this(null, null);
    }

    public Breakfast(MainCourse mainCourse, Desert desert)
    {
        this.mainCourse = mainCourse;
        this.desert = desert;
    }

    @Override
    public Map<String, Double> computeNutrientsValues()
    {
        nutrientsValuesMap = MapHelper.addNutrientsMaps(
                mainCourse.getNutrientsIdealValuesMap(),
                desert.getNutrientsIdealValuesMap());

        return getNutrientsValuesMap();
    }

    @Override
    public String toString()
    {
        return mainCourse.toString() + " " + desert.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (!(other instanceof Breakfast))
            return false;

        Breakfast _other = (Breakfast) other;
        return mainCourse.equals(_other.mainCourse) &&
                desert.equals(_other.desert);
    }

}