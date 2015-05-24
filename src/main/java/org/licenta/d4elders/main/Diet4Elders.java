package org.licenta.d4elders.main;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.licenta.d4elders.algorithm.HoneyBeeMatingOptimization;
import org.licenta.d4elders.algorithm.MainAlgorithm.RunInformation;
import org.licenta.d4elders.dal.BusinessLogicCache;
import org.licenta.d4elders.helper.AlgorithmConfiguration;
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

		Solution queen = null;
		RunInformation info = null;

		// log.log(Level.INFO,
		// "Running Honey Bee Mating Optimization with the following configuration\n" +
		// configuration);
		// System.out.println("Running Honey Bee Mating Optimization with the following configuration\n"
		// + configuration);
		HoneyBeeMatingOptimization HBMO = new HoneyBeeMatingOptimization(configuration);
		queen = HBMO.performAlgorithm();
		info = HBMO.getLastRunInformation();

		// Print results
		System.out.println("___\nFinal Result:");
		System.out.println(queen + "\nFitness: " + queen.getFitness());
		System.out.println("Number of iterations: " + info.nrOfItertions);

		System.out.println("Duration of execution(in millis): " + info.duration);

		// Export data
		ArrayList<String> data = configuration.getAllDataAsString();
		data.add(String.valueOf(queen.getFitness()));
		data.add(String.valueOf(info.nrOfItertions));
		data.add(String.valueOf(info.duration));
		data.addAll(queen.exportDataAsString());
		try {
			DataExporter.exportData(Paths.get("data\\HBMO_data.csv"), HBMO.getHeadersForExportedData(), data);
		} catch (DataExporterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
