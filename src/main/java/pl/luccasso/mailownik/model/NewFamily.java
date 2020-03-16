/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.model;

import com.google.gson.Gson;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.Pupil;
import pl.luccasso.mailownik.SinglePupil;

/**
 *
 * @author piko
 */

@Accessors(fluent = true, chain = true)
@Getter
public class NewFamily implements Comparable<NewFamily>{

    ContactData contacts;

    PaymentsData payments;

    Childrens childrens;

    public NewFamily(SinglePupil sp)  {

        contacts = new ContactData()
                .eMail(sp.getEMail())
                .nTel(sp.getNTel())
                .nTel2(sp.getNTel2());

        payments = new PaymentsData()
                .accountNrs(sp.getAccountNrs())
                .transactions(sp.getTransactions());

        var att = new Attendance()
                .nb(sp.getNb())
                .timeSheet(sp.getTimeSheet());
        var iDs = new IDs()
                .skryptId(sp.getSkryptId())
                .id(sp.getId());
        var child = new NewPupil()
                .fName(sp.getFName())
                .lName(sp.getLName())
                .klass(sp.getKlass())
                .attendance(att)
                .id(iDs)
                .school(sp.getSchoolNr());

        childrens = new Childrens().add(child);
    }

    public boolean isMyBrother(Pupil pup) {
        
        //In case all contact data is empty
        if("".equals(contacts.eMail()) 
                && "".equals(contacts.nTel()) 
                && "".equals(contacts.nTel2()) ){
            return false;
        }
         
        return contacts.eMail().equalsIgnoreCase(pup.getEMail())
                && contacts.nTel().equals(pup.getNTel())
                && contacts.nTel2().equals(pup.getNTel2());   
    }

    public NewFamily add(SinglePupil sp) {

        var att = new Attendance()
                .nb(sp.getNb())
                .timeSheet(sp.getTimeSheet());
        var iDs = new IDs()
                .skryptId(sp.getSkryptId())
                .id(sp.getId());
        var child = new NewPupil()
                .fName(sp.getFName())
                .lName(sp.getLName())
                .klass(sp.getKlass())
                .attendance(att)
                .id(iDs)
                .school(sp.getSchoolNr());

        childrens.add(child);
        payments.amendWith(sp);

        return this;
    }

    public int size() {
        return childrens.size();
    }

    public int totalPayments() {
        return payments.toalPayments();
    }

    Set<String> allAccounts() {
        return payments.accountNrs();
    }

    public int getSchoolNr() {
        return childrens().getSchoolNr();
    }

    public Iterable<String> getAccountNrs() {
        return payments().accountNrs();
    }

    public boolean isMyKlass(String klass) {
        return childrens.isMyKlass(klass);
    }

    public boolean isMySchoolHere(String school) {
        return childrens.isMySchoolHere(school);
    }

    public boolean isMyfNameHere(String niceString) {
        return childrens.isMyfNameHere(niceString);
    }

    public boolean isMylNameHere(String niceString) {
        return childrens.isMylNameHere(niceString);
    }

    public void writeNames() {
        childrens.writeNames();
    }
   
    public String getKlass() {
        return childrens().list().get(0).klass();
    }

    
    @Override
    public int compareTo(NewFamily other) {
        return this.childrens().list().get(0).lName
                .compareToIgnoreCase(other.childrens().list().get(0).lName);
    }
    
    public String getShortUniqueString(){
        return this.childrens().getShortUniqueString();             
    }

    @Override
    public String toString() {
        return getShortUniqueString();
    }
    
    public String toString(int fake) {
        return "NewFamily{" + ", childrens=" + childrens + "contacts=" + contacts + ", payments=" + payments + '}';
    }

    public void convertTransactionsToTrInfo(List<BankTransaction> btList) {
        btList.forEach(payments::addTransaction);
                
    }

     public String[] getFileLines() {
        int size = childrens.list().size();
        String[] response = new String[size];
        int i = 0;
         Gson gson = new Gson();
        for (var child : childrens.list()) {
            child.attendance().payAbleHoursCalculation();
            PaymentsResolver.PaymentDTO paymentInfo = new PaymentsResolver(this).moneyResolve();
            response[i] = String.join("\t", String.valueOf(child.id().id()),
                     String.valueOf(child.id().skryptId()),
                     String.valueOf(child.school()),
                     child.fName(),
                     child.lName(),
                     child.klass(),
                     contacts().nTel2(),
                     contacts().nTel(),
                     contacts().eMail(),
                     child.attendance().getTimeSheetString(),
                     String.valueOf(child.attendance().ones()),
                     String.valueOf(child.attendance().zeroes()),
                     String.valueOf(child.attendance().eMs()),
                     childrens.getFamilyId(),
                     String.valueOf(paymentInfo.totalPaidAmount()),
                     String.valueOf(paymentInfo.needToPay()),
                     paymentInfo.description(),
                     String.valueOf(paymentInfo.toPayForAllYearAmount()),
                     String.valueOf(paymentInfo.nZajec()),
                     gson.toJson(payments.transactions()),
                     gson.toJson(payments.accountNrs()) + "\n"
            );
            i++;
        }
        return response;
    }

    int getSumOfPayments() {
        return payments().getSumOfPayments();
    }

    int getNumberPaidKlasses() {
        int sum = 0;
        for(var child : childrens().list()){
            sum += child.attendance().payAbleHoursCalculation();
                    }
        return sum;
    }

    int getNumberUspraw() {
        int sum = 0;
        for(var child : childrens().list()){
            sum += child.attendance().zeroes();
                    }
        return sum;
    }   
    
    int[] getValuesArray(){
        return payments().getValuesArray();
    }
}

