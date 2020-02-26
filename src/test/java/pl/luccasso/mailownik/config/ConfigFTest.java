/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.config;

import java.io.File;
import java.io.FileNotFoundException;
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
public class ConfigFTest {
    
    public ConfigFTest() {
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
     * Test of saveToFile method, of class ConfigF.
     */
    @Test
    public void testSaveToFile() throws Exception {
        System.out.println("saveToFile");
        ConfigF.readFromFile("e:/config.txt");
        
        
        ConfigF.saveToFile("e:/test/UnitTestConfig.txt");
        File f = new File("e:/test/UnitTestConfig.txt");
        assertEquals(true, f.isFile());
        assertEquals(130, f.length());
    }

    /**
     * Test of readFromFile method, of class ConfigF.
     */
    @Test
    public void testReadFromFile() throws Exception {
        System.out.println("readFromFile");
        
        ConfigF.bankPath = "";
        ConfigF.readFromFile("e:/config.txt");
        assertEquals("e:/test/listatestowa.csv", ConfigF.bankPath);
        
    }
    @Test
    public void testTestSettings() throws FileNotFoundException{
        System.out.println("TestSettings");
        
        //Given
        ConfigF.logFile = "";
        
        //When
        ConfigF.setTestConfig();
        
        //Then
        assertTrue(ConfigF.logFile.toLowerCase().contains("asiowytest"));
        
    }

        
}
