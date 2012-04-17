/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nasa.gsfc.fdf.datalib.api;

import org.javafxdata.datasources.protocol.ObjectDataSource;

/**
 *
 * @author abouis
 */
public interface BeanTransactionCache<T> {
    
    public ObjectDataSource<T> getCachedData();
    
    public void refreshCache(String... cols);
    
    public void add(T bean);
    
    public void remove(T bean);
    
    public void commit(T bean);
}
