package org.licenta.d4elders.dao;
import org.licenta.d4elders.model.outdated.FoodProperties;

import java.util.List;

/**
 * Created by root on 22.02.2015.
 */
public interface FoodPropertiesDAO {
    public List<FoodProperties> listFoodProperties();
    public FoodProperties getFoodPropertiesByCode(String code);
}
