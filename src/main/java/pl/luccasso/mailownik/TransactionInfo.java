/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author piko
 */
class TransactionInfo {
    
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

    
    
    public boolean equals(BankTransaction other) {
        
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
    
    
    
}
