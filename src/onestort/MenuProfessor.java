/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class MenuProfessor implements Initializable {
    
    @FXML private Text Greet;
    private String Nm;
    private String ID;
    @FXML private Button AddComp;
    @FXML private Button VerAg;
    @FXML private AnchorPane AnchorSub;
    @FXML private Button LogOut;
    @FXML private Button VerCompromissos;
    @FXML private Button Ajuda;
    private Connection con;
    private Statement stat;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Nm);
        Greet.setText("Bem-vindo, "+Nm+"!");
        System.out.println(ID);
        AddComp.setOnAction((ActionEvent event) -> {
            try {
                AddComp();
            } catch (IOException ex) {
                Logger.getLogger(TelaAdmController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        LogOut.setOnAction((ActionEvent event) -> {
            JOptionPane.showMessageDialog(null, "Até mais!");
            Node  source = (Node)  event.getSource(); 
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        });
        VerCompromissos.setOnAction((ActionEvent event) -> {
            try {
                VerCompromissos();
            } catch (IOException ex) {
                Logger.getLogger(TelaAdmController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        VerAg.setOnAction((ActionEvent event) -> {
            try {
                VerAgenda();
            } catch (IOException ex) {
                Logger.getLogger(TelaAdmController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Ajuda.setOnAction((ActionEvent event) -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Help.fxml"));
                Stage stage=new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(TelaAdmController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    public void AddComp() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Marcar.fxml"));
        MarcarController c=new MarcarController();
        c.setID(ID);
        c.setNome(Nm);
        fxmlLoader.setController(c);
        Parent root1 = (Parent) fxmlLoader.load();
        AnchorSub.getChildren().clear();
        AnchorSub.getChildren().add(root1);
    }
    
    public void AddAdm() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuProfessor.fxml"));
        MenuProfessor c=new MenuProfessor();
        c.setID(ID);
        fxmlLoader.setController(c);
        Parent root1 = (Parent) fxmlLoader.load();
        AnchorSub.getChildren().clear();
        AnchorSub.getChildren().add(root1);
    }
    
    public void setNm(String Nm){
        this.Nm=Nm;
    }

    private void VerCompromissos() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerCompromissos.fxml"));
        VerCompromissosController c=new VerCompromissosController();
        c.setID(ID);
        c.setNome(Nm);
        fxmlLoader.setController(c);
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Ver usuários");
        stage.setScene(new Scene(root1));  
        stage.show(); 
    }

    private void VerAgenda() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXtrasAg.fxml"));
        FXtrasAgController c=new FXtrasAgController();
        c.setID(ID); 
        fxmlLoader.setController(c);
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Ver usuários");
        stage.setScene(new Scene(root1));  
        stage.show(); 
    }
}
