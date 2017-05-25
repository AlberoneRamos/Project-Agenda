/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onestort;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoaderAprimorado {
    
    public  <controllerType> controllerType pegarController(Stage window, String caminho){
             Parent root = null;
                try {
                    FXMLLoader loader= new FXMLLoader(getClass().getResource(caminho));
                    System.out.println("LOADER" + loader);
                     root = (Parent) loader.load();
                     if(root != null){
                        Scene novaCena = new Scene(root);
                        window.setScene(novaCena);
                        controllerType controller = loader.<controllerType>getController();
                        return controller;
                        //viewController a = (viewController) window.getScene().ge;
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

        return null;
    }
}
