/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static pl.luccasso.mailownik.DoCompare.finData;
import pl.luccasso.mailownik.config.ConfigF;

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
    public void tearDown()  {
        try {
            ConfigF.restoreCleanTestConfiguration();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DoCompareTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erron in ConfigF.restoreCleanTestConfiguration() klase doCompareTest");
            throw new RuntimeException("Erron in ConfigF.restoreCleanTestConfiguration() klase doCompareTest",ex);
        }
    }

    /**
     * Test of main method, of class DoCompare.
     */
    @Test
    public void testLoadPrevous() {
       /* System.out.println("main");
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
        dc.save();*/
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testLoadStuff(){
        System.out.println("------------------testLoadStuff--------------");
         var dc = new DoCompare();
         dc.loadStuff();
        // System.out.println(dc.listaTransakcji);
        // System.out.println(dc.wrongLines);
         System.out.println(dc.pupilList);
         for (var p : dc.pupilList){
            System.out.println(p.getShortUniqueString());
    }
         assertEquals(15,dc.pupilList.size());
    }
    
    @Test
    public void testLoadPrevData(){
        System.out.println("------------------testLoadPrevData--------------");
        var dc = new DoCompare();
        var pupilList = dc.loadPreviousData(ConfigF.getSavedPath());
        for (var p : pupilList){
            System.out.println(p.getShortUniqueString());
        }
        assertEquals(15,pupilList.size());
    
        pupilList = new DoCompare().loadPreviousData("e:/asiowytest/outputtstless.txt");
        assertEquals(13,pupilList.size());
    }
    
    @Test
    public void testLoadSomeFromOutputAndSomeNewFromGoogle(){
        System.out.println("------------------testLoadSomeFromOutputAndSomeNewFromGoogle()--------------");
        ConfigF.setSavedPath("e:/asiowytest/outputtstless.txt");
        
        var dc = new DoCompare();
        dc.loadStuff();
        
        assertEquals(15,dc.pupilList.size());
        assertEquals(15,new HashSet(dc.pupilList).size());
    }
    
    @Test
    public void testLoadSomeFromOutputAndSomeNewFromGoogle_WithOutputSchoolAltered(){
        System.out.println("------------------testLoadSomeFromOutputAndSomeNewFromGoogle_897()--------------");
        ConfigF.setSavedPath("e:/asiowytest/output15SP897.txt");
        
        var dc = new DoCompare();
        dc.loadStuff();
        
        long hasShoolNr897 = dc.pupilList.stream()
                .filter( p-> p.isMySchool(897))
                .count();
        assertEquals(13,hasShoolNr897);
    }
    @Test
    public void testLoadSomeFromOutputAndSomeNewFromGoogle_AllUnique(){
        System.out.println("------------------testLoadSomeFromOutputAndSomeNewFromGoogle_AllUnique()--------------");
        ConfigF.setSavedPath("e:/asiowytest/output15SkrIdChanged.txt");
        
        var dc = new DoCompare();
        dc.loadStuff();
        
        assertEquals(28,dc.pupilList.size());
    }
    
}
