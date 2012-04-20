/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.customer;

import com.dooapp.fxform.FXForm;
import com.dooapp.fxform.view.FXFormSkinFactory;
import gov.nasa.gsfc.fdf.datalib.api.BeanTransactionCache;
import gov.nasa.gsfc.fdf.fxlib.views.tableform.api.TableFormView;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.cell.PropertyValueFactory;
import org.javafxdata.datasources.protocol.ObjectDataSource;
import tableformexample.customer.data.Customer;
import tableformexample.customer.data.CustomerFxBean;

/**
 *
 * @author Zero
 */
public class CustomerTableFormPresenter extends TransactionalFxFormPresenter<CustomerFxBean> {

    /**
     * Constructor
     * 
     * @param view 
     */
    public CustomerTableFormPresenter(TableFormView view, BeanTransactionCache<CustomerFxBean> model) {
        super(view, model);
    }

    @Override
    public void refresh() {
        getModel().refreshCache("name", "creditLimit");
        ObjectDataSource<CustomerFxBean> dataSource = getModel().getCachedData();
        
        dataSource.getNamedColumn("name").setText("Name");
        dataSource.getNamedColumn("name").setCellValueFactory(new PropertyValueFactory("name"));
        dataSource.getNamedColumn("creditLimit").setText("Credit Limit");
        dataSource.getNamedColumn("creditLimit").setCellValueFactory(new PropertyValueFactory("creditLimit"));
        
        getView().loadData(dataSource);
    }

    @Override
    protected FXForm createBeanForm(CustomerFxBean bean) {
        FXForm fxForm = new FXForm();

        fxForm.setSkin(FXFormSkinFactory.INLINE_FACTORY.createSkin(fxForm));
        fxForm.setSource(bean);
        fxForm.setTitle(" Edit Customer: " + bean.getName());

        return fxForm;
    }
    
    @Override
    protected BooleanBinding getBeanChangeBinding(CustomerFxBean bean) {
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
    protected CustomerFxBean createNewBean() {
        Customer c = new Customer(778);
        c.setName("Shinra");
        c.setAddressline1("Midgar Sector 0");
        c.setEmail("shinra@mako.com");
        c.setCreditLimit(5000000);

        return new CustomerFxBean(c);
    }

}
