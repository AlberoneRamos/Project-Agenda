/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onestort;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Aluno
 */
public class DiasdaSemana {
private Locale locale = Locale.getDefault();
// 1. create calendar
private Calendar cal = new GregorianCalendar(locale);
private DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy",locale);
final String NEW_FORMAT = "dd/MM/yyyy";

// August 12, 2010
/*String oldDateString = "12/08/2010";
String newDateString;

SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
Date d = sdf.parse(oldDateString);

sdf.applyPattern(NEW_FORMAT);
newDateString = sdf.format(d);*/

public String getHoje(){
    DateFormat sf;
    sf = DateFormat.getDateInstance(DateFormat.FULL,locale);
    return sf.format(cal.getTime());
}

public DiasdaSemana(){
    cal.setTime(new Date());
}

public String [] getDiasdaSemana(int semAFrente) {
    cal.add(Calendar.DATE, semAFrente);
    cal.setFirstDayOfWeek(Calendar.SUNDAY);
    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
    String [] Dias=new String[7];
    
    for (int i = 0; i < 7; i++) {
        // 4. get some infomation
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        Dias[i]= df.format(cal.getTime());

        // 5. increase day field; add() will adjust the month if neccessary
        cal.add(Calendar.DAY_OF_WEEK, 1);
    }
    return Dias;
}

    public String getMonth(){
    String [] monthNames = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    Date d=new Date();
    String a=monthNames[d.getMonth()];
    return a;
}
}
