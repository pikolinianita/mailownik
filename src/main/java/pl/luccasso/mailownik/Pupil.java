/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import com.google.gson.Gson;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author piko
 */
public class Pupil implements Comparable<Pupil>{
        static int idCount = 1000;
        int id;
        int schoolNr;
        String fName;
        String lName;
        String klass;
        String nTel;        
        String eMail;
        String [] timeSheet;
        Set <String> accountNrs;
        List <TransactionInfo> transactions;
        int allPayments;
        boolean AllYear;
        boolean oneSemester;
   
    /*Pupil(){
        
    }
        */
    /**
    * For testing Only
    * @param i
    * @param fN
    * @param lN
    * @param kl 
    */     
   Pupil (int i, String fN, String lN, String kl){
      // this();
       this.id = idCount++;
       this.schoolNr = i;
       this.fName = fN.strip().toLowerCase();
       this.lName = lN.strip().toLowerCase();
       this.klass = kl.toLowerCase().strip();
   }     

    @Override
    public String toString() {
        //return "Uczeń: " + "schoolNr=" + schoolNr + ", fName=" + fName + ", lName=" + lName + ", klass=" + klass + " .";
        return lName + " " + fName + " ," + klass + " ," + schoolNr;
    }
        
    Pupil(GAppsParser.PupilImport e) {
       //this();
       this.id = idCount++;
       this.schoolNr = e.schoolNr;
       this.fName = e.fName.strip().toLowerCase();
       this.lName = e.lName.strip().toLowerCase();
       this.klass = e.klass.toLowerCase().strip();
       //String[] timeSheet;
       this.nTel = e.fTel.length()>5 ? e.fTel : e.mTel ;       
       this.eMail = e.eMail;
       timeSheet = new String[20];
       for( int i=0; i<20;i++){
           if (i >= e.timeSheet.length) {
               timeSheet[i] = "";
           } else {
               switch (e.timeSheet[i]){
                   case "0": timeSheet[i] = "0"; break;
                   case "1": timeSheet[i] = "1"; break;
                   default: timeSheet[i] = "";
               }
           }
       }
       accountNrs = new HashSet<>();
       transactions = new LinkedList<>();
       allPayments = 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.schoolNr;
        hash = 79 * hash + Objects.hashCode(this.fName);
        hash = 79 * hash + Objects.hashCode(this.lName);
        hash = 79 * hash + Objects.hashCode(this.klass);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pupil other = (Pupil) obj;
        if (this.schoolNr != other.schoolNr) {
            return false;
        }
        if (!Objects.equals(this.fName, other.fName)) {
            return false;
        }
        if (!Objects.equals(this.lName, other.lName)) {
            return false;
        }
        if (!Objects.equals(this.klass, other.klass)) {
            return false;
        }
        return true;
    }
    
    public boolean equals(GAppsParser.PupilImport other) {
        
        if (other == null) {
            return false;
        }
        
        if (this.schoolNr != other.schoolNr) {
            return false;
        }
        if (!Objects.equals(this.fName, other.fName)) {
            return false;
        }
        if (!Objects.equals(this.lName, other.lName)) {
            return false;
        }
        if (!Objects.equals(this.klass, other.klass)) {
            return false;
        }
        return true;
    }
    
    
    public boolean isMyfNameHere(String q){
        return q.toLowerCase().contains(fName.toLowerCase());
    }
    
    public boolean isMylNameHere(String q){
        return q.toLowerCase().contains(lName.toLowerCase());
    }
    
    public boolean isMyKlass(String kl){
        return klass.equals(kl);
    }
    
    public boolean isMySchool(Integer n){
        return schoolNr == n;
    }
    
    public boolean isMySchoolHere(String q){
        return q.contains(String.valueOf(schoolNr));
    }
            
    
    public boolean isMyKlassHere(String q){
        var ql = q.toLowerCase();
        if (ql.contains(klass)) {
            return true;
        }
        if (ql.contains(klass.charAt(0)+" " + klass.charAt(1))) {
            return true;
        }
        String roman;
        switch(klass.charAt(0)){
            case '1': roman = "i"; break;
            case '2': roman = "ii"; break;
            case '3': roman = "iii"; break;
            case '4': roman = "iv"; break;
            case '5': roman = "v"; break;
            case '6': roman = "vi";break;
            default : roman = "vii";
        }
        if (ql.contains(roman+klass.charAt(1))) {
            return true;
        }
        if (ql.contains(roman+" "+klass.charAt(1))) {
            return true;
        }
        return false;
    }
    
    public String getFileLine(){
        Gson gson = new Gson();
        int zeroes =0, ones =0;
        String ob = String.join("\t", timeSheet);
        for(char c: ob.toCharArray()){
            if (c=='0') {
                zeroes++;
            } else if (c == '1') {
                ones++;
            }
        }
        String tr =  gson.toJson(transactions);
        String acc = gson.toJson(accountNrs);
        String paymentType = AllYear ? "Roczna" : (oneSemester ? "semestr" : "Miesieczna");
        //ob.
        /*String.join("\t", "Id","Szkoła","Imie","Nazwisko","Klasa","Telefon","Mail",
                "Zaj 1","2","3","4","5","6","7","8","9","10","11","12", "13","14","15","16","17","18","19","20",
                "Suma wpłat","wpłaty","DanePrzelewow","konta"); */
        return String.join("\t",String.valueOf(id), String.valueOf(schoolNr),
                        fName,lName,klass, nTel, eMail, 
                        ob, String.valueOf(ones), String.valueOf(zeroes), String.valueOf(allPayments), paymentType, tr, acc )+"\n";
    }
    
    public String getShortUniqueString(){
        return String.join(", ", lName, fName, String.valueOf(schoolNr), klass );
    }

    Pupil processTransactions(List<BankTransaction> lisT) {
        if (lisT != null){
        for(var bt:lisT){
            FinancialData.SchoolPayments payValues = DoCompare.finData.schoolToPaymentsMap.get(schoolNr);
            if (bt.amount == payValues.allYear) {
                AllYear = true;
            } else if (bt.amount == payValues.oneSemester) {
                oneSemester = true;
            }
            transactions.add(new TransactionInfo(bt));
            accountNrs.add(bt.account);
            allPayments += bt.amount;
        }}
        return this;
    }

    
    @Override
    public int compareTo(Pupil p) {
        return this.lName.compareTo(p.lName);
    }
    
}
