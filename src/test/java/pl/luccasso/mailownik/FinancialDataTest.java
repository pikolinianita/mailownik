/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    @Test
    public void testImportPaymentPerKlasses() {
        System.out.println("importPaymentPerKlasses");
        String filePatch = "e:/cenyvsnz.txt";
        FinancialData instance = new FinancialData();
        FinancialData expResult = null;
        FinancialData result = instance.importPaymentPerKlasses(filePatch);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(instance));
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of importschools method, of class FinancialData.
     */
    @Test
    public void testImportschools() {
        System.out.println("importschools");
        String filePatch = "e:/zajwszk.txt";
        FinancialData instance = new FinancialData().importPaymentPerKlasses("e:/cenyvsnz.txt");
        FinancialData expResult = null;
        FinancialData result = instance.importschools(filePatch);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(instance));
        
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
