/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import fxdataexamples.persistence.Customer;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author abouis
 */
public class CustomerFxBean extends Customer {
    
    private Customer customer;
    private StringProperty name;
    private StringProperty email;
    private IntegerProperty creditLimit;
    private ObjectProperty<String> random;
    
    public CustomerFxBean(Customer c) {
        this.customer = c;
        name = new SimpleStringProperty(c.getName());
        email = new SimpleStringProperty(c.getEmail());
        creditLimit = new SimpleIntegerProperty(c.getCreditLimit());
        
        random = new SimpleObjectProperty<>(c.getCity());
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

    @Email
    @Override
    public String getEmail() {
        if(!customer.getEmail().equals(email.get())) {
            setEmail(customer.getEmail());
        }
        return email.get();
    }

    @Override
    public void setEmail(String email) {
        this.email.set(email);
        customer.setEmail(email);
    }

    @Override
    public Integer getCreditLimit() {
        if(!customer.getCreditLimit().equals(creditLimit.get())) {
            setCreditLimit(customer.getCreditLimit());
        }
        return creditLimit.get();
    }

    @Override
    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit.set(creditLimit);
        customer.setCreditLimit(creditLimit);
    }

    public String getRandom() {
        if(!customer.getCity().equals(random.get())) {
            setRandom(customer.getCity());
        }
        return random.get();
    }

    public void setRandom(String random) {
        this.random.set(random);
        customer.setCity(random);
    }
    
    

}
