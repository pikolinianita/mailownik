/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.gui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.DoCompare;

/**
 *
 * @author piko
 */
public class SibWindow extends JFrame implements ActionListener {
    List<BankTransaction> btList;
    List<SibPanel> sibPanList;
    JButton ok,cancel; 
    DoCompare mainData;
    MainWindow handler;
    
    public SibWindow(MainWindow mW, String title, DoCompare mD ) throws HeadlessException {
        super(title);
        mainData = mD;
        handler = mW;
        
        btList = mainData.getSiblingsBTList();
        sibPanList = new LinkedList<>();
      
        System.out.println("siblings");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane kasza = new JScrollPane(contentPanel);
        kasza.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(kasza);
        contentPanel.setOpaque(true);

        int nItems = btList.size()>50? 50 : btList.size();
        
        for (int i = 0; i < nItems;i++){
            var sip = new SibPanel(btList.get(i), mainData.getPupilList(), mainData.getPupBySchoolMap());
            sibPanList.add(sip);
            contentPanel.add(sip);
        }
        ok = new JButton("ok");
        ok.addActionListener(this);
       // ok.addActionListener(handler);
        cancel = new JButton("anuluj");
        cancel.addActionListener(this);
        //cancel.addActionListener(handler);
        contentPanel.add(ok);
        contentPanel.add(cancel);
        setSize(400, 300);
        pack();
        setVisible(true);
        System.out.println(SiblingsInfo());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("/listener for sib");
        if (e.getSource() == ok){
            System.out.println(SiblingsInfo());
            for(var s:sibPanList){
                if(s.isSet){
                    System.out.println("cosik " + s.chosenSiblings.size());
                    mainData.pushLinesToSiblings(s.bt, s.chosenSiblings);
                }
            }
            handler.writeSummary();
            this.dispose();
            
        } else if (e.getSource() == cancel) {
            handler.writeSummary();
            this.dispose();
        }
       
    }
    
    String SiblingsInfo(){
        int i=0;
         for(var s:sibPanList){
              if(s.isSet){
                    i++;
                }
         }
         return "Jest " + i + " wpisow z isSet";
    }
}
