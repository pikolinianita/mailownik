/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.luccasso.mailownik.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import pl.luccasso.mailownik.DoCompare;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import pl.luccasso.mailownik.config.ConfigF;

/**
 *
 * @author piko
 */
public class MainWindow extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6849483208440196489L;

	static DoCompare doCompare;

	JButton openConfigWindow;
	JButton bankButton;
	JButton pupButton;
	JButton fireButton;
	JButton saveButton;
	JButton leftOButton;
	JButton siblingsButton;
	JButton loadDB;
	JButton removeEntries;
	JButton fixEntries;
	JButton tbdButton;

	JLabel bankLabel;
	JLabel familyPupLabel;
	JLabel dBLabel;
        JLabel outputLabel;
	JLabel summary;
	JLabel header;

	TBDWindow tbdWindow;
	LeftOWindow leftOWindow;
	SibWindow sibWindow;
	ConfigWindow configWindow;

	public MainWindow() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private MainWindow createComponents() {
		header = new JLabel("Wybierz pliki");
		openConfigWindow = new JButton("Otworz okno Konfiguracji");
		dBLabel = new JLabel("Plik z istniejącą bazą Danych to: " + ConfigF.getSavedPath());
		bankLabel = new JLabel("Wybierz plik z banku. Teraz to: " + ConfigF.getBankPath());
		familyPupLabel = new JLabel("wybierz plik z danymi uczniów. Teraz to" + ConfigF.getPupPath());
                outputLabel = new JLabel("Zapis do: " + ConfigF.getOutputDirectory());
		loadDB = new JButton("Otwórz");
		bankButton = new JButton("Otwórz");
		pupButton = new JButton("Otworz");
		fireButton = new JButton("Licz");
		tbdButton = new JButton("Decyzje, decyzje...");
		saveButton = new JButton("Zapisz Dane");
		leftOButton = new JButton("Resztki...");
		siblingsButton = new JButton("Rodzenstwa...");
		summary = new JLabel();
		writeSummary();
		return this;
	}

	private MainWindow addToActionListener() {
		openConfigWindow.addActionListener(action -> {SwingUtilities.invokeLater(() -> {
				ConfigWindow.createAndShowGUI();
				writeSummary();
			});
		});
		loadDB.addActionListener(this);
		bankButton.addActionListener(this);
		pupButton.addActionListener(this);
		fireButton.addActionListener(this);
		tbdButton.addActionListener(this);
		saveButton.addActionListener(this);
		leftOButton.addActionListener(this);
		siblingsButton.addActionListener(this);
		return this;
	}

	private MainWindow addComponentsToFrame() {
		add(header);
		add(openConfigWindow);
		add(dBLabel);
		add(loadDB);
		add(bankLabel);
		add(bankButton);
		add(familyPupLabel);
		add(pupButton);
                add(outputLabel);
		add(fireButton);
		add(leftOButton);
		add(tbdButton);
		add(siblingsButton);
		add(summary);
		add(saveButton);
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("listener for MW");
		if (e.getSource() == loadDB) {
			choosePreviousDataFile();
		}

		if (e.getSource() == bankButton) {
			chooseBankFile();
		}

		if (e.getSource() == pupButton) {
			chooseGoogleDataFile();
		}

		if (e.getSource() == fireButton) {
			doCompare = new DoCompare();
			MainWindow.doCompare.doWork();
		}

		if (e.getSource() == tbdButton) {
			tbdWindow = new TBDWindow(this, "Niepewni", doCompare.getToBeDecidedMap());
		}

		if (e.getSource() == saveButton) {
			MainWindow.doCompare.getDataBase().saveToDisc();
		}

		if (e.getSource() == leftOButton) {
			leftOWindow = new LeftOWindow(this, "Resztkowka", doCompare);
		}

		if (e.getSource() == siblingsButton) {
			sibWindow = new SibWindow(this, "Rodzenstwa", doCompare);
		}

		writeSummary();
	}

	private void chooseGoogleDataFile() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			ConfigF.setPupPath(selectedFile.getAbsolutePath());

		}
	}

	private void chooseBankFile() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			ConfigF.setBankPath(selectedFile.getAbsolutePath());

		}
	}

	private void choosePreviousDataFile() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			ConfigF.setSavedPath(selectedFile.getAbsolutePath());

		}
	}

	void writeSummary() {
		bankLabel.setText("Wybierz plik z banku. Teraz to:" + ConfigF.getBankPath());
		dBLabel.setText("Plik z istniejącą bazą Danych to: " + ConfigF.getSavedPath());
		familyPupLabel.setText("wybierz plik z danymi uczniów. Teraz to" + ConfigF.getPupPath());
                outputLabel.setText("Zapis do: " + ConfigF.getOutputDirectory());
		if (doCompare == null || doCompare.getLeftOvers() == null) {
			summary.setText("<html>Niepoliczone<br><br><br><br><br><br><br><br></html>");
		} else {
			int fitted = doCompare.getAmountOfFamFittedTransactions();
			int leftOvers = doCompare.getLeftOvers().size();
			int toDecide = doCompare.getToBeDecidedMap().keySet().size();
			int siblings = doCompare.getSiblingsBTList().size();
			int rubbish = doCompare.getWrongLinesList().size();
			int sum = fitted + leftOvers + toDecide + siblings + rubbish;

			summary.setText("<html>Rekordów: " + sum + " <br>Dopasowane: " + fitted + " <br>Do decyzji: " + toDecide
					+ "<br>Niedopasowane: " + leftOvers + "<br>Rodzenstwa: " + siblings + " <br>Dno: " + rubbish
					+ "</html>");
		}
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Program Fit");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Create and set up the content pane.
		var mainPanel = new MainWindow();
		mainPanel.createComponents().addComponentsToFrame().addToActionListener();
		JScrollPane newContentPane = new JScrollPane(mainPanel);
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);
		frame.setSize(400, 300);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		

		SwingUtilities.invokeLater(() -> MainWindow.createAndShowGUI());
	}

}
