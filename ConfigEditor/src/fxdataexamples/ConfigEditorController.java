/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import com.dooapp.fxform.FXForm;
import com.dooapp.fxform.model.ElementController;
import com.dooapp.fxform.reflection.Util;
import com.dooapp.fxform.view.FXFormSkinFactory;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jfxtras.labs.scene.control.CalendarTextField;
import org.javafxdata.datasources.protocol.ObjectDataSource;

/**
 *
 * @author Zero
 */
public class ConfigEditorController {
    
    private ConfigViewPrototype view;
    private EntityManagerFactory emf;
    private CustomerJpaController customerJpaController;
    private ObjectDataSource<CustomerFxBean> customerDataSource;
    
    private final static NodeFactory DATE_FACTORY = new NodeFactory() {

        @Override
        public DisposableNode createNode(ElementController elementController) throws NodeCreationException {
//            return new DisposableNodeWrapper(new Label(elementController.getElement().getField().getType() + " wow this worked"),
//                    new Callback<Node, Void>() {
//                        public Void call(Node node) {
//                            return null;
//                        }
//                    });

            elementController.getValue();
            CalendarTextField cal = new CalendarTextField();

            return new DisposableNodeWrapper(cal,
                    new Callback<Node, Void>() {

                        @Override
                        public Void call(Node node) {
                            System.out.println("disposable callback called");
                            return null;
                        }
                    });
        }
    };
    
    public ConfigEditorController(ConfigViewPrototype view) {
        this.view = view;
        this.emf = Persistence.createEntityManagerFactory("FxDataExamplesPU");
        this.customerJpaController = new CustomerJpaController(emf);
        
        // Initialize FXForm with custom factory to handle ObjectProperty<Date>
        // The created node uses JFXtra's date picker
        DelegateFactory.addGlobalFactory(new DateHandler(), DATE_FACTORY);
        
//        ConfigEditorController.class.getResource("/fxdataexamples/ConfigEditorController.css").toExternalForm());
    }

    /**
     * FXForm's DelegateFactory allows registering a FieldHandler->NodeFactory
     * mapping to generate custom form editors.
     */
    private static class DateHandler implements FieldHandler {

        @Override
        public boolean handle(Field field) {
            if(!field.getType().isAssignableFrom(ObjectProperty.class)) {
                return false;
            }
            
            try {
                return Util.getObjectPropertyGeneric(field).isAssignableFrom(Date.class);
            } catch (Exception e) {
                // Null pointer exception will be thrown if this handler attempts
                // to evaluate a non ObjectProperty field
            }
            
            return false;
        }
    }

    public void refresh() {
        List<Customer> resultList = customerJpaController.findCustomerEntities();

        List<CustomerFxBean> adaptedData = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            CustomerFxBean c = new CustomerFxBean(resultList.get(i));
            adaptedData.add(c);

            System.out.println(c.getName());
        }

        customerDataSource = new ObjectDataSource<>(adaptedData, CustomerFxBean.class, "name", "creditLimit");
        
        customerDataSource.getData();
        
        customerDataSource.getNamedColumn("name").setText("Name");
        customerDataSource.getNamedColumn("name").setCellValueFactory(new PropertyValueFactory("name"));
        customerDataSource.getNamedColumn("creditLimit").setText("Credit Limit");
        customerDataSource.getNamedColumn("creditLimit").setCellValueFactory(new PropertyValueFactory("creditLimit"));
        
        view.loadData(customerDataSource);
    }
    
    public void changeActiveBean(Node oldForm, Object oldBean, Object newBean) {
        if(oldForm != null) {
            FXForm of = (FXForm) oldForm;
            of.dispose();
        }
        
        if (newBean != null) {
            CustomerFxBean nb = (CustomerFxBean) newBean;
            FXForm fxForm = new FXForm();
            
            fxForm.setSkin(FXFormSkinFactory.INLINE_FACTORY.createSkin(fxForm));
            fxForm.setSource(nb);
            fxForm.setTitle(" Edit Customer: " + nb.getName());
            
            formChangedProperty.unbind();
            formChangedProperty.bind(getBeanChangeBinding(nb));
            
            view.loadBeanForm(fxForm);
        }
    }
    
    private BooleanBinding getBeanChangeBinding(CustomerFxBean bean) {
        BooleanBinding changed = 
                bean.nameProperty().isNotEqualTo(bean.getName())
                .or(bean.addressline1Property().isNotEqualTo(bean.getAddressline1()))
                .or(bean.customerIdProperty().isNotEqualTo(bean.getCustomerId()))
                .or(bean.creditLimitProperty().isNotEqualTo(bean.getCreditLimit()))
                .or(bean.dateProperty().isNotEqualTo(bean.getDate()))
                .or(bean.emailProperty().isNotEqualTo(bean.getEmail()));
        
        return changed;
    }
    
    public BooleanProperty formChangedProperty = new SimpleBooleanProperty(false);
    
    public void addRow() {
        Customer c = new Customer(778);
        c.setName("Shinra");
        c.setAddressline1("Midgar");
        c.setEmail("shinra@shinra.com");
        c.setCreditLimit(5000000);
        customerDataSource.getData().add(new CustomerFxBean(c));
    }
    
    public void save(Object bean) {
        try {
            Customer c = ((CustomerFxBean)bean).getWrappedCustomer();
            System.out.println("Saving customer: " + c);
//            System.out.println(c.getAddressline1() + " " + bean.getAddressline1());
//            System.out.println(c.getName() + " " + bean.getSelectionModel().getSelectedItem().getName());
//            System.out.println(c.getCreditLimit() + " " + bean.getSelectionModel().getSelectedItem().getCreditLimit());
            customerJpaController.edit(c);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ConfigViewPrototype.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ConfigViewPrototype.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
