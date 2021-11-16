/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author Bruger
 */
public class logInSystem {
    @FXML
    private VBox vbox;
    @FXML
    private Parent fxml;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private void openSignup(ActionEvent event) {
         try {
             //insert name of page
             fxml = FXMLLoader.load(getClass().getResource("signup.fxml"));
             vbox.getChildren().removeAll();
             vbox.getChildren().setAll(fxml);
                        
         } catch (IOException ex) {
            
         }
    }

    @FXML
    public void switchToMain(ActionEvent event) throws IOException, Exception {
        Database myDatabase = new Database();
        if(myDatabase.login(username.getText(), password.getText()) == true){
            //insert name of page
           //App.setRoot("main");
           
        }
        if(myDatabase.login(username.getText(), password.getText()) == false){
           //fejlmelding appear men er for træt og kan ikke huske sql lige nu
        }
    }
}
