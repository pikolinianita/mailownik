/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static pl.luccasso.mailownik.DoCompare.finData;

/**
 *
 * @author piko
 */
public class DoCompareTest {
    
    public DoCompareTest() {
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
     * Test of main method, of class DoCompare.
     */
    @Test
    public void testLoadPrevous() {
        System.out.println("main");
        //String[] args = null;
        DoCompare dc = new DoCompare();
        dc.finData = new FinancialData()
                .importPaymentPerKlasses("e:/cenyvsnz.txt")
                .importschools("e:/zajwszk.txt");
        dc.pupilList = dc.loadPreviousData("e:/output a.txt");
        dc.leftOvers = new LinkedList<>();
        dc.humanToDecide = new HashMap<>();
        dc.siblings = new LinkedList<>();
        dc.wrongLines = new LinkedList<>();
        dc.save();
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
    
}
