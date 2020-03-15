/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

/**
 *
 * @author piko
 */
public class FinancialDataTest {
    
    
    /**
     * Test of importPaymentPerKlasses method, of class FinancialData.
     */
         @Test
    public void testImportPaymentPerKlasses() {
       
        String filePatch = "testfiles/cenyvsnz.txt";
        FinancialData instance = new FinancialData();
                
        instance.importPaymentPerKlasses(filePatch);
        FinancialData.SchoolPayments classes13 = instance.nKlassesToPaymentMap.get(13);
        
        
        assertTrue(378 == classes13.allYear);
        assertTrue(189 == classes13.oneSemester);
        assertEquals(712,classes13.allYearWithSibling);
        assertEquals(356,classes13.oneSemesterWithSibling);
        
       
    }

    /**
     * Test of importschools method, of class FinancialData.
     */
    
    @Test
    public void testImportschools() {
        
        String filePatch = "testfiles/zajwszk.txt";
        FinancialData instance = new FinancialData()
                .importPaymentPerKlasses("testfiles/cenyvsnz.txt")
                .importschools(filePatch);
    assertEquals(465,instance.schoolToPaymentsMap.get(50).allYear);
    }
    
    
    @Test
    public void testSibValues(){
        
       var finData = new FinancialData()
                .importPaymentPerKlasses("testfiles/cenyvsnz.txt")
                .importschools("testfiles/zajwszk.txt");
        
        assertTrue(finData.isSiblingsValue(33));
        assertTrue(finData.isSiblingsValue(99));
        assertTrue(finData.isSiblingsValue(931));
        assertFalse(finData.isSiblingsValue(100));
    }
    
     @Test
    public void testSchoolToNklass(){
        
       var finData = new FinancialData()
                .importPaymentPerKlasses("testfiles/cenyvsnz.txt")
                .importschools("testfiles/zajwszk.txt");
       assertEquals(16,finData.getNumberOfClassesForSchool(50));
       
    }
    
    
    @Disabled //jtak zebym pamietal
    @Test
    public void testImportFromRes(){
        System.out.println("resourceTest");
        var s = getClass().getClassLoader().getResourceAsStream("/cenywsz.txt");
        System.out.println(s);
        s = getClass().getClassLoader().getResourceAsStream("resources/cenywsz.txt");
        System.out.println(s);
        s = getClass().getClassLoader().getResourceAsStream("/resources/cenywsz.txt");
        System.out.println(s);
        s = getClass().getClassLoader().getResourceAsStream("cenywsz.txt");        
        System.out.println(s);
        var sc = new Scanner(s);
        
        s = getClass().getClassLoader().getResourceAsStream("resources/cenywsz.txt");
        System.out.println(s);
        s = getClass().getClassLoader().getResourceAsStream("/test/resources/cenywsz.txt");
        System.out.println(s);
        s = getClass().getClassLoader().getResourceAsStream("test/resources/cenywsz.txt");
        System.out.println(s);
        s = getClass().getClassLoader().getResourceAsStream("src/test/resources/cenywsz.txt");
        System.out.println(s);
        s = getClass().getClassLoader().getResourceAsStream("/src/test/resources/cenywsz.txt");
        System.out.println(s);
        
        
        while (sc.hasNext()){
            System.out.println(sc.nextLine());
        }
    }
    
}
