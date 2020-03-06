/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import pl.luccasso.mailownik.config.ConfigF;

/**
 *
 * @author piko
 */
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
   
    
}
