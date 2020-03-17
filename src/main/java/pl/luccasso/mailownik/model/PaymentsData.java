/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.luccasso.mailownik.model;

import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.SinglePupil;
import pl.luccasso.mailownik.TransactionInfo;

/**
 *
 * @author piko
 */
@ToString
@Accessors(fluent = true, chain = true)
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
public class PaymentsData{
    
     Set <String> accountNrs;
     
     List <TransactionInfo> transactions;

    void amendWith(SinglePupil sp) {
        //TODO Do something with it
        accountNrs().addAll(sp.getAccountNrs());
        //add only single transactions
        for(var otherTi : sp.getTransactions()){
            if (addedTRIfNotPresent(otherTi)) {
                break;
            }
        }
    }

    private boolean addedTRIfNotPresent(TransactionInfo otherTi) {
        for (TransactionInfo ti : transactions) {
            if (ti.sameAs(otherTi)) {
                return false;
            }
        }
        transactions.add(otherTi);
        return true;
    }

    int toalPayments() {
        return transactions.stream()
                .mapToInt(t->t.amount())
                .sum();
    }

    void addTransaction(BankTransaction bt) {
        
        accountNrs.add(bt.account());
        addedTRIfNotPresent(new TransactionInfo(bt));
        //transactions.add(new TransactionInfo(bt));
      
    }

    int getSumOfPayments() {
      return transactions.stream().mapToInt(ti->ti.amount()).sum();
    }
    
     int[] getValuesArray() {
        return transactions.stream()
                .mapToInt(ti -> ti.amount())
                .toArray();
    }
 }
