/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demowindowsystem;

import gov.nasa.gsfc.fdf.appsvc.fxworkspace.api.feature.Feature;
import gov.nasa.gsfc.fdf.appsvc.fxworkspace.api.feature.WindowFeature;
import gov.nasa.gsfc.fdf.appsvc.fxworkspace.api.workspace.WindowZone;
import gov.nasa.gsfc.fdf.appsvc.fxworkspace.api.workspace.WorkspaceController;
import gov.nasa.gsfc.fdf.appsvc.fxworkspace.api.workspace.WorkspaceService;
import gov.nasa.gsfc.fdf.appsvc.fxworkspace.api.workspace.WorkspaceView;
import gov.nasa.gsfc.fdf.appsvc.fxworkspace.impl.DefaultWorkspaceController;
import gov.nasa.gsfc.fdf.appsvc.fxworkspace.impl.DefaultWorkspaceService;
import gov.nasa.gsfc.fdf.appsvc.fxworkspace.impl.DefaultWorkspaceView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This is a bare bones example to demonstrate how to use the WorkspaceService
 * in the AppServices library.  
 * 
 * @author abouis
 */
public class DemoWindowSystem extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // Set up the windowing service
        WorkspaceService workspaceSvc = new DefaultWorkspaceService();
        WorkspaceView workspaceDisplay = new DefaultWorkspaceView();
        WorkspaceController controller = new DefaultWorkspaceController((DefaultWorkspaceService) workspaceSvc, workspaceDisplay);
        workspaceDisplay.setController(controller);
        workspaceSvc.addWorkspaceModelListener(controller);
        
        // Create features to plugin to workspace
        Feature f1 = createDemoFeature();
        workspaceSvc.addFeature(f1);
        
        Feature f2 = createOtherDemoFeature();
        workspaceSvc.addFeature(f2);

        Scene scene = new Scene(workspaceDisplay.getSceneRoot(), 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Demo Window System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private static class SimpleMainView extends StackPane {
        public SimpleMainView() {
            getChildren().add(
                    LabelBuilder.create()
                        .text("Hello this node would be a view that you've designed.")
                        .rotate(45)
                        .scaleX(2)
                        .scaleY(2)
                    .build());
        }
        
        public void refresh() {
            System.out.println("View opening... preform activation actions");
        }
        
        public void close() {
            System.out.println("View closing... preform deactivation actions");
        }
    }
    
    private Feature createDemoFeature() {
        WindowFeature feature = new WindowFeature<SimpleMainView>() {
            
            @Override
            public SimpleMainView createView() {
                return new SimpleMainView();
            }

            @Override
            public String getDisplayName() {
                return "Main View";
            }

            @Override
            public String[] getMenuPath() {
                return new String[] {"Feature", "Demo", "Main"};
            }

            @Override
            public boolean isQuickFeature() {
                return true;
            }

            @Override
            public String getQuickCategory() {
                return "Home";
            }

            @Override
            public boolean isSingleton() {
                return false;
            }

            @Override
            public void activateView(SimpleMainView view) {
                view.refresh();
            }

            @Override
            public void deactivateView(SimpleMainView view) {
                view.close();
            }

        };
        
        return feature;
    }
    
    private Feature createOtherDemoFeature() {
        WindowFeature feature = new WindowFeature() {

            @Override
            public Node createView() {
                try {
                    return FXMLLoader.load(getClass().getResource("FXMLView.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(DemoWindowSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return null;
            }

            @Override
            public String getDisplayName() {
                return "Other View";
            }

            @Override
            public String[] getMenuPath() {
                return new String[]{"Feature", "Demo", "Other"};
            }

            @Override
            public boolean isQuickFeature() {
                return false;
            }

            @Override
            public String getQuickCategory() {
                return null;
            }
            
            @Override
            public WindowZone getDefaultViewArea() {
                return DefaultWorkspaceView.DefWorkspaceZone.NW;
            }

            @Override
            public boolean isSingleton() {
                return true;
            }

        };
        
        return feature;
    }
}
