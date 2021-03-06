package pl.luccasso.mailownik.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import pl.luccasso.mailownik.config.ConfigF;

public class ConfigWindow extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton exitButton;
	
	JButton outputDir;

	JButton loadConfigFromFileButton;

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
        
        JLabel outputLabel;

	JPanel configButtonsPanel;

	JPanel bottomPanel;

	static ConfigWindow instance;

	private JButton saveConfigButton;

	public static ConfigWindow getInstance() {
		return instance;
	}

	private ConfigWindow setInstance(ConfigWindow inst) {
		instance = inst;
		return this;
	}

	public ConfigWindow() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	static void createAndShowGUI() {
		JFrame frame = new JFrame("Program Fit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create and set up the content pane.
		var mainPanel = new ConfigWindow();
		mainPanel.setInstance(mainPanel).createComponents().updateLabelsFromConfig().addComponentsToFrame()
				.addActionListener();
		JScrollPane newContentPane = new JScrollPane(mainPanel);
		newContentPane.setOpaque(true); // content panes must be opaque

		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setSize(800, 600);
		frame.setVisible(true);
	}

	private void addActionListener() {
		previousDataButton.addActionListener(new ConfigWindowListeners.PreviousListener());
		bankButton.addActionListener(new ConfigWindowListeners.BankListener());
		pupilsButton.addActionListener(new ConfigWindowListeners.GoogleListener());

		payPerClassButton.addActionListener(new ConfigWindowListeners.PayPerListener());
		classPerSchoolButton.addActionListener(new ConfigWindowListeners.ClassPerListener());
		outputDir.addActionListener(new ConfigWindowListeners.SavePlace());
		
		configPathButton.addActionListener(new ConfigWindowListeners.ConfigListener());
		saveConfigButton.addActionListener(new ConfigWindowListeners.SaveListener());
		exitButton.addActionListener(action -> SwingUtilities.windowForComponent(this).dispose());
		loadConfigFromFileButton.addActionListener(new ConfigWindowListeners.loadConfigFromFileListener());
	}

	public ConfigWindow updateLabelsFromConfig() {

		bankLabel.setText("plik z banku to: " + ConfigF.getBankPath());
		pupilsLabel.setText("plik z googla to: " + ConfigF.getPupPath());
		previousDataLabel.setText("uprzednio policzone to: " + ConfigF.getSavedPath());

		payPerClassLabel.setText("PLN za n klas to: " + ConfigF.getPayPerClass());
		classPerSchoolLabel.setText("ilosc zajec w szkolach to: " + ConfigF.getClassPerSchool());
		configPathLabel.setText("konfiguracja to: " + ConfigF.getConfigPath());
                
                outputLabel.setText("Zapis do: " + ConfigF.getOutputDirectory());

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

                add(outputLabel);
                
		add(bottomPanel);
		// add(exitButton);
		// add(saveButton);
		return this;

	}

	private ConfigWindow createComponents() {

		bankLabel = new JLabel("", SwingConstants.LEFT);
		pupilsLabel = new JLabel("", SwingConstants.LEFT);
		previousDataLabel = new JLabel("", SwingConstants.LEFT);

		payPerClassLabel = new JLabel("", SwingConstants.LEFT);
		classPerSchoolLabel = new JLabel("", SwingConstants.LEFT);
		configPathLabel = new JLabel("", SwingConstants.LEFT);

                outputLabel = new JLabel("", SwingConstants.CENTER);
                
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
		outputDir = new JButton("Zapis do...");

		configButtonsPanel.add(payPerClassButton);
		configButtonsPanel.add(classPerSchoolButton);
		configButtonsPanel.add(outputDir);
	}

	private void createBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

		exitButton = new JButton("wyjscie");
		loadConfigFromFileButton = new JButton("wczytaj z wybranego pliku konfiguracji");
		configPathButton = new JButton("wybierz konfig");
		saveConfigButton = new JButton("zapisz konfig");

		bottomPanel.add(exitButton);
		bottomPanel.add(loadConfigFromFileButton);
		bottomPanel.add(configPathButton);
		bottomPanel.add(saveConfigButton);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> ConfigWindow.createAndShowGUI());

	}

}
