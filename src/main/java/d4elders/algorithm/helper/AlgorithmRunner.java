package d4elders.algorithm.helper;

import java.nio.file.Paths;
import java.util.ArrayList;

import d4elders.algorithm.HoneyBeeMatingOptimization;
import d4elders.algorithm.MainAlgorithm.RunInformation;
import d4elders.dal.helper.RandomSolutionsGenerator;
import d4elders.dal.helper.SolutionsGenerator;
import d4elders.model.Solution;

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
		long tMin = 99999, tMax = -1, tTotal = 0;
		double fMin = 100, fMax = -1, fAvg, fTotal = 0, tAvg;
		final int NrIter = 2;
		for(int i = 0; i < NrIter; ++i)
		{
			queen = HBMO.performAlgorithm();
			info = HBMO.getLastRunInformation();

			if(info.duration < tMin)
				tMin = info.duration;

			if(info.duration > tMax)
				tMax = info.duration;
			tTotal += info.duration;

			if(queen.getFitness() < fMin)
				fMin = queen.getFitness();

			if(queen.getFitness() > fMax)
				fMax = queen.getFitness();
			fTotal = queen.getFitness();
		}

		tAvg = (double)tTotal / NrIter;
		fAvg = fTotal / NrIter;
		System.out.println("Time Average     : " + tAvg);
		System.out.println("Fitness Avgerage : " + fAvg);
		// Print results
/*
		System.out.println("___\nFinal Result:");
		System.out.println("	Breakfast: MenuId "
				+ queen.getDailyMenu().getBreakfast().getMenu().getMenuId()
				+ " Cost: "
				+ queen.getDailyMenu().getBreakfast().getCost()
				+ " DeliveryTime: "
				+ queen.getDailyMenu().getBreakfast().getDeliveryTime()
				+ " Food provider Id: "
				+ queen.getDailyMenu().getBreakfast().getFoodProv()
						.getFoodProviderId());
		System.out.println("	Lunch: MenuId "
				+ queen.getDailyMenu().getLunch().getMenu().getMenuId()
				+ " Cost: "
				+ queen.getDailyMenu().getLunch().getCost()
				+ " DeliveryTime: "
				+ queen.getDailyMenu().getLunch().getDeliveryTime()
				+ " Food provider Id: "
				+ queen.getDailyMenu().getLunch().getFoodProv()
						.getFoodProviderId());
		System.out.println("	Dinner: MenuId "
				+ queen.getDailyMenu().getDinner().getMenu().getMenuId()
				+ " Cost: "
				+ queen.getDailyMenu().getDinner().getCost()
				+ " DeliveryTime: "
				+ queen.getDailyMenu().getDinner().getDeliveryTime()
				+ " Food provider Id: "
				+ queen.getDailyMenu().getDinner().getFoodProv()
						.getFoodProviderId());
		System.out.println("	Snack1: MenuId "
				+ queen.getDailyMenu().getSnack1().getMenu().getMenuId()
				+ " Cost: "
				+ queen.getDailyMenu().getSnack1().getCost()
				+ " DeliveryTime: "
				+ queen.getDailyMenu().getSnack1().getDeliveryTime()
				+ " Food provider Id: "
				+ queen.getDailyMenu().getSnack1().getFoodProv()
						.getFoodProviderId());
		System.out.println("	Snack2: MenuId "
				+ queen.getDailyMenu().getSnack2().getMenu().getMenuId()
				+ " Cost: "
				+ queen.getDailyMenu().getSnack2().getCost()
				+ " DeliveryTime: "
				+ queen.getDailyMenu().getSnack2().getDeliveryTime()
				+ " Food provider Id: "
				+ queen.getDailyMenu().getSnack2().getFoodProv()
						.getFoodProviderId());
		System.out.println(queen);
		System.out.println("Fitness: " + queen.getFitness() + "--- f1: "
				+ queen.getF1() + "--- f2: " + queen.getF2() + "--- f3: "
				+ queen.getF3());
		//
		System.out.println("Number of iterations: " + info.nrOfItertions);

		System.out
				.println("Duration of execution(in millis): " + info.duration);
		System.out.println();
*/
		// Export data
		ArrayList<String> data = configuration.getAllDataAsString();

		data.add(String.valueOf(info.nrOfItertions));
		//data.add(String.valueOf(info.duration));

		data.add(String.valueOf(fMin));
		data.add(String.valueOf(fMax));
		data.add(String.valueOf(fAvg));

		data.add(String.valueOf(tMin));
		data.add(String.valueOf(tMax));
		data.add(String.valueOf(tAvg));

		data.addAll(queen.exportDataAsString());
		try {
			DataExporter.exportData(Paths.get(configuration.getExportFileName()),
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
