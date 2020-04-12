package pl.luccasso.mailownik.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import pl.luccasso.mailownik.config.ConfigF;

public class ConfigWindow extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton exitButton;

	JButton saveButton;
	
	JButton pupilsButton;

	JLabel pupilsLabel;

	JButton bankButton;

	JLabel bankLabel;

	JButton previousDataButton;

	JLabel previousDataLabel;

	JButton payPerClassButton;

	JLabel payPerClassLabel;

	JButton classPerSchoolButton;

	JLabel classPerSchoolLabel;

	JButton configPathButton;

	JLabel configPathLabel;

	JPanel inputButtonsPanel;

	JPanel configButtonsPanel;

	JPanel bottomPanel;

	private JButton saveConfigButton;

	public ConfigWindow() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Program Fit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create and set up the content pane.
		var mainPanel = new ConfigWindow();
		mainPanel.createComponents()
			.updateLabelsFromConfig()
			.addComponentsToFrame();
		// .addToActionListener();
		JScrollPane newContentPane = new JScrollPane(mainPanel);
		newContentPane.setOpaque(true); // content panes must be opaque

		frame.setContentPane(newContentPane);
		frame.setSize(400, 300);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private ConfigWindow updateLabelsFromConfig() {
		
		bankLabel.setText("plik z banku to: " + ConfigF.getBankPath());		
		pupilsLabel.setText("plik z googla to: " + ConfigF.getPupPath());
		previousDataLabel.setText("uprzednio policzone to: " + ConfigF.getSavedPath());
		
		payPerClassLabel.setText("PLN za n klas to: " + ConfigF.getPayPerClass());
		classPerSchoolLabel.setText("ilosc zajec w szkolach to: " + ConfigF.getClassPerSchool());
		configPathLabel.setText("konfiguracja to: " + ConfigF.getConfigPath());
		
		
		return this;
	}

	private ConfigWindow addComponentsToFrame() {

		add(bankLabel);
		add(pupilsLabel);
		add(previousDataLabel);
		add(inputButtonsPanel);

		add(payPerClassLabel);
		add(classPerSchoolLabel);
		add(configPathLabel);
		add(configButtonsPanel);

		add(bottomPanel);
		// add(exitButton);
		// add(saveButton);
		return this;

	}

	private ConfigWindow createComponents() {

		bankLabel = new JLabel("",SwingConstants.LEFT);
		pupilsLabel = new JLabel("",SwingConstants.LEFT);
		previousDataLabel = new JLabel("",SwingConstants.LEFT);
		

		payPerClassLabel = new JLabel("",SwingConstants.LEFT);
		classPerSchoolLabel = new JLabel("",SwingConstants.LEFT);
		configPathLabel = new JLabel("",SwingConstants.LEFT);

		createInputPanel();
		createConfigPanel();
		createBottomPanel();

		return this;
	}

	private void createInputPanel() {
		inputButtonsPanel = new JPanel();
		inputButtonsPanel.setLayout(new BoxLayout(inputButtonsPanel, BoxLayout.X_AXIS));

		previousDataButton = new JButton("Policzone wcześniej");
		bankButton = new JButton("Dane z Banku");
		pupilsButton = new JButton("Plik z Googla");

		inputButtonsPanel.add(previousDataButton);
		inputButtonsPanel.add(bankButton);
		inputButtonsPanel.add(pupilsButton);
	}

	private void createConfigPanel() {
		configButtonsPanel = new JPanel();
		configButtonsPanel.setLayout(new BoxLayout(configButtonsPanel, BoxLayout.X_AXIS));
		
		payPerClassButton = new JButton("PLN za nZajeć");
		classPerSchoolButton = new JButton("Ile Zajec W szkolach");
		configPathButton = new JButton("wczytaj konfig");
		saveConfigButton = new JButton("zapisz konfig");

		configButtonsPanel.add(payPerClassButton);
		configButtonsPanel.add(classPerSchoolButton);
		configButtonsPanel.add(configPathButton);
		configButtonsPanel.add(saveConfigButton);
	}

	private void createBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		
		exitButton = new JButton("exit");
		saveButton = new JButton("save");
		
		bottomPanel.add(exitButton);
		bottomPanel.add(saveButton);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> ConfigWindow.createAndShowGUI());

	}

}
