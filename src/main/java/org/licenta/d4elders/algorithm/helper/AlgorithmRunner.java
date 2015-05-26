package org.licenta.d4elders.algorithm.helper;

import java.nio.file.Paths;
import java.util.ArrayList;

import org.licenta.d4elders.algorithm.HoneyBeeMatingOptimization;
import org.licenta.d4elders.algorithm.MainAlgorithm.RunInformation;
import org.licenta.d4elders.dal.helper.RandomSolutionsGenerator;
import org.licenta.d4elders.dal.helper.SolutionsGenerator;
import org.licenta.d4elders.model.Solution;

public class AlgorithmRunner {
	public static void run(SolutionsGenerator solutionsGenerator,
			AlgorithmConfiguration configuration) {

		Solution queen = null;
		RunInformation info = null;

		// log.log(Level.INFO,
		// "Running Honey Bee Mating Optimization with the following configuration\n"
		// +
		// configuration);
		// System.out.println("Running Honey Bee Mating Optimization with the following configuration\n"
		// + configuration);
		HoneyBeeMatingOptimization HBMO = new HoneyBeeMatingOptimization(
				solutionsGenerator, configuration);
		queen = HBMO.performAlgorithm();
		info = HBMO.getLastRunInformation();

		// Print results
		System.out.println("___\nFinal Result:");
		System.out.println(queen + "\nFitness: " + queen.getFitness());
		System.out.println("Number of iterations: " + info.nrOfItertions);

		System.out
				.println("Duration of execution(in millis): " + info.duration);
		System.out.println();
		// Export data
		ArrayList<String> data = configuration.getAllDataAsString();
		data.add(String.valueOf(queen.getFitness()));
		data.add(String.valueOf(info.nrOfItertions));
		data.add(String.valueOf(info.duration));
		data.addAll(queen.exportDataAsString());
		try {
			DataExporter.exportData(Paths.get("data\\HBMO_data.csv"),
					HBMO.getCustomHeadersForExportedData(), data);
		} catch (DataExporterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void run(SolutionsGenerator solutionsGenerator,
			ArrayList<AlgorithmConfiguration> configurations) {
		for (AlgorithmConfiguration config : configurations) {
			run(solutionsGenerator, config);
		}
	}
}
