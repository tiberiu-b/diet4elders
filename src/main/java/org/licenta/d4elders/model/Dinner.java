package Meals.Model;

import java.util.*;

/**
 * 
 */
public class Dinner extends BasicMeal{
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
    public Dinner()
    {
        this(null, null, null);
    }

    public Dinner(StarterDish starterDish, MainCourse mainCourse, Desert desert)
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

        if (!(other instanceof Dinner))
            return false;

        Dinner _other = (Dinner) other;
        return starterDish.equals(_other.starterDish)   &&
                mainCourse.equals(_other.mainCourse)    &&
                desert.equals(_other.desert);
    }
}