/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Henrique
 */
public class VerUsuariosController implements Initializable {

    private ObservableList<Usuario> data;
    @FXML private TableView tabela;
    @FXML private TableColumn Nome;
    @FXML private TableColumn user;
    @FXML private TableColumn Senha;
    @FXML private TableColumn ID;
    @FXML private TextField Busca;
    private SortedList<Usuario> sortedData;
    private FilteredList<Usuario> filteredData;
    private static Connection con;
    private static Statement stat;
    private PreparedStatement prep;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BuscaBD();
        tabela.setRowFactory(
            new Callback<TableView<Usuario>, TableRow<Usuario>>() {
          @Override
          public TableRow<Usuario> call(TableView<Usuario> tableView) {
            final TableRow<Usuario> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Edit");
            editItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Usuario a;
                    a = row.getItem();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdicionarProfessor.fxml"));
                    Parent root = null;
                    try {
                        root = (Parent) fxmlLoader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(VerUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Stage stage=new Stage();
                    AdicionarProfessorController controller = fxmlLoader.<AdicionarProfessorController>getController();
                    System.out.println(a);
                    controller.Editar(a.getNome(), a.getEndereco(), a.getCidade(), a.getUF(), a.getTelefone(),a.getLogin(),a.getSenha(),a.getID(),a.getMaterias());
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Horários de aula");
                    stage.setScene(new Scene(root));
                    stage.show();
                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        System.out.println("Fechou o Stage");
                        BuscaBD();
                        AdicionaFiltro();
                        
                    }
                    });  
                }
            });
            MenuItem removeItem = new MenuItem("Delete");
            removeItem.setOnAction((ActionEvent event) -> {
                try {
                    PreparedStatement ps1;
                    PreparedStatement ps2;
                    Usuario a;
                    a = row.getItem();
                    ps1 = con.prepareStatement("DELETE FROM professores WHERE ID='"+a.getID()+"'");
                    ps1.execute();
                    ps2 = con.prepareStatement("DELETE FROM onestorsoftwar4.compromissos WHERE IDProfessor="+a.getID());
                    ps2.execute();
                    data.remove(row.getItem());
                    AdicionaFiltro();
                } catch (SQLException ex) {
                    Logger.getLogger(VerUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            rowMenu.getItems().addAll(editItem, removeItem);

            // only display context menu for non-null items:
            row.contextMenuProperty().bind(
              Bindings.when(Bindings.isNotNull(row.itemProperty()))
              .then(rowMenu)
              .otherwise((ContextMenu)null));
            return row;
          }
        });
        AdicionaFiltro();
    } 
    
    public void AdicionaFiltro(){
         filteredData = new FilteredList<>(data, p -> true);
            Busca.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Usuario -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Usuario.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; //Filter matches first name.
                }
                else if (Usuario.getLogin().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                else if (Usuario.getSenha().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                else if (Usuario.getID().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tabela.comparatorProperty());
        tabela.setItems(sortedData);
    }
     
   public void BuscaBD(){
         try {  
            con=DBConnect.connect();
            stat = con.createStatement();
            data = FXCollections.observableArrayList();
            final PreparedStatement ps = con.prepareStatement("select * from professores where ISADMIN='"+false+"'");
            final ResultSet rs = ps.executeQuery();
            String hr = null;
            while (rs.next()) {
                data.add(new Usuario(rs.getString("Nome"), rs.getString("Endereço"),rs.getString("Cidade"),rs.getString("UF"),rs.getString("Telefone"),rs.getString("user"),rs.getString("senha"),rs.getString("ID"),rs.getString("Materias")));
            }
            Nome.setCellValueFactory(new PropertyValueFactory("Nome"));
            user.setCellValueFactory(new PropertyValueFactory("user"));
            Senha.setCellValueFactory(new PropertyValueFactory("Senha"));
            ID.setCellValueFactory(new PropertyValueFactory("ID"));
            Nome.setText("Nome");
            user.setText("Usuario");
            Senha.setText("Senha");
            ID.setText("ID");       
        } catch (SQLException e) {
            System.out.println("Error on Building Data");
            e.printStackTrace();
        }
      }

}
