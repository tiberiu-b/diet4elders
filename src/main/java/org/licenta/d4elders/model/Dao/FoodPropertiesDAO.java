package org.licenta.d4elders.model.Dao;
import org.licenta.d4elders.model.FoodProperties;

import java.util.List;

/**
 * Created by root on 22.02.2015.
 */
public interface FoodPropertiesDAO {
    public List<FoodProperties> listFoodProperties();
    public FoodProperties getFoodPropertiesByCode(String code);
}
