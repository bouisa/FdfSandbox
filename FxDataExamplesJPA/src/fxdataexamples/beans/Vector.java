/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples.beans;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Zero
 */
public class Vector {
    
    private StringProperty id = new SimpleStringProperty();
    
    private Double velocity;

    public final String getId() {
        return id.get();
    }
    
    public final void setId(String value) {
        id.set(value);
    }
    
    public StringProperty idProperty() {
        return id;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

}
