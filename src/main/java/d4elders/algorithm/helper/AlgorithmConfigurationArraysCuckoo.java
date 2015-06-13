package d4elders.algorithm.helper;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgorithmConfigurationArraysCuckoo {
	private static final Logger log = Logger
			.getLogger(AlgorithmConfigurationArrays.class.getName());

	private String exportFileName = "data\\cuckoo_data.csv";
	private ArrayList<String> broodModificationStrategy = new ArrayList<String>();
	private ArrayList<ArrayList<String>> workerModificationStrategies = new ArrayList<ArrayList<String>>();
	private ArrayList<Integer> nestSize = new ArrayList<Integer>();
	private ArrayList<Integer> maxIterations = new ArrayList<Integer>();
	private ArrayList<Double> pa = new ArrayList<Double>();
	private ArrayList<Integer> hillClimbingNeighborhoodSize = new ArrayList<Integer>();
	private ArrayList<Double> T0 = new ArrayList<Double>();
	private ArrayList<Double> alpha = new ArrayList<Double>();
	private ArrayList<Double> Tmin = new ArrayList<Double>();
	private ArrayList<Integer> maxNrIterations = new ArrayList<Integer>();
	private ArrayList<Integer> tabuSize = new ArrayList<Integer>();
	private ArrayList<Integer> tabuNeighborhoodSize = new ArrayList<Integer>();

	public AlgorithmConfigurationArraysCuckoo() {
	}

	/**
	 * Computes all the possible combinations.
	 *
	 * @return
	 */
	public ArrayList<AlgorithmConfigurationCuckoo> getAllConfigrations() {
		populateEmptyArray();
		ArrayList<AlgorithmConfigurationCuckoo> configs = new ArrayList<AlgorithmConfigurationCuckoo>();

		for (String broodModificationStrategy : this.broodModificationStrategy) {
			for (ArrayList<String> workerModificationStrategies : this.workerModificationStrategies) {
				for (int nestSize : this.nestSize) {
					for (int maxIterations : this.maxIterations) {
						for (double pa : this.pa) {
							for (int hillClimbingNeighborhoodSize : this.hillClimbingNeighborhoodSize) {
								for (double T0 : this.T0) {
									for (double alpha : this.alpha) {
										for (double Tmin : this.Tmin) {
											for (int maxNrIterations : this.maxNrIterations) {
												for (int tabuSize : this.tabuSize) {
													for (int tabuNeighborhoodSize : this.tabuNeighborhoodSize) {
														AlgorithmConfigurationCuckoo algorithmConfiguration = new AlgorithmConfigurationCuckoo(
																broodModificationStrategy,
																workerModificationStrategies,
																nestSize,
																maxIterations,
																pa,
																hillClimbingNeighborhoodSize,
																T0,
																alpha,
																Tmin,
																maxNrIterations,
																tabuSize,
																tabuNeighborhoodSize);

														algorithmConfiguration
																.setExportFileName(exportFileName);
														configs.add(algorithmConfiguration);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return configs;
	}

	private void populateEmptyArray() {
		// initialize the arrays with the default values from a configurtion
		AlgorithmConfigurationHBMO config = new AlgorithmConfigurationHBMO();
		if (broodModificationStrategy.isEmpty())
			broodModificationStrategy
					.add(config.getBroodModificationStrategy());
		if (workerModificationStrategies.isEmpty())
			workerModificationStrategies.add(config
					.getWorkerModificationStrategies());
		if (nestSize.isEmpty())
			nestSize.add(config.getMaxNrMatings());
		if (maxIterations.isEmpty())
			maxIterations.add(config.getPopSize());
		if (pa.isEmpty())
			pa.add(config.getSpeedReductionFactor());
		if(hillClimbingNeighborhoodSize.isEmpty())
			hillClimbingNeighborhoodSize.add(config.getHillClimbingNeighborhoodSize());
		if(T0.isEmpty())
			T0.add(config.getT0());
		if(alpha.isEmpty())
			alpha.add(config.getAlpha());
		if(Tmin.isEmpty())
			Tmin.add(config.getTmin());
		if(maxNrIterations.isEmpty())
			maxNrIterations.add(config.getMaxNrIterations());
		if(tabuSize.isEmpty())
			tabuSize.add(config.getTabuSize());
		if(tabuNeighborhoodSize.isEmpty())
			tabuNeighborhoodSize.add(config.getTabuNeighborhoodSize());

		/*
		 * private ArrayList<String> broodModificationStrategy = new
		 * ArrayList<String>(); private ArrayList<ArrayList<String>>
		 * workerModificationStrategies = new ArrayList<ArrayList<String>>();
		 * private ArrayList<Integer> maxNrMatings = new ArrayList<Integer>();
		 * private ArrayList<Integer> popSize = new ArrayList<Integer>();
		 * private ArrayList<Integer> initialSpeed = new ArrayList<Integer>();
		 * private ArrayList<Integer> initialEnergy = new ArrayList<Integer>();
		 * private ArrayList<Double> speedReductionFactor = new
		 * ArrayList<Double>(); private ArrayList<Double> energyReductionAmount
		 * = new ArrayList<Double>(); private ArrayList<Double>
		 * probabilityToMateDroneThreshold = new ArrayList<Double>(); private
		 * ArrayList<Double> similarityCoefficientThreshold = new
		 * ArrayList<Double>();
		 * private ArrayList<Integer> hillClimbingNeighborhoodSize = new ArrayList<Integer>();
	private ArrayList<Double> T0 = new ArrayList<Double>();
	private ArrayList<Double> alpha = new ArrayList<Double>();
	private ArrayList<Double> Tmin = new ArrayList<Double>();
	private ArrayList<Integer> maxNrIterations = new ArrayList<Integer>();
	private ArrayList<Integer> tabuSize = new ArrayList<Integer>();
	private ArrayList<Integer> tabuNeighborhoodSize = new ArrayList<Integer>();
		 */
	}

	public void addBroodModificationStrategy(String strategy) {
		// little sanity check
		if (!AvailableProgramConfigurationOptions
				.getAvailableBroodModificationStrategies().contains(strategy)) {
			log.log(Level.SEVERE, "Invalid brood modification strategy "
					+ strategy);
		}
		this.broodModificationStrategy.add(strategy);
	}

	public void addWorkerModificationStrategies(ArrayList<String> strategies) {
		// little sanity check
		for (String strategy : strategies) {
			if (!AvailableProgramConfigurationOptions
					.getAvailableWorkerModificationStrategies().contains(
							strategy)) {
				log.log(Level.SEVERE, "Invalid worker modification strategy "
						+ strategy);
			}
		}
		this.workerModificationStrategies.add(strategies);
	}

	public void addNestSize(int value) {
		this.nestSize.add(value);
	}

	public void addMaxIterations(int value) {
		this.maxIterations.add(value);
	}

	public void addPa(double value){
		this.pa.add(value);
	}

	public void addHillClimbingNeighborhoodSize(int value){
		this.hillClimbingNeighborhoodSize.add(value);
	}

	public void addT0(double value) {
		this.T0.add(value);
	}

	public void addAlpha(double value) {
		this.alpha.add(value);

	}

	public void addTmin(double value) {
		this.Tmin.add(value);
	}

	public void addMaxNrIterations(int value) {
		this.maxNrIterations.add(value);

	}

	public void addTabuSize(int value) {
		this.tabuSize.add(value);
	}

	public void addTabuNeighSize(int value) {
		this.tabuNeighborhoodSize.add(value);

	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}
}






















