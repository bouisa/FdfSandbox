/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import com.dooapp.fxform.FXForm;
import com.dooapp.fxform.model.ElementController;
import com.dooapp.fxform.reflection.Util;
import com.dooapp.fxform.view.NodeCreationException;
import com.dooapp.fxform.view.factory.DelegateFactory;
import com.dooapp.fxform.view.factory.DisposableNode;
import com.dooapp.fxform.view.factory.DisposableNodeWrapper;
import com.dooapp.fxform.view.factory.NodeFactory;
import com.dooapp.fxform.view.handler.FieldHandler;
import fxdataexamples.beans.CustomerFxBean;
import fxdataexamples.persistence.Customer;
import fxdataexamples.persistence.CustomerJpaController;
import fxdataexamples.persistence.exceptions.NonexistentEntityException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jfxtras.labs.scene.control.CalendarTextField;
import org.javafxdata.datasources.protocol.ObjectDataSource;

/**
 *
 * @author Zero
 */
public class ConfigViewPrototype implements Initializable {
    
    private EntityManagerFactory emf;
    private CustomerJpaController customerJpaController;
    private ObjectDataSource<CustomerFxBean> customerDataSource;

    @FXML
    private TableView<CustomerFxBean> customerTable;
    
    @FXML
    private TextField textfield;
    
    @FXML
    private ScrollPane formPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEntityManager();
        loadCustomerTable();
    }
    
    private void loadEntityManager() {
        emf = Persistence.createEntityManagerFactory("FxDataExamplesPU");
        customerJpaController = new CustomerJpaController(emf);
    }

    private void loadCustomerTable() {
        // Initialize FXForm with custom factory to handle ObjectProperty<Date>
        // The created node uses JFXtra's date picker
        DelegateFactory.addGlobalFactory(new DateHandler(), DATE_FACTORY);
        
        List<Customer> resultList = customerJpaController.findCustomerEntities();
        
        List<CustomerFxBean> adaptedData = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            CustomerFxBean c = new CustomerFxBean(resultList.get(i));
            adaptedData.add(c);
       
            System.out.println(c.getName());
        }
        
        customerDataSource = new ObjectDataSource<>(adaptedData, CustomerFxBean.class, "name", "creditLimit");
        customerDataSource.getNamedColumn("name").setText("Name");
        customerDataSource.getNamedColumn("name").setCellValueFactory(new PropertyValueFactory("name"));
        customerDataSource.getNamedColumn("creditLimit").setText("Credit Limit");
        customerDataSource.getNamedColumn("creditLimit").setCellValueFactory(new PropertyValueFactory("creditLimit"));
        
        customerTable.setItems(customerDataSource.getData());
        customerTable.getColumns().addAll(customerDataSource.getColumns());
        
        customerTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<CustomerFxBean>() {

                    @Override
                    public void changed(ObservableValue<? extends CustomerFxBean> arg0, CustomerFxBean arg1, CustomerFxBean arg2) {
                        if (arg1 != null) {
                            textfield.textProperty().unbindBidirectional(arg1.nameProperty());
                        }
                        if (arg2 != null) {
                            FXForm fxForm = new FXForm(arg2);
                            fxForm.setTitle(" Edit Customer: " + arg2.getName());
                            formPane.setContent(fxForm);

                            textfield.textProperty().bindBidirectional(arg2.nameProperty());
                        }
                        
                    }
                });
    }
    
    /**
     * FXForm's DelegateFactory allows registering a FieldHandler->NodeFactory mapping
     * to generate custom form editors.
     */
    private static class DateHandler implements FieldHandler {
        
        @Override
        public boolean handle(Field field) {
            try {
                return Util.getObjectPropertyGeneric(field).isAssignableFrom(Date.class);
            } catch (Exception e) {
            }
            return false;
        }
    }
    
    private final static NodeFactory DATE_FACTORY = new NodeFactory() {

        public DisposableNode createNode(ElementController elementController) throws NodeCreationException {
//            return new DisposableNodeWrapper(new Label(elementController.getElement().getField().getType() + " wow this worked"),
//                    new Callback<Node, Void>() {
//                        public Void call(Node node) {
//                            return null;
//                        }
//                    });
            
            return new DisposableNodeWrapper(new CalendarTextField(),
                    new Callback<Node, Void>() {
                        public Void call(Node node) {
                            return null;
                        }
                    });
        }
    };

    @FXML
    private void insertButtonClicked(ActionEvent event) {
        Customer c = new Customer(778);
        c.setName("Shinra");
        c.setAddressline1("Midgar");
        c.setEmail("shinra@shinra.com");
        c.setCreditLimit(5000000);
        customerDataSource.getData().add(new CustomerFxBean(c));
    }
    
    @FXML
    private void saveButtonClicked(ActionEvent event) {
        try {
            Customer c = customerTable.getSelectionModel().getSelectedItem().getWrappedCustomer();
            System.out.println("Saving customer: " + c);
            System.out.println(c.getAddressline1() + " " + customerTable.getSelectionModel().getSelectedItem().getAddressline1());
            System.out.println(c.getName() + " " + customerTable.getSelectionModel().getSelectedItem().getName());
            System.out.println(c.getCreditLimit() + " " + customerTable.getSelectionModel().getSelectedItem().getCreditLimit());
            customerJpaController.edit(c);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ConfigViewPrototype.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ConfigViewPrototype.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void deleteButtonClicked(ActionEvent event) {
//        CustomerFxBean bean = customerTable.getSelectionModel().getSelectedItem();
//        bean.setName("hello");
        
//        Customer c = customerTable.getSelectionModel().getSelectedItem().getCustomer();
//        System.out.println("Saving customer: " + c);
//        System.out.println(c.getAddressline1() + " " + customerTable.getSelectionModel().getSelectedItem().getAddressline1());
//        System.out.println(c.getName() + " " + customerTable.getSelectionModel().getSelectedItem().getName());
//        System.out.println(c.getCreditLimit() + " " + customerTable.getSelectionModel().getSelectedItem().getCreditLimit());
    }
}
