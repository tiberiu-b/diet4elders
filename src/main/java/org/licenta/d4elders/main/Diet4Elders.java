package org.licenta.d4elders.main;

import org.licenta.d4elders.algorithm.HoneyBeeMatingOptimiziation;
import org.licenta.d4elders.algorithm.MainAlgorithm.RunInformation;
import org.licenta.d4elders.model.Solution;
import org.licenta.d4elders.model.user_profile.NutritionalRecommandationHelper;
import org.licenta.d4elders.model.user_profile.UserProfileStub;

public class Diet4Elders {
	public static UserProfileStub userProfile;

	public Diet4Elders() {
		userProfile = new UserProfileStub();
		new NutritionalRecommandationHelper(userProfile);
	}

	public void run() {
		//AlgorithmVer1 a1 = new AlgorithmVer1();
		//a1.start();


		HoneyBeeMatingOptimiziation HBMO = new HoneyBeeMatingOptimiziation();
        Solution queen = HBMO.performAlgorithm();
        RunInformation info = HBMO.getLastRunInformation();

        // Print results
        System.out.println("___\nFinal Result:");
        System.out.println(queen + " Fitness: " + queen.getFitness());
        System.out.println("Number of iterations: " + info.nrOfItertions);

        System.out.println("Duration of execution(in millis): " + info.duration);

	}

}
