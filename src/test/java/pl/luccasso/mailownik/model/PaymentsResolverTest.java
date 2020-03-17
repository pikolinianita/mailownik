/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.model;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.luccasso.mailownik.DoCompare;
import pl.luccasso.mailownik.config.ConfigF;

/**
 *
 * @author piko
 */
@ExtendWith(MockitoExtension.class)
public class PaymentsResolverTest {

    DoCompare dc;

    @Mock
    NewFamily family;

    @BeforeAll
    public static void setUpClass() {

    }

    @BeforeEach
    public void setUp() {

        dc = new DoCompare();
        try {
            ConfigF.restoreCleanTestConfiguration();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewFamilyTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Problem with restore clean configuration");
        }
        dc.loadStuff();

    }

    @Test
    public void testResolveNoPaySingle() {

        when(family.size()).thenReturn(1);
        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(0);
        when(family.getNumberPaidKlasses()).thenReturn(10);
        when(family.getNumberUspraw()).thenReturn(2);

        var resultToDo = new PaymentsResolver(family);
        var result = resultToDo.moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("No Payments").isEqualTo("Brak wpłat");
        softly.assertThat(result.nZajec).as("Number of classes").isEqualTo(16);
        softly.assertThat(result.needToPay).as("Need to pay").isEqualTo(350);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(490);

        softly.assertAll();

    }

    @Test
    public void testResolveNoPayDouble() {

        when(family.size()).thenReturn(2);
        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(0);
        when(family.getNumberPaidKlasses()).thenReturn(10);
        when(family.getNumberUspraw()).thenReturn(2);

        var result = new PaymentsResolver(family).moneyResolve();
        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("No Payments").isEqualTo("Brak wpłat");
        softly.assertThat(result.nZajec).as("Number of classes").isEqualTo(16);
        softly.assertThat(result.needToPay).as("Need to pay").isEqualTo(330);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(990);

        softly.assertAll();
    }

    @Test
    public void testResolveNoPayTriple() {

        when(family.size()).thenReturn(3);
        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(0);
        when(family.getNumberPaidKlasses()).thenReturn(10);
        when(family.getNumberUspraw()).thenReturn(2);

        var result = new PaymentsResolver(family).moneyResolve();
        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("No Payments").isEqualTo("Brak wpłat");
        softly.assertThat(result.nZajec).as("Number of classes").isEqualTo(16);
        softly.assertThat(result.needToPay).as("Need to pay").isEqualTo(330);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(1518);

        softly.assertAll();
    }

    @Test
    public void testResolveYearlySingle() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(465);
        when(family.size()).thenReturn(1);

        var result = new PaymentsResolver(family).moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("yearly pay ok").isEqualTo("Wpłata Roczna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(0);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(465);

        softly.assertAll();
    }

    @Test
    public void testResolveYearlySingleWrong() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(407);
        when(family.size()).thenReturn(1);

        var result = new PaymentsResolver(family).moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("yearly pay ok").isEqualTo("Wpłata Roczna - zla ilosc zajec");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(58);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(465);

