/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.AgendaSkinSwitcher;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class FXtrasAgController implements Initializable {
    @FXML private Agenda agenda;
    @FXML private ToggleButton Dia;
    @FXML private ToggleButton Semana;
    @FXML private ToggleButton Mes;
    @FXML private Pane PainelMaior;
    @FXML private Button Avança;
    @FXML private Button Volta;
    @FXML private AnchorPane PainelMenor;
    private ToggleGroup Skins;
    private String ID;
    private AgendaSkinSwitcher SkinSwitcher;
    private Connection con;
    private Statement stat;
    private ObservableList<Compromisso> data;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Skins= new ToggleGroup();
        Semana.setToggleGroup(Skins);
        Mes.setToggleGroup(Skins);
        Dia.setToggleGroup(Skins);
        Semana.setSelected(true);
        Skins.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) -> {
            if(Skins.getSelectedToggle()==Semana){
                agenda.setSkin(new AgendaWeekSkin(agenda));
                Avança.setOnAction(new EventHandler() {

                    @Override
                    public void handle(Event event) {
                        AvancaSemana();
                    }
                });
                Volta.setOnAction(new EventHandler() {

                    @Override
                    public void handle(Event event) {
                        VoltaSemana();
                    }
                });
            }
            if(Skins.getSelectedToggle()==Mes){
                 agenda.setSkin(new AgendaDaysFromDisplayedSkin(agenda));
            }
            if(Skins.getSelectedToggle()==Dia){
                agenda.setSkin(new AgendaDaySkin(agenda));
                Avança.setOnAction(new EventHandler() {

                    @Override
                    public void handle(Event event) {
                        AvancaDia();
                    }
                });
                Volta.setOnAction(new EventHandler() {

                    @Override
                    public void handle(Event event) {
                        VoltaDia();
                    }
                });
            }
        });
        try {
            BuscaBD();
        } catch (ParseException ex) {
            Logger.getLogger(FXtrasAgController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void AvancaSemana(){
        LocalDateTime a;
        a = agenda.getDisplayedLocalDateTime();
        System.out.println(a);
        agenda.setDisplayedLocalDateTime(a.plusDays(7));
    }
    
    public void AvancaDia(){
        LocalDateTime a;
        a = agenda.getDisplayedLocalDateTime();
        System.out.println(a);
        agenda.setDisplayedLocalDateTime(a.plusDays(1));
    }
    
    public void VoltaDia(){
        LocalDateTime a;
        a = agenda.getDisplayedLocalDateTime();
        a.plusHours(7);
        System.out.println(a);
        agenda.setDisplayedLocalDateTime(a.minusDays(1));
    }
    
    public void VoltaSemana(){
        LocalDateTime a;
        a = agenda.getDisplayedLocalDateTime();
        a.plusHours(7);
        System.out.println(a);
        agenda.setDisplayedLocalDateTime(a.minusDays(7));
    }
    
    public void BuscaBD() throws ParseException{
         try {  
            con=DBConnect.connect();
            stat = con.createStatement();
            data = FXCollections.observableArrayList();
            final PreparedStatement ps = con.prepareStatement("select * from compromissos where IDProfessor='"+ID+"'");
            final ResultSet rs = ps.executeQuery();
            String hr;
            while (rs.next()) {
                hr=rs.getString("HInicial").substring(0, 5)+"-"+rs.getString("HFinal").substring(0, 5);
                System.out.println(hr);
                data.add(new Compromisso(rs.getString("Data"), rs.getString("Atividade"),hr,rs.getString("Turma"),rs.getString("HInicial"),rs.getString("HFinal"),rs.getString("IDProfessor")));
            }
            for (Compromisso e : data) {
                System.out.println(e.getHIniGC().getTime());
                String group;
                if("Prova".equals(e.getAtividade())){
                    group="group3";
                }
                else if("Excursão".equals(e.getAtividade())){
                    group="group6";
                }
                else if("Atividade".equals(e.getAtividade())){
                    group="group13";
                }
                else if("Trabalho".equals(e.getAtividade())){
                    group="group8";
                }
                else{
                    group="group15";
                }
                agenda.appointments().add(
                new Agenda.AppointmentImpl()
                .withDescription(e.getAtividade()+"("+e.getTurma()+")")
                .withStartTime(e.getHIniGC())
                .withEndTime(e.getHFinGC())
                .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass(group)) 
                .withSummary(e.getAtividade()+"("+e.getTurma()+")")
                );
              
            }      
        } catch (SQLException e) {
            System.out.println("Error on Building Data");
        }
      }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    
    }    
    
