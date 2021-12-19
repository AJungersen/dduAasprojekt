package org.openjfx.dduaarsprojekt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import AssistantClasses.AssistantMyTeamsForTeamStaffController;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.dduaarsprojekt.databaseRepository.TestDatabaseMethods;
import org.openjfx.dduaarsprojekt.random.Team;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TeamStaffController implements Initializable {
   
    @FXML
    TableView<AssistantMyTeamsForTeamStaffController> teams;
    @FXML
    TableColumn<AssistantMyTeamsForTeamStaffController, String> teamName;
    @FXML
    TableColumn<AssistantMyTeamsForTeamStaffController, Integer> tests;
    @FXML
    TableColumn<AssistantMyTeamsForTeamStaffController, Integer> numberOfStudents;
    @FXML
    TableView addStudents;
    @FXML
    TableColumn studentID;
    @FXML
    TableColumn studentFirstName;
    @FXML
    TableColumn studentLastName;
    @FXML
    TextField newTeamName;
    @FXML
    ListView currentStudents;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        ArrayList<Team> teachersTeams = new ArrayList();
        try {
            teachersTeams = tdb.getTeachersTeams(App.getLoggedInUser().getUser_ID());
        } catch (Exception ex) {
            Logger.getLogger(TeamStaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addToMyTeams();
        //teams.getColumns().addAll(teamName, tests, numberOfStudents);
        teams.getItems().addAll(getAssistantMyTeamsForTeamStaffControllerArray(teachersTeams));
    }    
   
   @FXML
    private void mineHold() throws IOException {
        App.setRoot("teamStaff");
    }
    
    @FXML
    private void minePrøver() throws IOException {
        App.setRoot("testStaff");
    }
    
    @FXML
    private void forside() throws IOException{
        App.setRoot("main");
    }
    
    @FXML
    private void logud() throws IOException{
        App.setRoot("frontPage");
    }
    @FXML
    private void exit() {
        System.exit(0);
    }
    @FXML
    private void info () throws IOException{
        App.setRoot("teamInformation");
    }
    @FXML
    private void addStudent(){
        
    }
    @FXML
    private void addToMyTeams(){
        teamName.setCellValueFactory(new PropertyValueFactory<AssistantMyTeamsForTeamStaffController, String>("teamName"));
        tests.setCellValueFactory(new PropertyValueFactory<AssistantMyTeamsForTeamStaffController, Integer>("tests"));
        numberOfStudents.setCellValueFactory(new PropertyValueFactory<AssistantMyTeamsForTeamStaffController, Integer>("numberOfStudents"));
       
    }
    
    private ArrayList<AssistantMyTeamsForTeamStaffController> getAssistantMyTeamsForTeamStaffControllerArray(ArrayList<Team> teachersTeams){
        ArrayList<AssistantMyTeamsForTeamStaffController> myList = new ArrayList();
        for(int i = 0; i < teachersTeams.size(); i++){
            myList.add(new AssistantMyTeamsForTeamStaffController(teachersTeams.get(i).getTeamName(),teachersTeams.get(i).getTaskSet().size(),teachersTeams.get(i).getStudents().size()));
        }
        return myList;
    }
}
