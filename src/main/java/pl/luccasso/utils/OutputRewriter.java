/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import pl.luccasso.mailownik.DoCompare;
import pl.luccasso.mailownik.config.ConfigF;

/**
 *
 * @author piko
 */
public class OutputRewriter {
    
    DoCompare dc;
    
    public static void main(String[] args) {
        new OutputRewriter();
    }

    public OutputRewriter() {
        ConfigF.setSavedPath("e:\\backup\\Asiowe\\OfficialData\\SyfyFix\\output 02.txt");
        ConfigF.setClassPerSchool("e:\\backup\\Asiowe\\OfficialData\\zajwszk.txt" );
        ConfigF.setPayPerClass("e:\\backup\\Asiowe\\OfficialData\\cenyvsnz.txt" );
        ConfigF.setOutputDirectory("e:\\backup\\Asiowe\\OfficialData\\SyfyFix2");
        
        dc = new DoCompare();
        
        dc.oldFileRewriter();
    }
    
}
