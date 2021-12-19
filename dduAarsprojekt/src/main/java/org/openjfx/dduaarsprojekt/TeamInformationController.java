/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import AssistantClasses.AssistantTeamInformationController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;
import org.openjfx.dduaarsprojekt.databaseRepository.TestDatabaseMethods;
import org.openjfx.dduaarsprojekt.random.Student;

/**
 *
 * @author clara
 */
public class TeamInformationController implements Initializable {
    public static int currentTeam = 0;
    @FXML
    ListView<String> yourTasks;
    @FXML
    TableView<AssistantTeamInformationController> teamsTasks;
    @FXML
    TableColumn<AssistantTeamInformationController, String> testName;
    @FXML
    TableColumn<AssistantTeamInformationController, Integer> participation;
    @FXML
    ListView<String> allStudents;
    @FXML
    TableView<Student> teamsStudents;
    @FXML
    TableColumn<Student, String> studentName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        for(int i = 0; i < tdb.getAllTeachersTaskSets(App.getLoggedInUser().getUser_ID()).size(); i++){
            yourTasks.getItems().add(tdb.getAllTeachersTaskSets(App.getLoggedInUser().getUser_ID()).get(i).getName());
        }
        cellFactory();
        teamsTasks.getItems().addAll(getAssistantTeamInformationControllerArray(tdb.getAllTeachersTaskSets(App.getLoggedInUser().getUser_ID())));
    }    
      @FXML
    private void back() throws IOException{
        App.setRoot("main");
    }
    @FXML
    private void logout() throws IOException{
        App.setRoot("frontPage");
    }
    @FXML
    private void tests() throws IOException{
        App.setRoot("testStaff");
    }
    @FXML
    private void teams() throws IOException{
        App.setRoot("teamStaff");
    }
    @FXML
    private void exit() {
        System.exit(0);
    }
    @FXML
    private void create() throws IOException {
        App.setRoot("createTest");
    }
    @FXML
    private void info () throws IOException{
        App.setRoot("teamInformation");
    }
    private void cellFactory(){
        testName.setCellValueFactory(new PropertyValueFactory<AssistantTeamInformationController, String>("testName"));
        participation.setCellValueFactory(new PropertyValueFactory<AssistantTeamInformationController, Integer>("participation"));
        studentName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
    }

    private ArrayList<AssistantTeamInformationController> getAssistantTeamInformationControllerArray(ArrayList<TaskSet> allTeachersTaskSets) {
        ArrayList<AssistantTeamInformationController> myList = new ArrayList();
        
        return myList;
    }
}
