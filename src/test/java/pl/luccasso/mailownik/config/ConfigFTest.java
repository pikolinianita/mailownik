/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author piko
 */
public class ConfigFTest {
    
        
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
        try {
            ConfigF.restoreCleanTestConfiguration();
        } catch (FileNotFoundException ex) {
            System.out.println("Erron in ConfigF.restoreCleanTestConfiguration() klase doCompareTest");  
        }
    }
    
    
    /**
     * Test of saveToFile method, of class ConfigF.
     * @throws java.lang.Exception
     */
    @Test
    public void testSaveToFile() throws Exception {
        System.out.println("saveToFile");
        ConfigF.readConfigFromFile("testfiles/config.txt");
        
        
        ConfigF.saveToFile("testfiles/UnitTestConfig.txt");
        File f = new File("testfiles/UnitTestConfig.txt");
        assertEquals(true, f.isFile());
        //assertEquals(167, f.length()); //161 na linuxie
    }

    /**
     * Test of readConfigFromFile method, of class ConfigF.
     */
    @Test
    public void testReadFromFile() throws Exception {
        System.out.println("readFromFile");
        
        ConfigF.bankPath = "";
        ConfigF.readConfigFromFile("testfiles/config.txt");
        assertEquals("testfiles/listatestowa14b.csv", ConfigF.bankPath);
        
    }
    @Test
    public void testTestSettings() throws FileNotFoundException{
        System.out.println("TestSettings");
        
        //Given
        ConfigF.logFile = "";
        
        //When
        ConfigF.restoreCleanTestConfiguration();
        
        //Then
        assertTrue(ConfigF.logFile.toLowerCase().contains("testfiles"));
        
    }

    @Test
    public void testJson() throws IOException {
        ConfigF.setLogFile("kokoszka");
        ConfigF.saveToJsonFile("testfiles/fakejsonTxt.txt");
        File f = new File("testfiles/fakejsonTxt.txt");
        assertEquals(true, f.isFile());
        
    }
    @Test
    public void testReadJson() throws IOException {
        ConfigF.setLogFile("kokoszka");
        
        ConfigF.saveToJsonFile("testfiles/fakejsonTxt.txt");
        ConfigF.setLogFile("kukuruznik");        
        assertEquals("kukuruznik", ConfigF.getLogFile());
        
        ConfigF.readFromJsonFile("testfiles/fakejsonTxt.txt");
        assertEquals("kokoszka", ConfigF.getLogFile());
        
        //CleanUP
        ConfigF.restoreCleanTestConfiguration();
    }
    
}
