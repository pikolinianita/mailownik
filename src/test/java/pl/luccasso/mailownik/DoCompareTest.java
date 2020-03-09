/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.util.LinkedList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.luccasso.mailownik.model.NewFamily;
import pl.luccasso.utils.SinglePupilBuilder;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author piko
 */

@ExtendWith(SoftAssertionsExtension.class)
public class DoCompareTest {
    
    @Test
    public void listOfStrangersTest(SoftAssertions softly){
        int size = 9;
        List<Pupil> list = new LinkedList<>(new SinglePupilBuilder().createSinglePupilList(size));
        var dc = new DoCompare();
        
        List<NewFamily> fams = dc.convertPupilListToFamilyList(list);
        
        softly.assertThat(list.size())
                .as("Wielkosc oryg listy")
                .isEqualTo( size);
        
        softly.assertThat(fams.size())
                .as("Wielkosc listy")
                .isEqualTo(size);
        
        
    }
    
    @Test
    public void listOfDoubleSiblingsTest(SoftAssertions softly){
         int size = 9;
        List<Pupil> list = new LinkedList<>(new SinglePupilBuilder().createSinglePupilList(size));
        list.addAll(new LinkedList<>(new SinglePupilBuilder().setFName("bob").createSinglePupilList(size)));
        var dc = new DoCompare();
        
        List<NewFamily> fams = dc.convertPupilListToFamilyList(list); 
        
        softly.assertThat(list.size())
                .as("Wielkosc oryg listy")
                .isEqualTo(2* size);
        
        softly.assertThat(fams.size())
                .as("Wielkosc listy")
                .isEqualTo(size);
        
        System.out.println(fams);
    }
    
}
