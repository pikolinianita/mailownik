/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.luccasso.utils.TransactionStringBuilder;

/**
 *
 * @author piko
 */
@ExtendWith(SoftAssertionsExtension.class)
public class TransactionInfoTest {
    
    public TransactionInfoTest() {
    }

    @Test
    public void testConstructor(SoftAssertions softly) {
        var line = new TransactionStringBuilder().create();
        var transaction = new BankTransaction(line);
        
        var info = new TransactionInfo(transaction);
        
        softly.assertThat(info.account).as("account").isEqualTo(transaction.account);
        softly.assertThat(info.amount).as("amount").isEqualTo(transaction.amount);
        softly.assertThat(info.title).as("title").isEqualTo(transaction.title);
        softly.assertThat(info.date).as("date").isEqualTo(transaction.date);
    }
 
    @Test
    public void testSameAsTransaction(){
        var line = new TransactionStringBuilder().create();
        var transaction = new BankTransaction(line);
        var ti1 = new TransactionInfo(transaction);
                
        assertThat(ti1.sameAs(transaction)).as("shoud be equal").isTrue();
    }
    
    @Test
    public void testSameAsTInfo(){
        var line = new TransactionStringBuilder().create();
        var transaction = new BankTransaction(line);
        var ti1 = new TransactionInfo(transaction);
        var ti2 = new TransactionInfo(transaction);        
        
        assertThat(ti1.sameAs(ti2)).as("shoud be equal").isTrue();
    }
    
    @Test
    public void testNotSameAsTransaction(){
        var line = new TransactionStringBuilder().create();
        var transaction = new BankTransaction(line);
        var ti1 = new TransactionInfo(transaction);
        
        transaction.account= "123";
        
        assertThat(ti1.sameAs(transaction)).as("shoud be equal").isFalse();
    }
    
    @Test
    public void testNotSameAsTInfo(){
        var line = new TransactionStringBuilder().create();
        var transaction = new BankTransaction(line);
        var ti1 = new TransactionInfo(transaction);
        
        transaction.account= "123";
        
        var ti2 = new TransactionInfo(transaction);        
        
        assertThat(ti1.sameAs(ti2)).as("shoud be equal").isFalse();
    }
}
