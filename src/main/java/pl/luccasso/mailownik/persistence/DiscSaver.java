/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.persistence;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;
import lombok.Setter;

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
        saveToDecide();
        saveWrongLines();
        saveOutput();
    }

    private void saveLeftOvers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void saveToDecide() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void saveWrongLines() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void saveOutput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
/*public void save() {

        try (var fw = new FileWriter("e:/leftovers.txt")) {
            for (var p : dataBase.leftOvers) {
                fw.write(p.saveTransaction());
            }
            for (var p : dataBase.humanToDecide.keySet()) {
                fw.write(p.saveTransaction());
            }
        } catch (IOException exc) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, exc);
        }

        try (var fw = new FileWriter("e:/siblings_org.txt")) {
            for (var p : dataBase.siblings) {
                fw.write(p.saveTransaction());
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (var fw = new FileWriter("e:/syfy.txt")) {
            for (var p : dataBase.wrongLines) {
                fw.write(p);
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (var fw = new FileWriter("e:/output.txt")) {
            fw.write(String.join("\t", "Id", "SkryptID", "Szkoła", "Imie", "Nazwisko", "Klasa", "Tel Mamy", "Tel Taty", "Mail",
                    "Zaj 1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "obecny", "Nieobecny", "Usprawiedliwione",
                    "Suma wpłat", "Winien", "wpłaty", "W sumie zaplaci", "Ilosc zajec w szkole", "DanePrzelewow", "konta") + "\n");
            for (var p : dataBase.pupilList()) {
                fw.write(p.processTransactions(dataBase.fittedData.get(p)).getFileLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (var fw = new FileWriter("e:/siblings_calc.txt")) {
            fw.write(String.join("\t", "Id", "SkryptID", "Szkoła", "Imie", "Nazwisko", "Klasa", "Tel Mamy", "Tel Taty", "Mail",
                    "Zaj 1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "obecny", "Nieobecny", "Usprawiedliwione",
                    "Suma wpłat", "Winien", "wpłaty", "W sumie zaplaci", "Ilosc zajec w szkole", "DanePrzelewow", "konta" + "\n"));
            for (var p : dataBase.sibFitted.keySet()) {
                fw.write(p.processTransactions(dataBase.sibFitted.get(p)).getFileLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/