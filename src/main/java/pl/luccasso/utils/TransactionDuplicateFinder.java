/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import pl.luccasso.mailownik.BankFileParser;
import pl.luccasso.mailownik.TransactionInfo;

/**
 *
 * @author piko
 */
public class TransactionDuplicateFinder {

    public static void main(String[] args) throws IOException {

        var parser = new BankFileParser("e:/ListaTestowa.csv");

        System.out.println(parser.getListaTransakcji().size());

        Map<LocalDate, List<TransactionInfo>> map = parser.getListaTransakcji().stream()
                .map(bt -> new TransactionInfo(bt))
                .collect(Collectors.groupingBy(ti -> ti.date()));

        map.entrySet().forEach(TransactionDuplicateFinder::analyzeEntry);
    }

    private static void analyzeEntry(Map.Entry<LocalDate, List<TransactionInfo>> entry) {
        int startSize = entry.getValue().size();

        for (int i = 0; i < startSize; i++) {
            for (int j = i + 1; j < startSize; j++) {
                if (entry.getValue().get(i).title().equals(entry.getValue().get(j).title())) {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue().get(j));
                }
            }
        }

    }

}
