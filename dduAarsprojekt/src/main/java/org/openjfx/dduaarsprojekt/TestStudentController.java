/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;
import org.openjfx.dduaarsprojekt.random.Student;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TestStudentController implements Initializable {

    @FXML
    ListView<String> teams;
    @FXML
    TableView<TaskSet> done = new TableView();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] names = {};
        ArrayList<TaskSet> nameListTask = new ArrayList();
        try {
            nameListTask = Student.getThisStudentTasks(App.getLoggedInUser().getUser_ID());
        } catch (Exception ex) {
            Logger.getLogger(TestStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < nameListTask.size(); i++) {

        }
        /*ObservableList currentTests = getAllOngoingTests();
            
            ListView<String> OngoingTests = new ListView<>(currentTests);
            
            TableColumn<TaskSet,String> testName = new TableColumn<>("Prøve navn");
            testName.setCellFactory(new PropertyValueFactory<>("name"));
            
            TableColumn<TaskSet,String> percent = new TableColumn<>("Rigtighedsprocent");
            testName.setCellFactory(new PropertyValueFactory<>("name"));
            
            done.getColumns().add(testName);
            done.getColumns().add(percent);
            Logger.getLogger(TestStudentController.class.getName()).log(Level.SEVERE, null, ex);*/
    }
}
