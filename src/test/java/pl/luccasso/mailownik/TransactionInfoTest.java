/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
    
}
