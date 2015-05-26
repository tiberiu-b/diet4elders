package d4elders.main;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.text.DefaultCaret;

import d4elders.algorithm.helper.AlgorithmConfiguration;
import d4elders.algorithm.helper.AvailableProgramConfigurationOptions;
import d4elders.algorithm.helper.ConfigurationsGenerator;

/**
 * Created by cristiprg on 1/18/2015.
 */
public class Main extends Applet implements MouseListener {

	// This will take long
	Diet4Elders d4e = new Diet4Elders();

	boolean mousePress = false;

	Panel leftPanel;

	Button runButton;
	Button runConfiguration1Button;
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

	public static void main(String args[]) {
		// run with the default configuration
		// new Main().d4e.run(new AlgorithmConfiguration());

		new Main().d4e.run(ConfigurationsGenerator.varPopSize(2));
	}

	@Override
	public void init() {
		this.setSize(1100, 640);
		setLayout(new GridBagLayout());
		leftPanel = new Panel();
		add(leftPanel);

		// Initialize the labels
		broodModificationStrategyLabel = new Label(
				"Brood Modification Strategy: ");
		workerModificationStrategiesLabel = new Label(
				"Worker Modification Strategies: ");
		maxNrMatingsLabel = new Label("Max number of matings: ");
		popSizeLabel = new Label("Population size: ");
		initialEnergyLabel = new Label("Initial queen energy: ");
		initialSpeedLabel = new Label("Initial queen speed :");
		speedReductionFactorLabel = new Label("Speed reduction factor: ");
		energyReductionAmountLabel = new Label("Energy reduction amount: ");
		probabilityToMateDroneThresholdLabel = new Label(
				"Probability to mate dron threshold: ");
		similarityCoefficientThresholdLabel = new Label(
				"Solutions similarity threshold: ");

		// Initialize input elements
		AlgorithmConfiguration defaultConfiguration = new AlgorithmConfiguration();

		CheckboxGroup grp = new CheckboxGroup();
		broodModificationStrategiesCheckboxList = new ArrayList<Checkbox>();
		for (String strategy : AvailableProgramConfigurationOptions
				.getAvailableBroodModificationStrategies()) {
			Checkbox checkbox = new Checkbox(strategy, grp, false);
			broodModificationStrategiesCheckboxList.add(checkbox);

			if (strategy
					.equals(AvailableProgramConfigurationOptions.SIMPLE_CROSSOVER))
				checkbox.setState(true);
		}

		workerModificationStrategiesCheckboxList = new ArrayList<Checkbox>();
		for (String strategy : AvailableProgramConfigurationOptions
				.getAvailableWorkerModificationStrategies()) {
			Checkbox checkbox = new Checkbox(strategy, null, true);
			workerModificationStrategiesCheckboxList.add(checkbox);
		}

		maxNrMatingsField = new TextField(String.valueOf(defaultConfiguration
				.getMaxNrMatings()), 10);
		popSizeField = new TextField(String.valueOf(defaultConfiguration
				.getPopSize()), 10);
		initialEnergyField = new TextField(String.valueOf(defaultConfiguration
				.getInitialEnergy()), 10);
		initialSpeedField = new TextField(String.valueOf(defaultConfiguration
				.getInitialSpeed()), 10);
		speedReductionFactorField = new TextField(
				String.valueOf(defaultConfiguration.getSpeedReductionFactor()),
				10);
		energyReductionAmountField = new TextField(
				String.valueOf(defaultConfiguration.getEnergyReductionAmount()),
				10);
		probabilityToMateDroneThresholdField = new TextField(
				String.valueOf(defaultConfiguration
						.getProbabilityToMateDroneThreshold()), 10);
		similarityCoefficientThresholdField = new TextField(
				String.valueOf(defaultConfiguration
						.getSimilarityCoefficientThreshold()), 10);
		// The text area on the right
		outputTextArea = new TextArea(17, 150);

		// The run button
		runButton = new Button("Run");
		runConfiguration1Button = new Button("Run PopSize Config");

		// add the gui elements
		leftPanel.setLayout(new GridLayout(0, 2));

		// brood modification strategies
		leftPanel.add(broodModificationStrategyLabel);
		Panel broodsPanel = new Panel();
		for (Checkbox c : broodModificationStrategiesCheckboxList) {
			broodsPanel.add(c);
		}
		leftPanel.add(broodsPanel);

		// worker modification strategies
		leftPanel.add(workerModificationStrategiesLabel);
		Panel workersPanel = new Panel();
		for (Checkbox c : workerModificationStrategiesCheckboxList) {
			workersPanel.add(c);
		}
		leftPanel.add(workersPanel);

		// max nr matings
		leftPanel.add(maxNrMatingsLabel);
		leftPanel.add(maxNrMatingsField);

		// pop size
		leftPanel.add(popSizeLabel);
		leftPanel.add(popSizeField);

		// initial energy
		leftPanel.add(initialEnergyLabel);
		leftPanel.add(initialEnergyField);

		// initial speed
		leftPanel.add(initialSpeedLabel);
		leftPanel.add(initialSpeedField);

		// speed reduction
		leftPanel.add(speedReductionFactorLabel);
		leftPanel.add(speedReductionFactorField);

		// energy reduction
		leftPanel.add(energyReductionAmountLabel);
		leftPanel.add(energyReductionAmountField);

		// prob to mate drone
		leftPanel.add(probabilityToMateDroneThresholdLabel);
		leftPanel.add(probabilityToMateDroneThresholdField);

		// prob to mate drone
		leftPanel.add(similarityCoefficientThresholdLabel);
		leftPanel.add(similarityCoefficientThresholdField);
		leftPanel.add(runButton);
		leftPanel.add(runConfiguration1Button);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		add(outputTextArea, gbc);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));

		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// new Thread() {
				// @Override
				// public void run() {

				AlgorithmConfiguration configuration = new AlgorithmConfiguration();
				for (Checkbox c : broodModificationStrategiesCheckboxList) {
					if (c.getState() == true) {
						configuration.setBroodModificationStrategy(c.getLabel());
						break;
					}
				}

				configuration.clearWorkerModificationStrategies();
				for (Checkbox c : workerModificationStrategiesCheckboxList) {
					if (c.getState() == true) {
						configuration.addWorkerModificationStrategy(c
								.getLabel());
					}
				}

				configuration.setMaxNrMatings(Integer.valueOf(maxNrMatingsField
						.getText()));
				configuration.setPopSize(Integer.valueOf(popSizeField.getText()));
				configuration.setInitialSpeed(Integer.valueOf(initialSpeedField
						.getText()));
				configuration.setInitialEnergy(Integer
						.valueOf(initialEnergyField.getText()));
				configuration.setSpeedReductionFactor(Double
						.valueOf(speedReductionFactorField.getText()));
				configuration.setEnergyReductionAmount(Double
						.valueOf(energyReductionAmountField.getText()));
				configuration.setProbabilityToMateDroneThreshold(Double
						.valueOf(probabilityToMateDroneThresholdField.getText()));
				configuration.setSimilarityCoefficientThreshold(Double
						.valueOf(similarityCoefficientThresholdField.getText()));
				runButton.setEnabled(false);
				Diet4Elders d4e = new Diet4Elders();
				d4e.run(configuration);
				runButton.setEnabled(true);
				outputTextArea.setText(baos.toString());
				outputTextArea.setCaretPosition(baos.toString().length());
			}
			// }.start();
			// }
		});

		runConfiguration1Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Diet4Elders().run(ConfigurationsGenerator
						.getConfigFromIniFile("configs.ini"));
			}
		});

		// hack to redirect stdout to the text area:
		// 1) first redirect to a stream

		// 2) refresh every X seconds: copy the content of the stream in the
		// text area
		new Thread() {
			@Override
			public void run() {
				while (!isInterrupted()) {
					// outputTextArea.setText(baos.toString());
					// if (mousePress == false)
					// outputTextArea.setCaretPosition(baos.toString().length());
					// outputTextArea.setCaretPosition(outputTextArea.getText().length());
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mousePress = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mousePress = false;

	}

}