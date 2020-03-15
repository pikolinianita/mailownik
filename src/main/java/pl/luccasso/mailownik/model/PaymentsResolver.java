/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.model;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import pl.luccasso.mailownik.DoCompare;
import pl.luccasso.mailownik.config.ConfigF;

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
        } else if (isYearly(totalPaidAmount)) {
            processYearly();
        } else if (isBiYearly(totalPaidAmount)) {
            processBiYearly();
        } else if (isMonthly(totalPaidAmount)) {
            processMonthly();
        } else {
            processNoIdea();
        }

        return new PaymentDTO(this);
    }

    private boolean isYearly(int value) {
        return DoCompare.finData.isYearlyAmount(value / family.size());
    }

    private boolean isBiYearly(int value) {
        return DoCompare.finData.isHalfYearlyAmount(value / family.size());
    }

    private boolean isMonthly(int value) {
        return value % DoCompare.finData.singleKlassPayment() == 0
                || value % DoCompare.finData.singleKlassPaymentWithSibling() == 0;
    }

    private void processNoPay() {
        description = "Brak wpłat";
        var valueForOneKlass = family.size() == 1 ? DoCompare.finData.singleKlassPayment() : DoCompare.finData.singleKlassPaymentWithSibling();
        toPayForAllYearAmount = (family.size() * nZajec - family.getNumberUspraw()) * valueForOneKlass;
        needToPay = family.getNumberPaidKlasses() * valueForOneKlass;
    }

    private void processYearly() {
        description = "Wpłata Roczna";
        var valueForOneYear = family.size() == 1
                ? DoCompare.finData.getYearlyFor(family.getSchoolNr())
                : DoCompare.finData.getYearlyForSiblingsFor(family.getSchoolNr()) * family.size() / 2;
        toPayForAllYearAmount = valueForOneYear;
        //czasem placa za n-1 zajec jesli uczen opuscil jedne zajecia
        needToPay = toPayForAllYearAmount - totalPaidAmount;
        //czasem ktos wplaci za n-1, jak np ominie go pierwszy termin
        if (needToPay > 2) { 
            description = "Wpłata Roczna - zla ilosc zajec";
        }
    }

    private void processBiYearly() {
        description = "Wpłata semestralna";
        var valueForOneSemester = family.size() == 1
                ? DoCompare.finData.getHalfYearlyFor(family.getSchoolNr())
                : DoCompare.finData.getHalfYearlyForSiblingsFor(family.getSchoolNr()) * family.size() / 2;
        toPayForAllYearAmount = valueForOneSemester *2;
                if(ConfigF.isSecondSemester()) {
                    needToPay = toPayForAllYearAmount - totalPaidAmount;
                } else{
                    needToPay = valueForOneSemester - totalPaidAmount;
                }   
        //czasem ktos wplaci za n-1, jak np ominie go pierwszy termin, 100 powinno byc wiecej niz 
        if ((needToPay > 2) && (needToPay < 100)) { 
            description = "Wpłata Semestralna - zla ilosc zajec";
        } else if (needToPay >= 100 && !isBiYearly(needToPay)){
            description = "semestralna?"; //ToDo
        }
    }

    private void processMonthly() {
        description = "Wpłata miesieczna";
        var valueForOnecClass = family.size() == 1
                ? DoCompare.finData.singleKlassPayment()
                : DoCompare.finData.singleKlassPaymentWithSibling();
        toPayForAllYearAmount = getNumberOfClassesYearlyPerPupil(family.getSchoolNr())*family.size()  -  family.getNumberUspraw();
        needToPay = family.getNumberPaidKlasses()* valueForOnecClass - totalPaidAmount;
    }

    private void processNoIdea() {
        processNoPay(); //TODO - misieczne
        description = "Nie wiem, licze miesieczne";
    }

    private int getNumberOfClassesYearlyPerPupil(int schoolNr) {
        return DoCompare.finData.getNumberOfClassesForSchool(schoolNr); //To change body of generated methods, choose Tools | Templates.
    }

    @ToString
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
