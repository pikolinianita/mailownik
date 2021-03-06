/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author piko
 */
public class SinglePupilBuilderTest {
        

    @Test
    public void testCreateSinglePupil() {
        var p = new SinglePupilBuilder().createSinglePupil();
        assertEquals("Adam100",p.getFName());
        assertEquals("Byk100", p.getLName());
        assertEquals("500000000100", p.getNTel());
    }
    
    
    @ParameterizedTest(name = "male_listy")
    @ValueSource (ints = {1,2,3,40})
    public void testSizeOfPupilLists(int n){
        var lp = new SinglePupilBuilder().createSinglePupilList(n);
        assertEquals(n, lp.size());
         }
}
