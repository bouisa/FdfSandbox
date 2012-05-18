/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskservice;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author Zero
 */
public class TaskService extends Application {
    
    private BorderPane workspace;
    private HBox statusBar;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Progress Service");
        primaryStage.setScene(initScene());
        primaryStage.show();
        
        Model model = new Model();
        View view = new View(model);
        
        addStatusComponent(view.getParentNode());
        
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
    }
    
    public void addStatusComponent(Node node) {
        statusBar.getChildren().add(node);
    }
    
    private Scene initScene() {
        workspace = 
                BorderPaneBuilder.create()
                    .center(
                        StackPaneBuilder.create()
                            .children(new Button())
                        .build()
                    )
                    .bottom(
                        statusBar = HBoxBuilder.create()
                        .build()
                    )
                .build();
        
        Scene scene = new Scene(workspace, 300, 250);
        scene.getStylesheets().add("/taskservice/testcss2.css");
        
        return scene;
    }
}
