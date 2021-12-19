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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class TeamStaffController implements Initializable {
   
    public static ArrayList<Student> studentsOnCurrentTeam = new ArrayList();
            
    @FXML
    TableView<AssistantMyTeamsForTeamStaffController> teams;
    @FXML
    TableColumn<AssistantMyTeamsForTeamStaffController, String> teamName;
    @FXML
    TableColumn<AssistantMyTeamsForTeamStaffController, Integer> tests;
    @FXML
    TableColumn<AssistantMyTeamsForTeamStaffController, Integer> numberOfStudents;
    @FXML
    TableView<Student> addStudents;
    @FXML
    TableColumn<Student, Integer> studentID;
    @FXML
    TableColumn<Student, String> studentFirstName;
    //@FXML
    //TableColumn<Student, String> studentLastName;
    @FXML
    TextField newTeamName;
    @FXML
    ListView currentStudents;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        studentsOnCurrentTeam.clear();
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        ArrayList<Team> teachersTeams = new ArrayList();
        try {
            teachersTeams = tdb.getTeachersTeams(App.getLoggedInUser().getUser_ID());
        } catch (Exception ex) {
            Logger.getLogger(TeamStaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
        CellValues();
        //teams.getColumns().addAll(teamName, tests, numberOfStudents);
        teams.getItems().addAll(getAssistantMyTeamsForTeamStaffControllerArray(teachersTeams));
        try {
            //ERROR HER
            //addStudents.getItems().addAll(tdb.getSchoolsStudents(1).removeAll(studentsOnCurrentTeam));
            //Midlertidig
            addStudents.getItems().addAll(tdb.getSchoolsStudents(1));
        } catch (Exception ex) {
            Logger.getLogger(TeamStaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void addStudent() throws Exception{
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        studentsOnCurrentTeam.add(addStudents.getSelectionModel().getSelectedItem());
        //ERROR HER
            //addStudents.getItems().addAll(tdb.getSchoolsStudents(1).removeAll(studentsOnCurrentTeam));
            //Midlertidig
            addStudents.getItems().addAll(tdb.getSchoolsStudents(1));
            currentStudents.setItems((ObservableList) studentsOnCurrentTeam);
    }
    @FXML
    private void removeStudent() throws Exception{
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        studentsOnCurrentTeam.remove(currentStudents.getSelectionModel().getSelectedItem());
        //ERROR HER
            //addStudents.getItems().addAll(tdb.getSchoolsStudents(1).removeAll(studentsOnCurrentTeam));
            //Midlertidig
            addStudents.getItems().addAll(tdb.getSchoolsStudents(1));
            currentStudents.setItems((ObservableList) studentsOnCurrentTeam);
    }
    @FXML
    private void CellValues(){
        teamName.setCellValueFactory(new PropertyValueFactory<AssistantMyTeamsForTeamStaffController, String>("teamName"));
        tests.setCellValueFactory(new PropertyValueFactory<AssistantMyTeamsForTeamStaffController, Integer>("tests"));
        numberOfStudents.setCellValueFactory(new PropertyValueFactory<AssistantMyTeamsForTeamStaffController, Integer>("numberOfStudents"));
        studentID.setCellValueFactory(new PropertyValueFactory<Student, Integer>("user_ID"));
        studentFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("username"));
    }
    
    private ArrayList<AssistantMyTeamsForTeamStaffController> getAssistantMyTeamsForTeamStaffControllerArray(ArrayList<Team> teachersTeams){
        ArrayList<AssistantMyTeamsForTeamStaffController> myList = new ArrayList();
        for(int i = 0; i < teachersTeams.size(); i++){
            myList.add(new AssistantMyTeamsForTeamStaffController(teachersTeams.get(i).getTeamName(),teachersTeams.get(i).getTaskSet().size(),teachersTeams.get(i).getStudents().size()));
        }
        return myList;
    }
    @FXML
    private void saveTeam() throws Exception{
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        tdb.createTeam(new Team(App.getLoggedInUser().getUser_ID(), newTeamName.getText(), new ArrayList<TaskSet>(), studentsOnCurrentTeam));
        
        //Reset af lister
        studentsOnCurrentTeam.clear();
        currentStudents.setItems((ObservableList) studentsOnCurrentTeam);
        addStudents.getItems().addAll(tdb.getSchoolsStudents(1));
        
        ArrayList<Team> teachersTeams = new ArrayList();
        try {
            teachersTeams = tdb.getTeachersTeams(App.getLoggedInUser().getUser_ID());
        } catch (Exception ex) {
            Logger.getLogger(TeamStaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
        teams.getItems().addAll(getAssistantMyTeamsForTeamStaffControllerArray(teachersTeams));
        
        
    }
}
