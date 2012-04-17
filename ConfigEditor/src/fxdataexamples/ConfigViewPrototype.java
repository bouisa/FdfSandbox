/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import fxdataexamples.beans.CustomerFxBean;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import org.javafxdata.datasources.protocol.ObjectDataSource;

/**
 *
 * @author Zero
 */
public class ConfigViewPrototype implements Initializable {
    
    private ConfigEditorController controller;

    @FXML
    private TableView<CustomerFxBean> customerTable;
    
    @FXML
    private ScrollPane formPane;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button removeButton;
    
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = new ConfigEditorController(this);
        initSelectionListener();
        controller.refresh();
    }

    private void initSelectionListener() {
        addButton.disableProperty().bind(customerTable.getSelectionModel().selectedItemProperty().isNull());
        removeButton.disableProperty().bind(customerTable.getSelectionModel().selectedItemProperty().isNull());
        saveButton.disableProperty().bind(controller.formChangedProperty.not());
        
        customerTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {

                    @Override
                    public void changed(ObservableValue arg0, Object arg1, Object arg2) {
                        controller.changeActiveBean(formPane.getContent(), arg1, arg2);
                    }
                });
    }
    
    public void loadData(ObjectDataSource dataSource) {
        customerTable.setItems(dataSource.getData());
        customerTable.getColumns().addAll(dataSource.getColumns());
    }
    
    public void loadBeanForm(Node form) {
        formPane.setContent(form);
    }

    @FXML
    private void addActionFired(ActionEvent event) {
        controller.addRow();
    }
    
    @FXML
    private void saveActionFired(ActionEvent event) {
        controller.save(customerTable.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    private void deleteActionFired(ActionEvent event) {

    }
    
    @FXML
    private void resetActionFired(ActionEvent event) {
        
    }
}
