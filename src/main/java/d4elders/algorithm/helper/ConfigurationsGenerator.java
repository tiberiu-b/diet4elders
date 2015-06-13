package d4elders.algorithm.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;
import org.openjena.atlas.logging.Log;

public class ConfigurationsGenerator {
	private static final Logger log = Logger.getLogger(ConfigurationsGenerator.class.getName());

	/**
	 * Generates an ArrayList of length nrConfigs instances of {@link AlgorithmConfigurationHBMO} having
	 * the following popSize values: 1, 3, 5, ...
	 * @param nrConfigs
	 * @return
	 */
	public static ArrayList<AlgorithmConfigurationHBMO> varPopSize(int nrConfigs){
		ArrayList<AlgorithmConfigurationHBMO> configs = new ArrayList<AlgorithmConfigurationHBMO>();

		for (int i = 0; i < nrConfigs; ++i){
			AlgorithmConfigurationHBMO config = new AlgorithmConfigurationHBMO();
			config.setPopSize(i * 2 + 1 );
			configs.add(config);
		}

		return configs;
	}

	/**
	 * Returns all the possible combinations of {@link AlgorithmConfigurationHBMO} from the ini file.
	 * @param iniFilePath
	 * @return an ArrayList<AlgorithmConfiguration> that contains all the possible combinations.
	 */
	public static ArrayList<AlgorithmConfigurationHBMO> getConfigFromIniFile(String iniFilePath){
		AlgorithmConfigurationArrays config = new AlgorithmConfigurationArrays();
		// ini examples here: http://ini4j.sourceforge.net/tutorial/OneMinuteTutorial.java.html
		try {
			Wini ini = new Wini(new File(iniFilePath));
			Section section = ini.get("config");
			for(String optionKey : section.keySet()){
				String[] values = section.get(optionKey).split(",");
				switch(optionKey){
				case "BroodModificationStrategy":
					for(String value : values)
						config.addBroodModificationStrategy(value);
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
				default:
					log.log(Level.WARNING, "Unkown key " + optionKey + " in the ini configuration file.");
				}
			}

		} catch (InvalidFileFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		return config.getAllConfigrations();
	}
}
