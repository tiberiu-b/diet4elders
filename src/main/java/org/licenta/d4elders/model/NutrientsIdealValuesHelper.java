package org.licenta.d4elders.model;

import org.licenta.d4elders.model.user_profile.UserProfileStub;

import java.util.logging.Logger;

/**
 * Created by cristiprg on 02.03.2015.
 * Business logic for data related to the ideal nutritional values.
 */
public class NutrientsIdealValuesHelper {
    private static final Logger log = Logger.getLogger( NutrientsIdealValuesHelper.class.getName() );
    /*
    private static Map<String, Double> nutrientsIdealValuesMap =
            new NutrientsIdealValuesDAOImpl(). // get new instance of NutrientsIdealValuesDAO
                    getNutrientsIdealValuesById(1). // get new instance of NutrientsIdealValues - index 1 TODO: different ideals for different diets
                    getNutrientsIdealValuesMap(); // get the map only
                    */
/*
    private static Map<String, Double> nutrientsIdealValuesMap =
            new NutritionalInformation(
                    NutritionalInformationSourceType.GeneralRecommendation,
                    new UserProfileStub())
                    .combineFinalDoctorPrescription(null)
                    .getFixedIdealNutrientsMap();
*/
    /**
     * Cache a nutrient because there will be multiple calls for each nutrient.
     */
    private static SingleNutrientInformation cachedNutrient = null;

    private static NutritionalInformation nutritionalInformation = new NutritionalInformation(
            NutritionalInformationSourceType.GeneralRecommendation,
            new UserProfileStub());

    public static synchronized Double getIdealValueForNutrient(String nutrientName) throws HbmoNutrientNotFoundException {
        SingleNutrientInformation nutrient = getSingleNutrientInformation(nutrientName);
        return nutrient.getFixedValue();
    }

    public static synchronized Double[] getIdealIntervalForNutrient(String nutrientName) throws HbmoNutrientNotFoundException {
        SingleNutrientInformation nutrient = getSingleNutrientInformation(nutrientName);
        return new Double[] {nutrient.getLowerLimit(), nutrient.getUpperLimit()};
    }

    public static synchronized boolean nutrientHasInterval(String nutrientName) throws HbmoNutrientNotFoundException{
        SingleNutrientInformation nutrient = getSingleNutrientInformation(nutrientName);
        return nutrient.getLowerLimit() != null && nutrient.getUpperLimit() != null;
    }

    public static synchronized Integer getWeightForNutrient(String nutrientName)throws HbmoNutrientNotFoundException{
        SingleNutrientInformation nutrient = nutritionalInformation.getNutrient(nutrientName);
        return nutrient.getWeight();
    }

    private static SingleNutrientInformation getSingleNutrientInformation(String nutrientName) throws HbmoNutrientNotFoundException{
        if(cachedNutrient != null && cachedNutrient.getName().equals(nutrientName))
            return cachedNutrient;

        SingleNutrientInformation nutrient = nutritionalInformation.getNutrient(nutrientName);
        if(nutrient == null)
        {
            throw new HbmoNutrientNotFoundException(nutrientName);
        }

        cachedNutrient = nutrient;
        return nutrient;
    }
}
