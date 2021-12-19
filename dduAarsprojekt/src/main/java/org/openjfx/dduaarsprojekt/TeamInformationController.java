/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.openjfx.dduaarsprojekt.databaseRepository.TestDatabaseMethods;

/**
 *
 * @author clara
 */
public class TeamInformationController implements Initializable {
    public static int currentTeam = 0;
    @FXML
    ListView<String> yourTasks;
    @FXML
    TableView teamsTasks;
    @FXML
    TableColumn testName;
    @FXML
    TableColumn participation;
    @FXML
    ListView allStudents;
    @FXML
    TableView teamsStudents;
    @FXML
    TableColumn studentName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        for(int i = 0; i < tdb.getAllTeachersTaskSets(App.getLoggedInUser().getUser_ID()).size(); i++){
            yourTasks.getItems().add(tdb.getAllTeachersTaskSets(App.getLoggedInUser().getUser_ID()).get(i).getName());
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
}
