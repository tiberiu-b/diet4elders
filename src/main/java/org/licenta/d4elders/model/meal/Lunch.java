package org.licenta.d4elders.model.meal;
import java.util.*;

import org.licenta.d4elders.model.dish.Desert;
import org.licenta.d4elders.model.dish.MainCourse;
import org.licenta.d4elders.model.dish.StarterDish;
import org.licenta.d4elders.model.outdated.MapHelper;

/**
 * 
 */
public class Lunch extends BasicMeal{
    protected StarterDish starterDish;
    protected MainCourse mainCourse;
    protected Desert desert;

    public StarterDish getStarterDish() {
        return starterDish;
    }

    public void setStarterDish(StarterDish starterDish) {
        this.starterDish = starterDish;
    }

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
    public Lunch()
    {
        this(null, null, null);
    }

    public Lunch(StarterDish starterDish, MainCourse mainCourse, Desert desert)
    {
        this.starterDish = starterDish;
        this.mainCourse = mainCourse;
        this.desert = desert;
    }

    @Override
    public Map<String, Double> computeNutrientsValues()
    {
        nutrientsValuesMap = MapHelper.addNutrientsMaps(
                starterDish.getNutrientsIdealValuesMap(),
                mainCourse.getNutrientsIdealValuesMap(),
                desert.getNutrientsIdealValuesMap());

        return getNutrientsValuesMap();
    }


    @Override
    public String toString()
    {
        return starterDish.toString() + " " + mainCourse.toString() + " " + desert.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (!(other instanceof Lunch))
            return false;

        Lunch _other = (Lunch) other;
        return starterDish.equals(_other.starterDish)   &&
                mainCourse.equals(_other.mainCourse)    &&
                desert.equals(_other.desert);
    }
}