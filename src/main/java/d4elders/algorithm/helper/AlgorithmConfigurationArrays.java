package d4elders.algorithm.helper;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgorithmConfigurationArrays {
	private static final Logger log = Logger
			.getLogger(AlgorithmConfigurationArrays.class.getName());

	private ArrayList<String> broodModificationStrategy = new ArrayList<String>();
	private ArrayList<ArrayList<String>> workerModificationStrategies = new ArrayList<ArrayList<String>>();
	private ArrayList<Integer> maxNrMatings = new ArrayList<Integer>();
	private ArrayList<Integer> popSize = new ArrayList<Integer>();
	private ArrayList<Integer> initialSpeed = new ArrayList<Integer>();
	private ArrayList<Integer> initialEnergy = new ArrayList<Integer>();
	private ArrayList<Double> speedReductionFactor = new ArrayList<Double>();
	private ArrayList<Double> energyReductionAmount = new ArrayList<Double>();
	private ArrayList<Double> probabilityToMateDroneThreshold = new ArrayList<Double>();
	private ArrayList<Double> similarityCoefficientThreshold = new ArrayList<Double>();
	private ArrayList<Double> errorMargin2_K = new ArrayList<Double>();

	public AlgorithmConfigurationArrays() {
	}

	/**
	 * Computes all the possible combinations.
	 * 
	 * @return
	 */
	public ArrayList<AlgorithmConfiguration> getAllConfigrations() {
		populateEmptyArray();
		ArrayList<AlgorithmConfiguration> configs = new ArrayList<AlgorithmConfiguration>();

		for (String broodModificationStrategy : this.broodModificationStrategy) {
			for (ArrayList<String> workerModificationStrategies : this.workerModificationStrategies) {
				for (int maxNrMatings : this.maxNrMatings) {
					for (int popSize : this.popSize) {
						for (int initialSpeed : this.initialSpeed) {
							for (int initialEnergy : this.initialEnergy) {
								for (double speedReductionFactor : this.speedReductionFactor) {
									for (double energyReductionAmount : this.energyReductionAmount) {
										for (double probabilityToMateDroneThreshold : this.probabilityToMateDroneThreshold) {
											for (double similarityCoefficientThreshold : this.similarityCoefficientThreshold) {
												for (double errorMargin2_K : this.errorMargin2_K) {
													configs.add(new AlgorithmConfiguration(
															broodModificationStrategy,
															workerModificationStrategies,
															maxNrMatings,
															popSize,
															initialSpeed,
															initialEnergy,
															speedReductionFactor,
															energyReductionAmount,
															probabilityToMateDroneThreshold,
															similarityCoefficientThreshold,
															errorMargin2_K));
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
		AlgorithmConfiguration config = new AlgorithmConfiguration();
		if (broodModificationStrategy.isEmpty())
			broodModificationStrategy
					.add(config.getBroodModificationStrategy());
		if (workerModificationStrategies.isEmpty())
			workerModificationStrategies.add(config
					.getWorkerModificationStrategies());
		if (maxNrMatings.isEmpty())
			maxNrMatings.add(config.getMaxNrMatings());
		if (popSize.isEmpty())
			popSize.add(config.getPopSize());
		if (initialSpeed.isEmpty())
			initialSpeed.add(config.getInitialSpeed());
		if (initialEnergy.isEmpty())
			initialEnergy.add(config.getInitialEnergy());
		if (speedReductionFactor.isEmpty())
			speedReductionFactor.add(config.getSpeedReductionFactor());
		if (energyReductionAmount.isEmpty())
			energyReductionAmount.add(config.getEnergyReductionAmount());
		if (probabilityToMateDroneThreshold.isEmpty())
			probabilityToMateDroneThreshold.add(config
					.getProbabilityToMateDroneThreshold());
		if (similarityCoefficientThreshold.isEmpty())
			similarityCoefficientThreshold.add(config
					.getSimilarityCoefficientThreshold());

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

	public void addMaxNrMatings(int value) {
		this.maxNrMatings.add(value);
	}

	public void addPopSize(int value) {
		this.popSize.add(value);
	}

	public void addInitialEnergy(int value) {
		this.initialEnergy.add(value);
	}

	public void addInitialSpeed(int value) {
		this.initialSpeed.add(value);
	}

	public void addSpeedReductionFactor(double value) {
		this.speedReductionFactor.add(value);
	}

	public void addEnergyReductionAmlount(double value) {
		this.energyReductionAmount.add(value);
	}

	public void addProbabilityToMateDroneThreshold(double value) {
		this.probabilityToMateDroneThreshold.add(value);
	}

	public void addSimilarityCoefficientThreshold(double value) {
		this.similarityCoefficientThreshold.add(value);
	}

}
