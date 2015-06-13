package d4elders.mvc.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import d4elders.algorithm.CuckooSearch;
import d4elders.algorithm.MainAlgorithm.RunInformation;
import d4elders.algorithm.broodImprover.BusinessLogicCacheFilteredOpt;
import d4elders.algorithm.helper.*;
import d4elders.dal.helper.*;
import d4elders.model.Solution;
import d4elders.model.user_profile.NutritionalRecommandationHelper;
import d4elders.model.user_profile.UserProfileStub;

public class Controller {
	private static final Logger log = Logger.getLogger(Controller.class.getName());

	private static Controller controller;
	private static UserProfileStub userProfile;
	private static SolutionsGenerator solGenerator;
	private static AlgorithmConfigurationHBMO hbmoConf;
	private static AlgorithmConfigurationCuckoo cuckooConf;

	private Controller() {
		if (solGenerator == null)
			solGenerator = new RandomSolutionsGenerator();
		userProfile = new UserProfileStub();
		new NutritionalRecommandationHelper(userProfile);
		// BusinessLogic bLogic = BusinessLogic.getInstance();
		BusinessLogicCacheFilteredOpt blCacheFiltered = BusinessLogicCacheFilteredOpt.getInstance();
		blCacheFiltered.loadOntologyDataIntoMemory(userProfile.getAllergyList());
		hbmoConf = new AlgorithmConfigurationHBMO();
		cuckooConf = new AlgorithmConfigurationCuckoo();
	}

	public static Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	public void runHBMO(AlgorithmConfigurationHBMO configuration) {
		AlgorithmRunner.runHBMO(solGenerator, configuration);
	}

	public void runHBMO(ArrayList<AlgorithmConfigurationHBMO> configurations) {
		AlgorithmRunner.runHBMO(solGenerator, configurations);
	}

	public void runCuckoo(AlgorithmConfigurationCuckoo defaultConfiguration) {

		AlgorithmRunner.runCuckoo(solGenerator, defaultConfiguration);
		// CuckooSearch c = new CuckooSearch(solGenerator);
		// Solution res = c.performAlgorithm();
		// RunInformation info = c.getLastRunInformation();
		// System.out.println(res + "\n" + res.getFitness() + "\n" + info.duration);

	}

	public static AlgorithmConfigurationHBMO getHbmoConf() {
		return hbmoConf;
	}

	public static void setHbmoConf(AlgorithmConfigurationHBMO hbmoConf) {
		Controller.hbmoConf = hbmoConf;
	}

	public static Logger getLog() {
		return log;
	}

	public static AlgorithmConfigurationCuckoo getCuckooConf() {
		return cuckooConf;
	}

	public static void setCuckooConf(AlgorithmConfigurationCuckoo cuckooConf) {
		Controller.cuckooConf = cuckooConf;
	}
}
