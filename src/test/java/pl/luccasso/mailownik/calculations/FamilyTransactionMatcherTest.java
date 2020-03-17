/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.calculations;

import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pl.luccasso.mailownik.DoCompare;
import pl.luccasso.mailownik.config.ConfigF;
import pl.luccasso.mailownik.persistence.DataBase;

/**
 *
 * @author piko
 */
public class FamilyTransactionMatcherTest {

    DoCompare dc;
    DataBase db;

    @BeforeAll
    public static void setUpClass() {
    }

    @BeforeEach
    public void setUp() {
        try {
            ConfigF.restoreCleanTestConfiguration();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FamilyTransactionMatcherTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        dc = new DoCompare();
        dc.loadStuff();
        db = dc.getDataBase();
        db.makeStructures();
    }

    @Test
    public void testSomeMethod() {
        var ftm = new FamilyTransactionMatcher(db);
        var gson = new GsonBuilder().setPrettyPrinting().create();
           
    }

}
