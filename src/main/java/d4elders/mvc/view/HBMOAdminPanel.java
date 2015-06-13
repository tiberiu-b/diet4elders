package d4elders.mvc.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JPanel;

import d4elders.algorithm.helper.AlgorithmConfigurationHBMO;
import d4elders.algorithm.helper.AvailableProgramConfigurationOptions;
import d4elders.algorithm.helper.ConfigurationsGenerator;
import d4elders.mvc.controller.Controller;

public class HBMOAdminPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	Controller controller = Controller.getInstance();

	AlgorithmConfigurationHBMO defaultConfiguration;

	final ByteArrayOutputStream baos;
	Button runButton;
	Button runConfigurationButton;
	TextArea outputTextArea;
	Label broodModificationStrategyLabel;
	Label workerModificationStrategiesLabel;
	Label maxNrMatingsLabel;
	Label popSizeLabel;
	Label initialSpeedLabel;
	Label initialEnergyLabel;
	Label speedReductionFactorLabel;
	Label energyReductionAmountLabel;
	Label probabilityToMateDroneThresholdLabel;
	Label similarityCoefficientThresholdLabel;

	ArrayList<Checkbox> broodModificationStrategiesCheckboxList;
	ArrayList<Checkbox> workerModificationStrategiesCheckboxList;
	TextField maxNrMatingsField;
	TextField popSizeField;
	TextField initialSpeedField;
	TextField initialEnergyField;
	TextField speedReductionFactorField;
	TextField energyReductionAmountField;
	TextField probabilityToMateDroneThresholdField;
	TextField similarityCoefficientThresholdField;

	// Hill Climbing
	Label hillClimbingNeighborhoodSizeLabel;

	// Simulated Annealing
	Label T0Label;
	Label alphaLabel;
	Label TminLabel;

	// Tabu Searh
	Label maxNrIterationsLabel;
	Label tabuSizeLabel;
	Label tabuNeighborhoodSizeLabel;

	// Hill Climbing
	TextField hillClimbingNeighborhoodSizeTextField;

	// Simulated Annealing
	TextField T0TextField;
	TextField alphaTextField;
	TextField TminTextField;

	// Tabu Searh
	TextField maxNrIterationsTextField;
	TextField tabuSizeTextField;
	TextField tabuNeighborhoodSizeTextField;

	public HBMOAdminPanel() {

		defaultConfiguration = Controller.getHbmoConf();

		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 2));
		// Initialize the labels
		broodModificationStrategyLabel = new Label("Brood Modification Strategy: ");
		workerModificationStrategiesLabel = new Label("Worker Modification Strategies: ");
		maxNrMatingsLabel = new Label("Max number of matings: ");
		popSizeLabel = new Label("Population size: ");
		initialEnergyLabel = new Label("Initial queen energy: ");
		initialSpeedLabel = new Label("Initial queen speed :");
		speedReductionFactorLabel = new Label("Speed reduction factor: ");
		energyReductionAmountLabel = new Label("Energy reduction amount: ");
		probabilityToMateDroneThresholdLabel = new Label("Probability to mate dron threshold: ");
		similarityCoefficientThresholdLabel = new Label("Solutions similarity threshold: ");

		// Hill Climbing
		Label hillClimbingNeighborhoodSizeLabel = new Label("Hill-Climb neigh size: ");

		// Simulated Annealing
		Label T0Label = new Label("T0: ");
		Label alphaLabel = new Label("alpha: ");
		Label TminLabel = new Label("Tmin: ");

		// Tabu Searh
		Label maxNrIterationsLabel = new Label("TabuSearch iterations: ");
		Label tabuSizeLabel = new Label("Tabu Size");
		Label tabuNeighborhoodSizeLabel = new Label("Tabu neigh size: ");

		// Hill Climbing
		hillClimbingNeighborhoodSizeTextField = new TextField(String.valueOf(defaultConfiguration
				.getHillClimbingNeighborhoodSize()), 10);

		// Simulated Annealing
		T0TextField = new TextField(String.valueOf(defaultConfiguration.getT0()), 10);
		alphaTextField = new TextField(String.valueOf(defaultConfiguration.getAlpha()), 10);
		TminTextField = new TextField(String.valueOf(defaultConfiguration.getTmin()), 10);

		// Tabu Searh
		maxNrIterationsTextField = new TextField(String.valueOf(defaultConfiguration.getMaxNrIterations()), 10);
		tabuSizeTextField = new TextField(String.valueOf(defaultConfiguration.getTabuSize()), 10);
		tabuNeighborhoodSizeTextField = new TextField(String.valueOf(defaultConfiguration.getTabuNeighborhoodSize()),
				10);

		// hide heuristics elements
		hillClimbingNeighborhoodSizeLabel.setVisible(false);
		hillClimbingNeighborhoodSizeTextField.setVisible(false);

		T0Label.setVisible(false);
		T0TextField.setVisible(false);
		alphaLabel.setVisible(false);
		alphaTextField.setVisible(false);
		TminLabel.setVisible(false);
		TminTextField.setVisible(false);

		maxNrIterationsLabel.setVisible(false);
		maxNrIterationsTextField.setVisible(false);
		tabuSizeLabel.setVisible(false);
		tabuSizeTextField.setVisible(false);
		tabuNeighborhoodSizeLabel.setVisible(false);
		tabuNeighborhoodSizeTextField.setVisible(false);

		CheckboxGroup grp = new CheckboxGroup();
		broodModificationStrategiesCheckboxList = new ArrayList<Checkbox>();
		for (String strategy : AvailableProgramConfigurationOptions.getAvailableBroodModificationStrategies()) {
			Checkbox checkbox = new Checkbox(strategy, grp, false);
			broodModificationStrategiesCheckboxList.add(checkbox);

			if (strategy.equals(AvailableProgramConfigurationOptions.SIMPLE_CROSSOVER))
				checkbox.setState(true);
		}

		workerModificationStrategiesCheckboxList = new ArrayList<Checkbox>();
		for (String strategy : AvailableProgramConfigurationOptions.getAvailableWorkerModificationStrategies()) {
			Checkbox checkbox = new Checkbox(strategy, null, false);
			workerModificationStrategiesCheckboxList.add(checkbox);

			/*
			 * // Hill Climbing leftPanel.add(hillClimbingNeighborhoodSizeLabel);
			 * leftPanel.add(hillClimbingNeighborhoodSizeTextField);
			 * 
			 * // Simulated Annealing leftPanel.add(T0Label); leftPanel.add(T0TextField);
			 * leftPanel.add(alphaLabel); leftPanel.add(alphaTextField); leftPanel.add(TminLabel);
			 * leftPanel.add(TminTextField);
			 * 
			 * // Tabu Searh leftPanel.add(maxNrIterationsLabel);
			 * leftPanel.add(maxNrIterationsTextField); leftPanel.add(tabuSizeLabel);
			 * leftPanel.add(tabuSizeTextField); leftPanel.add(tabuNeighborhoodSizeLabel);
			 * leftPanel.add(tabuNeighborhoodSizeTextField);
			 */

			checkbox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					boolean currentState = e.getStateChange() == 1 ? true : false;

					switch (strategy) {
					case AvailableProgramConfigurationOptions.HILL_CLIMBING:
						hillClimbingNeighborhoodSizeLabel.setVisible(currentState);
						hillClimbingNeighborhoodSizeTextField.setVisible(currentState);
						break;
					case AvailableProgramConfigurationOptions.SIMULATED_ANNEALING:
						T0Label.setVisible(currentState);
						T0TextField.setVisible(currentState);
						alphaLabel.setVisible(currentState);
						alphaTextField.setVisible(currentState);
						TminLabel.setVisible(currentState);
						TminTextField.setVisible(currentState);
						break;
					case AvailableProgramConfigurationOptions.SIMPLE_TABU_SEARCH:
						maxNrIterationsLabel.setVisible(currentState);
						maxNrIterationsTextField.setVisible(currentState);
						tabuSizeLabel.setVisible(currentState);
						tabuSizeTextField.setVisible(currentState);
						tabuNeighborhoodSizeLabel.setVisible(currentState);
						tabuNeighborhoodSizeTextField.setVisible(currentState);
						break;
					}
					mainPanel.revalidate();
				}
			});

		}

		maxNrMatingsField = new TextField(String.valueOf(defaultConfiguration.getMaxNrMatings()), 10);
		popSizeField = new TextField(String.valueOf(defaultConfiguration.getPopSize()), 10);
		initialEnergyField = new TextField(String.valueOf(defaultConfiguration.getInitialEnergy()), 10);
		initialSpeedField = new TextField(String.valueOf(defaultConfiguration.getInitialSpeed()), 10);
		speedReductionFactorField = new TextField(String.valueOf(defaultConfiguration.getSpeedReductionFactor()), 10);
		energyReductionAmountField = new TextField(String.valueOf(defaultConfiguration.getEnergyReductionAmount()), 10);
		probabilityToMateDroneThresholdField = new TextField(String.valueOf(defaultConfiguration
				.getProbabilityToMateDroneThreshold()), 10);
		similarityCoefficientThresholdField = new TextField(String.valueOf(defaultConfiguration
				.getSimilarityCoefficientThreshold()), 10);
		// The text area on the right
		outputTextArea = new TextArea(17, 130);

		// The run button
		runButton = new Button("Run");
		runConfigurationButton = new Button("Run PopSize Config");

		// brood modification strategies
		mainPanel.add(broodModificationStrategyLabel);
		Panel broodsPanel = new Panel();
		for (Checkbox c : broodModificationStrategiesCheckboxList) {
			broodsPanel.add(c);
		}
		mainPanel.add(broodsPanel);

		// worker modification strategies
		mainPanel.add(workerModificationStrategiesLabel);
		Panel workersPanel = new Panel();
		for (Checkbox c : workerModificationStrategiesCheckboxList) {
			workersPanel.add(c);
		}
		mainPanel.add(workersPanel);

		// max nr matings
		mainPanel.add(maxNrMatingsLabel);
		mainPanel.add(maxNrMatingsField);

		// pop size
		mainPanel.add(popSizeLabel);
		mainPanel.add(popSizeField);

		// initial energy
		mainPanel.add(initialEnergyLabel);
		mainPanel.add(initialEnergyField);

		// initial speed
		mainPanel.add(initialSpeedLabel);
		mainPanel.add(initialSpeedField);

		// speed reduction
		mainPanel.add(speedReductionFactorLabel);
		mainPanel.add(speedReductionFactorField);

		// energy reduction
		mainPanel.add(energyReductionAmountLabel);
		mainPanel.add(energyReductionAmountField);

		// prob to mate drone
		mainPanel.add(probabilityToMateDroneThresholdLabel);
		mainPanel.add(probabilityToMateDroneThresholdField);

		// prob to mate drone
		mainPanel.add(similarityCoefficientThresholdLabel);
		mainPanel.add(similarityCoefficientThresholdField);

		// Hill Climbing
		mainPanel.add(hillClimbingNeighborhoodSizeLabel);
		mainPanel.add(hillClimbingNeighborhoodSizeTextField);

		// Simulated Annealing
		mainPanel.add(T0Label);
		mainPanel.add(T0TextField);
		mainPanel.add(alphaLabel);
		mainPanel.add(alphaTextField);
		mainPanel.add(TminLabel);
		mainPanel.add(TminTextField);

		// Tabu Searh
		mainPanel.add(maxNrIterationsLabel);
		mainPanel.add(maxNrIterationsTextField);
		mainPanel.add(tabuSizeLabel);
		mainPanel.add(tabuSizeTextField);
		mainPanel.add(tabuNeighborhoodSizeLabel);
		mainPanel.add(tabuNeighborhoodSizeTextField);

		mainPanel.add(runButton);
		mainPanel.add(runConfigurationButton);

		JPanel outputPanel = new JPanel();
		outputPanel.add(outputTextArea);
		baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));

		this.add(mainPanel, BorderLayout.CENTER);
		this.add(outputPanel, BorderLayout.SOUTH);

		runButton.addActionListener(this);

		runConfigurationButton.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent obj) {
		Button source = (Button) obj.getSource();

		// run results generator
		if (source.getLabel().equals(runConfigurationButton.getLabel()))
			controller.runHBMO(ConfigurationsGenerator.getHBMOConfigFromIniFile("configs.ini"));

		// run HBMO
		if (source.getLabel().equals(runButton.getLabel())) {

			for (Checkbox c : broodModificationStrategiesCheckboxList) {
				if (c.getState() == true) {
					defaultConfiguration.setBroodModificationStrategy(c.getLabel());
					break;
				}
			}

			defaultConfiguration.clearWorkerModificationStrategies();
			for (Checkbox c : workerModificationStrategiesCheckboxList) {
				if (c.getState() == true) {
					defaultConfiguration.addWorkerModificationStrategy(c.getLabel());

					switch (c.getLabel()) {
					case AvailableProgramConfigurationOptions.HILL_CLIMBING:
						defaultConfiguration.setHillClimbingNeighborhoodSize(Integer
								.valueOf(hillClimbingNeighborhoodSizeTextField.getText()));
						break;
					case AvailableProgramConfigurationOptions.SIMULATED_ANNEALING:
						defaultConfiguration.setT0(Double.valueOf(T0TextField.getText()));
						defaultConfiguration.setAlpha(Double.valueOf(alphaTextField.getText()));
						defaultConfiguration.setTmin(Double.valueOf(TminTextField.getText()));
						break;
					case AvailableProgramConfigurationOptions.SIMPLE_TABU_SEARCH:
						defaultConfiguration.setMaxNrIterations(Integer.valueOf(maxNrIterationsTextField.getText()));
						defaultConfiguration.setTabuSize(Integer.valueOf(tabuSizeTextField.getText()));
						defaultConfiguration.setTabuNeighborhoodSize(Integer.valueOf(tabuNeighborhoodSizeTextField
								.getText()));
						break;
					}
				}
			}

			defaultConfiguration.setMaxNrMatings(Integer.valueOf(maxNrMatingsField.getText()));
			defaultConfiguration.setPopSize(Integer.valueOf(popSizeField.getText()));
			defaultConfiguration.setInitialSpeed(Integer.valueOf(initialSpeedField.getText()));
			defaultConfiguration.setInitialEnergy(Integer.valueOf(initialEnergyField.getText()));
			defaultConfiguration.setSpeedReductionFactor(Double.valueOf(speedReductionFactorField.getText()));
			defaultConfiguration.setEnergyReductionAmount(Double.valueOf(energyReductionAmountField.getText()));
			defaultConfiguration.setProbabilityToMateDroneThreshold(Double.valueOf(probabilityToMateDroneThresholdField
					.getText()));
			defaultConfiguration.setSimilarityCoefficientThreshold(Double.valueOf(similarityCoefficientThresholdField
					.getText()));

			runButton.setEnabled(false);
			controller.runHBMO(defaultConfiguration);
			runButton.setEnabled(true);

			outputTextArea.setText(baos.toString());
			outputTextArea.setCaretPosition(baos.toString().length());

		}

	}
}
