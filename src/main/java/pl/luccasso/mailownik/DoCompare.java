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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Scanner;
import pl.luccasso.mailownik.config.ConfigF;

/**
 *
 * @author piko
 */
public class DoCompare {
    //String pupPath, bankPath, savedPath;
    //czyste dane:
    static FinancialData finData;
    List<BankTransaction> listaTransakcji;
    List<Pupil> pupilList;
    List <String> wrongLines;
    // uporzadkowane dane z Obecności
    Map<Integer, List<Pupil>> pupBySchoolMap;
    Map<String, List<Pupil>> pupByKlassMap;
    Map<String, List<Pupil>> pupByAccountMap;
    //Wynikowe Dane
    Map<Pupil, List<BankTransaction>> fittedData;
    Map<BankTransaction, List<Pupil>> humanToDecide;
    List<BankTransaction> leftOvers;
    List<BankTransaction> siblings;
    Map<Pupil, List<BankTransaction>> sibFitted;

    public static void main(String[] args) {
        var dc = new DoCompare();
        dc.doWork();
        dc.save();
    }
    
    public Map<BankTransaction, List<Pupil>> getToBeDecidedMap(){
        return humanToDecide;
    }
    
    public DoCompare() {
        //pupPath = "e:/pupils.txt";
        //bankPath = "e:/listatestowa.csv";
        //savedPath = null;
    }

    
    
    public void doWork() {        
        loadStuff();
        makeStructures();
        writeMapsToSout(); //debug
        listaTransakcji.forEach(this::analyzeTransaction2);
        writeListsToSout(); //debug
    }

    void writeListsToSout() {
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
        
        var tmpList = new LinkedList<>(listaTransakcji);
        tmpList.removeAll(siblings);
        tmpList.removeAll(leftOvers);
        tmpList.removeAll(humanToDecide.keySet());
        fittedData.values().forEach(e -> {
            tmpList.removeAll(e);
        });
        
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
        Map tmpMap = listaTransakcji.stream().collect(Collectors.groupingBy((tr) -> tr.matchNotes));
        System.out.println(tmpMap.entrySet().size());
        System.out.println(tmpMap);
    }

    void writeMapsToSout() {
        System.out.println("---------------org lista trans------");
        System.out.println(listaTransakcji.size());
        System.out.println("-------Set----------");
        System.out.println(pupByKlassMap.keySet());
        System.out.println("-------Set----------");
        System.out.println(pupBySchoolMap.keySet());
    }

