/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;


import com.google.gson.GsonBuilder;
import java.util.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author piko
 */
public class GAppsParserTest {
    //GAppsParser gap;
    
        
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
        var list = new LinkedList<Pupil>();
        GAppsParser gap = new GAppsParser("e:/asiowytest/convert.txt",list);
        var gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(gap.pupilsI));
        System.out.println("---------------faken----------------");
        //System.out.println(gson.toJson(gap.fakenNauczLudziKomentek()));
        /*gap.fakenNauczLudziKomentek().forEach((e)-> 
                System.out.println("SP " + e.schoolNr + " : " + e.fName + " " + e.lName));
        
        gap.fakenKlasses().forEach((e)-> 
                System.out.println("SP " + e.schoolNr + " : " + e.klass + " - " + e.fName + " " + e.lName));
                */
    }
    
}
