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
import org.openjfx.dduaarsprojekt.TestClasses.Question;

/**
 *
 * @author danie
 */
public class AnswerAnswerbasedController implements Initializable {

    Label Question;
    TextField Answer;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Question.setText("string fra database");
    }
    
    @FXML
    public void saveAnswer(){
        Answer.getText();
    }
}
