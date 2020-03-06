/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.Model;

import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
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
    
    List<NewPupil> childrens;

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
        
        childrens = new LinkedList<>();
        childrens.add(child);
        
        
        /*
         * this.id = id; this.skryptId = skryptId; this.nb = nb; this.schoolNr =
         * schoolNr; this.fName = fName; this.lName = lName; this.klass = klass;
         * this.nTel = nTel; this.nTel2 = nTel2; this.eMail = eMail;
         * this.timeSheet = timeSheet; this.accountNrs = accountNrs;
         * this.transactions = transactions; this.allPayments = allPayments;
         * this.allYear = allYear; this.oneSemester = oneSemester;
         * this.isSibling = isSibling;
         */
    }

}
