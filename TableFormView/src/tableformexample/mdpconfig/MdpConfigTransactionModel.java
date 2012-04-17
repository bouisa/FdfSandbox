/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.mdpconfig;

import tableformexample.customer.data.CustomerFxBean;
import tableformexample.customer.data.Customer;
import gov.nasa.gsfc.fdf.datalib.api.BeanDao;
import gov.nasa.gsfc.fdf.datalib.api.BeanTransactionCache;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javafxdata.datasources.protocol.ObjectDataSource;
import tableformexample.mdpconfig.data.MdpConfig;
import tableformexample.mdpconfig.data.MdpConfigFxBean;

/**
 *
 * @author Zero
 */
public class MdpConfigTransactionModel implements BeanTransactionCache<MdpConfigFxBean> {

    private BeanDao<MdpConfig> dao;
    private ObjectDataSource<MdpConfigFxBean> cache;
    
    public MdpConfigTransactionModel(BeanDao<MdpConfig> dao) {
        this.dao = dao;
    }

    @Override
    public ObjectDataSource<MdpConfigFxBean> getCachedData() {
        return cache;
    }

    @Override
    public void refreshCache(String... cols) {
        List<MdpConfig> resultList = dao.retrieve();

        List<MdpConfigFxBean> decoratedData = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            MdpConfigFxBean c = new MdpConfigFxBean(resultList.get(i));
            decoratedData.add(c);

            System.out.println(c.getConfigName());
        }

        cache = new ObjectDataSource<>(decoratedData, MdpConfigFxBean.class, cols);
    }

    @Override
    public void add(MdpConfigFxBean bean) {
        cache.getData().add(bean);
    }

    @Override
    public void remove(MdpConfigFxBean bean) {
        cache.getData().remove(bean);
    }

    @Override
    public void commit(MdpConfigFxBean bean) {
        MdpConfig c = bean.getWrappedMdpConfig();
        System.out.println("Saving customer: " + c);
        
        try {
            dao.save(c);
        } catch (Exception ex) {
            Logger.getLogger(MdpConfigTransactionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