        softly.assertAll();
    }

    @Test
    public void testResolveHalfYearSingleHalfPaidSecSem() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(233);
        when(family.size()).thenReturn(1);

        var result = new PaymentsResolver(family)
                .moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("Semester pay ok").isEqualTo("Wpłata semestralna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(233);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(233 * 2);

        softly.assertAll();
    }

    @Test
    public void testResolveHalfYearSingleHalfPaidFirstSem() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(233);
        when(family.size()).thenReturn(1);
        ConfigF.setSecondSemester(false);

        var result = new PaymentsResolver(family).moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("Semester pay ok").isEqualTo("Wpłata semestralna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(0);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(233 * 2);

        softly.assertAll();

        ConfigF.setSecondSemester(true);
    }

    @Test
    public void testResolveHalfYearSingleAllPaid() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(466);
        when(family.size()).thenReturn(1);

        var result = new PaymentsResolver(family).moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("Semester pay ok").isEqualTo("Wpłata semestralna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(0);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(233 * 2);

        softly.assertAll();
    }

    @Test
    public void testResolveYearlyDouble() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(876);
        when(family.size()).thenReturn(2);

        var result = new PaymentsResolver(family).moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("yearly pay ok").isEqualTo("Wpłata Roczna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(0);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(876);

        softly.assertAll();
    }

    @Test
    public void testResolveYearlyDoubleWrong() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(767);
        when(family.size()).thenReturn(2);

        var result = new PaymentsResolver(family)
                .moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("yearly pay not ok").isEqualTo("Wpłata Roczna - zla ilosc zajec");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(109);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(876);

        softly.assertAll();
    }

    @Test
    public void testResolveYearlyTriple() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(1314);
        when(family.size()).thenReturn(3);

        var result = new PaymentsResolver(family).moneyResolve();

        var softly = new SoftAssertions();
        //Wrong - cannot differ right now
        //softly.assertThat(result.description()).as("yearly pay ok").isEqualTo("Wpłata Roczna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(0);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(1314);

        softly.assertAll();
    }

    @Disabled
    @Test
    public void testResolveYearlyTripleWrong() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(1150);
        when(family.size()).thenReturn(3);

        var result = new PaymentsResolver(family).moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("yearly pay not ok").isEqualTo("Wpłata Roczna - zla ilosc zajec");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(164);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(1314);

        softly.assertAll();
    }

    @Test
    public void testResolveHalfYearDoubleHalfPaidSecSem() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(438);
        when(family.size()).thenReturn(2);

        var result = new PaymentsResolver(family).moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("Semester pay ok").isEqualTo("Wpłata semestralna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(438);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(438 * 2);

        softly.assertAll();
    }

    @Test
    public void testResolveHalfYearDoubleHalfPaidFirstSem() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(438);
        when(family.size()).thenReturn(2);
        ConfigF.setSecondSemester(false);

        var result = new PaymentsResolver(family).moneyResolve();

        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("Semester pay ok").isEqualTo("Wpłata semestralna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(0);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(438 * 2);

        softly.assertAll();
        ConfigF.setSecondSemester(true);
    }

    @Test
    public void testResolveHalfYearDoubleeAllPaid() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(438 * 2);
        when(family.size()).thenReturn(2);

        var result = new PaymentsResolver(family).moneyResolve();

        var softly = new SoftAssertions();
        //Wrong - cannot differ right now
        //softly.assertThat(result.description()).as("Semester pay ok").isEqualTo("Wpłata semestralna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(0);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(438 * 2);

        softly.assertAll();
    }

    @Test
    public void testResolveMonthlyOk() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(350);
        when(family.size()).thenReturn(1);
        when(family.getNumberPaidKlasses()).thenReturn(10);
        when(family.getNumberUspraw()).thenReturn(2);

        var result = new PaymentsResolver(family)
                .moneyResolve();
        
        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("Monthly pay ok").isEqualTo("Wpłata miesieczna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(0);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(490);
        
         softly.assertAll();
    }
    
    @Test
    public void testResolveMonthlyOkNotOk() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(350);
        when(family.size()).thenReturn(1);
        when(family.getNumberPaidKlasses()).thenReturn(12);
        when(family.getNumberUspraw()).thenReturn(2);

        var result = new PaymentsResolver(family)
                .moneyResolve();
        
        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("Monthly pay ok").isEqualTo("Wpłata miesieczna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(70);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(490);
        
         softly.assertAll();
}
    
    @Test
    public void testResolveMonthlyDoubleOk() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(330);
        when(family.size()).thenReturn(2);
        when(family.getNumberPaidKlasses()).thenReturn(10);
        when(family.getNumberUspraw()).thenReturn(2);

        var result = new PaymentsResolver(family)
                .moneyResolve();
        
        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("Monthly pay ok").isEqualTo("Wpłata miesieczna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(0);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(990);
        
         softly.assertAll();
    }
    
    @Test
    public void testResolveMonthlyDoubleNotOk() {

        when(family.getSchoolNr()).thenReturn(50);
        when(family.getSumOfPayments()).thenReturn(330);
        when(family.size()).thenReturn(2);
        when(family.getNumberPaidKlasses()).thenReturn(12);
        when(family.getNumberUspraw()).thenReturn(2);

        var result = new PaymentsResolver(family)
                .moneyResolve();
        
        var softly = new SoftAssertions();
        softly.assertThat(result.description()).as("Monthly pay ok").isEqualTo("Wpłata miesieczna");
        softly.assertThat(result.needToPay).as("Need to Pay").isEqualTo(66);
        softly.assertThat(result.toPayForAllYearAmount).as("Need to pay in total").isEqualTo(990);
        
         softly.assertAll();
}
}
