/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class AnswerTestController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private VBox question;
    private Parent fxml;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("answerOpen.fxml"));
            question.getChildren().setAll(fxml); 
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }    
    @FXML
    private void exit(){
        System.exit(0);   
    }
    @FXML
    private void answerAnswerbased () {
        try {
            fxml = FXMLLoader.load(getClass().getResource("answerAnswerbased.fxml"));
            question.getChildren().setAll(fxml); 
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    @FXML 
    private void answerMult () {
        try {
            fxml = FXMLLoader.load(getClass().getResource("answerMult.fxml"));
            question.getChildren().setAll(fxml); 
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    @FXML
    private void answerCorrect () {
        try {
            fxml = FXMLLoader.load(getClass().getResource("answerCorrect.fxml"));   
            question.getChildren().setAll(fxml); 
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    @FXML
    private void tests() throws IOException{
        App.setRoot("testStudent");
    }
}