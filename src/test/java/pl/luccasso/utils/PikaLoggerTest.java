/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author piko
 */
public class PikaLoggerTest {
    

    /**
     * Test of getLogger method, of class PikaLogger.
     */
    @Test
    public void testGetLogger() throws IOException {
        System.out.println("getLogger");
        Logger test = new PikaLogger("", "logtest.txt").getLogger();
        test.info("kicha");
        File f = new File("logtest.txt");
        assertEquals(true, f.isFile());
        assertEquals(58, f.length());
        }    
}
