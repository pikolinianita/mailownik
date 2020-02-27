/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.luccasso.mailownik.config.ConfigF;

/**
 *
 * @author piko
 */
public class BankFileParserTest {
    
    public BankFileParserTest() {
    }

    /**
     * Test of getListaTransakcji method, of class BankFileParser.
     */
    @Test
    public void testGetListaTransakcji() {
        
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
        var bp = new BankFileParser("u:/uhuh.xxx");
    }
    
    /**
     * Test of getWrongLines method, of class BankFileParser.
     * @param s
     */
   
    
}
