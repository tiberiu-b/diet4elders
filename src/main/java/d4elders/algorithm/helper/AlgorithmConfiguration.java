package d4elders.algorithm.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgorithmConfiguration {
	private static final Logger log = Logger
			.getLogger(AlgorithmConfiguration.class.getName());

	// HBMO
	private String broodModificationStrategy;
	private ArrayList<String> workerModificationStrategies = new ArrayList<String>();
	private int maxNrMatings;
	private int popSize;
	private int initialSpeed;
	private int initialEnergy;
	private double speedReductionFactor;
	private double energyReductionAmount;
	private double probabilityToMateDroneThreshold;
	private double similarityCoefficientThreshold = 0.8;
	private double errorMargin2_K = 0.7;

	// Hill Climbing
	private int hillClimbingNeighborhoodSize = 10;

	// Simulated Annealing
	private double T0 = 0.001;
	private double alpha = 0.7;
	private double Tmin = 0.0001;

	// Tabu Searh
	private int maxNrIterations = 100;
	private int tabuSize = 10;
	private int tabuNeighborhoodSize = 10;

	/**
	 * Initialize only HBMO parameters.
	 */
	public AlgorithmConfiguration(String broodModificationStrategy,
			ArrayList<String> workerModificationStrategies, int maxNrMatings,
			int popSize, int initialSpeed, int initialEnergy,
			double speedReductionFactor, double energyReductionAmount,
			double probabilityToMateDroneThreshold,
			double similarityCoefficientThreshold, double errorMargin2_K) {
		super();
		this.broodModificationStrategy = broodModificationStrategy;
		this.workerModificationStrategies = workerModificationStrategies;
		this.maxNrMatings = maxNrMatings;
		this.popSize = popSize;
		this.initialSpeed = initialSpeed;
		this.initialEnergy = initialEnergy;
		this.speedReductionFactor = speedReductionFactor;
		this.energyReductionAmount = energyReductionAmount;
		this.probabilityToMateDroneThreshold = probabilityToMateDroneThreshold;
		this.similarityCoefficientThreshold = similarityCoefficientThreshold;
		this.setErrorMargin2_K(errorMargin2_K);
	}

	/**
	 * Initialize an instance with the default values.
	 */
	public AlgorithmConfiguration() {
		broodModificationStrategy = AvailableProgramConfigurationOptions.SIMPLE_CROSSOVER;
		workerModificationStrategies
				.add(AvailableProgramConfigurationOptions.HILL_CLIMBING);
		workerModificationStrategies
				.add(AvailableProgramConfigurationOptions.SIMULATED_ANNEALING);
		workerModificationStrategies
				.add(AvailableProgramConfigurationOptions.SIMPLE_TABU_SEARCH);
		maxNrMatings = 15;
		popSize = 20;
		initialSpeed = 100;
		initialEnergy = 100;
		speedReductionFactor = .9;
		energyReductionAmount = 5;
		probabilityToMateDroneThreshold = .05;
	}

	@Override
	public String toString() {
		return "broodModificationStrategy=" + broodModificationStrategy + "\n"
				+ "workerModificationStrategies="
				+ workerModificationStrategies + "\n" + "maxNrMatings="
				+ maxNrMatings + "\npopSize=" + popSize + "\ninitialSpeed="
				+ initialSpeed + "\ninitialEnergy=" + initialEnergy
				+ "\nspeedReductionFactor=" + speedReductionFactor
				+ "\nenergyReductionAmount=" + energyReductionAmount
				+ "\nprobabilityToMateDroneThreshold="
				+ probabilityToMateDroneThreshold;
	}

	/**
	 * Returns the stored information in a nice format: Map[param]=value NOT
	 * Anymore - just a list
	 *
	 * @return a Map from param to its value
	 */
	public ArrayList<String> getAllDataAsString() {
		/*
		 * Map<String, String> data = new HashMap<String, String>();
		 * data.put("broodModificationStrategy",
		 * AvailableProgramConfigurationOptions.SIMPLE_CROSSOVER.toString());
		 * data.put("workerModificationStrategies",
		 * workerModificationStrategies.toString()); data.put("maxNrMatings",
		 * String.valueOf(maxNrMatings)); data.put("popSize",
		 * String.valueOf(popSize)); data.put("initialSpeed",
		 * String.valueOf(initialSpeed)); data.put("initialEnergy",
		 * String.valueOf(initialEnergy)); data.put("speedReductionFactor",
		 * String.valueOf(speedReductionFactor));
		 * data.put("energyReductionAmount",
		 * String.valueOf(energyReductionAmount));
		 * data.put("probabilityToMateDroneThreshold",
		 * String.valueOf(probabilityToMateDroneThreshold));
		 */
		ArrayList<String> data = new ArrayList<String>();
		data.add(broodModificationStrategy.toString());
		data.add(workerModificationStrategies.toString());
		data.add(String.valueOf(maxNrMatings));
		data.add(String.valueOf(popSize));
		data.add(String.valueOf(initialSpeed));
		data.add(String.valueOf(initialEnergy));
		data.add(String.valueOf(speedReductionFactor));
		data.add(String.valueOf(energyReductionAmount));
		data.add(String.valueOf(probabilityToMateDroneThreshold));
		return data;
	}

	public void addWorkerModificationStrategy(Collection<String> strategies) {
		for (String strategy : strategies) {
			addWorkerModificationStrategy(strategy);
		}
	}

	public void addWorkerModificationStrategy(String strategy) {
		if (!AvailableProgramConfigurationOptions
				.getAvailableWorkerModificationStrategies().contains(strategy)) {
			log.log(Level.SEVERE, "Invalid worker modification strategy "
					+ strategy);
		}

		workerModificationStrategies.add(strategy);
	}

	public void clearWorkerModificationStrategies() {
		workerModificationStrategies = new ArrayList<String>();
	}

	public void setBroodModificationStrategy(String strategy) {
		if (!AvailableProgramConfigurationOptions
				.getAvailableBroodModificationStrategies().contains(strategy)) {
			log.log(Level.SEVERE, "Invalid brood modification strategy "
					+ strategy);
		}
		broodModificationStrategy = strategy;
	}

	public String getBroodModificationStrategy() {
		return broodModificationStrategy;
	}

	public ArrayList<String> getWorkerModificationStrategies() {
		return workerModificationStrategies;
	}

	public double getSpeedReductionFactor() {
		return speedReductionFactor;
	}

	public void setSpeedReductionFactor(double speedReductionFactor) {
		this.speedReductionFactor = speedReductionFactor;
	}

	public double getEnergyReductionAmount() {
		return energyReductionAmount;
	}

	public void setEnergyReductionAmount(double energyReductionAmount) {
		this.energyReductionAmount = energyReductionAmount;
	}

	public double getProbabilityToMateDroneThreshold() {
		return probabilityToMateDroneThreshold;
	}

	public void setProbabilityToMateDroneThreshold(
			double probabilityToMateDroneThreshold) {
		this.probabilityToMateDroneThreshold = probabilityToMateDroneThreshold;
	}

	public int getMaxNrMatings() {
		return maxNrMatings;
	}

	public void setMaxNrMatings(int maxNrMatings) {
		this.maxNrMatings = maxNrMatings;
	}

	public int getPopSize() {
		return popSize;
	}

	public void setPopSize(int popSize) {
		this.popSize = popSize;
	}

	public int getInitialSpeed() {
		return initialSpeed;
	}

	public void setInitialSpeed(int speed) {
		this.initialSpeed = speed;
	}

	public int getInitialEnergy() {
		return initialEnergy;
	}

	public void setInitialEnergy(int energy) {
		this.initialEnergy = energy;
	}

	public double getSimilarityCoefficientThreshold() {
		return similarityCoefficientThreshold;
	}

	public void setSimilarityCoefficientThreshold(
			double similarityCoefficientThreshold) {
		this.similarityCoefficientThreshold = similarityCoefficientThreshold;
	}

	public double getErrorMargin2_K() {
		return errorMargin2_K;
	}

	public void setErrorMargin2_K(double errorMargin2_K) {
		this.errorMargin2_K = errorMargin2_K;
	}

	public int getHillClimbingNeighborhoodSize() {
		return hillClimbingNeighborhoodSize;
	}

	public void setHillClimbingNeighborhoodSize(int hillClimbingNeighborhoodSize) {
		this.hillClimbingNeighborhoodSize = hillClimbingNeighborhoodSize;
	}

	public double getT0() {
		return T0;
	}

	public void setT0(double t0) {
		T0 = t0;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getTmin() {
		return Tmin;
	}

	public void setTmin(double tmin) {
		Tmin = tmin;
	}

	public int getMaxNrIterations() {
		return maxNrIterations;
	}

	public void setMaxNrIterations(int maxNrIterations) {
		this.maxNrIterations = maxNrIterations;
	}

	public int getTabuSize() {
		return tabuSize;
	}

	public void setTabuSize(int tabuSize) {
		this.tabuSize = tabuSize;
	}

	public int getTabuNeighborhoodSize() {
		return tabuNeighborhoodSize;
	}

	public void setTabuNeighborhoodSize(int tabuNeighborhoodSize) {
		this.tabuNeighborhoodSize = tabuNeighborhoodSize;
	}



}
