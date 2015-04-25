package org.licenta.d4elders.helper;


/**
 * Created by cristiprg on 21.04.2015.
 */
public class HbmoNutrientNotFoundException extends HbmoException {
    private String nutrientNotFound = null;

    public HbmoNutrientNotFoundException(String nutrientNotFound){
        super("Nutrient not found: " + nutrientNotFound);
        this.nutrientNotFound = nutrientNotFound;
    }

    public String getNutrientNotFound() {
        return nutrientNotFound;
    }
}
