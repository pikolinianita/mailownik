/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import pl.luccasso.mailownik.model.NewFamily;


/**
 *
 * @author piko
 */

public class DiscSaver {
    DataBase dataBase;
    
    @Setter
    @Getter
    Path dirPath = Path.of("e:/asiowytest/saveplace");
    
    DiscSaver(DataBase arg) {
        this.dataBase = arg;
    }

    void saveAllToDisc() {
        prepareDirectory();
        saveStructures();
    }

    void prepareDirectory() {
        File directory = dirPath.toFile();
        if (!directory.exists()){
            directory.mkdirs();
        } else if (directory.isFile()){
            directory = directory.getParentFile();
            dirPath = directory.toPath();
        }
    }

    void saveStructures() {
        saveLeftOvers();
        saveWrongLines();
        saveOutput();
    }

    private void saveLeftOvers() {
        try (var fw = new FileWriter("e:/leftovers.txt")) { // TODO path
            for (var transaction : dataBase.leftOvers()) {
                fw.write(transaction.saveTransaction());
            }
            for (var transaction : dataBase.humanFamilyToDecide().keySet()) {
                fw.write(transaction.saveTransaction());
            }
        } catch (IOException exc) {
            Logger.getLogger(DiscSaver.class.getName()).log(Level.SEVERE, null, exc);
        }
    }

   

    private void saveWrongLines() {
      try (var fw = new FileWriter("e:/syfy.txt")) {  //TODO path
            for (var p : dataBase.wrongLines()) {
                fw.write(p);
            }
        } catch (IOException ex) {
            Logger.getLogger(DiscSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveOutput() {
       try (var fw = new FileWriter("e:/output.txt")) {  //TODO path
            writeCSVFileHeader(fw);
            for (var family : dataBase.neuFamilyList()) {
                family.convertTransactionsToTrInfo( dataBase.getTransactionListFor(family));
                writeFamilyMembersToFile(family, fw);
                //Old, to delete    fw.write(family.processTransactions(dataBase.famFittedData().get(family)).getFileLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(DiscSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //TODO Update dla wczytywania i zapisywania pliku - rodzenstwa ID
    void writeCSVFileHeader(final FileWriter fw) throws IOException {
        fw.write(String.join("\t", "Id", "SkryptID", "Szkoła", "Imie", "Nazwisko", "Klasa", "Tel Mamy", "Tel Taty", "Mail",
                "Zaj 1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "obecny", "Nieobecny", "Usprawiedliwione",
                "Suma wpłat", "Winien", "wpłaty", "W sumie zaplaci", "Ilosc zajec w szkole", "DanePrzelewow", "konta") + "\n");
    }

    private void writeFamilyMembersToFile(NewFamily family, FileWriter fw) throws IOException {
        String[] linesForWrite = family.getFileLines();
        for(var singleLine:linesForWrite){
            fw.write(singleLine);
        }
    }
    
}
/*public void save() {

        try (var fw = new FileWriter("e:/leftovers.txt")) {
            for (var family : dataBase.leftOvers) {
                fw.write(family.saveTransaction());
            }
            for (var family : dataBase.humanToDecide.keySet()) {
                fw.write(family.saveTransaction());
            }
        } catch (IOException exc) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, exc);
        }

        try (var fw = new FileWriter("e:/siblings_org.txt")) {
            for (var family : dataBase.siblings) {
                fw.write(family.saveTransaction());
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (var fw = new FileWriter("e:/syfy.txt")) {
            for (var family : dataBase.wrongLines) {
                fw.write(family);
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (var fw = new FileWriter("e:/output.txt")) {
            fw.write(String.join("\t", "Id", "SkryptID", "Szkoła", "Imie", "Nazwisko", "Klasa", "Tel Mamy", "Tel Taty", "Mail",
                    "Zaj 1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "obecny", "Nieobecny", "Usprawiedliwione",
                    "Suma wpłat", "Winien", "wpłaty", "W sumie zaplaci", "Ilosc zajec w szkole", "DanePrzelewow", "konta") + "\n");
            for (var family : dataBase.pupilList()) {
                fw.write(family.processTransactions(dataBase.fittedData.get(family)).getFileLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (var fw = new FileWriter("e:/siblings_calc.txt")) {
            fw.write(String.join("\t", "Id", "SkryptID", "Szkoła", "Imie", "Nazwisko", "Klasa", "Tel Mamy", "Tel Taty", "Mail",
                    "Zaj 1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "obecny", "Nieobecny", "Usprawiedliwione",
                    "Suma wpłat", "Winien", "wpłaty", "W sumie zaplaci", "Ilosc zajec w szkole", "DanePrzelewow", "konta" + "\n"));
            for (var family : dataBase.sibFitted.keySet()) {
                fw.write(family.processTransactions(dataBase.sibFitted.get(family)).getFileLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/