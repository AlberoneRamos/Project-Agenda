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
public class VerCompromissosController implements Initializable {

    private ObservableList<Compromisso> data;
    @FXML private TableView tabela;
    @FXML private TableColumn Data;
    @FXML private TableColumn Turma;
    @FXML private TableColumn Horario;
    @FXML private TableColumn Atividade;
    @FXML private TextField Busca;
    private SortedList<Compromisso> sortedData;
    private FilteredList<Compromisso> filteredData;
    private static Connection con;
    private static Statement stat;
    private PreparedStatement prep;
    private String ID;
    private String Nome;

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BuscaBD();
        tabela.setRowFactory(
            new Callback<TableView<Compromisso>, TableRow<Compromisso>>() {
          @Override
          public TableRow<Compromisso> call(TableView<Compromisso> tableView) {
            final TableRow<Compromisso> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Edit");
            editItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Compromisso a; 
                    a= row.getItem();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Marcar.fxml"));
                    Parent root = null;
                    Stage stage=new Stage();
                    MarcarController controller = new MarcarController();
                    controller.setID(ID);
                    controller.setNome(Nome);
                    System.out.print(a);
                    fxmlLoader.setController(controller);
                    try {
                        root = (Parent) fxmlLoader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(VerCompromissosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    controller.Editar(a.getHInicial(), a.getHFinal(), a.getTurma(), a.getAtividade(), a.getData());
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
                    final PreparedStatement ps;
                    Compromisso a;
                    a = row.getItem();
                    ps = con.prepareStatement("DELETE FROM compromissos WHERE Data ='"+a.getData()+"' AND IDProfessor='"+ID+"' AND HInicial='"+a.getHInicial()+"'");
                    ps.execute();
                    data.remove(row.getItem());
                    AdicionaFiltro();
                } catch (SQLException ex) {
                    Logger.getLogger(VerCompromissosController.class.getName()).log(Level.SEVERE, null, ex);
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
            filteredData.setPredicate(Compromisso -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Compromisso.getHorario().toLowerCase().contains(lowerCaseFilter)) {
                    return true; //Filter matches first name.
                }
                else if (Compromisso.getData().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                else if (Compromisso.getAtividade().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                else if (Compromisso.getTurma().toLowerCase().contains(lowerCaseFilter)) {
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
            final PreparedStatement ps = con.prepareStatement("select * from compromissos where IDProfessor='"+ID+"'");
            final ResultSet rs = ps.executeQuery();
            String hr = null;
            while (rs.next()) {
                hr=rs.getString("HInicial").substring(0, 5)+"-"+rs.getString("HFinal").substring(0, 5);
                System.out.println(hr);
                data.add(new Compromisso(rs.getString("Data"), rs.getString("Atividade"),hr,rs.getString("Turma"),rs.getString("HInicial"),rs.getString("HFinal"),rs.getString("IDProfessor")));
            }
            Data.setCellValueFactory(new PropertyValueFactory("Data"));
            Turma.setCellValueFactory(new PropertyValueFactory("Turma"));
            Horario.setCellValueFactory(new PropertyValueFactory("Horario"));
            //HFinal.setCellValueFactory(new PropertyValueFactory("HFinal"));
            Atividade.setCellValueFactory(new PropertyValueFactory("Atividade"));
            Data.setText("Data");
            Turma.setText("Turma");
            Horario.setText("Horario");
            //HFinal.setText("Término");
            Atividade.setText("Atividade");       
        } catch (SQLException e) {
            System.out.println("Error on Building Data");
            e.printStackTrace();
        }
      }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

   
}
