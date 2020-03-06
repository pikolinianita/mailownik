/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.config;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author piko
 */
public class ConfigF {
     static String pupPath = "e:/asiowytest/pupils14.txt";
     static String bankPath = "e:/asiowytest/listatestowa14b.csv";
     static String savedPath = "e:/asiowytest/outputtst.txt";
     static String logFile = "e:/asiowytest/logs.txt";
     static String payPerClass = "e:/asiowytest/cenyvsnz.txt"; 
     static String ClassPerSchool  ="e:/asiowytest/zajwszk.txt";
     static String configPath = "e:/asiowytest/config.txt";
    
     static ArrayList<String> commonAccounts ;
     
     //TODO dopisać że to z pliku;
     static{
         commonAccounts = new ArrayList<>();
         commonAccounts.add("36124025971111000031744632");
     }
     
     
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
    
   static void readConfigFromFile(String f) throws FileNotFoundException{
       
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

    
    static public void restoreCleanTestConfiguration() throws FileNotFoundException{
         readConfigFromFile("e:/asiowytest/config.txt");
         
    }
    
    
    //@Override
    static public String getPupilsConfigLong() {
        return "ConfigF{/n pupPath  = " + pupPath + '}';
    }

    public static String getPupPath() {
        return pupPath;
    }

    public static String getBankPath() {
        return bankPath;
    }

    public static String getSavedPath() {
        return savedPath;
    }

    public static String getLogFile() {
        return logFile;
    }

    public static String getPayPerClass() {
        return payPerClass;
    }

    public static String getClassPerSchool() {
        return ClassPerSchool;
    }

    public static String getConfigPath() {
        return configPath;
    }

    public static void setPupPath(String aPupPath) {
        pupPath = aPupPath;
    }

    public static void setBankPath(String aBankPath) {
        bankPath = aBankPath;
    }

    public static void setSavedPath(String aSavedPath) {
        savedPath = aSavedPath;
    }

    public static void setLogFile(String aLogFile) {
        logFile = aLogFile;
    }

    public static void setPayPerClass(String aPayPerClass) {
        payPerClass = aPayPerClass;
    }

    public static void setClassPerSchool(String aClassPerSchool) {
        ClassPerSchool = aClassPerSchool;
    }

    public static void setConfigPath(String aConfigPath) {
        configPath = aConfigPath;
    }
    
    
}
    
    
    

