/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author piko
 */
public class BankTransaction {

    String title;           //pole z mięskiem do dalszej analizy
    LocalDate date;         //data wpłaty
    int amount;             //wartość wpłaty
    String account;         //numer konta
    String klass;           //Jaka klasa
    String school;          //Jaka szkoła
    // cyfra-litera otoczone spacje/kropka etc. 
    static Pattern forKlass = Pattern.compile("(\\b\\d\\s?[a-z]\\b)", Pattern.CASE_INSENSITIVE);
    // klasy w formacie IIIa lub iv c
    static Pattern forKlass2 = Pattern.compile("(\\bi+\\s?[a-z]\\b)|(\\biv\\s?[a-z]\\b)", Pattern.CASE_INSENSITIVE);
    // dwie lub trzy cyfry otoczone spacja lub SPCyfry
    static Pattern forSchool = Pattern.compile("(\\b(\\d{2,3})\\b)|(\\bsp\\d+)", Pattern.CASE_INSENSITIVE);
    // SP XXX gdzie X to cyfry
    static Pattern forSchool2 = Pattern.compile("\\bsp\\s?\\d{2,3}", Pattern.CASE_INSENSITIVE);
    // diwe/try cyfry, na osteczną poprawkę szkoły
    static Pattern fewDigits = Pattern.compile("\\d{2,3}" , Pattern.CASE_INSENSITIVE);
    String dubiousInfo;         // co mi się nie zgadza z klasą/szkołą
    String amStr;               //String z wartością wpłaty
    boolean isDoomed;           //błąd krytyczny, nic nie wymyślę
    boolean hasDubiousKlass;    //problem z analizą klasy
    boolean hasDubiousSchool;   //problem z analizą szkoły
    boolean siblingsSuspected;  //tu może być dwójka dzieci
    String[] splittedTitle;     // tablice pomocnicze, może do wywałki?
    String[] syfTitle;          // tablice pomocnice, moze do wywałki
    public String niceString;          //String z tytułem przelewu, I hope
    String matchNotes;          //info about matches with guys  

