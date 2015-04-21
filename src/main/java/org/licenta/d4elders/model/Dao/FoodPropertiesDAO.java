package Meals.Model.Dao;

import Meals.Model.FoodProperties;

import java.util.List;

/**
 * Created by root on 22.02.2015.
 */
public interface FoodPropertiesDAO {
    public List<FoodProperties> listFoodProperties();
    public FoodProperties getFoodPropertiesByCode(String code);
}
