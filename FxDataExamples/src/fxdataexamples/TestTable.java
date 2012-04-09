/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Zero
 */
public class TestTable implements Initializable {
    
    @FXML
    private TableView<Vector> table;
    
    @FXML
    private void handleTableDrag(MouseEvent event) {
        System.out.println("You drag me!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("initilizing");
        Vector v1 = new Vector();
        v1.setId("Cecil");
        Vector v2 = new Vector();
        v2.setId("Kain");
        
        ObservableList<Vector> vectors = FXCollections.observableArrayList(v1, v2);
        
        table.setItems(vectors);
        
        TableColumn<Vector, String> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        
        table.getColumns().setAll(idCol);
    }    
}
