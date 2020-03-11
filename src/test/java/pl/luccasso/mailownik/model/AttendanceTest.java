/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author piko
 */
public class AttendanceTest {
    
   Attendance att;
    
    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @BeforeEach
    public void setUp() {
        att = new Attendance();
        
    }

    @Test
    public void testSomeMethod() {
        String[] ts = {"0","1","M","M"};
        att.timeSheet(ts);
        att.AbsenceCalculation();
        System.out.println(att);
    }
    
}
