package org.licenta.d4elders.main;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.licenta.d4elders.algorithm.HoneyBeeMatingOptimization;
import org.licenta.d4elders.algorithm.MainAlgorithm.RunInformation;
import org.licenta.d4elders.dal.BusinessLogicCache;
import org.licenta.d4elders.helper.AlgorithmConfiguration;
import org.licenta.d4elders.helper.AlgorithmRunner;
import org.licenta.d4elders.helper.DataExporter;
import org.licenta.d4elders.helper.DataExporterException;
import org.licenta.d4elders.model.Solution;
import org.licenta.d4elders.model.user_profile.NutritionalRecommandationHelper;
import org.licenta.d4elders.model.user_profile.UserProfileStub;

public class Diet4Elders {
	private static final Logger log = Logger.getLogger(Diet4Elders.class.getName());

	public static UserProfileStub userProfile;

	public Diet4Elders() {

		userProfile = new UserProfileStub();
		new NutritionalRecommandationHelper(userProfile);
		// new BusinessLogic().loadOntologyDataIntoMemory();
		BusinessLogicCache bLogicCache = BusinessLogicCache.getInstance();

	}

	public void run(AlgorithmConfiguration configuration) {
		AlgorithmRunner.run(configuration);
	}

	public void run(ArrayList<AlgorithmConfiguration> configurations) {
		AlgorithmRunner.run(configurations);
	}
}
