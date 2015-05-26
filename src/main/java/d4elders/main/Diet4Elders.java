package d4elders.main;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

import d4elders.algorithm.HoneyBeeMatingOptimization;
import d4elders.algorithm.MainAlgorithm.RunInformation;
import d4elders.algorithm.broodImprover.BusinessLogicCacheFilteredOpt;
import d4elders.algorithm.helper.AlgorithmConfiguration;
import d4elders.algorithm.helper.AlgorithmRunner;
import d4elders.algorithm.helper.DataExporter;
import d4elders.algorithm.helper.DataExporterException;
import d4elders.dal.BusinessLogic;
import d4elders.dal.BusinessLogicCache;
import d4elders.dal.BusinessLogicCacheFiltered;
import d4elders.dal.helper.RandomSolutionsGenerator;
import d4elders.dal.helper.SolutionsGenerator;
import d4elders.model.Solution;
import d4elders.model.user_profile.NutritionalRecommandationHelper;
import d4elders.model.user_profile.UserProfileHelper;
import d4elders.model.user_profile.UserProfileStub;

public class Diet4Elders {
	private static final Logger log = Logger.getLogger(Diet4Elders.class
			.getName());

	public static UserProfileStub userProfile;
	public static SolutionsGenerator solGenerator;

	public Diet4Elders() {
		if (solGenerator == null)
			solGenerator = new RandomSolutionsGenerator();
		userProfile = new UserProfileStub();
		new NutritionalRecommandationHelper(userProfile);
		// BusinessLogic bLogic = BusinessLogic.getInstance();
		// BusinessLogicCache bLogicCache = BusinessLogicCache.getInstance();
		BusinessLogicCacheFilteredOpt blCacheFiltered = BusinessLogicCacheFilteredOpt
				.getInstance();
		blCacheFiltered
				.loadOntologyDataIntoMemory(userProfile.getAllergyList());
	}

	public void run(AlgorithmConfiguration configuration) {
		AlgorithmRunner.run(solGenerator, configuration);
	}

	public void run(ArrayList<AlgorithmConfiguration> configurations) {
		AlgorithmRunner.run(solGenerator, configurations);
	}
}
