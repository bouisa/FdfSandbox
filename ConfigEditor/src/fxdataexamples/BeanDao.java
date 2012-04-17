/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import java.util.List;

/**
 *
 * @author abouis
 */
public interface BeanDao<T> {
    
    public List<T> retrieve();
    
    public void add(T bean);
    
    public void remove(T bean);
    
    public void save(T bean);
}
