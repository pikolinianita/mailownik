/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.luccasso.mailownik;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.stream.Collectors;

/**
 *
 * @author piko
 */
public class GAppsParser {

    List<Pupil> pupils;
    
    List<PupilImport> pupilsI;

    class PupilImport {

        int schoolNr;
        
        String tmpData;
        
        String fName;
        
        String lName;
        
        String klass;
        
        String[] timeSheet;
        
        String mTel;
        
        String fTel;
        
        String eMail;
        
        String skryptID;
        
        String nb; 
        

        private boolean isNice() {                   
                return !("".equals(lName) || "".equals(fName));
        }

        private void toLowerCase() {
         fName = fName.strip().toLowerCase();
         lName = lName.strip().toLowerCase();
         klass = klass.strip().toLowerCase();
        }
    }

    public GAppsParser(String filePath, List<Pupil> dBList) {
        if (dBList == null) {
            pupils = new LinkedList<>();
        } else {
            pupils = dBList;
        }
        
        try ( Scanner sc = new Scanner(new FileReader(filePath))){            
            pupilsI = new LinkedList<PupilImport>();            
            Gson gson = new Gson();
            while (sc.hasNext()) {
                PupilImport[] tmp = gson.fromJson(sc.nextLine(), PupilImport[].class);
                pupilsI.addAll(Arrays.asList(tmp));
            }            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GAppsParser.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("GAppsParser File Not Found");
        } 
        parseToPupils(pupilsI);
    }

    private void parseToPupils(List<PupilImport> pI) {
        for (var e : pI) {
            if (e.isNice()) {
                e.toLowerCase();
                List<Pupil> tmp = pupils.stream().filter(f->f.hasSameSkryptIdAs(e)).collect(Collectors.toList());
                //TODO poprawić Sprawdzanie jesli uczen już jest; 
                if (tmp.isEmpty()){ 
                    pupils.add(new SinglePupil(e));
                } else if(tmp.size()==1){
                    tmp.get(0).updateValuesWithGoogleData(new SinglePupil(e));
                } else System.out.println("Blad w parsowaniu uczniow:" + tmp);
            }
        }
    }
}
