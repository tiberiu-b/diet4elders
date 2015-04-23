package org.licenta.d4elders.model;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Computes and stores the nutritional values for a user.
 * TODO: Generic variables like names, lower and upper limits have to be read from a configuration file.
 */
public class NutritionalInformation {
    private static final Logger log = Logger.getLogger( NutritionalInformation.class.getName() );
    private Map<String, Boolean> nutrientExists = new HashMap<String, Boolean>();
    private NutritionalInformationSourceType type;

    // TODO: discuss whether to replace with Map: add, remove, update operations
    // We already have a Map which tells us whether a nutrients exists or not, maybe use that one?
    private AbstractCollection<SingleNutrientInformation> nutrientsInformation = null;

    /**
     * Creates an instance of NutritionalInformation which contains multiple instances of SingleNutrientInformation,
     * thus, it is like a Map specialized in nutrition :)
     * If the type is GeneralRecommendation, the user profile must be given in the second parameter and the recommended
     * values are automatically computed by the rules defined in documentul facut de Dalma.
     * If the type is DoctorPrescription, this can be combined with other instances of type DoctorPrescription to provide
     * the FinalDoctorPrescription.
     * @param type the type of NutritionalInformation
     * @param userProfileStub
     */
    public NutritionalInformation(NutritionalInformationSourceType type, UserProfileStub userProfileStub) {
        this.type = type;
        initializeNutrientExistsMap();
        switch(type)
        {
            case GeneralRecommendation:
            {
                assert (userProfileStub != null);
                nutrientsInformation = new ArrayList<SingleNutrientInformation>();
                computeAndPopulateNutritionalInformation(userProfileStub);
                if(!sanityCheck())
                {
                    log.log(Level.SEVERE, "Sanity check failed for NutritionalInformation!");
                }

                break;
            }

            case DoctorPrescription:
            case FinalDoctorPrescription:
            case Ideal: {
                if(userProfileStub != null)
                {
                    log.log(Level.WARNING, "Extra parameter not ignored as it ought to be!");
                }
                break;
            }

            default:
                log.log(Level.SEVERE, "Incorrect value of type field!");
        }

    }

    private void initializeNutrientExistsMap()
    {
        nutrientExists.put("calories", false);
        nutrientExists.put("carbo", false);
        nutrientExists.put("proteins", false);
        nutrientExists.put("fats", false);
        nutrientExists.put("vitaminA", false);
        nutrientExists.put("vitaminB", false);
        nutrientExists.put("vitaminC", false);
        nutrientExists.put("vitaminD", false);
        nutrientExists.put("calcium", false);
        nutrientExists.put("iron", false);
        nutrientExists.put("sodium", false);
    }

