/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import fxdataexamples.persistence.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author abouis
 */
public class CustomerFxBean extends Customer {
    
    private Customer customer;
    private StringProperty name;
    
    public CustomerFxBean(Customer c) {
        this.customer = c;
        name = new SimpleStringProperty(c.getName());
    }
    
    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public final String getName() {
        if(!customer.getName().equals(name.get())) {
            setName(customer.getName());
        }
        
        return name.get();
        
    }

    @Override
    public final void setName(String name) {
        this.name.set(name);
        customer.setName(name);
    }
    
    
}
