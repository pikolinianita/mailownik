/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author piko
 */
public class Family implements Pupil{

    private List<SinglePupil> siblings;
    
    Set<String> accountNrs;
    List<TransactionInfo> transactions;
    int allPayments;
    boolean AllYear;
    boolean oneSemester;
    boolean isSibling;

    public Family() {
        this.siblings = new LinkedList<>();
    }
    
    @Override
    public String getFileLine() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPupilDataNoTimeSheet() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getShortUniqueString() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasSameSkryptIdAs(GAppsParser.PupilImport other) {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isMyKlass(String kl) {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isMyKlassHere(String q) {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isMySchool(Integer n) {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isMySchoolHere(String q) {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isMyfNameHere(String q) {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isMylNameHere(String q) {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateValuesWithGoogleData(Pupil p) {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSkryptId() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pupil processTransactions(List<BankTransaction> lisT) {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getSchoolNr() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getKlass() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> getAccountNrs() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNb() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFName() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLName() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNTel() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNTel2() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEMail() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] getTimeSheet() {
        throw new SiblingsException("Unimplemented Family function called"); //To change body of generated methods, choose Tools | Templates.
    }
    
}
