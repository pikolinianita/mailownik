/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.time.LocalDate;

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
    
    
    
}