    private void analyzeTransaction2(BankTransaction bt) {
        bt.note("===========Fakk==============");
        try {
            if(pupByAccountMap.containsKey(bt.account)){
                var pList = pupByAccountMap.get(bt.account);
                if (pList.size()>1) {
                    siblings.add(bt);
                    bt.note("siblings - fitted by account");
                    return;
                }                
                fittedData.merge(pList.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {o.addAll(n); return o; });
                bt.note("account");
                return;
            }
            
            
            bt.checkForSiblings();
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
            System.out.println("anal2: -Ex- : " + bt.title);
            //e.printStackTrace(System.out);
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
    
    
    //List<Pupil> nameSearch(List<Pupil>)
    void loadStuff() {
        
        finData = new FinancialData()
                .importPaymentPerKlasses(ConfigF.getPayPerClass()) //"e:/cenyvsnz.txt
                .importschools(ConfigF.getClassPerSchool());
        BankFileParser.finData=this.finData;
        var parser = new BankFileParser(ConfigF.getBankPath());
        listaTransakcji = parser.getListaTransakcji();
        wrongLines = parser.getWrongLines();
        pupilList = loadPreviousData(ConfigF.getSavedPath());
        pupilList = new GAppsParser(ConfigF.getPupPath(), pupilList).pupils;
        

    }
    
    
    
    private void makeStructures() {
        pupBySchoolMap = pupilList.stream().collect(Collectors.groupingBy(e -> e.getSchoolNr()));
        pupByKlassMap = pupilList.stream().collect(Collectors.groupingBy(e -> e.getKlass()));
        pupByAccountMap = new HashMap<>();
        for (var p: pupilList){
            for(var acc: p.getAccountNrs()) {
                pupByAccountMap.merge(acc, new LinkedList(List.of(p)),(o,n)->{o.addAll(n);return o;});
            }
        }
        //
        fittedData = new HashMap<>();
        humanToDecide = new HashMap<>();
        leftOvers = new LinkedList<>();
        siblings = new LinkedList<>();
        sibFitted = new HashMap<>();
    }

    public void SetBankPath(String path){
        ConfigF.setBankPath(path); //TODO remove this Function
    }
    
    public void SetPupPath (String path){
       ConfigF.setPupPath(path); //TODO remove this Function
    }
    
    public void addToFittedData(Pupil p, BankTransaction bt){
        fittedData.merge(p, new LinkedList<>(List.of(bt)), (o, n) -> {o.addAll(n);return o;});
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
            for (var p : leftOvers) {
                fw.write(p.saveTransaction());
            }
            
            for (var p : humanToDecide.keySet()) {
                fw.write(p.saveTransaction());
            }            
            fw.close();
            
            fw = new FileWriter("e:/siblings_org.txt");
            for (var p : siblings) {
                fw.write(p.saveTransaction());
            }
            fw.close();
            
           
            
            fw = new FileWriter("e:/syfy.txt");
            for (var p : wrongLines){
                fw.write(p);
            }
            fw.close();
                    
            fw = new FileWriter("e:/output.txt");
            /*for (var p : fittedData.keySet()) {
                fw.write(p.processTransactions(fittedData.get(p)).getFileLine());
            }*/
            fw.write(String.join("\t", "Id","SkryptID","Szkoła","Imie","Nazwisko","Klasa","Tel Mamy","Tel Taty","Mail",
                "Zaj 1","2","3","4","5","6","7","8","9","10","11","12", "13","14","15","16","17","18","19","20","obecny","Nieobecny","Usprawiedliwione",
                "Suma wpłat","Winien","wpłaty","W sumie zaplaci","Ilosc zajec w szkole","DanePrzelewow","konta")+"\n");
            for (var p :pupilList){
                fw.write(p.processTransactions(fittedData.get(p)).getFileLine());
            }
            fw.close();
            
            fw = new FileWriter("e:/siblings_calc.txt");
            fw.write(String.join("\t", "Id","SkryptID","Szkoła","Imie","Nazwisko","Klasa","Tel Mamy", "Tel Taty","Mail",
                "Zaj 1","2","3","4","5","6","7","8","9","10","11","12", "13","14","15","16","17","18","19","20","obecny","Nieobecny","Usprawiedliwione",
                "Suma wpłat","Winien","wpłaty","W sumie zaplaci","Ilosc zajec w szkole","DanePrzelewow","konta"+"\n"));
            for (var p: sibFitted.keySet()){
                fw.write(p.processTransactions(sibFitted.get(p)).getFileLine());
            }
            fw.close();
            
            
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

    
    public List<BankTransaction> getLeftOvers() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Kurka - getLeftOvers");   
        return leftOvers;
    }
    
    public List<Pupil> getPupilList(){
        return pupilList;
    }
    
    public Map<Integer, List<Pupil>> getPupBySchoolMap(){
           return pupBySchoolMap;
    }

    public List <String> getWrongLinesList() {
         return wrongLines;
    }

    public  List<BankTransaction> getSiblingsBTList() {
        return siblings;
    }
    
    public int getAmountOfFittedTransactions(){
        return (int) fittedData.values().stream().flatMap(li->li.stream()).count();
    }

    public void pushLinesToSiblings(BankTransaction bt, List<Pupil> chosenSiblings) {
        int nSiblings = chosenSiblings.size();
        System.out.println("Lista rodzenstwa to: " +  nSiblings);
        System.out.println("sib Fitted: " + sibFitted.keySet().size());
        for(var p: chosenSiblings){
            BankTransaction tmpTrans = bt.divideCashAndReturnNew(nSiblings);
            sibFitted.merge(p, new LinkedList<>(List.of(bt)), (o, n) -> {o.addAll(n); return o; });
            
        }
        siblings.remove(bt);
            
    }

    List<Pupil> loadPreviousData(String inpPath) {
        if (inpPath == null) {
            return null;
        }
        FileReader fr = null;
        Scanner sc = null;
        List<Pupil> pupList = new LinkedList<>(); 
        try {
            fr = new FileReader(inpPath);
            sc = new Scanner(fr);
            sc.nextLine();
            while(sc.hasNext()){
                 pupList.add(new SinglePupil(sc.nextLine()));
            }
            sc.close();
            return pupList;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (sc != null) {
                    sc.close();
                }
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(DoCompare.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    return null;
    }

    public void SetDBPath(String dBPath) {
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
}


