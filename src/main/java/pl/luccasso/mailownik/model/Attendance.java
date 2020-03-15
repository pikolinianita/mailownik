/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.model;

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
public class Attendance {

    String[] timeSheet;

    int nb;

    int zeroes;

    int ones;

    int eMs;

    int payAbleHoursCalculation() {
        zeroes = 0;
        ones = 0;
        eMs = 0;
        String ob = getTimeSheetString();
        for (char c : ob.toCharArray()) {
            switch (c) {
                case '0':
                    zeroes++;
                    break;
                case '1':
                    ones++;
                    break;
                case 'M':
                    eMs++;
                    break;
                default:
                    break;
            }
        }
        return ones + zeroes;
    }

    String getTimeSheetString() {
        return String.join("\t", timeSheet);
    }
}
