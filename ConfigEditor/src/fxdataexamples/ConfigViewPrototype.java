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
public class ConfigViewPrototype implements Initializable, TableFormView {
    
    private TableFormPresenter presenter;

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
    
    private final ChangeListener selectionListener = new ChangeListener() {

        @Override
        public void changed(ObservableValue arg0, Object arg1, Object arg2) {
            presenter.changeActiveBean(formPane.getContent(), arg1, arg2);
        }
    };

    public TableFormPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(TableFormPresenter presenter) {
        this.presenter = presenter;
        initPresenter();
    }
    
    private void initPresenter() {
        dispose();
        initBindings();
        presenter.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void initBindings() {
        addButton.disableProperty().bind(customerTable.getSelectionModel().selectedItemProperty().isNull());
        removeButton.disableProperty().bind(customerTable.getSelectionModel().selectedItemProperty().isNull());
        saveButton.disableProperty().bind(presenter.formChangedProperty().not());
        
        customerTable.getSelectionModel().selectedItemProperty().addListener(selectionListener);
    }
    
    private void dispose() {
        addButton.disableProperty().unbind();
        removeButton.disableProperty().unbind();
        saveButton.disableProperty().unbind();
        
        customerTable.getSelectionModel().selectedItemProperty().removeListener(selectionListener);
    }
    
    @Override
    public void loadData(ObjectDataSource dataSource) {
        customerTable.setItems(dataSource.getData());
        customerTable.getColumns().addAll(dataSource.getColumns());
    }
    
    @Override
    public void loadBeanForm(Node form) {
        formPane.setContent(form);
    }

    @FXML
    private void addActionFired(ActionEvent event) {
        presenter.addRow();
    }
    
    @FXML
    private void saveActionFired(ActionEvent event) {
        presenter.save(customerTable.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    private void deleteActionFired(ActionEvent event) {

    }
    
    @FXML
    private void resetActionFired(ActionEvent event) {
        
    }
}
