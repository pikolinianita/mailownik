/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.Model;

import java.util.List;
import java.util.Set;
import pl.luccasso.mailownik.SinglePupil;
import pl.luccasso.mailownik.TransactionInfo;

/**
 *
 * @author piko
 */
public class NewFamily {

    Attendance attendance;
    ContactData contacts;
    PaymentsData payments;
    IDs iDs;
    List<NewPupil> Childrens;

    public NewFamily(SinglePupil sp) {
        attendance = new Attendance()
                .nb(sp.getNb())
                .timeSheet(sp.getTimeSheet());
        contacts = new ContactData()
                .eMail(sp.getEMail())
                .nTel(sp.getNTel())
                .nTel2(sp.getNTel2());
        
        /*        
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
        */
    }

}
