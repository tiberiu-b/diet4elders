package org.licenta.d4elders.helper;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Configurion parameters pools from which to choose.
 * @author cristiprg
 *
 */
public class AvailableProgramConfigurationOptions {
	public final static String PATH_RELINKING = "Path Relinking";
	public final static String SIMPLE_CROSSOVER = "Simple Crossover";
	public final static String HILL_CLIMBING = "Hill Climbing";
	public final static String SIMULATED_ANNEALING = "Simulated Annealing";
	public final static String SIMPLE_TABU_SEARCH = "Simple tabu search";

	private static ArrayList<String> availableBroodModificationStrategies = new ArrayList<String>();
	private static ArrayList<String> availableWorkerModificationStrategies = new ArrayList<String>();

	static{
		availableBroodModificationStrategies.add(SIMPLE_CROSSOVER);
		availableBroodModificationStrategies.add(PATH_RELINKING);

		availableWorkerModificationStrategies.add(HILL_CLIMBING);
		availableWorkerModificationStrategies.add(SIMULATED_ANNEALING);
		availableWorkerModificationStrategies.add(SIMPLE_TABU_SEARCH);
	}


	/**
	 *
	 * @return all the brood modification strategies that are implemented
	 */
	public static ArrayList<String> getAvailableBroodModificationStrategies() {
		return availableBroodModificationStrategies;
	}

	/**
	 *
	 * @return all the worker modification strategies that are implemented
	 */
	public static ArrayList<String> getAvailableWorkerModificationStrategies() {
		return availableWorkerModificationStrategies;
	}



}
