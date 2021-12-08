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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.openjfx.dduaarsprojekt.TestClasses.Question;
import org.openjfx.dduaarsprojekt.TestClasses.Task;
import org.openjfx.dduaarsprojekt.TestClasses.TaskSet;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TestMakerController implements Initializable {
    public static TaskSet current;
    public static ArrayList<Question> tasks;
    
    @FXML
    ListView questionList;
    TextArea description;
    TextField testName;
    ComboBox options;
    ListView questions;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Question>taskList = new ArrayList<Question>(tasks);
         for(int i = 0; i < tasks.size(); i++){
             questions.getItems().add(tasks.get(i).getQuestion());
         }
         
    }

    @FXML
    public void createMultQuestion() throws IOException{
        current.setName(testName.getText());
        current.setDescription(description.getText());
        App.setRoot("createMult");
    }
    
    @FXML
    public void createAnswerbasedQuestion() throws IOException{
        current.setName(testName.getText());
        current.setDescription(description.getText());
        App.setRoot("createAnswerbased");
    }
    
    @FXML
    public void savetaskSet(){
        //når funktionen er klar refereres til database og taskset uploades
    }
}
