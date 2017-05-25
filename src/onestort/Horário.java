/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;

/**
 *
 * @author Henrique
 */
class Horário {
    private String [] AtividadesdaSemana=new String[6];
    private String Horario;

    public Horário(String Horario,String [] AtividadesdaSemana) {
        this.Horario = Horario;
        int i;
        for(i=0;i<AtividadesdaSemana.length;i++){
               AtividadesdaSemana[i]="";
           }
        if(Horario.equals("Intervalo"))
           for(i=0;i<AtividadesdaSemana.length;i++){
               AtividadesdaSemana[i]="Intervalo";
           }
        this.AtividadesdaSemana = AtividadesdaSemana;
    }

    public String[] getAtividadesdaSemana() {
        return AtividadesdaSemana;
    }

    public void setAtividadesdaSemana(String[] AtividadesdaSemana) {
        this.AtividadesdaSemana = AtividadesdaSemana;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String Horario) {
        this.Horario = Horario;
    }
    
    
}
