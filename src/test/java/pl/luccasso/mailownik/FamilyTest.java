/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.luccasso.utils.SinglePupilBuilder;

/**
 *
 * @author piko
 */
public class FamilyTest {
    
    public FamilyTest() {
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

    @Test
    public void createFamilyOfTwo() {
        var pBuilder = new SinglePupilBuilder();
        var p1 = pBuilder.setLName("Nowak").setEMail("NT@Gmail").setNTel("501100100").setNTel2("504200200").createSinglePupil();
        var p3 = pBuilder.setFName("Olga").setCount(100).createSinglePupil();
                
        var f = new Family().add(p1).add(p3);
        assertEquals(2,f.size());
        
        
    }
    
}
