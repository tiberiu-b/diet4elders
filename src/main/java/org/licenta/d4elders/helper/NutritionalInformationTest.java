package org.licenta.d4elders.helper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.licenta.d4elders.model.SingleNutrientInformation;
import org.licenta.d4elders.model.user_profile.UserProfileStub;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class NutritionalInformationTest {

    private UserProfileStub userProfileStub = null;
    private Map<String, ExpectedNutrientValues> expectedNutrientsMap = null;

    public NutritionalInformationTest(UserProfileStub userProfileStub, Map<String, ExpectedNutrientValues> expectedNutrientsMap)
    {
        this.userProfileStub = userProfileStub;
        this.expectedNutrientsMap = expectedNutrientsMap;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data()
    {
        /* TODO: add more test cases. */

        // Test case 1 : recommended values.
        UserProfileStub userProfileStub = new UserProfileStub();
        Map<String, ExpectedNutrientValues> expectedNutrientsMap = new HashMap<String, ExpectedNutrientValues>();

        // Set up the user profile -> already set in constructor, it's just a stub.
        /*
        userProfileStub.setAge(27);
        userProfileStub.setHeight(180);
        userProfileStub.setWeight(79);
        userProfileStub.setGender(GenderType.Male);
        userProfileStub.setPAF(1.2);
        */

        // Set up the evaluation board.
        expectedNutrientsMap.put("calories", new ExpectedNutrientValues(null, null, 2243.676, null));
        expectedNutrientsMap.put("carbo", new ExpectedNutrientValues(252.41355, 364.59735, null, null));
        expectedNutrientsMap.put("proteins", new ExpectedNutrientValues(56.0919, 196.32164999999998, 63.2, null));
        expectedNutrientsMap.put("fats", new ExpectedNutrientValues(112.1838, 196.32164999999998, null, null));
        expectedNutrientsMap.put("vitaminA", new ExpectedNutrientValues(null, null, .9, 3.0));
        expectedNutrientsMap.put("vitaminB", new ExpectedNutrientValues(null, null, 1.3, 100.0));
        expectedNutrientsMap.put("vitaminC", new ExpectedNutrientValues(null, null, 90.0, 2000.0));
        expectedNutrientsMap.put("vitaminD", new ExpectedNutrientValues(null, null, .015, .1));
        expectedNutrientsMap.put("calcium", new ExpectedNutrientValues(null, null, 1000.0, 2000.0));
        expectedNutrientsMap.put("iron", new ExpectedNutrientValues(null, null, 8.0, 45.0));
        expectedNutrientsMap.put("sodium", new ExpectedNutrientValues(null, null, 2300.0, null));


        // Test case 2 ... soon

        return Arrays.asList(new Object[][]{
                {userProfileStub, expectedNutrientsMap},
        });
    }

    @Test
    public void testRecommendedValues()
    {
        String nutrientName;
        Double expected, actual;

        System.out.println("Testing profile " + userProfileStub);

        NutritionalInformation nutritionalInformation = new NutritionalInformation(
                NutritionalInformationSourceType.GeneralRecommendation,
                userProfileStub);

        AbstractCollection<SingleNutrientInformation> nutrientInformationList =
                nutritionalInformation.getNutrientsInformation();


        // Test whether the values are correctly set.
        for(SingleNutrientInformation nutrientInformation : nutrientInformationList)
        {
            nutrientName = nutrientInformation.getName();
            assertTrue("Unexpected nutrient found: " + nutrientName, expectedNutrientsMap.containsKey(nutrientName));

            ExpectedNutrientValues q = expectedNutrientsMap.get(nutrientName);
            assertTrue(q.visited == false); // It should be visited the first time.

            // test lower limit
            actual = nutrientInformation.getLowerLimit();
            expected = q.lowerLimit;

            assertTrue(nutrientName + " lowerLimit\nActual: " + actual + "\nExpected: " + expected,
                    actual == null && expected == null || actual.compareTo(expected) == 0);

            // test upper limit
            actual = nutrientInformation.getUpperLimit();
            expected = q.upperLimit;
            assertTrue(nutrientName + " upperLimit\nActual: " + actual + "\nExpected: " + expected,
                    actual == null && expected == null || actual.compareTo(expected) == 0);

            // test fixed value
            actual = nutrientInformation.getFixedValue();
            expected = q.fixedValue;
            assertTrue(nutrientName + " fixedValue\nActual: " + actual + "\nExpected: " + expected,
                    actual == null && expected == null || actual.compareTo(expected) == 0);

            // test tolerable
            actual = nutrientInformation.getTolerableUpperIntakeLevel();
            expected = q.tolerableUpperIntakeLevel;
            assertTrue(nutrientName + " tolerable\nActual: " + actual + "\nExpected: " + expected,
                    actual == null && expected == null || actual.compareTo(expected) == 0);

            q.visited = true;
        }

        // Test whether all nutrients have been visited.
        for(ExpectedNutrientValues q : expectedNutrientsMap.values())
        {
            assertTrue(q.visited);
        }
    }

    @Test
    public void testCombinations()
    {
        System.out.println("Testing combinations of prescriptions.");

        // 1. Create ni1 with calories, ni2 with carbo, then combine into ni1 and expect to find carbo in ni1.
        NutritionalInformation ni1 = new NutritionalInformation(NutritionalInformationSourceType.DoctorPrescription, null);
        NutritionalInformation ni2 = new NutritionalInformation(NutritionalInformationSourceType.DoctorPrescription, null);

        SingleNutrientInformation caloriesInformation = new SingleNutrientInformation("calories");
        caloriesInformation.setFixedValue(2000.0);
        ni1.addSingleNutrientInformation(caloriesInformation);

        SingleNutrientInformation carboInformation = new SingleNutrientInformation("carbo");
        carboInformation.setFixedValue(100.0);
        ni2.addSingleNutrientInformation(carboInformation);

        ni1.combineDoctorPrescription(ni2);

        // Check if carbo exists in ni1.
        assertTrue(ni1.hasNutrient("carbo"));

        // Check if there are two items in ni1.
        AbstractCollection<SingleNutrientInformation> nutrientsInformation = ni1.getNutrientsInformation();
        assertTrue("Invalid size!", nutrientsInformation.size() == 2);

        // Check the fixed value of carbo.
        SingleNutrientInformation carboNi1 = ni1.getNutrient("carbo");
        assertTrue(carboNi1 != null);
        assertTrue(carboNi1.getFixedValue() != null);
        assertTrue(carboNi1.getFixedValue().compareTo(100.0) == 0);


        // 2. Test creation of ideal values.
        NutritionalInformation nutritionalInformation = new NutritionalInformation(
                NutritionalInformationSourceType.GeneralRecommendation,
                userProfileStub);
        nutritionalInformation.combineFinalDoctorPrescription(ni1);

        // Test type
        assertTrue(nutritionalInformation.getType() == NutritionalInformationSourceType.Ideal);

        // Test overwritten calories.
        SingleNutrientInformation idealCalories = nutritionalInformation.getNutrient("calories");
        assertTrue(idealCalories != null);
        assertTrue(idealCalories.getFixedValue() != null);
        assertTrue(idealCalories.getFixedValue().compareTo(2000.0) == 0);

        // Test overwritten carbo.
        SingleNutrientInformation idealCarbo = nutritionalInformation.getNutrient("carbo");
        assertTrue(idealCarbo != null);
        assertTrue(idealCarbo.getFixedValue() != null);
        assertTrue(idealCarbo.getFixedValue().compareTo(100.0) == 0);


    }

    static class ExpectedNutrientValues
    {
        Double lowerLimit = null;
        Double upperLimit = null;
        Double fixedValue = null;
        Double tolerableUpperIntakeLevel = null;
        boolean visited = false;

        public ExpectedNutrientValues(Double lowerLimit, Double upperLimit, Double fixedValue, Double tolerableUpperIntakeLevel) {
            this.lowerLimit = lowerLimit;
            this.upperLimit = upperLimit;
            this.fixedValue = fixedValue;
            this.tolerableUpperIntakeLevel = tolerableUpperIntakeLevel;
        }
    }

}