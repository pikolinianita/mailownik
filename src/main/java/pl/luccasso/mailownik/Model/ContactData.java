/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author piko
 */

@Accessors(fluent = true, chain = true)
@Getter
@Setter
@NoArgsConstructor
public class ContactData{
    String nTel;
    String nTel2;
    String eMail;
}