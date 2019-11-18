/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author piko
 */
public class DoCompare {
    String pupPath, bankPath;
    //czyste dane:
    FinancialData finData;
    List<BankTransaction> listaTransakcji;
    List<Pupil> pupilList;

    // uporzadkowane dane z Obecności
    Map<Integer, List<Pupil>> pupBySchoolMap;
    Map<String, List<Pupil>> pupByKlassMap;

    //Wynikowe Dane
    Map<Pupil, List<BankTransaction>> fittedData;
    Map<BankTransaction, List<Pupil>> humanToDecide;
    List<BankTransaction> leftOvers;
    List<BankTransaction> siblings;

    public static void main(String[] args) {
        var dc = new DoCompare();
        dc.doWork();
        dc.save();
    }
    
    public Map<BankTransaction, List<Pupil>> getToBeDecidedMap(){
        return humanToDecide;
    }
    
    public DoCompare() {
        pupPath = "e:/pupils.txt";
        bankPath = "e:/listatestowa.csv";
    }

    
    
    public void doWork() {
       
        
        loadStuff();
        makeStructures();
        {
            System.out.println("---------------org lista trans------");
            System.out.println(listaTransakcji.size());
            System.out.println("-------Set----------");
            System.out.println(pupByKlassMap.keySet());
            System.out.println("-------Set----------");
            System.out.println(pupBySchoolMap.keySet());
        }
        listaTransakcji.forEach(this::analyzeTransaction2);
        {
            System.out.println("---------------sibli--------------");
            System.out.println(siblings.size());
            System.out.println(siblings);
            System.out.println("------------Left Overs------------");
            System.out.println(leftOvers.size());
            System.out.println(leftOvers);
            System.out.println("------------Human To Decide---------");
            System.out.println(humanToDecide.entrySet().size());
            System.out.println(humanToDecide.entrySet());
            System.out.println("------------fitted------------------");
            System.out.println(fittedData.values().stream().flatMap(li->li.stream()).count());
            System.out.println(fittedData.entrySet());
        }
        
        
            var tmpList = new LinkedList<>(listaTransakcji);
            tmpList.removeAll(siblings);
            tmpList.removeAll(leftOvers);
            tmpList.removeAll(humanToDecide.keySet());
            fittedData.values().forEach(e->{tmpList.removeAll(e);});
            //tmpList.removeAll()
            System.out.println("------------F lost------------------");
            System.out.println(tmpList.size());   
            System.out.println(tmpList);        
            System.out.println("--------- end ---------");
            System.out.println("---------------org lista trans------");
            System.out.println(listaTransakcji.size());
            System.out.println("---------------org lista trans set------");
            System.out.println(new HashSet(listaTransakcji).size());
            System.out.println("---------------mapa----------------------");
            Map tmpMap = listaTransakcji.stream().collect(Collectors.groupingBy((tr)->tr.matchNotes));
            System.out.println(tmpMap.entrySet().size());
            System.out.println(tmpMap);
    }

