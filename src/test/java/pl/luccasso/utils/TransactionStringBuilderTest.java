/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 *
 * @author piko
 */
@ExtendWith(SoftAssertionsExtension.class)
public class TransactionStringBuilderTest {    
    

    @Test
    public void testAutoCreate() {
        var fileLine = new TransactionStringBuilder().create();
        
        assertThat(fileLine).as("Line Created").contains("Heniek Kurek");
    }
    
    @Test
    public void testTitleChanges(SoftAssertions softly){
         var fileLine = new TransactionStringBuilder().title("kicha na resorach").create();
         
         softly.assertThat(fileLine).as("zawiera zmiane").contains("kicha");
         softly.assertThat(fileLine).as("nie ma oryg").doesNotContain("Heniek", "heniek");
         
    }
}
