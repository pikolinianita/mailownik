/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import pl.luccasso.mailownik.SinglePupil;
import pl.luccasso.mailownik.TransactionInfo;


public class SinglePupilBuilder {

    private int id = 400;
    private String skryptId = "s-300";
    private int nb = 0;
    private int schoolNr = 50;
    private String fName = "Adam";
    private String lName = "Byk";
    private String klass = "1a";
    private String nTel = "500000000";
    private String nTel2 = "400000000";
    private String eMail = "AdamByk@def.pl";
    private java.lang.String[] timeSheet = null;
    private Set<String> accountNrs = null;
    private List<TransactionInfo> transactions = null;
    private int allPayments = 0;
    private boolean AllYear = false;
    private boolean oneSemester = false;
    private boolean isSibling = false;

    private int count = 100;
    
    public SinglePupilBuilder() {
    }

    public SinglePupilBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public SinglePupilBuilder setSkryptId(String skryptId) {
        this.skryptId = skryptId;
        return this;
    }

    public SinglePupilBuilder setNb(int nb) {
        this.nb = nb;
        return this;
    }

    public SinglePupilBuilder setSchoolNr(int schoolNr) {
        this.schoolNr = schoolNr;
        return this;
    }

    public SinglePupilBuilder setFName(String fName) {
        this.fName = fName;
        return this;
    }

    public SinglePupilBuilder setLName(String lName) {
        this.lName = lName;
        return this;
    }

    public SinglePupilBuilder setKlass(String klass) {
        this.klass = klass;
        return this;
    }

    public SinglePupilBuilder setNTel(String nTel) {
        this.nTel = nTel;
        return this;
    }

    public SinglePupilBuilder setNTel2(String nTel2) {
        this.nTel2 = nTel2;
        return this;
    }

    public SinglePupilBuilder setEMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public SinglePupilBuilder setTimeSheet(java.lang.String[] timeSheet) {
        this.timeSheet = timeSheet;
        return this;
    }

    public SinglePupilBuilder setAccountNrs(Set<String> accountNrs) {
        this.accountNrs = accountNrs;
        return this;
    }

    public SinglePupilBuilder setTransactions(List<TransactionInfo> transactions) {
        this.transactions = transactions;
        return this;
    }

    public SinglePupilBuilder setAllPayments(int allPayments) {
        this.allPayments = allPayments;
        return this;
    }

    public SinglePupilBuilder setAllYear(boolean AllYear) {
        this.AllYear = AllYear;
        return this;
    }

    public SinglePupilBuilder setOneSemester(boolean oneSemester) {
        this.oneSemester = oneSemester;
        return this;
    }

    public SinglePupilBuilder setIsSibling(boolean isSibling) {
        this.isSibling = isSibling;
        return this;
    }

    public SinglePupil createSinglePupil() {
        return new SinglePupil(id, skryptId, nb, schoolNr, fName, lName, klass, nTel, nTel2, eMail, timeSheet, accountNrs, transactions, allPayments, AllYear, oneSemester, isSibling);
    }
    
    public List<SinglePupil> createSinglePupilList(int count) {
        var list = new LinkedList<SinglePupil>();
        list.add(createSinglePupil());
        for (int i = 1; i< count; i++){
            updatePupilData_Step();
            list.add(createSinglePupil());
        }
        
        return list;
    }

    private void updatePupilData_Step() {
        
        
        
        count++;
    }
}