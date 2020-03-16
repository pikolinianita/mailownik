/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.config;

import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;

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
    
    static String ClassPerSchool = "e:/asiowytest/zajwszk.txt";
    
    static String configPath = "e:/asiowytest/config.txt";

    static ArrayList<String> commonAccounts;
    
    @Getter
    @Setter
    private static boolean isSecondSemester; 

    //TODO dopisać że to z pliku
    static {
        commonAccounts = new ArrayList<>();
        commonAccounts.add("55114020040000340278370553");
        isSecondSemester = true;
    }

    private ConfigF(){
        
    }
    
    public static void saveToFile(String f) throws IOException {

        Path p = Paths.get(f);

        try (var fw = new BufferedWriter(new FileWriter(p.toFile()))) {
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

    public static void readConfigFromFile(String f) throws FileNotFoundException {
        var p = Paths.get(f);
        try (Scanner sc = new Scanner((new FileReader(p.toFile())))) {
            pupPath = sc.next();
            bankPath = sc.next();
            savedPath = sc.next();
            logFile = sc.next();
            payPerClass = sc.next();
            ClassPerSchool = sc.next();
            configPath = sc.next();

        }
    }

    public static void restoreCleanTestConfiguration() throws FileNotFoundException {
        readConfigFromFile("testfiles/config.txt");

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
    
    public static void readFromJsonFile(String path) throws FileNotFoundException, IOException{
       var gson = new GsonBuilder().excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT).create();
        try (var fr = new FileReader(path)){
            gson.fromJson(fr, ConfigF.class);
        }
    }
    

    public static void saveToJsonFile(String path) throws IOException{
       var gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT).create();
       var cnf = new ConfigF();
       try(var fw = new FileWriter(path)){
               gson.toJson(cnf,fw);            
       }
    }
    
        
}
