/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.persistence;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.Pupil;
import pl.luccasso.mailownik.SinglePupil;
import pl.luccasso.mailownik.model.NewFamily;

/**
 *
 * @author piko
 */
         
@ToString
@Accessors(fluent = true, chain = true)
@Getter
@Setter
@NoArgsConstructor
public class DataBase {

    private List<Pupil> pupilList;
    
    private List<NewFamily> neuFamilyList;
    
    public List<BankTransaction> listaTransakcji;
    
    public List<BankTransaction> leftOvers;
    
    // uporzadkowane dane z Obecności
    public Map<Integer, List<Pupil>> pupBySchoolMap;
    
    public Map<String, List<Pupil>> pupByKlassMap;
    
    public Map<String, List<Pupil>> pupByAccountMap;
    
    public Map<BankTransaction, List<Pupil>> humanToDecide;
    
    //Wynikowe Dane
    public Map<Pupil, List<BankTransaction>> fittedData;
    
    public Map<Pupil, List<BankTransaction>> sibFitted;
    
    public List<BankTransaction> siblings;
    
    public List<String> wrongLines;    
    
    private Map<Integer, List<NewFamily>> famBySchoolMap;
    
    private Map<String, List<NewFamily>> famByKlassMap;
    
    private Map<String, List<NewFamily>> famByAccountMap;
    
    private Map<NewFamily, List<BankTransaction>> famFittedData;
    
     public Map<BankTransaction, List<NewFamily>> humanFamilyToDecide;

    public void makeStructures() {
        pupBySchoolMap = pupilList().stream().collect(Collectors.groupingBy((e) -> e.getSchoolNr()));
        pupByKlassMap = pupilList().stream().collect(Collectors.groupingBy((e) -> e.getKlass()));
        pupByAccountMap = new HashMap<>();
        for (Pupil p : pupilList()) {
            for (String acc : p.getAccountNrs()) {
                pupByAccountMap.merge(acc, new LinkedList(List.of(p)), (o, n) -> {
                    o.addAll(n);
                    return o;
                });
            }
        }
        fittedData = new HashMap<>();
        humanToDecide = new HashMap<>();
        leftOvers = new LinkedList<>();
        siblings = new LinkedList<>();
        sibFitted = new HashMap<>();
        makeFamilyStructures();
    }

    private void makeFamilyStructures() {
        famBySchoolMap = neuFamilyList().stream().collect(Collectors.groupingBy((e) -> e.getSchoolNr()));
        //famByKlassMap = neuFamilyList().stream().collect(Collectors.groupingBy((e) -> e.getKlass()));
        famByAccountMap = new HashMap<>();
        for (NewFamily p : neuFamilyList()) {
            for (String acc : p.getAccountNrs()) {
                famByAccountMap.merge(acc, new LinkedList(List.of(p)), (o, n) -> {
                    o.addAll(n);
                    return o;
                });
            }
        }
        famFittedData = new HashMap<>();
        humanFamilyToDecide = new HashMap<>();
    }

    public List<NewFamily> convertPupilListToFamilyList(List<Pupil> list) {
        LinkedList<NewFamily> familyList = new LinkedList<NewFamily>();
        outer:
       for (Pupil pup : list) {
            for (NewFamily fam : familyList) {
                if (fam.isMyBrother(pup)) {
                    fam.add((SinglePupil) pup);
                    break;
                }
            }
            familyList.add(new NewFamily((SinglePupil) pup));
        }
        return familyList;
    }
    
}
