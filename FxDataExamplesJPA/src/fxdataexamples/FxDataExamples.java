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
public class FxDataExamples extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("FxDataExamples");
        
        Parent root = FXMLLoader.load(getClass().getResource("ConfigViewPrototype.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
