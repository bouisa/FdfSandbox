/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import fxdataexamples.persistence.Customer;
import fxdataexamples.persistence.CustomerJpaController;
import fxdataexamples.persistence.exceptions.NonexistentEntityException;
import fxdataexamples.persistence.exceptions.PreexistingEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abouis
 */
public class CustomerJpaDao implements BeanDao<Customer> {
    
    private CustomerJpaController jpaController;
    
    public CustomerJpaDao(CustomerJpaController controller) {
        this.jpaController = controller;
    }

    @Override
    public List<Customer> retrieve() {
        List<Customer> resultList = jpaController.findCustomerEntities();
        return resultList;
    }

    @Override
    public void add(Customer bean) {
        try {
            jpaController.create(bean);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(CustomerJpaDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CustomerJpaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void remove(Customer bean) {
        try {
            jpaController.destroy(bean.getCustomerId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CustomerJpaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void save(Customer bean) {
        try {
            jpaController.edit(bean);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CustomerJpaDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CustomerJpaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
