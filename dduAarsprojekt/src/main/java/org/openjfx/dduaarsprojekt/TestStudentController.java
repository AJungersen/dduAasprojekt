/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import AssistantClasses.AssistantMyTestForTestStudentController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;
import org.openjfx.dduaarsprojekt.databaseRepository.TestDatabaseMethods;
import org.openjfx.dduaarsprojekt.random.Student;
import org.openjfx.dduaarsprojekt.random.Team;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TestStudentController implements Initializable {

    TestDatabaseMethods tdb = new TestDatabaseMethods();

    @FXML
    TableView<TaskSet> myTest = new TableView();
    @FXML
    ListView<String> myTeams = new ListView();
    @FXML
    ListView<String> onGoingTest = new ListView();
    @FXML
    ListView<String> onGoingTestDone = new ListView();
    @FXML
    ArrayList<TaskSet> selectedTeamTaskSets = new ArrayList();
    @FXML
    Text name;
    @FXML
    TableColumn<TaskSet, String> teamName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        ArrayList<String> taskSetNames = new ArrayList();
        ArrayList<TaskSet> nameListTask = new ArrayList();
        ArrayList<TaskSet> usersIndividualAssignedTasksSets = new ArrayList();
        ArrayList<TaskSet> TaskSetsNotDone = new ArrayList();
        String[] teamNamesString = {};
       
        teamName.setCellValueFactory(new PropertyValueFactory<TaskSet, String>("teamName"));
        myTest.getItems().addAll(usersIndividualAssignedTasksSets);
        
        for (int i = 0; i < nameListTask.size(); i++) {
            taskSetNames.add(nameListTask.get(i).getName());
        }
        
        //upload taskSetNames i den med alle taskSets

        ArrayList<Team> teams = new ArrayList();
        for (int i = 0; i < teams.size(); i++) {
            teamNamesString[i] = teams.get(i).getTeamName();
        }
        //load dine hold
        try {
            myTeams.getItems().addAll(getNames(tdb.getTeachersTeams(App.getLoggedInUser().getUser_ID())));
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //load manglende tasks
        try {
            onGoingTest.getItems().addAll(AssistantMissingTests(tdb.getUsersIndividualAssignedTasksSets(App.getLoggedInUser().getUser_ID())));
        }catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //load gennemførte tasks
        try {
            onGoingTestDone.getItems().addAll(AssistantMissingTestsDone(tdb.getUsersIndividualAssignedTasksSets(App.getLoggedInUser().getUser_ID())));
        }catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //load dit brugernavn 
        try {
            name.setText(App.getLoggedInUser().getUsername());
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void back() throws IOException {
        App.setRoot("mainStudent");
    }

    @FXML
    private void logout() throws IOException {
        App.setRoot("frontPage");
    }

    @FXML
    private void tests() throws IOException {
        App.setRoot("testStudent");
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void teams() throws IOException {
        App.setRoot("teamStudent");
    }

    @FXML
    private void answer() throws IOException {
        AnswerOpenController.setTestName(onGoingTest.getSelectionModel().getSelectedItem());
        App.setRoot("answerTest");
    }

    @FXML
    private void feedback() throws IOException {
        App.setRoot("testFeedbackStudent");
    }

    private ArrayList<String> getNames(ArrayList<Team> teacherTeams) {
        ArrayList<String> myNames = new ArrayList();
        for (int i = 0; i < teacherTeams.size(); i++) {
            myNames.add(teacherTeams.get(i).getTeamName());
        }
        return myNames;
    }

    
    private ArrayList<String> AssistantMissingTests(ArrayList<TaskSet> TaskSetsNotDone) {
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        ArrayList<String> missingTests = new ArrayList();
        for (int i = 0; i < TaskSetsNotDone.size(); i++) {
            if (TaskSetsNotDone.get(i).getHandedIn() == false) {
                missingTests.add(TaskSetsNotDone.get(i).getName());
            }
        }
        return missingTests;
    }
    
    private ArrayList<String> AssistantMissingTestsDone (ArrayList<TaskSet> TaskSetDone) {
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        ArrayList<String> missingTests = new ArrayList();
        for (int i = 0; i < TaskSetDone.size(); i++) {
            if (TaskSetDone.get(i).getHandedIn() == true) {
                missingTests.add(TaskSetDone.get(i).getName());
            }
        }
        return missingTests;
    }

}
