/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author piko
 */
public class SinglePupil implements Pupil //implements Comparable<Pupil>
{
        static int idCount = 1000;
        int id;
        String skryptId;
        int nb;
        int schoolNr;
        String fName;
        String lName;
        String klass;
        String nTel;
        String nTel2;
        String eMail;
        String [] timeSheet;
        Set <String> accountNrs;
        List <TransactionInfo> transactions;

   
        int allPayments;
        boolean allYear;
        boolean oneSemester;
        boolean isSibling;

    public SinglePupil(int id, String skryptId, int nb, int schoolNr, String fName, String lName, String klass, String nTel, String nTel2, String eMail, String[] timeSheet, Set<String> accountNrs, List<TransactionInfo> transactions, int allPayments, boolean allYear, boolean oneSemester, boolean isSibling) {
        this.id = id;
        this.skryptId = skryptId;
        this.nb = nb;
        this.schoolNr = schoolNr;
        this.fName = fName;
        this.lName = lName;
        this.klass = klass;
        this.nTel = nTel;
        this.nTel2 = nTel2;
        this.eMail = eMail;
        this.timeSheet = timeSheet;
        this.accountNrs = accountNrs;
        this.transactions = transactions;
        this.allPayments = allPayments;
        this.allYear = allYear;
        this.oneSemester = oneSemester;
        this.isSibling = isSibling;
    }

    public int getId() {
        return id;
    }
   
    /**
    * For testing Only
    * @param i
    * @param fN
    * @param lN
    * @param kl 
    */     
   SinglePupil (int i, String fN, String lN, String kl){
       this.id = idCount++;
       this.schoolNr = i;
       this.fName = fN.strip().toLowerCase();
       this.lName = lN.strip().toLowerCase();
       this.klass = kl.toLowerCase().strip();
   }     

  
   /**
    * Do wczytywania danych z pliku "wynikowego".csv 
    * @param line linijka z pliku csv
    */
   
   public SinglePupil(String line) {
        try(var sc = new Scanner(line)){
        sc.useDelimiter("\t");
        var gson = new Gson();
        
        this.id = sc.nextInt();
        this.skryptId = sc.next();
        this.schoolNr = sc.nextInt();
        this.fName = sc.next();       
        this.lName = sc.next();        
        this.klass = sc.next();       
        this.nTel2 = sc.next();     
        this.nTel = sc.next();     
        this.eMail = sc.next();
        
        this.timeSheet = new String[20];
        for (int i = 0; i < 20; i++) {            
            this.timeSheet[i] = sc.next();
        }
        sc.next();
        sc.next();
        sc.next();
        this.allPayments = sc.nextInt();
        sc.next();
        sc.next();
        sc.next();
        sc.next();
        TransactionInfo[] tr = gson.fromJson(sc.next(), TransactionInfo[].class);
        this.transactions = new LinkedList(Arrays.asList(tr));
        this.accountNrs = new HashSet<>(Arrays.asList(gson.fromJson(sc.next(), String[].class)));
        }
    }
           
