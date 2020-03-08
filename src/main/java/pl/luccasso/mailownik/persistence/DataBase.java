/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.persistence;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.Pupil;

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

    public List<Pupil> pupilList;
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
    
}
