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
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jfxtras.labs.scene.control.CalendarTextField;

/**
 *
 * @author Zero
 */
public class ConfigViewPrototype implements Initializable {
    
    private EntityManagerFactory emf;
    private List<CustomerFxBean> customerData;

    @FXML
    private TableView<CustomerFxBean> customerTable;
    
    @FXML
    private TextField textfield;
    
    @FXML
    private ScrollPane formPane;
    
    @FXML
    private void handleTableDrag(MouseEvent event) {
        System.out.println("You drag me!");
    }
    
    @FXML
    private void buttonclick(ActionEvent event) {
    }
    
    @FXML
    private void bindPressed(ActionEvent event) {
    }
    
    @FXML
    private void textfieldenter(ActionEvent event) {
        System.out.println("text field pressed enter");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEntityManager();
        loadCustomerTable();
    }
    
    private void loadEntityManager() {
        emf = Persistence.createEntityManagerFactory("FxDataExamplesPU");
        
    }

    private void loadCustomerTable() {
        // Initialize FXForm with custom factory to handle ObjectProperty<Date>
        // The created node uses JFXtra's date picker
        DelegateFactory.addGlobalFactory(new DateHandler(), DATE_FACTORY);
        
        CustomerJpaController controller = new CustomerJpaController(emf);
        List<Customer> resultList = controller.findCustomerEntities();
//        Query query = em.createQuery("SELECT c FROM Customer c");
//        List<Customer> resultList = query.getResultList();
        
        List<CustomerFxBean> adaptedData = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            CustomerFxBean c = new CustomerFxBean(resultList.get(i));
            adaptedData.add(c);
            
            System.out.println(c.getName());
        }
        
        customerData = adaptedData;
        customerTable.setItems(FXCollections.observableArrayList(customerData));
        
        
        TableColumn<CustomerFxBean, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn<CustomerFxBean, Integer> creditLimitCol = new TableColumn<>("Credit Limit");
        creditLimitCol.setCellValueFactory(new PropertyValueFactory("creditLimit"));
        
        customerTable.getColumns().addAll(nameCol, creditLimitCol);
        

        customerTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<CustomerFxBean>() {

                    @Override
                    public void changed(ObservableValue<? extends CustomerFxBean> arg0, CustomerFxBean arg1, CustomerFxBean arg2) {
                        FXForm fxForm = new FXForm(arg2);
                        formPane.setContent(fxForm);

                        if (arg1 != null) {
                            textfield.textProperty().unbindBidirectional(arg1.nameProperty());
                        }
                        if (arg2 != null) {
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

}
