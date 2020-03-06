/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.Model;

import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.luccasso.mailownik.TransactionInfo;

/**
 *
 * @author piko
 */

@Accessors(fluent = true, chain = true)
@Getter
@Setter
@NoArgsConstructor
public class PaymentsData{
     Set <String> accountNrs;
     List <TransactionInfo> transactions;
 }