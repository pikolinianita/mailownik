/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.persistence.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.BatchUpdate;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AddProtectedRangeRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.ProtectedRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     * @throws java.io.IOException
     * @throws java.security.GeneralSecurityException
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "17p_yeQE5WHdJIvbzDdF1qPWfqPVIf-MXrxvjN4wMskU";
        final String range = "Input!A1:G";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        
        Spreadsheet ss = service.spreadsheets()
                .get("17p_yeQE5WHdJIvbzDdF1qPWfqPVIf-MXrxvjN4wMskU")
                .execute();
        
        Sheet inputSheet = ss.getSheets().stream()
                .filter(sheet -> sheet.getProperties().getTitle().equals("Input"))
                .findFirst().get();
        
        Sheet outputSheet = ss.getSheets().stream()
                .filter(sheet -> sheet.getProperties().getTitle().equals("Output"))
                .findFirst().get();
        System.out.println(inputSheet);
        
        List<ValueRange> data = new ArrayList<>();
        data.add(new ValueRange()
                .setRange("Output!A1")
                .setValues(Arrays.asList(
                        Arrays.asList("January Total", "booo", 2, "kool", "", ""))));
        
        data.add(new ValueRange()
                .setRange("Output!A2")
                .setValues(Arrays.asList(
                        Arrays.asList("February Total", "dzuu", 2.5, (double)7/3, true))));

        BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
                .setValueInputOption("USER_ENTERED")
                .setData(data);

        BatchUpdateValuesResponse batchResult = service.spreadsheets().values()
                .batchUpdate("17p_yeQE5WHdJIvbzDdF1qPWfqPVIf-MXrxvjN4wMskU", batchBody)
                .execute();
        
        System.out.println(batchResult);
       // protectSheet(outputSheet, service, "17p_yeQE5WHdJIvbzDdF1qPWfqPVIf-MXrxvjN4wMskU");
        
//        try{
//        protectSheet(outputSheet, service, "17p_yeQE5WHdJIvbzDdF1qPWfqPVIf-MXrxvjN4wMskU");
//        } catch (GoogleJsonResponseException e){
//            System.out.println("aa");
//            System.out.println(e.getClass());
//            System.out.println("zz");
//            System.out.println(e.getMessage());
//        }
//        System.out.println("cc");


        
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                System.out.printf("%s, %s\n", row.get(1), row.get(6));
            }
        }
    }

    public static void protectSheet(Sheet outputSheet, Sheets service, String sheetName) throws IOException {
        //---------------------------------------
        //---- secure Sheet ---------------------
        //------ empty grid = all sheet
        //--throws com.google.api.client.googleapis.json.GoogleJsonResponseException; if already locked
        var grid = new GridRange().setSheetId(outputSheet.getProperties().getSheetId());
        
        var protectedRange = new ProtectedRange()
                .setRange(grid);
        
        var addPRReq = new AddProtectedRangeRequest()
                .setProtectedRange(protectedRange);
        
        var req = new Request()
                .setAddProtectedRange(addPRReq);
       
        var bathReq = new BatchUpdateSpreadsheetRequest()
                .setRequests(List.of(req));
        
        var bath = service.spreadsheets()
                .batchUpdate(sheetName, bathReq)
                .execute();
        
        System.out.println(bath);
    }
}