    private void computeAndPopulateNutritionalInformation(UserProfileStub userProfileStub) {
        // 1. Calories
        // Compute the recommended calories intake.
        double calories = computeCaloriesIntake(userProfileStub);

        // Add the calories.
        SingleNutrientInformation caloriesInformation = new SingleNutrientInformation("calories");
        caloriesInformation.setFixedValue(calories);
        nutrientsInformation.add(caloriesInformation);
        nutrientExists.put("calories", true);

        // 2. Carbohydrates
        SingleNutrientInformation carboInformation = new SingleNutrientInformation("carbo");
        carboInformation.setLowerLimit(.45 * calories / 4);
        carboInformation.setUpperLimit((userProfileStub.isWeight_loss() ? .5 : .65) * calories / 4);
        carboInformation.setFixedValue((carboInformation.getLowerLimit() + carboInformation.getUpperLimit()) / 2);
        nutrientsInformation.add(carboInformation);
        nutrientExists.put("carbo", true);

        // 3. Proteins
        SingleNutrientInformation proteinsInformation = new SingleNutrientInformation("proteins");
        proteinsInformation.setLowerLimit(.1 * calories / 4);
        proteinsInformation.setUpperLimit(.35 * calories / 4);
        proteinsInformation.setFixedValue(userProfileStub.getWeight() * .8);
        proteinsInformation.setFixedValue((proteinsInformation.getLowerLimit() + proteinsInformation.getUpperLimit()) / 2);
        nutrientsInformation.add(proteinsInformation);
        nutrientExists.put("proteins", true);

        // 4. Fats
        SingleNutrientInformation fatsInformation = new SingleNutrientInformation("fats");
        fatsInformation.setLowerLimit(.2 * calories / 4);
        fatsInformation.setUpperLimit(.35 * calories / 4);
        fatsInformation.setFixedValue((fatsInformation.getLowerLimit() + fatsInformation.getUpperLimit()) / 2);
        nutrientsInformation.add(fatsInformation);
        nutrientExists.put("fats", true);

        // 5. vitamin A
        SingleNutrientInformation vitaminAInformation = new SingleNutrientInformation("vitaminA");
        vitaminAInformation.setFixedValue(userProfileStub.getGender() == GenderType.Male ? .9 : .7);
        vitaminAInformation.setTolerableUpperIntakeLevel(3.0);
        nutrientsInformation.add(vitaminAInformation);
        nutrientExists.put("vitaminA", true);

        // 6. vitamin B
        SingleNutrientInformation vitaminBInformation = new SingleNutrientInformation("vitaminB");
        vitaminBInformation.setFixedValue(1.3);
        vitaminBInformation.setTolerableUpperIntakeLevel(100.0);
        nutrientsInformation.add(vitaminBInformation);
        nutrientExists.put("vitaminB", true);

        // 6. vitamin C
        SingleNutrientInformation vitaminCInformation = new SingleNutrientInformation("vitaminC");
        vitaminCInformation.setFixedValue(userProfileStub.getGender() == GenderType.Male ? 90.0 : 75.0);
        vitaminCInformation.setTolerableUpperIntakeLevel(2000.0);
        nutrientsInformation.add(vitaminCInformation);
        nutrientExists.put("vitaminC", true);

        // 7. vitamin D
        SingleNutrientInformation vitaminDInformation = new SingleNutrientInformation("vitaminD");

        double vitaminD_fixed, vitaminD_tolerable;
        if(userProfileStub.getAge() < 1) {
            vitaminD_fixed = .01;
            vitaminD_tolerable = .038;
        }
        else if(isInRange(userProfileStub.getAge(), 1, 3))
        {
            vitaminD_fixed = .015;
            vitaminD_tolerable = .063;
        }
        else if(isInRange(userProfileStub.getAge(), 3, 8))
        {
            vitaminD_fixed = .015;
            vitaminD_tolerable = .075;
        }
        else if(isInRange(userProfileStub.getAge(), 8, 70))
        {
            vitaminD_fixed = .015;
            vitaminD_tolerable = .1;
        }
        else
        {
            vitaminD_fixed = .02;
            vitaminD_tolerable = .1;
        }

        vitaminDInformation.setFixedValue(vitaminD_fixed);
        vitaminDInformation.setTolerableUpperIntakeLevel(vitaminD_tolerable);
        nutrientsInformation.add(vitaminDInformation);
        nutrientExists.put("vitaminD", true);

        // 8. Calcium
        SingleNutrientInformation calciumInformation =  new SingleNutrientInformation("calcium");
        calciumInformation.setFixedValue(userProfileStub.getAge() < 50 ? 1000.0 : 1200.0);
        calciumInformation.setTolerableUpperIntakeLevel(2000.0);
        nutrientsInformation.add(calciumInformation);
        nutrientExists.put("calcium", true);

        // 9. Iron
        nutrientsInformation.add(computeIronInformation(userProfileStub));
        nutrientExists.put("iron", true);


        // 10. Sodium
        SingleNutrientInformation sodiumInformation = new SingleNutrientInformation("sodium");
        sodiumInformation.setFixedValue(userProfileStub.getAge() < 51 ? 2300.0 : 1500.0);
        nutrientsInformation.add(sodiumInformation);
        nutrientExists.put("sodium", true);

    }

    private SingleNutrientInformation computeIronInformation(UserProfileStub userProfileStub) {
        SingleNutrientInformation ironInformation = new SingleNutrientInformation("iron");
        int age = userProfileStub.getAge();
        GenderType gender = userProfileStub.getGender();
        double ironQuantity = 0, ironTolerabil = 0;
        if(age < 1)
        {
            ironQuantity = 11;
        }
        else if (isInRange(age, 1, 3))
        {
            ironQuantity = 7;
        }
        else if(isInRange(age, 3, 8))
        {
            ironQuantity = 10;
        }
        else if(isInRange(age, 8, 13))
        {
            ironQuantity = 8;
        }
        else if(isInRange(age, 13, 18))
        {
            ironQuantity = gender == GenderType.Male ? 11 : 15;
        }
        else if(isInRange(age, 18, 50))
        {
            ironQuantity = gender == GenderType.Male ? 8 : 18;
        }
        else
        {
            ironQuantity = 8;
        }

        ironInformation.setFixedValue(ironQuantity);
        ironInformation.setTolerableUpperIntakeLevel(age < 19 ? 40.0 : 45.0);

        return ironInformation;
    }

