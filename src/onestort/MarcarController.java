/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;


import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class MarcarController implements Initializable {

    @FXML private TimeTextField HInicial;
    @FXML private TimeTextField HFinal;
    @FXML private TextField Atividade;
    @FXML private ComboBox Turma;
    @FXML private DatePicker Data;
    @FXML private ToggleButton DIA;
    @FXML private Button Marcar;
    @FXML private ToggleButton DQA;
    @FXML private AnchorPane DaquiaTexto;
    @FXML private TextField ANOS;
    @FXML private Label ANOSTXT;
    @FXML private TextField MESES;
    @FXML private Label MESESTXT;
    @FXML private TextField DIAS;
    @FXML private Label DIASTXT;
    private String ID;
    private String Nome;
    private static Connection con;
    private static Statement stat;
    private PreparedStatement prep;
    private ToggleGroup group;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        group = new ToggleGroup();
        DIA.setToggleGroup(group);
        DIA.setSelected(true);
        DQA.setToggleGroup(group);
        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) -> {
            if(group.getSelectedToggle()==DIA){
                Data.setVisible(true);
                DaquiaTexto.setVisible(false);
            }
            if(group.getSelectedToggle()==DQA){
                Data.setVisible(false);
                DaquiaTexto.setVisible(true);
            }
        });
        getMaterias();
    }    
    
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    public void Grava() throws ParseException{
          try {  
            con=DBConnect.connect();
            stat = con.createStatement();
            String data = null;
            HolidayManager m = HolidayManager.getInstance(HolidayCalendar.BRAZIL);
            if(group.getSelectedToggle()==DIA){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = Data.getValue();
                if (date != null) {
                    data=formatter.format(date);
                } else {
                    data="";
                }
            }
            else if(group.getSelectedToggle()==DQA){
                data=FuncDQA();
            }
            if(PegaErros(data)==true){
            }
            else if(EventoIgual(data)==true){}
            else{
                InsereDados(data,ID);
            }
            
        } catch (SQLException | NumberFormatException | HeadlessException e) {
            System.out.println("Erro ao gravar dados!");
            e.printStackTrace();
        }
    }

    public void Editar(String HInicial,String HFinal,String Turma,String Atividade,String Data){
        this.HInicial.setText(HInicial.substring(0,5));
        this.HFinal.setText(HFinal.substring(0, 5));
        this.Atividade.setText(Atividade);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(Data, formatter);
        this.Data.setValue(date);
        this.Turma.setValue(Turma);
        Marcar.setOnAction((ActionEvent event) -> {
                try {
                    PreparedStatement ps = null;
                    con=DBConnect.connect();
                    ps = con.prepareStatement("delete from compromissos where IDProfessor='"+ID+"' and Data='"+Data+"' and HInicial='"+HInicial.substring(0, 5)+"' and HFinal='"+HFinal.substring(0, 5)+"'");
                    ps.execute();
                    String data = null;
                    if(group.getSelectedToggle()==DIA){
                        if (date != null) {
                            data=formatter.format(this.Data.getValue());
                        } else {
                            data="";
                        }
                    }
                    else if(group.getSelectedToggle()==DQA){
                        data=FuncDQA();
                    }
                    if(PegaErros(data)==true){
                        String query=" insert into compromissos (Data, Turma, HInicial, HFinal, Atividade,IDProfessor,prof_nome)"
                        + " values (?, ?, ?, ?, ?,?,?)";
                        PreparedStatement PrepState = con.prepareStatement(query);
                        PrepState.setString(1,data);
                        PrepState.setObject(2, Turma);
                        PrepState.setObject(3, HInicial);
                        PrepState.setObject(4, HFinal);
                        PrepState.setObject(5, Atividade);
                        PrepState.setObject(6,ID);
                        PrepState.setObject(7,Nome);
                        PrepState.execute();
                    }
                    else if(EventoIgual(data)==true){
                        String query=" insert into compromissos (Data, Turma, HInicial, HFinal, Atividade,IDProfessor,prof_nome)"
                        + " values (?, ?, ?, ?, ?,?,?)";
                        PreparedStatement PrepState = con.prepareStatement(query);
                        PrepState.setString(1,data);
                        PrepState.setObject(2, Turma);
                        PrepState.setObject(3, HInicial);
                        PrepState.setObject(4, HFinal);
                        PrepState.setObject(5, Atividade);
                        PrepState.setObject(6,ID);
                        PrepState.setObject(7,Nome);
                        PrepState.execute();
                    }
                    else {
                        InsereDados(data,ID);
                    }
                    
                }catch (SQLException ex) {
                    Logger.getLogger(MarcarController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                Logger.getLogger(MarcarController.class.getName()).log(Level.SEVERE, null, ex);
            }
});
        
    }
    
    public boolean PegaErros(String data) throws ParseException{
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date Init = parser.parse(this.HInicial.getText());
        Date Final = parser.parse(this.HFinal.getText());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar hoje=Calendar.getInstance();
        hoje.setTime(new Date());
        Calendar cm=Calendar.getInstance();
        cm.setTime(formatter.parse(data));
        cm.add(Calendar.HOUR_OF_DAY, Init.getHours());
        cm.add(Calendar.MINUTE, Init.getMinutes());
        if(cm.before(hoje)==true){
            JOptionPane.showMessageDialog(null, "Voltar no tempo não é permitido!");
            return true;
        }
        if(Init.compareTo(Final)==0){
            JOptionPane.showMessageDialog(null,"Horas iguais? Sério?");
            return true;
        }
        else if(this.Atividade.getText() == null || "".equals(this.Atividade.getText())){
            JOptionPane.showMessageDialog(null, "Digite algo em atividade!");
            return true;
        }
        else if(this.Turma.getValue() == null || "".equals(this.Turma.getValue())){
            JOptionPane.showMessageDialog(null, "Selecione uma turma!");
            return true;
        }
        else if(Init.after(Final)){
            JOptionPane.showMessageDialog(null, "Voltar no tempo não é permitido!");
            return true;
        }
        else if(this.Data.getValue()==null && group.getSelectedToggle()==DIA){
            JOptionPane.showMessageDialog(null, "Insira uma data!");
            return true;
        }
        else{
            return false;
        }
    }

    public String FuncDQA(){
        String data;
        int Dia;
        int Mes;
        int Ano;
        if(DIA.getText().equals("") || DIA.getText() == null || DIA.getText().equals("0"))
            Dia=0;
        else{
            Dia=Integer.parseInt(DIAS.getText());
        }
        if(MESES.getText().equals("") || MESES.getText() == null || MESES.getText().equals("0"))
            Mes=0;
        else{
            Mes=Integer.parseInt(MESES.getText());
        }
        if(ANOS.getText().equals("") || ANOS.getText() == null || ANOS.getText().equals("0"))
            Ano=0;
        else{
            Ano=Integer.parseInt(ANOS.getText());
        }
        Date dt=new Date();
        Date nw;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SomaData sum=new SomaData();
        nw=SomaData.addYearMonthDay(dt,Ano,Mes,Dia);
        data=dateFormat.format(nw);
        Calendar rightNow = Calendar.getInstance();
        return data;
    }

    public void InsereDados(String data,String ID) throws SQLException{
        String query=" insert into compromissos (Data, Turma, HInicial, HFinal, Atividade,IDProfessor,prof_nome)"
                + " values (?, ?, ?, ?, ?,?,?)";
        PreparedStatement PrepState = con.prepareStatement(query);
        PrepState.setString(1,data);
        PrepState.setObject(2, MarcarController.this.Turma.getValue());
        PrepState.setObject(3, MarcarController.this.HInicial.getText());
        PrepState.setObject(4, MarcarController.this.HFinal.getText());
        PrepState.setObject(5, MarcarController.this.Atividade.getText());
        PrepState.setObject(6,ID);
        PrepState.setObject(7,Nome);
        PrepState.execute();
        JOptionPane.showMessageDialog(null,"Dados gravados com sucesso!");
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }
            
    public boolean EventoIgual(String data) throws SQLException{
            final PreparedStatement ps; 
            ps = con.prepareStatement("SELECT count(*) from compromissos where compromissos.IDProfessor='"+ID+"' and compromissos.Data='"+data+"' AND (compromissos.HInicial = '"+HInicial.getText()+":00' OR (compromissos.HInicial < '"+HInicial.getText()+":00' AND compromissos.HFinal > '"+HInicial.getText()+":00') OR (compromissos.HInicial > '"+HFinal.getText()+":00' AND compromissos.HFinal < '"+HFinal.getText()+":00'))");
            final ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int rowCount = resultSet.getInt(1);
            if(rowCount>0){
                JOptionPane.showMessageDialog(null, "Você já tem um compromisso neste horário do dia!");
                return true;
            }
            else{
                return false;
            }
    }
    
    public void getMaterias(){
         try {  
            con=DBConnect.connect();
            stat = con.createStatement();
            String Mat = null;
            final PreparedStatement ps = con.prepareStatement("select * from professores where ID='"+ID+"'");
            final ResultSet rs = ps.executeQuery();
            String hr = null;
            System.out.println(hr);
            while(rs.next()) {  
                Mat=rs.getString("Materias");  
            }  
            String [] MatList;
            MatList=Mat.split(",");
            System.out.println(MatList);
            for(int i=0;i<MatList.length;i++){
                Turma.getItems().add(MatList[i]);
            }
        } catch (SQLException e) {
            System.out.println("Error on Building Data");
            e.printStackTrace();
    }
    }
}



