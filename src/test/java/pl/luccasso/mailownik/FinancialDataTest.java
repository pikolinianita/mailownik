/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URL;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

/**
 *
 * @author piko
 */
public class FinancialDataTest {
    
    public FinancialDataTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of importPaymentPerKlasses method, of class FinancialData.
     */
     @Disabled
    @Test
    public void testImportPaymentPerKlasses() {
        System.out.println("importPaymentPerKlasses");
        String filePatch = "e:/AsiowyTest/cenyvsnz.txt";
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
    @Disabled
    @Test
    public void testImportschools() {
        System.out.println("importschools");
        String filePatch = "e:/AsiowyTest/zajwszk.txt";
        FinancialData instance = new FinancialData().importPaymentPerKlasses("e:/AsiowyTest/cenyvsnz.txt").importschools(filePatch);
        
        
       
    }
    
    @Disabled
    @Test
    public void testSibValues(){
       var finData = new FinancialData()
                .importPaymentPerKlasses("e:/cenyvsnz.txt")
                .importschools("e:/zajwszk.txt");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(finData));
        System.out.println(finData.isSiblingsValue(33));
        System.out.println(finData.isSiblingsValue(99));
        System.out.println(finData.isSiblingsValue(931));
    }
    
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
