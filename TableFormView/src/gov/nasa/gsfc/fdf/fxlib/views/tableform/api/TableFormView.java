/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nasa.gsfc.fdf.fxlib.views.tableform.api;

import javafx.scene.Node;
import org.javafxdata.datasources.protocol.ObjectDataSource;

/**
 *
 * @author abouis
 */
public interface TableFormView {
    
    public void setPresenter(TableFormPresenter presenter);
    
    public void loadData(ObjectDataSource dataSource);
    
    public void loadBeanForm(Node form);
    
}
