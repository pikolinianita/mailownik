/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import pl.luccasso.mailownik.DoCompare;

/**
 *
 * @author piko
 */
public class PaymentsResolver {

    NewFamily family;

    //Kind of payments - monthy, yearly...
    String description;

    //Sum of paid money
    int totalPaidAmount;

    //... assuming 100% attendance
    int toPayForAllYearAmount;

    //debt
    int needToPay;

    //number courses for school
    int nZajec;

    PaymentsResolver(NewFamily aThis) {
        family = aThis;
        nZajec = DoCompare.finData.getNumberOfClassesForSchool(family.getSchoolNr());
        totalPaidAmount = family.getSumOfPayments();
    }

    PaymentDTO moneyResolve() {
        if (totalPaidAmount == 0) {
            processNoPay();
        } else if (isYearly()) {
            processYearly();
        } else if (isBiYearly()) {
            processBiYearly();
        } else if (isMonthly()) {
            processMonthly();
        } else {
            processNoIdea();
        }

        return new PaymentDTO(this);
    }

    private boolean isYearly() {
        return DoCompare.finData.isYearlyAmount(totalPaidAmount/family.size());
    }

    private boolean isBiYearly() {
        return DoCompare.finData.isHalfYearlyAmount(totalPaidAmount/family.size());
    }

    private boolean isMonthly() {
        return totalPaidAmount % DoCompare.finData.singleKlassPayment()== 0
                || totalPaidAmount % DoCompare.finData.singleKlassPaymentWithSibling() == 0;
    }

    private void processNoPay() {
        description = "Brak wpłat";
        var valueForOneKlass = family.size() == 1 ? DoCompare.finData.singleKlassPayment() : DoCompare.finData.singleKlassPaymentWithSibling();
        toPayForAllYearAmount = (family.size()*nZajec - family.getNumberUspraw())*valueForOneKlass;
        needToPay = family.getNumberPaidKlasses()*valueForOneKlass;
    }

    private void processYearly() {
       
    }

    private void processBiYearly() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void processMonthly() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void processNoIdea() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
    
    
    
    @Accessors(fluent = true, chain = true)
    @Getter
    static class PaymentDTO {

        //Kind of payments - monthy, yearly...
        String description;

        //Sum of paid money
        int totalPaidAmount;

        //... assuming 100% attendance
        int toPayForAllYearAmount;

        //debt
        int needToPay;

        //number courses for school
        int nZajec;

        public PaymentDTO(PaymentsResolver dataSource) {
            this.description = dataSource.description;
            this.needToPay = dataSource.needToPay;
            this.toPayForAllYearAmount = dataSource.toPayForAllYearAmount;
            this.totalPaidAmount = dataSource.totalPaidAmount;
            this.nZajec = dataSource.nZajec;
        }

    }
}
