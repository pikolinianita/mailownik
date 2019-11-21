/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.DoCompare;
import pl.luccasso.mailownik.Pupil;


/*class MyComboBoxRenderer extends JLabel implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object obj, int Val, boolean isSelected, boolean cellHasFocus) {
        
         if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
         }
           String text = ((Pupil)obj).getShortUniqueString();
            this.setText(text);
        
        return this;
    }
    
}*/

/**
 *
 * @author piko
 */
public class LOPanel_1 extends JPanel implements ActionListener{
    static DoCompare mainData;
    Pupil pu;
    List <BankTransaction> btList;
    List<Pupil> pupList;
    JLabel transaction;
    Random generator;
    String[] pupilStr;
    JComboBox patternList;
    JLabel output;
    JComboBox schoolSelect;
    
    public LOPanel_1() {
        super();
        //mainData.doWork();
        btList = mainData.getLeftOvers();
        pupList = mainData.getPupilList();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        int i=0;
        pupilStr = new String[pupList.size()];
        
        for(var p:pupList){
            pupilStr[i] = p.getShortUniqueString();
            i++;
        }
        Arrays.sort(pupilStr);
        generator = new Random();
        int number = generator.nextInt(10);
        
        transaction = new JLabel(btList.get(number).niceString);
        transaction.setToolTipText(btList.get(number).saveTransaction());
        //patternList = new JComboBox(pupilStr);
        patternList = new JComboBox(pupList.toArray(new Pupil[pupList.size()]));
        patternList.setEditable(false);
        patternList.addActionListener(this);
        patternList.setMaximumSize(new Dimension(400, 100));
        //patternList.setRenderer(new MyComboBoxRenderer());
        AutoCompleteDecorator.decorate(patternList);
        var schoolArray = mainData.getPupBySchoolMap().keySet().toArray();
        Arrays.sort(schoolArray);
        schoolSelect = new JComboBox(schoolArray);
        schoolSelect.addActionListener(this);
        output = new JLabel("----");
        add(transaction);
        add(patternList);
        add(schoolSelect);
        add(output);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Program Fit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JScrollPane newContentPane = new JScrollPane(new LOPanel_1());
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.setSize(400, 300);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        mainData = new DoCompare();
        mainData.doWork();
        
        javax.swing.SwingUtilities.invokeLater(() -> LOPanel_1.createAndShowGUI());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == patternList){
            JComboBox cb = (JComboBox)e.getSource();
            Pupil petName = (Pupil) cb.getSelectedItem();
            if (petName != null){
                output.setText(petName.getShortUniqueString());
                pu = petName;
            } else {
                output.setText("------");
                pu=null;
            }
            
        }
        if (e.getSource() == schoolSelect){
            JComboBox cb = (JComboBox)e.getSource();
            int school = (Integer) cb.getSelectedItem();
           /* patternList.removeAllItems();
            for(var p: mainData.getPupBySchoolMap().get(school)){
                patternList.addItem(p);
            }
            */
           DefaultComboBoxModel model = (DefaultComboBoxModel) patternList.getModel();
           model.removeAllElements();
           model.addAll(mainData.getPupBySchoolMap().get(school));
        }
    }
    
}
