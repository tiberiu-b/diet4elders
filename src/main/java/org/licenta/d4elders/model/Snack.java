package Meals.Model;

import java.util.*;

/**
 * 
 */
public class Snack extends BasicMeal{
    protected MainCourse mainCourse;

    public MainCourse getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(MainCourse mainCourse) {
        this.mainCourse = mainCourse;
    }

    /**
     * 
     */
    public Snack() {
        this(null);
    }

    public Snack(MainCourse mainCourse)
    {
        this.mainCourse = mainCourse;
    }

    @Override
    public Map<String, Double> computeNutrientsValues()
    {
        nutrientsValuesMap = mainCourse.getNutrientsIdealValuesMap();
        return getNutrientsValuesMap();
    }


    @Override
    public String toString()
    {
        return mainCourse.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (!(other instanceof Snack))
            return false;

        Snack _other = (Snack) other;
        return mainCourse.equals(_other.mainCourse);
    }
}