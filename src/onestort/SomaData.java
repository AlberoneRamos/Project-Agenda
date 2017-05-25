/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Henrique
 */
public class SomaData {
    public static Date addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        return cal.getTime();
    }
    
    public static Calendar addWeek(Calendar date) {
        Calendar cal = Calendar.getInstance();
        cal=date;
        cal.add(Calendar.DAY_OF_YEAR, 7);
        return cal;
    }
    
    public static Date addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return cal.getTime();
    }
    public static Date addYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return cal.getTime();
    }
    
    public static Calendar DateToCalendar(Date date){ 
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

     public static Date addYearMonthDay(Date date, int i,int x,int z) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        cal.add(Calendar.MONTH, x);
        cal.add(Calendar.DAY_OF_YEAR, z);
        return cal.getTime();
    }
}