    private void analyzeTransaction2(BankTransaction bt) {
        bt.note("===========Fakk==============");
        try {
            if (bt.siblingsSuspected) {
                siblings.add(bt);
                bt.note("siblings");
                return;
            }
            
            if(bt.isDoomed){
                leftOvers.add(bt);
                bt.note("Doomed");
                return;
            }
            
            List<Pupil> tmpList;
            if (!bt.hasDubiousSchool) {
                tmpList = new LinkedList(pupBySchoolMap.get(Integer.valueOf(bt.school)));
                var listWithlNames = tryFitNames(bt,tmpList);
                if (listWithlNames.size()==0){
                    if (!bt.hasDubiousKlass){                        
                        var listWithKlass = tryFitKlass(bt,tmpList);                        
                        var listWithfName = tryFitfName(bt,listWithKlass);                       
                        if (listWithfName.size()==0) {
                            if (listWithKlass.size() > 0) {
                               humanToDecide.put(bt, listWithKlass);
                               bt.note("School+ fName+ lname- klass++ ");
                               return;
                            } else {
                                leftOvers.add(bt);
                                bt.note("School+ lName- klass+ fname- ");
                                return;
                            }
                        } else if (listWithfName.size()==1){
                            fittedData.merge(listWithfName.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {o.addAll(n); return o; });
                            bt.note("School+ fname+ klass+ lName-");
                            return;
                        } else {
                            leftOvers.add(bt);
                            bt.note("School+ fname++ klass+ lName- ");
                            return;
                        } 
                            
                    } else {
                        leftOvers.add(bt);
                        bt.note("School+ lName- klass?");
                        return;
                    }
                } else if (listWithlNames.size()==1){
                    
                    /*if (fittedData.get(listWithlNames.get(0))!=null){
                        System.out.println(fittedData.getClass().getCanonicalName());
                        System.out.println(fittedData.get(listWithlNames.get(0)).getClass().getCanonicalName());
                        System.out.println(fittedData.get(listWithlNames.get(0)).getClass().getName());
                    }*/
                    fittedData.merge(listWithlNames.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {o.addAll(n); return o; });
                    bt.note("School+ lName+");
                    return;
                } else {
                    if(bt.hasDubiousKlass){
                    humanToDecide.put(bt, listWithlNames);
                    bt.note("School+ lname++ klass?");
                    return;
                    } else {
                        var listWithKlass = tryFitKlass(bt,tmpList);
                        if (listWithKlass.size()==0) {
                            humanToDecide.put(bt, listWithlNames);
                            bt.note("School+ lname++ klass-");
                            return;
                        } else if (listWithKlass.size()==1) {
                            bt.note("School+ lName++ klass+");
                            fittedData.merge(listWithKlass.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {o.addAll(n); return o; });
                            return;
                        } else {
                            bt.note("School+ lName++ klass++");
                            humanToDecide.put(bt, listWithKlass);
                            return;
                        }
                            
                    }
                }
                    
            }
            
            if (!bt.hasDubiousKlass) {
                tmpList = new LinkedList(pupByKlassMap.get(bt.klass));
                var ListlName = tryFitNames(bt,tmpList);
                var ListwSchool = tryFindSchool(bt,ListlName);
                if (ListwSchool.size() > 1){
                    bt.note("School++ lName++ klass+");
                    humanToDecide.put(bt, ListwSchool);
                    return;
                } else if (ListwSchool.size() == 1){
                    bt.note("Klass+ school+ lname +");
                    fittedData.merge(ListwSchool.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {o.addAll(n); return o; });
                    return;
                } else {
                    if (ListlName.size()==0){
                        leftOvers.add(bt);
                        bt.note("Klas+ lname- ");
                        return;
                    } else if (ListlName.size()==1){
                        bt.note("Klas+ lname+ school-");
                        fittedData.merge(ListlName.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {o.addAll(n); return o; });
                        return;
                    } else{
                        bt.note("Klas+ lname++");
                        humanToDecide.put(bt, ListlName);
                        return;
                    }       
                   
                }
            }
            
            tmpList = new LinkedList<>(pupilList);
            //tmpList = tryFitNames(bt,tmpList);
            //do Some 
            leftOvers.add(bt);
            bt.note("School?");
            
        } catch (Exception e) {
            leftOvers.add(bt);
            bt.note("------Exception----------");
           // Gson gson = new GsonBuilder().setPrettyPrinting().create();
           // System.out.println(gson.toJson(bt));
           // e.printStackTrace(System.out);

        }
    }

private List<Pupil> tryFitKlass(BankTransaction bt, List<Pupil> lList) {
        List<Pupil> tmp = lList.stream()
                .filter(p->p.isMyKlass(bt.klass))
                .collect(Collectors.toCollection(LinkedList::new));
        return tmp;
    }    

private List<Pupil> tryFindSchool(BankTransaction bt, List<Pupil> lList) {
        List<Pupil> tmp = lList.stream()
                .filter(p->p.isMySchoolHere(bt.klass))
                .collect(Collectors.toCollection(LinkedList::new));
        return tmp;
    } 

    private List<Pupil> tryFitfName(BankTransaction bt, List<Pupil> lList) {
        List<Pupil> tmp = lList.stream()
                .filter(p->p.isMyfNameHere(bt.niceString))
                .collect(Collectors.toCollection(LinkedList::new));
        return tmp;
    }

    private List<Pupil> tryFitNames(BankTransaction bt, List<Pupil> lList) {
        List<Pupil> tmp = lList.stream()
                .filter(p->p.isMylNameHere(bt.niceString))
                .collect(Collectors.toCollection(LinkedList::new));
        return tmp;
    }    
    