    private double computeCaloriesIntake(UserProfileStub userProfileStub)
    {
        double calories = computeCaloriesIntakeHarrisBenedict(userProfileStub.getGender(), userProfileStub.getWeight(),
                userProfileStub.getHeight(), userProfileStub.getAge(), userProfileStub.getPAF());

        // If the user wants to gain weight, add a surplus to calories.
        if (userProfileStub.isWeight_gain())
        {
            // Add 250 for males and 150 for women.
            double surplus = userProfileStub.getGender() == GenderType.Male ? 250 : 150;
            calories += surplus;
        }
        else // If the user wants to lose weight, subtract from calories.
            if (userProfileStub.isWeight_loss())
            {
                // TODO: revise weight loss
                // Subtract a random value between 500 and 1000
                calories -= Math.random() * 500 + 500;
            }

        return calories;
    }

    /**
     * Computes the recommended calories intake based on Harris-Benedict Equation.
     */
    private double computeCaloriesIntakeHarrisBenedict(GenderType gender, int weight, int height, int age, double paf) {
        return computeBMR(gender, weight, height, age) * paf;
    }

    /**
     * Computes the recommended calories intake based on Basal Metabolic Rate - BMR.
     */
    private double computeBMR(GenderType gender, int weight, int height, int age) {
        if(gender == GenderType.Male)
        {
            return 66 + 13.75 * weight + 5 * height - 6.76 * age;
        }
        else if(gender == GenderType.Female)
        {
            return 655 + 9.56 * weight + 1.85 * height - 4.68 * age;
        }

        log.log(Level.SEVERE, "Cannot compute calories intake: Invalid gender type!");
        return 0;
    }

    /**
     * Checks whether all the nutrients have been correctly introduced.
     * TODO: finish
     */
    private boolean sanityCheck()
    {
        return true;
    }

    /**
     * Checks whether x belongs to [a, b)
     */
    private boolean isInRange(double x, double a, double b)
    {
        return a <= x && x < b;
    }


    /**
     * Applies a set-union operation on the list "nutrientsInformation" of this and of prescription.
     * For example, this.nutrientsInformation = {calories}, prescrition.nutrientsInformation = {carbo} =>
     * this.nutrientsInformation = {calories, carbo} after this method call.
     * @param nutritionalInformation
     * @param finalPrescription Allows nutrients to get overwritten (including weights, quantities) if set to true.
     * @return this
     */
    private NutritionalInformation combineNutritionalInformation(NutritionalInformation nutritionalInformation,
                                                                 boolean finalPrescription)
    {
        assert (nutritionalInformation != null);
        for(SingleNutrientInformation nutrientInformation : nutritionalInformation.getNutrientsInformation())
        {
            if(!finalPrescription)
            {
                // TODO: reviese weights here
                // Multiply the weight 10 times, sin
                nutrientInformation.setWeight(nutrientInformation.getWeight() * 10);
            }
            else
            {
                assert (!nutrientExists.get(nutrientInformation.getName()));
            }
            this.addSingleNutrientInformation(nutrientInformation);
        }

        return this;
    }

    /**
     * Combines multiple prescriptions.
     * @param prescription the instance of NutritionalInformation from which to add the nutrientsInformation
     * @return this - allows chaining of multiple calls.
     */
    public NutritionalInformation combineDoctorPrescription(NutritionalInformation prescription)
    {
        // Both have to be doctor's prescription.
        assert (prescription != null);
        assert (type == NutritionalInformationSourceType.DoctorPrescription);
        assert (prescription.type == NutritionalInformationSourceType.DoctorPrescription);

        return combineNutritionalInformation(prescription, false);
    }

