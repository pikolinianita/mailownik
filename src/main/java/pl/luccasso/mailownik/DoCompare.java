/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
