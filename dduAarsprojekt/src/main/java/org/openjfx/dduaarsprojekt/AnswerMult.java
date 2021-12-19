/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author danie
 */
public class AnswerMult implements Initializable {
    @FXML private Text questionNumber;
    @FXML Text Question;
    @FXML Text Answer;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Question.setText("string fra database");
    }
    
    @FXML
    public void one(){
        saveAnswer(1);
    }
    
    @FXML
    public void two(){
        saveAnswer(2);
    }
    
    @FXML
    public void three(){
        saveAnswer(3);
    }
    
    @FXML
    public void four(){
        saveAnswer(4);
    }
    
    public void saveAnswer(int i){
        
    }
}
