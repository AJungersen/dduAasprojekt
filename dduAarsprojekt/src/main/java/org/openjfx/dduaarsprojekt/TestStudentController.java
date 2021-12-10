/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openjfx.dduaarsprojekt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TestStudentController implements Initializable {
   
    ListView<String> OngoingTests;
    TableView<TaskSet> done = new TableView();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*//hent alle prøve som eleven er igang med
        ObservableList currentTests = getAllOngoingTests();
        
        ListView<String> OngoingTests = new ListView<>(currentTests);
        
        TableColumn<TaskSet,String> testName = new TableColumn<>("Prøve navn");
        testName.setCellFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<TaskSet,String> percent = new TableColumn<>("Rigtighedsprocent");
        testName.setCellFactory(new PropertyValueFactory<>("name"));
        
        done.getColumns().add(testName);
        done.getColumns().add(percent);*/
    }   

}
