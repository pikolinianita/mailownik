/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author piko
 */
public class Siblings {
    Set <String> accountNrs;
    List <BankTransaction> bankTransactionsList;
    Set <String> sibIDSet;
    List <Pupil> familyList;
    int school;
    
    Siblings(Pupil p){
        this.accountNrs = new HashSet<>();
        this.bankTransactionsList = new LinkedList<>();
        this.sibIDSet = new HashSet<>();
        this.familyList = new LinkedList<>();
        this.school = p.schoolNr;
        this.addPup(p);
    }
    
    Siblings (List<Pupil> pupList){
        this(pupList.get(0));
        for (int i=1;i<pupList.size();i++){
            addPup(pupList.get(i));
        }
    }
    
    public final void addPup (Pupil p){
        if (p.accountNrs != null) {
            for (var a : p.accountNrs) {
                accountNrs.add(a);
            }
        }
        if (p.familyIDs != null) {
            for (var id : p.familyIDs) {
                sibIDSet.add(p.skryptId);
            }
        } else {
            sibIDSet.add(p.skryptId);
        }
        familyList.add(p);
    }
    
    public void addBT(BankTransaction bt){
        bankTransactionsList.add(bt);
    }

    /*void writeToFile(FileWriter fw) {
        
        fw.write(p.processTransactions(sibFitted.get(p)).getFileLine());
    }*/
    
    
}
