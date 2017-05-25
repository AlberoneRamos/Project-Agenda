/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Henrique
 */
public class Compromisso extends Date{
    private Locale locale;
    private Calendar cal;
    private DateFormat df;
    final String NEW_FORMAT = "dd/MM/yyyy";
    private String Data;
    private String Atividade;
    private String HInicial;
    private String HFinal;
    private String Turma;
    private String Horario;
    private String ID;


    public Compromisso(String Data, String Atividade, String Horario, String Turma,String HInicial, String HFinal,String ID) {
        this.Data = Data;
        this.Atividade = Atividade;
        this.HInicial = HInicial;
        this.HFinal = HFinal;
        this.Turma = Turma;
        this.Horario=Horario;
        this.ID=ID;
    }
    
    public Compromisso(){
        df = new SimpleDateFormat("EEE, dd MMM yyyy",locale);
        cal = new GregorianCalendar(locale);
        locale = Locale.getDefault();
    }
    
    
    public GregorianCalendar getHIniGC() throws ParseException{
        GregorianCalendar calend = new GregorianCalendar();
        String Intensifies=Data+" "+HInicial;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date parsed = df.parse(Intensifies);
        calend.setTime(parsed);
        return calend;
    }
    
    public GregorianCalendar getHFinGC() throws ParseException{
         GregorianCalendar calend=new GregorianCalendar();
        String Intensifies=Data+" "+HFinal;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date parsed = df.parse(Intensifies);
        calend.setTime(parsed);
        return calend;
         
    }


    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public String getAtividade() {
        return Atividade;
    }

    public void setAtividade(String Tipo) {
        this.Atividade = Tipo;
    }

    public String getHInicial() {
        return HInicial;
    }

    public void setHInicial(String HInicial) {
        this.HInicial = HInicial;
    }

    public String getHFinal() {
        return HFinal;
    }

    public void setHFinal(String HFinal) {
        this.HFinal = HFinal;
    }
    
     public String getHorario() {
        return Horario;
    }

    public void setHorario(String HFinal) {
        this.Horario = Horario;
    }

    public String getTurma() {
        return Turma;
    }

    public void setTurma(String Turma) {
        this.Turma = Turma;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    
    
}
