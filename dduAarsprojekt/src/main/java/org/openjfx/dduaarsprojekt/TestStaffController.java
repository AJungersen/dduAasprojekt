/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import org.openjfx.dduaarsprojekt.databaseRepository.UserDatabasemethods;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.openjfx.dduaarsprojekt.App;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;
import static org.openjfx.dduaarsprojekt.TestMakerController.current;
import org.openjfx.dduaarsprojekt.databaseRepository.TestDatabaseMethods;
/**
 *
 * @author danie
 */
public class TestStaffController implements Initializable{
    @FXML TableView doneTest;
    @FXML TableView pendingTests;
    @FXML TableColumn doneName;
    @FXML TableColumn doneCorrect;
    @FXML TableColumn doneParticipation;
    @FXML TableColumn pendingName;
    @FXML TableColumn pendingCorrect;
    @FXML TableColumn pendingParticipation;
    @FXML TextArea description;
    @FXML TextField testName;
    
    
   @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
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
    private void forside() throws IOException{
        App.setRoot("main");
    }
       
    @FXML
    private void createNewTaskSet() throws IOException{
        App.setRoot("testMaker");
    }
    @FXML
    private void logout() throws IOException{
        App.setRoot("frontPage");
    }
    @FXML
    private void info () throws IOException{
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
