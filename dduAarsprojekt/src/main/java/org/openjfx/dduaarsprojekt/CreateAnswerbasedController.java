/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.net.URL;
import java.sql.Savepoint;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.openjfx.dduaarsprojekt.TestClasses.CorrectAnswerBasedQuestion; 

/**
 * FXML Controller class
 *
 * @author danie
 */
public class CreateAnswerbasedController implements Initializable {
    TextField question;
    TextArea correct;
    Button point;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    public void save(){
        String q = question.getText();
        String c = correct.getText();
        TestMakerController.tasks.add(new CorrectAnswerBasedQuestion(c,q));
    }
}