    private void analyzeTransaction(BankTransaction bt) {
        try {
            bt.checkForSiblings();
            if (bt.siblingsSuspected) {
                siblings.add(bt);
                return;
            }
            List<Pupil> tmpList;
            if (!bt.hasDubiousSchool) {
                tmpList = new LinkedList(pupBySchoolMap.get(Integer.valueOf(bt.school)));
                tmpList.removeIf(p -> !p.isMylNameHere(bt.niceString));
                //TODO better last check, Adding two lists (merge?)
                if (tmpList.size() == 1) {
                    fittedData.merge(tmpList.get(0), List.of(bt), (o, n) -> {
                        o.addAll(n);
                        return o;
                    });
                } else if (tmpList.size() > 1) {
                    int index = -1;
                    for (int i = 0; i < tmpList.size(); i++) {
                        var e = tmpList.get(i);
                        if (bt.hasDubiousSchool) {
                            if (e.isMyKlassHere(bt.niceString)) {
                                if (index == -1) {
                                    index = tmpList.indexOf(e);
                                } else {
                                    index = -100;
                                }
                            }
                        } else {
                            if (e.isMyKlass(bt.klass)) {
                                if (index == -1) {
                                    index = tmpList.indexOf(e);
                                } else {
                                    index = -100;
                                }
                            }
                        }
                    }
                    if (index > -1) {
                        fittedData.merge(tmpList.get(index), List.of(bt), (o, n) -> {
                            o.addAll(n);
                            return o;
                        });
                    } else {
                        leftOvers.add(bt);
                    }
                } else {
                    leftOvers.add(bt);
                }
            } else if (!bt.hasDubiousKlass) {
                tmpList = new LinkedList(pupByKlassMap.get(bt.klass));
                tmpList.removeIf(p -> !p.isMylNameHere(bt.niceString));
                //TODO better last check, Adding two lists (merge?)
                if (tmpList.size() == 1) {
                    fittedData.merge(tmpList.get(0), List.of(bt), (o, n) -> {o.addAll(n);return o;});
                } else {
                    humanToDecide.put(bt, tmpList);
                }
            } else {
                System.out.println(bt);
                leftOvers.add(bt);
            }
        } catch (Exception e) {
            leftOvers.add(bt);
        }
    }

    //List<Pupil> nameSearch(List<Pupil>)
    private void loadStuff() {
        //listaTransakcji = new BankFileParser("e:/smalllist.txt").getListaTransakcji();
        listaTransakcji = new BankFileParser(bankPath).getListaTransakcji();
        finData = new FinancialData()
                .importPaymentPerKlasses("e:/cenyvsnz.txt")
                .importschools("e:/zajwszk.txt");
        
        pupilList = new GAppsParser(pupPath).pupils;

    }
    
    
    
    private void makeStructures() {
        pupBySchoolMap = pupilList.stream().collect(Collectors.groupingBy(e -> e.schoolNr));
        pupByKlassMap = pupilList.stream().collect(Collectors.groupingBy(e -> e.klass));
        fittedData = new HashMap<>();
        humanToDecide = new HashMap<>();
        leftOvers = new LinkedList<>();
        siblings = new LinkedList<>();
    }

    public void SetBankPath(String path){
        this.bankPath = path;
    }
    
    public void SetPupPath (String path){
        this.pupPath = path;
    }
    
    public void addToFittedData(Pupil p, BankTransaction bt){
        fittedData.merge(p, List.of(bt), (o, n) -> {o.addAll(n);return o;});
    }
    
    public void removeFromHumanToDecide(BankTransaction bt){
        humanToDecide.remove(bt);
    } 

    public void addToLeftOvers(BankTransaction bt){
        leftOvers.add(bt);
    }
    
    public void save(){
        
        FileWriter fw = null;
        try {
            fw = new FileWriter("e:/leftovers.txt");
            for(var p:leftOvers) {
                fw.write(p.saveTransaction());
            }   for(var p:siblings) {
                fw.write(p.saveTransaction());
            }   for(var p:humanToDecide.keySet()) {
                fw.write(p.saveTransaction());
            }
            
            fw = new FileWriter("e:/output.txt");
                for(var p: fittedData.keySet()){
                    
                    fw.write(p.processTransactions(fittedData.get(p)).getFileLine());
                }
        } catch (IOException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}


