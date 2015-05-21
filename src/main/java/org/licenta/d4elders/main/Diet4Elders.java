package org.licenta.d4elders.main;

import java.util.logging.Logger;

import org.licenta.d4elders.algorithm.HoneyBeeMatingOptimiziation;
import org.licenta.d4elders.algorithm.MainAlgorithm.RunInformation;
import org.licenta.d4elders.dal.BusinessLogic;
import org.licenta.d4elders.helper.AlgorithmConfiguration;
import org.licenta.d4elders.model.Solution;
import org.licenta.d4elders.model.user_profile.NutritionalRecommandationHelper;
import org.licenta.d4elders.model.user_profile.UserProfileStub;

public class Diet4Elders {
	private static final Logger log = Logger.getLogger(Diet4Elders.class.getName());

	public static UserProfileStub userProfile;

	public Diet4Elders() {

		userProfile = new UserProfileStub();
		new NutritionalRecommandationHelper(userProfile);
		new BusinessLogic().loadOntologyDataIntoMemory();

	}

	public void run(AlgorithmConfiguration configuration) {

		Solution queen = null;
		RunInformation info = null;

		// log.log(Level.INFO,
		// "Running Honey Bee Mating Optimization with the following configuration\n" +
		// configuration);
		// System.out.println("Running Honey Bee Mating Optimization with the following configuration\n"
		// + configuration);
		HoneyBeeMatingOptimiziation HBMO = new HoneyBeeMatingOptimiziation(configuration);
		queen = HBMO.performAlgorithm();
		info = HBMO.getLastRunInformation();

		// Print results
		System.out.println("___\nFinal Result:");
		System.out.println(queen + "\nFitness: " + queen.getFitness());
		System.out.println("Number of iterations: " + info.nrOfItertions);

		System.out.println("Duration of execution(in millis): " + info.duration);
	}
}
