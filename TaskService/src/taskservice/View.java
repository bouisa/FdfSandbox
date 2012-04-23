/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskservice;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.PopupWindow;
import javafx.util.Callback;

/**
 *
 * @author Zero
 */
public class View {
    
    private StackPane basePane;
    private ListView<Task> taskListView = new ListView<>();
    private Popup pview;
    
    public View(Model model) {
        initLayout();
        taskListView.setItems(model.getTaskList());
        taskListView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {

            @Override
            public ListCell<Task> call(ListView<Task> list) {
                System.out.println("creating cell");
                return new ProgressCell();
            }
        });
        taskListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked");
//                pview = 
//                PopupBuilder.create()
//                    .content(new Button("Popup"))
//                .build();
//                
//                pview.show(basePane.getScene().getWindow());
            }
            
        });
//        model.getTaskList().addListener(new ListChangeListener<Task>() {
//
//            @Override
//            public void onChanged(Change<? extends Task> change) {
//                while(change.next()) {
//                    if(change.wasAdded()) {
//                        Task task = change.getList().get(change.getFrom());
//                    }
//                }
//            }
//            
//        });
    }
    
    private void initLayout() {
        basePane = 
                StackPaneBuilder.create()
                    .children(
                        taskListView
                    )
                .build();
    }
    
    public Parent getParentNode() {
        return basePane;
    }
    
    private static class ProgressCell extends ListCell<Task> {
        
        private HBox progressBox;
        private ProgressBar progressBar;
        private ProgressIndicator progressInd;
        private Button cancelButton;
        private Label messageLabel;
        private Popup popup = 
                PopupBuilder.create()
                    .content(new Button("Popup"))
                .build();
        
        public ProgressCell() {
            setContentDisplay(ContentDisplay.RIGHT);
        }

        @Override
        protected void updateItem(Task item, boolean empty) {
            System.out.println("updating item");
            super.updateItem(item, empty);

            if (!empty) {
                if(progressBox == null) {
                    progressBox = 
                            HBoxBuilder.create()
                                .alignment(Pos.CENTER)
                                .spacing(5)
                                .children(
                                    messageLabel = 
                                        LabelBuilder.create()
                                            .alignment(Pos.CENTER_RIGHT)
                                            .minWidth(100)
                                        .build(),
                                    progressInd =
                                        ProgressIndicatorBuilder.create()
                                            
                                        .build(),
                                    progressBar = 
                                        ProgressBarBuilder.create()
                                            .mouseTransparent(true)
                                        .build(),
                                    cancelButton = ButtonBuilder.create()
                                        .text("Cancel")
                                        .onAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            popup.show(progressBox.getScene().getWindow());
                        }
                                            
                                        })
                            
//                                        .graphic(RectangleBuilder.create().width(8).height(8).fill(Color.RED).build())
                                    .build()
                                )
                            .build();
                }
                messageLabel.textProperty().bind(item.messageProperty());
                
                progressBar.progressProperty().bind(item.progressProperty());
                textProperty().bind(item.titleProperty());
                setGraphic(progressBox);
            }
        }
    }
}
