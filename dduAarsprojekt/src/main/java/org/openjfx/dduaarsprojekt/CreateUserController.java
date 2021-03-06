/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.dduaarsprojekt;

import org.openjfx.dduaarsprojekt.databaseRepository.UserDatabasemethods;
import org.openjfx.dduaarsprojekt.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.dduaarsprojekt.App;
import org.openjfx.dduaarsprojekt.random.SecurityMethods;
import org.openjfx.dduaarsprojekt.random.Teacher;

/**
 *
 * @author chris
 */
public class CreateUserController {
    
    @FXML
    private TextField textFieldUsername;
    @FXML
    private Text textErroMessage;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFieldrepeatPassword;
    @FXML
    private TextField textFieldSchoolKey;

    @FXML
    private void creatUser(ActionEvent event) throws Exception {
        textErroMessage.setText("");

        //Check if all fields is filled
        if (!textFieldUsername.getText().isBlank()
                && !passwordFieldPassword.getText().isBlank()
                && !passwordFieldrepeatPassword.getText().isBlank()
                && !textFieldSchoolKey.getText().isBlank()) {

            //Check if user already exist
            UserDatabasemethods userDatabasemethods = new UserDatabasemethods();
            if (!userDatabasemethods.cehckForMatchingUser(textFieldUsername.getText())) {
                
                //Check if password have a special and uppercase character and at least 8 carachters long
                boolean passwordMeetsRequirements = false;
                char[] passwordChars = passwordFieldPassword.getText().toCharArray();

                Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(passwordFieldPassword.getText());

                //length
                if (passwordFieldPassword.getText().length() >= 8) {
                    //special caracahter
                    if (matcher.find()) {
                        //upper case carachter
                        for (int i = 0; i < passwordChars.length; i++) {
                            if (Character.isUpperCase(passwordChars[i])) {
                                passwordMeetsRequirements = true;
                                break;
                            }
                        }
                        //passwords is identicel
                        if (passwordMeetsRequirements) {
                            if (passwordFieldPassword.getText().equals(passwordFieldrepeatPassword.getText())) {

                                SecurityMethods securityMethods = new SecurityMethods();

                                if (userDatabasemethods.checkForExistingKey(securityMethods.hexString(textFieldSchoolKey.getText()))) {

                                    userDatabasemethods.createTeacher(new Teacher(securityMethods.hexString(textFieldSchoolKey.getText()), 0, 0, 
                                            textFieldUsername.getText(), securityMethods.hexString(passwordFieldPassword.getText())));
                                    
                                    App.setLoggedInUser(userDatabasemethods.getLoggedInUser(textFieldUsername.getText()));
                                    
                                    //hop vider mangler fxml App.setRoot("");
                                    System.out.println(App.getLoggedInUser().getType() + " logged in");
                                } else {
                                    textErroMessage.setText("School key dosent exist");
                                }
                            } else {
                                textErroMessage.setText("Password meets requirements, but donsen't match");
                            }
                        } else {
                            textErroMessage.setText("Password is missing a uppercase character");
                        }
                    } else {
                        textErroMessage.setText("Password is missing a special character");
                    }
                } else {
                    textErroMessage.setText("Password needs to be at least 8 characters long");
                }
            } else {
                textErroMessage.setText("User already exist");
            }
        } else {
            textErroMessage.setText("All fields need to be filled");
        }

    }

    @FXML
    private void switchToLoginScreen(ActionEvent event) throws IOException, Exception {
        App.setRoot("login");
    }
}
