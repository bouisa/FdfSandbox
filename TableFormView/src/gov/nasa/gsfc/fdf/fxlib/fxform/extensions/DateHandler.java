/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nasa.gsfc.fdf.fxlib.fxform.extensions;

import com.dooapp.fxform.reflection.Util;
import com.dooapp.fxform.view.handler.FieldHandler;
import java.lang.reflect.Field;
import java.util.Date;
import javafx.beans.property.ObjectProperty;

/**
 *
 * @author abouis
 */
public class DateHandler implements FieldHandler {

    @Override
    public boolean handle(Field field) {
        if (!field.getType().isAssignableFrom(ObjectProperty.class)) {
            return false;
        }
        try {
            return Util.getObjectPropertyGeneric(field).isAssignableFrom(Date.class);
        } catch (Exception e) {
            // Null pointer exception will be thrown if this handler attempts
            // to evaluate a non ObjectProperty field
        }
        return false;
    }
}
