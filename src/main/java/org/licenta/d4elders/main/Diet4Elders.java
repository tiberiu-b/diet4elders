package org.licenta.d4elders.main;

import org.licenta.d4elders.algorithm.HoneyBeeMatingOptimizationPathRelinking;
import org.licenta.d4elders.algorithm.HoneyBeeMatingOptimiziation;
import org.licenta.d4elders.algorithm.MainAlgorithm.RunInformation;
import org.licenta.d4elders.dal.BusinessLogic;
import org.licenta.d4elders.model.Solution;
import org.licenta.d4elders.model.user_profile.NutritionalRecommandationHelper;
import org.licenta.d4elders.model.user_profile.UserProfileStub;

public class Diet4Elders {
	public static UserProfileStub userProfile;

	public Diet4Elders() {
		userProfile = new UserProfileStub();
		new NutritionalRecommandationHelper(userProfile);
		new BusinessLogic().loadOntologyDataIntoMemory();
	}

	public void run() {

		Solution queen = null;
		RunInformation info = null;

		HoneyBeeMatingOptimiziation HBMO = new HoneyBeeMatingOptimiziation();
        queen = HBMO.performAlgorithm();
        info = HBMO.getLastRunInformation();

        // Print results
        System.out.println("___\nFinal Result:");
        System.out.println(queen + "\nFitness: " + queen.getFitness());
        System.out.println("Number of iterations: " + info.nrOfItertions);

        System.out.println("Duration of execution(in millis): " + info.duration);


		HoneyBeeMatingOptimizationPathRelinking HBMO2 = new HoneyBeeMatingOptimizationPathRelinking();
        queen = HBMO2.performAlgorithm();
        info = HBMO2.getLastRunInformation();

        // Print results
        System.out.println("___\nFinal Result:");
        System.out.println(queen + "\nFitness: " + queen.getFitness());
        System.out.println("Number of iterations: " + info.nrOfItertions);

        System.out.println("Duration of execution(in millis): " + info.duration);

	}
}
