/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author piko
 */
public class SinglePupilBuilderTest {
    
    public SinglePupilBuilderTest() {
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
    public void testCreateSinglePupil() {
        var p = new SinglePupilBuilder().createSinglePupil();
        assertEquals("Adam100",p.getFName());
        assertEquals("Byk", p.getLName());
        assertEquals("500000000", p.getNTel());
    }
    
    
    @ParameterizedTest(name = "male_listy")
    @ValueSource (ints = {1,2,3,4})
    public void testSizeOfPupilLists(int n){
        var lp = new SinglePupilBuilder().createSinglePupilList(n);
        assertEquals(n, lp.size());
        System.out.println(lp);
        System.out.println("");
    }
}
