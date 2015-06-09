package d4elders.algorithm.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;
import org.openjena.atlas.logging.Log;

public class ConfigurationsGenerator {
	private static final Logger log = Logger.getLogger(ConfigurationsGenerator.class.getName());

	/**
	 * Generates an ArrayList of length nrConfigs instances of {@link AlgorithmConfiguration} having
	 * the following popSize values: 1, 3, 5, ...
	 * @param nrConfigs
	 * @return
	 */
	public static ArrayList<AlgorithmConfiguration> varPopSize(int nrConfigs){
		ArrayList<AlgorithmConfiguration> configs = new ArrayList<AlgorithmConfiguration>();

		for (int i = 0; i < nrConfigs; ++i){
			AlgorithmConfiguration config = new AlgorithmConfiguration();
			config.setPopSize(i * 2 + 1 );
			configs.add(config);
		}

		return configs;
	}

	/**
	 * Returns all the possible combinations of {@link AlgorithmConfiguration} from the ini file.
	 * @param iniFilePath
	 * @return an ArrayList<AlgorithmConfiguration> that contains all the possible combinations.
	 */
	public static ArrayList<AlgorithmConfiguration> getConfigFromIniFile(String iniFilePath){
		ArrayList<AlgorithmConfigurationArrays> configPacks = new ArrayList<AlgorithmConfigurationArrays>();
		// ini examples here: http://ini4j.sourceforge.net/tutorial/OneMinuteTutorial.java.html
		try {
			Wini ini = new Wini(new File(iniFilePath));

			for(String sectionName : ini.keySet()){
				AlgorithmConfigurationArrays config = new AlgorithmConfigurationArrays(); // un pack
				Section section  = ini.get(sectionName);
				for(String optionKey : section.keySet()){
					String[] values = section.get(optionKey).split(",");
					switch(optionKey){
					case "fileName":
						config.setExportFileName(values[0]);
						break;
					case "BroodModificationStrategy":
						for(String value : values)
							config.addBroodModificationStrategy(value.trim());
						break;
					case "BroodImprovementStrategies":
						for(String value : values)
						{
							ArrayList<String> strategies = new ArrayList<String>();
							for(String strategy : value.split(";"))
							{
								strategies.add(strategy.trim());
							}
							config.addWorkerModificationStrategies(strategies);
						}
						break;
					case "PopSize":
						for(String value : values)
							config.addPopSize(Integer.valueOf(value));
						break;
					case "MaxNrMatings":
						for(String value : values)
							config.addMaxNrMatings(Integer.valueOf(value));
						break;
					case "InitialSpeed":
						for(String value : values)
							config.addInitialSpeed(Integer.valueOf(value));
						break;
					case "initialEnergy":
						for(String value : values)
							config.addInitialSpeed(Integer.valueOf(value));
						break;
					case "speedReductionFactor":
						for(String value : values)
							config.addSpeedReductionFactor(Double.valueOf(value));
						break;
					case "energyReductionAmount":
						for(String value : values)
							config.addEnergyReductionAmlount(Double.valueOf(value));
						break;
					case "probabilityToMateDroneThreshold":
						for(String value : values)
							config.addProbabilityToMateDroneThreshold(Double.valueOf(value));
						break;
					case "similarityCoefficientThreshold":
						for(String value : values)
							config.addSimilarityCoefficientThreshold(Double.valueOf(value));
						break;
					case "hillClimbNeighSize":
						for(String value : values)
							config.addHillClimbingNeighborhoodSize(Integer.valueOf(value));
						break;
					case "T0":
						for(String value : values)
							config.addT0(Double.valueOf(value));
						break;
					case "alpha":
						for(String value : values)
							config.addAlpha(Double.valueOf(value));
						break;
					case "Tmin":
						for(String value : values)
							config.addTmin(Double.valueOf(value));
						break;
					case "tabuSearchIters":
						for(String value : values)
							config.addMaxNrIterations(Integer.valueOf(value));
						break;
					case "tabuSize":
						for(String value : values)
							config.addTabuSize(Integer.valueOf(value));
						break;
					case "tabuNeighSize":
						for(String value : values)
							config.addTabuNeighSize(Integer.valueOf(value));
						break;
					default:
						log.log(Level.WARNING, "Unkown key " + optionKey + " in the ini configuration file.");
					}
				}
				configPacks.add(config);
			}

		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<AlgorithmConfiguration> configs = new ArrayList<AlgorithmConfiguration>();
		for(AlgorithmConfigurationArrays config : configPacks)
			configs.addAll(config.getAllConfigrations());

		return configs;
	}
}
