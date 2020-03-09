/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author piko
 */

@ToString
@Accessors(fluent = true, chain = true)
@Getter
@Setter
@NoArgsConstructor
public class TransactionStringBuilder {
    String date = "3000-01-01";
    
    //fullTitle
    String title="";
    
    //title parts
    String name = "Jacek Kurek, Kurczatowa 145, Kórnik 123";
    String transactionTitle = " polata za mlodego SP 888, 2c, Heniek Kurek";
    String addressStreet = "Woronicza 135";
    String addressCity = "00-735 Warszawa";
    //nie rusz
    String kindOfTransationOPT = "PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY";    
    String account = "1234567890";
    
    //Nie rusz
    String mBiznesOPT = "mBiznes konto pomocnicze 0711 ... 2221";
    //Nie rusz
    String wplywyOPT = "Wpływy - inne";
    
    String pln = "70,00 PLN";
    
    public String create(){
        if ("".equals(title)){
            title (String.join("   ", name() + ',' + transactionTitle(),addressStreet(), addressCity() , kindOfTransationOPT(), account()));
        }
        return String.join(";", date(), title(),mBiznesOPT(),wplywyOPT(),pln());
    }
    
}
