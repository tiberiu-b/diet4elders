package d4elders.algorithm.helper;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import d4elders.algorithm.CuckooSearch;
import d4elders.algorithm.HoneyBeeMatingOptimization;
import d4elders.algorithm.MainAlgorithm.RunInformation;
import d4elders.dal.helper.RandomSolutionsGenerator;
import d4elders.dal.helper.SolutionsGenerator;
import d4elders.model.Solution;

public class AlgorithmRunner {
	private static final Logger log = Logger
			.getLogger(AlgorithmRunner.class.getName());

	public static void runHBMO(SolutionsGenerator solutionsGenerator,
			AlgorithmConfigurationHBMO configuration) {

		Solution queen = null;
		RunInformation info = null;

		// log.log(Level.INFO,
		// "Running Honey Bee Mating Optimization with the following configuration\n"
		// +
		// configuration);
		// System.out.println("Running Honey Bee Mating Optimization with the following configuration\n"
		// + configuration);
		HoneyBeeMatingOptimization HBMO = new HoneyBeeMatingOptimization(solutionsGenerator, configuration);
		long tMin = 99999, tMax = -1, tTotal = 0;
		double fMin = 100, fMax = -1, fAvg, fTotal = 0, tAvg;
		final int NrIter = 10;
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
			fTotal += queen.getFitness();
		}

		tAvg = (double)tTotal / NrIter;
		fAvg = fTotal / NrIter;
		System.out.println("Time Average     : " + tAvg);
		System.out.println("Fitness Avgerage : " + fAvg);
		// Print results

		System.out.println("___\nFinal Result:");
		System.out.println("	Breakfast: MenuId " + queen.getDailyMenu().getBreakfast().getMenu().getMenuId()
				+ " Cost: " + queen.getDailyMenu().getBreakfast().getCost() + " DeliveryTime: "
				+ queen.getDailyMenu().getBreakfast().getDeliveryTime() + " Food provider Id: "
				+ queen.getDailyMenu().getBreakfast().getFoodProv().getFoodProviderId());
		System.out.println("	Lunch: MenuId " + queen.getDailyMenu().getLunch().getMenu().getMenuId() + " Cost: "
				+ queen.getDailyMenu().getLunch().getCost() + " DeliveryTime: "
				+ queen.getDailyMenu().getLunch().getDeliveryTime() + " Food provider Id: "
				+ queen.getDailyMenu().getLunch().getFoodProv().getFoodProviderId());
		System.out.println("	Dinner: MenuId " + queen.getDailyMenu().getDinner().getMenu().getMenuId() + " Cost: "
				+ queen.getDailyMenu().getDinner().getCost() + " DeliveryTime: "
				+ queen.getDailyMenu().getDinner().getDeliveryTime() + " Food provider Id: "
				+ queen.getDailyMenu().getDinner().getFoodProv().getFoodProviderId());
		System.out.println("	Snack1: MenuId " + queen.getDailyMenu().getSnack1().getMenu().getMenuId() + " Cost: "
				+ queen.getDailyMenu().getSnack1().getCost() + " DeliveryTime: "
				+ queen.getDailyMenu().getSnack1().getDeliveryTime() + " Food provider Id: "
				+ queen.getDailyMenu().getSnack1().getFoodProv().getFoodProviderId());
		System.out.println("	Snack2: MenuId " + queen.getDailyMenu().getSnack2().getMenu().getMenuId() + " Cost: "
				+ queen.getDailyMenu().getSnack2().getCost() + " DeliveryTime: "
				+ queen.getDailyMenu().getSnack2().getDeliveryTime() + " Food provider Id: "
				+ queen.getDailyMenu().getSnack2().getFoodProv().getFoodProviderId());
		System.out.println(queen);
		System.out.println("Fitness: " + queen.getFitness() + "--- f1: " + queen.getF1() + "--- f2: " + queen.getF2()
				+ "--- f3: " + queen.getF3());
		//
		System.out.println("Number of iterations: " + info.nrOfItertions);

