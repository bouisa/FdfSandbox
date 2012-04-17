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
import java.lang.reflect.Field;
import java.util.Date;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import jfxtras.labs.scene.control.CalendarTextField;
import org.javafxdata.datasources.protocol.ObjectDataSource;

/**
 *
 * @author Zero
 */
public class CustomerTableFormPresenter implements TableFormPresenter {
    
    private TableFormView view;
    private BeanTransactionCache<CustomerFxBean> model;
//    private EntityManagerFactory emf;
//    private CustomerJpaController customerJpaController;
//    private ObjectDataSource<CustomerFxBean> customerDataSource;
    public BooleanProperty formChanged = new SimpleBooleanProperty(false);
    
    private final static NodeFactory DATE_FACTORY = new NodeFactory() {

        @Override
        public DisposableNode createNode(ElementController elementController) throws NodeCreationException {
            // TODO hook this up to underlying bean
            elementController.getValue();
            CalendarTextField cal = new CalendarTextField();

            return new DisposableNodeWrapper(cal,
                    new Callback<Node, Void>() {

                        @Override
                        public Void call(Node node) {
                            // TODO unhook from underlying bean
                            System.out.println("disposable callback called");
                            return null;
                        }
                    });
        }
    };
    
    /**
     * Constructor
     * 
     * @param view 
     */
    public CustomerTableFormPresenter(TableFormView view, BeanTransactionCache<CustomerFxBean> model) {
        this.view = view;
        this.model = model;
        
//        this.emf = Persistence.createEntityManagerFactory("FxDataExamplesPU");
//        this.customerJpaController = new CustomerJpaController(emf);
        
        // Initialize FXForm with custom factory to handle ObjectProperty<Date>
        // The created node uses JFXtra's date picker
        DelegateFactory.addGlobalFactory(new DateHandler(), DATE_FACTORY);
        
//        ConfigEditorController.class.getResource("/fxdataexamples/ConfigEditorController.css").toExternalForm());
    }

    @Override
    public BooleanProperty formChangedProperty() {
        return formChanged;
    }
    
    public Boolean getFormChanged() {
        return formChanged.get();
    }
    
    public void setFormChanged(Boolean changed) {
        formChanged.set(changed);
    }

    @Override
    public void refresh() {
        model.refreshCache("name", "creditLimit");
        ObjectDataSource<CustomerFxBean> dataSource = model.getCachedData();
        
//        List<CustomerFxBean> adaptedData = model.getCachedData();

//        customerDataSource = new ObjectDataSource<>(adaptedData, CustomerFxBean.class, "name", "creditLimit");

//        dataSource.columns("name", "creditLimit");
        dataSource.getNamedColumn("name").setText("Name");
        dataSource.getNamedColumn("name").setCellValueFactory(new PropertyValueFactory("name"));
        dataSource.getNamedColumn("creditLimit").setText("Credit Limit");
        dataSource.getNamedColumn("creditLimit").setCellValueFactory(new PropertyValueFactory("creditLimit"));
        
        view.loadData(dataSource);
    }
    
    @Override
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
            
            formChanged.unbind();
            formChanged.bind(getBeanChangeBinding(nb));
            
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

    @Override
    public void addRow() {
        Customer c = new Customer(778);
        c.setName("Shinra");
        c.setAddressline1("Midgar");
        c.setEmail("shinra@shinra.com");
        c.setCreditLimit(5000000);
        model.add(new CustomerFxBean(c));
//        customerDataSource.getData().add(new CustomerFxBean(c));
    }
    
    @Override
    public void save(Object bean) {
        model.commit((CustomerFxBean) bean);
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
    
}
