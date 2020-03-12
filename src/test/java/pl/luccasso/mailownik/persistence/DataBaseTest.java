/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
public class DataBaseTest {
    
    

    @Test
    public void testMapSort(SoftAssertions softly) {
        var map = new HashMap<Integer, List<String>>();
        var one = new LinkedList<String>();
        Collections.addAll(one, "raz", "jeden", "one", "ein");
        map.put(1, one);
        
        var two = new LinkedList<String>();
        Collections.addAll(two, "dwaz", "two", "zwei", "dwa");
        map.put(2, two);
        
       var db = new DataBase();
        
        db.mapSort(map);
        
        softly.assertThat(map.get(1)).as("sortowanie map 1 ").containsExactly("ein", "jeden", "one", "raz" );
        softly.assertThat(map.get(2)).as("sortowanie map 2 ").containsExactly("dwa", "dwaz", "two", "zwei" );
    }
    
}
