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
import pl.luccasso.mailownik.DoCompare;
import pl.luccasso.mailownik.Pupil;
import pl.luccasso.mailownik.persistence.DataBase;

/**
 *
 * @author piko
 */
public class FamilyTransactionMatcher {

    private void analyzeTransaction3(BankTransaction bt, DataBase dataBase) {
        bt.note("===========Fakk==============");
        try {
            if (dataBase.pupByAccountMap.containsKey(bt.account())) {
                List<Pupil> pList = dataBase.pupByAccountMap.get(bt.account());
                if (pList.size() > 1) {
                    dataBase.siblings.add(bt);
                    bt.note("siblings - fitted by account");
                    return;
                }
                dataBase.fittedData.merge(pList.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                    o.addAll(n);
                    return o;
                });
                bt.note("account");
                return;
            }
            bt.checkForSiblings();
            if (bt.siblingsSuspected()) {
                dataBase.siblings.add(bt);
                bt.note("siblings");
                return;
            }
            if (bt.isDoomed()) {
                dataBase.leftOvers.add(bt);
                bt.note("Doomed");
                return;
            }
            List<Pupil> tmpList;
            if (!bt.hasDubiousSchool()) {
                tmpList = new LinkedList(dataBase.pupBySchoolMap.get(Integer.valueOf(bt.school())));
                List<Pupil> listWithlNames = tryFitNames(bt, tmpList);
                if (listWithlNames.isEmpty()) {
                    if (!bt.hasDubiousKlass()) {
                        List<Pupil> listWithKlass = tryFitKlass(bt, tmpList);
                        List<Pupil> listWithfName = tryFitfName(bt, listWithKlass);
                        if (listWithfName.isEmpty()) {
                            if (listWithKlass.size() > 0) {
                                dataBase.humanToDecide.put(bt, listWithKlass);
                                bt.note("School+ fName+ lname- klass++ ");
                                return;
                            } else {
                                dataBase.leftOvers.add(bt);
                                bt.note("School+ lName- klass+ fname- ");
                                return;
                            }
                        } else if (listWithfName.size() == 1) {
                            dataBase.fittedData.merge(listWithfName.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                                o.addAll(n);
                                return o;
                            });
                            bt.note("School+ fname+ klass+ lName-");
                            return;
                        } else {
                            dataBase.leftOvers.add(bt);
                            bt.note("School+ fname++ klass+ lName- ");
                            return;
                        }
                    } else {
                        dataBase.leftOvers.add(bt);
                        bt.note("School+ lName- klass?");
                        return;
                    }
                } else if (listWithlNames.size() == 1) {
                    dataBase.fittedData.merge(listWithlNames.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                        o.addAll(n);
                        return o;
                    });
                    bt.note("School+ lName+");
                    return;
                } else {
                    if (bt.hasDubiousKlass()) {
                        dataBase.humanToDecide.put(bt, listWithlNames);
                        bt.note("School+ lname++ klass?");
                        return;
                    } else {
                        List<Pupil> listWithKlass = tryFitKlass(bt, tmpList);
                        if (listWithKlass.isEmpty()) {
                            dataBase.humanToDecide.put(bt, listWithlNames);
                            bt.note("School+ lname++ klass-");
                            return;
                        } else if (listWithKlass.size() == 1) {
                            bt.note("School+ lName++ klass+");
                            dataBase.fittedData.merge(listWithKlass.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                                o.addAll(n);
                                return o;
                            });
                            return;
                        } else {
                            bt.note("School+ lName++ klass++");
                            dataBase.humanToDecide.put(bt, listWithKlass);
                            return;
                        }
                    }
                }
            }
            if (!bt.hasDubiousKlass()) {
                tmpList = new LinkedList(dataBase.pupByKlassMap.get(bt.klass()));
                List<Pupil> ListlName = tryFitNames(bt, tmpList);
                List<Pupil> ListwSchool = tryFindSchool(bt, ListlName);
                if (ListwSchool.size() > 1) {
                    bt.note("School++ lName++ klass+");
                    dataBase.humanToDecide.put(bt, ListwSchool);
                    return;
                } else if (ListwSchool.size() == 1) {
                    bt.note("Klass+ school+ lname +");
                    dataBase.fittedData.merge(ListwSchool.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                        o.addAll(n);
                        return o;
                    });
                    return;
                } else {
                    if (ListlName.isEmpty()) {
                        dataBase.leftOvers.add(bt);
                        bt.note("Klas+ lname- ");
                        return;
                    } else if (ListlName.size() == 1) {
                        bt.note("Klas+ lname+ school-");
                        dataBase.fittedData.merge(ListlName.get(0), new LinkedList<>(List.of(bt)), (o, n) -> {
                            o.addAll(n);
                            return o;
                        });
                        return;
                    } else {
                        bt.note("Klas+ lname++");
                        dataBase.humanToDecide.put(bt, ListlName);
                        return;
                    }
                }
            }
            tmpList = new LinkedList<>(dataBase.pupilList());
            dataBase.leftOvers.add(bt);
            bt.note("School?");
        } catch (Exception e) {
            dataBase.leftOvers.add(bt);
            bt.note("------Exception----------");
            System.out.println("anal2: -Ex- : " + bt.title());
        }
    }
   private List<Pupil> tryFitKlass(BankTransaction bt, List<Pupil> lList) {
        return lList.stream()
                .filter(p -> p.isMyKlass(bt.klass()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<Pupil> tryFindSchool(BankTransaction bt, List<Pupil> lList) {
        return lList.stream()
                .filter(p -> p.isMySchoolHere(bt.klass()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<Pupil> tryFitfName(BankTransaction bt, List<Pupil> lList) {
        return lList.stream()
                .filter(p -> p.isMyfNameHere(bt.niceString))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<Pupil> tryFitNames(BankTransaction bt, List<Pupil> lList) {
        return lList.stream()
                .filter(p -> p.isMylNameHere(bt.niceString))
                .collect(Collectors.toCollection(LinkedList::new));
    }
} 
