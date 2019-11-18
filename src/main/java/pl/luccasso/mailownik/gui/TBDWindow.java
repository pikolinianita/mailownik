/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.gui;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pl.luccasso.mailownik.*;

/**
 *
 * @author piko
 */
public class TBDWindow extends JFrame implements ActionListener {

    JPanel mainPanel;
    JPanel bottomRow;
    JButton ok, exit;
    LinkedHashMap<BankTransaction, List<Pupil>> humanToDecide;
    int counter;
    List<TBDTuple> lTuples;

    public TBDWindow(String title, Map<BankTransaction, List<Pupil>> hTD) throws HeadlessException {
        super(title);
        counter = 0;
        humanToDecide = new LinkedHashMap<>(hTD);
        lTuples = new LinkedList<>();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        mainPanel = new JPanel();

        int n = humanToDecide.keySet().size();
        if (n > 10) {
            n = 10;
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
        exit = new JButton("Anuluj");
        exit.addActionListener(this);
        bottomRow.add(ok);
        bottomRow.add(exit);
        bottomRow.setOpaque(true);
        JPanel contentPanel = new JPanel();
        JScrollPane kasza = new JScrollPane(contentPanel);
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
        if (e.getSource() == ok) {
            for (var t : lTuples) {
                Pupil pup = t.getPupilOrNull();
                if (pup != null) {
                    MainWindow.mainData.addToFittedData(pup, t.getTransaction());
                    MainWindow.mainData.removeFromHumanToDecide(t.getTransaction());
                } else {
                    MainWindow.mainData.addToLeftOvers(t.getTransaction());
                    MainWindow.mainData.removeFromHumanToDecide(t.getTransaction());
                }
                    
            }
            this.dispose();
        }

        if (e.getSource() == exit) {
            this.dispose();
        }
    }

}
