/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.DoCompare;

/**
 *
 * @author piko
 */
class LeftOWindow extends JFrame implements ActionListener {
    List<BankTransaction> btList;
    List<LOPanel> lOPanList;
    JButton ok; 
    JButton cancel; 
    DoCompare mainData;
    MainWindow handler;
    
    public LeftOWindow(MainWindow mW, String title, DoCompare mD ) {
        super(title);
        mainData = mD;
        handler = mW;
        
        btList = mainData.getLeftOvers();
        lOPanList = new LinkedList<>();
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane kasza = new JScrollPane(contentPanel);
        setContentPane(kasza);
        kasza.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.setOpaque(true);
        int nItems = btList.size()>50? 50 : btList.size();
        
        for (int i = 0; i < nItems;i++){
            var lop = new LOPanel(btList.get(i), mainData.getPupilList(), mainData.getPupBySchoolMap());
            lOPanList.add(lop);
            contentPanel.add(lop);
        }
        
        ok = new JButton("ok");
        ok.addActionListener(this);
        ok.addActionListener(handler);
        cancel = new JButton("anuluj");
        cancel.addActionListener(this);
        cancel.addActionListener(handler);
        contentPanel.add(ok);
        contentPanel.add(cancel);
        setSize(400, 300);
        pack();
        setVisible(true);
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == ok){
            for(var l: lOPanList) {
                if(l.isSet){
                    mainData.addToFittedData(l.pu, l.bt);
                    btList.remove(l.bt);
                }
            }
            handler.writeSummary();
            this.dispose();      
        }
        
        if (e.getSource() == cancel){
            handler.writeSummary();
            this.dispose();
        }
    }
    
}
