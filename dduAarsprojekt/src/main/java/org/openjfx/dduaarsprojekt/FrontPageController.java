package org.openjfx.dduaarsprojekt;

import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.openjfx.dduaarsprojekt.databaseRepository.UserDatabasemethods;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.dduaarsprojekt.App;
import org.openjfx.dduaarsprojekt.random.*;
import org.openjfx.dduaarsprojekt.random.SecurityMethods;
import org.openjfx.dduaarsprojekt.TestClasses.QuestionsType;

public class FrontPageController implements Initializable {
    private static User loggedInUser = new User();
    
    @FXML private VBox vbox;
    private Parent fxml;
    private VBox returnvbox;
    @FXML private Button returnButton;
    @FXML private Button loginButton;
    @FXML private TextField textFieldUsername;
    @FXML private PasswordField passwordFieldPassword;
    @FXML private Text textErrorMessage;
    
    @FXML
    private void handleClose(){
        System.exit(0);   
    }
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("createUser.fxml"));
            vbox.getChildren().removeAll();
            vbox.getChildren().setAll(fxml);            
        } catch (IOException ex) {
            Logger.getLogger(FrontPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        QuestionsType qType = QuestionsType.valueOf("correctAnswerBasedQuestion");        
        System.out.println(qType);
        
        System.out.println("\n" + qType.toString());
    }    
    @FXML
    private void openSignUp (ActionEvent event) {
        //returnButton.setVisible(true);
        
        // returnvbox.setVisible(false);
     TranslateTransition t = new TranslateTransition(Duration.seconds(1),vbox);
       t.setToY(vbox.getLayoutX()*-150);
       t.play();
       t.setOnFinished((e)->{
       }); 
       TranslateTransition s = new TranslateTransition(Duration.seconds(1),returnButton);
       s.setToY(55);
       s.play();
       s.setOnFinished((e)->{     
       });
       //returnvbox.setVisible(false);
    }
    
    @FXML
    public void openLogin () {
     TranslateTransition t = new TranslateTransition(Duration.seconds(1),vbox);
       t.setToY(0);
       t.play();
       t.setOnFinished((e)->{
           vbox.getChildren().removeAll();     
       }); 
       TranslateTransition s = new TranslateTransition(Duration.seconds(1),returnButton);
       s.setToY(0);
       s.play();
       s.setOnFinished((e)->{     
       });
       
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
                
                if(App.getLoggedInUser().getType() == "teacher"){
                    App.setRoot("main");
                }else{
                    App.setRoot("mainStudent");
                }

            } else {
                textErrorMessage.setText("user dosen't exist or password dont match");
            }
        } else {
            textErrorMessage.setText("user dosen't exist or password dont match");
        }
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }
}
