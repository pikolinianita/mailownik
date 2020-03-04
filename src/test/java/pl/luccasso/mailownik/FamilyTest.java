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
    SinglePupil p1;
    SinglePupil p2;
    SinglePupilBuilder pBuilder;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
         pBuilder = new SinglePupilBuilder();
         p1 = pBuilder.setLName("Nowak").setEMail("NT@Gmail").setNTel("501100100").setNTel2("504200200").createSinglePupil();
         p2 = pBuilder.setFName("Olga").setCount(100).createSinglePupil();
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCreateFamilyOfTwo() {
                      
        var f = new Family().add(p1).add(p2);
        assertEquals(2,f.size());
        }
    
    @Test
    public void testAccountsMerge(){
        p1.addAccount("mamy").addAccount("taty").addAccount("Fladry");
        p2.addAccount("mamy").addAccount("taty").addAccount("Gacha");
        var f = new Family().add(p1).add(p2);
        assertEquals(4, f.getAccountNrs().size());
        assertTrue(f.getAccountNrs().contains("Fladry"));
        assertTrue(f.getAccountNrs().contains("Gacha"));
    }
    
}
