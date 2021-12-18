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
 *
 * @author clara
 */
public class TestFeedbackStaffController implements Initializable {
    @FXML private VBox question;
    @FXML private VBox feedback;
    private Parent fxml1;
    private Parent fxml2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fxml1 = FXMLLoader.load(getClass().getResource("answerOpen.fxml"));
            fxml2 = FXMLLoader.load(getClass().getResource("feedbackOpen.fxml"));
            question.getChildren().setAll(fxml1); 
            feedback.getChildren().setAll(fxml2);
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    } 
    
    @FXML
    private void openAnswerBased (){
        try {
            fxml1 = FXMLLoader.load(getClass().getResource("answerAnswerbased.fxml"));
            fxml2 = FXMLLoader.load(getClass().getResource("feedback.fxml"));
            feedback.getChildren().setAll(fxml2);
            question.getChildren().setAll(fxml1); 
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    @FXML
    private void openCorrect (){
        try {
            fxml1 = FXMLLoader.load(getClass().getResource("answerCorrect.fxml"));
            fxml2 = FXMLLoader.load(getClass().getResource("feedback.fxml"));
            feedback.getChildren().setAll(fxml2);
            question.getChildren().setAll(fxml1); 
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    @FXML
    private void openMult (){
        try {
            fxml1 = FXMLLoader.load(getClass().getResource("answerMult.fxml"));
            fxml2 = FXMLLoader.load(getClass().getResource("feedback.fxml"));
            feedback.getChildren().setAll(fxml2);
            question.getChildren().setAll(fxml1); 
        } catch (IOException ex) {
            Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
