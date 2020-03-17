/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.calculations;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import pl.luccasso.mailownik.BankTransaction;
import pl.luccasso.mailownik.model.NewFamily;
import pl.luccasso.mailownik.persistence.DataBase;

/**
 *
 * @author piko
 */
public class FamilyTransactionMatcher {

    DataBase dataBase;

    public FamilyTransactionMatcher(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void analyzeTransaction3(BankTransaction bt) {
        bt.note("===========Fakk==============");
        try {
            if (dataBase.famByAccountMap().containsKey(bt.account())) {
                accountAlreadyMatched(bt);
                return;
            }
            if (bt.isDoomed()) {
                dataBase.leftOvers().add(bt);
                bt.note("Doomed");
                return;
            }
            if (!bt.hasDubiousSchool()) {
                schoolInTransactionIsOK(bt);
                return;
            }
            if (!bt.hasDubiousKlass()) {
                klassIsOKButSchoolIsNot(bt);
                return;
            }

            //Catch Wrong Klass and school
            dataBase.leftOvers().add(bt);
            bt.note("Wrong Klass and School");

        } catch (Exception e) {
            dataBase.leftOvers().add(bt);
            bt.note("------Exception----------" + e.getMessage());
            System.out.println("anal2: -Ex- : " + bt.title() + "\n");
            e.printStackTrace();
        }
    }

    void klassIsOKButSchoolIsNot(BankTransaction bt) {
        List<NewFamily> tmpList;
        tmpList = new LinkedList(dataBase.famByKlassMap().get(bt.klass())); //TODO Throws Exception when Wrong School
        List<NewFamily> listlName = tryFitNames(bt, tmpList);
        List<NewFamily> listwSchool = tryFindSchool(bt, listlName);
        if (listwSchool.size() > 1) {
            bt.note("School++ lName++ klass+");
            dataBase.humanFamilyToDecide().put(bt, listwSchool);
            return;
        } else if (listwSchool.size() == 1) {
            bt.note("Klass+ school+ lname +");
            dataBase.famFittedData().merge(listwSchool.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                o.addAll(n);
                return o;
            });
            return;
        } else {
            if (listlName.isEmpty()) {
                dataBase.leftOvers().add(bt);
                bt.note("Klas+ lname- ");
                return;
            } else if (listlName.size() == 1) {
                bt.note("Klas+ lname+ school-");
                dataBase.famFittedData().merge(listlName.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                    o.addAll(n);
                    return o;
                });
                return;
            } else {
                bt.note("Klas+ lname++");
                dataBase.humanFamilyToDecide().put(bt, listlName);
                return;
            }
        }
    }

    void schoolInTransactionIsOK(BankTransaction bt) throws NumberFormatException {
        List<NewFamily> tmpList;
        tmpList = new LinkedList(dataBase.famBySchoolMap().get(Integer.valueOf(bt.school())));
        List<NewFamily> listWithlNames = tryFitNames(bt, tmpList);
        if (listWithlNames.isEmpty()) {
            if (!bt.hasDubiousKlass()) {
                List<NewFamily> listWithKlass = tryFitKlass(bt, tmpList);
                List<NewFamily> listWithfName = tryFitfName(bt, listWithKlass);
                if (listWithfName.isEmpty()) {
                    if (listWithKlass.size() > 0) {
                        dataBase.humanFamilyToDecide().put(bt, listWithKlass);
                        bt.note("School+ fName+ lname- klass++ ");
                        return;
                    } else {
                        dataBase.leftOvers().add(bt);
                        bt.note("School+ lName- klass+ fname- ");
                        return;
                    }
                } else if (listWithfName.size() == 1) {
                    dataBase.famFittedData().merge(listWithfName.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                        o.addAll(n);
                        return o;
                    });
                    bt.note("School+ fname+ klass+ lName-");
                    return;
                } else {
                    dataBase.leftOvers().add(bt);
                    bt.note("School+ fname++ klass+ lName- ");
                    return;
                }
            } else {
                dataBase.leftOvers().add(bt);
                bt.note("School+ lName- klass?");
                return;
            }
        } else if (listWithlNames.size() == 1) {
            dataBase.famFittedData().merge(listWithlNames.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                o.addAll(n);
                return o;
            });
            bt.note("School+ lName+");
            return;
        } else {
            if (bt.hasDubiousKlass()) {
                dataBase.humanFamilyToDecide().put(bt, listWithlNames);
                bt.note("School+ lname++ klass?");
                return;
            } else {
                List<NewFamily> listWithKlass = tryFitKlass(bt, tmpList);
                if (listWithKlass.isEmpty()) {
                    dataBase.humanFamilyToDecide().put(bt, listWithlNames);
                    bt.note("School+ lname++ klass-");
                    return;
                } else if (listWithKlass.size() == 1) {
                    bt.note("School+ lName++ klass+");
                    dataBase.famFittedData().merge(listWithKlass.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                        o.addAll(n);
                        return o;
                    });
                    return;
                } else {
                    bt.note("School+ lName++ klass++");
                    dataBase.humanFamilyToDecide().put(bt, listWithKlass);
                    return;
                }
            }
        }
    }

    private void accountAlreadyMatched(BankTransaction bt) {
        List<NewFamily> pList = dataBase.famByAccountMap().get(bt.account());
        if (pList.size() > 1) {
            dataBase.siblings().add(bt); //TODO Zrobic check na pocztowe konta, i to chyba jest zle
            bt.note("Many people with this account: " + bt.account());
            return;
        }
        dataBase.famFittedData().merge(pList.get(0), new LinkedList<>(List.of(bt)),
                (o, n) -> {
                    o.addAll(n);
                    return o;
                });
        bt.note("account");
    }

    private List<NewFamily> tryFitKlass(BankTransaction bt, List<NewFamily> lList) {
        return lList.stream()
                .filter(p -> p.isMyKlass(bt.klass()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<NewFamily> tryFindSchool(BankTransaction bt, List<NewFamily> lList) {
        return lList.stream()
                .filter(p -> p.isMySchoolHere(bt.klass()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<NewFamily> tryFitfName(BankTransaction bt, List<NewFamily> lList) {
        return lList.stream()
                .filter(p -> p.isMyfNameHere(bt.niceString))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<NewFamily> tryFitNames(BankTransaction bt, List<NewFamily> lList) {
        return lList.stream()
                .filter(p -> p.isMylNameHere(bt.niceString))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
