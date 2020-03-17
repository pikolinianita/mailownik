/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.luccasso.mailownik.config.ConfigF;
import pl.luccasso.utils.TransactionStringBuilder;

/**
 *
 * @author piko
 */

@ExtendWith(SoftAssertionsExtension.class)
public class BankFileParserTest {
    
    
    /**
     * Test of getListaTransakcji method, of class BankFileParser.
     */
    @Disabled
    @Test
    public void testGetListaTransakcji() throws FileNotFoundException {
        
        ConfigF.restoreCleanTestConfiguration();
        System.out.println("------------------testGetListaTransakcji--------------");
        
        var bp = new BankFileParser(ConfigF.getBankPath());        
        assertEquals(15 , bp.listaTransakcji.size());
        
    }
    
   /* 
   @Test   
    public void testOldFileListaTransakcji(){
        String[]strings1 = {"e:/asiowytest/listatestowa14.csv"
                         , "e:/asiowytest/listatestowa14a.csv"
                         , "e:/asiowytest/listatestowa14b.csv"
 //                        , "e:/listatestowa2.csv"
 //                        , "e:/listatestowaold.csv"
                         , "e:/asiowytest/listatestowa14c.csv"};
        for (var strings : strings1) {
           
           System.out.println("tst--- " + strings);
           var bp = new BankFileParser(strings);
           System.out.println(bp.listaTransakcji);
           System.out.println("tst--- " + strings);
       }
    }*/
    
    @Test
    public void testWrongFileName(){
        System.out.println("------------------testWrongFileName--------------");
        Exception exception = assertThrows(RuntimeException.class, () -> {
        var bp = new BankFileParser("u:/uhuh.xxx"); });
        
        assertTrue(exception.getMessage()
                .contains("Nie ma pliku Bankowego z przelewami"));
        
    }
    
    /**
     * Test of getWrongLines method, of class BankFileParser.
     * @param s
     */
   
    @Test
    public void singleLineTest(SoftAssertions softly){
        var parser = new BankFileParser("testfiles/emptyfile.txt"); //no Bank Config
        
        parser.analizeLine("Kicha", false);
        
        softly.assertThat(parser.wrongLines.size()).as("One Wrong Line").isEqualTo(1);
        softly.assertThat(parser.listaTransakcji.size()).as("One Wrong Line").isEqualTo(0);
    }
    
    
    @Test
    public void singleGeneratedLineTest(SoftAssertions softly){
        var parser = new BankFileParser("testfiles/emptyfile.txt"); //no Bank Config
        String line = new TransactionStringBuilder().create();
        var gson = new GsonBuilder().setPrettyPrinting().create();
        
        parser.analizeLine(line , false);
        
        softly.assertThat(parser.wrongLines.size()).as("One Wrong Line").isEqualTo(0);
        softly.assertThat(parser.listaTransakcji.size()).as("One Wrong Line").isEqualTo(1);
        
        var transaction = parser.listaTransakcji.get(0);
        
        softly.assertThat(transaction.amount).as("amount").isEqualTo(70);
        softly.assertThat(transaction.school).as("school number").isEqualTo("888");
        softly.assertThat(transaction.klass).as("klass number").isEqualTo("2c");
        softly.assertThat(transaction.account).as("account number").isEqualTo("1234567890");
        softly.assertThat(transaction.niceString).as("nice ma imie").containsSequence("heniek kurek");
    }
    
    
   /* @Test
    public void singleGeneratedLineTest(SoftAssertions softly){
        var parser = new BankFileParser("testfiles/emptyfile.txt");
        
        
        parser.analizeLine(new TransactionStringBuilder().create(), false);
        
        System.out.println(parser.listaTransakcji); 
        softly.assertThat(parser.wrongLines.size()).as("One Wrong Line").isEqualTo(1);
        softly.assertThat(parser.listaTransakcji.size()).as("One Wrong Line").isEqualTo(0);
               } */
}
