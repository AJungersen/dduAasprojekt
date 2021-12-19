/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openjfx.dduaarsprojekt;

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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    
    @FXML TableView<TaskSet> done = new TableView();
    @FXML ListView<String> myTeams = new ListView();
    @FXML ListView<String> teamsList = new ListView();
    @FXML ArrayList<TaskSet> selectedTeamTaskSets = new ArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            TestDatabaseMethods tdb = new TestDatabaseMethods();
            ArrayList<String> taskSetNames = new ArrayList();
            ArrayList<TaskSet> nameListTask = new ArrayList();
            String[] teamNamesString = {};
        try {
           // nameListTask = Student.getThisStudentTasks(App.getLoggedInUser().getUser_ID());
        } catch (Exception ex) {
            Logger.getLogger(TestStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            for(int i = 0; i < nameListTask.size(); i++){
                taskSetNames.add(nameListTask.get(i).getName());
            }
            //upload taskSetNames i den med alle taskSets
            
            ArrayList<Team> teams = new ArrayList();
        try {
            teams = tdb.getStudentsTeams(App.getLoggedInUser().getUser_ID());
        } catch (Exception ex) {
            Logger.getLogger(TestStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i = 0; i < teams.size(); i++){
            teamNamesString[i] = teams.get(i).getTeamName();
        }
        
        /*teamsList.getItems().addAll(teamNamesString);
        teamsList.getSelectionModel().selectedItemIndex().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2){
                selectedTeamTaskSets = teams.getSelectionModel().getSelectedItem().getTaskSet();
            }
        }*/
         try {
            myTeams.getItems().addAll(getNames(tdb.getTeachersTeams(App.getLoggedInUser().getUser_ID())));
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    
        @FXML
        private void back() throws IOException{
            App.setRoot("mainStudent");
        }
        @FXML
        private void logout() throws IOException{
            App.setRoot("frontPage");
        }
        @FXML
        private void tests() throws IOException{
            App.setRoot("testStudent");
        }
        @FXML
        private void exit() {
            System.exit(0);
        }
        @FXML
        private void teams() throws IOException{
            App.setRoot("teamStudent");
        }
        @FXML
        private void answer () throws IOException{
            App.setRoot("answerTest");
        }
        @FXML
        private void feedback () throws IOException{
            App.setRoot("testFeedbackStudent");
        }
        private ArrayList<String> getNames(ArrayList<Team> teacherTeams) {
        ArrayList<String> myNames = new ArrayList();
        for(int i = 0; i < teacherTeams.size(); i++){
            myNames.add(teacherTeams.get(i).getTeamName());
        }
        return myNames;
    }
    }