    /**
     * Converts this (general recommendation) to the final ideal values by combining the nutrients from this with
     * those from finalPrescription. If finalPrescription, this is directly transformed to Ideal without any other
     * operations.
     * @param finalPrescription
     * @return this - allows chaining of method calls.
     */
    public NutritionalInformation combineFinalDoctorPrescription(NutritionalInformation finalPrescription)
    {
        assert (type == NutritionalInformationSourceType.GeneralRecommendation);

        if (finalPrescription == null)
        {
            log.log(Level.INFO, "Directly transforming NutritionalInformation from General to Ideal.");
            type = NutritionalInformationSourceType.Ideal;
            return this;
        }

        assert (finalPrescription.type == NutritionalInformationSourceType.DoctorPrescription);
        combineNutritionalInformation(finalPrescription, true);
        type = NutritionalInformationSourceType.Ideal;
        return this;
    }

    /**
     * Adds a nutrient (instance of SingleNutrientInformation) to the list of nutrients.
     * @param singleNutrientInformation instance to be added
     */
    public void addSingleNutrientInformation(SingleNutrientInformation singleNutrientInformation)
    {
        if (nutrientsInformation == null)
        {
            nutrientsInformation = new ArrayList<SingleNutrientInformation>();
        }


        // Update if existing ... ugly here
        if (nutrientExists.get(singleNutrientInformation.getName()))
        {
            // Remove
            for(SingleNutrientInformation n : nutrientsInformation)
            {
                if (n.getName().equals(singleNutrientInformation.getName())){
                    nutrientsInformation.remove(n);
                    break;
                }
            }
        }

        nutrientsInformation.add(singleNutrientInformation);
        nutrientExists.put(singleNutrientInformation.getName(), true);
    }

    /**
     * Replaces the collection of SingleNutrientsInformation of this prescription and updates the nutrientExists map
     * correspondingly.
     * @param nutrientsInformation
     */
    public void setNutrientsInformation(AbstractCollection<SingleNutrientInformation> nutrientsInformation)
    {
        if(type == NutritionalInformationSourceType.GeneralRecommendation)
        {
            log.log(Level.WARNING, "NutrientsInformation should be automatically computed, not manually! " +
                    "This may be a logic error.");
        }

        this.nutrientsInformation = nutrientsInformation;
        initializeNutrientExistsMap();
        for(SingleNutrientInformation singleNutrientInformation : nutrientsInformation)
        {
            nutrientExists.put(singleNutrientInformation.getName(), true);
        }
    }

    /**
     * Returns whether nutrient "nutrientName" is among the list of nutrients.
     * @param nutrientName
     * @return true if present, false otherwise.
     */
    public boolean hasNutrient(String nutrientName)
    {
        return nutrientExists.containsKey(nutrientName);
    }

    /**
     * Returns information (an instance of SingleNutrientInformation) about a single nutrient in the list.
     * @param nutrientName
     * @return an instance of SingleNutrientInformation if the nutrient is present, null otherwise.
     */
    public SingleNutrientInformation getNutrient(String nutrientName)
    {
        if(!hasNutrient(nutrientName))
            return null;

        for( SingleNutrientInformation nutrient : nutrientsInformation )
        {
            if (nutrient.getName().equals(nutrientName))
                return nutrient;
        }

        return null;
    }

    /**
     * Returns the fixed ideal values
     * @return the nutrients in the following form: Map[nutrient] = quantity
     */
    public Map<String, Double> getFixedIdealNutrientsMap()
    {
        if(type != NutritionalInformationSourceType.Ideal)
        {
            log.log(Level.SEVERE, "NutritionalInformation not of type ideal, i.e. values are not ready yet!");
        }

        Map<String, Double> nutrientsMap = new HashMap<String, Double>();
        for(SingleNutrientInformation nutrient : nutrientsInformation)
        {
            if(nutrient.getFixedValue() == null)
            {
                log.log(Level.WARNING, "Null fixed nutrient value for " + nutrient.getName());
            }

            nutrientsMap.put(nutrient.getName(), nutrient.getFixedValue());
        }

        return nutrientsMap;
    }

    public AbstractCollection<SingleNutrientInformation> getNutrientsInformation() {
        return nutrientsInformation;
    }

    /**
     * Used for testing.
     * @return
     */
    protected NutritionalInformationSourceType getType() {
        return this.type;
    }

}