		System.out.println("Duration of execution(in millis): " + info.duration);
		System.out.println();

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

	public static void runHBMO(SolutionsGenerator solutionsGenerator,
			ArrayList<AlgorithmConfigurationHBMO> configurations) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		log.log(Level.INFO, "BEGIN: " + timeStamp);
		for (AlgorithmConfigurationHBMO config : configurations) {
			runHBMO(solutionsGenerator, config);
		}
		timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		log.log(Level.INFO, "END: " + timeStamp);
	}

	public static void runCuckoo(SolutionsGenerator solutionsGenerator, AlgorithmConfigurationCuckoo config) {

		System.out.println("Running the following configuration: " + config.toString());

		RunInformation info = null;

		CuckooSearch cuckoo = new CuckooSearch(solutionsGenerator, config);
		Solution bestCuckoo = cuckoo.performAlgorithm();
		info = cuckoo.getLastRunInformation();

		// Print results
		System.out.println("___\nFinal Result:");
		System.out.println("	Breakfast: MenuId " + bestCuckoo.getDailyMenu().getBreakfast().getMenu().getMenuId()
				+ " Cost: " + bestCuckoo.getDailyMenu().getBreakfast().getCost() + " DeliveryTime: "
				+ bestCuckoo.getDailyMenu().getBreakfast().getDeliveryTime() + " Food provider Id: "
				+ bestCuckoo.getDailyMenu().getBreakfast().getFoodProv().getFoodProviderId());
		System.out.println("	Lunch: MenuId " + bestCuckoo.getDailyMenu().getLunch().getMenu().getMenuId() + " Cost: "
				+ bestCuckoo.getDailyMenu().getLunch().getCost() + " DeliveryTime: "
				+ bestCuckoo.getDailyMenu().getLunch().getDeliveryTime() + " Food provider Id: "
				+ bestCuckoo.getDailyMenu().getLunch().getFoodProv().getFoodProviderId());
		System.out.println("	Dinner: MenuId " + bestCuckoo.getDailyMenu().getDinner().getMenu().getMenuId() + " Cost: "
				+ bestCuckoo.getDailyMenu().getDinner().getCost() + " DeliveryTime: "
				+ bestCuckoo.getDailyMenu().getDinner().getDeliveryTime() + " Food provider Id: "
				+ bestCuckoo.getDailyMenu().getDinner().getFoodProv().getFoodProviderId());
		System.out.println("	Snack1: MenuId " + bestCuckoo.getDailyMenu().getSnack1().getMenu().getMenuId() + " Cost: "
				+ bestCuckoo.getDailyMenu().getSnack1().getCost() + " DeliveryTime: "
				+ bestCuckoo.getDailyMenu().getSnack1().getDeliveryTime() + " Food provider Id: "
				+ bestCuckoo.getDailyMenu().getSnack1().getFoodProv().getFoodProviderId());
		System.out.println("	Snack2: MenuId " + bestCuckoo.getDailyMenu().getSnack2().getMenu().getMenuId() + " Cost: "
				+ bestCuckoo.getDailyMenu().getSnack2().getCost() + " DeliveryTime: "
				+ bestCuckoo.getDailyMenu().getSnack2().getDeliveryTime() + " Food provider Id: "
				+ bestCuckoo.getDailyMenu().getSnack2().getFoodProv().getFoodProviderId());
		System.out.println(bestCuckoo);
		System.out.println("Fitness: " + bestCuckoo.getFitness() + "--- f1: " + bestCuckoo.getF1() + "--- f2: "
				+ bestCuckoo.getF2() + "--- f3: " + bestCuckoo.getF3());
		//
		System.out.println("Number of iterations: " + info.nrOfItertions);

		System.out.println("Duration of execution(in millis): " + info.duration);
		System.out.println();

	}

	public static void runCuckoo(SolutionsGenerator solutionsGenerator,
			ArrayList<AlgorithmConfigurationCuckoo> configurations) {
		for (AlgorithmConfigurationCuckoo config : configurations) {
			runCuckoo(solutionsGenerator, config);
		}
	}

}
