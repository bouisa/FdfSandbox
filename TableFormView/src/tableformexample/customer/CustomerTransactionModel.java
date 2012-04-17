/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.customer;

import tableformexample.customer.data.CustomerFxBean;
import tableformexample.customer.data.Customer;
import gov.nasa.gsfc.fdf.datalib.api.BeanDao;
import gov.nasa.gsfc.fdf.datalib.api.BeanTransactionCache;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javafxdata.datasources.protocol.ObjectDataSource;

/**
 *
 * @author Zero
 */
public class CustomerTransactionModel implements BeanTransactionCache<CustomerFxBean> {

    private BeanDao<Customer> dao;
    private ObjectDataSource<CustomerFxBean> cache;
    
    public CustomerTransactionModel(BeanDao<Customer> dao) {
        this.dao = dao;
    }

    @Override
    public ObjectDataSource<CustomerFxBean> getCachedData() {
        return cache;
    }

    @Override
    public void refreshCache(String... cols) {
        List<Customer> resultList = dao.retrieve();

        List<CustomerFxBean> decoratedData = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            CustomerFxBean c = new CustomerFxBean(resultList.get(i));
            decoratedData.add(c);

            System.out.println(c.getName());
        }

        cache = new ObjectDataSource<>(decoratedData, CustomerFxBean.class, cols);
    }

    @Override
    public void add(CustomerFxBean bean) {
        cache.getData().add(bean);
    }

    @Override
    public void remove(CustomerFxBean bean) {
        cache.getData().remove(bean);
    }

    @Override
    public void commit(CustomerFxBean bean) {
        Customer c = bean.getWrappedCustomer();
        System.out.println("Saving customer: " + c);
        
        try {
            dao.save(c);
        } catch (Exception ex) {
            Logger.getLogger(CustomerTransactionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
