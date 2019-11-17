/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.util.Objects;

/**
 *
 * @author piko
 */
public class Pupil {
        static int idCount = 0;
        int id;
        int schoolNr;
        String fName;
        String lName;
        String klass;
    
   
    Pupil(){
        if (idCount == 0) {
            idCount=1000;
        }
    }
        
    /**
    * For testing Only
    * @param i
    * @param fN
    * @param lN
    * @param kl 
    */     
   Pupil (int i, String fN, String lN, String kl){
       this();
       this.id = idCount++;
       this.schoolNr = i;
       this.fName = fN.strip().toLowerCase();
       this.lName = lN.strip().toLowerCase();
       this.klass = kl.toLowerCase().strip();
   }     

    @Override
    public String toString() {
        return "Pupil{" + "schoolNr=" + schoolNr + ", fName=" + fName + ", lName=" + lName + ", klass=" + klass + '}';
    }
        
    Pupil(GAppsParser.PupilImport e) {
       this();
       this.id = idCount++;
       this.schoolNr = e.schoolNr;
       this.fName = e.fName.strip().toLowerCase();
       this.lName = e.lName.strip().toLowerCase();
       this.klass = e.klass.toLowerCase().strip();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.schoolNr;
        hash = 79 * hash + Objects.hashCode(this.fName);
        hash = 79 * hash + Objects.hashCode(this.lName);
        hash = 79 * hash + Objects.hashCode(this.klass);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pupil other = (Pupil) obj;
        if (this.schoolNr != other.schoolNr) {
            return false;
        }
        if (!Objects.equals(this.fName, other.fName)) {
            return false;
        }
        if (!Objects.equals(this.lName, other.lName)) {
            return false;
        }
        if (!Objects.equals(this.klass, other.klass)) {
            return false;
        }
        return true;
    }
    
    public boolean equals(GAppsParser.PupilImport other) {
        
        if (other == null) {
            return false;
        }
        
        if (this.schoolNr != other.schoolNr) {
            return false;
        }
        if (!Objects.equals(this.fName, other.fName)) {
            return false;
        }
        if (!Objects.equals(this.lName, other.lName)) {
            return false;
        }
        if (!Objects.equals(this.klass, other.klass)) {
            return false;
        }
        return true;
    }
    
    
    public boolean isMyfNameHere(String q){
        return q.toLowerCase().contains(fName.toLowerCase());
    }
    
    public boolean isMylNameHere(String q){
        return q.toLowerCase().contains(lName.toLowerCase());
    }
    
    public boolean isMyKlass(String kl){
        return klass.equals(kl);
    }
    
    public boolean isMySchool(Integer n){
        return schoolNr == n;
    }
    
    public boolean isMySchoolHere(String q){
        return q.contains(String.valueOf(schoolNr));
    }
            
    
    public boolean isMyKlassHere(String q){
        var ql = q.toLowerCase();
        if (ql.contains(klass)) {
            return true;
        }
        if (ql.contains(klass.charAt(0)+" " + klass.charAt(1))) {
            return true;
        }
        String roman;
        switch(klass.charAt(0)){
            case '1': roman = "i"; break;
            case '2': roman = "ii"; break;
            case '3': roman = "iii"; break;
            case '4': roman = "iv"; break;
            case '5': roman = "v"; break;
            case '6': roman = "vi";break;
            default : roman = "vii";
        }
        if (ql.contains(roman+klass.charAt(1))) {
            return true;
        }
        if (ql.contains(roman+" "+klass.charAt(1))) {
            return true;
        }
        return false;
    }
    
    
}
