/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 *
 * @author piko
 */

@Accessors(fluent = true, chain = true)
@Getter
public class TransactionInfo {
    
               //pole z mięskiem do dalszej analizy
    LocalDate date;         //data wpłaty
    int amount;      
    String title; 
    String account; 

    public TransactionInfo(BankTransaction bt) {
        this.title = bt.title;
        this.date = bt.date;
        this.amount =  bt.amount;
        this.account =  bt.account;
    }

    
    
    public boolean sameAs(BankTransaction other) {
        
        if (this.amount != other.amount) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.account, other.account)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TransactionInfo{" + "date=" + date + ", amount=" + amount + ", title=" + title + ", account=" + account + '}';
    }
    
    
    
}
