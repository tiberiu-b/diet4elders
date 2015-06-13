package d4elders.algorithm.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgorithmConfigurationCuckoo {
	@Override
	public String toString() {
		return "AlgorithmConfigurationCuckoo [exportFileName=" + exportFileName
				+ ", broodModificationStrategy=" + broodModificationStrategy
				+ ", workerModificationStrategies="
				+ workerModificationStrategies + ", nestSize=" + nestSize
				+ ", maxIterations=" + maxIterations + ", pa=" + pa
				+ ", hillClimbingNeighborhoodSize="
				+ hillClimbingNeighborhoodSize + ", T0=" + T0 + ", alpha="
				+ alpha + ", Tmin=" + Tmin + ", maxNrIterations="
				+ maxNrIterations + ", tabuSize=" + tabuSize
				+ ", tabuNeighborhoodSize=" + tabuNeighborhoodSize + "]";
	}

	private static final Logger log = Logger
			.getLogger(AlgorithmConfigurationCuckoo.class.getName());

	private String exportFileName = "data\\HBMO_data.csv";

	private String broodModificationStrategy;
	private ArrayList<String> workerModificationStrategies = new ArrayList<String>();
	private int nestSize;
	private int maxIterations;
	private double pa;
	private CuckooAlgorithmVersion algorithmVersion;

	// Hill Climbing
		private int hillClimbingNeighborhoodSize = 10;

		// Simulated Annealing
		private double T0 = 0.001;
		private double alpha = 0.7;
		private double Tmin = 0.0001;

		// Tabu Searh
		private int maxNrIterations = 10;
		private int tabuSize = 10;
		private int tabuNeighborhoodSize = 10;


	public AlgorithmConfigurationCuckoo() {
		// set default values
		setNestSize(5);
		setMaxIterations(500);
		setPa(0.7);
		setAlgorithmVersion(CuckooAlgorithmVersion.Version1);

	}



	public AlgorithmConfigurationCuckoo(
			String broodModificationStrategy,
			ArrayList<String> workerModificationStrategies, int nestSize,
			int maxIterations, double pa, int hillClimbingNeighborhoodSize,
			double t0, double alpha, double tmin, int maxNrIterations,
			int tabuSize, int tabuNeighborhoodSize, CuckooAlgorithmVersion algorithmVersion) {
		super();
		this.broodModificationStrategy = broodModificationStrategy;
		this.workerModificationStrategies = workerModificationStrategies;
		this.nestSize = nestSize;
		this.maxIterations = maxIterations;
		this.pa = pa;
		this.hillClimbingNeighborhoodSize = hillClimbingNeighborhoodSize;
		T0 = t0;
		this.alpha = alpha;
		Tmin = tmin;
		this.maxNrIterations = maxNrIterations;
		this.tabuSize = tabuSize;
		this.tabuNeighborhoodSize = tabuNeighborhoodSize;
		this.algorithmVersion = algorithmVersion;
	}

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
		//data.add(broodModificationStrategy.toString());
		//data.add(workerModificationStrategies.toString());
		data.add(String.valueOf(algorithmVersion));
		data.add(String.valueOf(nestSize));
		data.add(String.valueOf(maxIterations));
		data.add(String.valueOf(pa));
		data.add(String.valueOf(hillClimbingNeighborhoodSize));
		data.add(String.valueOf(T0));
		data.add(String.valueOf(alpha));
		data.add(String.valueOf(Tmin));
		data.add(String.valueOf(maxNrIterations));
		data.add(String.valueOf(tabuSize));
		data.add(String.valueOf(tabuNeighborhoodSize));
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
		if(!workerModificationStrategies.contains(strategy))
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


	public int getNestSize() {
		return nestSize;
	}

	public void setNestSize(int nestSize) {
		this.nestSize = nestSize;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	public double getPa() {
		return pa;
	}

	public void setPa(double pa) {
		this.pa = pa;
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

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public CuckooAlgorithmVersion getAlgorithmVersion() {
		return algorithmVersion;
	}

	public void setAlgorithmVersion(CuckooAlgorithmVersion algorithmVersion) {
		this.algorithmVersion = algorithmVersion;
	}

}
