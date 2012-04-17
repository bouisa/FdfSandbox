/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nasa.gsfc.fdf.datalib.api;

import java.util.List;

/**
 *
 * @author abouis
 */
public interface BeanDao<T> {
    
    public List<T> retrieve();
    
    public void add(T bean) throws Exception;
    
    public void remove(T bean) throws Exception;
    
    public void save(T bean) throws Exception;
}
