/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample.customer;

import gov.nasa.gsfc.fdf.datalib.api.BeanDao;
import gov.nasa.gsfc.fdf.datalib.api.BeanTransactionCache;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javafxdata.datasources.protocol.ObjectDataSource;
import tableformexample.FxEntityWrapper;

/**
 *
 * @author Zero
 */
public abstract class FxEntityTransactionCache<T extends FxEntityWrapper<S>, S> implements BeanTransactionCache<T> {

    private BeanDao<S> dao;
    private ObjectDataSource<T> cache;
    
    public FxEntityTransactionCache(BeanDao<S> dao) {
        this.dao = dao;
    }
    
    @Override
    public ObjectDataSource<T> getCachedData() {
        return cache;
    }
    
    @Override
    public void refreshCache(String... cols) {
        List<S> resultList = dao.retrieve();

        List<T> decoratedData = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            T fxWrapper = createEntityWrapper(resultList.get(i));
            decoratedData.add(fxWrapper);
        }

        T obj = decoratedData.size() > 0 ? decoratedData.get(0) : null;

        if (obj != null) {
            cache = new ObjectDataSource<>(decoratedData, obj.getClass(), cols);
        } else {
            cache = new ObjectDataSource<>();
        }
    }
    
    protected abstract T createEntityWrapper(S entity);

    @Override
    public void add(T bean) {
        cache.getData().add(bean);
    }

    @Override
    public void remove(T bean) {
        cache.getData().remove(bean);
    }

    @Override
    public void commit(T bean) {
        S entity = bean.getWrappedEntity();

        try {
            dao.save(entity);
        } catch (Exception ex) {
            Logger.getLogger(FxEntityTransactionCache.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    
}
