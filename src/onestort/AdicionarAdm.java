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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import org.controlsfx.control.CheckTreeView;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class AdicionarAdm implements Initializable {

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
    @FXML private CheckTreeView Materias;
    private static Connection con;
    private static Statement stat;
    private PreparedStatement prep;
    private ToggleGroup group;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CheckBoxTreeItem<String> Cursos = new CheckBoxTreeItem<>("Cursos");
        CheckBoxTreeItem<String> INF = new CheckBoxTreeItem<>("Informática");
        INF.setExpanded(true);
        INF.getChildren().addAll(
            new CheckBoxTreeItem<>("1º Ano"),
            new CheckBoxTreeItem<>("2º Ano"),
            new CheckBoxTreeItem<>("3º Ano"));
        CheckBoxTreeItem<String> Redes = new CheckBoxTreeItem<>("Redes de Computadores");
        INF.setExpanded(true);
        INF.getChildren().addAll(
            new CheckBoxTreeItem<>("1º Ano"),
            new CheckBoxTreeItem<>("2º Ano"),
            new CheckBoxTreeItem<>("3º Ano"));
        CheckBoxTreeItem<String> Eletrônica = new CheckBoxTreeItem<>("Eletrônica");
        INF.setExpanded(true);
        INF.getChildren().addAll(
            new CheckBoxTreeItem<>("1º Ano"),
            new CheckBoxTreeItem<>("2º Ano"),
            new CheckBoxTreeItem<>("3º Ano"));
        CheckBoxTreeItem<String> Eletrotécnica = new CheckBoxTreeItem<>("Eletrotécnica");
        INF.setExpanded(true);
        INF.getChildren().addAll(
            new CheckBoxTreeItem<>("1º Ano"),
            new CheckBoxTreeItem<>("2º Ano"),
            new CheckBoxTreeItem<>("3º Ano"));
        CheckBoxTreeItem<String> Mecatrônica = new CheckBoxTreeItem<>("Mecatrônica");
        INF.setExpanded(true);
        INF.getChildren().addAll(
            new CheckBoxTreeItem<>("1º Ano"),
            new CheckBoxTreeItem<>("2º Ano"),
            new CheckBoxTreeItem<>("3º Ano"));
        CheckBoxTreeItem<String> EBM = new CheckBoxTreeItem<>("Equipamentos Biomédicos");
        INF.setExpanded(true);
        INF.getChildren().addAll(
            new CheckBoxTreeItem<>("1º Ano"),
            new CheckBoxTreeItem<>("2º Ano"),
            new CheckBoxTreeItem<>("3º Ano"));
        CheckBoxTreeItem<String> MEI = new CheckBoxTreeItem<>("Meio ambiente");
        INF.setExpanded(true);
        INF.getChildren().addAll(
            new CheckBoxTreeItem<>("1º Ano"),
            new CheckBoxTreeItem<>("2º Ano"),
            new CheckBoxTreeItem<>("3º Ano"));
        CheckBoxTreeItem<String> MEC = new CheckBoxTreeItem<>("Mecânica");
        INF.setExpanded(true);
        INF.getChildren().addAll(
            new CheckBoxTreeItem<>("1º Ano"),
            new CheckBoxTreeItem<>("2º Ano"),
            new CheckBoxTreeItem<>("3º Ano"));
        CheckBoxTreeItem<String> MECAT = new CheckBoxTreeItem<>("Mecatrônica");
        INF.setExpanded(true);
        INF.getChildren().addAll(
            new CheckBoxTreeItem<>("1º Ano"),
            new CheckBoxTreeItem<>("2º Ano"),
            new CheckBoxTreeItem<>("3º Ano"));
        Cursos.getChildren().addAll(INF,Redes,MECAT,MEC,Eletrônica,Eletrotécnica,MEI,EBM);
        Materias=new CheckTreeView<>(Cursos);
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
                InsereDados(ID);
            }
            
        } catch (SQLException | NumberFormatException | HeadlessException e) {
            System.out.println("Erro ao gravar dados!");
            e.printStackTrace();
        }
                }
    }

    public void Editar(String Nome,String Endereco,String Cidade,String UF,String Telefone,String Login,String Senha,String ID){
        this.Nome.setText(Nome);
        this.Endereco.setText(Endereco);
        this.Cidade.setText(Cidade);
        this.Telefone.setText(Telefone);
        this.Login.setText(Login);
        this.Cidade.setText(Cidade);
        this.Senha.setText(Senha);
        Adicionar.setOnAction((ActionEvent event) -> {
            try {
                if(PegaErros()==true){}
                else{
                    try {
                        PreparedStatement ps = null;
                        ps = con.prepareStatement("delete from professores where ID="+ID);
                        ResultSet resultSet = ps.executeQuery();
                        if(loginIgual()==true){}
                        else {
                            InsereDados(ID);
                        }
                        
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

    public void InsereDados(String ID) throws SQLException{
        String query=" insert into professores (Nome, ID, user, senha, Endereço,Cidade,UF,Telefone,ISADMIN)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement PrepState = con.prepareStatement(query);
        PrepState.setObject(1, AdicionarAdm.this.Nome.getText());
        PrepState.setObject(2, ID);
        PrepState.setObject(3, AdicionarAdm.this.Login.getText());
        PrepState.setObject(4, AdicionarAdm.this.Senha.getText());
        PrepState.setObject(5, AdicionarAdm.this.Endereco.getText());
        PrepState.setObject(6, AdicionarAdm.this.Cidade.getText());
        PrepState.setObject(7, AdicionarAdm.this.UF.getText());
        PrepState.setObject(8, AdicionarAdm.this.Telefone.getText());
        PrepState.setObject(9,1);
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



