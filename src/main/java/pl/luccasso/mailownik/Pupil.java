/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.util.List;
import java.util.Set;

/**
 *
 * @author piko
 */
public interface Pupil {

    String getFileLine();

    String getPupilDataNoTimeSheet();

    String getShortUniqueString();

    boolean hasSameSkryptIdAs(GAppsParser.PupilImport other);

    boolean isMyKlass(String kl);

    boolean isMyKlassHere(String q);

    boolean isMySchool(Integer n);

    boolean isMySchoolHere(String q);

    boolean isMyfNameHere(String q);

    boolean isMylNameHere(String q);
    
    void updateValuesWithGoogleData(Pupil p);
    
    String getSkryptId();
    
    Pupil processTransactions(List<BankTransaction> lisT);
    
    public int getSchoolNr();

    public String getKlass();

    public Set<String> getAccountNrs();
    
    public int getNb();

    public String getFName();

    public String getLName();

    public String getNTel();

    public String getNTel2();

    public String getEMail();

    public String[] getTimeSheet();
}
