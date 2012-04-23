/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskservice;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Zero
 */
public class TaskService extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        Model model = new Model();
        View view = new View(model);
        
        primaryStage.setTitle("Progress Service");
        primaryStage.setScene(new Scene(view.getParentNode(), 300, 250));
        primaryStage.show();
        
        Task<String> task = new Task<String>() {

            @Override
            protected String call() throws Exception {
                updateTitle("Dummy task");
                updateProgress(0, 100);
                updateMessage("Initializing...");
                Thread.sleep(3000);
                updateProgress(25, 100);
                updateMessage("Processing...");
                Thread.sleep(3000);
                updateProgress(50, 100);
                updateMessage("Testing...");
                Thread.sleep(3000);
                updateProgress(75, 100);
                updateMessage("Finalizing...");
                Thread.sleep(3000);
                updateProgress(100, 100);
                updateMessage("Done.");
                
                return "Task finished";
            }
        };

        model.addTask(task);
        new Thread(task).start();
        
//        primaryStage.setTitle("Hello World!");
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
//        
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        primaryStage.setScene(new Scene(root, 300, 250));
//        primaryStage.show();
    }
}
