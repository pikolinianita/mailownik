/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.model;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 *
 * @author piko
 */
public class PaymentsResolver {
    NewFamily family;

    PaymentsResolver(NewFamily aThis) {
        family = aThis;
    }
    
    PaymentDTO moneyResolve(){
        
        return new PaymentDTO();
    }

    @Accessors(fluent = true, chain = true)
    @Getter
    static class PaymentDTO {
        
        //Kind of payments - monthy, yearly...
        String description;
        
        //Sum of paid money
        int totalPaidAmount;
        
        //... assuming 100% attendance
        int toPayForAllYearAmount;
        
        //debt
        int needToPay;
        
        //number courses for school
        int nZajec;
        
        }    
}
