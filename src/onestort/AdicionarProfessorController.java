/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;


import java.awt.HeadlessException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import org.controlsfx.control.CheckTreeView;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class AdicionarProfessorController implements Initializable {

    @FXML private TextField Nome;
    @FXML private TextField Endereco;
    @FXML private TextField UF;
    @FXML private TextField Cidade;
    @FXML private TextField Telefone;
    @FXML private TextField Login;
    @FXML private Button Adicionar;
    @FXML private TextField Senha;
    @FXML private AnchorPane DaquiaTexto;
    @FXML private TextField Email;
    @FXML private TextField RG;
    @FXML private AnchorPane AnchorPane;
    private CheckTreeView Materias;
    private ObservableList<? extends TreeItem<String>> MateriasSel;
    private static Connection con;
    private static Statement stat;
    private PreparedStatement prep;
    private ToggleGroup group;
    private String str;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CheckBoxTreeItem<String> Cursos = new CheckBoxTreeItem<>("Cursos");
        CheckBoxTreeItem<String> INF = new CheckBoxTreeItem<>("Informática");
        INF.setExpanded(false);
        INF.getChildren().addAll(
            new CheckBoxTreeItem<>("INF 1"),
            new CheckBoxTreeItem<>("INF 2"),
            new CheckBoxTreeItem<>("INF 3"));
        CheckBoxTreeItem<String> Redes = new CheckBoxTreeItem<>("Redes de Computadores");
        Redes.setExpanded(false);
        Redes.getChildren().addAll(
            new CheckBoxTreeItem<>("RDC 1"),
            new CheckBoxTreeItem<>("RDC 2"),
            new CheckBoxTreeItem<>("RDC 3"));
        CheckBoxTreeItem<String> Eletrônica = new CheckBoxTreeItem<>("Eletrônica");
        Eletrônica.setExpanded(false);
        Eletrônica.getChildren().addAll(
            new CheckBoxTreeItem<>("ELT 1"),
            new CheckBoxTreeItem<>("ELT 2"),
            new CheckBoxTreeItem<>("ELT 3"));
        CheckBoxTreeItem<String> Eletrotécnica = new CheckBoxTreeItem<>("Eletrotécnica");
        Eletrotécnica.setExpanded(false);
        Eletrotécnica.getChildren().addAll(
            new CheckBoxTreeItem<>("ELE 1"),
            new CheckBoxTreeItem<>("ELE 2"),
            new CheckBoxTreeItem<>("ELE 3"));
        CheckBoxTreeItem<String> HOSP = new CheckBoxTreeItem<>("Hospedagem");
        HOSP.setExpanded(false);
        HOSP.getChildren().addAll(
            new CheckBoxTreeItem<>("HOSP 1"),
            new CheckBoxTreeItem<>("HOSP 2"),
            new CheckBoxTreeItem<>("HOSP 3"));
        CheckBoxTreeItem<String> EBM = new CheckBoxTreeItem<>("Equipamentos Biomédicos");
        EBM.setExpanded(false);
        EBM.getChildren().addAll(
            new CheckBoxTreeItem<>("EBM 1"),
            new CheckBoxTreeItem<>("EBM 2"),
            new CheckBoxTreeItem<>("EBM 3"));
        CheckBoxTreeItem<String> MEI = new CheckBoxTreeItem<>("Meio ambiente");
        MEI.setExpanded(false);
        MEI.getChildren().addAll(
            new CheckBoxTreeItem<>("MEI 1"),
            new CheckBoxTreeItem<>("MEI 2"),
            new CheckBoxTreeItem<>("MEI 3"));
        CheckBoxTreeItem<String> MEC = new CheckBoxTreeItem<>("Mecânica");
        MEC.setExpanded(false);
        MEC.getChildren().addAll(
            new CheckBoxTreeItem<>("MEC 1"),
            new CheckBoxTreeItem<>("MEC 2"),
            new CheckBoxTreeItem<>("MEC 3"));
        CheckBoxTreeItem<String> MECAT = new CheckBoxTreeItem<>("Mecatrônica");
        MECAT.setExpanded(false);
        MECAT.getChildren().addAll(
            new CheckBoxTreeItem<>("MECAT 1"),
            new CheckBoxTreeItem<>("MECAT 2"),
            new CheckBoxTreeItem<>("MECAT 3"));
        Cursos.getChildren().addAll(INF,Redes,MECAT,MEC,Eletrônica,Eletrotécnica,MEI,EBM);
        Materias=new CheckTreeView<>(Cursos);
        //<CheckTreeView fx:id="Materias" layoutX="10.0" layoutY="154.0" prefHeight="93.0" prefWidth="357.0" />
        AnchorPane.getChildren().add(Materias);
        Materias.setLayoutX(10);
        Materias.setLayoutY(154);
        Materias.setPrefHeight(93);
        Materias.setPrefWidth(357);
    }    
    
    public void Grava() throws ParseException{
        UUID idOne = UUID.randomUUID();
        String ID=idOne.toString();
        if(PegaErros()==true){}
        else{
          try {  
            con=DBConnect.connect();
            stat = con.createStatement();
            if(loginIgual()==true){}
            else{
                StringBuilder sb = new StringBuilder();
                MateriasSel=Materias.getCheckModel().getCheckedItems();
                for (int i = 0, max = MateriasSel.size(); i < max; i++) {
                    if(MateriasSel.get(i).getValue().length()>7 || MateriasSel.get(i).getValue()=="Cursos"){}
                    else{
                        sb.append(MateriasSel.get(i).getValue());
                        if (i < max - 1) {
                            sb.append(",");
                        }
                    }
                }
                str = sb.toString();
                System.out.print(str);
                InsereDados(ID,str);
            }
            
        } catch (SQLException | NumberFormatException | HeadlessException e) {
            System.out.println("Erro ao gravar dados!");
            e.printStackTrace();
        }
                }
    }

    public void Editar(String Nome,String Endereco,String Cidade,String UF,String Telefone,String Login,String Senha,String ID,String Materias){
        this.Nome.setText(Nome);
        this.Endereco.setText(Endereco);
        this.Cidade.setText(Cidade);
        this.Telefone.setText(Telefone);
        this.Login.setText(Login);
        this.Cidade.setText(Cidade);
        this.Senha.setText(Senha);
        this.UF.setText(UF);
        Adicionar.setOnAction((ActionEvent event) -> {
            try {
                if(PegaErros()==true){}
                else{
                try {
                    PreparedStatement ps = null;
                    con=DBConnect.connect();
                        StringBuilder sb = new StringBuilder();
                        MateriasSel=this.Materias.getCheckModel().getCheckedItems();
                        for (int i = 0, max = MateriasSel.size(); i < max; i++) {
                            if(MateriasSel.get(i).getValue().length()>7 || MateriasSel.get(i).getValue()=="Cursos"){}
                            else{
                                sb.append(MateriasSel.get(i).getValue());
                                if (i < max - 1) {
                                    sb.append(",");
                                }
                            }
                        }
                        str = sb.toString();
                        System.out.print(str);
                        String SQL = "UPDATE professores SET Nome='"+this.Nome.getText()
                                + "',user='"+this.Login.getText()
                                + "',senha='"+this.Senha.getText()
                                + "',Endereço='"+this.Endereco.getText()
                                + "',Cidade='"+this.Cidade.getText()
                                + "',UF='"+this.UF.getText()
                                + "',Materias='"+str
                                + "' WHERE id="+ID;
                        PreparedStatement PrepState = con.prepareStatement(SQL);
                        PrepState.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Dados gravados com sucesso!");
                }catch (SQLException ex) {
                    Logger.getLogger(MarcarController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(MarcarController.class.getName()).log(Level.SEVERE, null, ex);
        }
});
        
    }
    
    public boolean PegaErros() throws ParseException{
        if(this.Nome.getText() == null || "".equals(this.Nome.getText())){
            JOptionPane.showMessageDialog(null, "Campo nome vazio!");
            return true;
        }
        else if(this.Endereco.getText() == null || "".equals(this.Endereco.getText())){
            JOptionPane.showMessageDialog(null, "Campo endereço vazio!");
            return true;
        }
        else if(this.UF.getText() == null || "".equals(this.UF.getText())){
            JOptionPane.showMessageDialog(null, "Campo UF vazio!");
            return true;
        }
        else if(this.Cidade.getText() == null || "".equals(this.Cidade.getText())){
            JOptionPane.showMessageDialog(null,"Campo cidade vazio!");
            return true;
        }
        else if(this.Telefone.getText() == null || "".equals(this.Telefone.getText())){
            JOptionPane.showMessageDialog(null,"Campo telefone vazio!");
            return true;
        }
        else if(this.Login.getText() == null || "".equals(this.Login.getText())){
            JOptionPane.showMessageDialog(null,"Campo login vazio!");
            return true;
        }
        else if(this.Senha.getText() == null || "".equals(this.Senha.getText())){
            JOptionPane.showMessageDialog(null,"Campo senha vazio!");
            return true;
        }
        else{
            return false;
        }
    }

    public void InsereDados(String ID,String Materias) throws SQLException{
        String query=" insert into professores (Nome, user, senha, Endereço,Cidade,UF,Telefone,Materias,ISADMIN)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement PrepState = con.prepareStatement(query);
        PrepState.setObject(1, AdicionarProfessorController.this.Nome.getText());
        PrepState.setObject(2, AdicionarProfessorController.this.Login.getText());
        PrepState.setObject(3, AdicionarProfessorController.this.Senha.getText());
        PrepState.setObject(4, AdicionarProfessorController.this.Endereco.getText());
        PrepState.setObject(5, AdicionarProfessorController.this.Cidade.getText());
        PrepState.setObject(6, AdicionarProfessorController.this.UF.getText());
        PrepState.setObject(7, AdicionarProfessorController.this.Telefone.getText());
        PrepState.setObject(8, Materias);
        PrepState.setObject(9,0);
        PrepState.execute();
        JOptionPane.showMessageDialog(null,"Dados gravados com sucesso!");
    }
                
    public boolean loginIgual() throws SQLException{
            final PreparedStatement ps; 
            ps = con.prepareStatement("SELECT count(*) from professores where professores.user='"+Login.getText()+"'");
            final ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int rowCount = resultSet.getInt(1);
            if(rowCount>0){
                JOptionPane.showMessageDialog(null, "Um usuário já tem este nome!");
                return true;
            }
            else{
                return false;
            }
    }
    
}



