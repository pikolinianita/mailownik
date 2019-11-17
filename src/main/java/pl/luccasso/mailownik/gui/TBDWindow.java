/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.gui;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.util.List;
import java.util.Map;
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
public class TBDWindow extends JFrame{
        JScrollPane mainPanel;
        JPanel bottomRow;
        JButton ok, exit;
        
    public TBDWindow(String title, Map<BankTransaction, List<Pupil>> humanToDecide) throws HeadlessException {
        super(title);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new FlowLayout() ); 
        mainPanel = new JScrollPane();
        mainPanel.setOpaque(true);
        var desc = new JLabel("Wybierz, powinnaś zaznaczyć tylko 1 bo jak nie to kichna");
        
        bottomRow = new JPanel();
        ok = new JButton("Dalej");
        exit = new JButton("Anuluj");
        bottomRow.add(ok);
        bottomRow.add(exit);
        bottomRow.setOpaque(true);
        JPanel contentPanel = new JPanel();
        setContentPane(contentPanel);
        contentPanel.setOpaque(true);
        contentPanel.add(desc);
        contentPanel.add(mainPanel);
        contentPanel.add(bottomRow);
        
        setVisible(true);
        setSize(400, 300);
        
    }
    
}
