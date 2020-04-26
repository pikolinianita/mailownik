/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import pl.luccasso.mailownik.DoCompare;
import pl.luccasso.mailownik.config.ConfigF;
import pl.luccasso.mailownik.persistence.DataBase;

/**
 *
 * @author piko
 */
public class ManyAccountsFinder {

    static DataBase dataBase;
    static DoCompare dc;

    public static void main(String[] args) {

        dataBase = new DataBase();
        dc = new DoCompare();

        dataBase.pupilList(dc.loadPreviousData("e:\\backup\\Asiowe\\OfficialData\\out do konca\\output.txt"  ));

        var list = dataBase.pupilList();

        list.stream()
                .filter((pup) -> (pup.getAccountNrs().size() > 1))
                .forEach((pup) -> {
                    System.out.println(pup.getAccountNrs().size());
                    System.out.println(pup.getFileLine());
                    
                });
    }

}
