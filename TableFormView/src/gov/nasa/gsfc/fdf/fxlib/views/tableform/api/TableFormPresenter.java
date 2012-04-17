/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nasa.gsfc.fdf.fxlib.views.tableform.api;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;

/**
 *
 * @author abouis
 */
public interface TableFormPresenter {
    
    public void refresh();
    
    public void changeActiveBean(Node oldForm, Object oldBean, Object newBean);
    
    public BooleanProperty formChangedProperty();
    
    public void addRow();
    
    public void save(Object bean);
    
}
