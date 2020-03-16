/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class PupilConstructorTest {

    List<Pupil> pupils = new LinkedList<>();

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
    public void testLoadFromOutput() {

        try (var sc = new Scanner(new BufferedReader(new FileReader("testfiles/autputtst.txt")))) {
            sc.nextLine();
            while (sc.hasNext()) {
                pupils.add(new SinglePupil(sc.nextLine()));
                System.out.println(pupils.get(pupils.size() - 1).getPupilDataNoTimeSheet());
            }
        } catch (IOException ex) {
            Logger.getLogger(PupilConstructorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(pupils);

    }

}
