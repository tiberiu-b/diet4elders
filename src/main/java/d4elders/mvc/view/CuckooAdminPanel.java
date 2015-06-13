package d4elders.mvc.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;

import d4elders.algorithm.helper.*;
import d4elders.mvc.controller.Controller;

public class CuckooAdminPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	Controller controller = Controller.getInstance();

	AlgorithmConfigurationCuckoo defaultConfiguration;

	final ByteArrayOutputStream baos;
	Button runButton;
	Button runConfigurationButton;
	TextArea outputTextArea;
	Label nestSizeLabel;
	Label maxIterationsLabel;
	Label paLabel;

	TextField nestSizeField;
	TextField maxIterationsField;
	TextField paField;

	public CuckooAdminPanel() {
		defaultConfiguration = Controller.getCuckooConf();

		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 2));

		nestSizeLabel = new Label("Nest size: ");
		maxIterationsLabel = new Label("Max iterations: ");
		paLabel = new Label("PA coefficient: ");

		nestSizeField = new TextField(String.valueOf(defaultConfiguration.getNestSize()), 10);
		maxIterationsField = new TextField(String.valueOf(defaultConfiguration.getMaxIterations()), 10);
		paField = new TextField(String.valueOf(defaultConfiguration.getPa()), 10);

		outputTextArea = new TextArea(17, 150);

		// The run button
		runButton = new Button("Run");
		runConfigurationButton = new Button("Run PopSize Config");

		mainPanel.add(nestSizeLabel);
		mainPanel.add(nestSizeField);

		mainPanel.add(maxIterationsLabel);
		mainPanel.add(maxIterationsField);

		mainPanel.add(paLabel);
		mainPanel.add(paField);

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
		if (source.getLabel().equals(runConfigurationButton.getLabel())) {
			// controller.runHBMO(ConfigurationsGenerator.getConfigFromIniFile("configs.ini"));
		}
		// run HBMO
		if (source.getLabel().equals(runButton.getLabel())) {

			defaultConfiguration.setNestSize(Integer.valueOf(nestSizeField.getText()));
			defaultConfiguration.setMaxIterations(Integer.valueOf(maxIterationsField.getText()));
			defaultConfiguration.setPa(Double.valueOf(paField.getText()));
			runButton.setEnabled(false);

			controller.runCuckoo(defaultConfiguration);
			runButton.setEnabled(true);
			outputTextArea.setText(baos.toString());
			outputTextArea.setCaretPosition(baos.toString().length());
		}
	}

}
