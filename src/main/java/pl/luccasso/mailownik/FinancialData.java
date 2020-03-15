/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 *
 * @author piko
 */
public class FinancialData {

    @Accessors(fluent = true, chain = true)
    @Getter()
    int singleKlassPayment = 35;

    @Accessors(fluent = true, chain = true)
    @Getter()
    int singleKlassPaymentWithSibling = 33;

    //w tej szkole tyle trzeba płacić;
    Map<Integer, SchoolPayments> schoolToPaymentsMap;

    //Przy tylu zajęciach tyle tzreba płacić 
    Map<Integer, SchoolPayments> nKlassesToPaymentMap;

    //
    Map<Integer, Integer> schoolToNKlassesMap;

    List<Integer> siblingsValues;

    List<Integer> NotSiblingsValues;

    List<Integer> yearlyValues;

    List<Integer> halfYearlyValues;

    public int getNumberOfClassesForSchool(int schoolNr) {
        return schoolToNKlassesMap.get(schoolNr);
    }

    public boolean isYearlyAmount(int amount) {
        return yearlyValues.contains(amount);
    }

    public boolean isHalfYearlyAmount(int amount) {
        return halfYearlyValues.contains(amount);
    }

   public  int getYearlyFor (int school){
        return schoolToPaymentsMap.get(school).allYear;
    }
    
    public int getYearlyForSiblingsFor (int school){
        return schoolToPaymentsMap.get(school).allYearWithSibling;
    }
    
    public  int getHalfYearlyFor (int school){
        return schoolToPaymentsMap.get(school).oneSemester;
    }
    
    public int getHalfYearlyForSiblingsFor (int school){
        return schoolToPaymentsMap.get(school).oneSemesterWithSibling;
    }
            
    /**
     * Klasa zbierająca roczne i półroczne opłaty dla szkoły.
     */
    class SchoolPayments {

        int allYear;

        int oneSemester;

        int allYearWithSibling;

        int oneSemesterWithSibling;

        int nZajec;

        public SchoolPayments(int nZajec, int allYear, int oneSemester, int allYearWithSibling, int oneSemesterWithSibling) {
            this.allYear = allYear;
            this.oneSemester = oneSemester;
            this.allYearWithSibling = allYearWithSibling;
            this.oneSemesterWithSibling = oneSemesterWithSibling;
            this.nZajec = nZajec;
        }

    }

    public FinancialData() {
        schoolToPaymentsMap = new HashMap<>();
        nKlassesToPaymentMap = new HashMap<>();
        schoolToNKlassesMap = new HashMap<>();
        siblingsValues = new LinkedList<>();
        NotSiblingsValues = new LinkedList<>();
        yearlyValues = new LinkedList<>();
        halfYearlyValues = new LinkedList<>();

    }

    public boolean isSiblingsValue(int val) {
        if (siblingsValues.contains(val)) {
            return true;
        }
        if (val % singleKlassPaymentWithSibling == 0) {
            return true;
        } else {
            return false;
        }
    }

    public FinancialData importPaymentPerKlasses(String filePatch) {

        try (var fr = new FileReader(filePatch);
                Scanner sc = new Scanner(fr);) {
            //skip header
            sc.nextLine();
            while (sc.hasNext()) {
                SchoolPayments sp = new SchoolPayments(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
                nKlassesToPaymentMap.put(sp.nZajec, sp);
                siblingsValues.add(sp.allYearWithSibling);
                siblingsValues.add(sp.oneSemesterWithSibling);
                NotSiblingsValues.add(sp.oneSemester);
                NotSiblingsValues.add(sp.allYear);
                yearlyValues.add(sp.allYear);
                yearlyValues.add(sp.allYearWithSibling);
                halfYearlyValues.add(sp.oneSemester);
                halfYearlyValues.add(sp.oneSemesterWithSibling);
            }
            siblingsValues.sort(null);
            NotSiblingsValues.sort(null);
        } catch (IOException ex) {  //includes File Not Found Exception
            Logger.getLogger(FinancialData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    public FinancialData importschools(String filePatch) {
        try (var fr = new FileReader(filePatch);
                Scanner sc = new Scanner(fr);) {

            //skip header
            sc.nextLine();
            while (sc.hasNext()) {
                int school = sc.nextInt();
                int klassCount = sc.nextInt();
                schoolToPaymentsMap.put(school, nKlassesToPaymentMap.get(klassCount));
                schoolToNKlassesMap.put(school, klassCount);
            }
        } catch (IOException ex) { //includes File Not Found Exception
            Logger.getLogger(FinancialData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
}
