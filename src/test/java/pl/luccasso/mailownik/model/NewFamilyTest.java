/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.model;

import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.JUnitSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import pl.luccasso.utils.SinglePupilBuilder;
import org.junit.jupiter.api.extension.ExtendWith;




/**
 *
 * @author piko
 */
@ExtendWith(SoftAssertionsExtension.class)
public class NewFamilyTest {
    
    
    
    @Test
    public void testSingleFamily(SoftAssertions softly) {
        SinglePupilBuilder sp = new SinglePupilBuilder();
        
        var family = new NewFamily(sp.createSinglePupil());
        
        softly.assertThat(family.childrens().get(0).fName())
                .as("First Name")
                .isEqualTo("Adam100");
        softly.assertThat(family.childrens().get(0).lName())
                .as("Last Name")
                .isEqualTo("Byk100");
        softly.assertThat(family.childrens().get(0).id().id()).as("Id").isEqualTo(500);
        softly.assertThat(family.contacts().nTel()).as("tel").isEqualTo("500000000100");
        softly.assertThat(family.contacts().eMail()).as("email").isEqualTo("AdamByk@def.pl100");
        
        System.out.println(family);
    }
    
}
