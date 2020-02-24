/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class PupilTest {
    
    public PupilTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        try(var f = new BufferedReader(new FileReader("e:/Asiowytest/output.txt"))){
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PupilTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PupilTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
     * Test of toString method, of class Pupil.
     */
    @Test
    public void testToString() {
    }

    /**
     * Test of hashCode method, of class Pupil.
     */
    @Test
    public void testHashCode() {
    }

    /**
     * Test of equals method, of class Pupil.
     */
    @Test
    public void testEquals_Object() {
    }

    /**
     * Test of equals method, of class Pupil.
     */
    @Test
    public void testEquals_GAppsParserPupilImport() {
    }

    /**
     * Test of isMyfNameHere method, of class Pupil.
     */
    @Test
    public void testIsMyfNameHere() {
    }

    /**
     * Test of isMylNameHere method, of class Pupil.
     */
    @Test
    public void testIsMylNameHere() {
    }

    /**
     * Test of isMyKlass method, of class Pupil.
     */
    @Test
    public void testIsMyKlass() {
    }

    /**
     * Test of isMySchool method, of class Pupil.
     */
    @Test
    public void testIsMySchool() {
    }

    /**
     * Test of isMySchoolHere method, of class Pupil.
     */
    @Test
    public void testIsMySchoolHere() {
    }

    /**
     * Test of isMyKlassHere method, of class Pupil.
     */
    @Test
    public void testIsMyKlassHere() {
    }

    /**
     * Test of getFileLine method, of class Pupil.
     */
    @Test
    public void testGetFileLine() {
    }

    /**
     * Test of getShortUniqueString method, of class Pupil.
     */
    @Test
    public void testGetShortUniqueString() {
    }

    /**
     * Test of compareTo method, of class Pupil.
     */
    @Test
    public void testCompareTo() {
    }
    
}
