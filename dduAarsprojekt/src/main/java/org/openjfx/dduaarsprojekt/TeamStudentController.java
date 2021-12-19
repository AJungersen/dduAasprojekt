/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openjfx.dduaarsprojekt;

import AssistantClasses.AssistantMyTeamsForTeamStudentController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.dduaarsprojekt.databaseRepository.TestDatabaseMethods;
import org.openjfx.dduaarsprojekt.random.Team;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TeamStudentController implements Initializable {
   
    @FXML
    TableView<AssistantMyTeamsForTeamStudentController> myTeams;
    @FXML
    TableColumn<AssistantMyTeamsForTeamStudentController, String> teamName;
    @FXML
    TableColumn<AssistantMyTeamsForTeamStudentController, String> numberOfStudents;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        teamName.setCellValueFactory(new PropertyValueFactory<AssistantMyTeamsForTeamStudentController, String>("teamName"));
        numberOfStudents.setCellValueFactory(new PropertyValueFactory<AssistantMyTeamsForTeamStudentController, String>("taskSetName"));
        try {
            myTeams.getItems().addAll(getAssistantMyTeamsForTeamStudentControllerArray(tdb.getStudentsTeams(App.getLoggedInUser().getUser_ID())));
        } catch (Exception ex) {
            Logger.getLogger(TeamStudentController.class.getName()).log(Level.SEVERE, null, ex);
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

    private ArrayList<AssistantMyTeamsForTeamStudentController> getAssistantMyTeamsForTeamStudentControllerArray(ArrayList<Team> studentsTeams) {
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        ArrayList<AssistantMyTeamsForTeamStudentController> myTeams = new ArrayList();
        for(int i = 0; i < studentsTeams.size(); i++){
            for(int m = 0; m < studentsTeams.get(i).getTaskSet().size(); m++){
                if(studentsTeams.get(i).getTaskSet().get(m).getHandedIn()==false){
                    myTeams.add(new AssistantMyTeamsForTeamStudentController(studentsTeams.get(i).getTeamName(),studentsTeams.get(i).getTaskSet().get(m).getName()));
                }
            }
            
        }
        return myTeams;
    }
}
