/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;

import java.io.IOException;
import java.net.URL;
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
public class TelaAdmController implements Initializable {
    
    @FXML private Text Greet;
    private String Nm;
    private String ID;
    @FXML private Button AddProf;
    @FXML private Button AddADM;
    @FXML private AnchorPane AnchorSub;
    @FXML private Button LogOut;
    @FXML private Button VerUsuarios;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Nm);
        Greet.setText("Bem-vindo, "+Nm+"!");
        System.out.println(ID);
        AddProf.setOnAction((ActionEvent event) -> {
            try {
                AddProf();
            } catch (IOException ex) {
                Logger.getLogger(TelaAdmController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        AddADM.setOnAction((ActionEvent event) -> {
            try {
                AddAdm();
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
        VerUsuarios.setOnAction((ActionEvent event) -> {
            try {
                VerUsuarios();
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
    
    public void AddProf() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdicionarProfessor.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        AnchorSub.getChildren().clear();
        AnchorSub.getChildren().add(root1);
    }
    
    public void AddAdm() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdicionarAdm.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        AnchorSub.getChildren().clear();
        AnchorSub.getChildren().add(root1);
    }
    
    public void setNm(String Nm){
        this.Nm=Nm;
    }

    private void VerUsuarios() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerUsuarios.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Ver usuários");
        stage.setScene(new Scene(root1));  
        stage.show(); 
    }
}
