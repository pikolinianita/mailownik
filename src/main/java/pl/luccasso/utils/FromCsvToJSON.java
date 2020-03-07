/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.luccasso.mailownik.DoCompare;
import pl.luccasso.mailownik.Pupil;
import pl.luccasso.mailownik.SinglePupil;

/**
 *
 * @author piko
 */
public class FromCsvToJSON {
    
    
    public static void main(String[] args) throws IOException {
      
        String s = "e:/asiowytest/autputTst.txt";
        var pups = loadPreviousData(s);
        saveJson(pups);
    }
    
      static List<Pupil> loadPreviousData(String inpPath) {
        if (inpPath == null) {
            return null;
        }
       
        List<Pupil> pupList = new LinkedList<>(); 
        try (var sc = new Scanner(new FileReader(inpPath))){
            sc.nextLine();
            while(sc.hasNext()){
                 var nl = sc.nextLine();
                var s = new SinglePupil(nl);
                pupList.add(s);
            }
            sc.close();
            return pupList;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        } 
    return null;
    }

    private static void saveJson(List<Pupil> pups) throws IOException {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(pups);
        System.out.println(s);
        var f = new BufferedWriter (new FileWriter(new File("e:/asiowytest/convert.txt")));
        f.write(s);
        f.flush();
        f.close();
    }
}
