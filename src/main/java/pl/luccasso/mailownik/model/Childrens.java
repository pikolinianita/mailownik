/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.luccasso.mailownik.model;

import java.util.LinkedList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author piko
 */

@ToString
@Accessors(fluent = true, chain = true)
@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
class Childrens {

    List<NewPupil> list = new LinkedList<>();
    
    Childrens add(NewPupil p){
        list.add(p);
        return this;
    }

    int size() {
        return list.size();
    }

    int getSchoolNr() {
       
       boolean goodResult = true;
       for (int i = 0; i<list.size(); i++){
           if (list.get(i).school()!=list.get(0).school()){
               goodResult = false;
           }
       }
       return goodResult ? list.get(0).school() :  -1;
    }

    boolean isMySchoolHere(String school) {
        var result = false;
        for (var np: list){
            if(np.isMySchoolHere(school)){
                result = true;
                break;
            }
        }
        return result;
    }

    boolean isMyKlass(String klass) {
        var result = false;
        for (var np: list){
            if(np.isMyKlassHere(klass)){
                result = true;
                break;
            }
        }
        return result;    
    }

    boolean isMylNameHere(String niceString) {
        var result = false;
        for (var np: list){
            if(np.isMylNameHere(niceString)){
                result = true;
                break;
            }
        }
        return result;
    }

    boolean isMyfNameHere(String niceString) {
       var result = false;
        for (var np: list){
            if(np.isMyfNameHere(niceString)){
                result = true;
                break;
            }
        }
        return result;
    }
    
}
