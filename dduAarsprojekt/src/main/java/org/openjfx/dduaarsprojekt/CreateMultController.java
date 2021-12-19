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
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import org.openjfx.dduaarsprojekt.TestClasses.MultipelChoiseAnswer;
import org.openjfx.dduaarsprojekt.TestClasses.MultipelChoiseQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.dduaarsprojekt.TestClasses.Task;


/**
 * FXML Controller class
 *
 * @author danie
 */
public class CreateMultController implements Initializable {
    @FXML TextField question;
    @FXML TextField Answer1;
    TextArea Answer2;
    TextArea Answer3;
    TextArea Answer4;
    ToggleButton correct1;
    ToggleButton correct2;
    ToggleButton correct3;
    ToggleButton correct4;
    Button saveQuestion;
    @FXML private Text questionNumber;
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    public void save() throws IOException{
        ArrayList<MultipelChoiseAnswer> answers = new ArrayList<>();
        answers.add(MultipelChoiseAnswer(Answer1.getText(),correct1.pressedProperty()));
        answers.add(MultipelChoiseAnswer(Answer2.getText(),correct2.pressedProperty()));
        answers.add(MultipelChoiseAnswer(Answer3.getText(),correct3.pressedProperty()));
        answers.add(MultipelChoiseAnswer(Answer4.getText(),correct4.pressedProperty()));
        
        TestMakerController.tasks.add(new Task(new MultipelChoiseQuestion(answers, question.getText()), null, null));
        App.setRoot("testMaker");
    }

    private MultipelChoiseAnswer MultipelChoiseAnswer(String text, ReadOnlyBooleanProperty pressedProperty) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @FXML
      private void addAnswer () {
          System.out.println("tilføj svarmulighed");
          
      }
    
}
