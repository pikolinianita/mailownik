/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;


import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.luccasso.mailownik.config.ConfigF;

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
        GAppsParser gap = new GAppsParser("testfiles/papils14.txt",list);
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
    @Test
    public void TestMergeWithPrevData(){
        
        
        var dc = new DoCompare();
        ConfigF.setSavedPath("e:/asiowytest/autputTstless.txt");
        ConfigF.setPupPath("e:/asiowytest/pupils14.txt");
        dc.loadStuff();
        
        var db = dc.getDataBase();
        db.neuFamilyList().stream().map(nf->nf.getFileLines()).forEach(sarr ->System.out.println(Arrays.toString(sarr)));
        //System.out.println(Arrays.toString(db.neuFamilyList()));
    }
    
    @Test
    public void TestFamilyFittingWithRealData(){
        var dc = new DoCompare();
        ConfigF.setBankPath("e:/listatestowa.csv");
        ConfigF.setSavedPath(null);
        ConfigF.setPupPath("e:/pupils.txt");
        dc.loadStuff();
        
        dc.doWork();
        
        long siblings = dc.getDataBase().neuFamilyList().stream().filter(nf-> nf.size()>1).count();
        //System.out.println(siblings);
        /*dc.getDataBase().neuFamilyList().stream().filter(nf-> nf.size()>1)
                .forEach(nf->{System.out.println(nf.contacts().toString());
                System.out.println(nf.getSchoolNr());});*/
        
        var db = dc.getDataBase();
        System.out.println("kicha");
      
    }
    
}
