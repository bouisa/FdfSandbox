/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.customer;

import com.dooapp.fxform.FXForm;
import com.dooapp.fxform.view.FXFormSkinFactory;
import gov.nasa.gsfc.fdf.datalib.api.BeanTransactionCache;
import gov.nasa.gsfc.fdf.fxlib.views.tableform.api.TableFormPresenter;
import gov.nasa.gsfc.fdf.fxlib.views.tableform.api.TableFormView;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javafxdata.datasources.protocol.ObjectDataSource;
import tableformexample.customer.data.Customer;
import tableformexample.customer.data.CustomerFxBean;

/**
 *
 * @author Zero
 */
public class CustomerTableFormPresenter implements TableFormPresenter {
    
    private TableFormView view;
    private BeanTransactionCache<CustomerFxBean> model;
    private BooleanProperty formChanged = new SimpleBooleanProperty(false);

    /**
     * Constructor
     * 
     * @param view 
     */
    public CustomerTableFormPresenter(TableFormView view, BeanTransactionCache<CustomerFxBean> model) {
        this.view = view;
        this.model = model;

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
    }
    
    @Override
    public void save(Object bean) {
        model.commit((CustomerFxBean) bean);
    }
    
}
