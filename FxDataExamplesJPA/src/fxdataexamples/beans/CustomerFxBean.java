/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples.beans;

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
    
    private IntegerProperty customerId;
    private StringProperty addressline1;
    private StringProperty name;
    private StringProperty email;
    private IntegerProperty creditLimit;
    private ObjectProperty<Date> date; // date has no underlying entity field, using it for date picker testing
    
    public CustomerFxBean(Customer c) {
        this.customer = c;
        
        customerId = new SimpleIntegerProperty(c.getCustomerId());
        // For some reason it's important that the following setter corresponding to
        // the entity's primary key is called, otherwise selection model change events
        // won't fire (e.g. on TableView)
        super.setCustomerId(c.getCustomerId());
        
        name = new SimpleStringProperty(c.getName());
        super.setName(c.getName());
        
        email = new SimpleStringProperty(c.getEmail());
        super.setEmail(c.getEmail());

        creditLimit = new SimpleIntegerProperty(c.getCreditLimit());

        addressline1 = new SimpleStringProperty(c.getAddressline1());
        
        date = new SimpleObjectProperty<>(new Date());
    }

    public Customer getWrappedCustomer() {
        customer.setCustomerId(customerId.get());
        customer.setName(name.get());
        customer.setAddressline1(addressline1.get());
        
        return customer;
    }
    
    public IntegerProperty customerIdProperty() {
        return customerId;
    }
    
    @Override
    public Integer getCustomerId() {
//        if (!customer.getCustomerId().equals(customerId.get())) {
//            setCustomerId(customer.getCustomerId());
//        }
        return customerId.get();
    }

    @Override
    public void setCustomerId(Integer customerId) {
        this.customerId.set(customerId);
        customer.setCustomerId(customerId);
    }
    
    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public final String getName() {
//        if(!customer.getName().equals(name.get())) {
//            setName(customer.getName());
//        }
        
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
    
    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public Date getDate() {
        return date.get();
    }

    public void setDate(Date random) {
        this.date.set(random);
    }

    public StringProperty addressline1Property() {
        return addressline1;
    }
    
    @Override
    public String getAddressline1() {
//        if (!customer.getAddressline1().equals(addressline1.get())) {
//            setAddressline1(customer.getAddressline1());
//        }
        return addressline1.get();
    }

    @Override
    public void setAddressline1(String addressline1) {
        this.addressline1.set(addressline1);
        customer.setAddressline1(addressline1);
    }
}
