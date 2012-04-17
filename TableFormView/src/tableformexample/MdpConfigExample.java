/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tableformexample;

import com.dooapp.fxform.view.factory.DelegateFactory;
import gov.nasa.gsfc.fdf.datalib.api.BeanTransactionCache;
import gov.nasa.gsfc.fdf.fxlib.fxform.extensions.DateHandler;
import gov.nasa.gsfc.fdf.fxlib.fxform.extensions.DateNodeFactory;
import gov.nasa.gsfc.fdf.fxlib.views.tableform.impl.DefaultTableFormView;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tableformexample.customer.CustomerTableFormPresenter;
import tableformexample.customer.CustomerTransactionModel;
import tableformexample.customer.data.CustomerFxBean;
import tableformexample.customer.data.CustomerJpaController;
import tableformexample.mdpconfig.MdpConfigTableFormPresenter;
import tableformexample.mdpconfig.MdpConfigTransactionModel;
import tableformexample.mdpconfig.data.MdpConfigFxBean;
import tableformexample.mdpconfig.data.MdpConfigJpaController;

/**
 * ???
 * @author Zero
 */
public class MdpConfigExample extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("TableForm Example");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OracleDevPU");
        MdpConfigJpaController jpaController = new MdpConfigJpaController(emf);
        BeanTransactionCache<MdpConfigFxBean> model = new MdpConfigTransactionModel(jpaController);
        
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResource(
                "/gov/nasa/gsfc/fdf/fxlib/views/tableform/impl/DefaultTableFormView.fxml").openStream());
        DefaultTableFormView view = (DefaultTableFormView) loader.getController();
        
        MdpConfigTableFormPresenter presenter = new MdpConfigTableFormPresenter(view, model);
        view.setPresenter(presenter);

        Scene scene = new Scene(root);
        
        // Initialize FXForm css
        String css = getClass().getResource(
                "mdpconfig/MdpConfigTableFormPresenter.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        // Initialize FXForm with custom factory to handle ObjectProperty<Date>
        // The created node uses JFXtra's date picker
        //
        // FXForm's DelegateFactory allows registering a FieldHandler->NodeFactory
        // mapping to generate custom form editors.
        //
        DelegateFactory.addGlobalFactory(new DateHandler(), new DateNodeFactory());
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
