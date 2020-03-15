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
        searchForSiblings();
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

  /*  private void analyzeTransaction2(BankTransaction bt) {
        bt.note("===========Fakk==============");
        try {
            if (dataBase.pupByAccountMap.containsKey(bt.account)) {
                var pList = dataBase.pupByAccountMap.get(bt.account);
                if (pList.size() > 1) {
                    dataBase.siblings.add(bt);
                    bt.note("siblings - fitted by account");
                    return;
                }
                dataBase.fittedData.merge(pList.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                    o.addAll(n);
                    return o;
                });
                bt.note("account");
                return;
            }

            bt.checkForSiblings();
            if (bt.siblingsSuspected) {
                dataBase.siblings.add(bt);
                bt.note("siblings");
                return;
            }

            if (bt.isDoomed) {
                dataBase.leftOvers.add(bt);
                bt.note("Doomed");
                return;
            }

            List<Pupil> tmpList;
            if (!bt.hasDubiousSchool) {
                tmpList = new LinkedList(dataBase.pupBySchoolMap.get(Integer.valueOf(bt.school)));
                var listWithlNames = tryFitNames(bt, tmpList);
                if (listWithlNames.isEmpty()) {
                    if (!bt.hasDubiousKlass) {
                        var listWithKlass = tryFitKlass(bt, tmpList);
                        var listWithfName = tryFitfName(bt, listWithKlass);
                        if (listWithfName.isEmpty()) {
                            if (listWithKlass.size() > 0) {
                                dataBase.humanToDecide.put(bt, listWithKlass);
                                bt.note("School+ fName+ lname- klass++ ");
                                return;
                            } else {
                                dataBase.leftOvers.add(bt);
                                bt.note("School+ lName- klass+ fname- ");
                                return;
                            }
                        } else if (listWithfName.size() == 1) {
                            dataBase.fittedData.merge(listWithfName.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                                o.addAll(n);
                                return o;
                            });
                            bt.note("School+ fname+ klass+ lName-");
                            return;
                        } else {
                            dataBase.leftOvers.add(bt);
                            bt.note("School+ fname++ klass+ lName- ");
                            return;
                        }

                    } else {
                        dataBase.leftOvers.add(bt);
                        bt.note("School+ lName- klass?");
                        return;
                    }
                } else if (listWithlNames.size() == 1) {

                    dataBase.fittedData.merge(listWithlNames.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                        o.addAll(n);
                        return o;
                    });
                    bt.note("School+ lName+");
                    return;
                } else {
                    if (bt.hasDubiousKlass) {
                        dataBase.humanToDecide.put(bt, listWithlNames);
                        bt.note("School+ lname++ klass?");
                        return;
                    } else {
                        var listWithKlass = tryFitKlass(bt, tmpList);
                        if (listWithKlass.isEmpty()) {
                            dataBase.humanToDecide.put(bt, listWithlNames);
                            bt.note("School+ lname++ klass-");
                            return;
                        } else if (listWithKlass.size() == 1) {
                            bt.note("School+ lName++ klass+");
                            dataBase.fittedData.merge(listWithKlass.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                                o.addAll(n);
                                return o;
                            });
                            return;
                        } else {
                            bt.note("School+ lName++ klass++");
                            dataBase.humanToDecide.put(bt, listWithKlass);
                            return;
                        }

                    }
                }

            }

            if (!bt.hasDubiousKlass) {
                tmpList = new LinkedList(dataBase.pupByKlassMap.get(bt.klass));
                var ListlName = tryFitNames(bt, tmpList);
                var ListwSchool = tryFindSchool(bt, ListlName);
                if (ListwSchool.size() > 1) {
                    bt.note("School++ lName++ klass+");
                    dataBase.humanToDecide().put(bt, ListwSchool);
                    return;
                } else if (ListwSchool.size() == 1) {
                    bt.note("Klass+ school+ lname +");
                    dataBase.fittedData().merge(ListwSchool.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                        o.addAll(n);
                        return o;
                    });
                    return;
                } else {
                    if (ListlName.isEmpty()) {
                        dataBase.leftOvers().add(bt);
                        bt.note("Klas+ lname- ");
                        return;
                    } else if (ListlName.size() == 1) {
                        bt.note("Klas+ lname+ school-");
                        dataBase.fittedData().merge(ListlName.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                            o.addAll(n);
                            return o;
                        });
                        return;
                    } else {
                        bt.note("Klas+ lname++");
                        dataBase.humanToDecide().put(bt, ListlName);
                        return;
                    }

                }
            }

            tmpList = new LinkedList<>(dataBase.pupilList());
            dataBase.leftOvers().add(bt);
            bt.note("School?");

        } catch (Exception e) {
            dataBase.leftOvers().add(bt);
            bt.note("------Exception----------");
            System.out.println("anal2: -Ex- : " + bt.title);

        }
    }
    */
    
    private List<Pupil> tryFitKlass(BankTransaction bt, List<Pupil> lList) {
        return lList.stream()
                .filter(p -> p.isMyKlass(bt.klass))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<Pupil> tryFindSchool(BankTransaction bt, List<Pupil> lList) {
        return lList.stream()
                .filter(p -> p.isMySchoolHere(bt.klass))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<Pupil> tryFitfName(BankTransaction bt, List<Pupil> lList) {
        return lList.stream()
                .filter(p -> p.isMyfNameHere(bt.niceString))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<Pupil> tryFitNames(BankTransaction bt, List<Pupil> lList) {
        return lList.stream()
                .filter(p -> p.isMylNameHere(bt.niceString))
                .collect(Collectors.toCollection(LinkedList::new));
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

    public void save() {

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