    SinglePupil(GAppsParser.PupilImport e) {
       
       this.id = idCount++;
       this.schoolNr = e.schoolNr;
       this.fName = e.fName.strip().toLowerCase();
       this.lName = e.lName.strip().toLowerCase();
       this.klass = e.klass.toLowerCase().strip();
       
       this.nTel = e.fTel.length()>1 ? e.fTel : "" ; 
       this.nTel2 = e.mTel.length()>1 ? e.mTel : "";
       this.eMail = e.eMail;
       try{
       this.nb = Integer.valueOf(e.nb);
       }
       catch (NumberFormatException exc){
           this.nb = 0;
       }
       this.skryptId = e.skryptID;
       timeSheet = new String[20];
       for( int i=0; i<20;i++){
           if (i >= e.timeSheet.length) {
               timeSheet[i] = "";
           } else {
               switch (e.timeSheet[i]){
                   case "0": timeSheet[i] = "0"; break;
                   case "1": timeSheet[i] = "1"; break;
                   case "M":
                   case "m": timeSheet[i] = "M"; break;
                   default: timeSheet[i] = ""; break;
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
    public String toString() {        
        return lName + " " + fName + " ," + klass + " ," + schoolNr;
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
        final SinglePupil other = (SinglePupil) obj;
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
    
    @Override
    public boolean hasSameSkryptIdAs(GAppsParser.PupilImport other) {
        
        if (other == null) {
            return false;
        }
        
        if(this.skryptId.equals(other.skryptID)) {
            return true;
        }
        
        return false;        
    }
    
    
    @Override
    public boolean isMyfNameHere(String q){
        return q.toLowerCase().contains(fName.toLowerCase());
    }
    
    @Override
    public boolean isMylNameHere(String q){
        return q.toLowerCase().contains(lName.toLowerCase());
    }
    
    @Override
    public boolean isMyKlass(String kl){
        return klass.equals(kl);
    }
    
    @Override
    public boolean isMySchool(Integer n){
        return schoolNr == n;
    }
    
    @Override
    public boolean isMySchoolHere(String q){
        return q.contains(String.valueOf(schoolNr));
    }
            
    
    @Override
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
            default : roman = "vii";break;
        }
        if (ql.contains(roman+klass.charAt(1))) {
            return true;
        }
        if (ql.contains(roman+" "+klass.charAt(1))) {
            return true;
        }
        return false;
    }
    
    @Override
    public String getFileLine(){
        Gson gson = new Gson();
        int zeroes =0;
        int ones =0; 
        int nbs = 0;
        String ob = String.join("\t", timeSheet);
        for(char c: ob.toCharArray()){
            if (c=='0') {
                zeroes++;
            } else if (c == '1') {
                ones++;
            } else if (c == 'M') {
                nbs++;
            }
        }
        String tr =  gson.toJson(transactions);
        String acc = gson.toJson(accountNrs);
        String paymentType;
        int toPay;
        int toPayTotal = 0;
        int nZajec =  DoCompare.finData.schoolToPaymentsMap.get(schoolNr).nZajec;
        if (this.allYear) {
            if (isSibling) {
                paymentType = "R Roczna";
                toPay = DoCompare.finData.schoolToPaymentsMap.get(schoolNr).allYearWithSibling;
                toPayTotal = toPay;

            } else {
                paymentType = "Roczna";
                toPay = DoCompare.finData.schoolToPaymentsMap.get(schoolNr).allYear;
                toPayTotal = toPay;
            }
        } else if (this.oneSemester) {
            if (isSibling) {
                paymentType = "R Semestr";
                toPay = DoCompare.finData.schoolToPaymentsMap.get(schoolNr).oneSemesterWithSibling;
                toPayTotal = toPay * 2;
                if (LocalDate.now().isAfter(LocalDate.of(2020, 2, 20))) {
                    toPay *= 2;
                }
            } else {
                paymentType = "Semestralna";
                toPay = DoCompare.finData.schoolToPaymentsMap.get(schoolNr).oneSemester;
                toPayTotal = toPay * 2;
                if (LocalDate.now().isAfter(LocalDate.of(2020, 2, 20))) {
                    toPay *= 2;
                }
            }
        } else if (allPayments % 35 == 0) {
            paymentType = "Miesieczna";
            toPay = (zeroes + ones - this.nb) * 35;
            toPayTotal = (nZajec - this.nb - nbs) * 35;
        } else if (allPayments % 33 == 0) {
            paymentType = "Rodz Mies";
            toPay = (zeroes + ones - this.nb) * 33;
            toPayTotal = (nZajec - this.nb - nbs) * 33;
        } else {
            paymentType = "?Suspected?";
            toPay = (zeroes + ones - this.nb) * 35;
        }
        if (allPayments == 0) {
            paymentType = "Brak Wpłat";
        }
        
        this.nb+=nbs;
       
        return String.join("\t",String.valueOf(id),skryptId, String.valueOf(schoolNr),
                        fName,lName,klass, nTel2, nTel,eMail, 
                        ob, String.valueOf(ones), String.valueOf(zeroes), String.valueOf(nb), String.valueOf(allPayments), String.valueOf(toPay), paymentType,String.valueOf(toPayTotal), String.valueOf(nZajec),tr, acc )+"\n";
    }
    
    @Override
    public String getPupilDataNoTimeSheet(){
        return String.join(", ", getShortUniqueString(), String.valueOf(allPayments), transactions.toString(), accountNrs.toString());
    }
            
    
    @Override
    public String getShortUniqueString(){
        return String.join(", ", lName, fName, String.valueOf(schoolNr), klass );
    }

    public Pupil processTransactions(List<BankTransaction> lisT) {
        if (lisT != null) {
            new_transaction:
            for (var bt : lisT) {
                for(var ti:transactions){
                    if(ti.sameAs(bt)){
                        break new_transaction;
                    }
                }
                FinancialData.SchoolPayments payValues = DoCompare.finData.schoolToPaymentsMap.get(schoolNr);
                if (bt.amount == payValues.allYear || bt.amount == payValues.allYearWithSibling) {
                    allYear = true;
                } else if (bt.amount == payValues.oneSemester || bt.amount == payValues.oneSemesterWithSibling) {
                    oneSemester = true;
                }
                if (bt.amount == payValues.allYearWithSibling || bt.amount == payValues.oneSemesterWithSibling) {
                    this.isSibling = true;
                }
                transactions.add(new TransactionInfo(bt));
                accountNrs.add(bt.account);
                allPayments += bt.amount;
            }
        }
        return this;
    }

    
   /* @Override
    public int compareTo(Pupil p) {
        return this.lName.compareTo(p.lName);
    } */

    @Override
    public void updateValuesWithGoogleData(Pupil p) {
       this.fName = p.getFName();
       this.lName = p.getLName();
       this.klass = p.getKlass();
       //String[] timeSheet;
       this.nTel = p.getNTel(); 
       this.nTel2 = p.getNTel2();
       this.eMail = p.getEMail();
       this.timeSheet = p.getTimeSheet();
       this.nb = p.getNb();
    }

    

    
    
    @Override
    public int getSchoolNr() {
        return schoolNr;
    }

    @Override
    public String getKlass() {
        return klass;
    }

    @Override
    public Set<String> getAccountNrs() {
        return accountNrs;
    }

    @Override
    public String getSkryptId() {
        return skryptId;
    }
    
    @Override
    public int getNb() {
        return nb;
    }

    @Override
    public String getFName() {
        return fName;
    }

    @Override
    public String getLName() {
        return lName;
    }

    @Override
    public String getNTel() {
        return nTel;
    }

    @Override
    public String getNTel2() {
        return nTel2;
    }

    @Override
    public String getEMail() {
        return eMail;
    }

    @Override
    public String[] getTimeSheet() {
        return timeSheet;
    }
    
     public List<TransactionInfo> getTransactions() {
        return transactions;
    }
     
     public SinglePupil addAccount(String accNo){
        accountNrs.add(accNo);
        return this;
    }
}
