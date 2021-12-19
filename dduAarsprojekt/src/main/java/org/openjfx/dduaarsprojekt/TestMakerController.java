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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.openjfx.dduaarsprojekt.TestClasses.Question;
import org.openjfx.dduaarsprojekt.TestClasses.Task;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;
import org.openjfx.dduaarsprojekt.databaseRepository.TestDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TestMakerController implements Initializable {
    public static TaskSet current;
    public static ArrayList<Task> tasks;
    
    @FXML
    ListView questionList;
    ComboBox options;
    ListView questions;
    
    @FXML private VBox create;
    @FXML private VBox show;  
    private Parent fxml1;
    private Parent fxml2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        create.getChildren().removeAll();
        show.getChildren().removeAll();
        
        /*ArrayList<Question>taskList = new ArrayList<Question>(tasks);
         for(int i = 0; i < tasks.size(); i++){
             questions.getItems().add(tasks.get(i).getQuestion());
         }*/
         
    }

    @FXML
    public void createMultQuestion() throws IOException{
        try {
            fxml1 = FXMLLoader.load(getClass().getResource("answerMult.fxml"));
            fxml2 = FXMLLoader.load(getClass().getResource("createMult.fxml"));
            show.getChildren().setAll(fxml1);   
            create.getChildren().setAll(fxml2); 
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @FXML
    public void createAnswerbasedQuestion() throws IOException{
        try {
            fxml1 = FXMLLoader.load(getClass().getResource("answerAnswerbased.fxml"));
            fxml2 = FXMLLoader.load(getClass().getResource("createAnswerbased.fxml"));
            show.getChildren().setAll(fxml1);   
            create.getChildren().setAll(fxml2); 
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void createCorrectQuestion() throws IOException{
        try {
            fxml1 = FXMLLoader.load(getClass().getResource("answerCorrect.fxml"));
            fxml2 = FXMLLoader.load(getClass().getResource("createCorrect.fxml"));
            show.getChildren().setAll(fxml1);   
            create.getChildren().setAll(fxml2); 
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
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
        App.setRoot("login");
    }
    
    @FXML
    public void savetaskSet() throws Exception, Exception{
        TestDatabaseMethods tdb = new TestDatabaseMethods();
        current.setTasks(tasks);
        
        tdb.createTaskSet(current, App.getLoggedInUser().getUser_ID());
    }
    @FXML
    private void exit() {
        System.exit(0);
    }
}
