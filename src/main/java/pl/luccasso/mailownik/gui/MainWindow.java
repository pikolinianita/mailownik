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
        
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;       

/**
 *
 * @author piko
 */
public class MainWindow extends JPanel implements ActionListener{
    static DoCompare mainData;
    JButton bankButton, pupButton, fireButton, saveButton;
    JLabel bankLabel, pupilLabel;
    String bankPath, pupPath;
    TBDWindow tbdWindow;
    private final JButton tbdButton;
    
    public MainWindow() {
        super();
         
        pupPath = "e:/pupils.txt";
        bankPath = "e:/listatestowa.csv";
       
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
         var header = new JLabel("Wybierz pliki");
         bankLabel = new JLabel("Wybierz plik z banku. Teraz to: " + bankPath);
         pupilLabel = new JLabel("wybierz plik z danymi uczniów. Teraz to" + pupPath);
         bankButton = new JButton("Otwórz");
         bankButton.addActionListener(this);
         pupButton = new JButton("Otworz");
         pupButton.addActionListener(this);
         fireButton = new JButton("Licz");
         fireButton.addActionListener(this);
         tbdButton = new JButton("Decyzje, decyzje...");
         tbdButton.addActionListener(this);
         saveButton = new JButton("Zapisz Dane");
         saveButton.addActionListener(this);
         
         add(header);
         add(bankLabel);
         add(bankButton);
         add(pupilLabel);
         add(pupButton);
         add(fireButton);
         add(tbdButton);
    }
    
    
    
    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Program Fit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JScrollPane newContentPane = new JScrollPane(new MainWindow());
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

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == bankButton) {
          JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
                        bankPath = selectedFile.getAbsolutePath();
                        MainWindow.mainData.SetBankPath(bankPath);
			System.out.println(selectedFile.getAbsolutePath());
                        bankLabel.setText("Wybierz plik z banku. Teraz to:"  + bankPath);
		}

      }
      if (e.getSource() == pupButton){
          JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
                        pupPath = selectedFile.getAbsolutePath();
                        MainWindow.mainData.SetPupPath(pupPath);
			System.out.println(selectedFile.getAbsolutePath());
                        pupilLabel.setText("wybierz plik z danymi uczniów. Teraz to" + pupPath);
		}

      }
      if (e.getSource() == fireButton){
          MainWindow.mainData.doWork();
      }
      if (e.getSource() == tbdButton){
          tbdWindow = new TBDWindow("To Be Decided", mainData.getToBeDecidedMap());
      }
      if (e.getSource() == saveButton){
          MainWindow.mainData.save();
      }
      
    }
    
    
}
