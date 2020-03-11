/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.gui;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.model.NewFamily;


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
    
}
*/
/**
 *
 * @author piko
 */
public class LOPanel extends JPanel implements ActionListener{
    //static DoCompare mainData;
    NewFamily pu;
    BankTransaction bt;
    List<NewFamily> famList;
    Map<Integer, List<NewFamily>> famBySchoolMap;
    JLabel transaction;
    Random generator;
    String[] pupilStr;
    JComboBox patternList;
    JLabel output;
    JComboBox schoolSelect;
    boolean isSet;
    
    public LOPanel(BankTransaction bt, List<NewFamily> pl, Map<Integer, List<NewFamily>> pBSM) {
        super();
        //mainData.doWork();
        this.bt = bt;
        famList = pl;
        famBySchoolMap = pBSM;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        /*int i=0;
        pupilStr = new String[famList.size()];
        
        for(var p:famList){
            pupilStr[i] = p.getShortUniqueString();
            i++;
        }
        Arrays.sort(pupilStr);
        generator = new Random();
        int number = generator.nextInt(10);
        */
        if (bt.niceString == null || bt.niceString.length()<5){
            transaction = new JLabel(bt.saveTransaction());
        } else {
            transaction = new JLabel(bt.niceString);
        }
        transaction.setToolTipText("<html><p width=\"500\">" + bt.saveTransaction()+"</p></html>");
         //"<html><p width=\"500\">" +value+"</p></html>"
        //patternList = new JComboBox(pupilStr);
        var pupArray = famList.toArray(new NewFamily[famList.size()]);
        Arrays.sort(pupArray);
        patternList = new JComboBox(pupArray);
        patternList.setSelectedIndex(-1);
        patternList.setEditable(false);
        patternList.addActionListener(this);
        patternList.setMaximumSize(new Dimension(400, 100));
//        patternList.setRenderer(new MyComboBoxRenderer());
        AutoCompleteDecorator.decorate(patternList);
        var schoolArray = famBySchoolMap.keySet().toArray();
        Arrays.sort(schoolArray);
        schoolSelect = new JComboBox(schoolArray);
        schoolSelect.setMaximumSize(new Dimension(400, 100));
        //schoolSelect = new JComboBox();
        schoolSelect.addActionListener(this);
        output = new JLabel("<html>------<br><br></html>");
        
        var level = new JPanel();
        level.add(patternList);
        level.add(schoolSelect);
        //level.setLayout(new BoxLayout(level, BoxLayout.X_AXIS));
        
        add(transaction);
        add(level);        
        add(output);
    }

  /*  private static void createAndShowGUI() {
        JFrame frame = new JFrame("Program Fit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JScrollPane newContentPane = new JScrollPane(new LOPanel());
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
        
        javax.swing.SwingUtilities.invokeLater(() -> LOPanel.createAndShowGUI());
    } */
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == patternList){
            JComboBox cb = (JComboBox)e.getSource();
            NewFamily petName = (NewFamily) cb.getSelectedItem();
            if (petName != null){
                output.setText("<html>" + petName.toString()+ "<br><br></html>");//TODO getShortUniqueString()+
                pu = petName;
                isSet=true;
            } else {
                output.setText("<html>------<br><br></html>");
                pu=null;
                isSet=false;
            }
            
        }
        if (e.getSource() == schoolSelect){
            isSet=false;
            JComboBox cb = (JComboBox)e.getSource();
            int school = (Integer) cb.getSelectedItem();
           /* patternList.removeAllItems();
            for(var p: mainData.getPupBySchoolMap().get(school)){
                patternList.addItem(p);
            }
            */
           DefaultComboBoxModel model = (DefaultComboBoxModel) patternList.getModel();
           model.removeAllElements();
           var list = famBySchoolMap.get(school);
           list.sort(Comparator.comparing(NewFamily::toString));
           model.addAll(list);
        }
    }
    
}
