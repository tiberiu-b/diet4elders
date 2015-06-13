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
			Checkbox checkbox = new Checkbox(strategy, null, true);
			workerModificationStrategiesCheckboxList.add(checkbox);
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
		outputTextArea = new TextArea(17, 150);

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
			controller.runHBMO(ConfigurationsGenerator.getConfigFromIniFile("configs.ini"));

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
