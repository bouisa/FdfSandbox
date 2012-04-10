/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Zero
 */
public class TestTable implements Initializable {
    
    private Vector v1;
    
    @FXML
    private TableView<Vector> table;
    
    @FXML
    private void handleTableDrag(MouseEvent event) {
        System.out.println("You drag me!");
    }
    
    @FXML
    private void buttonclick(ActionEvent event) {
        v1.setId("Rydia");
        v1.setVelocity(5.5);
        Vector v3 = new Vector();
        v3.setId("Tellah");
        table.getItems().add(v3);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initilizing");
        v1 = new Vector();
        v1.setId("Cecil");
        v1.setVelocity(2.0);
        Vector v2 = new Vector();
        v2.setId("Kain");
        v2.setVelocity(3.5);
        
        ObservableList<Vector> vectors = FXCollections.observableArrayList(v1, v2);
        
        table.setItems(vectors);
        
        TableColumn<Vector, String> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn<Vector, String> velocityCol = new TableColumn<>("Velocity");
        velocityCol.setCellValueFactory(new PropertyValueFactory("velocity"));
        velocityCol.setCellFactory(null);
        
        table.getColumns().setAll(idCol, velocityCol);
    }    
}
