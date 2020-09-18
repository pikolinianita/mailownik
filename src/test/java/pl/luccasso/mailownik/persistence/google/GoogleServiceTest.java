/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.persistence.google;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author piko
 */
public class GoogleServiceTest {

    private final String TEST_SS = "17p_yeQE5WHdJIvbzDdF1qPWfqPVIf-MXrxvjN4wMskU";

    @Test
    public void testConnection() throws IOException {
        var service = GoogleService.getInstance().initializeWith(TEST_SS);
        var result = service.service.spreadsheets().values().get(service.getSpreadsheetId(), "A1").execute();
        System.out.println(result);

        var service2 = GoogleService.getInstance().initializeWith(TEST_SS);
        var result2 = service2.service.spreadsheets().values().get(service.getSpreadsheetId(), "A1").execute();
        System.out.println(result2);
        assertThat(result).as("basic response").isNotNull().isEqualTo(result2);
    }

    @Test
    public void testRange() throws IOException {
        var service = GoogleService.getInstance().initializeWith(TEST_SS);
        var result = service.getValues("A1:B2");
        assertThat(result).as("2x2 list of lists")
                .isNotEmpty()
                .hasSize(2);
        
        assertThat(result.get(0)).as("first Row")
                .allMatch(Objects::nonNull);
    }

    @Test
    void protectionSheet() throws IOException {
        
        var service = GoogleService.getInstance().initializeWith(TEST_SS);

        try {

            service.protectSheet("Output");
            
            assertThatThrownBy(() -> {
                service.protectSheet("Output");
            })
                    .isInstanceOf(GoogleJsonResponseException.class)
                    .hasMessageContaining("already has sheet protection");
        } finally {
            service.unProtectSheet("Output");
        }
        
    }

    @Test
    void duplicate() throws IOException{
        var service = GoogleService.getInstance().initializeWith(TEST_SS);
        
        var ID = service.duplicateSheet("Output", "NewName");
      
        service.deleteSheet(ID);
    }
    
}
