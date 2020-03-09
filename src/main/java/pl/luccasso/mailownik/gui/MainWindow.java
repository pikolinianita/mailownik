/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.gui;

import java.awt.HeadlessException;
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

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author piko
 */
public class MainWindow extends JPanel implements ActionListener {

    static DoCompare mainData;

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
    JLabel pupilLabel;
    JLabel dBLabel;
    JLabel summary;
    JLabel header;

    String bankPath;
    String pupPath;
    String dBPath;

    TBDWindow tbdWindow;
    LeftOWindow leftOWindow;
    SibWindow sibWindow;

    public MainWindow() {
        super();

        pupPath = "e:/pupils.txt";
        bankPath = "e:/listatestowa.csv";
        dBPath = null;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private MainWindow createComponents() {
        header = new JLabel("Wybierz pliki");
        dBLabel = new JLabel("Plik z istniejącą bazą Danych to: " + dBPath);
        bankLabel = new JLabel("Wybierz plik z banku. Teraz to: " + bankPath);
        pupilLabel = new JLabel("wybierz plik z danymi uczniów. Teraz to" + pupPath);
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
        add(dBLabel);
        add(loadDB);
        add(bankLabel);
        add(bankButton);
        add(pupilLabel);
        add(pupButton);
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
            MainWindow.mainData.doWork();
        }

        if (e.getSource() == tbdButton) {
            tbdWindow = new TBDWindow(this, "Niepewni", mainData.getToBeDecidedMap());
        }

        if (e.getSource() == saveButton) {
            MainWindow.mainData.save();
        }

        if (e.getSource() == leftOButton) {
            leftOWindow = new LeftOWindow(this, "Resztkowka", mainData);
        }

        if (e.getSource() == siblingsButton) {
            sibWindow = new SibWindow(this, "Rodzenstwa", mainData);
        }

        writeSummary();
    }

    private void chooseGoogleDataFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            pupPath = selectedFile.getAbsolutePath();
            MainWindow.mainData.setPupPath(pupPath);
            System.out.println(selectedFile.getAbsolutePath());
            pupilLabel.setText("wybierz plik z danymi uczniów. Teraz to" + pupPath);
        }
    }

    private void chooseBankFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            bankPath = selectedFile.getAbsolutePath();
            MainWindow.mainData.setBankPath(bankPath);
            System.out.println(selectedFile.getAbsolutePath());
            bankLabel.setText("Wybierz plik z banku. Teraz to:" + bankPath);
        }
    }

    private void choosePreviousDataFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            dBPath = selectedFile.getAbsolutePath();
            MainWindow.mainData.setDBPath(dBPath);
            System.out.println(selectedFile.getAbsolutePath());
            dBLabel.setText("Plik z istniejącą bazą Danych to: " + dBPath);
        }
    }

    void writeSummary() {

        if (mainData.getLeftOvers() == null) {
            summary.setText("<html>Niepoliczone<br><br><br><br><br><br><br><br></html>");
        } else {
            int fitted = mainData.getAmountOfFittedTransactions();
            int leftOvers = mainData.getLeftOvers().size();
            int toDecide = mainData.getToBeDecidedMap().keySet().size();
            int siblings = mainData.getSiblingsBTList().size();
            int rubbish = mainData.getWrongLinesList().size();
            int sum = fitted + leftOvers + toDecide + siblings + rubbish;

            summary.setText("<html>Rekordów: " + sum + " <br>Dopasowane: " + fitted + " <br>Do decyzji: " + toDecide + "<br>Niedopasowane: " + leftOvers
                    + "<br>Rodzenstwa: " + siblings + " <br>Dno: " + rubbish + "</html>");
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Program Fit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        var mainPanel = new MainWindow();
        mainPanel.createComponents()
                .addComponentsToFrame()
                .addToActionListener();
        JScrollPane newContentPane = new JScrollPane(mainPanel);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.setSize(400, 300);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        mainData = new DoCompare();

        javax.swing.SwingUtilities.invokeLater(() -> MainWindow.createAndShowGUI());
    }

}
