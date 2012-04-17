/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fxdataexamples;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * ???
 * @author Zero
 */
public class TableFormExample extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("TableForm Example");
        
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResource("ConfigViewPrototype.fxml").openStream());
        ConfigViewPrototype view = (ConfigViewPrototype) loader.getController();
        
        CustomerTableFormPresenter presenter = new CustomerTableFormPresenter(view);
        view.setController(presenter);

        Scene scene = new Scene(root);
        
        String css = getClass().getResource("CustomerTableFormPresenter.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
