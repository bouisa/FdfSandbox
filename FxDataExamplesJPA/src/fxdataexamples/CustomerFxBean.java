/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import fxdataexamples.persistence.Customer;
import java.util.Date;
import javafx.beans.property.*;
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
    private ObjectProperty<Date> random;
    
    public CustomerFxBean(Customer c) {
        this.customer = c;
        name = new SimpleStringProperty(c.getName());
        email = new SimpleStringProperty(c.getEmail());
        creditLimit = new SimpleIntegerProperty(c.getCreditLimit());
        
        random = new SimpleObjectProperty<>(new Date());
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
    
    public StringProperty emailProperty() {
        return email;
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
    
    public IntegerProperty creditLimitProperty() {
        return creditLimit;
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
    
    public ObjectProperty<Date> randomProperty() {
        return random;
    }

    public Date getRandom() {
//        if(!customer.getCity().equals(random.get())) {
//            setRandom(customer.getCity());
//        }
        return random.get();
    }

    public void setRandom(Date random) {
        this.random.set(random);
//        customer.setCity(random);
    }
    
    

}
