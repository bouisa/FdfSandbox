/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nasa.gsfc.fdf.fxlib.fxform.extensions;

import com.dooapp.fxform.model.ElementController;
import com.dooapp.fxform.view.NodeCreationException;
import com.dooapp.fxform.view.factory.DisposableNode;
import com.dooapp.fxform.view.factory.DisposableNodeWrapper;
import com.dooapp.fxform.view.factory.NodeFactory;
import javafx.scene.Node;
import javafx.util.Callback;
import jfxtras.labs.scene.control.CalendarTextField;

/**
 *
 * @author abouis
 */
public class DateNodeFactory implements NodeFactory {

    @Override
    public DisposableNode createNode(ElementController elementController) throws NodeCreationException {
        // TODO hook this up to underlying bean
        elementController.getValue();
        CalendarTextField cal = new CalendarTextField();

        return new DisposableNodeWrapper(cal,
                new Callback<Node, Void>() {

                    @Override
                    public Void call(Node node) {
                        // TODO unhook from underlying bean
                        System.out.println("disposable callback called");
                        return null;
                    }
                });
    }
    
}
