/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import fxdataexamples.persistence.Customer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Zero
 */
public class TestTable implements Initializable {
    
    private EntityManagerFactory emf;
    private Vector v1;
    
    private List<CustomerFxBean> customerData;
    private CustomerFxBean firstCustomer;
    
    @FXML
    private TableView<Vector> table;
    
    @FXML
    private TableView<CustomerFxBean> customerTable;
    
    @FXML
    private TextField textfield;
    
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
        loadVectorTable();
        loadCustomerTable();
    }
    
    private void loadEntityManager() {
        emf = Persistence.createEntityManagerFactory("FxDataExamplesPU");
        
    }
    
    private void loadVectorTable() {
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

        TableColumn<Vector, Double> velocityCol = new TableColumn<>("Velocity");
        velocityCol.setCellValueFactory(new PropertyValueFactory("velocity"));
//        velocityCol.setCellFactory(null);

        table.getColumns().setAll(idCol, velocityCol);

//        textfield.textProperty().bindBidirectional(v1.idProperty());
//        DoubleProperty dp = new SimpleDoubleProperty(v1.getVelocity());
//        textfield.textProperty().bind(dp.asString());
//        DoubleProperty dp = null;
//        StringBinding b1 = dp.asString();
    }
    
    private void loadCustomerTable() {
        
        CustomerJpaController controller = new CustomerJpaController(emf);
        List<Customer> resultList = controller.findCustomerEntities();
        
//        Query query = em.createQuery("SELECT c FROM Customer c");
//        List<Customer> resultList = query.getResultList();
        
        List<CustomerFxBean> adaptedData = new ArrayList<>();
        firstCustomer = new CustomerFxBean(resultList.get(0));
        adaptedData.add(firstCustomer);
        for (int i = 1; i < resultList.size(); i++) {
            CustomerFxBean c = new CustomerFxBean(resultList.get(i));
            adaptedData.add(c);
            
            System.out.println(c.getName());
        }
        
        customerData = adaptedData;
        customerTable.setItems(FXCollections.observableArrayList(customerData));
        
        TableColumn<CustomerFxBean, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        customerTable.getColumns().addAll(nameCol);
        
        textfield.textProperty().bindBidirectional(firstCustomer.nameProperty());
    }
        
}
