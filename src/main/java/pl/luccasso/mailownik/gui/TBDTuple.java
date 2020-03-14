/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.gui;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.model.NewFamily;

/**
 *
 * @author piko
 */
public class TBDTuple extends JPanel {
    transient BankTransaction bt;
    transient List<NewFamily> guys;
    transient List<JCheckBox> lCheckbox;
    
    TBDTuple(TBDWindow handle, BankTransaction key, List<NewFamily> value) {
        super();
        lCheckbox = new LinkedList<>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new CompoundBorder( new LineBorder(Color.BLACK, 1),new EmptyBorder(5,5,5,5)));
        
        bt = key;
        guys = value;
        JLabel tran = new JLabel(bt.niceString);
        //System.out.println(bt.niceString);
        add(tran);
        for(var p:guys){
            var lab = new JCheckBox(p.toString());
            add(lab);
            lCheckbox.add(lab);
            //lab.addItemListener(handle);
            System.out.println(p.toString());
        }
        setOpaque(true);
        
    }
    public BankTransaction getTransaction(){
        return bt;
    }
    
    public NewFamily getFamilyOrNull(){
        for(int i=0;i<lCheckbox.size();i++){
            if (lCheckbox.get(i).isSelected()){
                System.out.println(guys.get(i).toString());
                return guys.get(i);
            }
        }
        return null;
    }
}
