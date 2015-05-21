package org.licenta.d4elders.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgorithmConfiguration {
	private static final Logger log = Logger.getLogger( AlgorithmConfiguration.class.getName() );

	private String broodModificationStrategy;
	private ArrayList<String> workerModificationStrategies = new ArrayList<String>();
	private int maxNrMatings;
	private int popSize;
	private int initialSpeed;
	private int initialEnergy;
	private double speedReductionFactor;
	private double energyReductionAmount;
	private double probabilityToMateDroneThreshold;

	/**
	 * Initialize an instance with the default values.
	 */
	public AlgorithmConfiguration() {
		broodModificationStrategy = AvailableProgramConfigurationOptions.SIMPLE_CROSSOVER;
		workerModificationStrategies.add(AvailableProgramConfigurationOptions.HILL_CLIMBING);
		workerModificationStrategies.add(AvailableProgramConfigurationOptions.SIMULATED_ANNEALING);
		workerModificationStrategies.add(AvailableProgramConfigurationOptions.SIMPLE_TABU_SEARCH);
		maxNrMatings = 100;
		popSize = 20;
		initialSpeed = 100;
		initialEnergy = 100;
		speedReductionFactor = .9;
		energyReductionAmount = 5;
		probabilityToMateDroneThreshold = .05;
	}

	@Override
	public String toString() {
		return "broodModificationStrategy="	+ broodModificationStrategy + "\n" +
				"workerModificationStrategies="	+ workerModificationStrategies + "\n" +
				"maxNrMatings="	+ maxNrMatings + "\npopSize=" + popSize + "\ninitialSpeed="
				+ initialSpeed + "\ninitialEnergy=" + initialEnergy
				+ "\nspeedReductionFactor=" + speedReductionFactor
				+ "\nenergyReductionAmount=" + energyReductionAmount
				+ "\nprobabilityToMateDroneThreshold="
				+ probabilityToMateDroneThreshold;
	}

	public void addWorkerModificationStrategy(Collection<String> strategies){
		for(String strategy : strategies){
			addWorkerModificationStrategy(strategy);
		}
	}

	public void addWorkerModificationStrategy(String strategy){
		if(!AvailableProgramConfigurationOptions.getAvailableWorkerModificationStrategies().contains(strategy)){
			log.log(Level.SEVERE, "Invalid worker modification strategy " + strategy);
		}

		workerModificationStrategies.add(strategy);
	}

	public void clearWorkerModificationStrategies(){
		workerModificationStrategies = new ArrayList<String>();
	}

	public void setBroodModificationStrategy(String strategy) {
		if(!AvailableProgramConfigurationOptions.getAvailableBroodModificationStrategies().contains(strategy)){
			log.log(Level.SEVERE, "Invalid brood modification strategy " + strategy);
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

}