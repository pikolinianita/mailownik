/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.model;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.luccasso.mailownik.DoCompare;
import pl.luccasso.mailownik.config.ConfigF;

/**
 *
 * @author piko
 */

@ExtendWith(MockitoExtension.class)
public class PaymentsResolverTest {

    DoCompare dc; 
    
    @Mock
    NewFamily family;

    @BeforeAll
    public static void setUpClass() {
        
    }

    @BeforeEach
    public void setUp() {
        System.out.println("1: " + ConfigF.getPayPerClass());
        dc = new DoCompare();
        try {
            
            ConfigF.restoreCleanTestConfiguration();
            System.out.println("2: " + ConfigF.getPayPerClass());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewFamilyTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Problem with restore clean configuration");
        }
          
        
        System.out.println("db " + dc.getDataBase());
        System.out.println("3: " + ConfigF.getPayPerClass());
        dc.loadStuff();
        //System.out.println("DC: " + dc);    
        System.out.println("db " + dc.getDataBase());   
    }

    @Test
    public void testResolveNoPaySingle() {

        try{
       when(family.size()).thenReturn(1);
       when(family.getSchoolNr()).thenReturn(50);
       when(family.getSumOfPayments()).thenReturn(0);
       when(family.getNumberPaidKlasses()).thenReturn(10);
       when(family.getNumberUspraw()).thenReturn(2);
       
       var resultToDo = new PaymentsResolver(family);
       var result = resultToDo.moneyResolve();
       
               
       
       var softly = new SoftAssertions() ;
       softly.assertThat(result.description()).as("No Payments").isEqualTo("Brak wpłat");
       softly.assertThat(result.nZajec).as("Number of classes").isEqualTo(16);
       softly.assertThat(result.needToPay).as("Need to pay").isEqualTo(350);
       softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(490);
       
       softly.assertAll();
        }
        catch (Exception e){
            e.printStackTrace();
    }
    }
    
     @Test
    public void testResolveNoPayDouble() {

       when(family.size()).thenReturn(2);
       when(family.getSchoolNr()).thenReturn(50);
       when(family.getSumOfPayments()).thenReturn(0);
       when(family.getNumberPaidKlasses()).thenReturn(10);
       when(family.getNumberUspraw()).thenReturn(2);
       
       var result = new PaymentsResolver(family).moneyResolve();
       var softly = new SoftAssertions() ;
       softly.assertThat(result.description()).as("No Payments").isEqualTo("Brak wpłat");
       softly.assertThat(result.nZajec).as("Number of classes").isEqualTo(16);
       softly.assertThat(result.needToPay).as("Need to pay").isEqualTo(330);
       softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(990);
       
       softly.assertAll();
    }
    
    @Test
    public void testResolveNoPayTriple() {

       when(family.size()).thenReturn(3);
       when(family.getSchoolNr()).thenReturn(50);
       when(family.getSumOfPayments()).thenReturn(0);
       when(family.getNumberPaidKlasses()).thenReturn(10);
       when(family.getNumberUspraw()).thenReturn(2);
       
       var result = new PaymentsResolver(family).moneyResolve();
       var softly = new SoftAssertions() ;
       softly.assertThat(result.description()).as("No Payments").isEqualTo("Brak wpłat");
       softly.assertThat(result.nZajec).as("Number of classes").isEqualTo(16);
       softly.assertThat(result.needToPay).as("Need to pay").isEqualTo(330);
       softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(1518);
       
       softly.assertAll();
    }
    
    @ParameterizedTest
    @ValueSource(ints = {465,140})
    public void testresolveYearlySingle(int value){
       
       when(family.getSchoolNr()).thenReturn(50);
       when(family.getSumOfPayments()).thenReturn(value);
       when(family.size()).thenReturn(1);
       
       var result = new PaymentsResolver(family).moneyResolve();
    }

}
