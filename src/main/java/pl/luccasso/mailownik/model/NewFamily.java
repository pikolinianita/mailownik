/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.luccasso.mailownik.model;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import pl.luccasso.mailownik.Pupil;
import pl.luccasso.mailownik.SinglePupil;


/**
 *
 * @author piko
 */
@ToString
@Accessors(fluent = true, chain = true)
@Getter
public class NewFamily {

    ContactData contacts;
    
    PaymentsData payments;
    
    Childrens childrens;

    public NewFamily(SinglePupil sp) {

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
                .attendance(att)
                .id(iDs)
                .school(sp.getSchoolNr());
        
        childrens = new Childrens().add(child);
    }

    public boolean isMyBrother(Pupil pup) {
       return contacts.eMail().equalsIgnoreCase(pup.getEMail()) 
               || (!"".equals(contacts.nTel())&&contacts.nTel().equals(pup.getNTel()))
               ||(!"".equals(contacts.nTel2())&&contacts.nTel2().equals(pup.getNTel2()));
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
        payments.amend(sp);
        
        return this;
    }

    public int size(){
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

    public boolean isMylNameHere(String niceString)  {
        return childrens.isMylNameHere(niceString);
    }
    
    public void writeNames(){
        childrens.writeNames();
    }
}
