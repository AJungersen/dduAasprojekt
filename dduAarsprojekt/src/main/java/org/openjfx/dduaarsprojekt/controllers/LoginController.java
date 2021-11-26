/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt.controllers;

import org.openjfx.dduaarsprojekt.databaseRepository.UserDatabasemethods;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.dduaarsprojekt.App;
import org.openjfx.dduaarsprojekt.SecurityMethods;

/**
 *
 * @author chris
 */
public class LoginController implements Initializable{
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Text textErrorMessage;
    @FXML
    private TableView tableViewTest;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
    }
    
    @FXML
    private void login(ActionEvent event) throws Exception {
        textErrorMessage.setText("");
        
        //check if user exist
        UserDatabasemethods userDatabasemethods = new UserDatabasemethods();
        if (userDatabasemethods.cehckForMatchingUser(textFieldUsername.getText())) {

            //check if user and passowrd match
            SecurityMethods securityMethods = new SecurityMethods();
            if (userDatabasemethods.checkForMatchingPassword(textFieldUsername.getText(),
                    securityMethods.hexString(passwordFieldPassword.getText()))) {

                App.setLoggedInUser(userDatabasemethods.getLoggedInUser(textFieldUsername.getText()));
                
                System.out.println(App.getLoggedInUser().getType() + " logged in");

            } else {
                textErrorMessage.setText("user dosen't exist or password dont match");
            }
        } else {
            textErrorMessage.setText("user dosen't exist or password dont match");
        }
    }

    @FXML
    private void switchToCreateUser(ActionEvent event) throws Exception {
        App.setRoot("createUser");
    }
}
