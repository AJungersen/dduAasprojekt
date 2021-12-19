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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    public static int currentTeamID = 0;
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
    ListView<String> teamsStudents;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        ArrayList<TaskSet> myTasks = new ArrayList<>();
        try {
            myTasks = tdb.getUsersIndividualAssignedTasksSets(App.getLoggedInUser().getUser_ID());
        } catch (Exception ex) {
            Logger.getLogger(TeamInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String> myTaskNames = new ArrayList<>();
        for(int i = 0; i < myTasks.size(); i++){
            myTaskNames.add(myTasks.get(i).getName());
        }
        yourTasks.getItems().addAll(myTaskNames);
        cellFactory();
        try {
            teamsTasks.setItems(getAssistantTeamInformationControllerArray(tdb.getTeamsAssignedTaskSets(currentTeamID ,App.getLoggedInUser().getUser_ID())));
        } catch (Exception ex) {
            Logger.getLogger(TeamInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            allStudents.getItems().addAll(getNames(tdb.getTeamsUnassignedStudents(currentTeamID)));
        } catch (Exception ex) {
            Logger.getLogger(TeamInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            teamsStudents.getItems().addAll(getNames(tdb.getTeamsAssignedStudents(currentTeamID)));
        } catch (Exception ex) {
            Logger.getLogger(TeamInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    }

    private ObservableList<AssistantTeamInformationController> getAssistantTeamInformationControllerArray(ArrayList<TaskSet> allTeachersTaskSets) throws Exception {
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        ArrayList<AssistantTeamInformationController> myList = new ArrayList();
        
        for(int i = 0; i < allTeachersTaskSets.size(); i++){
            int p = 0;
            ArrayList<TaskSet> checking = tdb.getAllTaskSetsAnswers(allTeachersTaskSets.get(i).getAssignment_ID());
            for(int m = 0; m < checking.size(); m++){
                if(checking.get(m).getHandedIn() == true){
                    p+=1;
                }
            }
            myList.add(new AssistantTeamInformationController(checking.get(i).getName(),100*p/checking.size()));
        }
        return FXCollections.observableArrayList(myList);
    }

    private ArrayList<String> getNames(ArrayList<Student> schoolsStudents) {
        ArrayList<String> myNames = new ArrayList();
        for(int i = 0; i < schoolsStudents.size(); i++){
            myNames.add(schoolsStudents.get(i).getName());
        }
        return myNames;
    }
    @FXML
    private void addTaskSet() throws Exception{
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        TaskSet myTaskSet = new TaskSet();
        myTaskSet = tdb.getUsersIndividualAssignedTasksSets(App.getLoggedInUser().getUser_ID()).get(yourTasks.getSelectionModel().getSelectedIndex());
        tdb.assignTaskSetToTeam(currentTeamID, myTaskSet);
    }
}
