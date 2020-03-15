/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;
import pl.luccasso.mailownik.calculations.FamilyTransactionMatcher;
import pl.luccasso.mailownik.config.ConfigF;
import pl.luccasso.mailownik.model.NewFamily;
import pl.luccasso.mailownik.persistence.DataBase;

/**
 *
 * @author piko
 */
@Getter
@Setter
public class DoCompare {

    public static FinancialData finData;
    DataBase dataBase;

    static {
        finData = new FinancialData()
                .importPaymentPerKlasses(ConfigF.getPayPerClass()) //"e:/cenyvsnz.txt
                .importschools(ConfigF.getClassPerSchool());
    }

    public Map<BankTransaction, List<NewFamily>> getToBeDecidedMap() {
        return dataBase.humanFamilyToDecide();
    }

    public DoCompare() {
        dataBase = new DataBase();
        try {
            ConfigF.readConfigFromFile("e:/asiowytest/config.txt");  //TODO Powinien czytac z nie wiem czego...
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doWork() {
        loadStuff();
        dataBase.makeStructures();
        //searchForSiblings();
        var ftm = new FamilyTransactionMatcher(dataBase);
        dataBase.listaTransakcji().forEach(ftm::analyzeTransaction3);
        dataBase.sort();      
    }
    
    public void loadStuff() {
        finData = new FinancialData()
                .importPaymentPerKlasses(ConfigF.getPayPerClass()) //"e:/cenyvsnz.txt
                .importschools(ConfigF.getClassPerSchool());
        BankFileParser.finData = DoCompare.finData;
        var parser = new BankFileParser(ConfigF.getBankPath());
        dataBase.listaTransakcji(parser.getListaTransakcji());
        dataBase.wrongLines(parser.getWrongLines());
        dataBase.pupilList(loadPreviousData(ConfigF.getSavedPath()));
        dataBase.pupilList(new GAppsParser(ConfigF.getPupPath(), dataBase.pupilList()).pupils);
        dataBase.neuFamilyList(dataBase.convertPupilListToFamilyList(dataBase.pupilList()));
    }  

    public void setBankPath(String path) {
        ConfigF.setBankPath(path); //TODO remove this Function
    }

    public void setPupPath(String path) {
        ConfigF.setPupPath(path); //TODO remove this Function
    }

    public void addToFittedData(NewFamily nf, BankTransaction bt) {
        dataBase.famFittedData().merge(nf, new LinkedList<>(List.of(bt)), (o, n) -> {
            o.addAll(n);
            return o;
        });
    }

    public void removeFromHumanToDecide(BankTransaction bt) {
        dataBase.humanFamilyToDecide().remove(bt);
    }

    public void addToLeftOvers(BankTransaction bt) {
        dataBase.leftOvers().add(bt);
    }

   /* public void save() {

        try (var fw = new FileWriter("e:/leftovers.txt")) {
            for (var p : dataBase.leftOvers()) {
                fw.write(p.saveTransaction());
            }
            for (var p : dataBase.humanToDecide().keySet()) {
                fw.write(p.saveTransaction());
            }
        } catch (IOException exc) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, exc);
        }

        try (var fw = new FileWriter("e:/siblings_org.txt")) {
            for (var p : dataBase.siblings()) {
                fw.write(p.saveTransaction());
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (var fw = new FileWriter("e:/syfy.txt")) {
            for (var p : dataBase.wrongLines()) {
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
                fw.write(p.processTransactions(dataBase.fittedData().get(p)).getFileLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (var fw = new FileWriter("e:/siblings_calc.txt")) {
            fw.write(String.join("\t", "Id", "SkryptID", "Szkoła", "Imie", "Nazwisko", "Klasa", "Tel Mamy", "Tel Taty", "Mail",
                    "Zaj 1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "obecny", "Nieobecny", "Usprawiedliwione",
                    "Suma wpłat", "Winien", "wpłaty", "W sumie zaplaci", "Ilosc zajec w szkole", "DanePrzelewow", "konta" + "\n"));
            for (var p : dataBase.sibFitted().keySet()) {
                fw.write(p.processTransactions(dataBase.sibFitted().get(p)).getFileLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
    public List<BankTransaction> getLeftOvers() {
        System.out.println("Kurka - getLeftOvers");
        return dataBase.leftOvers();
    }

    public List<NewFamily> getNeuFamilyList() {
        return dataBase.neuFamilyList();
    }

    public Map<Integer, List<NewFamily>> getFamBySchoolMap() {
        return dataBase.famBySchoolMap();
    }

    public List<String> getWrongLinesList() {
        return dataBase.wrongLines();
    }

    public List<BankTransaction> getSiblingsBTList() {
        return dataBase.siblings();
    }

    public int getAmountOfFamFittedTransactions() {
        return (int) dataBase.famFittedData().values().stream().flatMap(li -> li.stream()).count();
    }

    public void pushLinesToSiblings(BankTransaction bt, List<Pupil> chosenSiblings) {
        int nSiblings = chosenSiblings.size();
        System.out.println("Lista rodzenstwa to: " + nSiblings);
        System.out.println("sib Fitted: " + dataBase.sibFitted().keySet().size());
        for (var p : chosenSiblings) {
            BankTransaction tmpTrans = bt.divideCashAndReturnNew(nSiblings);
            dataBase.sibFitted().merge(p, new LinkedList<>(List.of(bt)), (o, n) -> {
                o.addAll(n);
                return o;
            });
        }
        dataBase.siblings().remove(bt);

    }

    List<Pupil> loadPreviousData(String inpPath) {
        if (inpPath == null) {
            return new LinkedList<>();
        }

        List<Pupil> pupList = new LinkedList<>();
        try (var sc = new Scanner(new FileReader(inpPath))) {
            sc.nextLine();
            while (sc.hasNext()) {
                pupList.add(new SinglePupil(sc.nextLine()));
            }
            return pupList;
        } catch (NoSuchElementException | FileNotFoundException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new LinkedList<>();
    }

    public void setDBPath(String dBPath) {
        ConfigF.setSavedPath(dBPath);
    }

    private void updatePupilsAddIfAbsent(List<Pupil> oldList, List<Pupil> googleList) {
        next_pupil:
        for (var p : googleList) {
            String ID = p.getSkryptId();
            for (var op : oldList) {
                if (ID.equals(op.getSkryptId())) {
                    op.updateValuesWithGoogleData(p);
                    break next_pupil;
                }
            }
            oldList.add(p);
        }
    }

    void searchForSiblings() {
        //TODO
    }
    
    
}

    /*
     * void writeListsToSout() {
     * System.out.println("---------------sibli--------------");
     * System.out.println(siblings.size()); System.out.println(siblings);
     * System.out.println("------------Left Overs------------");
     * System.out.println(leftOvers.size()); System.out.println(leftOvers);
     * System.out.println("------------Human To Decide---------");
     * System.out.println(humanToDecide.entrySet().size());
     * System.out.println(humanToDecide.entrySet());
     * System.out.println("------------fitted------------------");
     * System.out.println(fittedData.values().stream().flatMap(li->li.stream()).count());
     * System.out.println(fittedData.entrySet());
     *
     * var tmpList = new LinkedList<>(listaTransakcji);
     * tmpList.removeAll(siblings); tmpList.removeAll(leftOvers);
     * tmpList.removeAll(humanToDecide.keySet()); fittedData.values().forEach(e
     * -> { tmpList.removeAll(e); });
     *
     * System.out.println("------------F lost------------------");
     * System.out.println(tmpList.size()); System.out.println(tmpList);
     * System.out.println("--------- end ---------");
     * System.out.println("---------------org lista trans------");
     * System.out.println(listaTransakcji.size());
     * System.out.println("---------------org lista trans set------");
     * System.out.println(new HashSet(listaTransakcji).size());
     * System.out.println("---------------mapa----------------------"); Map
     * tmpMap = listaTransakcji.stream().collect(Collectors.groupingBy((tr) ->
     * tr.matchNotes)); System.out.println(tmpMap.entrySet().size());
     * System.out.println(tmpMap); }
     *
     * void writeMapsToSout() { System.out.println("---------------org lista
     * trans------"); System.out.println(listaTransakcji.size());
     * System.out.println("-------Set----------");
     * System.out.println(pupByKlassMap.keySet());
     * System.out.println("-------Set----------");
     * System.out.println(pupBySchoolMap.keySet()); }
     *
     *
     *
     * public static void main(String[] args) { var dc = new DoCompare();
     * dc.doWork(); dc.save();
    }
     */
//
