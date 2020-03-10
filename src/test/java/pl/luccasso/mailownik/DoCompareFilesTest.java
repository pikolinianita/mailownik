/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import static pl.luccasso.mailownik.DoCompare.finData;
import pl.luccasso.mailownik.config.ConfigF;

/**
 *
 * @author piko
 */
@Disabled
public class DoCompareFilesTest {
    
    public DoCompareFilesTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        try {
            ConfigF.restoreCleanTestConfiguration();
        } catch (FileNotFoundException ex) {
            System.out.println("Erron in ConfigF.restoreCleanTestConfiguration() klase doCompareTest");
        }
    }
    
    @AfterEach
    public void tearDown()  {
        try {
            ConfigF.restoreCleanTestConfiguration();
        } catch (FileNotFoundException ex) {
            System.out.println("Erron in ConfigF.restoreCleanTestConfiguration() klase doCompareTest");
        }
    }

        

    @Test
    public void testLoadStuff(){
        System.out.println("------------------testLoadStuff--------------");
         var dc = new DoCompare();
         dc.loadStuff();
        // System.out.println(dc.listaTransakcji);
        // System.out.println(dc.wrongLines);
         System.out.println(dc.dataBase.pupilList());
         for (var p : dc.dataBase.pupilList()){
            System.out.println(p.getShortUniqueString());
    }
         assertEquals(15,dc.dataBase.pupilList().size());
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
    
        pupilList = new DoCompare().loadPreviousData("testfiles/autputtstless.txt");
        assertEquals(13,pupilList.size());
    }
    
    @Test
    public void testLoadSomeFromOutputAndSomeNewFromGoogle(){
        System.out.println("------------------testLoadSomeFromOutputAndSomeNewFromGoogle()--------------");
        ConfigF.setSavedPath("testfiles/autputtstless.txt");
        
        var dc = new DoCompare();
        dc.loadStuff();
        
        assertEquals(15,dc.dataBase.pupilList().size());
        assertEquals(15,new HashSet(dc.dataBase.pupilList()).size());
    }
    
    @Test
    public void testLoadSomeFromOutputAndSomeNewFromGoogle_WithOutputSchoolAltered(){
        System.out.println("------------------testLoadSomeFromOutputAndSomeNewFromGoogle_897()--------------");
        ConfigF.setSavedPath("testfiles/autput897.txt");
        
        var dc = new DoCompare();
        dc.loadStuff();
        
        long hasShoolNr897 = dc.dataBase.pupilList().stream()
                .filter( p-> p.isMySchool(897))
                .count();
        assertEquals(13,hasShoolNr897);
    }
    @Test
    public void testLoadSomeFromOutputAndSomeNewFromGoogle_AllUnique(){
        System.out.println("------------------testLoadSomeFromOutputAndSomeNewFromGoogle_AllUnique()--------------");
        ConfigF.setSavedPath("testfiles/autputchangedid.txt");
        
        var dc = new DoCompare();
        dc.loadStuff();
        
        assertEquals(28,dc.dataBase.pupilList().size());
        System.out.println(dc);
    }
    
}