    /**
     * Analiza wpisu z banku. Struktura: YYYY-MM-DD; Dużo dziwnych informacji, w
     * tym tytuł przelewu, konto itp; "mBiznes konto pomocnicze 0711 ...
     * 2221";kategoria;kwota wpłacona; saldo;
     *
     * @param tit String do analizowania
     */
    public BankTransaction(String tit) {
        //System.out.println("--------Nowy-----------");
        //System.out.println(tit);
        
        isDoomed = false;
        hasDubiousKlass = false;
        dubiousInfo = "";
        Scanner sc = new Scanner(tit);

        //wczytaj datę - pierwsze pole pliku
        sc.useDelimiter(";");
        date = LocalDate.parse(sc.next());
        //wczytaj pole z mięskiem - może zawierać ";", na szczęście wiem co jest w następnym polu, 
        //więc jak to nie to to łączę 
        title = sc.next();
        String tmp = sc.next();
        while (!tmp.contains("mBiznes konto pomocnicze 0711 ... 2221")) {
            title = title + ", " + tmp;
            tmp = sc.next();
            //System.out.println(tit);
        }
        //pomija co trzeba i szuka jaka była wpłata
        sc.next();
        amStr = sc.next();
        try {
            amount = Integer.valueOf(amStr.substring(0, amStr.length() - 7));
        } catch (NumberFormatException e) {
            isDoomed = true;
        }
        
        /*w mięsku oddzielone wieloma spacjami są pewne subpola: Mięsko właściwe, Adres nadawcy, rodzaj przelewu, numer konta. Ekstracja*/
        syfTitle = title.split("\\s\\s+");
        account = syfTitle[syfTitle.length - 1];
        splittedTitle = syfTitle[0].split(",");
        
        //Czasem na początku mięska jest powtórzone imię nadawcy. Wycinamy. TODO - czy naprawde tu jest problem z kartą? 
        try {
            if (!syfTitle[syfTitle.length - 2].contains("KARTY KREDYTOWEJ")) {
                niceString = syfTitle[0].substring(syfTitle[0].indexOf(",")).toLowerCase();
            } else {
                niceString = syfTitle[0].toLowerCase();
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("-------- out of bound -----" + syfTitle[0]);
            niceString = null;
            isDoomed = true;
        }
        
        //Jeśli dotąd nie było dużego błędu to staramy się dalej procedować
        if (!isDoomed) {
            //checkForSiblings(amount);
            
            if (!checkForKlass(niceString, forKlass)) {
                checkForKlass(niceString, forKlass2);
            }

            //TODO ogarnąć suspected Siblings;
            if (!checkForSchool(niceString, forSchool2)) {
                checkForSchool(niceString, forSchool);
            }

            // Formatowanie szkoły i klasy do ludzkiej postaci: "1a" i "358"
            if (klass!=null) {
                klassPrettify();
            } 
            if (school!=null) {
                schoolPrettify();
            }

        }
    }

    /**
     * Sprawdza czy da się dopasować pattern (w domysle - klasy) do stringa.
     * jeśli znajdzie 2 lub więcej to podejrzewa rodzeńswo, jak 1 to wpisuje,
     * jak 0 to zwraca false
     *
     * @param niceS String do przeszukania
     * @param patt Pattern
     * @return true jak znalazło coś, false jak nie było żadnego dopasowania
     */
    private boolean checkForKlass(String niceS, Pattern patt) {

        Matcher matcher = patt.matcher(niceS);
        int hit = 0;
        while (matcher.find()) {
            hit++;
        }
        if (hit > 1) {
            matcher.reset();
            hasDubiousKlass = true;
            siblingsSuspected = true;
            while (matcher.find()) {
                dubiousInfo += matcher.group() + " ";
            }
            return true;
        } else if (hit == 1) {
            matcher.reset().find();
            klass = matcher.group();
            return true;
        } else {
            hasDubiousKlass = true;
            return false;
        }

    }

    /**
     * Sprawdza czy da się dopasować pattern (w domysle - szkoły) do stringa.
     * Jak znajdzie dokładnie 1 to wpisuje do pola school, jak więcej to
     * marudzi, jak 0 to zwraca false
     *
     * @param niceS String do szukania
     * @param patt Pattern
     * @return true jak znalazło 1 lub więcej, false jak nic nie ma
     */
    private boolean checkForSchool(String niceS, Pattern patt) {
        Matcher matcher = patt.matcher(niceS);
        int hit = 0;
        while (matcher.find()) {
            hit++;
        }
        if (hit > 1) {
            matcher.reset();
            hasDubiousSchool = true;
            //siblingsSuspected = true;
            while (matcher.find()) {
                dubiousInfo += matcher.group() + " ";
            }
            return true;
        } else if (hit == 1) {
            matcher.reset().find();
            school = matcher.group();
            return true;
        } else {
            hasDubiousSchool = true;
            return false;
        }
    }

    @Override
    public String toString() {
        return "BankTransaction{" + "niceString=" + niceString + "Note: " + matchNotes + '}';
    }

    public String specialToString() {
        return "BankTransaction{" + "niceString=" + niceString + " klasa: " + klass + " szkola: " + school + "Note: " + matchNotes +'}';
    }

    private void klassPrettify() {
        String klassTmp = klass.toLowerCase();
        char klassLetter = klassTmp.charAt(klassTmp.length()-1);
        if (klassTmp.contains("i")) {
            if(klassTmp.contains("iv")) {
                klass="4"+klassLetter;
            } else if(klassTmp.contains("iii")){
                klass="3"+klassLetter;
            } else if(klassTmp.contains("ii")){
                klass="2"+klassLetter;                
            } else {
                klass = "1"+klassLetter;
            }
        } else if (klassTmp.length()==3)
        {
            klass = "" + klassTmp.charAt(0)+klassTmp.charAt(2);
        } else {
            klass = klassTmp;
        }
    }

    private void schoolPrettify() {
        Matcher m = BankTransaction.fewDigits.matcher(school);
        m.find();
        school = m.group();
    }

    public void note(String s){
        matchNotes = s;
    }

    public void checkForSiblings() {
        System.out.println(BankFileParser.finData);
        if (BankFileParser.finData.isSiblingsValue(amount)) {
            siblingsSuspected = true;
        }
    }
    
    public String saveTransaction(){
        return String.join("\t", date.toString(), title, "konto: " + account, String.valueOf(amount)+"PLN") + "\n";
  }
    public String tooltipInfo() {
        return String.join("\n", "Wpłynęło: " + date.toString(), title, "konto: " + account, String.valueOf(amount)+"PLN");
    }
    
}
