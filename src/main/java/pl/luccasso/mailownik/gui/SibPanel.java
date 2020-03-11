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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.model.NewFamily;

/**
 *
 * @author piko
 */
public class SibPanel  extends JPanel implements ActionListener{

    BankTransaction bt;
    List<NewFamily> FamList;
    Map<Integer, List<NewFamily>> famBySchoolMap;
    JLabel transaction, output;
    List<NewFamily> chosenSiblings;
    List <JComboBox> famCombos;
    boolean isSet;
    
    SibPanel(BankTransaction bt, List<NewFamily> famlList, Map<Integer, List<NewFamily>> pupBySchoolMap) {
        this.bt = bt;
        FamList = famlList;
        this.famBySchoolMap = pupBySchoolMap;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        famCombos = new LinkedList<>();
        chosenSiblings = new LinkedList<>();
        isSet = false;
        
        transaction = new JLabel(bt.niceString);
        transaction.setToolTipText("<html><p width=\"500\">" + bt.saveTransaction() + "</p></html>");
         //"<html><p width=\"500\">" +value+"</p></html>"
        int nSiblings = 3;
        var level = new JPanel();
        level.setLayout(new BoxLayout(level, BoxLayout.Y_AXIS));
        for (int i = 0; i < nSiblings; i++) {
            var pupArray = FamList.toArray(new NewFamily[FamList.size()]);
            Arrays.sort(pupArray);
            var patternList = new JComboBox(pupArray);
            patternList.setEditable(false);
            patternList.setSelectedIndex(-1);
            patternList.addActionListener(this);
            patternList.setMaximumSize(new Dimension(400, 100));
//        patternList.setRenderer(new MyComboBoxRenderer());
            AutoCompleteDecorator.decorate(patternList);

            level.add(patternList);
            famCombos.add(patternList);
        }

        output = new JLabel("<html>------<br><br></html>");

        add(transaction);
        /*for(var c: famCombos){
            add(c);
        }*/
        add(level);
        add(output);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals("comboBoxEdited")){
            JComboBox cb = (JComboBox)e.getSource();
            if (famCombos.contains(cb)){
            NewFamily petName = (NewFamily) cb.getSelectedItem();
            if (petName != null){
                //output.setText("<html>" + petName.getShortUniqueString()+"<br><br></html>");
                chosenSiblings.add(petName);
                isSet=true;
                System.out.println("Action Event from sibPanel: " + petName.toString()); //TOOO byl short unique line
                System.out.println(e.getActionCommand());
                System.out.println(e.paramString());
                //System.out.println();
            } else {
                //pu=null;
                chosenSiblings.clear();
                isSet=false;
            }
            }
            
        String message = "<html>";
        for (var c:famCombos){
            NewFamily petName = (NewFamily) c.getSelectedItem();
            if (petName != null) {
                message += petName.toString()+"<br>";   //ToDo getShortUniqueString()
             }
        }
        if (message.equals("<html>") ) {
            message = "<html>------";
        }
        message += "<br></html>";
        
        output.setText(message);
    }}
    
}
