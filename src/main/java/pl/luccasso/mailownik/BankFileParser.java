/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;

/**
 *
 * @author piko
 */
public class BankFileParser {

    List<BankTransaction> listaTransakcji;
    static public FinancialData finData;
    List<String> wrongLines;

    public List<BankTransaction> getListaTransakcji() {
        return listaTransakcji;
    }

    public static void main(String[] args) {

        BankFileParser.finData = new FinancialData()
                .importPaymentPerKlasses("e:/cenyvsnz.txt")
                .importschools("e:/zajwszk.txt");
        var parser = new BankFileParser("e:/smallList.txt");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        saveToFile("e:/bzyk.txt", gson.toJson(parser.listaTransakcji));
        StringBuilder sb = new StringBuilder();
        parser.listaTransakcji.forEach((s) -> sb.append(s.specialToString()).append("\n"));
        saveToFile("e:/special.txt", sb.toString());
        //System.out.println("----- dub klas --------");
        //parser.listaTransakcji.stream().filter(e->e.hasDubiousKlass).forEach(System.out::println);
    }

    public BankFileParser(String fileName) {
        listaTransakcji = new LinkedList<>();
        wrongLines = new LinkedList<>();
        try (var sc = new Scanner(new FileReader(fileName))) {
            boolean isFirst = true;
            while (sc.hasNext()) {
                analizeLine(sc.nextLine(), isFirst);
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Nie ma pliku Bankowego z przelewami", ex);
        } 
    }

    public void analizeLine(String line, boolean isFirst) {

        if (line.contains("mBiznes konto pomocnicze 0711 ... 2221;") && line.contains("Wpływy - inne")) {
            listaTransakcji.add(new BankTransaction(line));
            isFirst = false;
        } else {
            if (!isFirst) {
                wrongLines.add(line);
            }
        }
    }

    private static void saveToFile(String patch, String message) {

        try (var fw = new FileWriter(patch);) {
            fw.write(message);
        } catch (IOException ex) {
            Logger.getLogger(BankFileParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> getWrongLines() {
        return wrongLines;
    }
}
