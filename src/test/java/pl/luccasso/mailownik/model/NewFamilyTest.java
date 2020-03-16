/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.luccasso.mailownik.model;

import java.io.FileNotFoundException;
import java.util.Arrays;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import pl.luccasso.utils.SinglePupilBuilder;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.SinglePupil;
import pl.luccasso.mailownik.config.ConfigF;
import pl.luccasso.utils.TransactionStringBuilder;


/**
 *
 * @author piko
 */

@ExtendWith(SoftAssertionsExtension.class)
public class NewFamilyTest {   
    
    private SinglePupil CreateSinglePupilWithOneTransaction() {
        SinglePupilBuilder sp = new SinglePupilBuilder();
        var transaction = new BankTransaction(new TransactionStringBuilder().create());
        var list = List.of(transaction);
        var pupil = sp.createSinglePupil();
        pupil.processTransactions(list);
        return pupil;
    }
    
    @BeforeEach
    public void setUp() {
        try {
            ConfigF.restoreCleanTestConfiguration();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewFamilyTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Problem with restore clean configuration");
        }
    }
    
    
    @Test
    public void testSingleFamily(SoftAssertions softly) {
        SinglePupilBuilder sp = new SinglePupilBuilder();
        
        var family = new NewFamily(sp.createSinglePupil());
        
        softly.assertThat(family.childrens().list().get(0).fName())
                .as("First Name")
                .isEqualTo("Adam100");
        softly.assertThat(family.childrens().list().get(0).lName())
                .as("Last Name")
                .isEqualTo("Byk100");
        softly.assertThat(family.childrens().list().get(0).id().id()).as("Id").isEqualTo(500);
        softly.assertThat(family.contacts().nTel()).as("tel").isEqualTo("500000000100");
        softly.assertThat(family.contacts().eMail()).as("email").isEqualTo("AdamByk@def.pl100");
        
        System.out.println(family);
    }
    
    @Test
    public void testSiblings(SoftAssertions softly){
        var spBuilder = new SinglePupilBuilder();
        var sp1 = spBuilder.createSinglePupil();
        var sp1a = new SinglePupilBuilder().createSinglePupil();
        var sp2 = spBuilder.setCount(101).createSinglePupil();
        var family = new NewFamily(sp1);
        
        System.out.println("sp1:" + sp1);
        System.out.println("sp1a:" + sp1a);
        System.out.println("sp2:" + sp2);
        
        softly.assertThat(family.isMyBrother(sp1a)).as("Should be brother").isTrue();
        softly.assertThat(family.isMyBrother(sp2)).as("Should not be a brother").isFalse();
    }
    
    @Test
    public void testAddBrother(SoftAssertions softly){
        var spBuilder = new SinglePupilBuilder();
        var sp1 = spBuilder.setFName("Bob").createSinglePupil();
        var sp1a = new SinglePupilBuilder().createSinglePupil();
        
        NewFamily family = new NewFamily(sp1).add(sp1a);
        
        softly.assertThat(family.size()).as("family size").isEqualTo(2);
        softly.assertThat(family.childrens().list()).as("are good names")
                .extracting("fName")
                .contains("Bob100","Adam100");
         // System.out.println(Arrays.toString(family.getFileLines()));
    }
    
    //@Disabled //ToDO - NewFamily!
    @Test
    public void testAddTransactionInfo(SoftAssertions softly){
         SinglePupil pupil = CreateSinglePupilWithOneTransaction();
         
         NewFamily family = new NewFamily(pupil);
         
         softly.assertThat(family.totalPayments()).as("Total payments").isEqualTo(70);
         softly.assertThat(family.allAccounts()).as("accounts list").contains("1234567890");
        // System.out.println(Arrays.toString(family.getFileLines()));
    }
    
    //@Disabled
    @Test
    public void testAddBrotherWithSameTI(SoftAssertions softly){
         SinglePupil pupil = CreateSinglePupilWithOneTransaction();
         SinglePupil pupil2 = CreateSinglePupilWithOneTransaction();
         
         NewFamily family = new NewFamily(pupil);
         family.add(pupil2);
         
         softly.assertThat(family.totalPayments()).as("Total payments").isEqualTo(70);
         softly.assertThat(family.allAccounts()).as("accounts list").hasSize(1);
         softly.assertThat(family.size()).as("size of family").isEqualTo(2);
         softly.assertThat(family.payments().transactions()).as("number of TI").hasSize(1);
         
    }
    
     @Test 
        public void testAddBrotherWithOtherTI(SoftAssertions softly){
         SinglePupil pupil = CreateSinglePupilWithOneTransaction();
         SinglePupil pupil2 = CreateSinglePupilWithOneTransaction();
         var transaction = new BankTransaction(new TransactionStringBuilder()
                 .pln("170,00 PLN")
                 .create());
         
         pupil2.processTransactions(List.of(transaction));
         
         NewFamily family = new NewFamily(pupil);
         
         family.add(pupil2);
         
         softly.assertThat(family.totalPayments()).as("Total payments").isEqualTo(240);
         softly.assertThat(family.allAccounts()).as("accounts list").hasSize(1);
         softly.assertThat(family.size()).as("size of family").isEqualTo(2);
    }
}
