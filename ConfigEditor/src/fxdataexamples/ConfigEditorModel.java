/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import fxdataexamples.persistence.Customer;
import fxdataexamples.persistence.CustomerJpaController;
import org.javafxdata.datasources.protocol.ObjectDataSource;

/**
 *
 * @author Zero
 */
public class ConfigEditorModel {
    
    private ObjectDataSource dataSource;
    private CustomerJpaController jpaController;
    
    public ConfigEditorModel() {
        
    }

//    public void save(Customer c) {
//        jpaController.edit(c);
//    }
}
