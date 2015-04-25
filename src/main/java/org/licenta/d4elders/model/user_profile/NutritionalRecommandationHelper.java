package org.licenta.d4elders.model.user_profile;

public class NutritionalRecommandationHelper {

	public static UserProfileStub userProfileStub;
	public static NutritionalRecommendation nutrR;

	public NutritionalRecommandationHelper(UserProfileStub user) {
		NutritionalRecommandationHelper.userProfileStub = user;
		NutritionalRecommandationHelper.nutrR = new NutritionalRecommendation();
		computeNutritionalRecomendationValues();
	}

	/**
	 * This method computes the recommended nutritional values for a userprofile based on age,
	 * gender, etc
	 */
	public void computeNutritionalRecomendationValues() {
		float calories = (float) computeCaloriesIntake(userProfileStub);

		// Set the energy Ontology UM => kcal
		nutrR.setEnergyFixed(calories);

		// 2. Carbohydrates Ontology UM => g
		nutrR.setCarbohydratesLowerLimit((float) (.45 * calories / 4));
		nutrR.setCarbohydratesUpperLimit((float) ((userProfileStub.isWeight_loss() ? .5 : .65) * calories / 4));
		nutrR.setCarbohydratesFixed((nutrR.getCarbohydratesLowerLimit() + nutrR.getCarbohydratesUpperLimit()) / 2);

		// 3. Proteins Ontology UM => g
		nutrR.setProteinsFixed((float) (userProfileStub.getWeight() * .8));
		nutrR.setProteinsLowerLimit((float) (.1 * calories / 4));
		nutrR.setProteinsUpperLimit((float) (.35 * calories / 4));
		nutrR.setProteinsFixed((float) ((nutrR.getProteinsLowerLimit() + nutrR.getProteinsUpperLimit()) / 2.0));

		// 4. Fats Ontology UM => g
		nutrR.setLipidsLowerLimit((float) (.2 * calories / 4));
		nutrR.setLipidsUpperLimit((float) (.35 * calories / 4));

		// 5. vitamin A Ontology UM => ug
		nutrR.setVitAFixed((float) (userProfileStub.getGender() == GenderType.Male ? 900 : 700));
		nutrR.setVitATolerableUpperIntake((float) 3.0);

		// 6. vitamin B Ontology UM => mg
		nutrR.setVitBFixed((float) 1.3);
		nutrR.setVitBTolerableUpperIntake((float) 100.0);

		// 7. vitamin C Ontology UM => mg
		nutrR.setVitCFixed((float) (userProfileStub.getGender() == GenderType.Male ? 90.0 : 75.0));
		nutrR.setVitCTolerableUpperIntake((float) 2000.0);

		// 7. vitamin D Ontology UM => ug
		computeVitDInformation();

		// 8. Calcium Ontology UM => mg
		nutrR.setCalciumFixed((float) (userProfileStub.getAge() < 50 ? 1000.0 : 1200.0));
		nutrR.setCalciumTolerableUpperIntake((float) 2000.0);

		// 9. Iron Ontology UM => mg
		computeIronInformation();

		// 10. Sodium Ontology UM => mg
		nutrR.setSodiumFixed((float) (userProfileStub.getAge() < 51 ? 2300.0 : 1500.0));
	}

	private double computeCaloriesIntake(UserProfileStub userProfileStub) {
		double calories = computeCaloriesIntakeHarrisBenedict(userProfileStub.getGender(), userProfileStub.getWeight(),
				userProfileStub.getHeight(), userProfileStub.getAge(), userProfileStub.getPAF());

		// If the user wants to gain weight, add a surplus to calories.
		if (userProfileStub.isWeight_gain()) {
			// Add 250 for males and 150 for women.
			double surplus = userProfileStub.getGender() == GenderType.Male ? 250 : 150;
			calories += surplus;
		} else // If the user wants to lose weight, subtract from calories.
		if (userProfileStub.isWeight_loss()) {
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
		if (gender == GenderType.Male) {
			return 66 + 13.75 * weight + 5 * height - 6.76 * age;
		} else if (gender == GenderType.Female) {
			return 655 + 9.56 * weight + 1.85 * height - 4.68 * age;
		}

		return 0;
	}

	/**
	 * Checks whether x belongs to [a, b)
	 */
	private boolean isInRange(double x, double a, double b) {
		return a <= x && x < b;
	}

	private void computeVitDInformation() {
		double vitaminD_fixed, vitaminD_tolerable;
		if (userProfileStub.getAge() < 1) {
			vitaminD_fixed = 10;
			vitaminD_tolerable = 38;
		} else if (isInRange(userProfileStub.getAge(), 1, 3)) {
			vitaminD_fixed = 15;
			vitaminD_tolerable = 63;
		} else if (isInRange(userProfileStub.getAge(), 3, 8)) {
			vitaminD_fixed = 15;
			vitaminD_tolerable = 75;
		} else if (isInRange(userProfileStub.getAge(), 8, 70)) {
			vitaminD_fixed = 15;
			vitaminD_tolerable = 100;
		} else {
			vitaminD_fixed = 20;
			vitaminD_tolerable = 100;
		}
		nutrR.setVitDFixed((float) vitaminD_fixed);
		nutrR.setVitDTolerableUpperIntake((float) vitaminD_tolerable);
	}

	private void computeIronInformation() {
		int age = userProfileStub.getAge();
		GenderType gender = userProfileStub.getGender();
		double ironQuantity = 0;
		if (age < 1) {
			ironQuantity = 11;
		} else if (isInRange(age, 1, 3)) {
			ironQuantity = 7;
		} else if (isInRange(age, 3, 8)) {
			ironQuantity = 10;
		} else if (isInRange(age, 8, 13)) {
			ironQuantity = 8;
		} else if (isInRange(age, 13, 18)) {
			ironQuantity = gender == GenderType.Male ? 11 : 15;
		} else if (isInRange(age, 18, 50)) {
			ironQuantity = gender == GenderType.Male ? 8 : 18;
		} else {
			ironQuantity = 8;
		}

		nutrR.setIronFixed((float) ironQuantity);
		nutrR.setIronTolerableUpperIntake((float) (age < 19 ? 40.0 : 45.0));
	}

}
