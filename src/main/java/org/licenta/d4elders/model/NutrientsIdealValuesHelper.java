package Meals.Model;

import Meals.Model.Dao.NutrientsIdealValuesDAOImpl;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by cristiprg on 02.03.2015.
 */
public class NutrientsIdealValuesHelper {
    private static final Logger log = Logger.getLogger( NutrientsIdealValuesHelper.class.getName() );
    /*
    private static Map<String, Double> nutrientsIdealValuesMap =
            new NutrientsIdealValuesDAOImpl(). // get new instance of NutrientsIdealValuesDAO
                    getNutrientsIdealValuesById(1). // get new instance of NutrientsIdealValues - index 1 TODO: different ideals for different diets
                    getNutrientsIdealValuesMap(); // get the map only
                    */

    private static Map<String, Double> nutrientsIdealValuesMap =
            new NutritionalInformation(
                    NutritionalInformationSourceType.GeneralRecommendation,
                    new UserProfileStub())
                    .combineFinalDoctorPrescription(null)
                    .getFixedIdealNutrientsMap();

    public static Double getIdealValueForNutrient(String nutrient) throws HbmoNutrientNotFoundException {

        if( !nutrientsIdealValuesMap.containsKey(nutrient))
        {
            HbmoNutrientNotFoundException exception = new HbmoNutrientNotFoundException(nutrient);
            throw exception;
        }

        return nutrientsIdealValuesMap.get(nutrient);
    }
}
