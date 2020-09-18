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
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AddProtectedRangeRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.DeleteProtectedRangeRequest;
import com.google.api.services.sheets.v4.model.DeleteSheetRequest;
import com.google.api.services.sheets.v4.model.DuplicateSheetRequest;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.ProtectedRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author piko
 */
class GoogleService {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private String spreadsheetId = "17p_yeQE5WHdJIvbzDdF1qPWfqPVIf-MXrxvjN4wMskU";
    Sheets service;
    private static GoogleService instance;
    Spreadsheet spreadSheet;
    private int protectedRangeId;

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

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

    public static GoogleService getInstance() {
        if (instance == null) {
            try {
                instance = new GoogleService();
            } catch (IOException | GeneralSecurityException ex) {
                Logger.getLogger(GoogleService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    private GoogleService() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

    }

    public String getSpreadsheetId() {
        return spreadsheetId;
    }

    public void setSpreadsheetId(String newID) {
        spreadsheetId = newID;
    }

    public GoogleService initializeWith(String ssId) throws IOException {
        spreadsheetId = ssId;
        return initialize();
    }

    public GoogleService initialize() throws IOException {
        spreadSheet = service.spreadsheets().get(spreadsheetId).execute();
        return this;
    }

    List<List<Object>> getValues(String range) throws IOException {
        return service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute().getValues();
    }

    void unProtectSheet(String sheetName) throws IOException {
//        Sheet outputSheet = spreadSheet.getSheets().stream()
//                .filter(sheet -> sheet.getProperties().getTitle().equals(sheetName))
//                .findFirst().get();

//        var grid = new GridRange().setSheetId(outputSheet.getProperties().getSheetId());
//
//        var protectedRange = new ProtectedRange()
//                .setRange(grid);
        var addPRReq = new DeleteProtectedRangeRequest().setProtectedRangeId(protectedRangeId);

        var req = new Request()
                .setDeleteProtectedRange(addPRReq);

        var bathReq = new BatchUpdateSpreadsheetRequest()
                .setRequests(List.of(req));

        var bath = service.spreadsheets()
                .batchUpdate(spreadsheetId, bathReq)
                .execute();

        System.out.println(bath);
    }

    void protectSheet(String sheetName) throws IOException {

        Sheet outputSheet = spreadSheet.getSheets().stream()
                .filter(sheet -> sheet.getProperties().getTitle().equals(sheetName))
                .findFirst().get();

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
                .batchUpdate(spreadsheetId, bathReq)
                .execute();

        System.out.println(bath);
        protectedRangeId = bath.getReplies().get(0).getAddProtectedRange().getProtectedRange().getProtectedRangeId();
        System.out.println(protectedRangeId);
    }
    
    public Integer duplicateSheet(String oldName, String newName) throws IOException{
        
        Sheet originalSheet = spreadSheet.getSheets().stream()
                .filter(sheet -> sheet.getProperties().getTitle().equals(oldName))
                .findFirst().get();
        
        DuplicateSheetRequest requestDuplicate = new DuplicateSheetRequest()
                .setNewSheetName(newName)
                .setSourceSheetId(originalSheet.getProperties().getSheetId());
        
        var request = new Request().setDuplicateSheet(requestDuplicate);
        
        BatchUpdateSpreadsheetRequest batchRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(List.of(request));
        
        var result = service.spreadsheets().batchUpdate(spreadsheetId, batchRequest).execute();
        
        System.out.println(result);
        
        return result.getReplies().get(0).getDuplicateSheet().getProperties().getSheetId();
        
       // return null;
        
        
    }

    void deleteSheet(Integer sheetID) throws IOException {
        var delRequest = new DeleteSheetRequest().setSheetId(sheetID);
        var request = new Request().setDeleteSheet(delRequest);
        BatchUpdateSpreadsheetRequest batchRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(List.of(request));
        
        var result = service.spreadsheets().batchUpdate(spreadsheetId, batchRequest).execute();
        
        System.out.println(result);
        
        //return null;
                }
    
    
}
