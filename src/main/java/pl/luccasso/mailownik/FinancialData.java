/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author piko
 */
public class FinancialData {

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

        /*
         * public SchoolPayments(int nZajec) {
         *
         * }
         */
    }

    int oneKlass = 35;
    int oneKlassWhenSibling = 33;
    //w tej szkole tyle trzeba płacić;
    Map<Integer, SchoolPayments> schoolToPaymentsMap;
    //Przy tylu zajęciach tyle tzreba płacić 
    Map<Integer, SchoolPayments> nKlassesToPaymentMap;
    List <Integer> siblingsValues; 

    public FinancialData() {
        schoolToPaymentsMap = new HashMap<>();
        nKlassesToPaymentMap = new HashMap<>();
        siblingsValues = new LinkedList<>();
    }
    
    public boolean isSiblingsValue(int val){
        if (siblingsValues.contains(val)) {
            return true;
        }
        if (val % oneKlassWhenSibling == 0) {
            return true;
        } else{
        return false;
        }
    }

    public FinancialData importPaymentPerKlasses(String filePatch) {
        FileReader fr = null;
        try {
            fr = new FileReader(filePatch);
            Scanner sc = new Scanner(fr);
            //skip header
            sc.nextLine();
            while (sc.hasNext()) {
                SchoolPayments sp = new SchoolPayments(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
                nKlassesToPaymentMap.put(sp.nZajec, sp);
                siblingsValues.add(sp.allYearWithSibling);
                siblingsValues.add(sp.oneSemesterWithSibling);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FinancialData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(FinancialData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this;
    }

    public FinancialData importschools(String filePatch) {
        FileReader fr = null;
        try {
            fr = new FileReader(filePatch);
            Scanner sc = new Scanner(fr);
            //skip header
            sc.nextLine();
            while (sc.hasNext()) {
                schoolToPaymentsMap.put(sc.nextInt(), nKlassesToPaymentMap.get(sc.nextInt()));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FinancialData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(FinancialData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this;
    }
}
