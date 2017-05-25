/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;

import java.io.IOException;
import static java.lang.Boolean.TRUE;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class LoginController implements Initializable {

    @FXML TextField User;
    @FXML TextField Password;
    @FXML Button LoginButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
  
    public void Logar() throws IOException {
        try{
                String url = "jdbc:mysql://mysql06.onestorsoftwares.hospedagemdesites.ws/"; //URL do host
                String nomeBD = "onestorsoftwar4"; //Nome do db a ser usado
                String driver = "com.mysql.jdbc.Driver";
                String username = "onestorsoftwar4";
                    Class.forName(driver).newInstance();
                    Connection conexao = DriverManager.getConnection(url+nomeBD,username,"academico1");
                    Statement s = conexao.createStatement();
                    Statement st = conexao.createStatement();
                    ResultSet result = st.executeQuery("SELECT * FROM professores WHERE user = '" + User.getText()
                            + "' AND senha = '" + Password.getText() + "' AND ISADMIN=0"
                    ); // executa query para checar se existe um administrdor com o mesmo login e senha
                    
                    if (!result.next()) {
                        JOptionPane.showMessageDialog(null, "Credenciais inválidas.");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Logado");
                        if(result.getBoolean("ISADMIN")==TRUE){
                            TelaAdmController c=new TelaAdmController();
                            c.setNm(result.getString("Nome"));
                            c.setID(result.getString("ID"));
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TelaAdm.fxml"));
                            fxmlLoader.setController(c);
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setTitle("Tela administração");
                            stage.setScene(new Scene(root1));  
                            stage.show();
                        }
                        else{
                            MenuProfessor c=new MenuProfessor();
                            c.setNm(result.getString("Nome"));
                            c.setID(result.getString("ID"));
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuProfessor.fxml"));
                            fxmlLoader.setController(c);
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setTitle("Tela professor");
                            stage.setScene(new Scene(root1));  
                            stage.show();
                        }   
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro");
                    ex.printStackTrace();
                }
            }
    
}
