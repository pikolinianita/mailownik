/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pl.luccasso.mailownik.*;
import pl.luccasso.mailownik.model.NewFamily;

/**
 *
 * @author piko
 */
public class TBDWindow extends JFrame implements ActionListener {
    MainWindow handler;
    JPanel mainPanel;
    JPanel bottomRow;
    JButton ok, exit;
    transient LinkedHashMap<BankTransaction, List<NewFamily>> humanToDecide;
    int counter;
    transient List<TBDTuple> lTuples;

    public TBDWindow(MainWindow mW, String title, Map<BankTransaction, List<NewFamily>> hTD) {
        super(title);
        handler = mW;
        counter = 0;
        humanToDecide = new LinkedHashMap<>(hTD);
        lTuples = new LinkedList<>();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        mainPanel = new JPanel();

        int n = humanToDecide.keySet().size();
        if (n > 30) {
            n = 30;
        }
        var it = humanToDecide.entrySet().iterator();
        for (int i = 0; i < n; i++) {
            var entry = it.next();
            var tuple = new TBDTuple(this, entry.getKey(), entry.getValue());
            lTuples.add(tuple);
            mainPanel.add(tuple);
        }
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(true);
        var desc = new JLabel("Wybierz, powinnaś zaznaczyć tylko 1 bo jak nie to kichna");
        //var scroll = new JScrollPane(mainPanel);
        bottomRow = new JPanel();
        ok = new JButton("Dalej");
        ok.addActionListener(this);
        ok.addActionListener(handler);
        exit = new JButton("Anuluj");
        exit.addActionListener(handler);
        exit.addActionListener(this);
        bottomRow.add(ok);
        bottomRow.add(exit);
        bottomRow.setOpaque(true);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane kasza = new JScrollPane(contentPanel);
        kasza.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(kasza);
        contentPanel.setOpaque(true);
        contentPanel.add(desc);
        contentPanel.add(mainPanel);
        //contentPanel.add(scroll);
        contentPanel.add(bottomRow);
        setSize(400, 300);
        pack();
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Listener for tbd");
        if (e.getSource() == ok) {
            for (var t : lTuples) {
                NewFamily pup = t.getFamilyOrNull();
                if (pup != null) {
                    MainWindow.doCompare.addToFittedData(pup, t.getTransaction());
                    MainWindow.doCompare.removeFromHumanToDecide(t.getTransaction());
                } else {
                    MainWindow.doCompare.addToLeftOvers(t.getTransaction());
                    MainWindow.doCompare.removeFromHumanToDecide(t.getTransaction());
                }
                    
            }
            handler.writeSummary();
            this.dispose();
        }

        if (e.getSource() == exit) {
            handler.writeSummary();
            this.dispose();
        }
    }

}
