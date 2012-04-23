/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskservice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author Zero
 */
public class Model {
    
    private ObservableList<Task> taskList = FXCollections.observableArrayList();
    
    public Model() {
        
    }

    public ObservableList<Task> getTaskList() {
        return taskList;
    }

    public void addTask(Task task) {
        taskList.add(task);
    }
}
