/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author piko
 */
public class ConfigF {
    static String pupPath = "e:/test/pupils.txt";
    static String bankPath = "e:/test/listatestowa.csv";
    static String savedPath = "e:/test/output.tsv";
    static String logFile = "e:/test/logs.txt";
    static String payPerClass = "e:/cenyvsnz.txt"; 
    static String ClassPerSchool  ="e:/zajwszk.txt";
    static String configPath = "e:/config.txt";
    
    static void saveToFile(String f) throws IOException{
       
        try (java.io.BufferedWriter fw = new BufferedWriter(new FileWriter(f))) {
            fw.write(pupPath);
            fw.newLine();
            fw.write(bankPath);
            fw.newLine();
            fw.write(savedPath);
            fw.newLine();
            fw.write(logFile);
            fw.newLine();
            fw.write(payPerClass);
            fw.newLine();
            fw.write(ClassPerSchool);
            fw.newLine();
            fw.write(configPath);
        }
    }
    
   static void readFromFile(String f) throws FileNotFoundException{
       
        try (java.util.Scanner sc = new Scanner((new FileReader(f)))) {
            pupPath = sc.next();
            bankPath = sc.next();
            savedPath = sc.next();
            logFile = sc.next();
            payPerClass = sc.next();
            ClassPerSchool  =sc.next();
            configPath = sc.next();
            
        }
    }

    
    static void setTestConfig() throws FileNotFoundException{
         readFromFile("e:/asiowytest/config.txt");
         
    }

    //@Override
    static public String getPupilsConfigLong() {
        return "ConfigF{/n pupPath  = " + pupPath + '}';
    }
    
    
}
    
    
    

