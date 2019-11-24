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
import pl.luccasso.mailownik.Pupil;

/**
 *
 * @author piko
 */
public class SibPanel  extends JPanel implements ActionListener{

    BankTransaction bt;
    List<Pupil> pupList;
    Map<Integer, List<Pupil>> pupBySchoolMap;
    JLabel transaction, output;
    List<Pupil> chosenSiblings;
    List <JComboBox> pupCombos;
    boolean isSet;
    
    SibPanel(BankTransaction bt, List<Pupil> pupilList, Map<Integer, List<Pupil>> pupBySchoolMap) {
        this.bt = bt;
        pupList = pupilList;
        this.pupBySchoolMap = pupBySchoolMap;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        pupCombos = new LinkedList<>();
        chosenSiblings = new LinkedList<>();
        isSet = false;
        
        transaction = new JLabel(bt.niceString);
        transaction.setToolTipText(bt.saveTransaction());
         
        int nSiblings = 3;
        var level = new JPanel();
        level.setLayout(new BoxLayout(level, BoxLayout.Y_AXIS));
        for (int i = 0; i < nSiblings; i++) {
            var pupArray = pupList.toArray(new Pupil[pupList.size()]);
            Arrays.sort(pupArray);
            var patternList = new JComboBox(pupArray);
            patternList.setEditable(false);
            patternList.setSelectedIndex(-1);
            patternList.addActionListener(this);
            patternList.setMaximumSize(new Dimension(400, 100));
//        patternList.setRenderer(new MyComboBoxRenderer());
            AutoCompleteDecorator.decorate(patternList);

            level.add(patternList);
            pupCombos.add(patternList);
        }

        output = new JLabel("<html>------<br><br></html>");

        add(transaction);
        /*for(var c: pupCombos){
            add(c);
        }*/
        add(level);
        add(output);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals("comboBoxEdited")){
            JComboBox cb = (JComboBox)e.getSource();
            if (pupCombos.contains(cb)){
            Pupil petName = (Pupil) cb.getSelectedItem();
            if (petName != null){
                //output.setText("<html>" + petName.getShortUniqueString()+"<br><br></html>");
                chosenSiblings.add(petName);
                isSet=true;
                System.out.println("Action Event from sibPanel: " + petName.getShortUniqueString());
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
        for (var c:pupCombos){
            Pupil petName = (Pupil) c.getSelectedItem();
            if (petName != null) {
                message += petName.getShortUniqueString()+"<br>";
             }
        }
        if (message.equals("<html>") ) {
            message = "<html>------";
        }
        message += "<br></html>";
        
        output.setText(message);
    }}
    
}
