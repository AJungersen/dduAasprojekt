/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import org.openjfx.dduaarsprojekt.databaseRepository.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.dduaarsprojekt.App;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;
import static org.openjfx.dduaarsprojekt.TestMakerController.current;
import org.openjfx.dduaarsprojekt.random.*;

/**
 *
 * @author danie
 */
public class TestStaffController implements Initializable {

    @FXML
    TableView doneTest;
    @FXML
    TableView pendingTests;
    @FXML
    TableColumn doneName;
    @FXML
    TableColumn doneCorrect;
    @FXML
    TableColumn doneParticipation;
    @FXML
    TableColumn pendingName;
    @FXML
    TableColumn pendingCorrect;
    @FXML
    TableColumn pendingParticipation;
    @FXML
    TextArea description;
    @FXML
    TextField testName;
    @FXML
    private Text doneTests;
    @FXML
    private ListView teamsView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            TestDatabaseMethods tdm = new TestDatabaseMethods();

            //get teams
            ArrayList<Team> teams = tdm.getTeachersTeams(App.getLoggedInUser().getUserType_ID());

            teamsView.getItems().clear();

            try {
                for (int i = 0; i < teams.size(); i++) {
                    teamsView.getItems().add(teams.get(i).getTeamName());
                }
            } catch (NullPointerException e) {
            }

            //get all task set for teacher
            ArrayList<TaskSet> teachersTaskSets = tdm.getUsersIndividualAssignedTasksSets(App.getLoggedInUser().getUser_ID());
            
            
            try {
                for (int i = 0; i < teachersTaskSets.size(); i++) {
                   
                }
            } catch (NullPointerException e) {
            }
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(TestStaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mineHold() throws IOException {
        App.setRoot("teamStaff");
    }

    @FXML
    private void minePrøver() throws IOException {
        //App.setRoot("testStaff");
    }

    @FXML
    private void forside() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void createNewTaskSet() throws IOException {
        // begin creation of task set
        TaskSet createdTaskSet = new TaskSet();

        createdTaskSet.setName(testName.getText());
        createdTaskSet.setDescription(description.getText());

        App.setCurrentTaskSetWorkingOn(createdTaskSet);

        //mangler at sætte hold - måske skal man bare gøre det når man opretter eller 
        //også så skal man gøre det efter man har oprettet prøven
        App.setRoot("testMaker");
    }

    @FXML
    private void logout() throws IOException {
        App.setRoot("frontPage");
    }

    private void info() throws IOException {
        App.setRoot("teamInformation");
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void feedback() throws IOException {
        App.setRoot("testFeedbackStaff");
    }
}
