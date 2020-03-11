/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.luccasso.mailownik.calculations.FamilyTransactionMatcherTest;
import pl.luccasso.mailownik.config.ConfigF;
import pl.luccasso.mailownik.model.NewFamily;
import pl.luccasso.utils.SinglePupilBuilder;


/**
 *
 * @author piko
 */
@Disabled
@ExtendWith(SoftAssertionsExtension.class)
public class DoCompareTest {
    
    @Test
    public void listOfStrangersTest(SoftAssertions softly){
        int size = 9;
        List<Pupil> list = new LinkedList<>(new SinglePupilBuilder().createSinglePupilList(size));
        var dc = new DoCompare();
        
        List<NewFamily> fams = dc.dataBase.convertPupilListToFamilyList(list);
        
        softly.assertThat(list.size())
                .as("Wielkosc oryg listy")
                .isEqualTo(size);
        
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
        
        List<NewFamily> fams = dc.dataBase.convertPupilListToFamilyList(list); 
        
        softly.assertThat(list.size())
                .as("Wielkosc oryg listy")
                .isEqualTo(2* size);
        
        softly.assertThat(fams.size())
                .as("Wielkosc listy")
                .isEqualTo(size);
        
        System.out.println(fams);
    }
    
    @Test
    public void listFromFileTest(SoftAssertions softly){
        try {
            ConfigF.restoreCleanTestConfiguration();
            ConfigF.readConfigFromFile("e:/asiowytest/config.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FamilyTransactionMatcherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       var dc = new DoCompare();
       dc.loadStuff();
       var db = dc.getDataBase();
       db.makeStructures();
       
        System.out.println(db.pupilList().size());
        db.convertPupilListToFamilyList(db.pupilList());
        System.out.println(db.neuFamilyList().size());
        for(var nf: db.neuFamilyList()){
            System.out.println("Oto Rodzina:");
            System.out.println(Arrays.toString(nf.getFileLines()));
        }
            
    }
}

